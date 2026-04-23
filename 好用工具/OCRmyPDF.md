# OCRmyPDF 使用手册

## 工具简介

ocrmypdf 是一个把普通 PDF 或图片文件转换为 可搜索、可复制文字的 PDF 的命令行工具。

它的核心流程是：

- 读取输入 PDF 或图片
- 将页面图像化处理
- 调用 OCR 引擎识别文字
- 把识别结果写回 PDF，生成带文字层的新文件

生成后的 PDF 一般具备这些能力：

- 可以复制文字
- 可以全文搜索
- 可以被部分阅读器索引
- 可额外导出纯文本文件

根据帮助说明，它的用途就是“从普通 PDF 生成 searchable PDF 或 PDF/A”。

## 基本语法

`ocrmypdf [参数] 输入文件 输出文件`

最简单示例：

```bash
ocrmypdf input.pdf output.pdf
```

含义：

- `input.pdf`：原始 PDF
- `output.pdf`：OCR 后的新 PDF

如果输入的是图片，也可以：

```bash
ocrmypdf scan.png output.pdf
```

如果输入图片没有正确 DPI 信息，通常要配合 `--image-dpi` 使用。

## 常用场景快速上手

### 扫描版 PDF 转可复制文字 PDF

```bash
ocrmypdf -l chi_sim input.pdf output.pdf
```

说明：

- `-l chi_sim`：使用简体中文 OCR

### 中英混合文档 OCR

```bash
ocrmypdf -l chi_sim+eng input.pdf output.pdf
```

说明：

- `chi_sim+eng`：简体中文 + 英文混合识别

### 页面可能有歪斜、方向不正

```bash
ocrmypdf -l chi_sim+eng --rotate-pages --deskew input.pdf output.pdf
```

说明：

- `--rotate-pages`：自动旋转页面
- `--deskew`：自动纠偏

### 文档质量较差，带噪点或背景脏污

```bash
ocrmypdf -l chi_sim+eng --rotate-pages --deskew --clean input.pdf output.pdf
```

说明：

- `--clean`：清理扫描伪影和噪点，用于改善 OCR 识别效果

### PDF 中有些页已经有文字层，有些页没有

```bash
ocrmypdf -l chi_sim+eng --skip-text input.pdf output.pdf
```

说明：

- `--skip-text`：跳过已有文字的页面，只 OCR 图片页

这个参数特别适合“混合型 PDF”。

### 同时导出纯文本

```bash
ocrmypdf -l chi_sim+eng --sidecar output.txt input.pdf output.pdf
```

说明：

- `--sidecar output.txt`：额外导出 OCR 识别出的纯文本文件

### 重做旧 OCR

```bash
ocrmypdf -l chi_sim+eng --redo-ocr input.pdf output.pdf
```

说明：

- `--redo-ocr`：如果原 PDF 已有旧的 OCR 文字层，但效果差，可以尝试重新识别

## 参数分类说明

### 输入输出参数

input_pdf_or_image

输入文件，可以是：

- PDF
- 图片文件
- -，表示从标准输入读取

output_pdf

输出 PDF 文件，可以是：

- 新 PDF 文件路径
- -，表示写到标准输出

### 语言参数

`-l, --language LANGUAGES`

指定 OCR 语言。

示例：

```bash
-l chi_sim
-l eng
-l chi_sim+eng
```

作用：

- `chi_sim`：简体中文
- `eng`：英文
- 多语言用 `+` 连接

建议：

- 中文文档：`chi_sim`
- 英文文档：`eng`
- 中英混排：`chi_sim+eng`
  
### 输出格式参数

`--output-type {auto,pdfa,pdf,pdfa-1,pdfa-2,pdfa-3,none}`

控制输出文件类型。

常见值：

- `auto`：默认，自动选择最佳输出方式
- `pdf`：尽量减少对原文件改动
- `pdfa` / `pdfa-2`：输出为适合长期归档的 PDF/A
- `none`：不输出 PDF，通常配合其他用途

示例：

```bash
ocrmypdf --output-type pdf input.pdf output.pdf
```

适用场景：

- 追求兼容归档：用默认或 `pdfa`
- 追求尽量少改动原始 PDF：用 `pdf`

