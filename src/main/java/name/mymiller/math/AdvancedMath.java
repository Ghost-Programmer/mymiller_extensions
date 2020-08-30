package name.mymiller.math;

import com.google.common.math.IntMath;
import com.google.common.math.LongMath;

import java.math.RoundingMode;
import java.util.List;

public class AdvancedMath  {

    public static Long averageLong(final List<Long> values) {
        Long sumOfValues = values.stream().mapToLong(value -> value).sum();
        return LongMath.divide(sumOfValues, values.size(), RoundingMode.HALF_EVEN);
    }

    public static Double standardDeviationLong(final List<Long> values, Long average) {
        long averageOfDiffs = (values.stream().mapToLong(value -> (value - average) * (value - average)).sum()) / values.size();
        return Math.sqrt(averageOfDiffs);
    }
}
