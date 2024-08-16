package Algorithm.leetcode;


//83. 删除排序链表中的重复元素

// 给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。返回 已排序的链表 。 

// 示例 1：

// 输入：head = [1,1,2]
// 输出：[1,2]
// 示例 2：

// 输入：head = [1,1,2,3,3]
// 输出：[1,2,3]

class ListNode {
        int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
public class DeleteDuplicates{
    public ListNode deleteDuplicates(ListNode head){
        if (head == null){
            return head;
        }
        ListNode cur = head;
        while(cur.next != null){
            if(cur.val == cur.next.val){
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }
}

//Golang

// type ListNode struct {
//     Val int
//     Next *ListNode
// }
// func deleteDuplicates(head *ListNode) *ListNode{
//     if head == nil {
//         return nil
//     }
//     cur := head
//     for cur.Next != nil {
//         if cur.Val == cur.Next.Val {
//             cur.Next = cur.Next.Next
//         } else {
//             cur = cur.Next
//         }
//     }
//     return head
// }