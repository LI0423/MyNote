class LargestPerimeter:
    def largestPerimeter(nums):
        nums.sort(reverse = True)
        for i in range(len(nums) - 2):
            if nums[i] < nums[i+1] + nums[i+2]:
                return nums[i] + nums[i+1] + nums[i+2]
        return 0

    if __name__ == '__main__':
        nums = [1, 2, 1, 10]
        print(largestPerimeter(nums))