package com.leszko.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void testSum() {
        Calculator calculator = new Calculator();
        assertEquals(3, calculator.sum(1, 2));
    }

    @Test
    void testSumWithNegative() {
        Calculator calculator = new Calculator();
        assertEquals(-1, calculator.sum(-5, 4));
    }

    @Test
    void testSumWithZero() {
        Calculator calculator = new Calculator();
        assertEquals(5, calculator.sum(5, 0));
    }
}
