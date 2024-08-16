package Algorithm.DataStructure;

public class LinkedListStack {
    private ListNode stackPeek;
    private int stkSize = 0;

    public LinkedListStack(){
        stackPeek = null;
    }

    // 栈的长度
    public int size(){
        return this.stkSize;
    }

    // 判断栈是否为空
    public boolean isEmpty(){
        return stkSize == 0;
    }

    // 入栈
    public void push(int num){
        ListNode listNode = new ListNode(num);
        listNode.next = this.stackPeek;
        this.stackPeek = listNode;
        stkSize++;
    }

    // 出栈
    public int pop(){
        int num = this.peek();
        this.stackPeek = this.stackPeek.next;
        stkSize--;
        return num;
    }

    // 访问栈顶元素
    public int peek(){
        if (this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        return this.stackPeek.val;
    }

    // 将List转化为Array并返回
    public int[] toArray(){
        ListNode node = stackPeek;
        int[] res = new int[this.size()];
        for (int i = res.length - 1; i >= 0; i--){
            res[i] = node.val;
            node = node.next;
        }
        return res;
    }
}
