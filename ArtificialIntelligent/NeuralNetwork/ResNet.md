# ResNet（Residual Networks，残差网络）

残差网络核心思想是通过引入残差连接（residual connection）来解决深层网络的训练问题。在传统的神经网络中，每一层的输出都是来自于前一层的输出。而在残差网络中，每一层的输出是由前一层的输出与该层的输入之和的到的。残差连接可以看作是一个跳跃连接，将前一层的信息直接传递给后面的层。

## 结构

残差网络的基本构建单元是残差块。

标准的残差块结构包含如下部分：

1. 主路径：一般由两个或三个卷积层组成，中间包含Batch Normalization和ReLU激活函数。
2. 捷径连接：也称为跳跃连接。将输入x直接绕道传到后面，与主路径的输出相加。
3. 相加操作：将捷径连接的输出x和主路径的输出$F(x)$进行逐元素相加，即$y = F(x) + x$。
4. 最后的激活函数：在相加之后，通常会再经过一个ReLU激活函数。

### 公式表达

$$y = F(x, {W_i}) + x$$
$$Out = ReLU(y)$$

如果捷径连接和主路径的输出维度不一致，就不能直接相加。此时需要在捷径连接上做一个线性投影（通常是1x1的卷积）来匹配维度：
$y = F(x, {W_i}) + W_s * x$。其中$W_s$是1x1的卷积。

## 代码实现

```python
import torch
import torch.nn as nn
import torch.nn.functional as F
from torch import Tensor
from typing import Type, Any, Callable, Union, Optional


# 基础残差块
class BasicBlock(nn.Module):
    expansion: int = 1

    def __init__(
        self,
        in_channels: int,
        out_channels: int,
        stride: int = 1,
        downsample: Optional[nn.Moudle] = None,
    ) -> None:
        super(BasicBlock, self).__init__()

        # 主路径的第一个卷积层
        self.conv1 = nn.Conv2d(
            in_channels,
            out_channels,
            kernel_size=3,
            stride=stride,
            padding=1,
            bias=False
        )
        self.bn1 = nn.BatchNorm2d(out_channels)

        # 主路径的第二个卷积层
        self.conv2 = nn.Conv2d(
            out_channels,
            out_channels,
            kernel_size=3,
            stide=1,
            padding=1,
            bias=False
        )
        self.bn2 = nn.BatchNorm2d(out_channels)

        # ReLU激活函数
        self.relu = nn.ReLU(inplace=True)
        self.stride = stride

    def forward(self, x: Tensor) -> Tensor:
        identity = x

        # 主路径的前向传播
        out = self.conv1(x)
        out = self.bn1(out)
        out = self.relu(out)

        out = self.conv2(out)
        out = self.bn2(out)

        # 如果需要进行下采样（维度匹配）
        if self.downsample is not None:
            identity = self.downsample(x)

        # 残差连接：主路径输出+捷径连接
        out += identity
        out = self.relu(out)

        return out

class Bottleneck(nn.Moudle):
    expansion: int = 4

    def __init__(
        self,
        in_channels: int,
        out_channels: int,
        stride = int = 1,
        downsample: Optional[nn.Moudle] = None,
    ) -> None:
        super(Bottleneck, self).__init__()

        # 1x1 卷积降维
        self.conv1 = nn.Conv2d(in_channels, out_channels, kernel_size=1, bias=False)
        self.bn1 = nn.BatchNorm2d(out_channels)

        # 3x3 卷积
        self.conv2 = nn.Conv2d(out_channels, out_channels, kernel_size=3, stride=stride, padding=1, bias=False)
        self.bn2 = nn.BatchNorm2d(out_channels)

        # 1x1 卷积升维
        self.conv3 = nn.Conv2d(out_channels, out_channels * self.expansion, kernel_size=1, bias=False)
        self.bn3 = nn.BatchNorm2d(out_channels * self.expansion)

        self.relu = nn.ReLU(inplace=True)
        self.downsample = downsample
        self.stride = stride

    def forward(self, x: Tensor) -> Tensor:
        identity = x

        out = self.conv1(x)
        out = self.bn1(out)
        out = self.relu(out)

        out = self.conv2(out)
        out = self.bn2(out)
        out = self.relu(out)

        out = self.conv3(out)
        out = self.bn3(out)

        if self.downsample is not None:
            identity = self.downsample(x)
        
        out += identity
        out = self.relu(out)
        return out

class ResNet(nn.Moudle):
    def __init__(
        self,
        block: Type[Union[BasicBlock, Bottleneck]],
        layers: List[int],
        num_classess: int = 1000,
        in_channels: int = 3,
    )
        super(ResNet, self).__init__()

        self.in_channels = 64

        # 初始卷积层
        self.conv1 = nn.Conv2d(
            in_channels,
            self.in_channels,
            kernel_size=7,
            stide=2,
            padding=3,
            bias=False
        )
        self.bn1 = nn.BatchNorm2d(self.in_channels)
        self.relu = nn.ReLU(inplace=True)
        self.maxpool = nn.MaxPool2d(kernel_size=3, stride=2, padding=1)

        # 四个残差层
        self.layer1 = self._make_layer(block, 64, layers[0])
        self.layer2 = self._make_layer(block, 128, layers[1], stride=2)
        self.layer3 = self._make_layer(block, 256, layers[2], stride=2)
        self.layer4 = self._make_layer(block, 512, layers[3], stride=2)

        # 分类器
        self.avgpool = nn.AdaptiveAvgPool2d((1, 1))
        self.fc = nn.Linear(512 * block.expansion, num_classes)

        # 权重初始化
        self._initialize_weights()

    def _make_layer(
        self,
        block: Type[Union[BasicBlock, Bottleneck]],
        out_channels: int,
        blocks: int,
        stride: int = 1,
    ) -> nn.Sequential:
        downsample = None

        # 当需要下采样或通道数变化时，创建下采样连接
        if stride != 1 or self.in_channels != out_channels * block.expansion:
            downsample = nn.Sequential(
                nn.Conv2d(
                    self.in_channels,
                    out_channels * block.expansion,
                    kernel_size=1,
                    stride=stride,
                    bias=False,
                ),
                nn.BatchNorm2d(out_channels * block.expansion),
            )

        layers = []
        # 第一个块可能需要下采样
        layers.append(
            block(
                self.in_channels, out_channels, stride, downsample
            )
        )
        self.in_channels = out_channels * block.expansion

        # 剩余的块
        for _ in range(1, blocks):
            layers.append(block(self.in_channels, out_channels))

        return nn.Sequential(*layers)

    def _initialize_weights(self) -> None:
        for m in self.modules():
            if isinstance(m, nn.Conv2d):
                nn.init.kaiming_normal_(m.weight, mode='fan_out', nonlinearity='relu')
            elif isinstance(m, nn.BatchNorm2d):
                nn.init.constant_(m.weight, 1)
                nn.init.constant_(m.bias, 0)

    def forward(self, x: Tensor) -> Tensor:
        # 初始卷积
        x = self.conv1(x)
        x = self.bn1(x)
        x = self.relu(x)
        x = self.maxpool(x)

        # 残差层
        x = self.layer1(x)
        x = self.layer2(x)
        x = self.layer3(x)
        x = self.layer4(x)

        # 分类器
        x = self.avgpool(x)
        x = torch.flatten(x, 1)
        x = self.fc(x)

        return x
```
