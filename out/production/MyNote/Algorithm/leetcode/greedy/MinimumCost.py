class MinimumCost:
    def minimumCost(cost: list[int]) -> int:
        cost.sort(key = lambda x : -x)
        res = 0
        n = len(cost)
        for i in range(n):
            if i % 3 != 2:
                res += cost[i]
        return res

    if __name__ == '__main__':
        cost = [1,2,3]
        res = minimumCost(cost)
        print(res)