class ArrayQueue:
    def __init__(self, size: int) -> None:
        self._nums: list[int] = [0] * size  # 用于存储队列元素的数组
        self._front: int = 0    # 队首指针，指向队首元素
        self._size: int = 0 # 队列长度

    # 获取队列的容量
    def capacity(self) -> int:
        return len(self._nums)

    # 获取队列的长度
    def size(self) -> int:
        return self._size

    # 判断队列是否为空
    def is_empty(self) -> bool:
        return self._size == 0

    # 入队
    def push(self, num: int):
        if self._size == self.capacity():
            raise IndexError("队列已满")
        # 计算队尾指针，指向队尾索引 + 1
        # 通过取余操作实现rear越过数组尾部后回到头部
        rear: int = (self._front + self._size) % self.capacity()
        # 将num添加到队尾
        self._nums[rear] = num
        self._size += 1

    # 出队
    def pop(self) -> int:
        num = self.peek()
        # 队首指针向后移动一位，若越过尾部，则返回到数组头部
        self._front = (self._front + 1) % self.capacity()
        self._size -= 1
        return num

    # 访问队首元素
    def peek(self) -> int:
        if self.is_empty:
            raise IndexError("队列为空")
        return self._nums[self._front]

    # 转换为列表
    def to_list(self) -> list[int]:
        res = [0] * self.size()
        j: int = self._front
        for i in range(self.size()):
            res[i] = self._nums[(j % self.capacity())]
            j += 1
        return res


    