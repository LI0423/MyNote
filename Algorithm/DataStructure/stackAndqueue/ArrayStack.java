package Algorithm.DataStructure.stackAndqueue;

import java.util.ArrayList;

public class ArrayStack {
    private ArrayList<Integer> stack;

    public ArrayStack(){
        stack = new ArrayList<>();
    }

    // 栈的长度
    public int size(){
        return stack.size();
    }

    // 判断栈是否为空
    public boolean isEmpty(){
        return stack.size() == 0;
    }

    // 入栈
    public void push(int num){
        this.stack.add(num);
    }

    // 出栈
    public int pop(){
        if (this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        return this.stack.remove(this.size() - 1);
    }

    // 访问栈顶元素
    public int peek(){
        if (this.isEmpty()){
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.stack.get(this.size() - 1);
    }

    // 将List转化为Array返回
    public Object[] toArray(){
        return stack.toArray();
    }
}
