class Noodle:
    def show():
        pass

class KangNoodle(Noodle):
    def show(self):
        print("康师傅方便面")

class TongNoodle(Noodle):
    def show(self):
        print("统一方便面")

class NoodleFactory:
    KANG : int = 1
    TONG : int = 2
    def getNoodle(self, type: int) -> Noodle:
        if type == self.KANG:
            return KangNoodle()
        elif type == self.TONG:
            return TongNoodle()

factory = NoodleFactory()
kang = factory.getNoodle(1)
kang.show()
tong = factory.getNoodle(2)
tong.show()
