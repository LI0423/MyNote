# 组合模式

## 定义

将对象组合成树状的层次结构的模式，用来表示“整体-部分”的关系，使用户对单个对象和组合对象具有一致的访问性。

## 优缺点

### 优点

- 组合模式使得客户端代码可以一直地处理单个对象和组合对象，无须关心自己处理的是单个对象还是组合对象。
- 更容易在组合体内加入新的对象，客户端不会因为加入了新的对象而更改源代码。

### 缺点

- 设计复杂
- 不容易限制容器中的构件
- 不容易用继承的方法来增加构件的新功能

## 结构

![image](/DesignPattern/images/CompositePattern.png)

1. 抽象构件角色：主要作用是为树叶构件和树枝构件声明公共接口，并实现他们的默认行为。在透明式的组合模式中抽象构件还声明访问和管理子类的接口；在安全式的组合模式中不声明访问和管理子类的接口，管理工作由树枝构件完成。
2. 树叶构件角色：组合中的叶节点对象，它没有子节点，用于继承或实现抽象构件。
3. 树枝构件角色：组合中的分支节点对象，有子节点，用于继承和实现抽象构件，主要作用是存储和管理子部件。

## 实现

1. 透明方式：由于抽象构件声明了所有子类中的全部方法，客户端无须区别树叶对线和树枝对象，对客户端来说是透明的。
2. 安全方式：将管理子构件的方法移到树枝构件中，抽象构件和树叶构件没有子对象的管理方法，由于叶子和分支有不同的接口，客户端在调用时要知道树叶对象和树枝对象的存在，所以失去了透明性。

## 代码示例

### Python代码

```Python
class Component:
    def operation(self):
        pass

class Leaf(Component):
    def operation(self):
        print('Leaf: Doing operation')

class Composite(Component):
    def __init__(self):
        self.children = []

    def add(self, component: Component):
        self.children.append(component)

    def remove(self, compoent: Component):
        self.children.remove(compoent)

    def operation(self):
        print('Composite: Doing operation.')
        for child in self.children:
            child.operation()

leaf = Leaf()
leaf.operation()

composite = Composite()
composite.add(Leaf())
composite.add(Leaf())
composite.operation()

composite2 = Composite()
composite2.add(Leaf())
composite2.add(Leaf())
composite.add(composite2)
composite.operation()
```
