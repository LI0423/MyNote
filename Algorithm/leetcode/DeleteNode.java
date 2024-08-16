package Algorithm.leetcode;

import Algorithm.DataStructure.ListNode;

public class DeleteNode {

    public void deleteLNode(ListNode node){
        node.val = node.next.val;
        node.next = node.next.next;
    }
    
}
