package name.mymiller.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamUtils {

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

}
