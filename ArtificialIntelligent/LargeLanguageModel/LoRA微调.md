# LoRA（Low-Rank Adaptation，低秩适应）

核心思想是相对于全量微调而言，只训练极少的参数，同时保持全量微调所能达到的性能。在某些特定层中添加两个低秩矩阵A和B，这些低秩矩阵包含了可训练的参数。这种方法是通过修改线性层中的权重矩阵$\delta W$来实现的。
$(W + \delta W)x = W x + A B x$。$W$是原始的权重矩阵，$\delta W$是权重的更新或调整。

- 低秩：一个矩阵的“秩”可以衡量其包含信息的内在维度。一个“低秩”矩阵意味着可以用两个更小、更薄的矩阵的乘积来高效地近似表示。即$\delta W = B * A$。

## 核心思想

把对某一层权重的更新用一个低秩矩阵来表示，只学习这个低秩增量，而锁死（freeze）原有的大矩阵权重。

## 核心公式

原线性层为
$$y = Wx, W \in R^{d_{out} \times d_{in}}, x \in R^{d_{in}}$$

LoRA改为
$$y = Wx + \frac{\alpha}{r} B(Ax)$$

- $A \in R^{r \times d_{in}}、B \in R^{d_{out} \times r}$；
- $r$ 是低秩（rank）的超参；
- $\alpha$是缩放因子（常见做饭把scaling=$\alpha / r$），用于控制增量幅度。

## 工作原理

![LoRA](images/LoRA.png)

1. 冻结预训练模型
2. 注入可训练的适配层
   - 在模型的某些层（通常是Transformer结构中的自注意力模块的Query，Key，Value，Output投影矩阵）旁边，并联地注入一对可训练的、低秩的矩阵A和B。
3. 修改前向传播
4. 只训练适配层
   - 只计算和更新低秩矩阵A和B的梯度。预训练权重W由于被冻结，其梯度为0。
5. 合并权重（用于推理）
   - 训练完成后，得到了训练好的A和B。在推理时，可以将LoRA适配器与原始权重直接合并，得到新的权重。
   - 合并后，模型在结构上和原始模型完全一样，没有任何额外的计算开销，推理速度保持不变。也可以不合并，在运行时动态加载LoRA权重，在需要快速切换多个任务的场景下非常有用。

## 其他变体

### LoRA-FA（LoRA-Frozen A）

矩阵A在初始化后被冻结，作为随机投影；矩阵B在用零初始化后进行训练（就像在LoRA中一样）。这样将参数数量减半，但具有与普通LoRA相当的性能。

### VeRA（Vector-based Random Maxtrix Adaptation，基于向量的随机矩阵适应）

VeRA可以进一步减少LoRA中可训练参数数量，同时能够匹配或接近LoRA的精度。

在VeRA中，矩阵A和B被冻结和随机化，并在所有模型层之间共享。消除了在不同层之间训练和更新大量参数的需求，简化了模型结构。

VeRA不更新矩阵A和B，而是专注于学习小型的、层特定的缩放向量，记作b和d，这些向量是层中唯一的可训练参数。不跨层共享。

- 矩阵A从正态分布中随机初始化，并且在微调过程中不进行训练。类似于LoRA中的做法。VeRA的这个权重矩阵在所有层之间共享。
- 矩阵B从正态分布中随机初始化，在LoRA中，B的初始化值是0，但在VeRA中不能这样做，因为矩阵B不训练，初始化为0将导致输出全为0。这个权重矩阵在所有层之间共享。
- 向量d的初始值为全1，并在微调过程中进行训练：
- 向量b的初始值为全0，同样微调过程中进行训练。以零初始化确保在微调开始时不对模型进行任何更新。

### Delta-LoRA

### LoRA+

### LoRA-drop

激活剪枝（Activation Pruning）是一种剪枝技术，通过分析神经网络中某些神经元的激活值来确定哪些神经元对模型输出的重要性较低，从而将其删除。

核心目标是减少模型复杂性、加速推理并降低模型参数量。若一个神经元很少有较高的激活值，那么它对模型输出的贡献就很小。

对应到LoRA，需要重新判断在每一层中添加低秩矩阵是否真的有必要。是否可以只在一些层中添加LoRA矩阵，而实现其性能在与所有层中都添加效果相同。

LoRA-drop首先判断在哪些层中添加LoRA矩阵是必要的。

## 代码示例

