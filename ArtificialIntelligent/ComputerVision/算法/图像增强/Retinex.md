# Retinex

## 名称由来

Retinex是Retina（视网膜）和Cortex（大脑皮层）两个词的合成。强调了颜色感知是视网膜接收光信号和大脑皮层进行复杂处理共同作用的结果。

## 基本假设

基本成像模型：Retinex理论建立在简单的物理模型之上：
$$S(x,y) = R(x,y) * L(x,y)$$

- $S(x,y)$：在图像位置 $(x,y)$ 处观察（感知）到的亮度值（传感器捕获的值）。
- $R(x,y)$：在位置 $(x,y)$ 处物体的反射率。这是物体的固有属性，决定了物体反射多少比例的光以及反射哪些波长的光（即颜色）。理想情况下，$R$ 应该在[0,1]范围内（0表示全吸收，1表示全反射）。
- $L(x,y)$：在位置 $(x,y)$ 处照射到物体上的光照强度。这是光源的属性，可以随空间位置变化（非均匀光照）。

目标：给定观测到的图像 $S$，估计出反射图像 $R$（代表物体本身的颜色和纹理）和光照图像 $L$。Retinex的核心任务就是估计 $R$，因为它被认为包含了物体的“真实”颜色信息，相对不受光照 $L$ 变化的影响。

## 核心思想：颜色恒常性

Retinex理论的核心目标是解释和模拟人类的颜色恒常性现象。什么是颜色恒常性？

现象：在不同的光照条件下，我们感知到的物体的颜色是相对稳定的。

挑战：实际上物体反射到我们眼睛的光的绝对强度和光谱组成是随着光照条件剧烈变化的。相机拍出来的原始照片就明显收到光照颜色的影响。

Retinex的解答：人类视觉系统并不是简单地记录到达视网膜光的绝对量，而是能够分离场景中的光照成分和物体本身的反射属性成分。大脑通过比较场景中不同区域的亮度关系，来推断物体固有的“颜色”（反射率），从而在很大程度上抵消了光照变化的影响。

## 关键原理：相对亮度比较

**物体的颜色感知主要取决于它与周围区域亮度的相对关系，而不是绝对亮度值本身。**

- 局部对比度：大脑更关注相邻区域之间的亮度比值。
- 多尺度处理：颜色恒常性的实现可能涉及在不同空间尺度上比较亮度。小尺度的比较处理细节和纹理，大尺度的比较处理整体光照变化。

## 经典Retinex算法

### 1.路径比较法（Path-wise/Random Path Retinex）

- 思路：对于图像中的每个像素点 $(x,y)$，随机选择一条从图像边界（或其他参考点）到该点的路径。
- 计算：沿着路径计算相邻像素点之间亮度值的比值（通常是某个颜色通道，如R，G，B）。最终，$(x,y)$ 点的反射率估计 $R(x,y)$ 被计算为这条路径上所有比值的乘积（或对数和）。
- 意义：这个乘积（或和）模拟了光从边界传播到目标点时，沿途反射率变化累积的效果（比值相乘），抵消了光照变化（如果光照是平滑变化的，路径上的光照比会接近1）。
- 问题：结果依赖于随机路径的选择，需要多次随机取平均，计算量大；路径选择方式对结果有显著影响。

### 2.中心环绕法/比例处理（Center/Surround Retinex）

- 思路：这是最著名和最实用的Retinex变种。对于每个像素点 $(x,y)$，计算它在一个小的中心区域（通常就是该点本身或极小领域）的亮度与在一个较大的环绕区域（通过高斯模糊模拟）的平均亮度之间的比值。
- 计算：单尺度 Retinex(SSR)：$R(x,y)\approx log(S(x,y)) - log[F(x,y) * S(x,y)]$
  - $F(x,y)$ 是一个低通滤波器（通常是高斯滤波器）的核，&*& 表示卷积。$F(x,y) * S(x,y)$ 就是对图像进行高斯模糊得到的结果，代表了该点周围区域的平均光照估计 $L_{est}(x,y)$。
  - 取对数是为了将乘法关系 $S=R*L$转换为加法关系：$log(S) = log(R) + log(L)$。这样，$log(R) \approx log(S) - log(L_{est})$。
