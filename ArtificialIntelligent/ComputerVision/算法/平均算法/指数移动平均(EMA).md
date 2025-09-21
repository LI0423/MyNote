# 指数移动平均（EMA）

## 1. 定义

指数移动平均（EMA）是对时间序列数据进行平滑处理的一种手段，它通过对历史数据按指数方式加权，给与较新的数据更高的权重。EMA 的递推公式为：

$$
\mathrm{EMA}_t = \alpha\,x_t + (1 - \alpha)\,\mathrm{EMA}_{t-1},
$$

其中：

- $x_t$：时刻 $t$ 的观测值。
- $\mathrm{EMA}_t$：时刻 $t$ 的 EMA 值。
- $\alpha\in(0,1)$：平滑因子（Smoothing Factor），决定了新旧数据权重分配。

**常用平滑因子选择**：

当期望等效窗口长度为 $N$ 时，通常取：

$$
\alpha = \frac{2}{N + 1}.
$$

---

## 2. 数学推导

### 2.1 递推展开

将递推式不断展开：

$$
\begin{aligned}
\mathrm{EMA}_t &= \alpha x_t + (1 - \alpha)\mathrm{EMA}_{t-1} \\
&= \alpha x_t + \alpha(1-\alpha)x_{t-1} + (1-\alpha)^2 \mathrm{EMA}_{t-2} \\
&= \alpha x_t + \alpha(1-\alpha)x_{t-1} + \alpha(1-\alpha)^2 x_{t-2} + \dots.
\end{aligned}
$$

因此，$x_{t-i}$ 的权重为：

$
w_i = \alpha(1 - \alpha)^i,\quad i = 0,1,2,\dots.
$

所有权重之和为：

$$
\sum_{i=0}^{\infty} w_i = \sum_{i=0}^{\infty} \alpha(1-\alpha)^i = 1
$$

### 2.2 初始条件

由于递推公式需要 $\mathrm{EMA}_{t-1}$，必须设定初始值：

1. **直接法**：$\mathrm{EMA}_1 = x_1$。
2. **简单平均初始化**：用前 $N$ 点的 SMA 作为 $\mathrm{EMA}_N$ 的初始值，以减少前期波动影响：

   $$
   \mathrm{EMA}_N = \frac{1}{N}\sum_{i=1}^N x_i.
   $$

---

## 3. 参数与性质

### 3.1 平滑因子 $\alpha$ 的效果

- $\alpha$ 越大：EMA 对最新数据的敏感度越高，响应速度越快，但平滑效果较弱。
- $\alpha$ 越小：EMA 平滑效果越强，但对新变化的响应较慢。

### 3.2 等效记忆长度

EMA 对历史数据的权重以指数方式衰减，等效“窗口”长度可近似估算为：

$$
N_{\mathrm{eff}} \approx \frac{2}{\alpha} - 1.
$$

### 3.3 半衰期 (Half-life)

权重衰减到一半所需的时间步数：

$$
T_{1/2} = \frac{\ln(0.5)}{\ln(1 - \alpha)}.
$$

---

## 4. 与简单移动平均 (SMA) 对比

| 特性        | SMA                         | EMA                         |
|------------|-----------------------------|-----------------------------|
| 计算复杂度   | $O(N)$                      | $O(1)$                      |
| 权重分布     | 过去 $N$ 点均等加权           | 指数衰减，更重视最近数据        |
| 响应速度     | 滞后明显                     | 响应更快                     |
| 平滑程度     | 突变时产生明显边界             | 平滑过渡，对波动更柔和         |

---

## 5. 实际应用示例

1. **金融市场分析**：
   - 趋势判断：利用 EMA 判断价格上升或下降趋势。
   - 技术指标：MACD 指标中使用短期和长期 EMA 的差值。  
2. **信号处理**：
   - 噪声滤波：对时间序列信号进行平滑，去除高频噪声。
3. **质量控制**：
   - 控制图：EWMA 控制图用于监测生产过程中的慢变偏移。

---

## 6. 代码示例 (Python)

```python
class ExponentialMovingAverage:
    """
    指数移动平均实现
    - alpha: 平滑因子 (0 < alpha <= 1)
    - adjust: 是否进行初始阶段偏差校正
    """
    def __init__(self, alpha=0.3, adjust=True):
        self.alpha = alpha
        self.adjust = adjust
        self.ema = None
        self.count = 0  # 用于偏差校正的计数器
        
    def update(self, value):
        if self.ema is None:
            self.ema = value
        else:
            # 核心EMA公式
            self.ema = self.alpha * value + (1 - self.alpha) * self.ema
        
        self.count += 1
        
        # 偏差校正（前N个周期）
        if self.adjust and self.count < 20:
            corrected_ema = self.ema / (1 - (1 - self.alpha)**self.count)
            return corrected_ema
            
        return self.ema

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

    ema = ExponentialMovingAverage(alpha=0.3)
    for price, _ in stock_data:
        print(f"Price: {price:.1f} → EMA: {ema.update(price):.2f}")
```
