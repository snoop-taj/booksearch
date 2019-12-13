package com.mthree;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("This class is for Calculator")
public final class CalculatorTest {

    Calculator calc;

    @BeforeAll
    public static void loadOnce() {

        System.out.println("Static method");
    }

    @BeforeEach
    public void init(TestInfo testInfo, TestReporter testReporter) {
        System.out.println(testInfo.getTestMethod());
        calc = new Calculator();
    }

    @Test
    @DisplayName("This method will perform addition")
    public void addTest() {

        int expected = 30;
        int actual = calc.add(10, 20);

        assertEquals(expected, actual, "should return " + expected);

    }

    @Test
    @Disabled
    public void addTest2() {

        int expected = 30;
        int actual = calc.add(10, 20);

        assertEquals(expected, actual, "should return " + expected);

    }

    @Test
    public void addTest3() {

        assertThrows(ArithmeticException.class, () -> calc.divide(10, 10));

    }

    @Test
    public void addTest4() {

        int expected = 30;
        int actual = calc.add(10, 20);
        boolean isConncted = false;

        assumeTrue(isConncted);
        assertEquals(expected, actual, "should return " + expected);

    }

    @Test
    @EnabledOnOs(OS.LINUX)
    public void addTest5() {

        int expected = 30;
        int actual = calc.add(10, 20);
        boolean isConncted = false;

        assumeTrue(isConncted);
        assertEquals(expected, actual, "should return " + expected);

    }

    @RepeatedTest(value = 5)
    public void addTest6(RepetitionInfo repetitionInfo) {

        System.out.println(repetitionInfo.getCurrentRepetition());
        int expected = 30;
        int actual = calc.add(10, 20);
        assertEquals(expected, actual, "should return " + expected);

    }

    @AfterEach
    public void clean() {
        System.out.println("Cleaning stuff!!!");
    }

    @Nested
    @DisplayName("Nested test class")
    class InnerTestClass {
        @Test
        public void addTest() {

        }
    }
}
