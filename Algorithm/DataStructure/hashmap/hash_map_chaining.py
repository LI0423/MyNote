class Pair:
    def __init__(self, key: int, val: str) -> None:
        self.key = key
        self.val = val

# 基于链式地址实现的哈希表
class HashMapChaining:
    def __init__(self) -> None:
        self.size = 0   # 键值对数量
        self.capacity = 0   # 哈希表容量
        self.load_thres = 2.0 / 3.0 # 触发扩容因子阈值
        self.extend_ratio = 2   # 扩容倍数
        self.buckets = [[] for _ in range(self.capacity)]   # 桶数组

    # 哈希函数
    def hash_func(self, key: int) -> int:
        return key % self.capacity

    # 负载因子
    def load_factor(self) -> float:
        return self.size / self.capacity

    # 查询
    def get(self, key: int) -> str | None:
        index = self.hash_func(key)
        bucket = self.buckets[index]
        # 遍历桶，若找到key，则返回对应val
        for pair in bucket:
            if pair.key == key:
                return pair.val
        # 若未找到key，则返回None
        return None

    # 添加
    def put(self, key: int, val: str):
        # 当负载因子超过阈值时，执行扩容
        if self.load_factor() > self.load_thres:
            self.extend()
        index = self.hash_func(key)
        bucket = self.buckets[index]
        # 遍历桶，若遇到指定key，则更新对应val并返回
        for pair in bucket:
            if pair.key == key:
                pair.val = val
                return
        # 若无该key，则将键值对添加至尾部
        pair = Pair(key, val)
        bucket.append(pair)
        self.size += 1

    # 删除
    def remove(self, key: int):
        index = self.hash_func(key)
        bucket = self.buckets[index]
        # 遍历桶，从中删除键值对
        for pair in bucket:
            if pair.key == key:
                bucket.remove(pair)
                self.size -= 1
                break
    
    # 扩展哈希表
    def extend(self):
        # 暂存原哈希表
        buckets = self.buckets
        # 初始化扩容后的新哈希表
        self.capacity *= self.extend_ratio
        self.buckets = [[] for _ in range(self.capacity)]
        self.size = 0
        # 将键值对从原哈希表搬运至新哈希表
        for bucket in buckets:
            for pair in bucket:
                self.put(pair.key, pair.val)
    
    # 打印
    def print(self):
        for bucket in self.buckets:
            res = []
            for pair in bucket:
                res.append(str(pair.key) + ' -> ' + pair.val)
            print(res)
