package ch.asridt.tester;

import ch.asridt.record.RecordItem;
import ch.asridt.rest.RecordList;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Date;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RecordTestInteractive {
    
    private static String[] recordArray;

    public static void main(String[] args) {
        // Create a WebTarget object for our RESTful calls
        String baseURL = "http://localhost:8080/WebRecordManager/resources";
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseURL);

        System.out.println(" *** ************************** ***\n ***    RESTful Record Client\n *** ************************** ***\n");
        boolean timeToQuit = false;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            do {
                timeToQuit = executeMenu(in, target);
            } while (!timeToQuit);
        } catch (IOException e) {
            System.out.println("Error " + e.getClass().getName() + " , quiting.");
            System.out.println("Message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error closing resource " + e.getClass().getName());
            System.out.println("Message: " + e.getMessage());
        }
    }

    public static boolean executeMenu(BufferedReader in, WebTarget target) throws IOException {
        RecordList list;
        String action;
        int id;

        System.out.println("\n\n[L]ist | [R]ead | [A]dd | [D]elete | [I]nput | [Q]uit: ");
        action = in.readLine();
        if (action.length() == 0) {
            System.out.println("Enter one of: L, R, D, A, Q");
            return false;
        } else if (action.toUpperCase().charAt(0) == 'Q') {
            return true;
        }

        switch (action.toUpperCase().charAt(0)) {
            // List all the record resources available using GET
            case 'L':
                //code to list all of the record in the collection
                list = target
                        .path("record")
                        .request(MediaType.APPLICATION_JSON)
                        .get(RecordList.class);
                // convert list of record to string array
                recordArray = list.recordId.toArray(new String[list.recordId.size()]);
                // output
                System.out.println("Record in the collection:");
                for (int i = 0; i < recordArray.length; i++){
                    System.out.println("(" + i + ")" + recordArray[i]);
                }
                break;

            // Display a record element (metadata) using a GET 
            case 'R':
                System.out.println("Enter record number to read: ");
                id = new Integer(in.readLine().trim());

                // code to display a record item
                if (recordArray != null && id < recordArray.length && id >= 0){
                    RecordItem item = target
                            .path("record/" + recordArray[id])
                            .request(MediaType.APPLICATION_JSON)
                            .get(RecordItem.class);
                    System.out.println("RecordItem:");
                    System.out.println(item.toString());
                } else {
                    System.out.println(" ==> Please enter a valid item number between 0 and " + (recordArray.length-1));
                }
                break;

            // Delete a record item using DELETE
            case 'D':
                System.out.println("Enter record number to delete: ");
                id = new Integer(in.readLine().trim());

                // code to delete a record item
                if (recordArray != null && id < recordArray.length && id >= 0){
                    Response response = target
                            .path("record/" + recordArray[id])
                            .request()
                            .delete();
                    if (response.getStatus() == Response.Status.OK.getStatusCode()){
                        System.out.println("Successfully deleted record item with number: " + id);
                    }
                } else {
                    System.out.println(" ==> Please enter a valid item number between 0 and " + (recordArray.length-1));
                }
                break;

            // Add a new record item using a PUT 
            case 'A':
                System.out.println("Enter full path to new record item:");
                String path = in.readLine();

                // code to add a new record item to the collection
                File file = new File(path);
                Path p = file.toPath();
                if (!Files.isRegularFile(p, LinkOption.NOFOLLOW_LINKS)){
                    System.out.println("The file: " + path + " either does not exist or is not a regular file.");
                    break;
                }
                String fileName = p.getFileName().toString();
                final Response response = target
                        .path("record/" + fileName)
                        .request()
                        .put(Entity.entity(file, MediaType.APPLICATION_OCTET_STREAM));
                if (response.getStatus() == Response.Status.OK.getStatusCode()){
                    System.out.println("Successfully uploaded: " + path);
                }
                break;
                
            // Input a new record item
            case 'I':
                System.out.println("Enter new record item manually:");
                // get info about new record item from command line
                RecordItem item = getRecordItemInfo(in);
        
                System.out.println(" ==> Checking entered record:");
                System.out.println(item.toString());
                
                // do the rest part
                final Response insertionResponse = target
                        .path("record/" + item.getId())
                        .request()
                        .post(Entity.entity(item, MediaType.APPLICATION_JSON));
                if (insertionResponse.getStatus() == Response.Status.OK.getStatusCode()){
                    System.out.println("Successfully uploaded item with id: " + item.getId());
                }        
                break;

            default:
                System.out.println("Enter one of: L, R, D, A, Q");
        }

        return false;
    }

    private static RecordItem getRecordItemInfo(BufferedReader in) throws IOException {
        // input record item info from stdin
        System.out.println(" > Please enter record id:");
        String id = in.readLine();
        System.out.println(" > Please enter record type:");
        String recordType = in.readLine();
        System.out.println(" > Please enter date of recording:");
        String dateOfRecording = in.readLine();
        System.out.println(" > Please enter record value:");
        double recordValue = Double.parseDouble(in.readLine());
        System.out.println(" > Please enter record unit:");
        String recordUnit = in.readLine();
        
        // create instance of RecordItem
        RecordItem resultItem = new RecordItem(id, id, new Date());
        resultItem.setRecordType(recordType);
        resultItem.setDateOfRecording(dateOfRecording);
        resultItem.setRecordValue(recordValue);
        resultItem.setRecordUnit(recordUnit);
                
        return resultItem;
        
    }
}
