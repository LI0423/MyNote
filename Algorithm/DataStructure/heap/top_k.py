import heapq

# 基于堆查找数组中最大的k个元素
def top_k_heap(nums: list[int], k: int) -> list[int]:
    # 初始化小顶堆
    heap = []
    # 将数组的前k个元素入堆
    for i in range(k):
        heapq.heappush(heap, nums[i])
    # 从第k+1个元素开始，保持堆的长度为k
    for i in range(k, len(nums)):
        # 若当前元素大于堆顶元素，则将堆顶元素出堆、当前元素入堆
        if nums[i] > heap[0]:
            heapq.heappop(heap)
            heapq.heappush(heap, nums[i])
    return heap