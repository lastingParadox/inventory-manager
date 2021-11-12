/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ItemList {

    private final ObservableList<Item> items = FXCollections.observableArrayList();

    public void addItem(String name, String value, String serial) {
        Item item = new Item(name, value, serial);
        items.add(item);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItems(List<Item> removedItems) {
        items.removeAll(removedItems);
    }

    public void clear() {
        items.clear();
    }

    //Sorters

    public void sortByName() {
        items.sort(Comparator.comparing(Item::getName));
    }

    public void sortByValue() {
        items.sort(Comparator.comparing(Item::getValue));
    }

    public void sortBySerial() {
        items.sort(Comparator.comparing(Item::getSerial));
    }

    //Filter Stuff

    public boolean searchItem(Item item, String search) {
        return (item.getName().toLowerCase().contains(search.toLowerCase()) ||
                item.getSerial().toLowerCase().contains(search.toLowerCase()));
    }

    public Predicate<Item> createPredicate(String search) {
        return item -> {
            if(search == null || search.isEmpty()) return true;
            return searchItem(item, search);
        };
    }

    //Getter and Setter

    public ObservableList<Item> getObservableList() {
        return items;
    }
    public List<Item> getList() {
        return items;
    }

    public void setList(List<Item> newList) {
        items.clear();
        items.addAll(newList);
    }
}
