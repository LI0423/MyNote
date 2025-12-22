# Conda 常用命令

## 版本与信息

- 查询 Conda 版本：

```bash
conda --version
```

- 更新 Conda：

```bash
conda update conda
```

- 查看 Conda 环境详细信息：

```bash
conda info
```

## 环境（虚拟环境）

- 查看当前虚拟环境列表：

```bash
conda env list
conda info --envs
```

- 创建新的虚拟环境：

```bash
conda create -n <env_name> python=3.9
```

- 激活虚拟环境：

```bash
conda activate <env_name>
```

- 退出虚拟环境：

```bash
conda deactivate
```

- 删除某个虚拟环境：

```bash
conda remove -n <env_name> --all
```

- 复制（克隆）虚拟环境：

```bash
conda create -n <new_env_name> --clone <old_env_name>
```

- 分享 / 备份环境：

```bash
conda env export > environment.yml
conda env create -f environment.yml
```

## 包管理

- 安装包：

```bash
conda install <package>
conda install xlrd=1.2.0
# 或使用 pip
pip install xlrd==1.2.0
```

- 批量导出依赖：

```bash
conda list -e > requirements.txt
```

- 删除包：

```bash
conda remove <package>
```

- 升级包：

```bash
conda update <package>
```

- 升级所有包：

```bash
conda update --all
```

- 搜索包：

```bash
conda search <package>
```

- 清理无用文件 / 包 / 缓存：

```bash
conda clean -p            # 删除未使用的包缓存
conda clean --packages    # 删除未使用的包
conda clean -t            # 删除 tar 包
conda clean --tarballs
conda clean -y --all      # 删除所有安装包及 cache
```

## 镜像源与配置

- 查看当前 channels：

```bash
conda config --show channels
```

- 添加镜像源（示例：清华镜像）：

```bash
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main
```

- 在安装包时显示来源：

```bash
conda config --set show_channel_urls yes
```

- 清除索引缓存（确保使用镜像站提供的索引）：

```bash
conda clean -i
```

- 切回默认源：

```bash
conda config --remove-key channels
```

- 移除镜像源：

```bash
conda config --remove channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free
```

- 临时指定 pip 安装源（示例）：

```bash
pip install <package> -i https://mirrors.tuna.tsinghua.edu.cn/simple/
pip install <package> -i https://pypi.douban.com/simple/ --trusted-host pypi.douban.com
```
