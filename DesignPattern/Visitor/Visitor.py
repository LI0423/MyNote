from abc import ABC, abstractmethod


class Animal(ABC):
    @abstractmethod
    def accept(self):
        pass

class Cat(Animal):
    def accept(self, person):
        person.feed()
        print('宠物猫接受喂食')

class Dog(Animal):
    def accept(self, person):
        person.feed()
        print('宠物狗接受喂食')

class Person(ABC):
    @abstractmethod
    def feed(cat: Cat):
        pass

    @abstractmethod
    def feed(dog: Dog):
        pass

class Owner(Person):
    def feed(cat: Cat):
        print('主人给猫喂食')

    def feed(dog: Dog):
        print('主人给狗喂食')

class Someone(Person):
    def feed(self, cat):
        print('其他人给猫喂食')

    def feed(dog):
        print('其他人给狗喂食')

class Home:
    def __init__(self):
        self.nodeList = []

    def add(self, animal: Animal):
        self.nodeList.append(animal)

    def action(self, person: Person):
        for animal in self.nodeList:
            animal.accept(person)

home = Home()
home.add(Cat())
home.add(Dog())

owner = Owner()
home.action(owner)

print('===========')

someone = Someone()
home.action(someone)



