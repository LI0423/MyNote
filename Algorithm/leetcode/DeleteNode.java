package Algorithm.leetcode;

public class DeleteNode {

    public void deleteLNode(ListNode node){
        node.val = node.next.val;
        node.next = node.next.next;
    }
    
}
