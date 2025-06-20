# 策略模式

## 定义

该模式定义了一系列算法，并将每个算法封装起来，使它们可以相互替换，且算法的变化不会影响使用算法的客户。

## 核心价值

1. 消除条件分支：避免复杂的if-else/switch-case语句
2. 开闭原则：新增策略无需修改已有代码
3. 算法复用：相同策略在不同上下文中复用
4. 运行时切换：动态改变对象行为
5. 解耦：策略实现与使用分离

## 优缺点

1. 优点：
    多重条件语句不易维护，而使用策略模式可以避免使用多重条件语句。
    提供了一系列的可供重用的算法族，恰当使用继承可以把算法族的公共代码转移到父类里面，避免重复的代码。
    提供相同行为的不同实现，客户可以根据不同时间或空间要求选择不同的。

2. 缺点：
    客户端必须理解所有策略算法的区别，以便适时选择恰当的算法类。
    策略模式造成很多的策略类，增加维护难度。

## 结构

1. 抽象策略类：定义了一个公共接口，各种不同的算法以不同的方式实现这个接口，环境角色使用这个接口调用不同的算法，一般使用接口或抽象类实现。
2. 具体策略类：实现了抽象策略定义的接口，提供具体的算法实现。
3. 环境类：持有一个策略类的引用，最终给客户端调用。

## 典型应用场景

- 存在多种算法变体需要切换
- 算法包含复杂条件逻辑
- 需要在运行时选择算法
- 需要隔离算法实现细节
- 系统需要支持多种业务规则

## 实现

![image](/DesignPattern/images/StrategyPattern.png)

```Java
//抽象策略类
interface Strategy{
    public void strategyMethod();   //策略方法
}

//具体策略类A
class ConcreteStrategyA implements Strategy {
    public void strategyMethod(){
        System.out.println("具体策略A的策略方法被访问");
    }
}

//具体策略类B
class ConcreteStrategyB implements Strategy {
    public void strategyMethod(){
        System.out.println("具体策略B的策略方法被访问");
    }
}

class Context {
    private Strategy strategy;

    public Strategy getStrategy(){
        return strategy;
    }

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public void strategyMethod(){
        strategy.strategyMethod();
    }
}
```

## Java示例

```Java
// 示例一
public interface QuackBehavior {
    void quack();
}

public class Quack implements QuackBehavior {
    @Override
    public void quack(){
        System.out.println("quack!");
    }
}

public class Squeak implements QuackBehavior {
    @Override
    public void quack(){
        System.out.println("squeack");
    }
}

public class Duck{
    private QuackBehavior quackBehavior;

    public void performQuack(){
        if(quackBehavior != null){
            quackBehavior.quack();
        }
    }

    public void setQuackBehavior(QuackBehavior quackBehavior){
        this.quackBehavior = quackBehavior;
    }
}

public class client{
    public static void main(String[] args){
        Duck duck = new Duck();
        duck.setQuackBehavior(new Squeak());
        duck.performQuack();
        duck.setQuackBehavior(new Quack());
        duck.performQuack();
    }
}

//示例二 策略模式与工厂模式结合使用
public interface Strategy {

    void method1();

    void method2();

}

public class ConcreteStrategyA implements Strategy{
    @Override
    public void method1() {
        System.out.println("StrategyA method1");
    }

    @Override
    public void method2() {
        System.out.println("StrategyA method2");
    }
}

public class ConcreteStrategyB implements Strategy{
    @Override
    public void method1() {
        System.out.println("StrategyB method1");
    }

    @Override
    public void method2() {
        System.out.println("StrategyB method2");
    }
}

public class StrategyFactory {

    private static StrategyFactory strategyFactory = new StrategyFactory();

    private StrategyFactory(){}

    public static StrategyFactory getInstance(){
        return strategyFactory;
    }

    private static Map<String, Strategy> strategyMap = new HashMap<>();

    static {
        strategyMap.put("A", new ConcreteStrategyA());
        strategyMap.put("B", new ConcreteStrategyB());
    }

    public Strategy getStrategy(String id){
        return strategyMap.get(id);
    }

}
```

## Python示例

