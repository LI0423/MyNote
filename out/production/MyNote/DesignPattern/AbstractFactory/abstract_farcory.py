class Pet:
    def __init__(self, name: str) -> None:
        self.name = name

    def speak(self) -> None:
        raise NotImplementedError

    def __str__(self) -> str:
        raise NotImplementedError

class Cat(Pet):
    def speak(self) -> None:
        print('miao')

    def __str__(self) -> str:
        return f'Cat<{self.name}>'

class Dog(Pet):
    def speak(self) -> None:
        print('wang')

    def __str__(self) -> str:
        return f'Dog<{self.name}>'

class PetShop:
    def __init__(self, animal_factory: type[Pet]) -> None:
        self.pet_factory = animal_factory

    def buy_pet(self, name: str) -> Pet:
        pet = self.pet_factory(name)
        print(f'here you are lovely pet {name}')
        return pet

if __name__ == '__main__':
    cat = PetShop(Cat)
    tom = cat.buy_pet('tom')
    print(tom.__str__())
    tom.speak()

    dog = PetShop(Dog)
    jack = dog.buy_pet('jack')
    print(jack.__str__())
    jack.speak()