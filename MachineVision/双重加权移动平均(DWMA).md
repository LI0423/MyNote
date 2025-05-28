# 双重加权移动平均 (Double Weighted Moving Average)

## 1. 定义

双重加权移动平均 (DWMA) 是对原始序列进行两轮加权移动平均处理，以进一步提高平滑效果并减少延迟。

设原始序列为 $x_{t}$，权重向量为 $\{w_i\}_{i=0}^{N-1}$，满足：

$$
\sum_{i=0}^{N-1} w_i = 1, \quad w_i \ge 0.
$$

**计算流程**：

1. **第一次加权移动平均 (WMA\,1)**：
   $$
   W^{(1)}_t = \sum_{i=0}^{N-1} w_i\,x_{t-i}.
   $$
2. **第二次加权移动平均 (DWMA)**：
   $$
   W^{(2)}_t = \sum_{i=0}^{N-1} w_i\,W^{(1)}_{t-i}
   = \sum_{i=0}^{N-1}\sum_{j=0}^{N-1} w_i w_j \,x_{t-i-j}.
   $$

通常第二次使用相同权重向量，也可采用不同权重配置。

---

## 2. 权重性质与等效窗口

### 2.1 双重权重展开

将第二次加权展开得：

$$
W^{(2)}_t = \sum_{k=0}^{2N-2} \Bigl(\sum_{i=0}^k w_i w_{k-i}\Bigr) x_{t-k}.
$$

其中对 $x_{t-k}$ 的复合权重为：

$$
W_k = \sum_{i=0}^k w_i w_{k-i},\quad k = 0,1,\dots,2N-2.
$$

### 2.2 等效窗口长度

- 原始 WMA 窗口长度 $N$。
- 双重后等效窗口长度变为 $2N - 1$，但有效权重主要集中在前 $\approx N$ 长度内。

### 2.3 滞后与平滑

- 二次平滑降低高频噪声，但会略微增加时序滞后。
- 相对于单次 WMA，可显著减少波动。

---

## 3. 性能对比

| 指标             | 单次 WMA            | 双重 WMA (DWMA)         |
|------------------|---------------------|-------------------------|
| 平滑程度         | 中等                 | 更强                    |
| 计算复杂度       | $O(N)$               | $O(N)$                 |
| 有效延迟         | 平均延迟 ~$(N-1)/2$ | 平均延迟 ~$N-1$       |
| 有限响应特性     | FIR 滤波             | FIR 滤波                |

---

## 4. 边界处理

- 序列前端缺少完整窗口时，可采用：
  - **零填充**：用 0 填充 $x_t$ 或中间结果。
  - **前向填充**：重复使用首值 $x_1$。
  - **缩短窗口**：仅在数据足够时计算 DWMA。

---

## 5. 实际应用

1. **金融数据平滑**：
   - 对价格或成交量序列进行双重平滑，减少假信号。
2. **信号处理**：
   - FIR 滤波器设计：二次加权形成更陡峭的通带/阻带特性。
3. **工业控制**：
   - 对传感器读数进行双重平均，提高测量稳定性。

---

## 6. Python 示例代码

```python
class DoubleWeightedMovingAverage:
    """
    双重加权移动平均
    - time_weights: 时间维度权重
    - feature_weights: 特征维度权重
    """
    def __init__(self, window_size=5, 
                 time_weights=None, 
                 feature_weights=None):
        self.window = deque(maxlen=window_size)
        self.window_size = window_size
        
        # 时间维度权重（默认线性衰减）
        self.time_weights = time_weights or list(np.linspace(1, 0.2, window_size))
        
        # 特征维度权重（需要与输入特征维度一致）
        self.feature_weights = feature_weights  # 例如: [0.4, 0.6] 对应二维特征
        
    def update(self, features):
        """
        输入features为多维数组
        示例: [[price, volume], ...]
        """
        if self.feature_weights and len(features) != len(self.feature_weights):
            raise ValueError("特征维度与权重不匹配")
            
        self.window.append(features)
        if len(self.window) < self.window_size:
            return None
            
        # 双重加权计算
        total = 0
        for t_weight, data_point in zip(self.time_weights, self.window):
            if self.feature_weights:
                # 特征维度加权求和
                f_weighted = sum(fw * f for fw, f in zip(self.feature_weights, data_point))
            else:
                f_weighted = sum(data_point)
                
            total += t_weight * f_weighted
            
        # 归一化
        return total / (sum(self.time_weights) * (len(features) if not self.feature_weights else 1))


if __name__ == "__main__":
    # 测试数据：10天的收盘价和成交量
    stock_data = [
        [50.2, 1_000_000],
        [51.5, 1_200_000],
        [52.3, 900_000],
        [53.6, 1_500_000],
        [54.1, 800_000],
        [55.7, 1_100_000],
        [56.2, 1_300_000],
        [57.4, 950_000],
        [58.0, 1_400_000],
        [59.1, 1_600_000]
    ]
    dwma = DoubleWeightedMovingAverage(
        window_size=5,
        time_weights=[0.5, 0.3, 0.15, 0.04, 0.01],  # 时间衰减权重
        feature_weights=[0.7, 0.3]  # 价格权重70%，成交量30%
    )
    for features in stock_data:
        result = dwma.update(features)
        if result is not None:
            print(f"DWMA: {result:.2f}")
```
