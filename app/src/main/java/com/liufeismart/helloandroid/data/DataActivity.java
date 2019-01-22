package com.liufeismart.helloandroid.data;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by humax on 18/12/16
 */
public class DataActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //java.util包
        //工具类
//        Collections
//        Arrays
        //Collection是一个接口，List,Set,Map,Queue,Deque都实现了该接口
        Collection col1 = new ArrayList();
        Collection col2 = new LinkedList();
        Collection col3 = new TreeSet();
        Collection col4 = (Collection) new TreeMap<String, String>();
        //=======================List=======================
        //List: Vector, ArrayList, LinkedList, CopyOnWriteArrayList 这几个线性表
        //AbstractList
        //AbstractList 是 Vector, ArrayList,AbstractSequeuentialList的父类
        //而AbstractSequeuentialList是LinkedList的父类
        //AbstractList 实现了Collection,List的接口


        //=======================Vector
        //Vector包装了一个Object[],默认的容量是10
        //Vector的扩容方式：默认是当调用add，发现容量不足，数组将增加当前数组大小，即增加一倍
        //                也可通过Constructor设置数组增加的数量
        //Vector中的对外提供的方法都是synchronized的，表明Vector是线程安全的
        //Vector实现了 RandomAccess, Cloneable, java.io.Serializable

        //RandomAccess
        //      是个空接口，作为标记，用于标明实现该接口的List支持快速随机访问，主要目的是使算法能够在随机和顺序访问的list中表现的更加高效。
        //      Collections.binarySearch
//        public static <T>
//        int binarySearch(List<? extends Comparable<? super T>> list, T key) {
//            if (list instanceof RandomAccess || list.size()<BINARYSEARCH_THRESHOLD)
//                return Collections.indexedBinarySearch(list, key);
//            else
//                return Collections.iteratorBinarySearch(list, key);
//        }
        //Cloneable
        //      是一个空接口，作为标记，Cloneable接口没有定义成员。它通常用于指明被创建的一个允许对对象进行位复制（也就是对象副本）的类。
        //      如果试图用一个不支持Cloneable接口的类调用clone( )方法，将引发一个CloneNotSupportedException异常。

        //Serializable
        //      是一个空接口，作为标记
        //      在ObjectOutputStream.writeObject中
//        if (obj instanceof Class) {
//            writeClass((Class) obj, unshared);
//        } else if (obj instanceof ObjectStreamClass) {
//            writeClassDesc((ObjectStreamClass) obj, unshared);
//            // END Android-changed
//        } else if (obj instanceof String) {
//            writeString((String) obj, unshared);
//        } else if (cl.isArray()) {
//            writeArray(obj, desc, unshared);
//        } else if (obj instanceof Enum) {
//            writeEnum((Enum<?>) obj, desc, unshared);
//        } else if (obj instanceof Serializable) {
//            writeOrdinaryObject(obj, desc, unshared);
//        } else {
//            if (extendedDebugInfo) {
//                throw new NotSerializableException(
//                        cl.getName() + "\n" + debugInfoStack.toString());
//            } else {
//                throw new NotSerializableException(cl.getName());
//            }
//        }
        //可以看出除了Class,ObjectStreamClass,String,数组，Enum，其他对象若要序列化，则必须实现Serializable

        Vector v = new Vector();
        //增
        v.add("a");//增
        v.add(0,"b");
        v.add("c");
        //查
        v.isEmpty();
        v.size();
        v.capacity();
        v.contains("a");
        v.indexOf("a");
        v.indexOf("a", 0);
        v.lastIndexOf("a");
        v.lastIndexOf("a", 0);
        v.get(0);
        v.elements();
        Iterator iter_vector = v.iterator();
        while(iter_vector.hasNext()) {
            iter_vector.next();
        }
        //删
        v.remove("a");//删
        v.remove(0);
        v.clear();

        //=======================ArrayList
        //ArrayList实现了RandomAccess, Cloneable, java.io.Serializable
        //ArrayList包装了一个Object[],默认容量是{}
        //ArrayList的扩容方式: 默认的，当第一次需要扩容时，计算扩容值oldCapacity + (oldCapacity >> 1),
        // 若小于10,扩容到10,以后每次扩容都根据oldCapacity + (oldCapacity >> 1)
        //10是可以通过构建函数扩建的
        //
        ArrayList arrList = new ArrayList();
        //增
        arrList.add("a");
        arrList.add(0,"b");
        arrList.add("c");
        //查
        arrList.isEmpty();
        arrList.size();
        arrList.contains("a");
        arrList.indexOf("a");
        arrList.lastIndexOf("a");
        arrList.get(0);
        Iterator iter_arrList = arrList.iterator();
        while(iter_arrList.hasNext()) {
            iter_arrList.next();
        }
        //删
        arrList.remove("a");
        arrList.remove(0);
        //=======================LinkedList
        //LinkedList实现了Deque<E>, Cloneable, java.io.Serializable
        //  因为是链式存储，所以不能标记RandomAccess
        //Deque: double ended queue
        //element insertion and removal at both ends




        //LinkedList有两个Node:first, last
        LinkedList lkList = new LinkedList();
        //增
        lkList.add("a");
        lkList.addFirst("b");
        lkList.addLast("c");
        lkList.push("d");
        lkList.offer("e");
        lkList.offerFirst("f");
        lkList.offerLast("h");
        //
        //查
        lkList.size();
        lkList.contains("a");
        lkList.indexOf("a");
        lkList.lastIndexOf("a");
        lkList.element();//getFirst();
        lkList.get(0);
        lkList.getFirst();
        lkList.getLast();
        Iterator iter_lkList = lkList.iterator();
        while(iter_lkList.hasNext()) {
            iter_lkList.next();
        }
        //删
        lkList.remove();//lkList.removeFirst()
        lkList.remove("a");
        lkList.remove(3);
        lkList.removeFirst();
        lkList.removeLast();
        lkList.pop();

        //=======================CopyOnWriteArrayList
        //CopyOnWriteArrayList  写时复制，复制出一个新的Object[]
        //CopyOnWriteArrayList是java.util.concurrent
        //CopyOnWriteArrayList包装了一个Object[]
        //add如下，当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，
        //然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器
        //因此无需扩容
