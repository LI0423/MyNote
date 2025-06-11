# 均值滤波(Mean Filter)

是一种线性空间滤波技术，是图像处理和信号处理中最基础、应用最广泛的平滑滤波方法之一。核心思想是用局部区域像素的平均值代替中心像素值，从而达到平滑图像、抑制噪声的目的。

## 1. 基本概念

### 1.1 原理

- 核心操作：用一个固定大小的窗口（卷积核）在图像上滑动，计算窗口内所有像素的算数平均值，并用该值替换窗口中心像素的值。
- 数学本质：离散卷积操作

### 1.2 关键参数

- 窗口大小：通常为奇数（3x3，5x5，7x7等），决定平滑度
- 边界处理：需特殊处理边界像素（常用补零、镜像、复制边界等方法）

## 2. 数学模型

### 2.1 一维信号均值滤波

对于一维离散信号$x[n]$，窗口大小为$2M+1$的均值滤波器输出为：

$$
y[n]=\frac{1}{2M+1}\sum_{k=-M}^{M}x[n+k]
$$

### 2.2 二维图像均值滤波

对于二维图像$I(x,y)$，窗口大小为$m\times{n}$，（$m,n$为奇数）：

$$
I'(x,y)=\frac{1}{m\times{n}}\sum_{i=-a}^{a}\sum_{j=-b}^{b}I(x+i,y+j)
$$

其中：

- $a=\frac{m-1}{2},b=\frac{n-1}{2}$
- $(x,y)$为中心像素坐标
  
## 3. 卷积核表示

均值滤波可视为使用特定卷积核进行卷积运算：

- **3x3均值滤波核**

$$
K=\frac{1}{9}\begin{bmatrix}
1 & 1 & 1 \\
1 & 1 & 1 \\
1 & 1 & 1
\end{bmatrix}
$$

- **5x5均值滤波核**

$$
K=\frac{1}{25}\begin{bmatrix}
1 & 1 & 1 & 1 & 1 \\
1 & 1 & 1 & 1 & 1 \\
1 & 1 & 1 & 1 & 1 \\
1 & 1 & 1 & 1 & 1 \\
1 & 1 & 1 & 1 & 1
\end{bmatrix}
$$

## 4. 算法实现

```Python
import numpy as np

def mean_filter(image, kernel_size=3):
    """
    均值滤波实现
    :param image: 输入图像（H，W）或（H，W，C）
    :param kernel_size: 卷积核大小（奇数）
    :return: 滤波后图像
    """
    # 边界填充
    pad = kernel_size // 2
    padded = np.pad(image, pad, mode='edge')

    # 初始化输出
    filtered = np.zeros_like(image)

    # 遍历每个像素
    for i in range(image.shape[0]):
        for j in range(image.shape[1]):
            # 获取局部窗口
            window = padded[i:i+kernel_size, j:j+kernel_size]
            # 计算均值
            filtered[i, j] = np.mean(window)
    return filtered
```

## 5. 特性

- **优点**

- 计算简单，实现高效
- 有效抑制高斯噪声
- 保持图像整体亮度不变

- **缺点**

|缺点        |说明                            |
|-----------|--------------------------------|
|边缘模糊    |平滑过程中丢失高频信息，导致边缘变模糊 |
|椒盐噪声敏感 |对脉冲型噪声（椒盐噪声）处理效果差    |
|方块效应    |大窗口滤波时可能出现块状伪影         |
