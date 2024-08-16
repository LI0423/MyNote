
class BubbleSort:

    def bubble_sort(items, comp=lambda x, y : x > y):
        items = items[:]
        for i in range(len(items) - 1):
            swapped = False
            for j in range(len(items) - i - 1):
                if comp(items[j], items[j+1]):
                    items[j], items[j + 1] = items[j+1], items[j]
                    swapped = True
            if not swapped:
                break
        return items

    def bubble_sort2(items, comp=lambda x, y: x > y):
        items = items[:]
        for i in range(len(items) - 1):
            swapped = False
            for j in range(len(items) - 1 -i):
                if comp(items[j], items[j + 1]):
                    items[j], items[j + 1] = items[j + 1], items[j]
                    swapped = True
            if swapped:
                swapped = False
                for j in range(len(items) - 2 - i, i, -1):
                    if comp(items[j - 1], items[j]):
                        items[j], items[j - 1] = items[j - 1], items[j]
                        swapped = True
            if not swapped:
                break
        return items