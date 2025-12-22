# Docker

## 什么是docker

docker是一个开源的应用容器引擎，基于go语言开发。docker可以让开发者打包他们的应用以及依赖包到一个轻量级、可移植的容器中，然后发布到任何流行的linux服务器，也可以实现虚拟化。容器是完全使用沙箱机制，相互之间不会有任何接口，并且容器开销极低。

## docker越来越受欢迎

### 镜像和容器

- 通过镜像启动一个容器，一个镜像是一个可执行的包，其中包括运行应用程序所需要的所有内容包含代码，运行时间，库、环境变量和配置文件。
- 容器是镜像的运行实例，当被运行时有镜像状态和用户进程，可以使用docker ps查看。

### 容器和虚拟机

- 容器在linux上本机运行，并于其他容器共享主机的内核，它与行的一个独立的进程，不占用其他任何可执行文件的内存，非常轻量。
- 虚拟机运行的是一个完成的操作系统，通过虚拟机管理程序对主机资源进行虚拟访问，相比之下需要的资源更多。

## 容器在内核中支持2项重要技术

docker本质就是宿主机的一个进程，docker是通过namespace实现资源隔离，通过cgroup实现资源限制，通过写时复制技术（copy-on-write）实现了高效的文件操作（类似虚拟机的磁盘比如分配500g并不是实际占用物理磁盘500g）

## docker三个重要概念

### image镜像

- docker镜像就是一个只读模版，比如，一个镜像可以包含一个完整的centos，里面仅安装apache或用户的其他应用，镜像可以用来创建docker容器，
- 另外docker提供了一个很简单的机制来创建镜像或者更新现有的镜像，用户甚至可以直接从其他人那里下载一个已经做好的镜像来直接使用。

### container容器

docker利用容器来运行应用，容器是从镜像创建的运行实例，可以被启动、开始、停止、删除、每个容器都是互相隔离的，保证安全的平台，可以把容器看作是简易版的linux环境（包括root用户权限、镜像空间、用户空间和网络空间等）。

### repository仓库

- 仓库是集中存储镜像文件的，registry是仓库主从服务器，实际上注册服务器上存放着多个仓库，每个仓库中包含了多个镜像，每个镜像有不同的标签。
- 仓库分为两种，公有仓库和私有仓库，最大的公开仓库是docker Hub，存放了数量庞大的镜像供用户下载，国内的docker pool，这里仓库的概念与git类似，registry可以理解为GitHub这样的托管服务。

## docker架构

