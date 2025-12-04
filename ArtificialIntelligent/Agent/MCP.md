# MCP

MCP（Model Context Protocol，模型上下文协议）是一个开放标准，用于定义AI应用程序如何与外部数据源、API和工具进行安全、标准化的通信。

## Stdio（标准输入/输出）

### 核心概念

Stdio是Standard Input/Output的缩写，即标准输入输出。是最经典、最简单的进程间通信方式。一个进程（Client）启动另一个进程（Server），然后通过“管道”连接起来：Client向Server的stdin写入数据，并从Server的stdout读取数据。

### MCP工作原理

MCP Client在启动时，会根据配置直接启动MCP Server的可执行文件。创建了两个单向的信息通道：

- Client -> Server：通过Server的标准输入发送请求。
- Server -> Client：通过Server的标准输出返回响应。

## SSE

SSE（Server-Sent Events），基于HTTP的网络协议，允许服务器将数据“推送”到客户端。建立在标准的HTTP请求之上。

### SSE工作原理

- MCP Client 向一个已知的URL发起一个HTTP GET请求，并在请求头中指明希望接收事件流。
- MCP Server（作为一个HTTP服务器）会保持这个连接处于打开状态，并以一种特定的格式持续地、异步地向Client发送数据块。
- 虽然叫做“Server-Sent”，但这是双向通信的抽象层。在实际的MCP实现中，Client通常会通过额外的HTTP请求（如POST）向Server发送指令。

## 如何选择

- 需要连接一个本地的、简单的工具时，Stdio是更简单、更高效的选择。
- 需要连接一个远程的、共享的或资源密集的服务时，SSE是唯一的选择，因为它允许跨网络连接。
