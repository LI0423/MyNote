# OpenCV

## 图像处理

### threshold

图像二值化处理。

ret, img = cv2.threshold(scr=imgGray, thresh=0, maxval=255, type=cv2.THRESH_BINARY | cv2.THRESH_OTSU)

- src，通常是一个灰度图像。阈值处理通常在灰度图像上进行，因为灰度图像只有一个通道，处理起来比较简单。
- thresh，阈值。用于分类像素值的参数。所有小于thresh的像素值都会被赋予一个新的值，而所有大于或等于thresh的像素都会被赋予另一个值（通常是maxval）。
- maxval，当像素值超过阈值时，所赋予的值，在二值化情况下，通常是255（白色）。
- type，阈值类型，决定如何处理像素值与阈值之间的关系。
  - cv2.THRESH_BINARY，二值化阈值化，所有大于thresh的像素值设为maxval，小于等于thresh的像素值设为0。
  - cv2.THRESH_BINARY_INV，反二值化阈值化，大于thresh的像素值设为0，小于等于的设为maxval。
  - cv2.THRESH_TRUNC，截断阈值化，大于thresh的值设为thresh，小于等于的不变。
  - cv2.THRESH_TOZERO，阈值化为0，大于thresh的不变，小于等于的设为0。
  - cv2.THRESH_TOZERO_INV，反阈值化为0，大于thresh的设为0，小于等于的不变。
  - cv2.THRESH_OTSU，大津阈值处理，根据当前图像自动找到最佳的类间分割阈值，使用时需要吧thresh设为0。OTSU方法会遍历所有可能的阈值，从而找到最佳的阈值，使类间方差最大。

### adaptiveThreshold

自适应阈值，对于色彩不均匀的图像，需要使用自适应阈值来处理，可以得到清洗有效的阈值分割记过。通过计算每个像素点周围临近区域的加权平均值来获取阈值，并使用该阈值对当前像素点进行处理。

img = cv2.adaptiveThreshold(src=imgGray, maxval=255, adaptiveMethod=cv2.ADAPTIVE_THRESH_MEAN_C, thresholdType=cv2.THRESH_BINARY, blockSize=5, C=3)

- adaptiveMethod，表示自适应方法
- cv2.ADAPTIVE_THRESH_MEAN_C，邻域所有像素点的权重值是一致的。
- cv2.ADAPTIVE_THRESH_GAUSSIAN_C，与邻域各个像素点到中心点的距离有关，通过高斯方程得到各个点的权重值。
- blockSize，表示块大小，一个像素在计算其阈值时所用的邻域尺寸，通常为3，5，7
- C，表示常量值

### erode

腐蚀操作

erosion = cv2.erode(img, kernel, iterations=1)

- 腐蚀要对灰度图像进行操作，kernel定义腐蚀操作的结构元素，iterations表示腐蚀的次数，值越大腐蚀效果更明显

### dilate

膨胀操作

dilate = cv2.dilate(img, kernel, iterations=1)

- 膨胀对灰度图像进行操作，kernel定义膨胀操作的结构元素，iterations表示膨胀的次数，值越大膨胀效果更明显

### morphologyEx

#### cv2.MORPH_OPEN 开运算

- opened_image = cv2.morphologyEx(img, cv2.MORPH_OPEN, kernel)

- 开操作，先腐蚀后膨胀，分离物体，消除小区域，消除噪点，去除小的干扰块，而不影响原来的图像。

#### cv2.MORPH_CLOSE 闭运算

- closed_image = cv2.morphologyEx(img, cv2.MORPH_CLOSE, kernel)

- 闭操作，先膨胀后腐蚀，消除闭合物体里的孔洞，可以填充闭合区域。

#### cv2.MORPH_BLACKHAT 黑帽运算

- black_hat = cv2.morphologyEx(img, cv2.MORPH_BLACKHAT, kernel)

