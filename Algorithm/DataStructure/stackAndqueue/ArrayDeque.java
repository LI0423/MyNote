package Algorithm.DataStructure.stackAndqueue;

import java.lang.reflect.InaccessibleObjectException;

public class ArrayDeque {
    private int[] nums;
    private int front;
    private int queSize;

    public ArrayDeque(int capacity){
        this.nums = new int[capacity];
        front = queSize = 0;
    }

    // 获取双向队列容量
    public int capacity(){
        return this.nums.length;
    }

    // 获取双向队列的大小
    public int size(){
        return this.queSize;
    }

    // 判断队列是否为空
    public boolean isEmpty(){
        return queSize == 0;
    }

    // 计算环形数组索引
    private int index(int i){
        // 通过取余操作实现数组首尾相连
        // 当 i 越过数组尾部后，回到头部
        // 当 i 越过数组头部后，回到尾部
        return (i + this.capacity()) % this.capacity();
    }

    // 队首入队
    public void pushFirst(int num){
        if (queSize == this.capacity()){
            System.out.println("双向队列已满");
            return;
        }
        // 队首指针向左移动一位
        // 通过取余操作实现front越过数组头部后回到尾部
        this.front = this.index(this.front - 1);
        // 将num添加到队首
        this.nums[this.front] = num;
        this.queSize++;
    }

    // 队尾入队
    public void pushLast(int num){
        if (queSize == this.capacity()){
            System.out.println("双向队列已满");
            return;
        }
        // 计算队尾指针，指向队尾索引+1
        int rear = this.index(this.front + this.queSize);
        this.nums[rear] = num;
        this.queSize++;
    }

    // 队首出队
    public int popFirst(){
        int num = this.peekFirst();
        // 队首指针向后移一位
        this.front = this.index(this.front + 1);
        this.queSize--;
        return num;
    }

    // 队尾出队
    public int popLast(){
        int num = this.peekLast();
        this.queSize--;
        return num;
    }

    // 访问队首元素
    public int peekFirst(){
        if (this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        return this.nums[this.front];
    }

    // 访问队尾元素
    public int peekLast(){
        if (this.isEmpty()){
            throw new InaccessibleObjectException();
        }
        // 计算尾元素索引
        int rear = this.index(this.front + this.queSize - 1);
        return this.nums[rear];
    }

    // 转换为数组
    public int[] toArray(){
        int[] res = new int[this.queSize];
        for (int i = 0, j = this.front; i < this.queSize; i++, j++){
            res[i] = this.nums[this.index(j)];
        }
        return res;
    }
}
