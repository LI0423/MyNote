class MinimumMoves:
    def minimumMoves(s: str) -> int:
        ans = i = 0
        while i < len(s):
            if s[i] == 'X':
                ans += 1
                i += 3
            else:
                i += 1
        return ans

    if __name__ == '__main__':
        s = 'XXOX'
        print(minimumMoves(s))