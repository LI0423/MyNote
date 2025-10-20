# LSTM（Long Short-Term Memory）

长短期记忆网络，一种时间循环神经网络。为了解决一般的RNN（循环神经网络）存在的长期依赖问题。

## 核心架构

### 三个关键门控

1. 输入门（Input Gate，$i_t$）：决定输入哪些信息。
2. 遗忘门（Forget Gate，$g_t$）：决定丢弃哪些信息。
3. 输出门（Output Gate，$o_t$）：决定输出哪些信息。

### 细胞状态（Cell State）

LSTM的记忆通道，信息可以几乎不变地流过整个序列。

## 数学公式

### 输入门

决定要更新哪些信息
$$i_t = \sigma(W_{xi}x_t + W_{hi}h_{t-1} + b_i)$$

### 候选值

候选细胞状态提供新的可能值
$$g_t = tanh(W_{xg}x_t + W_{hg} h_{t-1} + b_g)$$

### 遗忘门

决定从细胞状态中丢弃哪些信息
$$f_t = \sigma(W_{xf}x_t + W_{hf}h_{t-1} + b_f)$$

#### 物理意义

- $f_t=0$：完全忘记
- $f_t=1$：完全保留

### 更新细胞状态

结合遗忘门和输入门来更新细胞状态
$$c_t = f_t ⊙ c_{t-1} + i_t ⊙ g_t$$

- ⊙：逐元素相乘
- 第一部分：遗忘门控制保留多少旧记忆
- 第二部分：输入门控制添加多少新信息

### 输出门

决定基于当前细胞状态输出什么
$$o_t = \sigma(W_{xo}x_t + W_{ho}h_{t-1} + b_o)$$

### 输出结果

$$h_t = o_t * tanh(c_t)$$

## 如何解决梯度消失

LSTM引入了细胞状态（Cell State），信息可以几乎不变地流过整个序列。

LSTM的细胞状态更新公式如上，在反向传播时，细胞状态的梯度为：
$$\frac{\delta C_t}{\delta C_{t-1}}=f_t + (其他项)$$
如果遗忘门$f_t\approx1$，那么$\frac{\delta C_t}{\delta C_{t-1}}\approx1$，梯度可以无损耗地流过很长的距离。解决了梯度消失问题。

## 代码实现

### 基础LSTM

```python
import torch 
import torch.nn as nn
import torch.optim as optim
import numpy as np


class BasicLSTM(nn.Module):
    def __init__(self, input_size, hidden_size, num_layers, output_size, dropout=0.2):
        super(BasicLSTM, self).__init__()
        self.hidden_size = hidden_size
        self.num_layers = num_laysers
        # LSTM层
        self.lstm = nn.LSTM(
            input_size=input_size,
            hidden_size=hidden_size,
            num_layers=num_layers,
            batch_first=True,
            dropout=dropout if num_layers > 1 else 0
        )
        self.fc = nn.Linear(hidden_size, output_size)
        self.dropout = nn.Dropout(dropout)

    def forward(self, x):
        batch_size = x.size(0)
        # 初始化隐藏状态和细胞状态
        h0 = torch.zeros(self.num_layers, batch_size, self.hidden_size).to(x.device)
        c0 = torch.zeros(self.num_layers, batch_size, self.hidden_size).to(x.device)
        # LSTM前向传播
        # lstm_out形状: (batch_size, seq_len, hidden_size)
        # hidden: 元组(h_n, c_n)
        lstm_out, (h_n, c_n) = self.lstm(x, (h0, c0))
        # 取最后一个时间步的输出
        last_output = lstm_out[:, -1, :]
        output = self.fc(self.dropout(last_output))
        return output

input_size = 10
hidden_size = 64
num_layers = 2
output_size = 1

model = BasicLSTM(input_size, hidden_size, num_layers, output_size)
print(model)
```

### LSTM单元

```python
class ManualCell(nn.Module):
    def __init__(self, input_size, hidden_size):
        super(ManualCell, self).__init__()
        self.input_size = input_size
        self.hidden_size = hidden_size
        # 输入权重
        self.weight_ih = nn.Parameter(torch.randn(4 * hidden_size, input_size))
        self.weight_hh = nn.Parameter(torch.randn(4 * hidden_size, input_size))
        self.bias = nn.Parameter(torch.randn(4 * hidden_size))

    def forward(self, x, hidden):
        h_prev, c_prev = hidden
        # 计算所有门和候选值
        gates = (torch.mm(x, self.weight_ih.t()) + torch.mm(h_prev, self.weight_hh.t()) + self.bias)
        # 分割成输入门、遗忘门、输出门、候选值
        i_gate, f_gate, g_gate, o_gate = gates.chunk(4, 1)
        # 应用激活函数
        i_gate = torch.sigmoid(i_gate) # 输入门
        f_gate = torch.sigmoid(f_gate) # 遗忘门
        g_gate = torch.tanh(g_gate) # 候选值
        o_gate = torch.sigmoid(o_gate) # 输出门
        # 更新细胞状态
        c_t = f_gate * c_prev + i_gate * g_gate
        # 更新隐藏状态
        h_t = o_gate * torch.tanh(c_t)
        return h_t, c_t
```

## 应用场景

### 自然语言处理（NLP）

### 语音识别和语音处理

### 时间序列预测

### 医疗健康

### 推荐系统

### 自动驾驶和机器人
