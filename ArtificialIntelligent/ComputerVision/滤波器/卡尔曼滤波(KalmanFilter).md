# 卡尔曼滤波

## 原理

    一种利用线性系统状态方程，通过系统输入输出观测数据，对系统状态进行最优估计的算法。由于观测数据中包括系统中的噪声和干扰的影响，所以最优估计也可以看作是滤波过程。

### 1.解释

    就是一种聪明的“预测+修正”的办法，用来从带噪声的数据里，尽量估计出真实值。它相信：“对未来的预测 + 现在的观测值，综合一下，能得到更靠谱的估计。”

### 2.处理步骤

#### 1. **预测**

    根据上一个时刻的值，预测当前时刻的值和误差大小。
    比如：
    我预测这帧跳绳 y 坐标在 200 像素
    我预测这帧误差是 ±10 像素

#### 2. **更新**

    拿到当前实际观测值后，根据它和预测值的差距，调整预测值。
    如果观测值离预测很近，说明预测靠谱 → 小改动
    如果观测值离预测很远，说明观测可能有噪声 → 轻信预测，多修正

## 数学公式详解

### 1. **系统模型**

#### 1.1 状态方程（预测模型）

$$
x_t = F_t x_{t-1} + B_t u_t + w_t \quad (w_t \sim \mathcal{N}(0, Q_t))-----（1）
$$

#### 1.2 观测方程（测量模型）

$$
z_t = H_t x_t + v_t \quad (v_t \sim \mathcal{N}(0, R_t))-----（2）
$$

为什么要引入 $w_t$ 和 $v_t$？

对于状态方程（1）来说，大多数实际的控制系统并不是一个严格的线性时变系统，或者系统结构参数的不确定，导致估计的状态值$x_t$存在偏差，而这个偏差值由
过程噪声$w_t$来表征。对于观测方程（2）来说，采集的信号往往是包含噪声及干扰信号的，亦或观测矩阵$H_t$自身的观测偏差，从而导致了测得的实际信号（观测值）
与真实值之间存在一定的偏差，这个量由$v_t$来表征。

---

### 2. **预测阶段（Predict）**

#### 2.1 状态预测

$$
\hat{x}_{t|t-1} = F_t \hat{x}_{t-1|t-1} + B_t u_t
$$

#### 2.2 协方差预测

$$
P_{t|t-1} = F_t P_{t-1|t-1} F_t^T + Q_t
$$

---

### 3. **更新阶段（Update）**

#### 3.1 卡尔曼增益计算

$$
K_t = P_{t|t-1} H_t^T \left( H_t P_{t|t-1} H_t^T + R_t \right)^{-1}
$$

#### 3.2 状态更新

$$
\hat{x}_{t|t} = \hat{x}_{t|t-1} + K_t \left( z_t - H_t \hat{x}_{t|t-1} \right)
$$

#### 3.3 协方差更新

$$
P_{t|t} = (I - K_t H_t) P_{t|t-1}
$$

---

### 4. **符号说明**

| 符号               | 含义                                                        |
|-------------------|-------------------------------------------------------------|
|   $x_t$           | 系统在时刻 $t$ 的状态向量（如位置、速度）                         |
|   $z_t$           | 时刻 $t$ 的观测值                                             |
|   $F_t$           | 状态转移矩阵（描述状态如何随时间变化）                             |
|   $B_t$           | 控制输入矩阵（将控制输入 $u_t$ 映射到状态空间）                    |
|   $u_t$           | 控制输入向量（如加速度指令）                                     |
|   $H_t$           | 观测矩阵（将状态映射到观测空间）                                  |
|   $Q_t$           | 过程噪声协方差矩阵（表示预测模型本身的噪声）                        |
|   $R_t$           | 观测噪声协方差矩阵（表示测量值与真实值之间的差值）                   |
|   $P_{t\|t}$      | 后验估计协方差矩阵（表示系统的不确定程度，在卡尔曼滤波初始化时会很大，随着越来越多的数据注入滤波器中，不确定程度会变小）|
|   $K_t$           | 卡尔曼增益（平衡模型预测与观测值的权重）                           |
|   $I$             | 单位矩阵                                                      |
|   $w_t$           | 过程噪声，协方差为$Q$                                          |
|   $v_t$           | 测量噪声，且为高斯白噪声，协方差为$R$                             |

---

### 5. **匀速模型特例**

#### 5.1 状态向量定义

$$
x = \begin{bmatrix} \text{position} \\ \text{velocity} \end{bmatrix}
$$

#### 5.2 状态转移矩阵

$$
F = \begin{bmatrix}
1 & \Delta t \\
0 & 1
\end{bmatrix}
$$

##### 物理意义

1. 位置更新：新位置 = 原位置 + 速度 x 时间间隔

   对应矩阵元素：$F[0,0] = 1, F[0,1] = \Delta t$

2. 速度更新：假设匀速运动，速度保持不变

   对应矩阵元素：$F[1,0] = 0, F[1,1] = 1$

##### 矩阵相乘过程

