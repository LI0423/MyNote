class CanJump:
    def canJump(nums):
        most = 0
        for i in range(len(nums)):
            if i <= most:
                most = max(most, i + nums[i])
                if most >= len(nums) - 1:
                    return True
        return False

    if __name__ == '__main__':
        nums = [2,3,1,1,4]
        res = canJump(nums)
        print(res)