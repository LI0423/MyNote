# 自适应直方图均衡（Adaptive Histogram Equalization，AHE）

## 核心思想

将图像划分成许多小的“窗口”（或称tile、patch），对每个窗口单独做直方图均衡，从而在局部范围内增强对比度。

## 算法流程

1. 划分窗口

    将原图划分成大小相同的网格。假设图像尺寸为 $H\times W$，网格大小为 $h\times w$，则网格行数为 $H / h$，列数为 $W / w$。
2. 局部直方图均衡

   对每个网格内部：
   1. 统计灰度直方图 $h_i[g]$，其中 $g \in [0,255]$
   2. 计算累积分布函数
        $$CDF_i(g) = \sum^g_{k=0}h_i[k]$$
   3. 归一化CDF，得到映射函数
        $$T_i(g)=\begin{bmatrix}
            255 \times \frac{CDF_i(g)}{N_i}
        \end{bmatrix}$$
        其中 $N_i = h \times w$（窗口像素总数）。
3. 插值融合

    窗口之间会产生“接缝”或者不连续的映射，为了平滑过渡，对图像上每个像素，根据其所在的四个相邻窗口的映射函数加权插值：
    - 找到像素点 $(x,y)$ 所在的网格索引 $(i,j)$ 及其相邻窗口 $(i+1,j)(i,j+1)(i+1,j+1)$。
    - 分别取这四个窗口的映射值 $T_{i,j}(g),T_{i+1,j}(g),...$。
    - 按照像素在四窗口中心的距离做双线性插值：
        $$T(x,y)=\alpha\beta T_{i,j} + (1-\alpha)\beta T_{i+1,j} + \alpha(1-\beta)T_{i,j+1} + (1-\alpha)(1-\beta)T_{i+1,j=1}$$
        其中 $\alpha$ 和 $\beta$ 分别是横向和纵向的归一化距离系数。
4. 重建图像

   将每个像素的原灰度 $g$ 替换为插值后的映射值 $T(x,y)$。

## 关键参数影响

- 窗口大小（tile size）
  - 小窗口 -> 更细致的局部增强，但计算量大，易出现“块状”伪影。
  - 大窗口 -> 趋近于全局均衡，局部增强效果下降。
- 插值方式
  - 通常使用双线性插值。

## 优缺点

- 优点：
  - 能针对不同明暗区域分别增强对比度，突出局部细节。
  - 对于低对比度、细节丰富的图像，能挖掘更多信息。
- 缺点：
  - 噪声放大：平坦区域的微小噪声也会被当成“对比度”信号增强，可能出现雪花或斑点伪影。
  - 计算量大：每个窗口都需要计算直方图、CDF和插值，实时性较差。
  - 需要调参：窗口大小需根据图像特点反复试验。

## 代码示例

```Python
def contrast_limited_ahe(img, clipLimit=1.7.0, tileGridSize=(8, 8)):
    clahe = cv2.createCLAHE(clipLimit=clipLimit, tileGridSize=tileGridSize)
    channels = cv2.split(img)
    channels_res = [clahe.apply(channel) for channel in channels]
    return cv2.merge(channels_res)
```
