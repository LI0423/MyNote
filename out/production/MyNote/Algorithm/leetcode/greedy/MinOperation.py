class MinOperation:
    def minOperation(nums):
        res = 0
        for i in range(1, len(nums)):
            if nums[i] <= nums[i-1]:
                res += nums[i - 1] - nums[i] + 1
                nums[i] = nums[i - 1] + 1
        return res

    if __name__ == '__main__':
        nums = [1,5,2,4,1]
        print(minOperation(nums))