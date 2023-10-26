package Algorithm.search;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class UnionFind {
    public static class Element<V>{
        public V value;
        public Element(V value) {
            this.value = value;
        }
    }

    public static class UnionFindSet<V> {
        public HashMap<V, Element<V>> elementHashMap;
        public HashMap<Element<V>, Element<V>> fatherMap;
        public HashMap<Element<V>, Integer> sizeMap;
        public UnionFindSet(List<V> list) {
            for (V value : list) {
                Element<V> element = new Element<>(value);
                elementHashMap.put(value, element);
                fatherMap.put(element, element);
                sizeMap.put(element, 1);
            }
        }

        public void union(V a, V b) {
            if (elementHashMap.containsKey(a) && elementHashMap.containsKey(b)) {
                Element<V> headA = elementHashMap.get(a);
                Element<V> headB = elementHashMap.get(b);
                if (headA != headB) {
                    Element<V> big = sizeMap.get(headA) > sizeMap.get(headB) ? headA : headB;
                    Element<V> small = big == headA ? headB : headA;
                    fatherMap.put(small, big);
                    sizeMap.put(big, sizeMap.get(big) + sizeMap.get(small));
                    sizeMap.remove(small);
                }
            }
        }

        public Element<V> getHead(Element<V> e) {
            Stack<Element<V>> stack = new Stack<>();
            while (e != fatherMap.get(e)) {
                stack.push(e);
                e = fatherMap.get(e);
            }
            while (!stack.isEmpty()) {
                fatherMap.put(stack.pop(), e);
            }
            return e;
        }
    }
}


// Python

// class UnionFind:
//     def __init__(self, nums):
//         self.par = list(range(len(nums)))
//         self.rank = [0] * len(nums)

//     def find(self, x):
//         if self.par[x] == x:
//             return x
//         else:
//             return self.find(self.par[x])
    
//     def unite(self, x, y):
//         x = self.find(x)
//         y = self.find(y)
//         if x == y:
//             return
//         if self.rank[x] < self.rank[y]:
//             self.par[x] = y
//         else:
//             self.par[y] = x
//             if self.rank[x] == self.rank[y]:
//                 self.rank[x] += 1
    
//     def same(self, x, y):
//         return self.find(x) == self.find(y)
