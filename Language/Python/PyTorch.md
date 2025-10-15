# PyTorch

## 基础

### 什么是PyTorch

开源机器学习库，用于开发和训练基于神经网络的深度学习模型，常用于图像识别和自然语言处理等应用的机器学习库。对GPU有出色支持以及使用反向传播自动微分，使计算图可以即时修改。

### PyTorch张量是什么

张量是PyTorch的核心数据类型，类似于多维数组，用于存储和操作模型的输入和输出以及模型的参数。类似于NumPy的张量ndarrays，不同之处是张量可以在GPU上运行以加速计算。

### 常用组件有哪些

1. 张量：与Numpy的数组非常相似，而且也是多维的。张量可以作为tensor类型来访问torch模块。一些示例包括torch.CharTensor、torch.IntTensor、torch.FloatTensor等。用于在深度学习中存储数据和进行各种数学运算。
2. 变量：充当Tensor的包装器，用于抓取梯度。在最新版本中，张量本身已经具有自动求导的功能，直接使用张量即可，不需要显式的使用torch.autograd.Variable。
3. 参数：参数的作用是包装变量，当模块的张量不具有梯度时，会使用它。（torch.nn.Parameter）。通常用于存储神经网络中的可学习权重和偏置等。
4. 函数：不具有任何内存，工作是执行特定的转换操作。示例：torch.sum、torch.log等。通常使用torch.nn.functional模块来实现。提供了一系列用于构建神经网络的函数，这些函数可以直接对张量进行操作。
5. 模块：所有神经网络的基类，可以包含不同的函数、模块和参数。可以有效存储可学习的权重和状态。示例：torch.nn.Linear、torch.nn.Conv2d。提供了一种结构化的方式来构建神经网络，使得网络的定义和管理更加方便。模块可以包含多个子模块，并且可以通过集成torch.nn.Moudle类来定义自定义的模块。

### 常见的PyTorch模块

1. Autograd：autograd模块是PyTorch的自动微分模块，有助于快速计算前向传播中的梯度。Autograd生成有向无环图，叶子是输入张量，根是输出张量。能够自动追踪和计算张量在计算过程中的梯度，为反向传播算法提供了强大的支持。通过autograd可以轻松构建复杂的神经网络模型，并进行高效的梯度计算和参数更新。
2. Optim：optim模块是一个包含预先编写的优化器算法的包，可用于构建神经网络。常见的优化器如随机梯度下降（SGD）、Adam、Adagrad等都可以在这个模块中找到。优化器的作用是根据计算得到的梯度来更新模型的参数，以最小化损失函数。
3. nn：nn模块包含各种有助于构建神经网络模型的类。PyTorch中的所有模块都属于nn模块的子类。这个模块提供了一系列的层（如全连接层、卷积层、循环层等）、激活函数（如ReL、Sigmoid、Tanh）、损失函数（如交叉熵损失、均方误差损失等）以及模型容器（如Sequential、MoudleList等）。

### 使用PyTorch最常见的错误

1. 形状错误：当尝试对形状不一致的矩阵或张量进行操作时，就会发生形状错误。例如，数据形状为[1,28,28]，但模型第一层的输入要求是[10]。解决此问题的一种方法是根据具体情况对张量进行重塑或转制，使其形状与所需操作相匹配。
2. 设备错误：当模型和数据位于不同的设备上时，会发生设备错误。比如，将模型发送到目标GPU设备，但数据仍在CPU上。解决这个问题的方法是使用'.to(device)'方法将模型或数据发送到正确的目标设备上。
3. 数据类型错误：当数据是一种数据类型（如torch.float32），而尝试执行的操作需要另一种数据类型（torch.int64）时，就会发生数据类型错误。为了解决这个问题，可以使用torch.Tensor.type(dtype=None)方法来正确调整张量的数据类型，其中dtype参数是所需的数据类型。

### 有哪些方法可以重塑张量维度

