class GiveGift(object):
    def give_dolls(self):
        pass
    def give_flowers(self):
        pass
    def give_chocolate(self):
        pass

class Girl(object):
    def __init__(self, name: str) -> None:
        self.name = name

class Boy(GiveGift):
    def __init__(self, girl: Girl) -> None:
        self.girl = girl

    def give_dolls(self):
        print(self.girl.name, '送你洋娃娃')

    def give_flowers(self):
        print(self.girl.name, '送你鲜花')

    def give_chocolate(self):
        print(self.girl.name, '送你巧克力')

class Proxy(GiveGift):
    def __init__(self, boy: Boy) -> None:
        self.boy = boy

    def give_dolls(self):
        self.boy.give_dolls()

    def give_flowers(self):
        self.boy.give_flowers()

    def give_chocolate(self):
        self.boy.give_chocolate()

girl = Girl("微微")
boy = Boy(girl)
proxy = Proxy(boy)
proxy.give_dolls()
proxy.give_flowers()
proxy.give_chocolate()
    