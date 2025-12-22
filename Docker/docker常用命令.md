# Docker 常用命令

## 服务

### 查看 Docker 版本信息

```bash
docker version
```

### 查看 Docker 简要信息

```bash
docker -v
```

### 启动 / 停止 / 重启 Docker 服务

- 启动 Docker: `systemctl start docker`
- 停止 Docker: `systemctl stop docker`
- 设置开机启动: `systemctl enable docker`
- 重启 Docker 服务: `service docker restart`
- 关闭 Docker 服务: `service docker stop`

## 镜像

- 检索镜像：`docker search <关键字>`
- 拉取镜像：`docker pull [选项] [Docker Registry 地址[:端口号]/]仓库名[:标签]`
- 列出镜像：`docker image ls` 或 `docker images`
- 删除镜像：`docker rmi <镜像id>`
- 将镜像保存为归档文件：`docker save -o <file> <image>`
- 导入镜像：`docker load -i <file>`
- 查看镜像/容器/数据卷所占的空间：`docker system df`

## 容器

### 新建并启动

示例：

```bash
docker run -it -d --name <容器名> -p <宿主机端口>:<容器端口> --restart=always -v <宿主路径>:<容器路径> <镜像> /bin/bash
```

参数说明：

- `-i`：以交互模式启动容器，通常与 `-t` 一起使用
- `-t`：为容器分配伪终端，通常与 `-i` 一起使用
- `-d`：后台运行容器并返回容器 ID
- `--name`：指定容器名称
- `/bin/bash`：交互路径
- `-P`：随机端口映射
- `-p`：指定端口映射（宿主机端口:容器端口）
- `--restart=always`：使容器随 Docker 服务启动而自动启动
- `-v`：数据挂载（可多次使用）

### 启动 / 列出 / 停止 容器

- 启动已终止容器：`docker start <容器id>`
- 列出运行中的容器：`docker ps`
- 列出所有容器（包括历史）：`docker ps -a`
  - `-l`：显示最近创建的容器
  - `-n <num>`：显示最近创建的 n 个容器
  - `-q`：静默模式，只显示容器 ID
- 停止容器：`docker stop <容器id>`
- 强制停止（杀死）容器：`docker kill <容器id>`
- 重启容器：`docker restart <容器id>`

### 删除容器

- 删除单个容器：`docker rm -f <容器id>`
- 删除多个容器：`docker rm -f <id1> <id2> ...`
- 删除全部容器：`docker rm -f $(docker ps -aq)`

### 更换容器名

- 更改容器名：`docker rename <容器id> <新容器名>`

### 进入容器

- 附着到容器：`docker attach <容器id>`（从该 stdin 退出不会导致容器停止）
- 交互式进入容器（推荐）：`docker exec -it <容器id> /bin/bash`
  - 参数：`-d`（后台执行），`-i`（打开标准输入）
- 从主机复制到容器：`docker cp <host_path> <容器id>:<container_path>`
- 从容器复制到主机：`docker cp <容器id>:<container_path> <host_path>`

### 导出 / 导入

- 导出容器文件系统为归档：`docker export <容器id> -o <file>`
- 将导出的快照导入为镜像：`docker import <path_or_url>`

### 查看日志

- 查看容器日志：`docker logs <容器id>`
  - `-f`：跟踪日志输出（类似 `tail -f`）
  - `--since <time>`：显示从某时间点起的日志
  - `-t`：显示时间戳
  - `--tail <n>`：仅查看最新 n 条日志