- 多尺度Retinex(MSR)：使用多个不同尺度（不同高斯模糊核大小）的高斯环绕滤波器计算结果，然后将这些结果加权求和。这能同时处理不同尺度的光照变化和细节。
- 带颜色恢复的多尺度Retinex（MSRCR）：经典MSR处理各颜色通道（R，G，B）独立进行，有时会导致颜色失真。MSRCR在MSR结果上引入了颜色恢复步骤（如根据原始图像色彩比例进行恢复），以获得更自然的颜色。
- 优点：计算相对高效，效果直观可见（增强图像，颜色校正，去雾）。
- 缺点：高斯模糊可能导致光晕伪影，特别是在强边缘附近（模糊区域跨越了明暗边界）；参数（高斯核大小、尺度权重）需要调整。

### 3.变分法（Variational Methods）

- 思路：将Retinex问题表述为一个能量最小化问题。定义能量函数包含两项：
  - 数据保真项：要求重建的图像 $R * L$ 尽可能接近观测图像 $S$。
  - 正则化项：利用先验知识约束 $R$ 和 $L$。关键的先验是：
    - 反射图 $R$：具有分段常数特性（同一物体区域反射率变化小），包含锐利的边缘（物体边界）。
    - 光照图 $L$：变化缓慢且平滑（光照通常是空间渐变的），没有锐利边缘。
- 求解：通过优化算法（如梯度下降、迭代重加权最小二乘）最小化能量函数，同时估计出 $R$ 和 $L$。
- 优点：数学框架更严谨，能更好地处理光晕问题（通过显示约束 $L$ 平滑，$R$ 分片光滑），结果通常更优。
- 缺点：计算更复杂；需要精心设计能量函数和正则化项。

## 代码示例

### 单尺度Retinex（SSR）

```Python
import cv2
import numpy as np
from scipy.ndimage import gaussian_filter

def single_scale_retinex(image, sigma, gain=128, offset=128):
    """
    单尺度Retinex(SSR)图像增强算法

    参数:
    image: 输入图像 (BGR格式)
    sigma: 高斯滤波器的标准差，控制光照估计的尺度
    gain: 输出增益，控制增强程度
    offset: 输出偏移量，调整亮度水平

    返回:
    enhanced: 增强后的图像 (BGR格式)
    """
    # 1. 将BGR图像转换为浮点类型并归一化到[0,1]范围
    # 这样便于后续计算，避免整数运算的截断误差
    img_float = image.astype(np.float32) / 255.0

    # 2. 分离图像的三个通道 (B, G, R)
    # Retinex算法需要对每个颜色通道独立处理
    b, g, r = cv2.split(img_float)

    # 3. 对每个通道计算对数域的Retinex处理
    # SSR的核心公式: R(x,y) = log(I(x,y)) - log(F(x,y)*I(x,y))
    # 其中F是高斯滤波器，*表示卷积

    # 处理蓝色通道
    # 3.1 计算蓝色通道的对数值 (+1e-6避免log(0))
    log_b = np.log(b + 1e-6)
    # 3.2 应用高斯滤波器估计光照分量
    blur_b = gaussian_filter(b, sigma=sigma)
    # 3.3 计算光照分量的对数值
    log_blur_b = np.log(blur_b + 1e-6)
    # 3.4 计算反射分量 (Retinex核心操作)
    retinex_b = log_b - log_blur_b

    # 处理绿色通道 (同上)
    log_g = np.log(g + 1e-6)
    blur_g = gaussian_filter(g, sigma=sigma)
    log_blur_g = np.log(blur_g + 1e-6)
    retinex_g = log_g - log_blur_g

    # 处理红色通道 (同上)
    log_r = np.log(r + 1e-6)
    blur_r = gaussian_filter(r, sigma=sigma)
    log_blur_r = np.log(blur_r + 1e-6)
    retinex_r = log_r - log_blur_r

    # 4. 合并三个通道的Retinex结果
    retinex = cv2.merge([retinex_b, retinex_g, retinex_r])

    # 5. 对Retinex结果进行对比度拉伸和归一化
    # 5.1 计算整个图像的最小值和最大值
    min_val = np.min(retinex)
    max_val = np.max(retinex)

    # 5.2 将结果线性映射到[0,1]范围
    # 公式: (retinex - min_val) / (max_val - min_val)
    normalized = (retinex - min_val) / (max_val - min_val + 1e-6)

    # 6. 应用增益和偏移调整增强效果
    # 增益控制对比度，偏移控制亮度
    enhanced = normalized * gain + offset

    # 7. 将像素值限制在[0,255]范围内并转换为uint8类型
    # 使用clip确保值在0-255之间，避免溢出
    enhanced = np.clip(enhanced, 0, 255).astype(np.uint8)

    return enhanced

# 示例使用
if __name__ == "__main__":
    # 读取输入图像
    image_path = "input.jpg"
    original = cv2.imread(image_path)

    # 检查图像是否正确加载
    if original is None:
        print(f"错误: 无法读取图像 {image_path}")
        exit()

    # 设置Retinex参数
    sigma = 80  # 高斯滤波器标准差 (控制光照估计的尺度)
    gain = 128  # 输出增益 (控制对比度增强)
    offset = 128  # 输出偏移 (控制整体亮度)

    # 应用单尺度Retinex增强
    enhanced = single_scale_retinex(original, sigma, gain, offset)

    # 显示原始和增强后的图像
    cv2.imshow("Original Image", original)
    cv2.imshow("Enhanced Image (SSR)", enhanced)

    # 保存结果
    cv2.imwrite("enhanced_output.jpg", enhanced)

    # 等待按键关闭窗口
    cv2.waitKey(0)
    cv2.destroyAllWindows()
```

