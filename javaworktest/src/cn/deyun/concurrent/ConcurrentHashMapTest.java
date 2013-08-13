package cn.deyun.concurrent;
/**
 * 重点:深入分析ConcurrentHashMap的学习
 * 用ConcurrentHashMap替代Hashtable和synchronizedMap
 * ConcurrentHashMap,一个更快的HashMap
 * ConcurrentHashMap是一个线程安全的Hash Table
 * 主要功能是提供了一组和HashTable功能相同但是线程安全的方法。ConcurrentHashMap可以做到读取数据不加锁
 * 并且其内部的结构可以让其在进行写操作的时候能够将锁的粒度保持地尽量地小，不用对整个ConcurrentHashMap加锁
 * ConcurrentHashMap为了提高本身的并发能力，在内部采用了一个叫做Segment的结构
 * 一个Segment其实就是一个类Hash Table的结构，Segment内部维护了一个链表数组
 * ConcurrentHashMap实现原理:
 * ConcurrentHashMap的目标是实现支持高并发、高吞吐量的线程安全的HashMap。当然不能直接对整个hashtable加锁，所以在ConcurrentHashMap中，数据的组织结构和HashMap有所区别。
 * 一个ConcurrentHashMap由多个segment组成，每一个segment都包含了一个HashEntry数组的hashtable
 * 每一个segment包含了对自己的hashtable的操作，比如get，put，replace等操作，这些操作发生的时候，对自己的hashtable进行锁定。
 * 由于每一个segment写操作只锁定自己的hashtable，所以可能存在多个线程同时写的情况，性能无疑好于只有一个hashtable锁定的情况。
 * @author Kervin
 *
 */
public class ConcurrentHashMapTest {

}
