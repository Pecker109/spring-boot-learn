package com.example.demo.multiThread;


import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.remove("3");
        System.out.println(map);
        map.clear();
        System.out.println(map);
    }

    /**
     * {@link ConcurrentHashMap#put(Object, Object)} HashMap#put()方法详解
     */
    void put() {
        /*
        整体流程与 HashMap 类似:数组+hash桶(链表或红黑树)

        关键点解析:
        （1）线程安全机制
            CAS 操作：在插入新节点时，使用 CAS 操作保证线程安全。
            分段锁：对每个哈希桶（链表或树）加锁（synchronized），减少锁的粒度。
        （2）扩容机制
            当元素数量超过阈值时，触发扩容。
            扩容时，使用 ForwardingNode 标记正在扩容的哈希桶，其他线程可以协助扩容。

        final V putVal(K key, V value, boolean onlyIfAbsent) {
    if (key == null || value == null) throw new NullPointerException(); // 不允许 null 键或值
    int hash = spread(key.hashCode()); // 计算哈希值
    int binCount = 0; // 用于记录链表长度
    for (Node<K,V>[] tab = table;;) { // 自旋插入
        Node<K,V> f; int n, i, fh;
        // 1. 如果表为空，初始化表
        if (tab == null || (n = tab.length) == 0)
            tab = initTable();
        // 2. 计算索引位置，如果该位置为空，尝试 CAS 插入新节点
        else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
            if (casTabAt(tab, i, null, new Node<K,V>(hash, key, value, null)))
                break; // CAS 成功，插入完成
        }
        // 3. 如果当前节点是 ForwardingNode，表示正在扩容，协助扩容
        else if ((fh = f.hash) == MOVED)
            tab = helpTransfer(tab, f);
        // 4. 否则，处理哈希冲突
        else {
            V oldVal = null;
            synchronized (f) { // 对当前节点加锁
                if (tabAt(tab, i) == f) { // 再次检查节点是否被修改
                    // 4.1 如果是链表节点
                    if (fh >= 0) {
                        binCount = 1;
                        for (Node<K,V> e = f;; ++binCount) {
                            K ek;
                            // 如果找到相同键的节点，更新值
                            if (e.hash == hash &&
                                ((ek = e.key) == key || (ek != null && key.equals(ek)))) {
                                oldVal = e.val;
                                if (!onlyIfAbsent)
                                    e.val = value;
                                break;
                            }
                            // 如果到达链表尾部，插入新节点
                            Node<K,V> pred = e;
                            if ((e = e.next) == null) {
                                pred.next = new Node<K,V>(hash, key, value, null);
                                break;
                            }
                        }
                    }
                    // 4.2 如果是树节点
                    else if (f instanceof TreeBin) {
                        Node<K,V> p;
                        binCount = 2;
                        // 调用红黑树的插入方法
                        if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key, value)) != null) {
                            oldVal = p.val;
                            if (!onlyIfAbsent)
                                p.val = value;
                        }
                    }
                }
            }
            // 5. 如果链表长度超过阈值，转换为红黑树
            if (binCount != 0) {
                if (binCount >= TREEIFY_THRESHOLD)
                    treeifyBin(tab, i);
                if (oldVal != null)
                    return oldVal;
                break;
            }
        }
    }
    // 6. 更新元素数量
    addCount(1L, binCount);
    return null;
}
         */
    }
}
