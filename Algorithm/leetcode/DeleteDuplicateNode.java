package Algorithm.leetcode;

public class DeleteDuplicateNode {

    public ListNode deleteDuplicateNode(ListNode node){
        ListNode dummy = new ListNode(-1 , node);
        ListNode cur = dummy;
        while(cur.next != null && cur.next.next != null){
            if(cur.next.val == cur.next.next.val){
                int val = cur.next.val;
                while(cur.next != null && cur.next.val == val){
                    cur.next = cur.next.next;
                }
            }else{
                cur = cur.next; 
            }
        }
        return dummy.next;
    }
    
}
