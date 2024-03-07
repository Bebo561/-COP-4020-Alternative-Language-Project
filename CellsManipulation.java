import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public class CellsManipulation {

    public static void main(String[] args) {
        String csvFile = "cells.csv"; 
        
        HashMap<Integer, Cell> cellMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            //Skip the headers
            br.readLine();
            
            String line;
            int id = 1; // Start ID from 1
            
            //Go through each row of the csv
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                //Take in input of csv into cell object
                Cell cell = new Cell();
                cell.setOEM(cleanStrings(data[0]));
                cell.setModel(cleanStrings(data[1]));
                cell.setLaunchAnnounced(cleanLaunchAnnounced(data[2], id));
                cell.setLaunchStatus(cleanLaunchStatus(data[3]));
                cell.setBodyDimensions(cleanStrings(data[4]));
                cell.setBodyWeight(data[5]);
                cell.setBodySim(cleanBodySim(data[6]));
                cell.setDisplayType(cleanStrings(data[7]));
                cell.setDisplaySize(data[8]);
                cell.setDisplayResolution(cleanStrings(data[9]));
                cell.setFeaturesSensors(data[10]);
                cell.setPlatformOS(data[11]);
                
                //Store each cell object in hashmap, mapped to its row number.
                cellMap.put(id++, cell);
            }

           //Print out initial cells
           for(int i = 1; i <= cellMap.size(); i++){
            Cell cell = cellMap.get(i);
            
            System.out.println("Cell" + i + ": " + cell.getDisplayResolution());
            
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String cleanStrings(String input){
        input = removeQuotes(input);
        if( "-".equals(input) ){
            return null;
        }
        return input;
    }

    //This function takes in the input of the launch announced string, cleans it,
    //and returns the year. If a year cannot be parsed, it is set to null.
    public static Integer cleanLaunchAnnounced(String input, Integer id){
        input = removeQuotes(input);

        Integer returnValue;
        if(input.length() < 4){
            return null;
        }
        else if(!isNumeric(input.substring(0,4))){
            return null;
        }
        else{
            String temp = input.substring(0,4);
            returnValue = Integer.parseInt(temp);
        }

        return returnValue;
    }

    //Remove quotations from within strings
    public static String removeQuotes(String str) {
        return str.replace("\"", "");
    }

    public static String cleanLaunchStatus(String str) {
        //Regular expression to match a 4-digit year
        str = removeQuotes(str);
        Pattern pattern = Pattern.compile("\\b\\d{4}\\b");
        Matcher matcher = pattern.matcher(str);
        
        //Check if a match is found
        if("Discontinued".equals(str) || "Cancelled".equals(str )){
            return str;
        }
        else if (matcher.find()) {
            return matcher.group();
        } else {
            return null; 
        }
    }

    //Helper function used to check if number is an integer or not
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String cleanBodySim(String input) {
        if(input.equals("Yes" )|| input.equals("No")){
            return null;
        }
        return input;

    }
}


class Cell {
    private String oem;
    private String model;
    private Integer launch_announced;
    private String launch_status;
    private String body_dimensions;
    private String body_weight;
    private String body_sim;
    private String display_type;
    private String display_size;
    private String display_resolution;
    private String features_sensors;
    private String platform_os;
    
    // Getters and setters 
    public String getOEM(){
        return oem;
    }

    public void setOEM(String OEM){
        this.oem = OEM;
    }

    public String getModel(){
        return model;
    }

    public void setModel(String model){
        this.model = model;
    }  

    public Integer getlaunchAnnounced(){
        return launch_announced;
    }

    public void setLaunchAnnounced(Integer launchedAnnounced){
        this.launch_announced = launchedAnnounced;
    }

    public String getLaunchStatus(){
        return launch_status;
    }

    public void setLaunchStatus(String launchStatus){
        this.launch_status = launchStatus;
    }

    public String getBodyDimensions(){
        return body_dimensions;
    }

    public void setBodyDimensions(String bodyDimensions){
        this.body_dimensions = bodyDimensions;
    }

    public String getBodyWeight(){
        return body_weight;
    }

    public void setBodyWeight(String bodyWeight){
        this.body_weight = bodyWeight;
    }

    public String getBodySim(){
        return body_sim;
    }

    public void setBodySim(String bodySim){
        this.body_sim = bodySim;
    }

    public String getDisplayType(){
        return display_type;
    }

    public void setDisplayType(String displayType){
        this.display_type = displayType;
    }

    public String getDisplaySize(){
        return display_size;
    }

    public void setDisplaySize(String displaySize){
        this.display_size = displaySize;
    }

    public String getDisplayResolution(){
        return display_resolution;
    }

    public void setDisplayResolution(String displayResolution){
        this.display_resolution = displayResolution;
    }

    public String getFeaturesSensors(){
        return features_sensors;
    }

    public void setFeaturesSensors(String featureSensor){
        this.features_sensors = featureSensor;
    }

    public String getPlatformOS(){
        return platform_os;
    }

    public void setPlatformOS(String platformOS){
        this.platform_os = platformOS;
    }
}