$$
\begin{bmatrix}
x_{new} \\
v_{new}
\end{bmatrix}
=
\begin{bmatrix}
1 & \Delta t \\
0 & 1
\end{bmatrix}
\times
\begin{bmatrix}
    x \\
    v
\end{bmatrix}
=
\begin{bmatrix}
    1 · x + \Delta t · v \\
    0 · x + 1 · v
\end{bmatrix}
$$

- 新位置：$x_{new} = x + v · \Delta t$
- 新速度：$v_{new} = v （假设匀速）$

#### 5.3 过程噪声协方差（随机加速度假设）

$$
Q = \sigma_a^2 \begin{bmatrix}
\frac{\Delta t^4}{4} & \frac{\Delta t^3}{2} \\
\frac{\Delta t^3}{2} & \Delta t^2
\end{bmatrix}
$$

##### 物理基础

来自匀速运动模型中的随机加速度。

## 状态向量如何定义

### 核心原则

1. 包含关键状态变量：选择最能表征系统当前状况且会随时间演化的物理量。
   - 运动系统：位置、速度、加速度、角度、角速度等。
   - 传感器融合：多个传感器的测量值或其偏差。
   - 参数估计：系统本身的未知参数。

2. 考虑系统动态：状态向量必须包含足够的信息，使得仅根据当前状态和系统输入（控制量），就能通过系统模型（状态转移方程）计算出下一个时刻的状态。**如果需要导数系统来预测未来状态，那么导数项通常需要包含在状态向量中**。
3. 与观测相关：状态向量中的元素必须能够通过观测模型（观测方程）映射到实际测量到的观测值上。
4. 最小化：状态向量应该包含**最少且必要**的向量。过多的变量会增加计算复杂度，可能导致数值不稳定或过拟合；过少的变量则无法准确描述系统动态。
5. 可观测性：理想情况下，状态向量的所有元素都应该是（直接或间接）可观测的。

### 思考步骤

1. 明确问题：想用卡尔曼滤波解决什么问题？
2. 分析系统动态：
   - 系统的核心物理量是什么？
   - 这些量如何随时间变化？（速度是位置的导数，加速度是速度的导数）
   - 影响这些量变化的主要因素是什么？（力、控制输入、噪声）
3. 确定关键状态变量：基于系统动态分析，列出所有描述系统当前状态所必需的变量。
4. 检查预测需求：
   - 仅凭当前的位置值，能否预测下一刻的位置？通常不能，除非速度是零或已知。
   - 如果知道了当前的位置和速度（假设匀速），就能预测下一时刻的位置。
   - 如果存在加速度（如重力、推力），就需要把加速度也包含进来（或作为输入，或作为状态的一部分）才能准确预测速度的变化。
   - **经验法则：如果预测下一个时刻的状态 $X_{k+1}$ 需要知道某个量在当前时刻 $k$ 的值，并且这个量本身也会变化，那么这个量通常需要包含在状态向量中。**

### 常见示例

1. 匀速直线运动（constant velocity-cv模型）
   - 状态变量：位置（p），速度（v）
   - 状态向量：$x = \begin{bmatrix} \text{p} \\ \text{v} \end{bmatrix}$
   - 为什么？知道p和v就能预测下一时刻的位置 $p(k+1) = p(k) + v(k) * \Delta t$。
2. 匀加速直线运动（constant acceleration-ca模型）
   - 状态变量：位置（p），速度（v），加速度（a）
   - 状态向量：$x = \begin{bmatrix} \text{p} \\ \text{v} \\ \text{a} \end{bmatrix}$
   - 为什么？知道p、v和a，就能预测下一时刻的速度 $v(k+1) = v(k) + a(k) * \Delta t$ 和位置 $p(k+1) = p(k) + v(k) * \Delta t + 1/2 * a(k) * \Delta t^2$
3. 二维/三维位置跟踪：
   - 状态变量：x位置（px），x速度（vx），y位置（py），y速度（vy），z位置（pz），z速度（vz）
   - 状态向量：$x = \begin{bmatrix} \text{px} \\ \text{vx} \\ \text{py} \\ \text{vy} \end{bmatrix}$ 或 $x = \begin{bmatrix} \text{px} \\ \text{vx} \\ \text{py} \\ \text{vy} \\ \text{pz} \\ \text{vz} \end{bmatrix}$

## 状态转移矩阵

描述系统系统状态如何随时间演化的数学模型，其定义直接决定了状态预测的准确性。它的设计需要结合物理规律和离散化时间步长。

### 定义步骤

1. 确定系统动态模型，根据物理规律建立连续时间的状态微分方程：

    **位置的变化率=速度**： $\frac{dp(t)}{dt}=v(t)$

    **速度的变化率=0**（假设无外力作用）：$\frac{dv(t)}{dt}=0$

    将两个微分方程写成矩阵形式：
    $$\frac{d}{dt}\begin{bmatrix} p(t) \\ v(t) \end{bmatrix}=\begin{bmatrix} v(t) \\ 0 \end{bmatrix}$$