### 多尺度Retinex（MSR）

```Python
import cv2
import numpy as np
from scipy.ndimage import gaussian_filter

def multi_scale_retinex(image, sigmas=[15, 80, 250], weights=None, gain=128, offset=128):
    """
    多尺度Retinex(MSR)图像增强算法

    参数:
    image: 输入图像 (BGR格式)
    sigmas: 高斯滤波器的标准差列表，控制不同尺度的光照估计
    weights: 各尺度结果的权重列表（默认等权重）
    gain: 输出增益，控制增强程度
    offset: 输出偏移量，调整亮度水平

    返回:
    enhanced: 增强后的图像 (BGR格式)
    """
    # 1. 将BGR图像转换为浮点类型并归一化到[0,1]范围
    # 这样便于后续计算，避免整数运算的截断误差
    img_float = image.astype(np.float32) / 255.0

    # 2. 分离图像的三个通道 (B, G, R)
    # Retinex算法需要对每个颜色通道独立处理
    b, g, r = cv2.split(img_float)

    # 3. 初始化各通道的多尺度结果累加器
    # 使用零数组存储每个尺度的Retinex结果之和
    msr_b = np.zeros_like(b)
    msr_g = np.zeros_like(g)
    msr_r = np.zeros_like(r)

    # 4. 设置默认权重（等权重）
    # 如果未提供权重，则使用等权重
    if weights is None:
        weights = [1.0 / len(sigmas)] * len(sigmas)

    # 5. 对每个尺度进行Retinex处理
    # 多尺度Retinex的核心：组合不同尺度的光照估计
    for i, sigma in enumerate(sigmas):
        weight = weights[i]  # 当前尺度的权重

        # 5.1 处理蓝色通道
        log_b = np.log(b + 1e-6)  # 避免log(0)
        blur_b = gaussian_filter(b, sigma=sigma)  # 高斯滤波
        log_blur_b = np.log(blur_b + 1e-6)  # 光照分量对数
        # 单尺度Retinex结果并加权累加
        msr_b += weight * (log_b - log_blur_b)

        # 5.2 处理绿色通道
        log_g = np.log(g + 1e-6)
        blur_g = gaussian_filter(g, sigma=sigma)
        log_blur_g = np.log(blur_g + 1e-6)
        msr_g += weight * (log_g - log_blur_g)

        # 5.3 处理红色通道
        log_r = np.log(r + 1e-6)
        blur_r = gaussian_filter(r, sigma=sigma)
        log_blur_r = np.log(blur_r + 1e-6)
        msr_r += weight * (log_r - log_blur_r)

    # 6. 合并三个通道的多尺度Retinex结果
    # 将各通道的MSR结果组合成彩色图像
    msr = cv2.merge([msr_b, msr_g, msr_r])

    # 7. 对MSR结果进行颜色恢复和对比度调整
    # 7.1 计算原始图像的对数域平均值（用于颜色恢复）
    log_mean = np.log(img_float + 1e-6).mean(axis=(0, 1))

    # 7.2 颜色恢复因子
    # 公式: C = β * (log(I) - log_mean)
    beta = 46  # 颜色恢复强度参数
    color_restore = beta * (np.log(img_float + 1e-6) - log_mean)

    # 7.3 将颜色恢复因子与MSR结果结合
    # 添加颜色恢复项以保持更自然的色彩
    msrcr = msr + color_restore

    # 8. 结果归一化与后处理
    # 8.1 分别对每个通道进行归一化
    # 避免跨通道归一化导致的色彩失真
    for c in range(3):
        channel = msrcr[:, :, c]
        min_val = np.min(channel)
        max_val = np.max(channel)
        # 线性归一化到[0,1]范围
        msrcr[:, :, c] = (channel - min_val) / (max_val - min_val + 1e-6)

    # 8.2 应用增益和偏移调整
    enhanced = msrcr * gain + offset

    # 8.3 将像素值限制在[0,255]范围内
    enhanced = np.clip(enhanced, 0, 255).astype(np.uint8)

    return enhanced

# 示例使用
if __name__ == "__main__":
    # 读取输入图像
    image_path = "input.jpg"
    original = cv2.imread(image_path)

    # 检查图像是否正确加载
    if original is None:
        print(f"错误: 无法读取图像 {image_path}")
        exit()

    # 设置多尺度Retinex参数
    sigmas = [15, 80, 250]  # 三个不同尺度
    weights = [0.3, 0.4, 0.3]  # 各尺度权重
    gain = 200  # 输出增益
    offset = 100  # 输出偏移

    # 应用多尺度Retinex增强
    enhanced = multi_scale_retinex(original, sigmas, weights, gain, offset)

    # 显示原始和增强后的图像
    cv2.imshow("Original Image", original)
    cv2.imshow("Enhanced Image (MSR)", enhanced)

    # 保存结果
    cv2.imwrite("msr_enhanced_output.jpg", enhanced)

    # 等待按键关闭窗口
    cv2.waitKey(0)
    cv2.destroyAllWindows()
```

