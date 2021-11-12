package data;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemListTest {

    ItemList test = new ItemList();

    @Test
    void addItemTest() {
        List<Item> testList = new ArrayList<>(Arrays.asList(
                new Item("Test Item 5", "100", "Z-090-000-000"),
                new Item("Test Item 1", "50", "K-091-000-000"),
                new Item("Test Item 2", "75", "A-092-000-000")));

        test.setList(testList);

        Item item = new Item("Xbox 100", "600", "X-801-BRO-45A");

        test.addItem(item);
        List<Item> expected = new ArrayList<>();
        expected.add(new Item("Test Item 5", "100", "Z-090-000-000"));
        expected.add(new Item("Test Item 1", "50", "K-091-000-000"));
        expected.add(new Item("Test Item 2", "75", "A-092-000-000"));
        expected.add(new Item("Xbox 100", "600", "X-801-BRO-45A"));

        List<Item> actual = test.getList();

        assertEquals(expected.get(3).getName(), actual.get(3).getName());
    }

    @Test
    void removeItemTest() {
        List<Item> testList = new ArrayList<>(Arrays.asList(
                new Item("Test Item 5", "100", "Z-090-000-000"),
                new Item("Test Item 1", "50", "K-091-000-000"),
                new Item("Test Item 2", "75", "A-092-000-000")));

        test.setList(testList);

        List<Item> removedItems = new ArrayList<>();
        removedItems.add(test.getList().get(0));
        removedItems.add(test.getList().get(1));

        List<Item> expected = new ArrayList<>();
        expected.add(test.getList().get(2));

        test.removeItems(removedItems);
        List<Item> actual = test.getList();

        assertEquals(expected, actual);
    }

    @Test
    void clearTest() {
        List<Item> testList = new ArrayList<>(Arrays.asList(
                new Item("Test Item 5", "100", "Z-090-000-000"),
                new Item("Test Item 1", "50", "K-091-000-000"),
                new Item("Test Item 2", "75", "A-092-000-000")));

        test.setList(testList);

        test.clear();

        List<Item> expected = new ArrayList<>();
        List<Item> actual = test.getList();

        assertEquals(expected, actual);
    }

    @Test
    void sortByNameTest() {
        List<Item> testList = new ArrayList<>(Arrays.asList(
                new Item("Test Item 5", "100", "Z-090-000-000"),
                new Item("Test Item 1", "50", "K-091-000-000"),
                new Item("Test Item 2", "75", "A-092-000-000")));

        test.setList(testList);
        Item expected = test.getList().get(1);

        test.sortByName();
        Item actual = test.getList().get(0);

        assertEquals(expected, actual);
    }

    @Test
    void sortByValueTest() {
        List<Item> testList = new ArrayList<>(Arrays.asList(
                new Item("Test Item 5", "100", "Z-090-000-000"),
                new Item("Test Item 1", "50", "K-091-000-000"),
                new Item("Test Item 2", "75", "A-092-000-000")));

        test.setList(testList);
        Item expected = test.getList().get(1);

        test.sortByValue();
        Item actual = test.getList().get(0);

        assertEquals(expected, actual);
    }

    @Test
    void sortByValueSerial() {
        List<Item> testList = new ArrayList<>(Arrays.asList(
                new Item("Test Item 5", "100", "Z-090-000-000"),
                new Item("Test Item 1", "50", "K-091-000-000"),
                new Item("Test Item 2", "75", "A-092-000-000")));

        test.setList(testList);
        Item expected = test.getList().get(2);

        test.sortBySerial();
        Item actual = test.getList().get(0);

        assertEquals(expected, actual);
    }

    @Test
    void createPredicateTest() {
        List<Item> testList = new ArrayList<>(Arrays.asList(
                new Item("Test Item 5", "100", "Z-090-000-000"),
                new Item("Scoob", "50", "K-091-000-000"),
                new Item("Scoobie Doo", "75", "A-092-000-000")));
        test.setList(testList);

        FilteredList<Item> filteredList = new FilteredList<>(FXCollections.observableArrayList(test.getList()));
        filteredList.setPredicate(test.createPredicate("Scoob"));

        List<Item> expected = new ArrayList<>();
        expected.add(testList.get(1));
        expected.add(testList.get(2));

        List<Item> actual = filteredList;

        assertEquals(expected, actual);
    }

}