2. 提取连续时间状态转移矩阵$A_c$：

    将右侧表示为状态向量的线性组合：
    $$\begin{bmatrix}
        v(t) \\
        0
    \end{bmatrix}=\begin{bmatrix}
        0 · p(t) + 1 · v(t) \\
        0 · p(t) + 0 · v(t)
    \end{bmatrix}=\begin{bmatrix}
        0 & 1 \\
        0 & 0
    \end{bmatrix}\begin{bmatrix}
        p(t) \\
        v(t)
    \end{bmatrix}=A_cx(t)$$
    因此，连续时间状态转移矩阵为：
    $$A_c=\begin{bmatrix}
        0 & 1 \\
        0 & 0
    \end{bmatrix}$$
    #### 物理意义解读
    - 第一行（位置方程）：
      - 0 · p：位置变化不依赖当前位置
      - 1 · v：位置变化率等于速度 v
    - 第二行（速度方程）：
      - 0 · p：速度变化与位置无关
      - 0 · v：速度变化率为零
3. 离散化得到状态转移矩阵$F$

    连续时间状态转移矩阵是$A_c$，但卡尔曼滤波需要在离散时间上运行，因此需要将连续模型离散化。离散化公式为：
    $$F=e^{A_c\Delta t}$$
    其中$\Delta t$是离散时间步长。
    通过矩阵指数运算离散化（一阶近似）：
    $$F=e^{A_c\Delta t}\approx I + A_c \Delta t = \begin{bmatrix}
        1 & 0 \\
        0 & 1
    \end{bmatrix}+\begin{bmatrix}
        0 & 1 \\
        0 & 0
    \end{bmatrix}\Delta t$$
    最终得到离散状态转移矩阵：
    $$F=\begin{bmatrix}
        1 & \Delta t \\
        0 & 1
    \end{bmatrix}$$

## 代码示例

    ```python
    import torch


    class KalmanFilter1D:
        """一维位置-速度卡尔曼滤波器"""

        def __init__(self, dt, process_variance, meas_variance, init_y):
            """
            :param dt: 帧间隔 (s)，25fps -> 1/25
            :param process_variance: 过程噪声方差 1e-2 适度信任模型，允许小幅加速度变化，增大过程噪声 Q，滤波器更敏感于动态变化 越大 → 越不信物理模型（F·x），更依赖测量。越小 → 越相信上一帧预测出的位移+速度，认为脚踝变化平稳，少突变。
            :param meas_variance: 测量噪声方差 检测误差方差约为 0.1 (像素²)，减小测量噪声 R，滤波器更“信”观测值。越大 → 越不信观测点，更多用模型预测。
            :param init_y: 传入脚踝y坐标的初始化值
            """
            self.dt = dt

            # 状态转移矩阵 F (改为 torch.Tensor)
            self.F = torch.tensor([[1.0, self.dt],
                                [0.0, 1.0]], dtype=torch.float32)  # 注意浮点数类型

            # 观测矩阵 H
            self.H = torch.tensor([[1.0, 0.0]], dtype=torch.float32)

            # 过程噪声协方差 Q (使用 torch 计算)
            q = process_variance
            self.Q = q * torch.tensor([[self.dt ** 4 / 4, self.dt ** 3 / 2],
                                    [self.dt ** 3 / 2, self.dt ** 2]], dtype=torch.float32)

            # 测量噪声协方差 R (保持标量但包装成 1x1 矩阵)
            self.R = torch.tensor([[meas_variance]], dtype=torch.float32)

            if init_y is not None:
                self.x = torch.tensor([[init_y], [0.0]], dtype=torch.float32)
                self.P = torch.diag(torch.tensor([0.5, 100.0]))
            else:
                # 后验状态估计 x (保持 2x1 向量形状)
                self.x = torch.zeros((2, 1))
                # 估计误差协方差矩阵 P
                self.P = torch.eye(2)

            self.warmup_steps = 5
            self.step = 0

        def update_init_y(self, init_y):
            self.x = torch.tensor([[init_y], [0.0]], dtype=torch.float32)

        def predict(self):
            # 矩阵乘法
            self.x = self.F @ self.x
            self.P = self.F @ self.P @ self.F.T + self.Q
            return self.x

        def update(self, z):
            self.step += 1
            # 热身：跳过前 warmup_steps 次 update 只做 predict
            if self.step <= self.warmup_steps:
                return self.predict()

            # 将观测值 z 转换为 1x1 张量 (保持维度一致性)
            z = torch.tensor([[z]], dtype=torch.float32)

            # 残差计算(1×1) H(1×2) @ x(2×1) → 标量(包装为1×1)
            y = z - self.H @ self.x

            # 残差协方差(1×1) H(1×2)@P(2×2)@Hᵀ(2×1)→1×1
            S = self.H @ self.P @ self.H.T + self.R

            # 卡尔曼增益 P(2×2)@Hᵀ(2×1)→2×1, 再@S⁻¹(1×1)
            K = self.P @ self.H.T @ torch.inverse(S)

            # 更新状态和(2×1) K(2×1)@y(1×1)→2×1
            self.x = self.x + K @ y
            # 协方差更新(2×2) K(2×1)@H(1×2)→2×2 torch.eye 单位矩阵
            self.P = (torch.eye(2) - K @ self.H) @ self.P

            return self.x
    ```
