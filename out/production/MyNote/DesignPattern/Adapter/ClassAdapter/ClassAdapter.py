class WeChat:
    def __init__(self, username: str) -> None:
        self.uername = username

    def login(self):
        return f'Logging in WeChat user: {self.uername}'

    def send_payment(self, amount):
        return f'WeChat proccessed payment of ${amount}'

class Ali:
    def __init__(self, username: str) -> None:
        self.username = username

    def login(self):
        return f'Logging in Ali user: {self.username}'

    def send_payment(self, amount):
        return f'Ali procced payment of ${amount}'
    
class PaymentProcessor():
    def pay(self, amount):
        raise NotImplementedError('This method should be overriden.')

class WeChatAdapter(WeChat, PaymentProcessor):
    def __init__(self, username: str) -> None:
        self.wechat = WeChat(username)
        self.wechat.login()

    def pay(self, amount):
        return self.wechat.send_payment(amount)

class AliAdapter(Ali, PaymentProcessor):
    def __init__(self, username: str) -> None:
        self.ali = Ali(username)
        self.ali.login()

    def pay(self, amount):
        return self.ali.send_payment(amount)

weChatAdapter = WeChatAdapter('wechat')
print(weChatAdapter.pay(5))

aliAdapter = AliAdapter('ali')
print(aliAdapter.pay(10))
