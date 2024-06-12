class ConvertTime:
    def convertTime(current: str, correct: str) -> int:
        total = (int(correct[:2]) - int(current[:2])) * 60 + (int(correct[3:]) - int(current[3:]))
        count = 0
        for m in [60, 15, 5, 1]:
            count += total // m
            total %= m
        return count

    if __name__ == '__main__':
        current, correct = '02:30', '04:35'
        print(convertTime(current, correct))