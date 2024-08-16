class ArrayDeque:

    def __init__(self, capacity) -> None:
        self._nums: list[int] = [0] * capacity
        self._front: int = 0
        self._size: int = 0

    # 获取双向队列的容量
    def capacity(self) -> int:
        return len(self._nums)

    # 获取双向队列的长度
    def size(self) -> int:
        return self._size

    # 判断双向队列是否为空
    def is_empty(self) -> bool:
        return self._size == 0

    # 计算环形数组索引
    def index(self, i: int) -> int:
        # 通过取余操作实现数组首尾相连
        # 当 i 越过数组尾部后，回到头部
        # 当 i 越过数组头部后，回到尾部
        return (i + self.capacity()) % self.capacity()

    # 队首入队
    def push_first(self, num: int):
        if self._size == self.capacity():
            print('双向队列已满')
            return
        # 队首指针向左移动一位
        # 通过取余操作实现 front 越过数组头部后回到尾部
        self._front = self.index(self._front - 1)
        # 将 num 添加到队首
        self._nums[self._front] = num
        self._size += 1

    # 队尾入队
    def push_last(self, num: int):
        if self._size == self.capacity():
            print('双向队列已满')
            return
        # 计算队尾指针，指向队尾索引+1
        rear = self.index(self._front + self._size)
        # 将num添加到队尾
        self._nums[rear] = num
        self._size += 1

    # 队首出队
    def pop_first(self) -> int:
        num = self.peek_first()
        # 队首指针向后移一位
        self._front = self.index(self._front + 1)
        self._size -= 1
        return num

    # 队尾出队
    def pop_last(self) -> int:
        num = self.peek_last()
        self._size -= 1
        return num

    # 访问队首元素
    def peek_first(self) -> int:
        if self.is_empty():
            raise IndexError('双向队列为空')
        return self._nums[self._front]

    # 访问队尾元素
    def peek_last(self) -> int:
        if self.is_empty():
            raise IndexError('双向队列为空')
        # 计算尾元素索引
        last = self.index(self._front + self._size - 1)
        return self._nums[last]

    # 转换为数组
    def to_array(self) -> list[int]:
        res = []
        for i in range(self._size):
            res.append(self._nums[self.index(self._front + i)])
        return res
        


        