package Algorithm.leetcode;

import java.nio.file.WatchService;

public class MergeTwoLists {

    private static class ListNode{
        int val;
        ListNode next;
        ListNode(){}
        ListNode(int val){
            this.val = val;
        }
        ListNode(int val,ListNode next){
            this.val = val;
            this.next = next;
        }
    }

    private static ListNode mergeTwoLists(ListNode l1, ListNode l2){
        ListNode prevhead = new ListNode(-1);
        ListNode prev = prevhead;
        while(l1 != null && l2 != null){
            if(l1.val<l2.val){
                prevhead.next = l1;
                l1 = l1.next;
            }else{
                prevhead.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        prev.next = l1 == null ? l2 : l1;
        return prevhead.next;
    }  
}
