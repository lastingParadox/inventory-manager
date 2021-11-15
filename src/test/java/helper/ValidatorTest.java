package helper;

import data.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    Validator test = new Validator();

    @ParameterizedTest
    @CsvSource({"Test,Test","a,null","123456789010111212345678901011121234567890101112123456789010111212345678901011121234567890101112123456789010111212345678901011121234567890101112123456789010111212345678901011121234567890101112123456789010111212345678901011121234567890101112123456789010111213,null"})
    void verifyNameTest(String input, String expected) {
        if (expected.equals("null"))
            expected = null;

        String actual = test.verifyName(input);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"1.00,1.00","-1.45,null","a,null"})
    void verifyValueTest(String input, String expected) {
        if (expected.equals("null"))
            expected = null;

        String actual = test.verifyValue(input);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"A-0A0-A0A-0A0,A-0A0-A0A-0A0","1-ABC-234-DEF,null","bruh,null"})
    void verifySerialTest(String input, String expected) {
        if (expected.equals("null"))
            expected = null;

        String actual = test.verifySerial(input);
        assertEquals(expected, actual);
    }

    @Test
    void verifyUniqueTestNull() {
        ObservableList<Item> testList = FXCollections.observableArrayList(Arrays.asList(
                new Item("Test Item", "0.01", "A-090-000-000"),
                new Item("Test Item 2", "0.02", "A-091-000-000"),
                new Item("Test Item 3", "0.03", "A-092-000-000")));


        String actual = test.verifyUnique("A-090-000-000", testList);

        assertNull(actual);
    }

    @Test
    void verifyUniqueTestSuccess() {
        ObservableList<Item> testList = FXCollections.observableArrayList(Arrays.asList(
                new Item("Test Item", "0.01", "A-090-000-000"),
                new Item("Test Item 2", "0.02", "A-091-000-000"),
                new Item("Test Item 3", "0.03", "A-092-000-000")));

        String expected = "Z-090-000-000";
        String actual = test.verifyUnique("Z-090-000-000", testList);

        assertEquals(expected, actual);
    }

}