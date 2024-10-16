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


