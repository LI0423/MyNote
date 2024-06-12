class MinSubsequence:
    def minSubsequence(nums: list) -> list:
        nums.sort(reverse=True)
        total, min = sum(nums), 0
        for i, num in enumerate(nums):
            min += num
            if min > total - min:
                return nums[:i+1]

    if __name__ == '__main__':
        nums = [4,3,10,9,8]
        print(minSubsequence(nums))