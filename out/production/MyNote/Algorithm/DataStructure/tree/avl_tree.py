class TreeNode:
    def __init__(self, val) -> None:
        # 节点值
        self.val: int = val
        # 节点高度，
        self.height: int = 0
        # 左子节点引用
        self.left: TreeNode | None = None
        # 右子节点引用
        self.right: TreeNode | None = None


# 二叉搜索树
class AvlTree:
    def height(self, node: TreeNode | None):
        if node is not None:
            return node.height
        return -1

    # 更新节点高度
    def update_height(self, node: TreeNode | None):
        node.height = max([self.height(node.left), self.height(node.right)]) + 1

    # 获取平衡因子
    def balance_factor(self, node: TreeNode | None) -> int:
        # 空气平衡节点因子为0
        if node is None:
            return 0
        # 平衡节点因子 = 左子树高度 - 右子树高度
        return self.height(node.left) - self.height(node.right)
    
    # 右旋操作
    def right_rotate(self, node: TreeNode | None) -> TreeNode:
        child = node.left
        grand_child = child.right
        # 以child为原点，将node向右旋转
        child.right = node
        node.left = grand_child
        # 更新节点高度
        self.update_height(node)
        self.update_height(node.right)
        # 返回旋转后子树的根节点
        return child

    # 左旋操作
    def left_rotate(self, node: TreeNode | None) -> TreeNode:
        child = node.right
        grand_child = child.left
        # 以child为原点，将node向左旋转
        child.left = node
        node.right = grand_child
        # 更新节点高度
        self.update_height(node)
        self.update_height(child)
        # 返回旋转后子树的根节点
        return child

    # 失衡节点的平衡因子 > 1 左偏树，子节点的平衡因子 >= 0 右旋
    #                 > 1 左偏树，              < 0 先左旋后右旋
    #                 < -1 右偏树，             <= 0 左旋
    #                 < -1 右偏树，             > 0 先右旋后左旋
    # 执行旋转操作，使该子树重新恢复平衡
    def rotate(self, node: TreeNode | None) -> TreeNode:
        # 获取节点node的平衡因子
        balance_factor = self.balance_factor(node)
        # 左偏树
        if balance_factor > 1:
            if self.balance_factor(node.left) >= 0:
                # 右旋
                return self.right_rotate(node)
            else:
                node.left = self.left_rotate(node.left)
                return self.right_rotate(node)
        # 右偏树
        elif balance_factor < -1:
            if self.balance_factor(node.right) <= 0:
                # 左旋
                return self.left_rotate(node)
            else:
                # 先右旋后左旋
                node.right = self.right_rotate(node)
                return self.left_rotate(node)
        # 平衡树，无需旋转，直接返回
        return node

    # 插入节点
    def insert(self, val):
        self._root = self.insert_hepler(self._root, val)

    # 递归插入节点
    def insert_helper(self, node: TreeNode | None, val: int) -> TreeNode:
        if node is None:
            return TreeNode(val)
        # 查找插入位置并插入节点
        if val < node.val:
            node.left = self.insert_helper(node.left, val)
        elif val > node.val:
            node.right = self.insert_helper(node.right, val)
        else:
            # 重复节点不插入，直接返回
            return node
        # 更新节点高度
        self.update_height(node)
        # 执行旋转操作，使该子树重新恢复平衡
        return self.rotate(node)

    # 删除节点
    def remove(self, val: int):
        self._root = self.remove_helper(self._root, val)

    # 递归删除节点
    def remove_helper(self, node: TreeNode | None, val: int) -> TreeNode:
        if node is None:
            return None
        # 查找删除节点
        if val < node.val:
            node.left = self.remove_helper(node.left, val)
        elif val > node.val:
            node.right = self.remove_helper(node.right, val)
        else:
            if node.left is None or node.right is None:
                child = node.left or node.right
                if child is None:
                    return None
                # 子节点数量 = 1，直接删除node并返回
                else:
                    node = child
            else:
                # 子节点数量 = 2， 则将中序遍历的下个节点删除，并用该节点替换当前节点
                tmp = node.right
                while tmp.left is not None:
                    tmp = tmp.left
                node.right = self.remove_helper(node.right, tmp.val)
                node.val = tmp.val
        # 更新节点高度
        self.update_height(node)
        # 执行旋转操作，使该子树重新恢复平衡
        return self.rotate(node)
