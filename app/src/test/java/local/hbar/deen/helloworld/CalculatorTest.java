package local.hbar.deen.helloworld;

/*
 * Copyright 2018, Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

/**
 * JUnit4 unit tests for the calculator logic. These are local unit tests; no device needed
 */
@RunWith(JUnit4.class)
public class CalculatorTest {

    private Calculator mCalculator;

    /**
     * Set up the environment for testing
     */
    @Before
    public void setUp() {
        mCalculator = new Calculator();
    }

    /**
     * Test for simple addition
     */
    @Test
    public void addTwoNumbers() {
        double resultAdd = mCalculator.add(1d, 1d);
        assertThat(resultAdd, is(equalTo(2d)));
    }

    @Test
    public void addTwoNumbersNegative() {
        double resultAdd = mCalculator.add(-1d, -1d);
        assertThat(resultAdd, is(equalTo(-2d)));
    }

    @Test
    public void addTwoNumbersFloating() {
        double resultAdd = mCalculator.add(1.22222222222222222d, 2.811111111111111111d);
        assertThat(resultAdd, is(closeTo(4.0d, 0.5d)));
    }

    @Test
    public void addTwoLargeNumbers() {
        double resultAdd = mCalculator.add((double) (Integer.MAX_VALUE), (double) (Integer.MAX_VALUE));
        assertThat(resultAdd, is(equalTo((double) (Integer.MAX_VALUE) + (double) (Integer.MAX_VALUE))));
    }

    @Test
    public void addNumberWithZero() {
        double resultAdd = mCalculator.add(1d, 0d);
        assertThat(resultAdd, is(equalTo(1d)));
    }

    @Test
    public void addNumberWithInf() {
        double resultAdd = mCalculator.add(1d, Double.POSITIVE_INFINITY);
        assertThat(resultAdd, is(equalTo(Double.POSITIVE_INFINITY)));
    }


    @Test
    public void subTwoNumbers() {
        double resultSub = mCalculator.sub(1d, 1d);
        assertThat(resultSub, is(equalTo(0d)));
    }

    @Test
    public void subWorksWithNegativeResult() {
        double resultSub = mCalculator.sub(1d, 17d);
        assertThat(resultSub, is(equalTo(-16d)));
    }

    @Test
    public void mulTwoNumbers() {
        double resultMul = mCalculator.mul(32d, 2d);
        assertThat(resultMul, is(equalTo(64d)));
    }

    @Test
    public void divTwoNumbers() {
        double resultDiv = mCalculator.div(32d, 2d);
        assertThat(resultDiv, is(equalTo(16d)));
    }

    @Test
    public void divTwoNumbersZero() {
        double resultDiv = mCalculator.div(32d, 0);
        assertThat(resultDiv, is(equalTo(Double.POSITIVE_INFINITY)));
    }
}