### 文本导出参数

`--sidecar [FILE]`

生成 OCR 文本文件。

示例：

```bash
ocrmypdf --sidecar out.txt input.pdf output.pdf
```

作用：

- `output.pdf`：OCR 后的 PDF
- `out.txt`：OCR 识别出的纯文本

如果不写文件名，默认使用输出 PDF 名称加 `.txt`。

### 覆盖控制参数

`-n, --no-overwrite`

如果输出文件已存在，则报错，不覆盖。

示例：

```bash
ocrmypdf -n input.pdf output.pdf
```

适合：

防止误覆盖已有结果文件

### 并行与日志参数

`-j, --jobs N`

使用多少个 CPU 核心并行处理。

示例：

```bash
ocrmypdf -j 4 input.pdf output.pdf
```

`-q, --quiet`

安静模式，减少输出信息。

`-v, --verbose [VERBOSE]`

显示更详细的日志。

示例：

```bash
ocrmypdf -v 1 input.pdf output.pdf
```

适合排错时使用。

### 图像预处理参数

这组参数会直接影响 OCR 效果。

`-r, --rotate-pages`

自动检测页面方向并旋转。

适合：

- 扫描件有倒置页
- 横向扫描页
- 拍照版 PDF

`--rotate-pages-threshold CONFIDENCE`

设置自动旋转的置信度阈值。

适合：

自动旋转误判时手动调节

`-d, --deskew`

自动纠正页面歪斜。

适合：

- 扫描时纸张放歪
- 文本略微倾斜

`-c, --clean`

清理图像噪点，只用于 OCR 识别，不影响最终页面外观。

适合：

- 页面有黑点
- 杂线较多
- 想提高识别率，但不想改页面显示

`-i, --clean-final`

清理图像噪点，并把清理后的页面写入最终 PDF。

适合：

不仅想识别更准，也希望最终 PDF 视觉更干净

注意：

可能误删部分细节内容

`--remove-background`

尝试去掉灰色或彩色背景，使背景更接近白色。

适合：

- 老旧纸张扫描
- 发黄底色
- 彩色噪声背景

`--oversample DPI`

将图像放大到至少指定 DPI 后再做 OCR。

示例：

```bash
ocrmypdf --oversample 300 input.pdf output.pdf
```

适合：

- 原始扫描分辨率太低
- 小字识别困难

`--remove-vectors`

实验性功能，尝试屏蔽 PDF 中的矢量对象，避免误识别。

适合：

- 页面上有复杂线条或图形
- OCR 把图形识别成乱码字符

### OCR 处理模式参数

`-m, --mode {default,force,skip,redo}`

控制遇到“已有文字的 PDF 页面”时如何处理。

#### default

默认模式。

作用：

如果检测到页面已有文字，程序可能直接退出报错

适合：

你确定输入就是纯扫描 PDF

#### force

强制 OCR 整页。

等价于：

`--force-ocr`

作用：

不管页面是否已有文字，都先栅格化，再重新 OCR

适合：

- 原 PDF 文字层有问题
- 可复制出来的是乱码
- 想彻底重做文字层

注意：

会对页面内容进行重建
可能让原始矢量文本变成图像风格

#### skip

跳过已有文字页。

等价于：

`--skip-text`

作用：

页面如果已有文字层，就不 OCR
只有图片页会被识别

适合：

混合型 PDF

#### redo

重做已有 OCR。

等价于：

`--redo-ocr`

作用：

尝试移除旧的隐藏 OCR 层，重新识别

适合：

PDF 以前做过 OCR，但识别质量差

`-f, --force-ocr`

强制对所有页 OCR。

示例：

```bash
ocrmypdf --force-ocr input.pdf output.pdf
```

`-s, --skip-text`

跳过已有文字页。

示例：

```bash
ocrmypdf --skip-text input.pdf output.pdf
```

`--redo-ocr`

重做 OCR。

示例：

```bash
ocrmypdf --redo-ocr input.pdf output.pdf
```

`--skip-big MPixels`

当某页图像超过指定百万像素时跳过 OCR，但保留页面。

示例：

