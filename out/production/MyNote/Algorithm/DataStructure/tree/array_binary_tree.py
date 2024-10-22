class ArrayBinaryTree:
    def __init__(self, arr: list[int | None]) -> None:
        self._tree = list(arr)

    # 列表容量
    def size(self):
        return len(self._tree)

    # 获取索引为i节点的值
    def val(self, i: int) -> int | None:
        # 若索引越界，则返回None，代表空位
        if i < 0 or i >= self.size():
            return None
        return self._tree[i]

    # 获取索引为i节点的子节点的索引
    def left(self, i: int) -> int | None:
        return 2 * i + 1

    def right(self, i: int) -> int | None:
        return 2 * i + 2

    def parent(self, i: int) -> int | None:
        return (i - 1) // 2

    # 层序遍历，直接遍历数组
    def level_order(self) -> list[int]:
        self.res = []
        for i in range(self.size()):
            if self.val(i) is not None:
                self.res.append(self.val(i))
        return self.res

    # 深度优先遍历
    def dfs(self, i: int, order: str):
        if self.val(i) is None:
            return
        # 前序遍历
        if order == 'pre':
            self.res.append(self.val(i))
        self.dfs(self.left(i), order)
        # 中序遍历
        if order == 'in':
            self.res.append(self.val(i))
        self.dfs(self.right(i), order)
        # 后序遍历
        if order == 'post':
            self.res.append(self.val(i))

    # 前序遍历
    def pre_order(self) -> list[int]:
        self.res = []
        self.dfs(0, order='pre')
        return self.res
    
    # 中序遍历
    def in_order(self) -> list[int]:
        self.res = []
        self.dfs(0, order='in')
        return self.res

    # 后序遍历
    def post_order(self) -> list[int]:
        self.res = []
        self.dfs(0, order='post')
        return self.res
