class LargestOddNumber:
    def largestOddNumer(num):
        n = len(num)
        for i in range(n-1, -1, -1):
            if int(num[i]) % 2 != 0:
                return num[:i+1]
        return ""

    if __name__ == '__main__':
        num = '35427'
        print(largestOddNumer(num))