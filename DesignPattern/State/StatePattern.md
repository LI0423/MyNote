# 状态模式

## 定义

对有状态的对象把复杂的“判断逻辑”提取到不同的状态对象中，允许状态对象在其内部状态发生改变时改变其行为。

## 优缺点

### 优点

- 结构清晰，状态模式将与特定状态相关的行为局部化到一个状态中，并且将不同状态的行为分割开来。
- 将状态转换显示化，减少对象间的相互依赖。
- 状态类职责明确，有利于程序的扩展。

### 缺点

- 状态模式的使用会增加系统的类与对象的个数。
- 状态模式的结构与实现较为复杂，使用不当会导致代码混乱。

## 结构

![image](/DesignPattern/images/StatePattern.png)

1. 环境类：也称上下文，定义了客户端需要的接口，内部维护一个当前状态，并负责具体状态的切换。
2. 抽象状态类：定义一个接口，用来封装环境对象中的的特定状态所对应的行为，可以有一个或多个行为。
3. 具体状态类：实现抽象状态所对应的行为，并且在需要的情况下进行状态切换。

## 实现

```Python
//环境类
class Context {
    private State state;

    public Context(){
        this.state = new ConcreteStateA();
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return (state);
    }

    public void Handler(){
        state.Handle(this);
    }
}

//抽象状态类
abstract class State {
    public abstract void Handle(Context context);
}

//具体状态类A
class ConcreteStateA extends State {
    public void Handle(Context context){
        System.out.println("当前状态是：A");
        context.setState(new ConcreteStateA());
    }
}

//具体状态类B
class ConcreteStateB extends State {
    public void Handle(Context context){
        System.out.println("当前状态是：B");
        context.setState(new ConcreteStateB());
    }
}
```

## 代码示例

### Java代码

```Java
public interface State {
    //投入25分钱
    void insertQuarter();

    //退回25分钱
    void ejectQuarter();

    //转动曲柄
    void turnCrank();

    //发放糖果
    void dispense();
}

public class HasQuarterState implements State{
    private GumballMachine gumballMachine;

    public HasQuarterState(GumballMachine gumballMachine){
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter(){
        System.out.println("You can't insert another quarter");
    }

    @Override
    public void ejectQuarter(){
        System.out.println("Quarter returned");
        gumballMachine.setState(gumballMachine.getNoQuarterState());
    }

    @Override
    public void turnCrank(){
        System.out.println("You turned...");
        gumballMachine.setState(gumballMachine.getSoldState());
    }

    @Override
    public void dispense(){
        System.out.println("No gumball dispensed");
    }
}

public class NoQuarterState implements State{
    private GumballMachine gumballMachine;

    public NoQuarterState(GumballMachine gumballMachine){
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter(){
        System.out.println("You insert a quarter");
        gumballMachine.setState(gumballMachine.getHasQuarterState());
    }

    @Override
    public void ejectQuarter(){
        System.out.println("Yuo haven't insert a quarter");
    }

    @Override
    public void turnCrank(){
        System.out.println("You turned but there's no quarter");
    }

    @Override
    public void dispense(){
        System.out.println("You need pay first");
    }
}

public class SoldOutState implements State{
    private GumballMachine gumballMachine;

    public SoldOutState(GumballMachine gumballMachine){
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter(){
        System.out.println("You can't insert a quarter, the machine is sold out");
    }

    @Override
    public void ejectQuarter(){
        System.out.println("You can't eject, you haven't inserted a quarter yet");
    }

    @Override
    public void turnCrank(){
        System.out.println("You turned, but there are no gumballs");
        gumballMachine.setState(gumballMachine.getSoldState());
    }

    @Override
    public void dispense(){
        System.out.println("No gumball dispensed");
    }
}

public class SoldState implements State{
    private GumballMachine gumballMachine;

    public SoldState(GumballMachine gumballMachine){
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter(){
        System.out.println("Please wait, we're alreday giving you a gumball");
    }

    @Override
    public void ejectQuarter(){
        System.out.println("Sorry, you already turned the crank");
    }

    @Override
    public void turnCrank(){
        System.out.println("Turning twice doesn't get your another gumball!");
    }

    @Override
    public void dispense(){
        gumballMachine.releaseBall();
        if(gumballMachine.getCount > 0) {
            gumballMachine.setState(gumballMachine.getNoQuarterState());
        } else {
            System.out.println("Oops, out of gumballs");
            gumballMachine.setState(gumballMachine.getSoldState());
        }
    }
}

public class GumballMachine{
    private State soldOutState;
    private State noQuarterState;
    private State hasQuarterState;
    private State soldState;

    private State state;
    private int count = 0;

    public GumballMachine(int numberGumballs){
        count = numberGumballs;
        soldOutState = new SoldOutState(this);
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldOutState = new SoldOutState(this);

        if(numberGumballs > 0) {
            state = noQuarterState;
        } else {
            state = soldOutState;
        }
    }

    public void insertQuarter(){
        state.insertQuarter();
    }

    public void ejectQuarter(){
        state.ejectQuarter();
    }

    public void turnCrank(){
        state.turnCrank();
        state.dispense();
    }

    public void setState(State state){
        this.state = state;
    }

    public void releaseBall(){
        System.out.println("A gumball comes rolling out the slot...");
        if(count != 0){
            count -= 1;
        }
    }

    public State getSoldState(){
        return soldOutState;
    }

    public State getNoQuarterState(){
        return noQuarterState;
    }

    public State getHasQuarterState(){
        return hasQuarterState;
    }

    public State getSoldOutState(){
        return soldOutState;
    }

    public int getCount(){
        return count;
    }

}

public class Client {
    public static void main(String[] args){
        GumballMachine gumballMachine = new GumballMachine(5);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        gumballMachine.insertQuarter();
        gumballMachine.ejectQuarter();
        gumballMachine.turnCrank();

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        gumballMachine.ejectQuarter();

        gumballMachine.insertQuarter();
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
    }
}
```

### Python代码

```Python

```
