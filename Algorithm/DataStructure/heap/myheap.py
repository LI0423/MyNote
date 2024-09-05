
# 堆是一种满足特定条件的完全二叉树：
# 小顶堆（min heap）：任意节点的值 <= 其子节点的值。
# 大顶堆（max heap）：任意节点的值 >= 其子节点的值。
# 堆通常用于实现优先队列，大顶堆相当于元素按照从大到小的顺序出队的优先队列。
    # 初始化小顶堆
    # min_heap, flag = [], 1
    # # 初始化大顶堆
    # max_heap, flag = [], -1

    # # 元素入堆
    # heapq.heappush(max_heap, flag * 1)
    # heapq.heappush(max_heap, flag * 3)
    # heapq.heappush(max_heap, flag * 2)
    # heapq.heappush(max_heap, flag * 5)
    # heapq.heappush(max_heap, flag * 4)

    # # 获取堆顶元素
    # peek: int = flag * max_heap[0] # 5

    # # 堆顶元素出堆
    # # 出堆元素会形成一个从大到小的序列
    # val = flag * heapq.heappop(max_heap) # 5
    # val = flag * heapq.heappop(max_heap) # 4
    # val = flag * heapq.heappop(max_heap) # 3
    # val = flag * heapq.heappop(max_heap) # 2
    # val = flag * heapq.heappop(max_heap) # 1

    # # 获取堆大小
    # size: int = len(max_heap)

    # # 判断堆是否为空
    # is_empty: bool = not max_heap

    # # 输入列表并建堆
    # min_heap: list[int] = [1, 3, 2, 5, 4]
    # heapq.heapify(min_heap)

class MyHeap:
    def __init__(self, nums: list[int]) -> None:
        self.max_heap = nums
        for i in range(self.parent(self.size() - 1), -1, -1):
            self.sift_down(i)
    # 初始化小顶堆
    min_heap, flag = [], 1
    # 初始化大顶堆
    max_heap, flag = [], -1

    # 获取左子节点的索引
    def left(self, i: int) -> int:
        return 2 * i + 1

    # 获取右子节点的索引
    def right(self, i: int) -> int:
        return 2 * i + 2

    # 获取父节点的索引
    def parent(self, i: int) -> int:
        # 向下整除
        return (i - 1) // 2

    # 访问堆顶元素
    def peek(self) -> int:
        return self.max_heap[0]

    # 元素入堆
    def push(self, val: int):
        # 添加元素
        self.max_heap.append(val)
        # 从底至顶堆化
        self.sift_up(self.size() - 1)

    # 从节点i开始，从底至顶堆化
    def sift_up(self, i: int):
        while True:
            # 获取节点i的父节点
            p = self.parent(i)
            # 当“越过根节点”或“节点无须修复”时，结束堆化
            if p < 0 or self.max_hep[i] <= self.max_heap[p]:
                break
            # 交换两节点
            self.swap(i, p)
            # 循环向上堆化
            i = p

    # 元素出堆
    def pop(self) -> int:
        # 判空处理
        if self.is_empty():
            raise IndexError("堆为空")
        # 交换根节点与最右叶节点（交换首元素与尾元素）
        self.swap(0, self.size() - 1)
        # 删除节点
        val = self.max_heap.pop()
        # 从顶至底堆化
        self.sift_down(0)
        # 返回堆顶元素
        return val

    # 从节点i开始，从顶至底堆化
    def sift_down(self, i: int):
        while True:
            # 判断节点 i，l，r 中最大的节点，记为 ma
            l, r, ma = self.left(i), self.right(i), i
            if l < self.size() and self.max_heap[l] > self.max_heap[ma]:
                ma = l
            if r < self.size() and self.max_heap[r] > self.max_heap[ma]:
                ma = r
            # 若节点 i 最大或索引 l，r 越界，则无须继续堆化，跳出
            if ma == i:
                break
            # 交换两节点
            self.swap(i, ma)
            # 循环向下堆化
            i = ma
    
    

    
