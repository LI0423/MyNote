class MinMovesToSeat:
    def minMovesToSeat(seats: list[int], students: list[int]):
        seats.sort()
        students.sort()
        ans = 0
        for i in range(len(seats)):
            ans += abs(seats[i] - students[i])
        return ans

    if __name__ == '__main__':
        seats = [4,1,5,9]
        students = [1,3,2,6]
        print(minMovesToSeat(seats, students))