import collections


class LongestPalindrome:
    def longestPalindrome(s):
        ans = 0
        count = collections.Counter(s)
        for v in count.values():
            ans += v // 2 * 2
            if ans % 2 == 0 and v % 2 == 1:
                ans += 1
        return ans

    if __name__ == '__main__':
        s = 'abccccdd'
        print(longestPalindrome(s))