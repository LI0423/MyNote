package Algorithm.DataStructure.stackAndqueue;

public class LinkedListDeque {
    
    public class ListNode{
        int val;
        ListNode next;
        ListNode prev;

        ListNode(int val){
            this.val = val;
            prev = next = null;
        }
    }

    private ListNode front, rear;
    private int queSize = 0;

    public LinkedListDeque(){
        front = rear = null;
    }

    // 获取数组大小
    public int size(){
        return queSize;
    }

    // 判断数组是否为空
    public boolean isEmpty(){
        return this.size() == 0;
    }

    // 入队
    private void push(int num, boolean isFront){
        ListNode node = new ListNode(num);
        // 如果队列为空，front和rear都指向node
        if (this.isEmpty()){
            this.front = rear = node;
        } 
        // 队首入队
        if (isFront){
            this.front.prev = node;
            node.next = this.front;
            this.front = node;
        // 队尾入队
        } else {
            this.rear.next = node;
            node.prev = this.rear;
            this.rear = node;
        }
        // 更新队列大小
        queSize++;
    }

    // 队首入队
    public void pushFirst(int num){
        this.push(num, true);
    }

    // 队尾入队
    public void pushLast(int num){
        this.push(num, false);
    }

    // 出队
    private int pop(boolean isFront){
        if (this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        int val;
        if (isFront){
            // 暂存头节点
            val = front.val;
            // 删除头节点
            ListNode fnext = this.front.next;
            if (fnext != null){
                fnext.prev = null;
                this.front.next = null;
            }
            // 更新头节点
            this.front = fnext;
        // 队尾出队操作
        } else {
            // 暂存尾节点
            val = rear.val;
            // 删除尾节点
            ListNode rprev = this.rear.prev;
            if (rprev != null){
                rprev.next = null;
                this.rear.prev = null;
            }
            // 更新尾节点
            this.rear = rprev;
        }
        queSize--;
        return val;
    }

    // 队首出队
    public int popFirst(){
        return this.pop(true);
    }

    // 队尾出队
    public int popLast(){
        return this.pop(false);
    }

    // 访问队首元素
    public int peekFirst(){
        if (this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        return this.front.val;
    }

    // 转换为数组
    public int[] toArray(){
        ListNode node = front;
        int[] res = new int[this.size()];
        for (int i = 0; i < res.length; i++){
            res[i] = node.val;
            node = node.next;
        }
        return res;
    }
}