```python
import json
import os

import torch
from transformers import AutoTokenizer, AutoModelForCausalLM, TrainingArguments, Trainer
from data_prepare import samples
from datasets import load_dataset
from transformers import BitsAndBytesConfig
from peft import get_peft_model, LoraConfig, TaskType, PeftModel

device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

# 1.加载模型
model_path = os.path.abspath(
    '/finetune/model/DeepSeek-R1-Distill-Qwen-1.5B')
tokenizer = AutoTokenizer.from_pretrained(model_path, local_files_only=True)
if device == 'cuda':
    quantization_config = BitsAndBytesConfig(
        load_in_8bit=True,
        llm_int8_has_fp16_weight=True
    )
    model = AutoModelForCausalLM.from_pretrained(
        model_path,
        quantization_config=quantization_config,
        device_map="auto",
        local_files_only=True
    )
else:
    model = AutoModelForCausalLM.from_pretrained(model_path, local_files_only=True)

print("------模型加载完成-------")

# 2.制作数据集
if not samples or len(samples) == 0:
    raise ValueError("samples为空，请检查data_prepare模块")

with open('datasets.jsonl', 'w', encoding='utf-8') as f:
    for s in samples:
        json_line = json.dumps(s, ensure_ascii=False)
        f.write(json_line + '\n')
    else:
        print('prepare data finished')
print("------数据集制作完成-------")

# 3.准备训练集和测试集
dataset = load_dataset('json', data_files={'train': 'datasets.jsonl'}, split='train')
train_dataset, test_dataset = dataset.train_test_split(test_size=0.2).values()

print(f'train dataset size: {len(train_dataset)}')
print(f'test dataset size: {len(test_dataset)}')


# 4.编写tokenizer处理工具
def tokenize(batch):
    texts = [f"{prompt}\n{completion}" for prompt, completion in zip(batch['prompt'], batch['completion'])]
    tokens = tokenizer(texts, truncation=True, padding='max_length', max_length=512)
    tokens['labels'] = tokens['input_ids'].copy()  # 对于因果语言模型，labels需要和input_ids相同
    return tokens


# 5.进行tokenize
tokenized_train_dataset = train_dataset.map(tokenize, batched=True)
tokenized_test_dataset = test_dataset.map(tokenize, batched=True)

print("------tokenize完成-------")
print(tokenized_train_dataset[0])

# 6.Lora微调
lora_config = LoraConfig(
    task_type=TaskType.CAUSAL_LM,  # 任务类型，这里为CausalLM
    r=8,  # LoRA的层数，即A和B矩阵的秩
    lora_alpha=16,  # LoRA的alpha值，可视为缩放因子，默认=r
    lora_dropout=0.05,  # LoRA的dropout率
    target_modules=['q_proj'],  # 目标模块列表，即LoRA优化的模型部分
    bias='none',  # 偏置类型，这里只训练权重
)
model = get_peft_model(model, lora_config)  # 获取参数高效微调的LoRA模型
model.print_trainable_parameters()
print("------Lora微调设置完成-------")

# 7.设置训练参数
train_arg = TrainingArguments(
    output_dir='./results',
    num_train_epochs=5,  # 对于微调，5个epoch可能足够
    per_device_train_batch_size=4 if device == 'cuda' else 2,
    per_device_eval_batch_size=8 if device == 'cuda' else 4,  # 评估批次可以更大
    gradient_accumulation_steps=4 if device == 'cuda' else 2,  # 调整累积步数
    warmup_steps=50,  # 根据数据集大小调整
    logging_steps=20,  # 减少日志频率
    save_steps=200,  # 减少保存频率
    eval_strategy='steps',
    eval_steps=50,  # 减少评估频率
    fp16=device == 'cuda',
    learning_rate=2e-4,  # LoRA通常使用稍大的学习率
    logging_dir='./logs',
    run_name='DeepSeek-R1-Distill-Qwen-1.5B-Lora-Finetune',
    dataloader_pin_memory=device == 'cuda',  # CUDA时启用pin_memory
    load_best_model_at_end=True,
    metric_for_best_model='eval_loss',
    greater_is_better=False,
    save_total_limit=3,  # 只保存最好的3个检查点
    prediction_loss_only=True,  # 只计算损失，加速评估
)
trainer = Trainer(
    model=model,
    args=train_arg,
    train_dataset=tokenized_train_dataset,
    eval_dataset=tokenized_test_dataset,
    tokenizer=tokenizer
)

print('------开始训练------')
trainer.train()
trainer.save_model()
print('------训练结束------')

# 8.保存模型
# lora模型保存
save_path = './model/DeepSeek-R1-Distill-Qwen-1.5B-Lora-Finetune'
model.save_pretrained(save_path)
tokenizer.save_pretrained(save_path)
print(f'lora模型保存在：{save_path}')
print('------模型保存完成------')

# 全量模型保存
final_save_path = './model/DeepSeek-R1-Distill-Qwen-1.5B-Finetune-full'
base_model = AutoModelForCausalLM.from_pretrained(model_path)
model = PeftModel.from_pretrained(base_model, save_path)
model = model.merge_and_unload()

model.save_pretrained(final_save_path)
tokenizer.save_pretrained(final_save_path)
print(f'全量模型保存在：{final_save_path}')

```
