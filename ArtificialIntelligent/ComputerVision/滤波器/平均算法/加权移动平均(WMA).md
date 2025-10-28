# 加权移动平均 (WMA)

## 1. 定义

加权移动平均（Weighted Moving Average, WMA）对时间序列最近的观测赋予不同权重，通常权重随观测距当前的时间点增加而线性或自定义递减。
其核心思想是：
赋予窗口内不同时间点的观测值不同权重，通过加权求和的方式计算平均值。

其标准形式为：

$$
\mathrm{WMA}_t = \sum_{i=0}^{N-1} w_i \, x_{t-i},
\quad \sum_{i=0}^{N-1} w_i = 1,
$$

其中：

- $N$：窗口长度。
- $x_{t-i}$：滞后 $i$ 步的观测值。
- $w_i$：权重系数，满足 $w_i \ge 0$ 且归一化。

---

## 2. 权重设计

### 2.1 线性权重

最常见的设计为线性递减权重：

$$
w_i = \frac{N - i}{\sum_{j=1}^N j} = \frac{N - i}{N(N+1)/2},
\quad i = 0,1,\dots,N-1.
$$

- 当 $i = 0$（最新观测）时，权重最大 $w_0 = \tfrac{N}{\frac{N(N+1)}{2}}$。
- 当 $i = N-1$（最老观测）时，权重最小 $w_{N-1} = \tfrac{1}{\frac{N(N+1)}{2}}$。

### 2.2 自定义权重

根据实际需求，可采用非线性函数（如指数、正态分布等）设计权重：

$$
w_i \propto f(i),
\quad w_i = \frac{f(i)}{\sum_{j=0}^{N-1} f(j)}.
$$

常见示例：指数权重 $f(i) = (1-\alpha)^i$。此时 WMA 退化为 EWMA 的有限窗口版本。

---

## 3. 数学性质

1. **归一化**：权重之和为 1，保证 WMA 为原序列的平滑。
2. **线性滤波器**：WMA 是有限脉冲响应 (FIR) 滤波器。
3. **边界效应**：前 $N-1$ 个点需要特殊处理，常用零填充或缩短窗口。
4. **响应速度**：比简单移动平均 (SMA) 响应更快，但取决于权重分布。

---

## 4. 归一化

核心作用是确保计算结果具有可比性和合理性。

### 4.1 作用

1. 消除权重尺度影响

   当权重系数（如指数权重）的总和不为1时，加权和会随权重尺度变化。归一化通过除以总权重，使最终结果与权重绝对值无关，只与权重相对比例有关。

2. 保持结果在合理范围

    未归一化：加权和数值会过大。

    归一化后：结果始终在数据值的合理范围内。

    ```Python
    weight = 0.5
    vels = [30, 20, 15, 20]
    avg_vels = avg(30, 20, 15, 20)
             = 21.25 
    weights = [0.5^(4-1-0), 0.5^(4-1-1), 0.5^(4-1-2), 0.5^(4-1-3)]
            = [0.5^3, 0.5^2, 0.5^1, 0.5^0]
            = [0.125, 0.25, 0.5, 1]
    total_weight = 0.125 + 0.25 + 0.5 + 1
                 = 1.875
    pre_res = sum(30 * 0.125, 20 * 0.25, 15 * 0.5, 20 * 1)
        = sum(3.75, 5, 7.5, 20)
        = 36.25
    after_res = 36.25 / 1.875
              = 19.6
    ```

3. 数学等价性保证

    使加权平均值与算术平均值公式统一。

    ```Python
    weight = 1
    vels = [30, 20, 15, 20]
    avg_vels = avg(30, 20, 15, 20)
             = 21.25 
    weights = [1^(4-1-0), 1^(4-1-1), 1^(4-1-2), 1^(4-1-3)]
            = [1^3, 1^2, 1^1, 1^0]
            = [1, 1, 1, 1]
    total_weight = 1 + 1 + 1 + 1
                 = 4
    pre_res = sum(30 * 1, 20 * 1, 15 * 1, 20 * 1)
        = sum(30, 20, 15, 20)
        = 85
    after_res = 85 / 4
              = 21.25
    ```

### 4.2 实际应用意义

1. 避免阈值漂移

   防止权重累计导致阈值无限增大/减小。

2. 保持动态稳定性

    新值的影响权重 = 新值权重 / 总权重

## 5. Python 示例代码

1. **线性加权移动平均**

    ```python
    class LinearWeightedMovingAverage:
        """
        线性加权移动平均
        - window_size: 观察窗口大小
        - mode: 权重模式 ('linear'或'custom')
        - custom_weights: 自定义权重数组
        """
        def __init__(self, window_size=5, mode='linear', custom_weights=None):
            self.window = deque(maxlen=window_size)
            self.window_size = window_size
            
            if mode == 'linear':
                # 生成线性递减权重 [n, n-1, ..., 1]
                self.weights = list(range(1, window_size+1))[::-1]
            elif mode == 'custom' and custom_weights:
                if len(custom_weights) != window_size:
                    raise ValueError("自定义权重长度需与窗口大小一致")
                self.weights = custom_weights
            else:
                raise ValueError("不支持的权重模式")
                
            # 归一化权重
            self.weights = [w/sum(self.weights) for w in self.weights]
            
        def update(self, value):
            self.window.append(value)
            if len(self.window) < self.window_size:
                return None  # 窗口未填满时返回空
                
            # 计算加权平均
            weighted_sum = sum(w * x for w, x in zip(self.weights, self.window))
            return weighted_sum

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
        wma = LinearWeightedMovingAverage(window_size=5)
        for price, _ in stock_data:
            result = wma.update(price)
            if result is not None:
                print(f"WMA(5): {result:.2f}")
    ```

2. **通用加权移动平均**

    ```python
    def weighted_moving_average(data, weights):
        window = len(weights)
        total_weight = sum(weights)
        
        wma = []
        for i in range(len(data)-window+1):
            window_data = data[i:i+window]
            weighted_sum = sum(w * d for w, d in zip(weights, window_data))
            wma.append(weighted_sum / total_weight)
        return wma

    # 自定义权重示例
    custom_weights = [0.5, 0.3, 0.2]  # 最新数据权重最高
    print(weighted_moving_average(prices, custom_weights))
    ```
