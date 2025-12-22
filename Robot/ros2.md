# ROS（Robot Operation System）

## 文件结构

### 功能包

```bash
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

### 启动底盘

```bash
source /opt/tros/setup.bash
ros2 launch originbot_base robot.launch.py
```

### 编译指定包

```bash
colcon build --packages-select <package>
```

### 刷新环境

```bash
source install/setup.bash
```

### 创建包

```bash
ros2 pkg create --build-type ament_python <package>
ros2 pkg create --build-type ament_cmake <package>
```

### 激活环境

```bash
source /opt/tros/setup.bash        # 激活基础ROS
source install/setup.bash          # 激活你的工作空间
```

### 启动底盘

```bash
ros2 launch originbot_bringup originbot.launch.py
```

### rviz

```bash
ros2 run rviz2 rviz2
```

### 参数

```bash
# 查看所有参数
ros2 param list /move_distance_server

# 获取某个参数
ros2 param get /move_distance_server kp_lin

# 修改某个参数（立即生效）
ros2 param set /move_distance_server kp_lin 0.7
```

### 查看节点

```bash
ros2 node list
```
