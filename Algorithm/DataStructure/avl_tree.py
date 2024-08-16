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