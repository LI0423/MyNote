# 层：从顶至底递增，根节点所在层为1.
# 度：节点的子节点数量。在二叉树中，度的取值范围是0、1、2.
# 高度：从根节点到最远节点所经过的边的数量。
# 深度：从根节点到该节点所经过的边的数量。
# 高度：从距离该节点最远的叶节点到该节点所经过的边的数量。
class TreeNode:
    def __init__(self, val) -> None:
        self.val: int = val
        self.left: TreeNode | None = None
        self.right: TreeNode | None = None

# 初始化节点
n1 = TreeNode(val=1)
n2 = TreeNode(val=2)
n3 = TreeNode(val=3)
n4 = TreeNode(val=4)
n5 = TreeNode(val=5)
# 构建节点之间的引用
n1.left = n2
n1.right = n3
n2.left = n4
n2.right = n5

# 插入节点P
p = TreeNode(0)
n1.left = p
p.left = n2
# 删除节点P
n1.left = n2