# 指数加权移动平均 (EWMA)

## 1. 定义

指数加权移动平均（EWMA）是对时间序列数据进行平滑处理的一种技术，采用指数衰减的权重分配，使得最近的数据对均值贡献更大。其标准形式为：

$$
\mathrm{EWMA}_t = \sum_{i=0}^{t-1} w_i \, x_{t-i},
\quad w_i = \alpha(1-\alpha)^i,
$$

其中：

- $x_{t}$：时刻 $t$ 的观测值。
- $w_i$：对 $x_{t-i}$ 的权重。
- $\alpha\in(0,1)$：平滑因子（Smoothing Factor），控制衰减速率。

满足权重归一化：

$$
\sum_{i=0}^{\infty} w_i = \sum_{i=0}^{\infty} \alpha(1-\alpha)^i = 1.
$$

---

## 2. 递推形式

同样可以使用递推公式高效计算：

$$
\mathrm{EWMA}_t = \alpha x_t + (1 - \alpha)\mathrm{EWMA}_{t-1},
$$

- 初始值通常设为 $\mathrm{EWMA}_1 = x_1$ 或者使用前 $N$ 点的简单平均。

---

## 3. 数学推导与权重特性

### 3.1 递推展开

不断展开递推式：

$$
\mathrm{EWMA}_t = \alpha x_t + \alpha(1-\alpha)x_{t-1} + \alpha(1-\alpha)^2 x_{t-2} + \dots.
$$

因此，历史观测值 $x_{t-i}$ 的权重为：

$$
w_i = \alpha(1-\alpha)^i.
$$

### 3.2 衰减速度

- **权重衰减率**：第 $i$ 次滞后数据的权重衰减比例为 $(1-\alpha)^i$。
- **半衰期**：权重衰减至一半时对应的滞后期数
  $$
  T_{1/2} = \frac{\ln(0.5)}{\ln(1-\alpha)}.
  $$

### 3.3 等效窗口长度

尽管 EWMA 涉及无限历史，但其“等效窗口”可通过：

$$
N_{\mathrm{eff}} \approx \frac{2}{\alpha} - 1.
$$

来近似表示对历史观测的累计影响长度。

---

## 4. 参数 $\alpha$ 的选取

- $\alpha$ 越大：赋予最新数据更高权重，响应更灵敏；
- $\alpha$ 越小：平滑效果更强，但对新变化响应较慢。

常见经验：设定期望等效窗口长度 $N$，取

$$
\alpha = \frac{2}{N + 1}.
$$

---

## 5. 与其他移动平均对比

| 特性         | 简单移动平均 (SMA)           | 加权移动平均 (WMA)          | 指数加权移动平均 (EWMA)    |
|-------------|----------------------------|---------------------------|--------------------------|
| 权重分配     | 均匀                       | 线性递减                    | 指数递减                   |
| 计算复杂度    | $O(N)$                    | $O(N)$                    | $O(1)$                    |
| 对新数据响应  | 最慢                       | 中等                       | 最快                       |
| 平滑效果     | 最低                       | 中等                       | 最高                       |

---

## 6. 实际应用

1. **金融分析**：
   - 风险管理：用于估计资产收益波动率（EWMA 波动率模型）。
   - 技术指标：与 EMA 同用于价格趋势跟踪。
2. **信号处理**：
   - 实时滤波：平滑传感器或指标信号。
3. **质量控制**：
   - 控制图：EWMA 控制图及时监测过程漂移。

---

## 7. Python 示例代码

```python
class ExponentialWeightedMovingAverage:
    """
    带时间衰减的指数加权平均
    - alpha: 基础衰减系数
    - time_scale: 时间衰减系数
    """
    def __init__(self, alpha=0.5, time_scale=0.1):
        self.alpha = alpha
        self.time_scale = time_scale
        self.ewma = None
        self.last_time = None
        
    def update(self, value, timestamp=None):
        if self.ewma is None:
            self.ewma = value
            self.last_time = timestamp if timestamp else 0
            return self.ewma
            
        # 计算时间差
        current_time = timestamp if timestamp else self.last_time + 1
        delta_t = current_time - self.last_time
        self.last_time = current_time
        
        # 动态调整衰减系数
        dynamic_alpha = self.alpha * np.exp(-self.time_scale * delta_t)
        
        self.ewma = dynamic_alpha * value + (1 - dynamic_alpha) * self.ewma
        return self.ewma


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
    ewma = ExponentialWeightedMovingAverage(alpha=0.5, time_scale=0.2)
    timestamp = 0
    for price, _ in stock_data:
        timestamp += 1
        print(f"t={timestamp} Price: {price:.1f} → EWMA: {ewma.update(price, timestamp):.2f}")
```
