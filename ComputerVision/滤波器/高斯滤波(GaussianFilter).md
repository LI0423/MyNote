# 高斯滤波（Gaussian Filter）

信号处理与图像处理中的一种基本平滑（低通技术）。通过与高斯核（Gaussian kernel）卷积，抑制信号或图像中的高频成分（噪声、细节），同时尽量保留低频成分（主要结构）。

## 连续高斯函数与卷积

### 1.1 一维与二维高斯函数

- 一维
  $$G(x;\sigma) = \frac{1}{\sqrt{2\pi}\sigma}exp(-\frac{x^2}{2\sigma^2})$$

- 二维
  $$G(x,y;\sigma) = \frac{1}{2\pi\sigma^2}exp(-\frac{x^2+y^2}{2\sigma^2})$$

### 1.2 连续卷积定义

对信号或图像$I(x,y)$做高斯滤波：
$$(I*G)(x,y)=\int^{+\infin}_{-\infin}\int^{+\infin}_{-\infin} I(u,v)G(x-u,y-v;\sigma)dudv$$
这里 $\sigma$ 决定滤波强度：$\sigma$越大，平滑越明显。

## 离散化：高斯卷积核的构造
