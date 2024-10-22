class Pair:
    def __init__(self, key: int, val: str) -> None:
        self.key = key
        self.val = val

# 开放寻址哈希表——线性探测
# 线性探测容易产生“聚集现象”，数组中连续被占用的位置越长，这些连续位置发生哈希冲突的可能性越大，从而进一步促使该位置的聚堆生长，形成恶性循环。
class HashMapOpenAddressing:
    def __init__(self) -> None:
        self.size = 0   # 键值对数量
        self.capacity = 4   # 哈希表数量
        self.load_thres = 2.0 / 3.0 # 触发扩容的负载因子阈值
        self.extend_ratio = 2   # 扩容倍数
        self.buckets: list[Pair | None] = [None] * self.capacity    # 桶数组
        # 不能在开放寻址哈希表中直接删除元素，删除元素会在数组内产生一个空桶None，当查询元素时，线性探测到该空桶就会返回，
        # 因此在空桶下的元素都无法再被访问到，程序可能误判这些元素不存在。
        self.TOMBSTONE = Pair(-1, '-1') # 删除标记

    # 哈希函数
    def hash_func(self, key) -> int:
        return key % self.capacity

    # 负载因子
    def load_factor(self) -> float:
        return self.size / self.capacity

    # 搜索key对应的桶索引
    def find_bucket(self, key: int) -> int:
        index = self.hash_func(key)
        first_tombstone = -1
        # 线性探测，当遇到空桶时跳出
        while self.buckets[index] is not None:
            # 若遇到key，返回对应的桶索引
            if self.buckets[index].key == key:
                # 若之前遇到了删除标记，则将键值对移动到该索引处
                if first_tombstone != -1:
                    self.buckets[first_tombstone] = self.buckets[index]
                    self.buckets[index] = self.TOMBSTONE
                    # 返回移动后的桶索引
                    return first_tombstone
                # 返回桶索引
                return index
            # 记录遇到的首个删除标记
            if first_tombstone == -1 and self.buckets[index] is self.TOMBSTONE:
                first_tombstone = index
            # 计算桶索引，越过尾部则返回头部
            index = (index + 1) % self.capacity
        # 若key不存在，则返回添加点的索引
        return index if first_tombstone == -1 else first_tombstone

    # 查询
    def get(self, key: int) -> str:
        # 搜索key对应的桶索引
        index = self.find_bucket(key)
        # 若找到键值对，则返回对应val
        if self.buckets[index] not in [None, self.TOMBSTONE]:
            return self.buckets[index].val
        # 若键值对不存在，则返回None
        return None

    # 添加
    def put(self, key: int, val: str):
        # 当负载因子超过阈值时，进行扩容
        if self.load_factor() > self.extend_ratio:
            self.extend()
        # 搜索key对应的桶索引
        index = self.find_bucket(key)
        # 若找到键值对，则覆盖val并返回
        if self.buckets[index] not in [None, self.TOMBSTONE]:
            self.buckets[index].val = val
            return
        # 若键值对不存在，则添加该键值对
        self.buckets[index] = Pair(key, val)
        self.size += 1
    
    # 删除
    def remove(self, key: int):
        # 搜索key对应的桶索引
        index = self.find_bucket(key)
        # 如果找到键值对，则用删除标记覆盖它
        if self.buckets[index] not in [None, self.TOMBSTONE]:
            self.buckets[index] = self.TOMBSTONE
            self.size -= 1
    
    # 扩容
    def extend(self):
        # 暂存原哈希表
        buckets_tmp = self.buckets
        # 初始化扩容后的新哈希表
        self.capacity *= self.extend_ratio
        self.buckets = [None] * self.capacity
        self.size = 0
        # 将键值对从原哈希表搬运至新哈希表
        for pair in buckets_tmp:
            if pair not in [None, self.TOMBSTONE]:
                self.put(pair.key, pair.val)

    def print(self):
        for pair in self.buckets:
            if pair is None:
                print('None')
            elif pair is self.TOMBSTONE:
                print('TOMBSTONE')
            else:
                print(pair.key, '->', pair.val)

