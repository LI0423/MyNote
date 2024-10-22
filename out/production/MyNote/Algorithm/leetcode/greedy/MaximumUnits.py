class MaximumUnits:
    def maximumUnits(boxTypes: list[list[int]], truckSize: int) -> int:
        boxTypes.sort(key= lambda x : x[1], reverse=True)
        count = 0
        for nums, units in boxTypes:
            if nums < truckSize:
                count += nums * units
                truckSize -= nums
            else:
                count += truckSize * units
                break
        return count

    if __name__ == '__main__':
        boxTypes = [[1,3],[2,2],[3,1]]
        truckSize = 4
        print(maximumUnits(boxTypes, truckSize))
