def hammingWeight(n: int) -> int:
        ret = sum(1 for i in range(32) if n & (1 << i)) 
        return ret

hammingWeight(11)