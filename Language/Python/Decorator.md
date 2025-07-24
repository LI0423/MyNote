# 装饰器

一种用于修改或扩展函数或方法行为的工具。允许在不改变原函数代码的情况下，动态地添加功能。装饰器本质上是一个闭包函数，接受一个函数作为参数并返回一个新的函数。

## 主要作用

- 代码复用：将通用功能（如日志记录、权限检查、性能测试等）封装到装饰器中，避免重复代码。
- 增强函数功能：在不修改原函数的情况下，添加额外功能。
- 简化代码：通过装饰器将横切关注点（如日志、缓存等）与核心逻辑分离，使代码更清晰。

## 基本语法

装饰器的构建有两种方式：传统语法和语法糖。

```Python
"""传统语法"""
def log_decorator(func):
    def log_inner(*args, **kwargs):
        print("方法调用前")
        result = func(*args, **kwargs)
        print("方法调用后")
        return result
    return log_inner

def test(content):
    print(content)

# 创建闭包对象，将要装饰的函数对象作为参数传入
t = log_decorator(test)
t = ("hello world")

"""语法糖"""
@log_decorator
def test(content):
    print(content)

test("hello world")

"""无参无返回"""
def log_decorator(func):
    def log_inner(*args, **kwargs):
        print("方法调用前")
        func(*args, **kwargs)
        print("方法调用后")
    return log_inner

@log_decorator
def test():
    print("hello world")

if __name__ == '__main__':
    # 传统调用
    t = log_decorator(test)
    t()
    # 语法糖
    test()

"""有参无返回"""
def log_decorator(func):
    def log_inner(content):
        print("方法调用前")
        func(content)
        print("方法调用后")
    return log_innner

@log_decorator
def test(content):
    print(content)

if __name__ == '__main__':
    # 传统调用
    t = log_decorator(test)
    t("hello")
    # 语法糖
    test('hello')

"""无参有返回"""
def log_decorator(func):
    def log_inner():
        print("方法调用前")
        return func()
    return log_inner

@log_decorator
def test():
    return "hello world"

if __name__ == '__main__':
    # 传统调用
    t = log_decorator(test)
    t()
    # 语法糖
    test()

"""有参有返回"""
def log_decorator(func):
    def log_inner(content):
        print("方法调用前")
        return func(content)
    return log_inner

@log_decorator
def test(content):
    return content

if __name__ == '__main__':
    # 传统调用
    t = log_decorator(test)
    t('hello')
    # 语法糖
    test('hello')

"""可变参数"""
def log_decorator(func):
    def log_inner(*args, **kwargs):
        print("方法调用前")
        func(*args, **kwargs)
        print("方法调用后")
    return log_inner

@log_decorator
def test(*args, **kwargs):
    for arg in args:
        print(arg, end=' ')
    for key, value in kwargs.items():
        print((key, value), end=' ')

if __name__ == '__main__':
    # 传统调用
    t = log_decorator(test)
    t('hello', 'python', name='python', age=20)
    # 语法糖
    test('hello', 'python', name='python', age=20)

"""多个装饰器的使用"""
def log_decorator(func):
    def log_inner(*args, **kwargs):
        print("日志记录中...")
        func(*args, **kwargs)
    return log_inner

def cache_decorator(func):
    def cache_inner(*args, **kwargs):
        print("数据缓存中...")
        func(*args, **kwargs)
    return cache_inner

@log_decorator
@cache_decorator
def test(*args, **kwargs):
    for arg in args:
        print(arg, end=' ')
    for key, value in kwargs.items():
        print((key, value), end=' ')

if __name__ == '__main__':
    # 传统调用，从内向外
    cache = cache_decorator(test)
    log = log_decorator(cache)
    log("hello", "python", name='python', age=20)
    # 语法糖，从上往下
    test("hello", "python", name='python', age=20)
```
