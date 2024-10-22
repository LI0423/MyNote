
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


