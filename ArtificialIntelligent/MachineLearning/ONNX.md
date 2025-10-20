# ONNX（Open Neural Network Exchange，开放神经网络交换）

一个开放标准，主要用于深度学习模型的跨框架和跨平台部署。核心目标是解决AI生态中不同训练框架和推理环境之间的兼容性问题，让模型可以一次训练，到处运行。

## 核心价值

- 打破框架壁垒：ONNX能将在一种框架中训练的模型，转换为ONNX格式，然后在另一个框架或推理引擎中使用，避免了不同平台重写或新训练模型，节省了大量开发时间和资源。
- 优化推理性能：专门的ONNX推理引擎可以对模型进行图优化、内核调优等操作，从而在不同硬件上实现高性能的推理速度。

## 工作流程

1. 模型训练与导出：在一个支持ONNX的框架（如PyTorch）中完成模型训练，然后使用框架提供的工具将训练好的模型导出为.onnx文件。这个文件包含模型的网络结构和所有训练好的参数。
2. 模型转换与优化
3. 跨平台推理

## 使用流程

### ONNX模型转换

```Python
import torch
from torchvision import models

device = torch.device('cuda:0' if torch.cuda.is_available() else 'CPU')

model = models.resnet18(pretrained=True)
model = model.eval().to(device)

x = torch.randn(1, 3, 256, 256).to(device)

output = model(x)

print(output.shape)

"""Pytorch模型转换ONNX格式"""
with torch.no_grad():
    torch.onnx.export(
        model, # 要转换的模型
        x, # 模型的任意一组输入
        'resnet18_imagenet.onnx', # 导出的ONNX文件名
        opset_version=11, # ONNX算子集版本
        input_names=['input'], # 输入Tensor的名称
        output_names=['output'] # 输出Tensor的名称
    )
```

### ONNX模型加载与运行

```Python
"""验证ONNX模型导出成功"""
import onnx

onnx_model = onnx.load('resnet18_imagenet.onnx')
onnx.checker.check_model(onnx_model)

print('无报错，ONNX模型转换成功')

"""加载ONNX模型"""
import onnxruntime as ort
import numpy as np

# 创建推理会话
session = ort.InferenceSession('YOLOv8s.onnx')

# 获取输入输出信息
input_names = session.get_inputs()[0].name
output_names = session.get_outputs()[0].name

# 准备输入数据 (需要转换为Numpy数组并确保形状和类型正确)
input_data = np.random.randn(1, 3, 224, 224).astype(np.float32)

# 进行推理
outputs = session.run([output_name], {input_name: input_data})
```
