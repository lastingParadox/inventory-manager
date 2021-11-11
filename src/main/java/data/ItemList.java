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
        //Item item is a new item with name, value, serial
        //Add item to items
    }

    public void removeItems(List<Item> removedItems) {
        //Remove all removedItems from items
    }

    public void clear() {
        //Clear items
    }

    //Sorters

    public void sortByName() {
        //Lambda
        //items.sort(o1, o2) -> return o1.getName().compareTo(o2.getName)
    }

    public void sortByValue() {
        //Lambda
        //items.sort(o1, o2) -> return o1.getValue().compareTo(o2.getValue)
    }

    public void sortBySerial() {
        //Lambda
        //items.sort(o1, o2) -> return o1.getSerial().compareTo(o2.getSerial)
    }

    //Filter Stuff

    public boolean searchItem(Item item, String search) {
        //Return value:
        //If item's name contains search
        //Or If item's serial number contains search
        return false;
    }

    public Predicate<Item> createPredicate(String search) {
        //Lambda
        //Return item ->
        //If search is null or search is Empty, return true
        //return searchItem(item, search)
        return null;
    }

    //Getter and Setter

    public List<Item> getList() {
        return items;
    }

    public void setList(List<Item> newList) {
        //Clear items
        //items.addAll(newList)
    }
}
