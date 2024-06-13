class MinMaxDifference:
    def minMaxDifference(num: int) -> int:
        mx = num
        s = str(num)
        for c in s:
            if c != '9':
                mx = int(s.replace(c, '9'))
                break
        return mx - int(s.replace(s[0], 's'))