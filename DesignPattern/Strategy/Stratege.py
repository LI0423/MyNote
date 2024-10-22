class DiscountStrategy:
    def apply_discount(self):
        pass

class PercentageDiscountStrategy(DiscountStrategy):
    def __init__(self, percentage: float):
        self.percentage = percentage

    def apply_discount(self, amount: int):
        return amount - (self.percentage * amount / 100)
    
class FixedAmountDiscountStrategy(DiscountStrategy):
    def __init__(self, discountAmount: float):
        self.discountAmount = discountAmount

    def apply_discount(self, amount: int):
        return amount - self.discountAmount
    
class ShoppingCart:
    def __init__(self):
        self.discountStrategy = None

    def set_discount_strategy(self, discountStrategy: DiscountStrategy):
        self.discountStrategy = discountStrategy

    def checkout(self, totalAmount: int):
        return self.discountStrategy.apply_discount(totalAmount)
    
shoppingCart = ShoppingCart()
shoppingCart.set_discount_strategy(PercentageDiscountStrategy(0.8))
totalAmount = shoppingCart.checkout(100)
print(totalAmount)

shoppingCart.set_discount_strategy(FixedAmountDiscountStrategy(20))
totalAmount = shoppingCart.checkout(100)
print(totalAmount)