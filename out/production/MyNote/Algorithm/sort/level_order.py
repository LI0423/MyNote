from collections import deque
from Algorithm.DataStructure.tree_node import TreeNode


# 层序遍历
def level_order(root: TreeNode | None) -> list[int]:
    # 初始化队列，加入根节点
    queue: deque(TreeNode) = deque()
    queue.append(root)
    # 初始化一个列表，用于保存遍历序列
    res = []
    while queue:
        # 队列出队
        node: TreeNode = queue.popleft()
        # 保存节点值
        res.append(node.val)
        if node.left is not None:
            # 左子节点入队
            queue.append(node.left)
        if node.right is not None:
            # 右子节点入队
            queue.append(node.right)
    return res
