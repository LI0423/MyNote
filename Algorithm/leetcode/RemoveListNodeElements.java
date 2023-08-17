package Algorithm.leetcode;

public class RemoveListNodeElements {

    public ListNode removeListNodeElements(ListNode node,int val){
        ListNode dummy = new ListNode(-1,node);
        ListNode cur = dummy;
        while(cur != null && cur.next != null){
            if(cur.next.val != val){
                cur = cur.next;
            }else{
                cur.next = cur.next.next;
            }
        }
        return dummy.next;
    }
    
}
