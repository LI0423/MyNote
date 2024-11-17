FROM ubuntu:20.04

# 设置环境变量
ENV PYTHON_VERSION=3.12.0

# 禁用交互模式的提示，并更新包管理器
ENV DEBIAN_FRONTEND=noninteractive

# 更新包管理器，安装构建python需要的依赖
RUN apt-get update && apt-get install -y \
    wget \
    build-essential \
    libssl-dev \
    zlib1g-dev \
    libbz2-dev \
    libreadline-dev \
    libsqlite3-dev \
    libffi-dev \
    curl \
    libncursesw5-dev \
    xz-utils \
    tk-dev \
    libxml2-dev \
    libxmlsec1-dev \
    liblzma-dev \
    ca-certificates

# 下载并安装指定版本的Python
RUN wget https://www.python.org/ftp/python/$PYTHON_VERSION/Python-$PYTHON_VERSION.tgz
RUN tar xzf Python-$PYTHON_VERSION.tgz && \
    cd Python-$PYTHON_VERSION && \
    ./configure --enable-optimizations && \
    make -j$(nproc) && \
    make altinstall

# 清理不需要的文件
RUN rm -rf /Python-${PYTHON_VERSION}

# 设置python的软链接
RUN ln -s /usr/local/bin/python3.12 /usr/local/bin/python

# 安装pip
RUN wget https://bootstrap.pypa.io/get-pip.py
RUN /usr/local/bin/python3.12 get-pip.py

# 删除get-pip.py文件
RUN rm get-pip.py

# 验证Python和pip安装
RUN /usr/local/bin/python3.12 --version
RUN /usr/local/bin/pip3 --version

# 设置默认的ENTRYPOINT
CMD ["/usr/local/bin/python3.12"]