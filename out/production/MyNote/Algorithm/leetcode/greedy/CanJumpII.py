class CanJumpII:
    def canJumpII(nums):
        steps, maxPosition, next = 0, 0, 0
        for i in range(len(nums) - 1):
            maxPosition = max(maxPosition, i + nums[i])
            if i == next:
                next = maxPosition
                steps += 1
        return steps

    if __name__ == '__main__':
        nums = [2,3,1,1,4]
        res = canJumpII(nums)
        print(res)