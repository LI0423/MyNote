# OE安装手册

## Docker安装

## 镜像下载

[https://hub.docker.com/r/openexplorer/ai_toolchain_ubuntu_20_j5_gpu](https://hub.docker.com/r/openexplorer/ai_toolchain_ubuntu_20_j5_gpu)

## 镜像加载

docker load -i <image_name>

## OE包挂载

## docker 启动

```bash
cd /opt/horizon
bash run_docker.sh data
```

## 模型验证

```bash
cd root@[containerId]:/open_explorer/ddk/samples/ai_toolchain/horizon_model_convert_sample/04_detection/03_yolov5s/mapper/
sh 01_checker.sh
或
hb_mapper checker --model-type onnx --march bernoulli2 --model ball_best.onnx
```

## 修改preprocess.py

```python
def calibration_transformers():
    """
    step：
        1、pad resize to 672 * 672
        2、NHWC to NCHW
        3、bgr to rgb
    """
    transformers = [
        PadResizeTransformer(target_size=(640, 640)), 
        HWC2CHWTransformer(),
        BGR2RGBTransformer(data_format="CHW"),
    ]
    return transformers
```

## 生成校准数据

```bash
sh 02_preprocess.sh
或
python3 data_preprocess.py \
  --src_dir /opt/img_data/ball_data \
  --dst_dir /opt/img_data/ball_data_rgb_f32 \
  --pic_ext .rgb \
  --read_mode opencv \
  --saved_data_type float32
```

## 修改配置文件

## 模型转换

```bash
sh 03_build.sh
```

## 模型测试

```bash
hrt_model_exec perf --model_file ball_640x640_nv12.bin \
                      --model_name="" \
                      --core_id=0 \
                      --frame_count=200 \
                      --perf_time=0 \
                      --thread_num=1 \
                      --profile_path="."
```

## 模型训练和部署

### 环境配置

```bash
# 环境配置
conda create -n yolov5 python=3.7 
conda activate -n yolov5
conda install pytorch torchvision torchaudio pytorch-cuda=11.7 -c pytorch -c nvidia
pip install -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple
pip install apex -i https://pypi.tuna.tsinghua.edu.cn/simple
```

### 拉取代码

```bash
# 拉取yolov5仓库
git clone https://github.com/ultralytics/yolov5 
# 切换tag至v2.0
cd yolov5
git checkout v2.0
```

### 模型训练

```bash
# 使用yolov5s.pt进行模型训练
python train.py --img 640 --batch 16 --epochs 1 --data /data/data.yaml --weights yolov5s.pt
```

### 模型训练报错修复

进行模型训练时会发生报错，需要一一修改

```python
# 在models/common.py中添加以下代码
class C3(nn.Module):
    # CSP Bottleneck with 3 convolutions
    def __init__(self, c1, c2, n=1, shortcut=True, e=0.5):  # ch_in, ch_out, number, shortcut, groups, expansion
        super().__init__()
        c_ = int(c2 * e)  # hidden channels
        self.cv1 = Conv(c1, c_, 1, 1)
        self.cv2 = Conv(c1, c_, 1, 1)
        self.cv3 = Conv(2 * c_, c2, 1)  # act=FReLU(c2)
        self.m = nn.Sequential(*[Bottleneck(c_, c_, shortcut, e=1.0) for _ in range(n)])
        # self.m = nn.Sequential(*[CrossConv(c_, c_, 3, 1, g, 1.0, shortcut) for _ in range(n)])

    def forward(self, x):
        return self.cv3(torch.cat((self.m(self.cv1(x)), self.cv2(x)), dim=1))

class SPPF(nn.Module):
    # Spatial Pyramid Pooling - Fast (SPPF) layer for YOLOv5 by Glenn Jocher
    def __init__(self, c1, c2, k=5):  # equivalent to SPP(k=(5, 9, 13))
        super().__init__()
        c_ = c1 // 2  # hidden channels
        self.cv1 = Conv(c1, c_, 1, 1)
        self.cv2 = Conv(c_ * 4, c2, 1, 1)
        self.m = nn.MaxPool2d(kernel_size=k, stride=1, padding=k // 2)

    def forward(self, x):
        x = self.cv1(x)
        y1 = self.m(x)
        y2 = self.m(y1)
        y3 = self.m(y2)
        return self.cv2(torch.cat([x, y1, y2, y3], 1))

# 修改yolo.py的forward方法中代码，由 
# x[i] = x[i].view(bs, self.na, self.no, ny, nx).permute(0, 1, 3, 4, 2).contiguous()
# 改为以下代码
x[i] = x[i].permute(0, 2, 3, 1).contiguous()

# 修改yolo.py的_initialize_biases方法中的代码，在
# b[:, 4] += math.log(8 / (640 / s) ** 2)  # obj (8 objects per 640 image)
# b[:, 5:] += math.log(0.6 / (m.nc - 0.99)) if cf is None else torch.log(cf / cf.sum())  # cls
# 这两行代码之前添加：
with torch.no_grad():
    b[:, 4] += math.log(8 / (640 / s) ** 2)  # obj (8 objects per 640 image)
    b[:, 5:] += math.log(0.6 / (m.nc - 0.99)) if cf is None else torch.log(cf / cf.sum())  # cls
mi.bias = torch.nn.Parameter(b.view(-1), requires_grad=True)

# 修改utils.py的build_targets中的代码，由
# at = torch.arange(na).view(na, 1).repeat(1, nt)  # anchor tensor, same as .repeat_interleave(nt)
# 改为以下代码
at = torch.tensor(torch.arange(na).view(na, 1).repeat(1, nt),device=targets.device)

# 修改utils.py的output_to_target中的代码，由
# for pred in o:
# 改为以下代码
for pred in o.cpu():
```

## 模型导出为ONNX

### 依赖安装

```bash
pip  install onnx==1.7 -i https://pypi.tuna.tsinghua.edu.cn/simple
```

### 修改代码

```python
# export.py文件第48行
torch.onnx.export(model, img, f, verbose=False, opset_version=11, input_names=['images'],
                          output_names=['classes', 'boxes'] if y is None else ['output'])

# yolo.py文件第29行
x[i] = x[i].permute(0, 2, 3, 1).contiguous()
```

### 执行导出命令

```bash
export PYTHONPATH="$PWD"
python models/export.py --weights runs/exp28/weights/best.pt --img-size 640 --batch-size 1
```

## 模型量化

```bash
# 进入工具链文件夹
cd horizon

# 启动docker容器
bash run_docker.sh data

# 构建校准数据
mkdir img_data
sudo docker cp /opt/img_data/ 1ff56ccc3634:/opt/img_data/
sudo docker cp ~/Desktop/models/ball_best.onnx 1ff56ccc3634:/opt/

# 进入量化文件夹
cd /opt/horizon/ddk/samples/ai_toolchain/horizon_model_convert_sample/04_detection/03_yolov5s/mapper

# 修改校准数据路径
vim 02_preprocess.sh
修改 --src_dir /opt/img_data/ball_data

# 前处理指定图片大小
vim preprocess.py
修改  PadResizeTransformer(target_size=(640, 640))

# 执行生成校准数据
sh 02_preprocess.sh

# 修改量化配置文件
vim yolov5s_config.yaml
修改 onnx_model: '/opt/ball_best.onnx'
修改 output_model_file_prefix: 'ball_640x640_nv12'

# 量化编译
sh 03_build.sh

# 量化模型推理（可以不执行）
vim 04_inference.sh
修改 quanti_model_file="./model_output/ball_640x640_nv12_quantized_model.onnx"
修改 original_model_file="./model_output/ball_640x640_nv12_original_float_model.onnx"
修改 infer_image="/opt/img_data/ball_data/00000002_jpg.rf.b77ed24c9ba76e519914281996fe34f5.jpg"
sh 04_inference.sh

# 量化模型导出
cd model_output
sudo docker cp ball_best.onnx 1ff56ccc3634:/open_explorer/ddk/samples/ai_toolchain/horizon_model_convert_sample/04_detection/03_yolov5s/mapper/model_output/ball_640x640_nv12.bin /opt/

```

## 上板使用

```bash
复制 https://github.com/D-Robotics/rdk_model_zoo/blob/rdk_x3/demos/detect/YOLOv5/YOLOv5_Detect.py

修改 --model-path
修改 --test-img
修改 YOLOv5_Detect self.nc = 类别数
修改 coco_names = [所有类别名称]
修改 rdk_colors = [所有类别画线颜色]

python3 yolov5_Detect.py
```
