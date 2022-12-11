package ua.kotsko.project.Examinator.utils;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomSequence {

    public static Integer[] create(int length) {
        AtomicInteger itr = new AtomicInteger(0);
        Integer[] random = Arrays.stream(new Integer[length]).
                                  map(x -> itr.getAndIncrement()).
                                  toArray(Integer[]::new);
        ShiftToRight<Integer> intShift = new ShiftToRight<>();
        for (int i = 1; i <= length * 2 + (int) (Math.random() * 2); i++)
            intShift.shift(random, (int) (Math.random() * (length - 1) + 1));
        return random;
    }

}
