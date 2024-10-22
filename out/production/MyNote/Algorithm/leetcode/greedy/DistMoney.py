class DistMoney:
    def distMoney(money: int, children: int) -> int:
        if money < children:
            return -1
        tmp = children * 8 - money
        if tmp == 4:
            return children - 2
        if tmp < 0:
            return children - 1
        return (money - children) // 7

    if __name__ == '__main__':
        money , children = 20 , 3
        print(distMoney(money, children))