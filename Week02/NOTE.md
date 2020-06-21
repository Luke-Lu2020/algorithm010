学习笔记

#### 一，关于 HashMap 的put、get：

1，get

```java
final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    //hash表table不为空，length>0，
    if ((tab = table) != null && (n = tab.length) > 0 &&
        //hash & length-1 定位数组下标
        //HashMap的初始大小和扩容都是以2的次方来进行的，换句话说length-1换成二进制永远是全部为1，比如容量为16，则length-1为1111，
        //感觉不到这个 & 运算的意义。。。。。
        (first = tab[(n - 1) & hash]) != null) {
        
        if (first.hash == hash && // always check first node
            ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        if ((e = first.next) != null) {
            if (first instanceof TreeNode) //如果是红黑树
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            do {
                //hash相同，且key相等
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    return null;
}
```

2，put

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    //这个tab就是成员变量里的table
    Node<K,V>[] tab;
    //这个就是数组上的链表,当桶上的对象太多的时候 就会转换成红黑树
    Node<K,V> p; 
    int n, i;
    // 如果tab数组未被初始化,初始化该数组
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    // 如果key没有存在过直接放到tab数组中
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        //新的链表，需要放进来的元素
        Node<K,V> e; 
        K k;
        //如果put的key和hash恰好与对应桶的首个对象一样,那么可以不用考虑直接替换
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        //第一个节点是红黑树TreeNode，即tree-bin
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            //不是TreeNode,即为链表,遍历链表
            for (int binCount = 0; ; ++binCount) {
                /**
                到达链表的尾端也没有找到key值相同的节点
                *则生成一个新的Node,并且判断链表的节点个数是不是到达转换成红黑树的上界
                *达到，则转换成红黑树
                */
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                //下一个节点
                p = e;
            }
        }
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    //链表元素增加，并判断是否大于阈值，如果大于，则扩容
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```



