# 观察者模式

## 定义

指多个对象间存在一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。有时又称作发布-订阅模式、模型-视图模式，是对象行为型模式。

## 核心优势

1. 松耦合：主题和观察者独立变化，互不影响
2. 动态关系：运行时可以添加/删除观察者
3. 广播通信：主题无需知道具体观察者细节
4. 开闭原则：新增观察者无需修改主题代码

## 优缺点

1. 优点：
   - 降低了目标与观察者之间的耦合关系，两者之间是抽象耦合关系。
   - 目标与观察者之间建立了一套触发机制。
2. 缺点：
   - 目标与观察者之间的依赖关系并没有完全接触，而且有可能出现循环引用。
   - 当观察者对象很多时，通知的发布会花费很多时间，影响程序的效率。

## 结构

- 抽象主题角色：也叫抽象目标类，提供了一个用于保存观察者对象的聚集类和增加、删除观察者对象的方法，以及通知所有观察者的方法。
- 具体主题角色：也叫具体目标类，实现抽象目标中的通知方法，当具体主题的内部状态发生改变时，通知所有注册过的观察者对象。
- 抽象观察者角色：是一个抽象类或接口，包含一个更新自己的抽象方法，当接到具体主题的更改通知时被调用。
- 具体观察者角色：实现抽象观察者中定义的抽象方法，以便在得到目标的更改通知时更新自身的状态。

## 典型应用场景

- 事件驱动系统
- 数据监控与报警
- 分布式系统通信
- 模型-视图架构
- 发布-订阅模式
- 自动化工作流
- 实时数据同步

## 实现

![image](/DesignPattern/images/ObserverPattern.png)

```Java
//抽象观察者
interface Observer {
    void response();
}

//抽象目标
abstract class Subject {
    protected List<Observer> observers = new ArrayList<Observer>();

    //增加观察者方法
    public void add(Observer observer){
        observers.add(observer);
    }

    //删除观察者方法
    public void remove(Observer observer){
        observers.remove(observer);
    }

    public abstract void notifyObserver();
}

//具体目标
class ConcreteSubject extends Subject {
    public void notifyObserver(){
        System.out.println("具体目标发生改变");

        for(Object obs : observers){
            ((Observer) obs).response();
        }
    }
}

//具体观察者1   
class ConcreteObserver1 implements Observer {
    public void response(){
        System.out.println("具体观察者1作出反应");
    }
}

//具体观察者2
class ConcreteObserver2 implements Observer {
    public void response(){
        System.out.println("具体观察者2作出反应");
    }
}

public class ObserverPattern {
    public static void main(String[] args){
        Subject subject = new ConcreteSubject();
        Observer obs1 = new ConcreteObserver1();
        Observer obs2 = new ConcreteObserver2();
        subject.add(obs1);
        subject.add(obs2);
        subject.notifyObserver();
    }
}
```

## 代码示例

### Java示例

```Java
//创建主题接口Subject，定义注册观察者、注销观察者和通知观察者的方法
public interface Subject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}

//实现主题接口，创建具体主题类WeatherData并在其中实现观察者注册、注销和通知的方法
public class WeatherData implements Subject {
    private ArrayList<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    
    public WeatherData(){
        observers = new ArrayList<>();
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o){
        int i = observers.indexOf(o);
        if(i > 0){
            observers.remove(i);
        }
    }

    public void notifyObservers(){
        for(Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }

    public void measurementsChanged(){
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
}

//创建观察者接口Observer，定义更新方法
public interface Observer {
    public void update(float temperature, float humidity, float pressure);
}

//实现观察者接口，创建具体观察者类CurrentConditionDisplay，并在其中实现更新方法
public class CurrentConditionDisplay implements Observer {
    private float temperature;
    private float humidity;
    private Subject weatherData;

    public CurrentConditionDisplay(Subject weatherData){
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    public void update(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display(){
        System.out.println("Current conditions: " + temperature + "F degress and " + humidity + "% humidity");
    }
}

public class WeatherStation {
    public static void main(String[] args){
        WeatherData weatherData = new WeatherData();

        CurrentConditionDisplay currentDisplay = new CurrentConditionDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }
}
```

### Python示例