- 黑帽运算是结果图与原图像之差，结果图像突出了比原图轮廓周围的区域更暗的区域，这一操作和选择的核的大小相关。可以用来分离比邻近点暗一些的斑块。

#### cv2.MORPH_GRADIENT 形态学梯度

- gradient = cv2.morphologyEx(img, cv2.MORPH_GRADIENT, kernel)

- 形态学梯度时膨胀图像与腐蚀图像之差，可以得到图像的轮廓，通常用于提取物体边缘。

## 图像滤波

### blur

均值滤波，将每个像素的值设置为其周围像素的平均值。

cv2.blur(frame, ksize=(5, 5), dst=0)

### medianBlur

中值滤波，将每个像素的值设置为其周围像素的中值，即排序后的中间值。

cv2.medianBlur(frame, ksize=(5, 5))

### bilateralFilter

双边滤波，将每个像素的值设置为其周围像素的加权平均值，权重除了像素间距离外，还考虑它们的像素相似值。

cv2.bilateralFilter(frame, d=9, sigmaColor=75, sigmaSpace=75)

- 双边滤波，图像，滤波器直径，颜色空间滤波器的sigma值，坐标空间滤波器的sigma值。
- d，滤波器的直径，必须是正奇数，决定了滤波器的空间范围。
- sigmaColor，该值越大，颜色滤波的范围越广，即更多的颜色将被混合在一起。
- sigmaSpace，该值越大，空间滤波的范围越广，即更多的像素被包括在滤波过程中。

### GaussianBlur

高斯滤波，将每个像素的值设置为其周围像素的加权平均值，权重取决于他们到中心像素的距离，距离越远的像素权重越小。

cv2.GaussianBlur(src=frame, ksize=(5, 5), sigmaX=0)

- 高斯滤波，图像，滤波核的大小，卷积核在X轴方向的标准差，卷积核在Y轴方向的标准差，边界样式
- ksize，滤波核大小是指在滤波处理过程中其邻域图像的高度和宽度。滤波核的值必须是奇数。
- sigmaX，控制的是权重比例。如果设置为0，则让函数自己去计算sigmaX的具体值
- sigmaY，如果将该值设置为0，则只采用sigmaX的值。
- borderType，该值决定以何种方式处理边界，一般不需要考虑该值，直接采用默认值就好。

## 边缘检测

### Sobel算子

一种离散的微分算子，结合了高斯平滑（离像素点越远的值越小）和微分求导运算（像素变化率，从左到右或从右到左）。利用局部差分寻找边缘，计算所得的是一个梯度的近似值。

cv2.Sobel(src, ddepth, dx, dy[, ksize[, delta[, [borderType]]]])

- ddepth，输出图像的深度（可以理解为数据类型），-1表示与原图像相同的深度。
- dx，dy，当组合为dx=1，dy=0时求x方向的一阶导数，当组合为dx=0，dy=1时求y方向的一阶导数（如果同时为1，通常效果不佳）。
- ksize，（可选参数）Sobel算子的大小，必须是1，3，5或7，默认为3。

```python
yuan = cv2.imread('yuan.png')
x_64 = cv2.Sobel(yuan, cv2.CV_64F, dx=1, dy=0)
x_full = cv2.convertScaleAbs(x_64)
y_64 = cv2.Sobel(yuan, cv2.CV_64F, dx=0, dy=1)
y_full = cv2.convertScaleAbs(y_64)
xy_full = cv2.addWeighted(x_full, 1, y_full, 1, 0)
```

### Scharr算子

Scharr算子是Sobel算子在ksize=3时的优化，与Sobel的速度相同，且精度更高。

cv2.Scharr(src, ddepth, dx, dy[, dst[, scale[, delta[, borderType]]]])

- ddepth，输出图片的数据深度，由输入图像的深度进行选择
- dx，x轴方向导数的阶数
- dy，y轴方向导数的阶数

