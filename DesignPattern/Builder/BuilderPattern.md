# 建造者模式

## 定义

将一个复杂对象的构造与它的表示分离，使同样的构建过程可以创建不同的表示。将一个复杂的对象分解为多个简单的对象，然后一步一步构建而成。

## 优缺点

### 优点

- 封装性好，构建和表示分离。
- 扩展性好，各个具体的建造者相互独立，有利于系统的解耦。
- 客户端不知道产品内部组成的细节，建造者可以对创建过程逐步细化，而不对其他模块产生任何影响。

### 缺点

- 产品的组成部分必须相同。
- 如果产品内部发生变化，则建造者也要同步修改。

## 结构

1. 产品角色：包含多个组成部件的复杂对象，由具体建造者来创建其各个部件。
2. 抽象建造者：包含创建产品各个部件的抽象方法的接口，通常还包含一个返回复杂产品的方法getResult()。
3. 具体建造者：实现Builder接口，完成复杂产品的各个部件的具体创建方法。
4. 指挥者：调用建造者对象中的部件构造与装配方法完成复杂对象的创建，在指挥者中不包含具体产品的信息。

## 实现

```Java
/**变种的Builder模式包括以下内容：
    在要构造的类内部创建一个静态内部类Builder；
    静态内部类的参数与构建类一致；
    构建类的构造参数是静态内部类，使用静态内部类的变量一一赋值给构建类；
    静态内部类提供参数的setter方法，并且返回值是当前Builder对象；
    最终提供一个build方法构建一个构建类对象，参数是当前Builder对象。
**/
public class Student {
    private String name;
    private int age;
    private String email;

    //静态builder方法
    public static Student.Builder builder(){
        return new Student.Builder();
    }

    //外部调用builder类的属性接口进行设值
    public static class Builder{
        private String name;
        private int age;
        private String email;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder age(int age){
            this.age = age;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Student build(){
            //将builder对象传入到学生构造函数
            return new Student(this);
        }
    }

    //私有化构造器
    private Student(Builder builder){
        name = builder.name;
        age = builder.age;
        email = builder.email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", num=" + num +
                ", email='" + email + '\'' +
                '}';
    }

    public static void student(){
        Student student = Student.builder().name("测试").num(1).age(18).email("ceshi@163.com").build();
        System.out.println(student);
    }
}
```

## 代码示例

### Python代码

```Python
import abc


class House:
    jiadian: str
    diban: str
    youqi: str

    def __repr__(self) -> str:
        return f'家电 {self.jiadian} 地板 {self.diban} 油漆 {self.youqi}'

class HouseBuilder():
    def __init__(self) -> None:
        self.house = House()

    @abc.abstractmethod
    def doJiadian(self, jiadian: str):
        pass

    @abc.abstractmethod
    def doDiban(self, diban: str):
        pass

    @abc.abstractmethod
    def doYouqi(self, youqi: str):
        pass

    def getHouse(self):
        return self.house

class JiazhuangBuilder(HouseBuilder):
    def doJiadian(self, jiadian: str):
        self.house.jiadian = jiadian
    
    def doDiban(self, diban: str):
        self.house.diban = diban

    def doYouqi(self, youqi: str):
        self.house.youqi = youqi

    def getHouse(self):
        return self.house

class HouseDirector:
    def build(self, houseBuilder: HouseBuilder):
        houseBuilder.doJiadian('简单家电')
        houseBuilder.doDiban('简单地板')
        houseBuilder.doYouqi('简单油漆')
        return houseBuilder.getHouse()

houseDirector = HouseDirector()
jiazhuangBuilder = JiazhuangBuilder()
print(houseDirector.build(jiazhuangBuilder))
```
