class Pair:
    def __init__(self, key: int, val: str) -> None:
        self.key = key
        self.val = val

# 基于数组实现的哈希表
class ArrayHashMap:
    def __init__(self) -> None:
        self.buckets: list[Pair | None] = [None] * 100

    # 哈希函数
    def hash_func(self, key: int) -> int:
        index = key % 100
        return index

    # 查询
    def get(self, key: int) -> int:
        index: int = self.hash_func(key)
        pair: Pair = self.buckets[index]
        if pair is None:
            return None
        return pair.val

    # 添加
    def put(self, key: int, val: str):
        pair = Pair(key, val)
        index: int = self.hash_func(key)
        self.buckets[index] = pair

    # 删除
    def remove(self, key: int):
        index: int = self.hash_func(key)
        self.buckets[index] = None

    # 获取所有键值对
    def entry_set(self) -> list[Pair]:
        result: list[Pair] = []
        for pair in self.buckets:
            if pair is not None:
                result.append(pair)
        return result

    # 获取所有键
    def key_set(self) -> list[int]:
        result: list[int] = []
        for pair in self.buckets:
            if pair is not None:
                result.append(pair.key)
        return result

    # 获取所有值
    def value_set(self) -> list[str]:
        result: list[int] = []
        for pair in self.buckets:
            if pair is not None:
                result.append(pair.val)
        return result

    def print(self):
        for pair in self.buckets:
            if pair is not None:
                print(pair.key, '->', pair.val)

    