/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Predicate;

public class ItemList {

    private final ObservableList<Item> items = FXCollections.observableArrayList();

    public void addItem(String name, String value, String serial) {
        Item item = new Item(name, value, serial);
        items.add(item);
    }

    public void addItem(Item item) {
        //Adds an item to the list.
        items.add(item);
    }

    public void removeItems(List<Item> removedItems) {
        //Removes all the items provided from the list.
        items.removeAll(removedItems);
    }

    public void clear() {
        //Clears the list.
        items.clear();
    }

    //Sorters

    public void sortByName() {
        //Sorts the list by name in alphabetical order.
        items.sort((o1, o2) -> Integer.compare(o1.getName().compareToIgnoreCase(o2.getName()), 0));
    }

    public void inverseSortByName() {
        //Sorts the list by name in reverse alphabetical order.
        items.sort((o1, o2) -> Integer.compare(0, o1.getName().compareToIgnoreCase(o2.getName())));
    }

    public void sortByValue() {
        //Sorts the list by value in ascending order.
        items.sort((o1, o2) -> Integer.compare(o1.getValue().compareTo(o2.getValue()), 0));
    }

    public void inverseSortByValue() {
        //Sorts the list by value in descending order.
        items.sort((o1, o2) -> Integer.compare(0, o1.getValue().compareTo(o2.getValue())));
    }

    public void sortBySerial() {
        //Sorts the list by serial in alphabetical order.
        items.sort((o1, o2) -> Integer.compare(o1.getSerial().compareToIgnoreCase(o2.getSerial()), 0));
    }

    public void inverseSortBySerial() {
        //Sorts the list by serial in reverse alphabetical order.
        items.sort((o1, o2) -> Integer.compare(0, o1.getSerial().compareToIgnoreCase(o2.getSerial())));
    }

    //Filter Stuff

    public boolean searchItem(Item item, String search) {
        //Returns the item if it matches the search field input.
        return (item.getName().toLowerCase().contains(search.toLowerCase()) ||
                item.getSerial().toLowerCase().contains(search.toLowerCase()));
    }

    public Predicate<Item> createPredicate(String search) {
        //Creates the predicate for the filteredList by going through each item in the list and returning the result of searchItem().
        return item -> {
            if(search == null || search.isEmpty()) return true;
            return searchItem(item, search);
        };
    }

    //Getters and Setter

    public ObservableList<Item> getObservableList() {
        return items;
    }

    public List<Item> getList() {
        return getObservableList();
    }

    public void setList(List<Item> newList) {
        items.clear();
        items.addAll(newList);
    }
}
