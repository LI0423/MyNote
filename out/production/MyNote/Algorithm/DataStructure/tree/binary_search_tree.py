from tree_node import TreeNode

# 二叉搜索树
# 对于根节点，左子树中所有节点的值 < 根节点的值 < 右子树中所有节点的值。
# 任意节点的左右子树也是二叉搜索树。
class BinarySearchTree:

    # 查找节点
    def search(self, num: int) -> TreeNode | None:
        cur = self._root
        # 循环查找，越过叶节点后跳出
        while cur is not None:
            # 目标节点在cur的右子树中
            if cur.val < num:
                cur = cur.right
            # 目标节点在cur的左子树中
            elif cur.val > num:
                cur = cur.left
            # 找到目标节点，跳出循环
            else:
                break
        # 返回目标节点
        return cur

    # 插入节点，二叉搜索树不允许存在重复节点，否则违反定义
    def insert(self, num: int):
        # 若树为空，则初始化根节点
        if self._root is None:
            self._root = TreeNode(num)
            return
        # 循环查找，越过叶节点后直接返回
        cur, pre = self._root, None
        while cur is not None:
            # 找到重复节点，直接返回
            if cur.val == num:
                return
            # 保存父节点
            pre = cur
            # 插入位置在cur的右子树
            if cur.val < num:
                cur = cur.right
            # 插入位置在cur的左子树
            elif cur.val > num:
                cur = cur.left
        # 插入节点
        node = TreeNode(num)
        if pre.val < num:
            pre.right = node
        else:
            pre.left = node

    # 删除
    def remove(self, num: int):
        # 若树为空，直接提前返回
        if self._root is None:
            return
        
        # 循环查找，越过叶节点后跳出
        cur, pre = self._root, None
        while cur is not None:
            # 找到待删除节点后跳出
            if cur.val == num:
                break
            # 保存待删除节点的父节点
            pre = cur
            # 待删除节点在cur的右子树中
            if cur.val < num:
                cur = cur.right
            # 待删除节点在cur的左子树中
            else:
                cur = cur.left
        # 若没有待删除节点，直接返回
        if cur is None:
            return

        # 待删除节点只有一个子节点或者没有子节点
        if cur.left is None or cur.right is None:
            # 当子节点数量 = 0 / 1时，child = null / 该子节点
            child = cur.left or cur.right
            # 删除节点cur
            if cur != self._root:
                if pre.left == cur:
                    pre.left = child
                else:
                    pre.right = child
            else:
                # 若删除节点为根节点，则重新指定根节点
                self._root = child
        # 子节点数量 = 2
        else: 
            # 获取中序遍历中cur的下一个子节点
            tmp: TreeNode = cur.right
            while tmp.left is not None:
                tmp = tmp.left
            # 递归删除节点tmp
            self.remove(tmp.val)
            # 用 tmp 覆盖cur
            cur.val = tmp.val
