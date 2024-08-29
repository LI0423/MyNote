class ArrayStack:
    def __init__(self) -> None:
        self._stack: list[int] = []

    # 获取栈的大小
    def size(self) -> int:
        return len(self._stack)

    # 判断栈是否为空
    def is_empty(self) -> bool:
        return len(self._stack) == 0

    # 入栈
    def push(self, item: int):
        self._stack.append(item)

    # 出栈
    def pop(self) -> int:
        if self.is_empty():
            raise IndexError("栈为空")
        return self._stack.pop()

    # 访问栈顶元素
    def peek(self) -> int:
        if self.is_empty():
            raise IndexError("栈为空")
        return self._stack[-1]

    # 转换为列表
    def to_list(self) -> list[int]:
        return self._stack