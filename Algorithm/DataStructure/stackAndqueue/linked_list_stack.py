from Algorithm.DataStructure.list_node import ListNode


class LinkedListStack:
    # 基于链表实现栈
    def __init__(self) -> None:
        self._peek: ListNode | None = None
        self._size: int = 0

    # 获取栈的长度
    def size(self) -> int:
        return self._size

    # 判断栈是否为空
    def is_empty(self) -> bool:
        return self._size == 0

    # 入栈
    def push(self, val: int):
        node = ListNode(val)
        node.next = self._peek
        self._peek = node
        self._size += 1
    
    # 出栈
    def pop(self) -> int:
        num = self.peek()
        self._peek = self._peek.next
        self._size -= 1
        return num

    # 访问栈顶元素
    def peek(self) -> int:
        if self.is_empty():
            raise IndexError("栈为空")
        return self._peek.val

    # 转化为列表
    def to_list(self) -> list[int]:
        arr = []
        node = self._peek
        while node:
            arr.append(node.val)
            node = node.next
        arr.reverse()
        return arr
    