package com.github.sunjx.modbus.common.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class IntArrayUtil {
    public IntArrayUtil() {
    }

    public static int[] toIntArray(Collection<Integer> values) {
        return values.stream().mapToInt(Integer::intValue).toArray();
    }

    public static Integer[] toIntegerArray(int[] values) {
        return (Integer[]) Arrays.stream(values).boxed().toArray((x$0) -> {
            return new Integer[x$0];
        });
    }

    public static List<Integer> toIntegerList(int[] values) {
        return (List) Arrays.stream(values).boxed().collect(Collectors.toList());
    }

    public static int[] toIntArray(Integer[] values) {
        return Arrays.stream(values).mapToInt(Integer::valueOf).toArray();
    }

    public static int[] toIntArray(String[] values) {
        int[] ints = new int[values.length];

        for (int i = 0; i < values.length; ++i) {
            ints[i] = Integer.valueOf(values[i]);
        }

        return ints;
    }
}

