package Algorithm.leetcode;

public class RemoveListNodeElements {

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