[参考文章 - cloud.tencent.com](https://cloud.tencent.com/developer/article/1533958)

### 架构1

- distribution 负责与docker registry交互，上传镜像以及V2 registry有关的源数据。
- registry 负责与docker registry有关的身份认证、镜像查找、镜像验证以及管理registry mirror等交互操作。
- image 负责与镜像源数据有关的存储、查找、镜像层的索引、查找以及镜像tar包有关的导入、导出操作。
- reference 负责存储本地所有镜像的repository和tag名，并维护与镜像id之间的映射关系。
- layer 模块负责与镜像层和容器层源数据有关的增删改查，并负责将镜像层的增删改查映射到实际存储镜像层文件的graphdriver模块。
- graphdriver 是所有与容器镜像相关操作的执行者。

### 架构2

- 而 Docker Daemon 作为 Docker 架构中的主体部分，首先提供 Server 的功能使其可以接受 Docker Client 的请求；而后 Engine 执行 Docker内部的一系列工作，每一项工作都是以一个 Job 的形式的存在。
- Job 的运行过程中，当需要容器镜像时，则从 Docker Registry 中下载镜像，并通过镜像管理驱动 graphdriver 将下载镜像以 Graph 的形式存储；
- 当需要为 Docker 创建网络环境时，通过网络管理驱动 networkdriver 创建并配置 Docker 容器网络环境；当需要限制 Docker 容器运行资源或执行用户指令等操作时，则通过 execdriver 来完成。
- 而 libcontainer 是一项独立的容器管理包，networkdriver 以及 execdriver 都是通过 libcontainer 来实现具体对容器进行的操作。当执行完运行容器的命令后，一个实际的 Docker 容器就处于运行状态，该容器拥有独立的文件系统，独立并且安全的运行环境等。

#### docker client

- docker client是docker架构中用户用来和docker daemon建立通信的客户端，用户使用的可执行文件为docker，通过docker命令行工具可以发起众多管理container的请求。可以使用三种方式和docker daemon建立通信：
  - tcp://host:port
  - unix:path_to_socket
  - fd://socketfd
- docker client可以通过设置命令行flag参数的形式设置安全传输层协议（TLS）的有关参数，保证传输的安全性。
- docker client发送容器管理请求后，由docker daemon接受并处理请求，当docker client接收到返回的请求响应并简单处理后，docker client一次完整的生命周期就结束了，当需要继续发送容器管理请求时，用户必须再次通过docker可执行文件创建docker client。

#### docker daemon

- docker daemon是docker架构中一个常驻在后台的系统进程，接收处理docker client发送的请求。该守护进程在后台启动一个sever，接收请求后，server通过路由与分发调度，找到相应的handler来执行请求。
- docker daemon启动所使用的可执行文件也为docker，与docker client启动所使用的可执行文件docker相同，在docker命令执行时，通过传入的参数来判别docker daemon与docker client。

#### docker server

- docker server在docker架构中时专门服务于docker client的server，接受并调度分发docker clietn发送的请求。
- 在 Docker 的启动过程中，通过包 gorilla/mux（golang 的类库解析），创建了一个 mux.Router，提供请求的路由功能。在 Golang 中，gorilla/mux 是一个强大的 URL 路由器以及调度分发器。该 mux.Router 中添加了众多的路由项，每一个路由项由HTTP请求方法（PUT、POST、GET或DELETE）、URL、Handler 三部分组成。
- 若 Docker Client 通过 HTTP 的形式访问 Docker Daemon，创建完 mux.Router 之后，Docker 将 Server 的监听地址以及 mux.Router 作为参数，创建一个 httpSrv=http.Server{}，最终执行 httpSrv.Serve() 为请求服务。
- 在 Server 的服务过程中，Server 在 listener 上接受 Docker Client 的访问请求，并创建一个全新的 goroutine 来服务该请求。在 goroutine 中，首先读取请求内容，然后做解析工作，接着找到相应的路由项，随后调用相应的 Handler 来处理该请求，最后 Handler 处理完请求之后回复该请求。
- 需要注意的是：Docker Server 的运行在 Docker 的启动过程中，是靠一个名为 serveapi 的 job 的运行来完成的。原则上，Docker Server 的运行是众多job 中的一个，但是为了强调 Docker Server 的重要性以及为后续 job 服务的重要特性，将该 serveapi 的 job 单独抽离出来分析，理解为 Docker Server。

#### engine

- engine是docker架构中的运行引擎，同时也是docker运行的核心模块，扮演docker container存储仓库的角色，并且通过执行job的方式来操纵管理这些容器。
- 在Engine数据结构的设计与实现过程中，有一个handler对象。该handler对象存储的都是关于众多特定job的handler处理访问。

#### job

- 一个job可以认为是docker架构中engine内部最基本的工作执行单元。docker可以做的每一项工作，都可以抽象为一个job。例如：在容器内部运行一个进程，这是一个 job；
- 创建一个新的容器，这是一个 job，从 Internet 上下载一个文档，这是一个 job；包括之前在 Docker Server 部分说过的，创建 Server 服务于 HTTP 的 API，这也是一个 job。

#### docker registry

- docker registry 是一个存储容器镜像的仓库，而容器镜像是在容器被创建时，被加载用来初始化容器的文件架构与目录。
- 在docker运行过程中，docker daemon会与docker registry通信，并实现搜索镜像、下载镜像、上传镜像三个功能，这三个功能对应的job名称分别为search，pull与push。

#### graph

- graph在docker架构中扮演已下载容器镜像的保管者，以及已下载容器镜像之间关系的记录者。一方面graph存储着本地具有版本信息的文件系统镜像，另一方面也通过graphDB记录着所有文件系统镜像彼此之间的关系。
- graphDB是一个构建在SQLite之上的小型图数据库，实现了节点的命名以及节点之间的关联关系的记录。它仅仅实现了大多数图数据库所拥有的一个小的子集，但是提供了简单的接口表示节点之间的关系。
- 在 Graph 的本地目录中，关于每一个的容器镜像，具体存储的信息有：该容器镜像的元数据，容器镜像的大小信息，以及该容器镜像所代表的具体 rootfs。

#### driver

#### libcontainer

#### docker container

- docker container是docker架构中服务交付的最终体现形式。
- docker按照用户的需求与指令，定制相应的docker容器：
  - 用户通过指定容器镜像，使得docker容器可以自定义rootfs等文件系统；
  - 用户通过指定计算资源的配额，使得docker容器使用指定的计算资源；
  - 用户通过配置网络及其安全策略，使得docker容器拥有独立且安全的网络环境；
  - 用户通过指定运行的命令，使得docker容器执行指定的工作。

### 架构3

- docker daemon 就是 docker 的守护进程即 server 端，可以是远程的，也可以是本地的，这个不是 C/S 架构吗，客户端 Docker client 是通过 rest api 进行通信。
- docker cli 用来管理容器和镜像，客户端提供一个只读镜像，然后通过镜像可以创建多个容器，这些容器可以只是一个 RFS（Root file system根文件系统），也可以是一个包含了用户应用的 RFS，容器再 docker client 中只是要给进程，两个进程之间互不可见。
- 用户不能与 server 直接交互，但可以通过与容器这个桥梁来交互，由于是操作系统级别的虚拟技术，中间的损耗几乎可以不计。

docker 虚悬镜像是什么
    仓库名、标签都是none的镜像，dangling image

## 容器间的通信

### 通过虚拟ip直接访问

- 安装docker后，docker会默认搭建一个docker0的网络，后续每个容器就是网络中的一个节点，每个节点会有属于网络中的一个虚拟ip，可以利用虚拟ip直接访问。
  1. 进入容器，通过ifconfig或inspect查看容器ip，然后使用ping和curl实现互相访问。
        `docker exec -it container_name /bin/bash`
  2. 如果container 没有ifconfig，先进行安装
        `apt update`
        `apt install net-tools -y`
        `ifconfig`
- 通过link方式指定要连接的容器。本质上还是使用docker0网络，修改host文件的ip映射来实现网络通信，并且随着ip的变化还可以动态维护这种映射关系。
  1. 使用 --link 指定连接容器
        `docker run -d --name container_name --link container_name2:container_nickname imgId`
  2. 进入容器，通过ifconfig或inspect查看容器ip。
        `docker exec -it container_name /bin/bash`
        `ifconfig`
  3. 使用ip或网络别名来访问容器
        `ping 172.17.0.2`
        `ping container_nickname`
    这种方式存在局限性，只能单向请求，两个容器无法互相连接请求对方。因为link配置是在容器启动时生成好的，只能后面的容器单项请求前边的容器。
- 创建bridge网络，实现容器互联
  1. 创建bridge网络
        `docker network create network_name`
        `docker network ls`
  2. 创建容器并加入到bridge网络中
        `docker run -p port:port -d --name container_name --network network_name --network-alias container_nickname imgId`
  3. 进入容器，并访问其他容器
        `docker exec -it container_name /bin/bash`
    这种方式相对灵活，不需要提前规划，并且可以实现网状的网络通信请求。
