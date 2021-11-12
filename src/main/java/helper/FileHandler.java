package helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import data.Item;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileHandler {

    private File path;
    private List<Item> list;

    public FileHandler(File path) {
        this.path = path;
    }

    public FileHandler(File path, List<Item> list) {
        this.path = path;
        this.list = list;
    }

    public FileHandler() {
        //Null constructor for testing
    }

    public List<Item> fileImport() {
        String fileType = "";
        try {
            fileType = Files.probeContentType(Paths.get(path.getPath()));
        } catch(IOException e) {
            e.printStackTrace();
        }

        return switch (fileType) {
            case "text/plain" -> importText();
            case "text/html" -> importHTML();
            case "application/json" -> importJSON();
            default -> Collections.emptyList();
        };
    }

    public List<Item> importText() {
        List<Item> importList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int loopCount = 1;
            String line = reader.readLine();

            while(line != null) {
                if (loopCount == 1) {
                    line = reader.readLine();
                    loopCount++;
                }
                else {
                    String[] info = line.split("\\t");
                    Item item = new Item(info[0], info[1], info[2]);
                    importList.add(item);

                    line = reader.readLine();
                }
            }

        } catch (Exception e) {
            return Collections.emptyList();
        }

        return importList;
    }

    public List<Item> importHTML() {
        List<Item> importList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while(line != null) {
                if (line.contains("</td>")) {
                    String[] info = new String[3];
                    for(int i = 0; i < 3; i++) {
                        info[i] = StringUtils.substringBetween(line, "<td>", "</td>");
                        line = reader.readLine();
                    }
                    Item item = new Item(info[0], info[1], info[2]);
                    importList.add(item);
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }

        return importList;
    }

    public List<Item> importJSON() {
        List<Item> inventory;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            Gson gson = new Gson();
            Type listOfItemToken = new TypeToken<ArrayList<Item>>() {}.getType();
            inventory = gson.fromJson(reader, listOfItemToken);
        } catch (IOException e) {
            return Collections.emptyList();
        }

        return inventory;
    }

    public String fileExport()  {
        String fileType = "";
        try {
            fileType = Files.probeContentType(Paths.get(path.getPath()));
        } catch(IOException e) {
            e.printStackTrace();
        }

        return switch (fileType) {
            case "text/plain" -> exportText();
            case "text/html" -> exportHTML();
            case "application/json" -> exportJSON();
            default -> null;
        };
    }

    public String exportText() {
        StringBuilder output = new StringBuilder();
        try (FileWriter writer = new FileWriter(path)) {
            output.append(String.format("Name\tValue\tSerial Number%n"));
            for (Item item : list) {
                output.append(String.format("%s\t%s\t%s%n", item.getName(), item.getValue().toString(), item.getSerial()));
            }
            writer.write(String.valueOf(output));
        } catch (IOException e) {
            return null;
        }
        return String.valueOf(output);
    }

    public String exportHTML() {
        StringBuilder output = new StringBuilder();
        try (FileWriter writer = new FileWriter(path)) {
            output.append(String.format("<!DOCTYPE html>%n%4s<html>%n%8s<head>%n%12s<title>Inventory Table</title>%n%8s</head>%n","","","",""));
            output.append(String.format("%8s<body>%n%12s<table>%n","",""));
            output.append(String.format("%16s<tr>%n%20s<th>Name</th>%n%20s<th>Value</th>%n%20s<th>Serial Number</th>%n%16s</tr>%n","","","","",""));
            for (Item item : list) {
                output.append(String.format("%16s<tr>%n%20s<td>%s</td>%n", "","",item.getName()));
                output.append(String.format("%20s<td>%s</td>%n","",item.getValue()));
                output.append(String.format("%20s<td>%s</td>%n%16s<tr>%n","",item.getSerial(),""));
            }
            output.append(String.format("%12s</table>%n%8s</body>%n%4s</html>","","",""));

            writer.write(String.valueOf(output));
        } catch (IOException e) {
            return null;
        }

        return String.valueOf(output);
    }

    public String exportJSON() {
        String output;

        for (Item item : list) {
            item.setSelected(false);
        }

        try (FileWriter writer = new FileWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            output = gson.toJson(list);
            writer.write(output);
        } catch (IOException e) {
            return null;
        }

        return output;
    }

    public List<Item> getList() {
        return list;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }
}
