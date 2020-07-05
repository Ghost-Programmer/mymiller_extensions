package name.mymiller.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CalUtils {
    /**
     * Apply a Function to Date Range of this AdvancedCalendar to end, by breaking the time period into months.
     *
     * @param startDate LocalDate that is the start of the range;
     * @param endDate  LocalDate that is the end of the range.
     * @param function BiFunction<AdvancedCalendar,AdvancedCalendar,R> taking in a start and end AdvancedCalendar for the
     *                 begining of the month and end of month. R the return type from the BiFunction
     * @param <R>      R the return type from the BiFunction
     * @return List<R> containing the results from the BiFunction
     */
    public static <R> List<R> applyByMonth(LocalDate startDate, LocalDate endDate, BiFunction<LocalDate, LocalDate, R> function) {
        ArrayList<R> results = new ArrayList<>();

        int startYear = startDate.getYear();
        int endYear = endDate.getYear();
        LocalDate startOfMonth = startDate;
        LocalDate endOfMonth = startDate.with(TemporalAdjusters.lastDayOfMonth());

        boolean exitLoop = false;

        do {
            if (endOfMonth.isAfter(endDate)) {
                endOfMonth = endDate;
                exitLoop = true;
            }

            if (!startOfMonth.isAfter(endDate)) {
                results.add(function.apply(startOfMonth,
                        endOfMonth));

                startOfMonth = startOfMonth.plusMonths(1).withDayOfMonth(1);
                endOfMonth = startDate.with(TemporalAdjusters.lastDayOfMonth());
            } else {
                exitLoop = true;
            }

        } while (!exitLoop);

        return results;
    }

    /**
     * Apply a Function to Date Range of this AdvancedCalendar to end, by breaking the time period into Weeks Sunday to Saturday.
     *
     * @param startDate LocalDate that is the start of the range;
     * @param endDate  LocalDate that is the end of the range.
     * @param function     BiFunction<AdvancedCalendar,AdvancedCalendar,R> taking in a start and end AdvancedCalendar for the
     *                     begining of the month and end of month. R the return type from the BiFunction
     * @param endDayOfWeek int representing the day to use as end of the week.
     * @param <R>          R the return type from the BiFunction
     * @return List<R> containing the results from the BiFunction
     */
    public static  <R> List<R> applyByWeek(LocalDate startDate, LocalDate endDate, DayOfWeek endDayOfWeek, BiFunction<LocalDate, LocalDate, R> function) {
        ArrayList<R> results = new ArrayList<>();


        LocalDate currentStartDateOfWeek = startDate;
        LocalDate lastDateOfCurrentWeek = currentStartDateOfWeek;

        while(!lastDateOfCurrentWeek.getDayOfWeek().equals(endDayOfWeek)) {
            lastDateOfCurrentWeek = lastDateOfCurrentWeek.plusDays(1);
        }

        boolean exitLoop = false;

        do {
            if (lastDateOfCurrentWeek.isAfter(endDate)) {
                lastDateOfCurrentWeek = endDate;
                exitLoop = true;
            }

            if (!currentStartDateOfWeek.isAfter(endDate)) {
                results.add(function.apply(currentStartDateOfWeek,
                        lastDateOfCurrentWeek));

                currentStartDateOfWeek = currentStartDateOfWeek.plusWeeks(1);
                lastDateOfCurrentWeek = lastDateOfCurrentWeek.plusWeeks(1);
            } else {
                exitLoop = true;
            }

        } while (!exitLoop);


        return results;
    }

    /**
     * Apply a Function to Date Range of this AdvancedCalendar to end, by breaking the time period into Days
     *
     * @param startDate LocalDate that is the start of the range;
     * @param endDate  LocalDate that is the end of the range.
     * @param function Function<AdvancedCalendar,R> taking in as the day. R the return type from the BiFunction
     * @param <R>      R the return type from the BiFunction
     * @return List<R> containing the results from the BiFunction
     */
    public static <R> List<R> applyByDay(LocalDate startDate, LocalDate endDate, Function<LocalDate, R> function) {
        ArrayList<R> results = new ArrayList<>();
        LocalDate currentStartDateOfMonth = startDate;
        boolean exitLoop = false;

        do {
            results.add(function.apply(currentStartDateOfMonth));
            currentStartDateOfMonth = currentStartDateOfMonth.plusDays(1);

            if (currentStartDateOfMonth.isAfter(endDate)) {
                exitLoop = true;
            }

        } while (!exitLoop);

        return results;
    }

    public static boolean sameDay(Calendar dateA, Calendar dateB) {
        if ((dateB != null) && (dateA != null) &&(dateA.get(Calendar.YEAR) == dateB.get(Calendar.YEAR))
                && (dateA.get(Calendar.MONTH) == dateB.get(Calendar.MONTH))
                && (dateA.get(Calendar.DAY_OF_MONTH) == dateB.get(Calendar.DAY_OF_MONTH)))
        {
            return true;
        }
        return false;
    }
}
