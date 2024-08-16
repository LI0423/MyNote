package Algorithm.leetcode;

import Algorithm.DataStructure.ListNode;

public class SwapPairs {

    public ListNode swapPairs(ListNode head){
        ListNode dummy = new ListNode(0,head);
        ListNode pre = dummy,cur = head;
        while(cur!=null&&cur.next!=null){
            ListNode t = cur.next;
            cur.next = t.next;
            t.next = cur;
            pre.next = t;
            pre = cur;
            cur = cur.next;
        }
        return dummy.next;
    }
    
}
