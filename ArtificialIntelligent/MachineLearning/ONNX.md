# ONNX详解

## ONNX模型转换

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

"""验证ONNX模型导出成功"""
import onnx

onnx_model = onnx.load('resnet18_imagenet.onnx')
onnx.checker.check_model(onnx_model)

print('无报错，ONNX模型转换成功')
```