```bash
ocrmypdf --skip-big 20 input.pdf output.pdf
```

适合：

- 超高分辨率页面
- 避免程序过慢或内存占用过高

`--pages PAGES`

只处理指定页面。

示例：

```bash
ocrmypdf --pages 1-5 input.pdf output.pdf
ocrmypdf --pages 1,3,7 input.pdf output.pdf
```

适合：

- 只处理部分页面
- 先测试几页效果

### Tesseract 相关参数

OCRmyPDF 底层会调用 Tesseract，以下参数用于控制 Tesseract 行为。

`--tesseract-pagesegmode PSM`

设置页面分割模式。

作用：

告诉 OCR 引擎页面是整页、多列、单行还是其他结构

适合：

特殊排版文档调优

通常：

普通文档不用改

`--tesseract-oem MODE`

设置 OCR 引擎模式。

帮助说明中可选值：

- 0：传统引擎
- 1：LSTM 引擎
- 2：传统 + LSTM
- 3：默认

通常保持默认即可。

`--tesseract-thresholding`

设置 Tesseract 图像阈值处理模式。

可选：

- auto
- otsu
- adaptive-otsu
- sauvola

适合：

- 低质量扫描件调优
- 背景不均匀页面

`--tesseract-timeout SECONDS`

设置 OCR 超时时间。

作用：

如果某页识别太久，到时间就放弃 OCR，但页面仍可进入结果文件

`--tesseract-non-ocr-timeout SECONDS`

设置非 OCR 操作超时时间。

适用：

- 页面方向检测
- 纠偏
- 其他辅助步骤

`--tesseract-downsample-large-images`

当图像过大时先缩小后再 OCR。

适合：

超大图像页面
防止 Tesseract 无法处理

`--tesseract-downsample-above PIXELS`

当图像超过指定尺寸时，自动缩小后 OCR。

`--user-words FILE`

使用自定义词典。

适合：

- 大量专业术语
- 专有名词较多
- OCR 总把专业词识错

`--user-patterns FILE`

使用自定义识别模式文件。

适合：

高级定制场景
### 输入图像与渲染参数

`--image-dpi DPI`

当输入是图片而不是 PDF 时，指定图像 DPI。

示例：

```bash
ocrmypdf --image-dpi 300 scan.jpg output.pdf
```

适合：

- 图片本身没有正确 DPI 信息
- OCR 结果偏差较大

`--max-image-mpixels MPixels`

限制单张图像解压后允许的最大像素。

作用：

防止超大图像导致资源问题

`--pdf-renderer {auto,hocr,sandwich,hocrdebug,fpdf2}`

选择 OCR 结果写入 PDF 的方式。

通常：

用默认 `auto` 即可

帮助信息指出，`fpdf2` 对国际语言支持更好。

`--ocr-engine {auto,tesseract,none}`

指定 OCR 引擎。

通常：

- `auto`：自动
- `tesseract`：显式指定 Tesseract
- `none`：不做 OCR，仅做其他处理

`--rasterizer {auto,ghostscript,pypdfium}`

选择 PDF 页面光栅化工具。

通常：

`auto` 即可

### 优化与压缩参数

`-O, --optimize {0,1,2,3}`

控制优化等级。

- 0：不优化
- 1：安全、无损优化
- 2：有损压缩
- 3：更激进有损压缩

示例：

```bash
ocrmypdf -O 2 input.pdf output.pdf
```

适合：

想减小输出文件体积

注意：

等级越高，图像质量可能越差

`--jpeg-quality Q`

设置 JPEG 压缩质量。

`--png-quality Q`

设置 PNG 压缩质量。

`--jbig2-threshold T`

设置 JBIG2 符号分类阈值。

适合高级调优场景。

`--color-conversion-strategy`

设置颜色转换策略。

可选项包括：

- CMYK
- Gray
- LeaveColorUnchanged
- RGB
- UseDeviceIndependentColor

一般默认即可。

`--pdfa-image-compression {auto,jpeg,lossless}`

设置 PDF/A 输出时图像压缩方式。

常见取值：

- `auto`
- `jpeg`
- `lossless`

### 其他参数

`--invalidate-digital-signatures`

