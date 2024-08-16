package Algorithm.DataStructure;

public class LinkedListQueue {
    private ListNode front, rear;
    private int queSize = 0;

    public LinkedListQueue(){
        this.front = null;
        this.rear = null;
    }

    // 获取队列的长度
    public int size(){
        return this.queSize;
    }

    // 判断队列是否为空
    public boolean isEmpty(){
        return this.queSize == 0;
    }

    // 入队
    public void push(int num){
        ListNode node = new ListNode(num);
        if (this.queSize == 0){
            this.front = node;
            this.rear = node;
        } else {
            this.rear.next = node;
            this.rear = node;
        }
        this.queSize++;
    }

    // 出队
    public int pop(){
        int num = this.peek();
        front = front.next;
        queSize--;
        return num;
    }

    // 访问队首元素
    public int peek(){
        if (this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        return front.val;
    }

    // 转换为数组
    public int[] toArray(){
        ListNode node = this.front;
        int[] res = new int[this.size()];
        for (int i = 0; i < res.length; i++){
            res[i] = node.val;
            node = node.next;
        }
        return res;
    }
}
