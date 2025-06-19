package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        // key 和 value 都可以为 null
        map.put(null, null);
    }

    /**
     * {@link HashMap#put(Object, Object)} HashMap#put()方法详解
     */
    public static void put() {
        /*
        HashMap#put()方法整体流程:
        数组(Node<K,V>[] tab)+链表(内部Node对象实现链表)--转换-->数组+红黑树(内部 TreeNode 对象实现)
        1.计算 key 的 hash 值,hash值与运算确定数组下标
        2.hash冲突时,插入到此下标对应链表的尾部
        3.链表长度大于 8 且小于 64时扩容,链表长度大于 64 时链表转为红黑树

        扩容主要是为了减少冲突，提高查询效率
        扩容时机:
            1.首次添加元素时初始化数组
            2.数组长度大于阈值 n*0.75
            3.链表长度大于 8 小于 64
        扩容的核心方法是 resize()，主要步骤如下：
            1.创建新数组：容量为原数组的 2 倍。
            2.重新哈希：遍历原数组中的每个哈希桶，将节点重新分配到新数组的哈希桶中。
                对于链表节点，根据 (newCapacity - 1) & hash 重新计算索引。
                对于红黑树节点，调用 split() 方法拆分树结构。


    inal V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
    Node<K,V>[] tab; // 哈希表,数组
    Node<K,V> p; // 当前节点
    int n, i; // n: 哈希表长度, i: 索引

    // 1. 如果哈希表为空或长度为 0，进行扩容
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;

    // 2. 计算索引位置，如果该位置为空，直接插入新节点
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; // 用于存储已存在的节点
        K k;

        // 链表 hash 冲突处理逻辑:
        // 3. 如果当前节点的键与要插入的键相同，直接覆盖
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;

        // 4. 如果当前节点是树节点，调用红黑树的插入方法
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);

        // 5. 否则，遍历链表
        else {
            for (int binCount = 0; ; ++binCount) {
                // 如果到达链表尾部，插入新节点
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    // 如果链表长度超过阈值，转换为红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                // 如果找到相同键的节点，退出循环
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }

        // 6. 如果找到相同键的节点，更新值并返回旧值
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }

    // 7. 修改计数器，检查是否需要扩容
    ++modCount;
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}

         */
    }
}
