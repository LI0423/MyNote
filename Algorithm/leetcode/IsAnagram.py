def isAnagram(s: str, t: str) -> bool:
    if len(s) != len(t): return False
    tmp = {}
    for i in s:
        tmp[i] = tmp.get(i, 0) + 1
    for x, y in tmp.items():
        if y != t.count(x):
            return False
    return True

isAnagram()