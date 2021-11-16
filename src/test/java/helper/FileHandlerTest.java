/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package helper;

import data.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileHandlerTest {

    FileHandler test = new FileHandler();

    @BeforeAll
    void createList() {
        List<Item> testList = new ArrayList<>(Arrays.asList(
                new Item("Test Item", "0.01", "A-090-000-000"),
                new Item("Test Item 2", "0.02", "A-091-000-000"),
                new Item("Test Item 3", "0.03", "A-092-000-000")));

        test.setList(testList);
    }

    @Test
    void exportTextTest() {
        test.setPath(new File("./tests/test1_out.txt"));


        String expected = String.format("Name\tValue\tSerial Number%n") +
                          String.format("Test Item\t0.01\tA-090-000-000%n") +
                          String.format("Test Item 2\t0.02\tA-091-000-000%n") +
                          String.format("Test Item 3\t0.03\tA-092-000-000%n");

        String actual = test.exportText();

        assertEquals(expected, actual);
    }

    @Test
    void exportHTMLTest() {
        test.setPath(new File("./tests/test2_out.html"));


        String expected = String.format("<!DOCTYPE html>%n%4s<html>%n%8s<head>%n%12s<title>Inventory Table</title>%n%8s</head>%n","","","","") +
                          String.format("%8s<body>%n%12s<table>%n","","") +
                          String.format("%16s<tr>%n%20s<th>Name</th>%n%20s<th>Value</th>%n%20s<th>Serial Number</th>%n%16s</tr>%n","","","","","") +

                          String.format("%16s<tr>%n%20s<td>%s</td>%n", "","","Test Item") +
                          String.format("%20s<td>%s</td>%n","","0.01") +
                          String.format("%20s<td>%s</td>%n%16s<tr>%n","","A-090-000-000","") +

                          String.format("%16s<tr>%n%20s<td>%s</td>%n", "","","Test Item 2") +
                          String.format("%20s<td>%s</td>%n","","0.02") +
                          String.format("%20s<td>%s</td>%n%16s<tr>%n","","A-091-000-000","") +

                          String.format("%16s<tr>%n%20s<td>%s</td>%n", "","","Test Item 3") +
                          String.format("%20s<td>%s</td>%n","","0.03") +
                          String.format("%20s<td>%s</td>%n%16s<tr>%n","","A-092-000-000","") +

                          String.format("%12s</table>%n%8s</body>%n%4s</html>","","","");

        String actual = test.exportHTML();

        assertEquals(expected, actual);
    }

    @Test
    void exportJSONTest() {
        test.setPath(new File("./tests/test3_out.json"));

        //Stupid Gson using \n line breaks
        String expected = """
                [
                  {
                    "selected": false,
                    "name": "Test Item",
                    "value": 0.01,
                    "serialNumber": "A-090-000-000"
                  },
                  {
                    "selected": false,
                    "name": "Test Item 2",
                    "value": 0.02,
                    "serialNumber": "A-091-000-000"
                  },
                  {
                    "selected": false,
                    "name": "Test Item 3",
                    "value": 0.03,
                    "serialNumber": "A-092-000-000"
                  }
                ]""";

        String actual = test.exportJSON();

        assertEquals(expected, actual);
    }

    @Test
    void importTextTest() {
        test.setPath(new File("./tests/test1_out.txt"));

        List<Item> expected = test.getList();

        List<Item> actual = test.importText();

        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
        assertEquals(expected.get(2).getName(), actual.get(2).getName());
    }

    @Test
    void importHTMLTest() {
        test.setPath(new File("./tests/test2_out.html"));

        List<Item> expected = test.getList();

        List<Item> actual = test.importHTML();

        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
        assertEquals(expected.get(2).getName(), actual.get(2).getName());
    }

    @Test
    void importJSONTest() {
        test.setPath(new File("./tests/test3_out.json"));

        List<Item> expected = test.getList();

        List<Item> actual = test.importJSON();

        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
        assertEquals(expected.get(2).getName(), actual.get(2).getName());
    }

}