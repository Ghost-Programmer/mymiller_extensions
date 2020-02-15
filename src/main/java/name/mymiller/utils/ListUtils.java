package name.mymiller.utils;

import java.util.ArrayList;
import java.util.List;
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
}
