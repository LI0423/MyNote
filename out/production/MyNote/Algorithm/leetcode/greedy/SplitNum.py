class SplitNum:
    def splitNum(num: int) -> int:
        stnum = "".join(sorted(str(num)))
        num1, num2 = int(stnum[::2]), int(stnum[1::2])
        return num1 + num2

    if __name__ == '__main__':
        num = 4325
        print(splitNum(num))