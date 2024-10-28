class Component:
    def operation(self):
        pass

class Leaf(Component):
    def operation(self):
        print('Leaf: Doing operation')

class Composite(Component):
    def __init__(self):
        self.children = []

    def add(self, component: Component):
        self.children.append(component)

    def remove(self, compoent: Component):
        self.children.remove(compoent)

    def operation(self):
        print('Composite: Doing operation.')
        for child in self.children:
            child.operation()

leaf = Leaf()
leaf.operation()

composite = Composite()
composite.add(Leaf())
composite.add(Leaf())
composite.operation()

composite2 = Composite()
composite2.add(Leaf())
composite2.add(Leaf())
composite.add(composite2)
composite.operation()