# Linux常用命令

## 服务器免密登录配置

```sh
scp -p ~/.ssh/id_rsa.pub root@ubuntu:/root/.ssh/id_rsa.pub # 把本机公钥推到服务器上

ssh ubuntu
```

## 修改文件\文件夹名字

```sh
mv file1 file2
```

## zip文件压缩与解压

```sh
zip -q -r xxx.zip filename 
unzip -d /root xxx.zip
```

## 修改文件所有者

```sh
chown [-R] 账号名称 文件或目录
chown [-R] 账号名称:用户组名称 文件或目录
```

## 查询进程

```sh
ps -ef | grep nginx
```

## 查看日志常用

```sh
tail -f xxx.log
tail -n xxx.log
```

## 查找文件

```sh
find -name xxx # 指定字符串作为寻找文件或目录的范本样式
find -ls # 假设find指令的回传值为true，就将文件或目录名称列出到标准输出
```

## 查看端口占用

```sh
lsof -i:端口号 # 查看指定端口的占用
netstat -tunlp | grep 端口号 # 用于显示tcp，udp的端口和进程等相关情况。
	-t # 仅显示tcp相关选项
	-u # 仅显示udp相关选项
	-n # 拒绝显示别名，能显示数字的全部转化为数字
	-l # 仅列出在Listen的服务状态
	-p # 显示建立相关连接的程序名
```

## 显示磁盘空间使用情况

```sh
df -l # 显示磁盘使用情况
	-a # 全部文件系统列表
	-h # 以方便阅读的方式显示信息
	-T # 列出文件系统类型
```

## Ubuntu关闭卡死程序

```sh
gnome-system-monitor
```

## 查找文件位置

```sh
which xxx 
whereis xxx
```

## Linux修改pip指向的python

创建新的pip链接，首先找到pip的可执行文件路径，通常位于Python3.x的安装目录下，例如/usr/local/bin/pip3.9。
更新pip指向，sudo ln -sf /usr/local/bin/pip3.9 /usr/local/bin/pip
验证更改，pip --version
