from Algorithm.DataStructure.list_node import ListNode


class LinkedListQueue:
    def __init__(self):
        self._front: ListNode | None = None
        self._rear: ListNode | None = None
        self._size: int = 0

    # 获取队列长度
    def size(self) -> int:
        return self._size

    # 判断队列是否为空
    def is_empty(self) -> bool:
        return self._size == 0

    # 入队
    def push(self, num: int):
        node = ListNode(num)
        # 如果队列为空，则令头、尾节点都指向该节点
        if self._front is None:
            self._front = node
            self._rear = node
        # 如果队列不为空，则将该节点添加到尾节点后
        else:
            self._rear.next = node
            self._rear = node
        self._size += 1

    # 出队
    def pop(self) -> int:
        num = self.peek()
        self._front = self._front.next
        self._size -= 1
        return num
    
    # 访问队首元素
    def peek(self) -> int:
        if self.is_empty():
            raise IndexError('队列为空')
        return self._front.val

    # 转换为列表
    def to_list(self) -> list[int]:
        queue = []
        tmp = self._front
        while tmp:
            queue.append(tmp.val)
            tmp = tmp.next
        return queue