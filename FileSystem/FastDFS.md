# FastDFS

开源的高性能分布式文件系统（DFS）。

## 角色说明

### Tracker Server（跟踪服务器）

- 负责调度和管理
- 记录所有Storage Server的状态
- 接收客户端请求，返回可用的Storage Server
- 不存储实际文件，只存储元数据

### Storage Server（存储服务器）

- 实际存储文件
- 提供文件上传、下载、删除等操作
- 支持分组（Group），同组内服务器互为备份
- 每个组可以独立扩容
- 内置HTTP服务器（通过Nginx模块）

### Client（客户端）

- 集成在应用程序中
- 通过Java API连接FastDFS
- 上传时连接Tracker获取Storage地址
- 下载时可直接连接Storage或HTTP
