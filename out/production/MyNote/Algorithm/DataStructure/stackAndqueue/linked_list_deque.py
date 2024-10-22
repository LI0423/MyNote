class ListNode:
    def __init__(self, val: int) -> None:
        self.val: int = val
        self.next: ListNode | None = None
        self.prev: ListNode | None = None

class LinkedListDeque:
    # 基于双向链表实现的双向队列

    def __init__(self) -> None:
        self._front: ListNode | None = None
        self._rear: ListNode | None = None
        self._size: int = 0

    # 获取双向队列长度
    def size(self) -> int:
        return self._size

    # 判断双向队列是否为空
    def is_empty(self) -> bool:
        return self._size == 0

    # 入队
    def push(self, num: int, is_front: bool):
        node = ListNode(num)
        # 若链表为空，则令front和rear都指向node
        if self.is_empty():
            self._front = self._rear = node
        # 队首入队
        elif is_front:
            # 将node添加至链表头部
            self._front.prev = node
            node.next = self._front
            # 更新头节点
            self._front = node
        # 队尾入队
        else:
            # 将node添加到链表尾部
            self._rear.next = node
            node.next = self._rear
            # 更新尾节点
            self._rear = node
        # 更新队列长度
        self._size += 1

    # 队首入队
    def push_first(self, num: int):
        self.push(num, True)

    # 队尾入队
    def push_last(self, num: int):
        self.push(num, False)

    # 出队
    def pop(self, is_front: bool):
        if self.is_empty():
            raise IndexError('双向队列为空')
        # 队首出队
        if is_front:
            # 暂存头节点值
            val: int = self._front.val
            # 删除头节点
            fnext: ListNode | None = self._front.next
            if fnext != None:
                fnext.prev = None
                self._front.next = None
            # 更新头节点
            self._front = fnext
        # 队尾出队
        else:
            # 暂存尾节点
            val: int = self._rear.val
            # 删除尾节点
            rprev: ListNode | None = self._rear.prev
            if rprev != None:
                rprev.next = None
                self._rear.prev = None
            # 更新尾节点
            self._rear = rprev
        # 更新队列长度
        self._size -= 1
        return val
    
    # 队首出队
    def pop_first(self) -> int:
        return self.pop(True)

    # 队尾出队
    def pop_last(self) -> int:
        return self.pop(False)

    # 访问队首元素
    def peek_first(self) -> int:
        if self.is_empty():
            raise IndexError('双向队列为空')
        return self._front.val
    
    # 访问队尾元素
    def peek_last(self) -> int:
        if self.is_empty():
            raise IndexError('双向队列为空')

    # 返回数组用于打印
    def to_array(self) -> list[int]:
        node = self._front
        res = [0] * self.size()
        for i in range(self.size()):
            res[i] = node.val
            node = node.next
        return res