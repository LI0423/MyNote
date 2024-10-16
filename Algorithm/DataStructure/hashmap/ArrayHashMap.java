package Algorithm.DataStructure.hashmap;

import java.util.ArrayList;
import java.util.List;

public class ArrayHashMap {
    class Pair{
        private int key;
        private String val;

        public Pair(int key, String val){
            this.key = key;
            this.val = val;
        }
    }

    private List<Pair> buckets;

    public ArrayHashMap(){
        buckets = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            buckets.add(null);
        }
    }

    // 哈希函数
    private int hashFunc(int key){
        int index = key % 100;
        return index;
    }

    // 获取
    public String get(int key){
        int index = this.hashFunc(key);
        Pair pair = buckets.get(index);
        if (pair == null){
            return null;
        }
        return pair.val;
    }

    // 添加
    public void put(int key, String val){
        Pair pair = new Pair(key, val);
        int index = this.hashFunc(key);
        buckets.set(index, pair);
    }

    // 删除
    public void remove(int key){
        int index = this.hashFunc(key);
        buckets.set(index, null);
    }

    // 获取所有键值对
    public List<Pair> pairSet(){
        List<Pair> pairSet = new ArrayList<>();
        for (Pair pair: buckets){
            if (pair != null){
                pairSet.add(pair);
            }
        }
        return pairSet;
    }

    // 获取所有键
    public List<Integer> keySet(){
        List<Integer> keySet = new ArrayList<>();
        for(Pair pair: this.buckets){
            if (pair != null){
                keySet.add(pair.key);
            }
        }
        return keySet;
    }

    // 获取所有值
    public List<String> valueSet(){
        List<String> valueSet = new ArrayList<>();
        for (Pair pair: this.buckets){
            if (pair != null){
                valueSet.add(pair.val);
            }
        }
        return valueSet;
    }

    // 打印哈希表
    public void print() {
        for (Pair kv : pairSet()) {
            System.out.println(kv.key + " -> " + kv.val);
        }
    }
}
