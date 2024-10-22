class BalancedStringSplit:
    def balancedStringSplit(s):
        count, d = 0, 0
        for c in s:
            if c == 'R':
                d += 1
            else:
                d -= 1
            if d == 0:
                count += 1
        return count
    
    if __name__ == '__main__':
        s = 'RLRRLLRLRL'
        res = balancedStringSplit(s)
        print(res)