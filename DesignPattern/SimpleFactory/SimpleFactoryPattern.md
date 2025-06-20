# 简单工厂模式

## 定义

定义一个创建产品对象的工厂接口，将产品对象的实际创建工作推迟到具体子工厂类当中。

## 优缺点

### 优点

- 工厂类包含必要的逻辑判断，可以决定在什么时候创建哪一个产品的实例。
- 客户端无需知道所创建具体产品的类名，只需知道参数即可。
- 可以引入配置文件，在不修改客户端代码的情况下更换和添加新的具体产品类。

### 缺点

- 工厂模式的工厂类单一，负责所有产品的创建，职责过重，一旦异常，整个系统将受影响。
- 使用简单工厂模式会增加系统中类的个数，增加系统的复杂度和理解难度。
- 系统扩展困难，一旦增加新产品不得不修改工厂逻辑，在产品类型较多时可能造成逻辑过于复杂。

## 结构

![image](/DesignPattern/images/SimpleFactoryPattern.png)

1. 简单工厂：负责创建所有实例的内部逻辑，工厂类的创建产品类的方法可以被外界直接调用，创建所需的产品对象。
2. 抽象产品：简单工厂创建的所有对象的父类，负责描述所有实例共有的公共接口。
3. 具体产品：简单工厂模式的创建目标。

## 代码示例

### Python代码

```Python
class Noodle:
    def show():
        pass

class KangNoodle(Noodle):
    def show(self):
        print("康师傅方便面")

class TongNoodle(Noodle):
    def show(self):
        print("统一方便面")

class NoodleFactory:
    KANG : int = 1
    TONG : int = 2
    def getNoodle(self, type: int) -> Noodle:
        if type == self.KANG:
            return KangNoodle()
        elif type == self.TONG:
            return TongNoodle()

factory = NoodleFactory()
kang = factory.getNoodle(1)
kang.show()
tong = factory.getNoodle(2)
tong.show()
```
