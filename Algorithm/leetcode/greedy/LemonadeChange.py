class LemonadeChange:
    def lemonadeChange(bills):
        five, ten = 0, 0
        for bill in bills:
            if bill == 5:
                five += 1
            elif bill == 10:
                if five == 0:
                    return False
                five -= 1
                ten += 1
            else:
                if five > 0 and ten > 0:
                    five -= 1
                    ten -= 1
                elif five >= 3:
                    five -= 3
                else:
                    return False
        return True

    if __name__ == '__main__':
        bills = [5,5,5,10,20]
        print(lemonadeChange(bills))