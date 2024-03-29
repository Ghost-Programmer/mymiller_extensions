package name.mymiller.utils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author jmiller Provide a set of utilities to use on lists.
 */
public class ListUtils {

    /**
     * Find the common elements between two Lists
     *
     * @param <E> Type used in lists, which must have overridden equals();
     * @param a   List A
     * @param b   List B
     * @return List<E> that contains the common elements between the Lists.
     */
    public static <E> List<E> intersection(List<E> a, List<E> b) {
        b = ListUtils.safe(b);
        return ListUtils.safe(a).stream().distinct().filter(b::contains).collect(Collectors.toList());
    }

    /**
     * Convert a Map of K,V to a List of K
     *
     * @param map Map to Convert
     * @param <K> Type for K
     * @param <V> Type for V
     * @return List of K
     */
    public static <K, V> List<K> mapKeyToList(Map<K, V> map) {
        return ListUtils.safe(new ArrayList<>(map.keySet()));
    }

    /**
     * Convert and Iterator of E to a List of E
     *
     * @param iterator Iterator of E to convert
     * @param <E>      Type to iterate over
     * @return List of E converted.
     */
    public static <E> List<E> iterate(Iterator<E> iterator) {
        Iterable<E> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    /**
     * Method to convert a Set to a List.
     *
     * @param set Set to convert to List
     * @param <E> Type of elemnet in list
     * @return List
     */
    public static <E> List<E> toList(Set<E> set) {
        return set.stream().toList();
    }

    /**
     * Convert a Map of K,V to a List of V
     *
     * @param map Map to Convert
     * @param <K> Type for K
     * @param <V> Type for V
     * @return List of V
     */
    public static <K, V> List<V> mapValuesToList(Map<K, V> map) {
        return ListUtils.safe(new ArrayList<>(map.values()));
    }

    /**
     * Join two lists together to form a new List
     *
     * @param <E> Type used in lists, which must have overridden equals();
     * @param a   List A
     * @param b   List B
     * @return List<E> that contains the contents of both Lists.
     */
    public static <E> List<E> union(List<E> a, List<E> b) {
        final List<E> unionList = new ArrayList<>(ListUtils.safe(a));
        unionList.addAll(ListUtils.safe(b));
        return ListUtils.safe(unionList.stream().distinct().collect(Collectors.toList()));
    }

    /**
     * Create a list of the unique elements in both Lists.
     *
     * @param <E> Type used in lists, which must have overridden equals();
     * @param a   List A
     * @param b   List B
     * @return List<E> that contains the unique elements not shared between lists.
     */
    public static <E> List<E> unique(List<E> a, List<E> b) {
        final List<E> unionList = ListUtils.union(a, b);
        unionList.removeAll(ListUtils.intersection(a, b));
        return unionList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Returns boolean indicating if the List is empty
     *
     * @param list List to check
     * @param <E>  List type
     * @return boolean indicating if LIst is empty
     */
    public static <E> boolean isEmpty(List<E> list) {
        if (list != null) {
            return list.size() <= 0;
        }

        return true;
    }

    /**
     * Returns boolean indicating if the List is not empty
     *
     * @param list List to check
     * @param <E>  List type
     * @return boolean indicating if List is not empty
     */
    public static <E> boolean notEmpty(List<E> list) {
        return !ListUtils.isEmpty(list);
    }

    /**
     * Returns the number of elements in the list.  Checks for Null.
     *
     * @param list List to check
     * @param <E>  List Type
     * @return int indicating the number of elements in the list or 0 if null.
     */
    public static <E> int size(List<E> list) {
        if (list == null) {
            return 0;
        }

        return list.size();
    }

    /**
     * Gurantees a list will be safe to use, even if null.
     *
     * @param list List to check if null
     * @param <E>  List Type
     * @return List, or an empty list of E.
     */
    public static <E> List<E> safe(List<E> list) {
        if (list != null) {
            return list;
        }

        return Collections.emptyList();
    }

    /**
     * Performs pagination on the List,  returning the indicated page of elements, or an empty ist.
     *
     * @param list     List to paginate
     * @param pageSize Number of elements per page
     * @param page     The Page to return
     * @param <E>      Type of Elements
     * @return Empty list if no matching page, or List containing the page's elements.
     */
    public static <E> List<E> page(List<E> list, int pageSize, int page) {
        List<E> safe = ListUtils.safe(list);

        if (pageSize > 0 && page >= 0 && safe.size() > 0) {
            int startIndex = pageSize * (page - 1);
            int endIndex = startIndex + pageSize;
            if (startIndex > safe.size()) {
                return Collections.emptyList();
            }

            if (endIndex > safe.size()) {
                endIndex = safe.size();
            }

            return safe.subList(startIndex, endIndex);
        }

        return Collections.emptyList();
    }

    public static <E> int pages(List<E> list, int pageSize) {
        if (ListUtils.isEmpty(list)) {
            return 0;
        }
        return (list.size() / pageSize) + 1;
    }

    /**
     * Filter out elements based on Predicate. Simplified method to stream->filter.
     *
     * @param list      List to filter
     * @param predicate Predciate to indicate whether to remove of keep elements in the list
     * @param <E>       Type of Elements
     * @return List containing the elements after the filter.
     */
    public static <E> List<E> filter(List<E> list, Predicate<E> predicate) {
        return ListUtils.safe(list).parallelStream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Sets a value on a field for all objects in a list.
     *
     * @param list   List to set value on
     * @param value  Value to set
     * @param setter Setter reference to set value on
     * @param <E>    Type of item in list
     * @param <R>    Type of Value to set
     * @return List with value set or an empty list.
     */
    public static <E, R> List<E> setValue(List<E> list, R value, BiConsumer<E, R> setter) {
        return ListUtils.safe(list).parallelStream().peek(item -> setter.accept(item, value)).collect(Collectors.toList());
    }

    /**
     * Returns a list of values from the field of the list
     *
     * @param list   List to retrieve values from
     * @param getter Getter method to retrieve value
     * @param <E>    Type of object in LIst
     * @param <R>    Type of Value to get
     * @return List of type R from all objects in list.
     */
    public static <E, R> List<R> getValue(List<E> list, Function<? super E, ? extends R> getter) {
        return ListUtils.safe(list).parallelStream().map(getter).collect(Collectors.toList());
    }

    /**
     * Convert a List to a map
     *
     * @param list   List to convert to Map
     * @param getter Getter to use to get Key
     * @param <E>    Type of Value
     * @param <R>    Type of Key
     * @return Map of elements E, based on Key R.
     */
    public static <E, R> Map<R, E> toMap(List<E> list, Function<? super E, ? extends R> getter) {
        return ListUtils.safe(list).parallelStream().collect(Collectors.toMap(getter, Function.identity()));
    }
}
