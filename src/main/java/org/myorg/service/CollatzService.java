package org.myorg.service;

import java.util.ArrayList;
import java.util.List;

public class CollatzService {
    public static String sequence(int number) {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(number);
        while (sequence.get(sequence.size() - 1) != 1) {
            int n = sequence.get(sequence.size() - 1);
            if (n % 2 == 0) {
                sequence.add(n / 2);
            } else {
                sequence.add((3 * n) + 1);
            }
        }

        return formatSequence(sequence);
    }

    private static String formatSequence(List<Integer> sequence) {
        StringBuilder res = new StringBuilder(String.valueOf(sequence.get(0)));
        for (int index = 1; index < sequence.size(); index++) {
            res.append(" -> ").append(String.valueOf(sequence.get(index)));
        }

        return res.toString();
    }
}
