from abc import ABC, abstractmethod


class SoyaMilk(ABC):
    def make(self):
        self.select()
        self.addCondiments()
        self.soak()
        self.beat()
    
    def select(self):
        print('第一步：选择上好的黄豆')

    @abstractmethod
    def addCondiments(self):
        pass

    def soak(self):
        print('第三步：黄豆和配料开始浸泡，需要3小时')

    def beat(self):
        print('第四步：黄豆和配料放到豆浆机打碎')

class RedSoyaMilk(SoyaMilk):

    def addCondiments(self):
        print('第二步：加入上好的花生')

soyaMilk = RedSoyaMilk()
soyaMilk.make()

