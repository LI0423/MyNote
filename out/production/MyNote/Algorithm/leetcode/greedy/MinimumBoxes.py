class MinimumBoxes:
    def minimumBoxes(apple: list(), capacity: list()) -> int:
        s = sum(apple)
        capacity.sort(reversed = True)
        for i, x in enumerate(capacity, 1):
            s -= x
            if s <= 0:
                return i