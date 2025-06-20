# 装饰器模式

## 定义

指在不改变现有对象结构的情况下，动态地给该对象增加一些职责。主要解决继承关系过于复杂的问题，通过组合来替代继承。主要功能是给原始类添加增强功能。

## 优缺点

### 优点

是继承的有力补充，在不改变原有对象的情况下动态的给一个对象扩展功能，即插即用。通过使用装饰类及这些装饰类的排列组合可以实现不同效果。

### 缺点

会增加许多子类，增加程序复杂性。

## 结构

![image](/DesignPattern/images/DecoratorPattern.png)

1. 抽象构件角色：定义一个抽象接口以规范准备接收附加责任的对象。
2. 具体构建角色：实现抽象构件通过装饰角色为其添加一些职责。
3. 抽象装饰角色：继承抽象构件并包含具体构件的实例，可以通过其子类扩展具体构件的功能。
4. 具体装饰角色：实现抽象装饰的相关方法，并给具体构件对象添加附加的责任。

## 实现

```Java
//抽象构件角色
public interface Text {
    String getContent();
}

//具体构件角色
public class PlainText implements Text {
    private String content;

    public PlainText(String content){
        this.content = content;
    }

    @Override
    public String getContent(){
        return content;
    }
}

//抽象装饰角色
public abstract class TextDecorator implements Text {
    protected Text text;

    public TextDecorator (Text text){
        this.text = text;
    }
}

//具体装饰角色
public class BoldText extends TextDecorator {
    public BoldText(Text text){
        super(text);
    }

    @Override
    public String getContent(){
        return "<b>" + text.getContent() + "</b>";
    }
}

public class DecoratorPattern {
    public static void main(String[] args) {
        Text text = new PlainText("Hello world!");
        text = new BoldText(text);
        System.out.println(text.getContent());
    }
}
```

## 代码示例

### Java示例

```Java
//抽象构建角色
public abstract class Drink{
    public String des;
    private Double price = 0.0;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public abstract Double cost();
}

//缓冲类，提取共同点
public class Coffee extends Drink {
    @Override
    public Double cost() {
        return super.getPrice();
    }
}

//具体构建角色
public class LongBlack extends Coffee{
    public LongBlack() {
        setDes("美式咖啡");
        setPrice(7.0);
    }
}

//具体构建角色
public class Espresso extends Coffee{
    public Espresso() {
        setDes("意大利咖啡");
        setPrice(6.0);
    }
}

//抽象装饰者
public class CoffeeDecorator extends Drink {

    private Drink drink;

    public CoffeeDecorator(Drink drink) {
        this.drink = drink;
    }

    @Override
    public Double cost() {
        return super.getPrice() + drink.cost();
    }

    @Override
    public String getDes() {
        return des + " " + getPrice() + " " + drink.getDes();
    }
}

//具体装饰者，巧克力
public class Chocolate extends CoffeeDecorator {
    public Chocolate(Drink drink) {
        super(drink);
        setDes("巧克力");
        setPrice(4.0);
    }
}

//具体装饰者，牛奶
public class Milk extends CoffeeDecorator{
    public Milk(Drink drink) {
        super(drink);
        setDes("牛奶");
        setPrice(3.0);
    }
}

public class CoffeeBar {
    public static void main(String[] args) {
        System.out.println("订单1");
        Drink order = new LongBlack();
        System.out.println("费用：" + order.getPrice() + ", " + "描述：" + order.getDes());

        order = new Milk(order);
        System.out.println("加入一份牛奶费用：" + order.cost() + ", " + "描述：" + order.getDes());

        order = new Milk(order);
        System.out.println("加入两份牛奶费用: " + order.cost() + ", " + "描述：" + order.getDes());

        order = new Chocolate(order);
        System.out.println("加入一份巧克力费用：" + order.cost() + ", " + "描述：" + order.getDes());
    }
}
```

## Python示例

```Python
class Coffee:
    def getDescription() -> str:
        pass

    def getCost() -> float:
        pass

class BlackCoffee(Coffee):
    def getDescription(self) -> str:
        return '黑咖啡'

    def getCost(self) -> float:
        return 2

class CoffeeDecorator(Coffee):
    def __init__(self, coffee: Coffee) -> None:
        self.coffee = coffee

    def getDescription(self) -> str:
        return self.coffee.getDescription()

    def getCost(self) -> float:
        return self.coffee.getCost()

class MilkDecorator(CoffeeDecorator):
    def getDescription(self) -> str:
        return self.coffee.getDescription() + ", 加牛奶"

    def getCost(self) -> float:
        return self.coffee.getCost() + 0.3

class SugarDecorator(CoffeeDecorator):
    def getDescription(self) -> str:
        return self.coffee.getDescription() + ", 加糖"

    def getCost(self) -> float:
        return self.coffee.getCost() + 0.5

coffee = BlackCoffee()
coffee = MilkDecorator(coffee)
coffee = SugarDecorator(coffee)
print(coffee.getDescription())
print(coffee.getCost())

coffee = BlackCoffee()
coffee = MilkDecorator(coffee)
coffee = SugarDecorator(coffee)
coffee = SugarDecorator(coffee)
print(coffee.getDescription())
print(coffee.getCost())
```
