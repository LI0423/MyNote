# LlaMA-Factory

## 命令示例

```bash
llamafactory-cli train \
    --stage sft \
    --do_train True \
    --model_name_or_path /opt/vllm/Qwen3-4B \
    --preprocessing_num_workers 16 \
    --finetuning_type lora \
    --template qwen3 \
    --flash_attn auto \
    --dataset_dir data \
    --dataset class_train2 \
    --cutoff_len 2048 \
    --learning_rate 5e-05 \
    --num_train_epochs 3.0 \
    --max_samples 100000 \
    --per_device_train_batch_size 2 \
    --gradient_accumulation_steps 8 \
    --lr_scheduler_type cosine \
    --max_grad_norm 1.0 \
    --logging_steps 5 \
    --save_steps 100 \
    --warmup_steps 0 \
    --packing False \
    --enable_thinking True \
    --report_to none \
    --output_dir saves/Qwen3-4B-Thinking/lora/train_2026-03-11-19-43-36 \
    --bf16 True \
    --plot_loss True \
    --trust_remote_code True \
    --ddp_timeout 180000000 \
    --include_num_input_tokens_seen True \
    --optim adamw_torch \
    --lora_rank 8 \
    --lora_alpha 16 \
    --lora_dropout 0 \
    --lora_target all \
```

### 训练参数

1. 训练任务类型
    - sft: Supervised Fine-tuning，监督微调，拿“输入-输出”监督样本直接训练模型，让模型学会按标注答案输出。
    - dpo: Direct Preference Optimization，直接偏好优化，拿“输入-好答案-坏答案”样本训练模型，让模型学会生成更好的答案。
    - alm: Auto-Labeling Model，自动标注模型，拿“输入”样本，让模型生成“输出”，然后人工审核，形成“输入-输出”样本，用于sft训练。
    - dlm: Deep Learning Model，深度学习模型，拿“输入”样本，让模型生成“输出”，然后人工审核，形成“输入-输出”样本，用于sft训练。
    - rm: Reward Model，奖励模型，拿“输入-好答案-坏答案”样本训练模型，让模型学会评估答案的好坏。
    - dpo: Direct Preference Optimization，直接偏好优化，拿“输入-好答案-坏答案”样本训练模型，让模型学会生成更好的答案。
    - ppo: Proximal Policy Optimization，近端策略优化，拿“输入-好答案-坏答案”样本训练模型，让模型学会生成更好的答案。

2. 模型相关
    - model_name_or_path: 模型路径，可以指定本地路径也可以是Hugging Face仓库名。
    - trust_remote_code: 允许加载模型仓库里自定义的Python代码。有些模型不是完全走Transformers的标准实现，而是在仓库里自带了相关代码。开启此参数，Transformers才会执行自定义的代码。

3. 数据预处理
    - preprocessing_num_workers: 数据预处理时启用的worker数。
    - template：表示使用模型对应的对话模版来组织训练样本。
    - dataset_dir: 数据集目录。
    - dataset: 使用的数据集。不是文件名，而是LlamaFactory数据集注册名，要在dataset_dir下的配置里找到对应定义。
    - cutoff_len: 单条样本的最大序列长度。
    - packing：是否启用sample packing。把多个短样本拼接到一个长序列里，提高token利用率、减少padding浪费。
    - max_samples: 训练样本数量上限。如果超过这个数据，会截取前面的部分；如果不足，就按照实际数量训练。

4. 微调方式相关
    - finetuning_type: 微调方式。LoRA的核心思路是冻结原模型大部分权重，只给目标线性层挂小型低秩适配器，实际训练的参数量大幅下降。
    - lora_rank: LoRA的秩，rank越大，LoRA适配器容量越强，训练参数越多、显存也增加。
    - lora_alpha: LoRA的缩放系数。通常设置为lora_rank的两倍。
    - lora_dropout: LoRA的dropout率，设置为0表示LoRA路径不做随机丢弃，收敛更直接，小数据集上更容易拟合。正则化更弱，数据少时更容易过拟合。
    - lora_target: all表示把LoRA挂到所有可支持的目标模块上。不只作用在q_proj/v_proj，而是更广泛地覆盖注意力层和MLP里的线性层。

5. 注意力/推理结构相关
    - flash_attn: 是否启用Flash Attention，并让框架自动判断。如果当前环境、模型、torch/cuda兼容，就启用；不兼容就回退到普通attention实现。
    - enable_thinking: 和模型模版有关，会影响训练样本中是否保留/使用thinking相关标记与格式。

6. 训练超参数
    - learning_rate: 学习率，优化器每一步更新参数的步长。
    - num_train_epochs: 训练轮数，整个数据集会被完整遍历多少次。
    - per_device_train_batch_size: 每张卡上的训练batch size。
    - gradient_accumulation_steps: 梯度累积步数做一次参数更新。
    - lr_scheduler_type: 学习率衰减策略，cosine特点是前期学习率高，后期平滑下降到较低值，比固定学习率更稳。
    - warmup_steps: 预热，0表示不做预热，也就是一开始直接使用设定学习率及调度策略。通常用于避免刚开始梯度不稳定，提高训练前期稳定性。
    - max_grad_norm: 表示梯度裁剪阈值，当梯度范数过大时，会被裁减到这个上限，避免梯度爆炸。

7. 日志与保存
    - logging_steps: 每多少个step记录一次日志。
    - save_steps: 每多少个step保存一次checkpoint。保存内容一般包括LoRA adapter权重，optimizer state，scheduler state，trainer state，如果训练中断，可以从checkpoint恢复。
    - plot_loss: 表示训练结束后或保存时绘制loss曲线图。
    - output_dir: 模型保存路径。

8. 精度与优化器
    - bf16: 是否使用bfloat16混合精度训练。通常要求硬件支持BF16。显存占用低，训练快，相比FP16数值稳定性更好。
    - optim: 优化器选择。adamw_torch是PyTorch原生实现的AdamW优化器，

9. 分布式与统计项
    - ddp_timeout: 分布式训练中，进程间通信的超时时间。
    - include_num_input_tokens_seen: 表示在训练统计中记录已经看到的输入token数。通常用于更细粒度地追踪训练进度。

10. 实验上报
    - report_to: 指定训练统计上报的平台。