### 带颜色恢复的多尺度Retinex（MSRCR）

```Python
import cv2
import numpy as np
from scipy.ndimage import gaussian_filter

def msrcr(image, sigmas=[15, 80, 250], weights=None, alpha=125, beta=46, gain=128, offset=128, 
          color_restore_strength=1.0, dynamic_range_adjust=True):
    """
    带颜色恢复的多尺度Retinex(MSRCR)图像增强算法
    
    参数:
    image: 输入图像 (BGR格式)
    sigmas: 高斯滤波器的标准差列表，控制不同尺度的光照估计
    weights: 各尺度结果的权重列表（默认等权重）
    alpha: 颜色恢复的非线性参数（通常125-130）
    beta: 颜色恢复的增益参数（通常40-60）
    gain: 输出增益，控制增强程度
    offset: 输出偏移量，调整亮度水平
    color_restore_strength: 颜色恢复强度系数 (0.0-1.0)
    dynamic_range_adjust: 是否应用动态范围调整
    
    返回:
    enhanced: 增强后的图像 (BGR格式)
    """
    # 1. 将BGR图像转换为浮点类型并归一化到[0,1]范围
    img_float = image.astype(np.float32) / 255.0
    
    # 2. 分离图像的三个通道 (B, G, R)
    b, g, r = cv2.split(img_float)
    
    # 3. 初始化各通道的多尺度结果累加器
    msr_b = np.zeros_like(b)
    msr_g = np.zeros_like(g)
    msr_r = np.zeros_like(r)
    
    # 4. 设置默认权重（等权重）
    if weights is None:
        weights = [1.0 / len(sigmas)] * len(sigmas)
    
    # 5. 对每个尺度进行Retinex处理
    for i, sigma in enumerate(sigmas):
        weight = weights[i]
        
        # 蓝色通道处理
        log_b = np.log(b + 1e-6)
        blur_b = gaussian_filter(b, sigma=sigma)
        log_blur_b = np.log(blur_b + 1e-6)
        msr_b += weight * (log_b - log_blur_b)
        
        # 绿色通道处理
        log_g = np.log(g + 1e-6)
        blur_g = gaussian_filter(g, sigma=sigma)
        log_blur_g = np.log(blur_g + 1e-6)
        msr_g += weight * (log_g - log_blur_g)
        
        # 红色通道处理
        log_r = np.log(r + 1e-6)
        blur_r = gaussian_filter(r, sigma=sigma)
        log_blur_r = np.log(blur_r + 1e-6)
        msr_r += weight * (log_r - log_blur_r)
    
    # 6. 合并三个通道的多尺度Retinex结果
    msr = cv2.merge([msr_b, msr_g, msr_r])
    
    # 7. MSRCR核心 - 颜色恢复处理
    # 7.1 计算每个像素的总强度 (R+G+B)
    total_intensity = r + g + b + 3e-6  # 避免除零
    
    # 7.2 计算颜色恢复因子 (CRF)
    # 公式: CRF = β * [log(α * I_i) - log(∑I)]
    crf_b = beta * (np.log(alpha * b + 1e-6) - np.log(total_intensity))
    crf_g = beta * (np.log(alpha * g + 1e-6) - np.log(total_intensity))
    crf_r = beta * (np.log(alpha * r + 1e-6) - np.log(total_intensity))
    
    # 7.3 合并颜色恢复因子
    crf = cv2.merge([crf_b, crf_g, crf_r])
    
    # 7.4 应用颜色恢复强度系数
    crf = color_restore_strength * crf
    
    # 7.5 将MSR结果与颜色恢复因子结合
    # 经典MSRCR公式: MSRCR = MSR * CRF
    msrcr = msr * crf
    
    # 8. 动态范围调整 (可选)
    if dynamic_range_adjust:
        # 8.1 计算图像的动态范围
        min_vals = np.array([np.min(msrcr[:, :, i]) for i in range(3)])
        max_vals = np.array([np.max(msrcr[:, :, i]) for i in range(3)])
        dynamic_ranges = max_vals - min_vals
        
        # 8.2 计算目标动态范围 (平均动态范围的2倍)
        target_range = 2 * np.mean(dynamic_ranges)
        
        # 8.3 对每个通道进行动态范围调整
        for c in range(3):
            channel = msrcr[:, :, c]
            min_val = min_vals[c]
            max_val = max_vals[c]
            
            # 压缩过大的动态范围
            if (max_val - min_val) > target_range:
                # 线性映射到[0, target_range]范围
                channel = (channel - min_val) * target_range / (max_val - min_val)
                msrcr[:, :, c] = channel
    
    # 9. 结果归一化与后处理
    # 9.1 分别对每个通道进行归一化
    for c in range(3):
        channel = msrcr[:, :, c]
        min_val = np.min(channel)
        max_val = np.max(channel)
        msrcr[:, :, c] = (channel - min_val) / (max_val - min_val + 1e-6)
    
    # 9.2 应用增益和偏移调整
    enhanced = msrcr * gain + offset
    
    # 9.3 将像素值限制在[0,255]范围内
    enhanced = np.clip(enhanced, 0, 255).astype(np.uint8)
    
    return enhanced

# 示例使用
if __name__ == "__main__":
    # 读取输入图像
    image_path = "input.jpg"
    original = cv2.imread(image_path)
    
    # 检查图像是否正确加载
    if original is None:
        print(f"错误: 无法读取图像 {image_path}")
        exit()
    
    # 设置MSRCR参数
    sigmas = [15, 80, 250]      # 三个不同尺度
    weights = [0.3, 0.4, 0.3]   # 各尺度权重
    alpha = 128                 # 颜色恢复非线性参数
    beta = 46                   # 颜色恢复增益参数
    gain = 200                  # 输出增益
    offset = 100                # 输出偏移
    
    # 应用带颜色恢复的多尺度Retinex增强
    enhanced = msrcr(original, sigmas, weights, alpha, beta, gain, offset)
    
    # 显示原始和增强后的图像
    cv2.imshow("Original Image", original)
    cv2.imshow("Enhanced Image (MSRCR)", enhanced)
    
    # 保存结果
    cv2.imwrite("msrcr_enhanced_output.jpg", enhanced)
    
    # 等待按键关闭窗口
    cv2.waitKey(0)
    cv2.destroyAllWindows()
```
