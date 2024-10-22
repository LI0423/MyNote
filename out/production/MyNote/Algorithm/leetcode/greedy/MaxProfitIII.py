class MaxProfitIII:
    def maxProfitIII(prices: list(), fee :int) -> int:
        buy = prices[0] + fee
        profit = 0
        for i in range(1, len(prices)):
            if prices[i] + fee < buy:
                buy = prices[i] + fee
            elif prices[i] > buy:
                profit += prices[i]- buy
                buy = prices[i]
        return profit