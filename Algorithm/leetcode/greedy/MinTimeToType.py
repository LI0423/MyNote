class MinTimeToType:
    def minTimeToType(str):
        res = min(str[0] - 'a' + 1, 26 - str[0] - 'a' + 1)
        for i in range(1, len(str)):
            res += min(abs(str[i] - str[i - 1]) + 1, 27 - abs(str[i] - str[i - 1]))
        return res
    
    if __name__ == '__main__':
        str = 'bza'
        print(minTimeToType(str))