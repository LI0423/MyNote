# 自适应限幅直方图均衡（CLAHE，Contrast Limited Adaptive Histogram Equalization）

## 核心思想

在AHE的基础上，为了避免局部直方图中某些灰度频次过高（如噪点或均匀区域）被过度放大，CLAHE在对每个tile的直方图做均衡前，先对直方图进行限幅处理：

1. 限定直方图最大条目数：

   设定一个阈值 $T$（由 clip_limit 参数决定），当某灰度级在该tile中的频次 $h_i \gt T$ 时，将多余部分（$h_i - T$）称为“溢出像素”。

2. 溢出像素再分配

   将所有灰度级的溢出像素加总和后，平均分配到直方图的每一个灰度级上，保证总像素数不变。

3. 基于限幅后直方图做均衡映射

   用处理后的直方图计算累积分布函数（CDF），映射该tile内的像素灰度。

4. 双线性插值融合

   对相邻tile的映射结果进行双线性插值，消除边界块状效应。

## 主要参数及影响

|    参数       |       含义                  |      影响                                                                   |
|--------------|----------------------------|-----------------------------------------------------------------------------|
|tileGridSize  |图像被划分的块大小$(M,N)$      |块越小，局部对比度越强，但块数多，计算开销大；块越大，接近全局HE，局部增强弱。            |
|clipLimit     |限幅阈值，通常按比例或绝对像素给出|限幅越小，对比度提升越弱，噪声和伪影越少；限幅越大，局部对比度越强，但易过曝或产生网格伪影。 |

## 优缺点

### 优点

- 局部对比度增强：能突出细节，尤其在光照不均或暗部细节提升明显。
- 边缘伪影抑制：通过限幅和重分配，避免AHE放大噪声和过度明显的网格状伪影。
- 通用性强：在医学影像、显微成像、夜景增强、人脸细节修复等领域表现稳定。

### 缺点

- 计算开销：分块+插值+限幅，运算量高于全局HE；
- 伽马扭曲：对比度过强时，亮部或暗部可能失真；
- 参数敏感：tileGridSize 与 clipLimit 需要根据图像特性调参，否则容易出现块状或过曝。

## 代码示例

```Python
def contrast_limited_ahe(img, clipLimit=40.0, tileGridSize=(8, 8)):
    # 转换到 LAB 色彩空间
    lab = cv2.cvtColor(img, cv2.COLOR_BGR2LAB)
    l, a, b = cv2.split(lab)
    clahe = cv2.createCLAHE(clipLimit=clipLimit, tileGridSize=tileGridSize)
    cl = clahe.apply(l)
    # 合并回 LAB
    merged_lab = cv2.merge((cl, a, b))
    # 转换回 BGR
    final_img = cv2.cvtColor(merged_lab, cv2.COLOR_LAB2BGR)
    return final_img
```
