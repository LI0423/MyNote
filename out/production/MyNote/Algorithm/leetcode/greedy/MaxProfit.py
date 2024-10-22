class MaxProfit:
    def maxProfit(arr) -> int:
        minprofit = int(1e9)
        maxprofit = 0
        for i in range(len(arr)):
            if arr[i] < minprofit:
                minprofit = arr[i]
            elif maxprofit < arr[i] - minprofit:
                maxprofit = arr[i] - minprofit
        return maxprofit

    
    if __name__ == '__main__':
        arr = [7,1,5,3,6,4]
        res = maxProfit(arr)
        print(res)