1. torch.reshape(input, shape)：重塑input为shape（如果兼容）。
2. torch.Tensor.view(shape)：返回原始张量的视图，其形状不同，但与原始张量共享相同数据。
3. torch.permute(input, dims)：返回原始输入的视图，其尺寸已重新排列dims。

### 有哪些做法可以提高可重复性

1. 通过设置随机数生成器（RNG）来控制随机源：可以在应用程序开始时，使用torch.manual_seed()来为所有设备（CPU和CUDA）设置RNG。每次在相同环境中运行应用程序时都可以生成相同的一系列随机数。
2. 避免对某些操作使用非确定性算法：可以将PyTorch配置为在可用的torch.use_deterministic_algorithms()情况下使用确定性算法而不是非确定性算法，并且如果已知操作是非确定性的（并且没有确定性的替代方法），则抛出错误。

### 如何定义神经网络模型

```Python
import torch
import torch.nn as nn

class SimpleNN(nn.Moudle):
    def __init__(self):
        super(SimpleNN, self).__init__()
        self.fc1 = nn.Linear(10, 20)
        self.relu = nn.ReLU()
        self.fc2 = nn.Linear(20, 2)

    def forward(self, x):
        x = self.fc1(x)
        x = self.relu(x)
        x = self.fc2(x)
        return x

model = SimpleNN()
input_data = torch.randn(32, 10)
output = model(input_data)
print(output.shape)
```

### forward()和backward()有什么区别

1. forward() 函数用于计算当前神经网络模型的前向传播，定义了模型输入到输出的运行方式。
2. backward() 番薯计算当前张量相对于某个标量值的梯度。在神经网络模型中，backward()计算模型参数相对于给定损失函数的梯度。

### 如何使用PyTorch获得函数的导数

1. 初始化函数：明确要计算导数的函数。例如假设我们有一个简单的线性函数y=4*x+3
2. 设置变量可求导属性：可以通过将变量包装成torch.autograd.Variable并设置requires_grad=True来实现。PyTorch会跟踪该变量在计算过程中的梯度信息。
3. 计算函数的导数：使用backward()方法来计算函数的导数，这个方法会自动根据计算图进行反向传播，计算出各个变量的梯度。
4. 获取导数的值：通过变量的grad属性可以获取该变量的导数值。

## API

### torch.einsum

用于执行高效张量运算的函数，基于爱因斯坦求和约定（Einstein summation convention）。能够处理复杂的张量操作，并简化代码书写。

torch.einsum(subscriptions, *operands)

- subscriptions：一个字符串，用于输入张量的维度如何结合
- *operands：待操作的张量

```python
# "i,i->" 表示对向量进行点积操作
# i：索引表示
# -> 之后为空表示求和
torch.einsum('i,i->', a, b)

# "ij,jk->ik" 表示矩阵乘法
# i和k是结果的维度，j是求和维度
torch.einsum('ij,jk->ik', a, b)

# "bij,bjk->bik" 表示批量矩阵乘法
torch.einsum("bij,bjk->bik", a, b)

# "nqhd,nkhd->nhqk" 描述了如何对这两个张量进行操作
# n：批次大小（batch size）
# q：查询序列大小（query length）
# k：键序列长度（key length）
# h：注意力头的数量（number of heads）
# d：每个注意力头的维度（dimension per head）
torch.einsum("nqhd,nkhd->nhqk", [queries, keys]) # 计算点积相似性分数

# "ij->ji" 表示将矩阵进行转置操作
torch.einsum('ij->ji', a)
```

### torch.bmm

torch.bmm(input1, input2, out=None)

- input1：输入的第一个张量，形状必须为(b, n, m)，b是批次大小，n和m是张量的行和列。
- input2：输入的第二个张量，形状必须为(b, m, p)，第一个矩阵的列数必须和第二个矩阵的行数匹配。
- out：输出张量，用于存储计算结果，形状为(b, n, p)。

```python
torch.bmm(m1, m2.transpose(1, 2))
```