```Python
from abc import ABC, abstractmethod
from typing import List

# 1. 观察者接口
class Observer(ABC):
    @abstractmethod
    def update(self, temperature: float, humidity: float, presure: float):
        pass

# 2. 主题接口
class Subject(ABC):
    @abstractmethod
    def register_observer(self, observer: Observer):
        pass

    @abstractmethod
    def remove_observer(self, observer: Observer):
        pass

    @abstractmethod
    def notify_observers(self):
        pass

# 3. 具体主题：气象站
class WeatherStation(Subject):
    def __init__(self):
        self._observers: List[Observer] = []
        self._temperature = 0.0
        self._humidity = 0.0
        self._pressure = 0.0

    def register_observer(self, observer: Observer):
        if observer not in self._observers:
            self._observers.append(observer)

    def remove_observer(self, observer: Observer):
        if observer in self._observers:
            self._observers.remove(observer)

    def notify_observers(self):
        for observer in self._observers:
            observer.update(self._temperature, self._humidity, self.pressure)

    def measurements_changed(self):
        """当气象数据更新时调用此方法"""
        self.notify_observers()

    def set_measurements(self, temperature: float, humidity: float, pressure: float):
        """更新气象数据并通知观察者"""
        self._temperature = temperature
        self._humidity = humidity
        self._pressure = pressure
        self.measurements_changed()

    def get_temperature(self) -> float:
        return self._temperature

    def get_humidity(self) -> float:
        return self._humidity

    def get_pressure(self) -> float:
        return self._pressure

# 4. 具体观察者：当前天气显示
class CurrentConditionDisplay(Observer):
    def __init__(self, weather_station: Subject):
        self._weather_station = weather_station
        weather_station.register_observer(self)
        self._temperature = 0.0
        self._humidity = 0.0

    def update(self, temperature: float, humidity: float, pressure: float):
        self._temperature = temperature
        self._humidity = humidity
        self.display()

    def display(self):
        print(f"当前天气: {self._temperature}°C, 湿度 {self._humidity}%")

# 5. 具体观察者：气象统计
class StatisticsDisplay(Observer):
    def __init__(self, weather_station: Subject):
        self.weather_station = weather_station
        weather_station.register_observer(self)
        self._max_temp = 0.0
        self._min_temp = 100.0
        self._temp_sum = 0.0
        self._num_readings = 0

    def update(self, temperature: float, humidity: float, pressure: float):
        self._temp_sum += temperature
        self._num_readings += 1

        if temperature > self._max_temp:
            self._max_temp = temperature

        if temperature < self._min_temp:
            self._min_temp = temperature

        self.display()

    def display(self):
        avg_temp = self._temp_sum / self._num_readings if self._num_readings > 0 else 0
        print(f"温度统计: 平均 {avg_temp:.1f}°C, 最高 {self._max_temp}°C, 最低 {self._min_temp}°C")

# 6. 具体观察者：天气预报
class ForecastDisplay(Observer):
    def __init__(self, weather_station: Subject):
        self._weather_station = weather_station
        weather_station.register_observer(self)
        self._last_pressure = 0.0
        self._forecast = ''

    def update(self, temperature: float, humidity: float, pressure: float):
        if self._last_pressure == 0:
            self._forecast = "等待更多数据..."
        elif pressure > self._last_pressure:
            self._forecast = "天气将好转!"
        elif pressure < self._last_pressure:
            self._forecast = "可能下雨!"
        else:
            self._forecast = "天气稳定"

        self._last_pressure = pressure
        self.display()

    def display(self):
        print(f'天气预报：{self._forecast}')

# 客户端代码
if __name__ == "__main__":
    # 创建气象站（主题）
    weather_station = WeatherStation()
    
    # 创建显示装置（观察者）并注册到主题
    current_display = CurrentConditionsDisplay(weather_station)
    stats_display = StatisticsDisplay(weather_station)
    forecast_display = ForecastDisplay(weather_station)
    
    print("===== 第一次气象数据更新 =====")
    weather_station.set_measurements(25.0, 65.0, 101.3)
    
    print("\n===== 第二次气象数据更新 =====")
    weather_station.set_measurements(26.5, 70.0, 101.2)
    
    print("\n===== 第三次气象数据更新 =====")
    weather_station.set_measurements(24.0, 90.0, 101.0)
    
    # 移除统计显示
    weather_station.remove_observer(stats_display)
    
    print("\n===== 移除统计显示后的更新 =====")
    weather_station.set_measurements(23.5, 85.0, 100.9)
```
