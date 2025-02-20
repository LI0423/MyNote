class Solution(object):
    def strStr(self, haystack, needle):
        """
        :type haystack: str
        :type needle: str
        :rtype: int
        """
        if len(haystack) < len(needle):
            return -1
        
        n, m = len(haystack), len(needle)
        for i in range(0, n - m+1):
            a = i
            b = 0
            while b < m and haystack[a:a+1] == needle[b:b+1]:
                a += 1
                b += 1
            if b == m: return i

        return -1


if __name__ == '__main__':
    haystack = 'sadbutsad'
    needle = 'sad'
    s = Solution()
    s.strStr(haystack, needle)