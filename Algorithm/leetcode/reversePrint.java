package Algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class ReversePrint {

    /**
     * 先计算链表长度，然后创建一个数组，遍历链表倒序插入数组
     * @param head
     * @return
     */
    public int[] reversePrint(ListNode head){

        if(head == null){
            return new int[]{};
        }

        int n = 0;
        for(ListNode cur = head;cur != null;cur = cur.next){
            ++n;
        }
        int[] ans = new int[n];
        for(ListNode cur = head; cur != null;cur = cur.next){
            ans[--n] = cur.val;
        }
        return ans;
    }

    /**
     * 以栈的方式
     * @param head
     * @return
     */
    public int[] reversePrintStack(ListNode head){
        if(head == null){
            return new int[]{};
        }

        Deque<Integer> stack = new ArrayDeque<>();
        for(; head != null;head = head.next){
            stack.push(head.val);
        }
        int[] ans = new int[stack.size()];
        int i = 0;
        while(!stack.isEmpty()){
            ans[i++] = stack.pop();
        }
        return ans;
    }
    
}
