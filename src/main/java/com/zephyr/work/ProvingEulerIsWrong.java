package com.zephyr.work;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 8/7/18
 * Time: 9:49 AM
 */
public class ProvingEulerIsWrong extends AbstractWork {
    private static final Logger LOG = LoggerFactory.getLogger(ProvingEulerIsWrong.class);

    /**
     *  find a case that satisfies x^n + y^n + z^n + ... + (n-1)th^n = w^n, while n > 2
     *  such as 27^5 + 84^5 + 110^5 + 133^5 = 144^5
     */

    private List<long[]> answers = new ArrayList<>();

    @Override
    protected void doWorkInternal() {
        int startN = 3, endN = 10;
        int depth = 150;

        for (int n = startN; n <= endN; n++) {
            long[] guessing = new long[n];
            boolean found = calculate(guessing, 0, depth);

            if (found) {
                LOG.info("Found answers with n={}, limit={}", n, depth);
            } else {
                LOG.info("Answer not found :( with n={}, limit={}", n, depth);
            }
        }

        LOG.info("All answers:");
        answers.forEach(a -> {
            LOG.info(Arrays.toString(a));
        });

    }

    private boolean calculate(long[] numbers, int currPos, int depth) {
        boolean ret = false;

        if (currPos == numbers.length) {
            return ret;
        }

        for (int i = 1; i <= depth; i++) {
            numbers[currPos] = i;

            if (foundAnswer(numbers)) {
                LOG.info("Found an answer! numbers={}", Arrays.toString(numbers));
                answers.add(Arrays.copyOf(numbers, numbers.length));
                ret = true;
                break;
            }

            ret |= calculate(numbers, currPos + 1, depth);
        }

        return ret;
    }

    private boolean foundAnswer(long[] numbers) {
        int n = numbers.length;
        long left = 0;

        for (int i = 0; i < numbers.length - 1; i++) {
            left += (long) Math.pow(numbers[i], n);
        }

        long right = (long) Math.pow(numbers[numbers.length - 1], n);

        return left == right;
    }

    @Test
    public void test() {
        doWorkInternal();
    }

}
