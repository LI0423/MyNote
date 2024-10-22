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

class WeChatAdapter(PaymentProcessor):
    def __init__(self, wechat: WeChat) -> None:
        self.wechat = wechat

    def pay(self, amount):
        print(self.wechat.login)
        return self.wechat.send_payment(amount)

class AliAdapter(PaymentProcessor):
    def __init__(self, ali: Ali) -> None:
        self.ali = ali

    def pay(self, amount):
        print(self.ali.login)
        return self.ali.send_payment(amount)

class Client:
    def __init__(self, payment_processor: PaymentProcessor) -> None:
        self.payment_processor = payment_processor

    def chekout(self, amount):
        print(self.payment_processor.pay(amount))

wechat = WeChat('wechat')
weChatAdapter = WeChatAdapter(wechat)
client = Client(weChatAdapter)
client.chekout(10)

ali = Ali('ali')
aliAdapter = AliAdapter(ali)
client2 = Client(aliAdapter)
client2.chekout(20)