//        public boolean add(E e) {
//            synchronized (lock) {
//                Object[] elements = getArray();
//                int len = elements.length;
//                Object[] newElements = Arrays.copyOf(elements, len + 1);
//                newElements[len] = e;
//                setArray(newElements);
//                return true;
//            }
//        }
        //而get等查找方法并没有synchronized
        CopyOnWriteArrayList cOnWList = new CopyOnWriteArrayList();
        //增
        cOnWList.add("a");
        cOnWList.add(0, "b");
        cOnWList.addIfAbsent("c");
        //查
        cOnWList.isEmpty();
        cOnWList.size();
        cOnWList.contains("a");
        cOnWList.indexOf("a");
        cOnWList.indexOf("b",0);
        cOnWList.get(0);
        Iterator iter_cOnWList = cOnWList.iterator();
        while(iter_cOnWList.hasNext()) {
            iter_cOnWList.next();
        }
        //删
        cOnWList.remove("a");
        cOnWList.remove(0);



        //=======================Stack
        //Stack是Vector的子类, //Java中的栈是顺序栈
        Stack stack = new Stack();
        //增
        stack.add("a");
        stack.add(0, "b");
        stack.push("c");
        //查
        stack.empty();
        stack.size();
        stack.contains("c");
        stack.indexOf("a");
        stack.search("c");
        stack.indexOf("c", 0);
        stack.peek();//peek与pop的不同，peek并不会出栈
        //删
        stack.pop();
        stack.clear();
        stack.remove("a");
        stack.remove(0);



        //=======================Map=======================
        //Map
        //实现了Map的类 :java.util包中的  HashMap, LinkedHashMap,IdentityHashMap,WeakHashMap
        //                             HashTable,
        //                             SortedMap, TreeMap, EnumMap,
        //             java.util.concurrent包中的 ConcurrentSkipListMap,
        //                                       ConcurrentHashMap
        //             android.support.v4.util／android.util包中的 ArrayMap
        //还有junit.frmework.Junit4TestAdpaterCache 和 java.security.Provider 应用了Map


        Map map = new HashMap();
        //增
        map.put("a", "A");
        map.put("b", "B");
        map.put("c", "C");
        //查
        map.isEmpty();
        map.size();
        map.containsKey("a");
        map.containsValue("A");
        map.get("a");
        Set set_map = map.entrySet();
        Iterator iter_map = set_map.iterator();
        while(iter_map.hasNext()) {
            iter_map.next();
        }
        Set set_key_map = map.keySet();
        Iterator iter_set_key_map = set_key_map.iterator();
        while(iter_set_key_map.hasNext()) {
            iter_set_key_map.next();
        }
        //删
        map.clear();
        map.remove("a");
        //?????
        map.values();

        //=======================HashMap
        //HashMap 实现了Cloneable, Serializable
        //HashMap 用到的hash函数:
