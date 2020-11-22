package name.mymiller.utils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Set of utility methods for working with Stream
 */
public class StreamUtils {

    /**
     * Join multiple streams together into a single stream.
     * @param streams Array or variable argument list of streams to join
     * @param <T> Type of data in stream
     * @return Single Stream<T> of all streams combined.
     */
    public static <T> Stream<T> concat(Stream<? extends T> ... streams ) {
        Stream.Builder<T> builder = Stream.builder();
        if(streams != null && streams.length > 0) {
            List<Stream<? extends T>> streamsList = ListUtils.safe(Arrays.asList(streams));
            streamsList.forEach(stream -> {
                stream.forEach(item -> builder.add(item));
            });
        }
        return builder.build();
    }

    /**
     * Join data from two stream together in order.  Will stop once one of the two streams join together.
     * @param streamA Stream of A to join
     * @param streamB Stream of B to join
     * @param joinFunction BiFunction used to join A and B
     * @param <A> Type of data in StreamA
     * @param <B> Type of Data in StreamB
     * @param <C> Type of Data return in the Stream
     * @return Stream<C> returned with the joined A and B.
     */
    public static <A,B,C> Stream<C> zip(Stream<? extends A> streamA, Stream<? extends B> streamB, BiFunction<A,B,C> joinFunction) {
        List<? extends A> listA = streamA.collect(Collectors.toList());
        List<? extends B> listB = streamB.collect(Collectors.toList());
        List<C> listC = new ArrayList<>();

        Iterator<? extends A> iteratorA = listA.iterator();
        Iterator<? extends B> iteratorB = listB.iterator();
        while(iteratorA.hasNext() && iteratorB.hasNext()) {
            listC.add(joinFunction.apply(iteratorA.next(),iteratorB.next()));
        }

        return listC.stream();
    }

    /**
     * Will join two streams together by identify whether two items match based on the mapFunction, then join them together
     * using the joinFunction. All matching combinations will be created.
     * @param streamA Stream of A to join
     * @param streamB Stream of B to join
     * @param mapFunction BiFunction used to identify if two elements should be joined.
     * @param joinFunction BiFunction used to join A and B
     * @param <A> Type of data in StreamA
     * @param <B> Type of Data in StreamB
     * @param <C> Type of Data return in the Stream
     * @return Stream<C> returned with the joined A and B.
     */
    public static <A,B,C> Stream<C> join(Stream<? extends A> streamA, Stream<? extends B> streamB, BiFunction<A,B,Boolean> mapFunction, BiFunction<A,B,C> joinFunction) {
        List<? extends A> listA = streamA.collect(Collectors.toList());
        List<? extends B> listB = streamB.collect(Collectors.toList());
        List<C> listC = new ArrayList<>();

        Iterator<? extends A> iteratorA = listA.iterator();
        while(iteratorA.hasNext()) {
            A a = iteratorA.next();
            Iterator<? extends B> iteratorB = listB.iterator();

            while(iteratorB.hasNext()) {
                B b = iteratorB.next();

                if(mapFunction.apply(a,b)) {
                    listC.add(joinFunction.apply(a,b));
                }
            }
        }

        return listC.stream();
    }

    /**
     * Partition the contents of a Stream into two streams, whether if they match a given predicate. The method will
     * return two streams in a map, that have they key true/false.
     * @param stream Stream to partition
     * @param partition Predicate to match
     * @param <A> Type of data
     * @return Map with keys true & false, with Stream<A>.
     **/
    public static <A> Map<Boolean, Stream<? extends A>> partition(Stream<? extends A> stream, Predicate<A> partition) {
        Map<Boolean, Stream<? extends A>> map = new HashMap<>();
        map.put(true,stream.filter(partition));
        map.put(false,stream.filter(item -> !partition.test(item)));
        return map;
    }
}
