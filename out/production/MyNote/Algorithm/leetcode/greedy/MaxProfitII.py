class MaxProfitII:
    def maxProfit(prices):
        ans = 0
        for i in range(1, len(prices)):
            if prices[i] > prices[i - 1]:
                ans += prices[i] - prices[i - 1]
        return ans

    if __name__ == '__main__':
        prices = [7,1,5,3,6,4]
        ans = maxProfit(prices)
        print(ans)