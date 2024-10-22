class RemoveDigit:
    def removeDigit(number: str, digit: str) -> str:
        n = len(number)
        res = ''
        for i in range(n):
            if number[i] == digit:
                tmp = number[:i] + number[i+1:n]
                res = max(res, tmp)
        return res

    def removeDigit2(number: str, digit: str) -> str:
        n = len(number)
        max = -1
        for i in range(n):
            if number[i] == digit:
                max = i
                if i == n - 1 or number[i + 1] > digit:
                    break
        return number[:max] + number[max + 1 :]

    if __name__ == '__main__':
        number = "1231"
        digit = '1'
        # print(removeDigit(number, digit))
        print(removeDigit2(number, digit))