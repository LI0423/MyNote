package Algorithm.leetcode;

public class SwapPairs {

    private static class ListNode{
        int val ;
        ListNode next ;
        ListNode(){}
        ListNode(int val){
            this.val = val;
        }
        ListNode(int val,ListNode next){
            this.val = val;
            this.next = next;
        }
    }

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
