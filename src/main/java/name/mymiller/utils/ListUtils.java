package name.mymiller.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        return a.stream().distinct().filter(b::contains).collect(Collectors.toList());
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
        final List<E> unionList = new ArrayList<>(a);
        unionList.addAll(b);
        return unionList.stream().distinct().collect(Collectors.toList());
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
     * @param list List to check
     * @param <E> List type
     * @return boolean indicating if LIst is empty
     */
    public static <E> boolean isEmpty( List<E> list) {
        if (list != null) {
            if (list.size() > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns boolean indicating if the List is not empty
     * @param list List to check
     * @param <E> List type
     * @return boolean indicating if List is not empty
     */
    public static <E> boolean notEmpty( List<E> list) {
        return !ListUtils.isEmpty(list);
    }

    /**
     * Returns the number of elements in the list.  Checks for Null.
     * @param list List to check
     * @param <E> List Type
     * @return int indicating the number of elements in the list or 0 if null.
     */
    public static <E> int size( List<E> list) {
        if(list == null) {
            return 0;
        }

        return list.size();
    }

    /**
     *  Gurantees a list will be safe to use, even if null.
     * @param list List to check if null
     * @param <E> List Type
     * @return List, or an empty list of E.
     */
    public static <E> List<E> safe( List<E> list) {
        if(list != null) {
            return list;
        }

        return Collections.emptyList();
    }

    /**
     *  Performs pagination on the List,  returning the indicated page of elements, or an empty ist.
     * @param list List to paginate
     * @param pageSize Number of elements per page
     * @param page The Page to return
     * @param <E> Type of Elements
     * @return Empty list if no matching page, or List containing the page's elements.
     */
    public static <E> List<E> page( List<E> list, int pageSize, int page) {
        List<E> safe = ListUtils.safe(list);

        if(pageSize > 0 && page >= 0 && safe.size() > 0) {
            int startIndex = pageSize * (page - 1);
            int endIndex = startIndex + pageSize;
            if(startIndex > safe.size()) {
                return Collections.EMPTY_LIST;
            }

            if(endIndex > safe.size()) {
                endIndex = safe.size() - 1;
            }

            return safe.subList(startIndex,endIndex);
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * Filter out elements based on Predicate. Simplified method to stream->filter.
     * @param list List to filter
     * @param predicate Predciate to indicate whether to remove of keep elements in the list
     * @param <E> Type of Elements
     * @return List containing the elements after the filter.
     */
    public static <E> List<E> filter( List<E> list, Predicate<E> predicate) {
        return ListUtils.safe(list).parallelStream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Sets a value on a field for all objects in a list.
     * @param list List to set value on
     * @param value Value to set
     * @param setter Setter reference to set value on
     * @param <E> Type of item in list
     * @param <R> Type of Value to set
     * @return List with value set or an empty list.
     */
    public static <E,R> List<E> setValue( List<E> list, R value, BiConsumer<E, R> setter) {
        return ListUtils.safe(list).parallelStream().map(item -> {
            setter.accept(item,value);

            return item;
        }).collect(Collectors.toList());
    }

    /**
     * Returns a list of values from the field of the list
     * @param list List to retrieve values from
     * @param getter Getter method to retrieve value
     * @param <E> Type of object in LIst
     * @param <R> Type of Value to get
     * @return List of type R from all objects in list.
     */
    public static <E, R> List<R> getValue( List<E> list, Function<? super E,? extends R> getter) {
        return ListUtils.safe(list).parallelStream().map(item -> getter.apply(item)).collect(Collectors.toList());
    }

    /**
     * Convert a List to a map
     * @param list List to convert to Map
     * @param getter Getter to use to get Key
     * @param <E> Type of Value
     * @param <R> Type of Key
     * @return Map of elements E, based on Key R.
     */
    public static <E, R> Map<R,E> toMap(List<E> list, Function<? super E,? extends R> getter) {
        return ListUtils.safe(list).parallelStream().collect(Collectors.toMap(getter,Function.identity()));
    }
}
