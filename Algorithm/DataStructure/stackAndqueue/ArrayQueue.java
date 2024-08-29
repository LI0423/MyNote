package Algorithm.DataStructure;

public class ArrayQueue {
    private int[] nums;
    private int front;
    private int queSize;

    public ArrayQueue(int capacity){
        nums = new int[capacity];
        front = queSize = 0;
    }

    // 获取队列的容量
    public int capacity(){
        return this.nums.length;
    }

    // 获取队列的长度
    public int size(){
        return queSize;
    }

    // 判断队列是否为空
    public boolean isEmpty(){
        return queSize == 0;
    }

    // 入队
    public void push(int num){
        if (queSize == this.capacity()){
            System.out.println("队列已满");
            return;
        }
        // 计算队尾指针，指向队尾索引 + 1
        // 通过取余操作实现rear越过数组尾部后回到头部
        int rear = (front + queSize) % this.capacity();
        // 将 num 添加至队尾
        nums[rear] = num;
        queSize++;
    }

    // 出队
    public int pop(){
        int num = this.peek();
        front = (front + 1) % this.capacity();
        queSize--;
        return num;
    }

    // 访问队首元素
    public int peek(){
        if (this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        return this.nums[this.front];
    }

    // 转换为数组输出
    public int[] toArray(){
        int[] res = new int[queSize];
        for (int i = 0, j = front; i < queSize; i++, j++){
            res[i] = nums[j % this.capacity()];
        }
        return res;
    }
}
