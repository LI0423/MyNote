# 二分查找（双闭区间）
def binary_search(nums: list[int], target: int) -> int:
    # 初始化双闭区间[0, n-1]，即left，right分别指向首尾元素
    left, right = 0, len(nums) - 1
    # 循环，当搜索空间为空时跳出（当left > right时为空）
    while left <= right:
        # 计算中点
        mid = (left + right) >> 1
        # target 在区间 [mid + 1, right]中
        if nums[mid] < target:
            left = mid + 1
        # target 在区间 [left, mid - 1]中
        elif nums[mid] > target:
            right = mid - 1
        else:
            # 找到目标元素，返回索引
            return mid
    # 未找到目标元素，返回-1
    return -1

# 二分查找（左闭右开区间）
def binary_search_lcro(nums: list[int], target: int) -> int:
    # 初始化左闭右开区[0, n)，即left，right分别指向首元素、尾元素+1
    left, right = 0, len(nums)
    # 循环，当搜索区间为空时跳出（当left = right时为空）
    while left < right:
        mid = (left + right) // 2
        # target 在区间 [mid + 1, right)中
        if nums[mid] < target:
            left = right + 1
        # target 在区间 [left, mid)中
        elif nums[mid] > target:
            right = mid
        else:
            return mid
    return -1 