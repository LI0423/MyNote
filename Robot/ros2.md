# ROS（Robot Operation System）

## 文件结构

### 功能包

    ```
    # 创建功能包
    mkdir -r ~/dev_ws/src
    cd ~/dev_ws/src
    ros2 pkg create --build-type ament_python [pkg_name]

    # 编译功能包
    cd ~/dev_ws
    colcon build
    source install/local_setup.sh
    ```

## 常用命令

    ```bash
    ros2 pkg executables    # 查看所有包的可执行文件
    ros2 pkg executables node   # 查看node包的所有可执行文件
    ros2 run --list | grep ^node
    ```
