package helper;

import data.Item;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class FileHandler {

    //Create new private File path
    //Create new private List of Items list

    FileHandler(File path) {
        //this path = path
    }

    FileHandler(File path, List<Item> list) {
        //this path = path
        //this list = list
    }

    FileHandler() {
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

    public String fileExport() {
        //Create String "fileType" = Files.probeContentType(path)
        //String output

        //If fileType equals "text/plain":
            //output = exportText()
        //Else If fileType equals "text/html":
            //output = exportHTML()
        //Else If fileType equals "application/json"
            //output = exportJSON()

        //return output
        return null;
    }

    public String exportText() {
        //StringBuilder output is a new StringBuilder
        //Try to create a FileWriter "writer" to the path
            //Append "Name%tValue%tSerial Number%n" to output
            //For each item in list:
                //Append "'item.getName()'%t'item.getValue()'%t'item.getSerial()'%n" to output
            //Write the string value of output with writer
        //Catch an IOException:
            //Return null
        //return String value of output
        return null;
    }

    public String exportHTML() {
        //StringBuilder output is a new StringBuilder
        //Try to create a FileWriter "writer" to the path
            //Append "<!DOCTYPE html>%n%t<html>%n%t%t<head>%n%t%t%t<title>Inventory Table</title>%n%t%t</head>%n" to output
            //Append "%t%t<body>%n%t%t%t<table>%n%n%t%t%t%t%t%n" to output
            //Append "%t%t%t%t%t<th>Name</th>%n%t%t%t%t%t<th>Value</th>%n%t%t%t%t%t<th>Serial Number</th>%n%t%t%t%t%t</tr>%n" to output
            //For each item in list:
                //Append "%t%t%t%t%t<tr>%n" to output
                //Append "%t%t%t%t%t%t<td>'item.getName()'</td>%n" to output
                //Append "%t%t%t%t%t%t<td>'item.getValue()'</td>%n" to output
                //Append "%t%t%t%t%t%t<td>'item.getSerial()'</td>%n" to output
            //Append "%t%t%t</table>%n" to output
            //Append "%t%t</body>%n" to output
            //Append "%t</html>" to output

            //Write String value of output to path using writer

        //Catch IOException:
            //return null

        //return String value of output
        return null;
    }

    public String exportJSON() {
        //Create String output
        //Try to create a FileWriter "writer" to the path
            //Create new Gson "gson"
            //output = gson.toJson(list)
            //Write output to path using writer
        //Catch IOException:
            //return null

        //return output
        return null;
    }

}