```python
yuan = cv2.imread('yuan.png', cv2.IMREAD_GRAYSCALE)
x_64 = cv2.Scharr(yuan, cv2.CV_64F, dx=1, dy=0)
x_full = cv2.convertScaleAbs(x_64)
y_64 = cv2.Scharr(yuan, cv2.CV_64F, dx=0, dy=1)
y_full = cv2.convertScaleAbs(y_64)
xy_full = cv2.addWeighted(x_full, 1, y_full, 1, 0)
```

### Laplacian

不再以x和y的方向计算，而是以圆方向计算变化率。因此不需要Gx+Gy。

cv2.Laplacian(src, ddepth, [, dst[, ksize[, scale[, delta[, borderType]]]]])

- ddepth，输出图片的数据深度
- ksize，计算二阶导数滤波器的孔径大小，必须为正奇数
- scale，缩放比例因子，可选项，默认为1
- delta，输出图像的偏移量，可选项，默认为0

```python
yuan = cv2.imread('yuan.png', cv2.IMREAD_GRAYSCALE)
lap = cv2.Laplacian(yuan, cv2.CV_64F)
full = cv2.convertScaleAbs(lap)
```

### Canny

1. 图像降噪
2. 梯度计算
   - 根据图像的梯度幅值和梯度方向来确定边缘，一般均采用sobel算子对图像进行梯度幅值与梯度方向计算。
3. 非极大值抑制
   - 在梯度图像中寻找梯度方向上的最大值作为边缘，不是梯度方向上的最大值则抑制为0。梯度方向是灰度变化最大的方向。
4. 双阈值边界跟踪
   - 根据实际情况需要设置一个灰度高阈值和一个灰度低阈值对NMS后的图像进行过滤，使得得到的边缘尽可能是真实的边缘。
   - $f_H$和$f_L$分别表示大阈值和小阈值，由用户设定：
     - $f > f_H$，将当前像素点标记为强边缘像素；
     - $f_L < f < f_H$，将当前像素点标记为弱边缘像素；
     - $f < f_L$，将当前像素点灰度置0。

cv2.Canny(src, threashold1, threshold2[, apertureSize[, L2gradient]])

- threshold1，表示处理过程中的第一个阈值。
- threshold2，表示处理过程中的第二个阈值。

```python
yuan = cv2.imread('yuan.png', cv2.IMREAD_GRAYSCALE)
canny = cv2.Canny(yuan, 100, 150)
```

## 深度神经网络模块（DNN）

### blobFromImage

用于将图像转换为深度学习模型所需的输入格式，主要是对传入的图像进行的转换包括图像尺寸调整、均值减法、缩放等预处理步骤，以便图像数据能够适配深度学习模型的输入要求。

- 图像尺寸调整：函数可以根据需要调整图像的尺寸，以匹配神经网络的输入尺寸。
- 均值减法：可以指定一个均值（mean），该函数会从图像的每个通道中减去这个均值。通常用于数据中心化，以提高模型的训练和推理性能。
- 缩放：通过scalefactor参数，可以对图像数据进行缩放，通常用于数据归一化。
- 通道交换：如果swapRB参数设置为true，则会交换图像的红色（R）和蓝色（B）通道，因为OpenCV默认使用BGR格式，而某些神经网络框架使用RGB格式。
- 裁剪：如果crop参数设置为true，则在调整图像大小时进行中心裁剪，以确保输出尺寸与指定的尺寸精确匹配。
- 数据类型：通过ddepth参数可以指定输出blob的深度，通常选择CV_32F（32位浮点数）。
- 批量处理：还有一个对应的函数blobFromImages，用于将一系列图像转换为一个批量的blob，在处理图像批次时更为高效。

函数执行顺序：

1. 先相对于原图像中心resize，crop
2. 减去均值
3. 像素值缩放0-255 -> 0-1
4. 图像数据通道转换，RGB -> BGR
5. 返回一个NCHW数组
