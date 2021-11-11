package helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.Item;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        //Create a list of items "importList"
        //Create String "fileType" = Files.probeContentType(path)

        //If fileType equals "text/plain":
            //importList = importText()
        //Else If fileType equals "text/html":
            //importList = importHTML()
        //Else If fileType equals "application/json"
            //importList = importJSON()

        //return importList
        return Collections.emptyList();
    }

    public List<Item> importText() {
        //Create a list of Items "importList"
        //Try to create a new BufferedReader "reader" to read path
            //String line = reader.readLine()
            //line = reader.readLine()
            //While line is not null:
                //Array of Strings info = line.split with delimiter "%t"

                //Item item is a new item with values info[0], info[1], info[2]

                //Add item to importList
                //line = reader.readLine()
        //Catch an exception:
            //return an empty list

        //return importList
        return Collections.emptyList();
    }

    public List<Item> importHTML() {
        //Create a list of Items "importList"
        //Try to create a new BufferedReader "reader" to read path
            //String line = reader.readLine()
            //line = reader.readLine()
            //While line is not null:
                //If line does not contain "/td":
                    //line = reader.readLine()
                //Else:
                    //Array of Strings info
                    //Loop 3 times:
                        //info[loop #] = subString of line between "<td>" and "</td>"
                    //Item item is a new item with values info[0], info[1], info[2]
                    //Add item to importList
                    //line = reader.readLine()
        //Catch an exception:
            //return an empty list

        //return importList
        return Collections.emptyList();
    }

    public List<Item> importJSON() {
        //Create a list of Items "inventory"
        //Try to create a new BufferedReader "reader" to read path
            //inventory = gson.fromJson(reader, Item.Class)
        //Catch an Exception
            //Return an empty list

        //return importList
        return Collections.emptyList();
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

    public void setPath(File path) {
        this.path = path;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }
}
