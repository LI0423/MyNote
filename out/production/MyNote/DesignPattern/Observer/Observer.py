from typing import List


class Subject:
    def registerObserver(self):
        pass

    def removeObserver(self):
        pass

    def notifyObservers(self):
        pass

class Observer:
    def update(self, temprature: float, humidity: float, pressure: float):
        pass

class WeatherData(Subject):
    temprature: float
    humidity: float
    pressure: float

    def __init__(self) -> None:
        self.observers: list[Observer] = []

    def registerObserver(self, observer: Observer):
        self.observers.append(observer)

    def removeObserver(self, observer: Observer):
        self.observers.remove(observer)

    def notifyObservers(self):
        for o in self.observers:
            o.update(self.temprature, self.humidity, self.pressure)

    def measurementsChanged(self):
        self.notifyObservers()

    def setMeasurements(self, temprature, humidity, pressure):
        self.temprature = temprature
        self.humidity = humidity
        self.pressure = pressure
        self.measurementsChanged()

class CurrentConditionDisplay(Observer):
    temprature: float
    humidity: float
    weatherData: Subject

    def __init__(self, weatherData: Subject) -> None:
        self.weatherData = weatherData
        self.weatherData.registerObserver(self)

    def update(self, temprature: float, humidity: float, pressure: float):
        self.temprature = temprature
        self.humidity = humidity
        self.display()

    def display(self):
        print(f'Current conditions: {self.temprature} F degress and {self.humidity}% humidity')

weatherData = WeatherData()
currentDisplay = CurrentConditionDisplay(weatherData)
weatherData.setMeasurements(80, 65, 30.4)