如果 PDF 有数字签名，默认 OCR 可能拒绝处理。
使用该参数可强制继续，但数字签名会失效。

`--tagged-pdf-mode {default,ignore}`

控制遇到 Tagged PDF 时的处理方式。

一般保持默认即可。

`--fast-web-view MEGABYTES`

当文件大于指定大小时，为 PDF 做线性化，方便网页快速查看。

适合：

- 上传到 Web 端预览

`--continue-on-soft-render-error`

遇到可恢复渲染错误时继续处理。

适合：

- PDF 页面部分字体缺失
- 渲染有警告但不影响继续处理

`--plugin PLUGINS`

导入插件。

适合：

- 二次开发
- 高级扩展

`-k, --keep-temporary-files`

保留临时文件，方便调试。

适合：

- 分析处理失败原因
- 调试 OCR 效果

## 推荐命令模板

### 模板 1：普通中文扫描 PDF

```bash
ocrmypdf -l chi_sim input.pdf output.pdf
```

### 模板 2：中英混排 PDF

```bash
ocrmypdf -l chi_sim+eng input.pdf output.pdf
模板 3：质量一般，建议自动旋转 + 纠偏
ocrmypdf -l chi_sim+eng --rotate-pages --deskew input.pdf output.pdf
```

### 模板 4：质量差，建议加清理

```bash
ocrmypdf -l chi_sim+eng --rotate-pages --deskew --clean input.pdf output.pdf
```

### 模板 5：混合 PDF，跳过已有文字页

```bash
ocrmypdf -l chi_sim+eng --skip-text --rotate-pages --deskew --clean input.pdf output.pdf
```

### 模板 6：同时导出文本

```bash
ocrmypdf -l chi_sim+eng --skip-text --rotate-pages --deskew --clean --sidecar output.txt input.pdf output.pdf
```

### 模板 7：重做已有 OCR

```bash
ocrmypdf -l chi_sim+eng --redo-ocr input.pdf output.pdf
```

### 模板 8：只测试前 5 页

```bash
ocrmypdf -l chi_sim+eng --pages 1-5 input.pdf output.pdf
```

## 常见问题与建议

### 为什么识别不准？

常见原因：

- 没指定正确语言
- 原始扫描质量差
- 页面歪斜
- 分辨率太低

建议尝试：

```bash
ocrmypdf -l chi_sim+eng --rotate-pages --deskew --clean --oversample 300 input.pdf output.pdf
```

### 为什么报“文件已存在”？

可能使用了：

`--no-overwrite`

或者输出文件本身已经存在。

处理方法：

- 删除旧文件
- 或换一个输出文件名

### 为什么遇到已有文字的 PDF 会失败？

默认模式下，如果页面已有文本，程序可能报错退出。

解决方法：

- 跳过已有文字页：`--skip-text`
- 或重做 OCR：`--redo-ocr`
- 或强制整页 OCR：`--force-ocr`

### 为什么输出体积变大了？

因为 OCR 后可能新增文字层、重新压缩或重建图像。

可以尝试：

```bash
ocrmypdf -O 2 input.pdf output.pdf
```

或者：

```bash
ocrmypdf -O 3 input.pdf output.pdf
```

但图像质量可能会下降。

### 为什么中文识别失败？

先检查 Tesseract 是否安装了中文语言包：

```bash
tesseract --list-langs
```

确认是否存在：

`chi_sim`

如果没有，需要额外安装中文语言数据。

## 最推荐的日常命令

如果你平时处理的大多是中文或中英混合扫描 PDF，建议默认先用这条：

```bash
ocrmypdf -l chi_sim+eng --skip-text --rotate-pages --deskew --clean input.pdf output.pdf
```

如果还想顺便导出文本：

```bash
ocrmypdf -l chi_sim+eng --skip-text --rotate-pages --deskew --clean --sidecar output.txt input.pdf output.pdf
```

## 速查总结

最常用的 6 个参数：

- `-l chi_sim+eng`：设置识别语言
- `--skip-text`：跳过已有文字页
- `--rotate-pages`：自动旋转
- `--deskew`：自动纠偏
- `--clean`：去噪清理
- `--sidecar output.txt`：导出文本
