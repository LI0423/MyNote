package Algorithm.leetcode;

import Algorithm.DataStructure.ListNode;

public class KthToLast {

    /**
     * 快指针先向前走k步，然后快慢指针一起走，直到快指针指向null，慢指针指向的就是倒数第k个节点
     */
    public int kthToLast(ListNode head , int k){
        ListNode slow = head,fast = head;
        while(k-- > 0){
            fast = fast.next;
        }
        while(fast != null){
            slow = slow.next;
            fast = fast.next;
        }
        return slow.val;
    }
    
}