//                    static final int hash(Object key) {
//                        int h;
//                        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
//                    }
        //HashMap 存储结构是 数组＋链表 结点是Node
        //HashMap threshold, loadFactor, table大小之间的关系
        //        当第一次调用put()方法,table创建了一个size为16的Node[]
        //        当调用put时， table.size*loadFactor>threshold时, 需要扩容，扩容大小为当前大小的一倍
        //HashMap put()方法的关键newNode()
        //HashMap containsKey 与 containsValue
        //        containsKey 可直接根据key 获取到table[index],只需要在table[index]或其链表上遍历
        //        containsValue 则需要挨个遍历table[i]的链表
        //HashMap keySet(), entrySet(), values()
        //HashMap remove 首先,要找到要删除的Node(Node是否只需要match key,或 key, value都需要match
        //               其次, Node是在链表上, 还是在table上
        //HashMap get(), replace()的关键getNode(),getNode()方法，通过hash(key)找到第一个符合key条件的Node

        HashMap map_hash = new HashMap();
        //增
        map_hash.put("a", "A");
        map_hash.put("b", "B");
        map_hash.put("c", "C");
        //查
        map_hash.containsKey('a');
        map_hash.containsValue("A");
        map_hash.get("a");
        Set keySet_map_hash = map_hash.keySet();
        Iterator iter_keySet_map_hash = keySet_map_hash.iterator();
        while(iter_keySet_map_hash.hasNext()) {
            iter_keySet_map_hash.next();
        }
        Set entrySet_map_hash = map_hash.entrySet();
        Iterator iter_entrySet_map_hash = entrySet_map_hash.iterator();
        while(iter_entrySet_map_hash.hasNext()) {
            iter_entrySet_map_hash.next();
        }
        Collection values= map_hash.values();
        for (Object value : values) {

        }
        //删
        map_hash.remove("a");
        map_hash.remove("a", "B");
        //改
        map_hash.replace("a", "1", "a");
        map_hash.replace("a", "1");


        //=======================LinkedHashMap
        //LinkedHashMap 继承自HashMap, header, tail
        //              LinkedHashMapEntry继承自HashMap.Node
        //              LinkedHashMapEntry  before, after
        //                  LinkedHashMap的header,tail和 LinkedHashMapEntry.before, after说明
        //                  LinkedHashMap的逻辑结构是一个双向链表

        //LinkedHashMap 继承了put(),containsKey, getNode
        //              重写了newNode,containsValue, get,
        //LinkedHashMap newNode()方法 定义一个双向链表的Node
        //LinkedHashMap containsValue()方法 不同HashMap, 在一个链表中查找
        //LinkedHashMap get()方法，首先利用Hash.getNode(), 得到Node,
        //                        然后检查accessOrder,是否需要将Node，放入双向链表的尾部
        //                        这就是缓存封装一个LinkedHashMap的原因，LinkedHashMap实现了最近最先使用原则
        //              replace()方法也调用了afterNodeAccess
        //LinkedHashMap keySet(), entrySet(), values() 从head开始，链表查询
        LinkedHashMap map_linked_hash = new LinkedHashMap();
        //增
        map_linked_hash.put("a", "A");
        map_linked_hash.put("b", "B");
        map_linked_hash.put("c", "C");
        //查
        map_linked_hash.isEmpty();
        map_linked_hash.size();
        map_linked_hash.containsKey("a");
        map_linked_hash.containsValue("A");
        map_linked_hash.get("a");
        Set keySet_map_linked_hash = map_linked_hash.keySet();
        Iterator iter_keySet_map_linked_hash = keySet_map_linked_hash.iterator();
        while(iter_keySet_map_linked_hash.hasNext()) {
            iter_keySet_map_linked_hash.next();
        }
        map_linked_hash.entrySet();
        map_linked_hash.values();
        //改
        map_linked_hash.replace("a", "1");
        map_linked_hash.replace("a", "A", "1");
        //删
        map_linked_hash.remove("a");
        map_linked_hash.remove("a", "A");



        //=======================WeakHashMap
        //WeakHashMap 继承了AbstractMap, 没有继承HashMap
        //WeakHashMap 存储结构是数组+链表
        //WeakHashMap 封装了一个Entry[] table
        //WeakHashMap threshold, loadFactor, table大小之间的关系
        //            当调用put时， table.size*loadFactor>threshold时, 需要扩容，扩容大小为当前大小的一倍

        WeakHashMap map_weak_hash = new WeakHashMap();
        //增
        map_weak_hash.put("a", "A");
        map_weak_hash.put("b", "B");
        map_weak_hash.put("c", "C");




        //=======================Set=======================
        //Set
        //实现了Set的类：java.util包中的 HashSet, LinkedHashSet,
        //                            TreeSet,EnumSet
        //             java.util.concurrent包中的 ConcurrentSkipListSet, CopyOnWriteArrayList
        //             android.util包的 ArraySet
        Set set = new TreeSet();
        //增
        set.add("a");
        set.add("b");
        set.add("c");
        //查
        set.isEmpty();
        set.size();
        set.contains("a");
        Iterator iter_set = set.iterator();
        while(iter_set.hasNext()) {
            iter_set.next();
        }
        //删
        set.clear();
        set.remove("a");
    }
}