```Python
from abc import ABC, abstractmethod
from typing import List

# 策略接口
class DiscountStrategy(ABC):
    @abstractmethod
    def apply_discount(self, total_price: float) -> float:
        pass

# 具体策略实现
class NoDiscount(DiscountStrategy):
    # 无折扣策略
    def apply_discount(self, total_price: float) -> float:
        return total_price
        
class PercentageDiscount(DiscountStrategy):
    # 百分比折扣策略
    def __init__(self, percentage: float):
        self.percentage = percentage / 100 # 转换为小数
    
    def apply_discount(self, total_price: float) -> float:
        return total_price * (1 - self.percentage)
        
class FullReduction(DiscountStrategy):
    # 满减策略
    def __init__(self, threshold: float, reduction: float):
        self.threshold = threshold
        self.reduction = reduction

    def apply_discount(self, total_price: float) -> float:
        if total_price >= self.threshold:
            return total_price - self.reduction
        return total_price

# 上下文类
class ShoppingCart:
    def __init__(self, discount_strategy: DiscountStrategy = NoDiscount()):
        self._items = []
        self._discount_strategy = discount_strategy

    def add_item(self, name: str, price: float):
        self._items.append((name, price))

    def set_discount_strategy(self, strategy: DiscountStrategy):
        self._strategy_discount = strategy

    def calculate_total(self) -> float:
        subtotal = sum(price for _, price in self._items)
        return self._discount_strategy.apply_discount(subtotal)

    def get_items(self) -> List[str]:
        return [f'{name} - ${price}' for name, price in self._items]

if __name__ == '__main__':
    cart = ShoppingCart()
    cart.add_item("Python编程书", 99.0)
    cart.add_item("无线鼠标", 150.0)
    cart.add_item("机械键盘", 399.0)

    # 使用不同策略计算总价
    print("\n商品列表:")
    for item in cart.get_items():
        print(f"  {item}")
    
    # 无折扣
    print(f"\n无折扣总价: ¥{cart.calculate_total():.2f}")
    
    # 切换为8折策略
    cart.set_discount_strategy(PercentageDiscount(20))
    print(f"8折后价格: ¥{cart.calculate_total():.2f}")
    
    # 切换为满减策略（满500减100）
    cart.set_discount_strategy(FullReduction(500, 100))
    print(f"满500减100后价格: ¥{cart.calculate_total():.2f}")
    
    # 新增策略：组合折扣（先满减再打折）
    class ComboDiscount(DiscountStrategy):
        def apply_discount(self, total_price: float) -> float:
            # 先满减
            temp = total_price - 100 if total_price >= 500 else total_price
            # 再打9折
            return temp * 0.9
    
    cart.set_discount_strategy(ComboDiscount())
    print(f"组合折扣后价格: ¥{cart.calculate_total():.2f}")
```

## 应用场景

1. 电商促销系统
   - 场景：电商平台需要支持多种促销策略（满减、折扣、买赠等）

    ```Python
    class PromotionStrategy(ABC):
        @abstractmethod
        def apply(self, order: Order): 
            pass

    class Double11Strategy(PromotionStrategy):
        def apply(self, order):
            order.total *= 0.85

    class NewUserStrategy(PromotionStrategy):
        def apply(self, order):
            order.total -= 50

    class OrderProcessor:
        def __init__(self, strategy: PromotionStrategy):
            self.strategy = strategy

        def process_order(self, order):
            self.strategy.apply(order)
    ```

2. 支付网关集成
   - 场景：支付多种方式（支付宝、微信等）

    ```Python
    class PaymentStrategy(ABC):
        @abstractmethod
        def apply(self, amount):
            pass

    class AlipayStrategy(PaymentStrategy):
        def pay(self, amount):
            print(f"支付宝支付：{amount}元")

    class WechatPayStrategy(PaymentStrategy):
        def pay(self, amount):
            print(f"微信支付：{amount}元")
    
    class PaymentContext:
        def __init__(self, strategy: PaymentStrategy):
            self.strategy = strategy

        def execute_payment(self, amount):
            self.strategy.pay(amount)
    ```

3. 导航系统
   - 场景：地图应用提供多种路线策略（最短路径、最快路径、避开高速等）

4. 数据压缩工具
   - 场景：文件压缩工具支持多种压缩算法

5. 游戏AI行为系统
   - 场景：游戏NPC根据玩家行为切换战斗策略

6. 日志系统记录
   - 场景：支持多种日志输出方式（控制台、文件、数据库、云服务）

    ```Python
    class LoggingStrategy(ABC):
        @abstractmethod
        def log(self, message):
            pass

    class ConsoleLogger(LoggingStrategy):
        def log(self, message):
            print(f"[CONSOLE] {message}")

    class FileLogger(LoggingStrategy):
        def __init__(self, message):
            self.filename = filename

        def log(self, message):
            with open(self.filename, 'a') as f:
                f.write(f'{message}\n')
    
    class DatabaseLogger(LoggingStrategy):
        def log(self, message):
            # 数据库插入逻辑
            print(f"[DB] 已记录日志: {message}")

    class Logger:
        def __init__(self, strategy=ConsoleLogger()):
            self.strategy = strategy
        
        def set_strategy(self, strategy):
            self.strategy = strategy

        def write_log(self, message):
            self.strategy.log(message)
    ```

7. 验证系统
   - 场景：表单验证支持多种规则（邮箱、手机号、密码强度）
  
    ```Python
    class ValidationStrategy(ABC):
        @abstractmethod
        def validate(self, input):
            pass

    class EmailValidator(ValidationStrategy):
        def validate(self, input):
            return "@" in input and "." in input.split("@")[-1]

    class PhoneValidator(ValidationStrategy):
        def validate(self, input):
            return input.isdigit() and len(input) == 11

    class PasswordValidator(ValidationStrategy):
        def validate(self, input):
            has_upper = any(c.isupper() for c in input)
            has_digit = any(c.isdigit() for c in input)
            return len(input) >= 8 and has_upper and has_digit

    class FormValidator:
        def __init__(self):
            self.validators = {}

        def add_validator(self, field, validator):
            self.validators[field] = validator

        def validate_field(self, field):
            return self.validators[filed].validator(value)
    ```
