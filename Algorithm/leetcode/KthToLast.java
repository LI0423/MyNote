package Algorithm.leetcode;

public class KthToLast {

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
