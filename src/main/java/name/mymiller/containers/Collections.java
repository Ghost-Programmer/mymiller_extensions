package name.mymiller.containers;

import java.util.*;

public class Collections {

    /**
     * The empty list (immutable). This list is serializable.
     */
    @SuppressWarnings("rawtypes")
    public static List EMPTY_LIST = java.util.Collections.EMPTY_LIST;

    /**
     * The empty map (immutable). This map is serializable.
     */
    @SuppressWarnings("rawtypes")
    public static Map EMPTY_MAP = java.util.Collections.EMPTY_MAP;

    /**
     * The empty set (immutable). This set is serializable.
     */
    @SuppressWarnings("rawtypes")
    public static Set EMPTY_SET = java.util.Collections.EMPTY_SET;

    public Collections() {

    }

    /**
     * @param c
     * @param elements
     * @return
     */
    @SafeVarargs
    public static <T> boolean addAll(Collection<? super T> c, T... elements) {
        return java.util.Collections.addAll(c, elements);
    }

    /**
     * @param deque
     * @return
     */
    public static <T> Queue<T> asLifoQueue(Deque<T> deque) {
        return java.util.Collections.asLifoQueue(deque);
    }

    /**
     * @param list
     * @param key
     * @return
     */
    public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key) {
        return java.util.Collections.binarySearch(list, key);
    }

    /**
     * @param list
     * @param key
     * @param c
     * @return
     */
    public static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c) {
        return java.util.Collections.binarySearch(list, key, c);
    }

    /**
     * @param c
     * @param type
     * @return
     */
    public static <E> Collection<E> checkedCollection(Collection<E> c, Class<E> type) {
        return java.util.Collections.checkedCollection(c, type);
    }

    /**
     * @param list
     * @param type
     * @return
     */
    public static <E> List<E> checkedList(List<E> list, Class<E> type) {
        return java.util.Collections.checkedList(list, type);
    }

    /**
     * @param m
     * @param keyType
     * @param valueType
     * @return
     */
    public static <K, V> Map<K, V> checkedMap(Map<K, V> m, Class<K> keyType, Class<V> valueType) {
        return java.util.Collections.checkedMap(m, keyType, valueType);
    }

    /**
     * @param s
     * @param type
     * @return
     */
    public static <E> Set<E> checkedSet(Set<E> s, Class<E> type) {
        return java.util.Collections.checkedSet(s, type);
    }

    /**
     * @param m
     * @param keyType
     * @param valueType
     * @return
     */
    public static <K, V> SortedMap<K, V> checkedSortedMap(SortedMap<K, V> m, Class<K> keyType, Class<V> valueType) {
        return java.util.Collections.checkedSortedMap(m, keyType, valueType);
    }

    /**
     * @param s
     * @param type
     * @return
     */
    public static <E> SortedSet<E> checkedSortedSet(SortedSet<E> s, Class<E> type) {
        return java.util.Collections.checkedSortedSet(s, type);
    }

    /**
     * @param dest
     * @param src
     */
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        java.util.Collections.copy(dest, src);
    }

    /**
     * @param c1
     * @param c2
     * @return
     */
    public static boolean disjoint(Collection<?> c1, Collection<?> c2) {
        return java.util.Collections.disjoint(c1, c2);
    }

    /**
     * @return
     */
    public static <T> Enumeration<T> emptyEnumeration() {
        return java.util.Collections.emptyEnumeration();
    }

    /**
     * @return
     */
    public static <T> Iterator<T> emptyIterator() {
        return java.util.Collections.emptyIterator();
    }

    /**
     * @return
     */
    public static <T> List<T> emptyList() {
        return java.util.Collections.emptyList();
    }

    /**
     * @return
     */
    public static <T> ListIterator<T> emptyListIterator() {
        return java.util.Collections.emptyListIterator();
    }

    /**
     * @return
     */
    public static <K, V> Map<K, V> emptyMap() {
        return java.util.Collections.emptyMap();
    }

    /**
     * @return
     */
    public static <T> Set<T> emptySet() {
        return java.util.Collections.emptySet();
    }

    /**
     * @param c
     * @return
     */
    public static <T> Enumeration<T> enumeration(Collection<T> c) {
        return java.util.Collections.enumeration(c);
    }

    /**
     * @param list
     * @param obj
     */
    public static <T> void fill(List<? super T> list, T obj) {
        java.util.Collections.fill(list, obj);
    }

    /**
     * @param c
     * @param o
     * @return
     */
    public static int frequency(Collection<?> c, Object o) {
        return java.util.Collections.frequency(c, o);
    }

    /**
     * @param source
     * @param target
     * @return
     */
    public static int indexOfSubList(List<?> source, List<?> target) {
        return java.util.Collections.indexOfSubList(source, target);
    }

    /**
     * @param source
     * @param target
     * @return
     */
    public static int lastIndexOfSubList(List<?> source, List<?> target) {
        return java.util.Collections.lastIndexOfSubList(source, target);
    }

    /**
     * @param e
     * @return
     */
    public static <T> ArrayList<T> list(Enumeration<T> e) {
        return java.util.Collections.list(e);
    }

    /**
     * @param coll
     * @return
     */
    public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll) {
        return java.util.Collections.max(coll);
    }

    /**
     * @param coll
     * @param comp
     * @return
     */
    public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) {
        return java.util.Collections.max(coll, comp);
    }

    /**
     * @param coll
     * @return
     */
    public static <T extends Object & Comparable<? super T>> T min(Collection<? extends T> coll) {
        return java.util.Collections.min(coll);
    }

    /**
     * @param coll
     * @param comp
     * @return
     */
    public static <T> T min(Collection<? extends T> coll, Comparator<? super T> comp) {
        return java.util.Collections.min(coll, comp);
    }

    /**
     * @param n
     * @param o
     * @return
     */
    public static <T> List<T> nCopies(int n, T o) {
        return java.util.Collections.nCopies(n, o);
    }

    /**
     * @param map
     * @return
     */
    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return java.util.Collections.newSetFromMap(map);
    }

    /**
     * @param list
     * @param oldVal
     * @param newVal
     * @return
     */
    public static <T> boolean replaceAll(List<T> list, T oldVal, T newVal) {
        return java.util.Collections.replaceAll(list, oldVal, newVal);
    }

    /**
     * @param list
     */
    public static void reverse(List<?> list) {
        java.util.Collections.reverse(list);
    }

    /**
     * @return
     */
    public static <T> Comparator<T> reverseOrder() {
        return java.util.Collections.reverseOrder();
    }

    /**
     * @param cmp
     * @return
     */
    public static <T> Comparator<T> reverseOrder(Comparator<T> cmp) {
        return java.util.Collections.reverseOrder(cmp);
    }

    /**
     * @param list
     * @param distance
     */
    public static void rotate(List<?> list, int distance) {
        java.util.Collections.rotate(list, distance);
    }

    /**
     * @param list
     */
    public static void shuffle(List<?> list) {
        java.util.Collections.shuffle(list);
    }

    /**
     * @param list
     * @param rnd
     */
    public static void shuffle(List<?> list, Random rnd) {
        java.util.Collections.shuffle(list, rnd);
    }

    /**
     * @param o
     * @return
     */
    public static <T> Set<T> singleton(T o) {
        return java.util.Collections.singleton(o);
    }

    /**
     * @param o
     * @return
     */
    public static <T> List<T> singletonList(T o) {
        return java.util.Collections.singletonList(o);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static <K, V> Map<K, V> singletonMap(K key, V value) {
        return java.util.Collections.singletonMap(key, value);
    }

    /**
     * @param list
     */
    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        java.util.Collections.sort(list);
    }

    /**
     * @param list
     * @param c
     */
    public static <T> void sort(List<T> list, Comparator<? super T> c) {
        java.util.Collections.sort(list, c);
    }

    /**
     * @param list
     * @param i
     * @param j
     */
    public static void swap(List<?> list, int i, int j) {
        java.util.Collections.swap(list, i, j);
    }

    /**
     * @param c
     * @return
     */
    public static <T> Collection<T> synchronizedCollection(Collection<T> c) {
        return java.util.Collections.synchronizedCollection(c);
    }

    /**
     * @param list
     * @return
     */
    public static <T> List<T> synchronizedList(List<T> list) {
        return java.util.Collections.synchronizedList(list);
    }

    /**
     * @param m
     * @return
     */
    public static <K, V> Map<K, V> synchronizedMap(Map<K, V> m) {
        return java.util.Collections.synchronizedMap(m);
    }

    /**
     * @param s
     * @return
     */
    public static <T> Set<T> synchronizedSet(Set<T> s) {
        return java.util.Collections.synchronizedSet(s);
    }

    /**
     * @param m
     * @return
     */
    public static <K, V> SortedMap<K, V> synchronizedSortedMap(SortedMap<K, V> m) {
        return java.util.Collections.synchronizedSortedMap(m);
    }

    /**
     * @param s
     * @return
     */
    public static <T> SortedSet<T> synchronizedSortedSet(SortedSet<T> s) {
        return java.util.Collections.synchronizedSortedSet(s);
    }

    /**
     * @param c
     * @return
     */
    public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> c) {
        return java.util.Collections.unmodifiableCollection(c);
    }

    /**
     * @param list
     * @return
     */
    public static <T> List<T> unmodifiableList(List<? extends T> list) {
        return java.util.Collections.unmodifiableList(list);
    }

    /**
     * @param m
     * @return
     */
    public static <K, V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> m) {
        return java.util.Collections.unmodifiableMap(m);
    }

    /**
     * @param s
     * @return
     */
    public static <T> Set<T> unmodifiableSet(Set<? extends T> s) {
        return java.util.Collections.unmodifiableSet(s);
    }

    /**
     * @param m
     * @return
     */
    public static <K, V> SortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> m) {
        return java.util.Collections.unmodifiableSortedMap(m);
    }

    /**
     * @param s
     * @return
     */
    public static <T> SortedSet<T> unmodifiableSortedSet(SortedSet<T> s) {
        return java.util.Collections.unmodifiableSortedSet(s);
    }
}
