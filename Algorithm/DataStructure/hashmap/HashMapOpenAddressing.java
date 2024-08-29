package Algorithm.DataStructure;

// 开放寻址哈希表
public class HashMapOpenAddressing{

    class Pair{
        private int key;
        private String value;

        public Pair(int key, String value){
            this.key = key;
            this.value = value;
        }
    }

    private int size;   // 键值对数量
    private int capacity = 4;   // 哈希表容量
    private final double loadThres = 2.0 / 3.0; // 触发扩容的负载因子阈值
    private final int extendRatio = 2;  // 扩容倍数
    private Pair[] buckets; // 桶数组
    private final Pair TOMBSTONE = new Pair(-1, "-1");  // 删除标记

    public HashMapOpenAddressing(){
        this.size = 0;
        this.buckets = new Pair[capacity];
    }

    // 哈希函数
    private int hashFunc(int key){
        return key % this.capacity;
    }

    // 负载因子
    private double loadFactor(){
        return (double) size / this.capacity;
    }

    // 搜索key对应的桶索引
    private int findBucket(int key){
        int index = this.hashFunc(key);
        int firstTombstone = -1;
        // 线性探测，当遇到空桶时跳出
        while(this.buckets[index] != null){
            // 若遇到key，返回对应的桶索引
            if (this.buckets[index].key == key){
                // 之前遇到了删除标记，则将键值对移动至该索引处
                if (firstTombstone != -1){
                    this.buckets[firstTombstone] = this.buckets[index];
                    this.buckets[index] = TOMBSTONE;
                    // 返回移动后的桶索引
                    return firstTombstone;
                }
                // 返回桶索引
                return index;
            }
            // 记录遇到的首个删除标记
            if (firstTombstone == -1 && this.buckets[index] == TOMBSTONE){
                firstTombstone = index;
            }
            // 计算桶索引，越过尾部则返回头部
            index = (index + 1) % this.capacity;
        }
        // 若key不存在，则返回添加点的索引
        return firstTombstone == -1 ? index : firstTombstone;
    }

    // 查询
    public String get(int key){
        // 搜索key对应的桶索引
        int index = this.findBucket(key);
        // 若找到键值对，则返回对应val
        if (this.buckets[index] != null && this.buckets[index] != TOMBSTONE){
            return this.buckets[index].value;
        }
        // 若键值对不存在，则返回null
        return null;
    }

    // 添加
    public void put(int key, String value){
        // 当负载因子超过阈值时，进行扩容
        if (this.loadFactor() > this.loadThres){
            this.extend();
        }
        // 搜索key对应的桶索引
        int index = this.hashFunc(key);
        // 若找到键值对，则覆盖val并返回
        if (this.buckets[index] != null && this.buckets[index] != TOMBSTONE){
            this.buckets[index].value = value;
            return;
        }
        // 若键值对不存在，则添加该键值对
        this.buckets[index] = new Pair(key, value);
        this.size++;
    }

    // 删除
    public void remove(int key){
        // 搜索key对应的桶索引
        int index = this.hashFunc(key);
        // 若找到键值对，则用删除标记覆盖它
        if (this.buckets[index] != null && this.buckets[index] != TOMBSTONE){
            this.buckets[index] = this.TOMBSTONE;
            this.size--;
        }
    }

    // 扩容
    private void extend(){
        // 暂存原哈希表
        Pair[] bucketsTmp = this.buckets;
        // 初始化扩容后的新哈希表
        this.capacity *= this.extendRatio;
        this.buckets = new Pair[this.capacity];
        size = 0;
        // 将键值对从原哈希表搬运至新哈希表
        for (Pair pair: bucketsTmp){
            if (pair != null && pair != TOMBSTONE){
                this.put(pair.key, pair.value);
            }
        }
    }

    // 打印
    public void print(){
        for (Pair pair: this.buckets){
            if (pair == null){
                System.out.println("null");
            } else if (pair == this.TOMBSTONE){
                System.out.println("TOMBSTONE");
            } else {
                System.out.println(pair.key + " -> " + pair.value);
            }
        }
    }

}