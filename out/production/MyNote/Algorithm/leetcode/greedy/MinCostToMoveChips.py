class MinCostToMoveChips:
    def minCostToMoveChips(position):
        res_1, res_2 = 0, 0
        for i in position:
            if i % 2 == 0:
                res_2 += 1
            else:
                res_1 += 1
        return min(res_1, res_2)
    
    if __name__ == '__main__':
        position = [1, 2, 3]
        res = minCostToMoveChips(position)
        print(res)