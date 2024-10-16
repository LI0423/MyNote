package Algorithm.DataStructure.hashmap;

import java.util.ArrayList;
import java.util.List;

// 链式地址哈希表
public class HashMapChaining {
    class Pair{
        private int key;
        private String value;

        public Pair(int key, String value){
            this.key = key;
            this.value = value;
        }
    }

    int size;   // 键值对数量
    int capacity;   // 哈希表容量
    double loadThres;   // 触发扩容的负载因子阈值
    int extendRatio;    // 扩容倍数
    List<List<Pair>> buckets;   // 桶数组

    public HashMapChaining(){
        this.size = 0;
        this.capacity = 4;
        this.loadThres = 2.0 / 3.0;
        this.extendRatio = 2;
        this.buckets = new ArrayList<>(this.capacity);
        for (int i = 0; i < this.capacity; i++){
            this.buckets.add(new ArrayList<>());
        }
    }

    // 哈希函数
    private int hashFunc(int key){
        return key % this.capacity;
    }

    // 负载因子
    private double loadFactor(){
        return (double) this.size / this.capacity;
    }

    // 查询
    public String get(int key){
        int index = this.hashFunc(key);
        List<Pair> bucket = this.buckets.get(index);
        // 遍历桶，若找到key，则返回对应value
        for (Pair pair: bucket){
            if (pair.key == key){
                return pair.value;
            }
        }
        return null;
    }

    // 添加
    public void put(int key, String value){
        // 如果负载因子超过阈值，执行扩容
        if (this.loadFactor() > this.extendRatio){
            this.extend();
        }
        int index = this.hashFunc(key);
        // 遍历桶，若遇到指定key，则更新对应value并返回
        List<Pair> bucket = this.buckets.get(index);
        for (Pair pair: bucket){
            if (pair.key == key){
                pair.value = value;
                return;
            }
        }
        // 若无该key，则将键值对添加至尾部
        Pair pair = new Pair(key, value);
        bucket.add(pair);
        this.size++;
    }

    // 删除
    public void remove(int key){
        int index = this.hashFunc(key);
        List<Pair> bucket = this.buckets.get(index);
        // 遍历桶，从桶中删除键值对
        for (Pair pair: bucket){
            if (pair.key == key){
                bucket.remove(pair);
                this.size--;
                break;
            }
        }
    }

    // 扩展
    private void extend(){
        // 暂存原哈希表
        List<List<Pair>> tmp = this.buckets;
        // 初始化扩容后的新哈希表
        this.capacity *= this.extendRatio;
        this.buckets = new ArrayList<>(capacity);
        for (int i = 0; i < this.capacity; i++){
            this.buckets.add(new ArrayList<>());
        }
        this.size = 0;
        // 将键值对搬运至新哈希表
        for(List<Pair> bucket: tmp){
            for (Pair pair: bucket){
                this.put(pair.key, pair.value);
            }
        }
    }

    /* 打印哈希表 */
    void print() {
        for (List<Pair> bucket : buckets) {
            List<String> res = new ArrayList<>();
            for (Pair pair : bucket) {
                res.add(pair.key + " -> " + pair.value);
            }
            System.out.println(res);
        }
    }
}
