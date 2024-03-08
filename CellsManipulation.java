import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

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
                cell.setBodyWeight(cleanBodyWeight(data[5]));
                cell.setBodySim(cleanBodySim(data[6]));
                cell.setDisplayType(cleanStrings(data[7]));
                cell.setDisplaySize(cleanDisplaySize(data[8]));
                cell.setDisplayResolution(cleanStrings(data[9]));
                cell.setFeaturesSensors(cleanFeatureSensors(data[10]));
                cell.setPlatformOS(cleanPlatformOS(data[11]));
                
                //Store each cell object in hashmap, mapped to its row number.
                cellMap.put(id++, cell);
            }
            displayUniqueData(cellMap);

           //Get unique data for each cell attribute
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayUniqueData(HashMap<Integer, Cell> cellMap){
        //Initialize unique data function input arrays
        ArrayList<String> oemList = new ArrayList<>();
        ArrayList<String> modelList = new ArrayList<>();
        ArrayList<Integer> launchAnnouncedList = new ArrayList<>();
        ArrayList<String> launchStatusList = new ArrayList<>();
        ArrayList<String> bodyDimensionsList = new ArrayList<>();
        ArrayList<Float> bodyWeightList = new ArrayList<>();
        ArrayList<String> bodySimList = new ArrayList<>();
        ArrayList<String> displayTypeList = new ArrayList<>();
        ArrayList<Float> displaySizeList = new ArrayList<>();
        ArrayList<String> displayResolutionList = new ArrayList<>();
        ArrayList<String> featureSensorsList = new ArrayList<>();
        ArrayList<String> platformOSsList = new ArrayList<>();

        for(int i = 1; i <= cellMap.size(); i++){
            Cell cell = cellMap.get(i);
            oemList.add(cell.getOEM());
            modelList.add(cell.getModel());
            launchAnnouncedList.add(cell.getlaunchAnnounced());
            launchStatusList.add(cell.getLaunchStatus());
            bodyDimensionsList.add(cell.getBodyDimensions());
            bodyWeightList.add(cell.getBodyWeight());
            bodySimList.add(cell.getBodySim());
            displayTypeList.add(cell.getDisplayType());
            displaySizeList.add(cell.getDisplaySize());
            displayResolutionList.add(cell.getDisplayResolution());
            featureSensorsList.add(cell.getFeaturesSensors());
            platformOSsList.add(cell.getPlatformOS());
        }

        Set<String> uniqueOEM = uniqueDataStrings(oemList);
        Set<String> uniqueModel = uniqueDataStrings(modelList);
        Set<Integer> uniqueLaunchAnnounced = uniqueDataInteger(launchAnnouncedList);
        Set<String> uniqueLaunchStatus = uniqueDataStrings(launchStatusList);
        Set<String> uniqueBodyDimension = uniqueDataStrings(bodyDimensionsList);
        Set<Float> uniqueBodyWeightList = uniqueDataFloats(bodyWeightList);
        Set<String> uniqueBodySim = uniqueDataStrings(bodySimList);
        Set<String> uniqueDisplayType = uniqueDataStrings(displayTypeList);
        Set<Float> uniqueDisplaySize = uniqueDataFloats(displaySizeList);
        Set<String> uniqueDisplayResolution = uniqueDataStrings(displayResolutionList);
        Set<String> uniqueFeatureSensors = uniqueDataStrings(featureSensorsList);
        Set<String> uniquePlatformOS = uniqueDataStrings(platformOSsList);

        System.out.println("Unique items for OEM: " + uniqueOEM);
        System.out.println("\n\nUnique items for Model: " + uniqueModel);
        System.out.println("\n\nUnique items for Launch Announced: " + uniqueLaunchAnnounced);
        System.out.println("\n\nUnique items for Launch Status: " + uniqueLaunchStatus);
        System.out.println("\n\nUnique items for Body Dimension: " + uniqueBodyDimension);
        System.out.println("\n\nUnique items for Body Weight: " + uniqueBodyWeightList);
        System.out.println("\n\nUnique items for Body Sim: " + uniqueBodySim);
        System.out.println("\n\nUnique items for Display Type: " + uniqueDisplayType);
        System.out.println("\n\nUnique items for Display Size: " + uniqueDisplaySize);
        System.out.println("\n\nUnique items for Display Resolution: " + uniqueDisplayResolution);
        System.out.println("\n\nUnique items for Feature Sensors: " + uniqueFeatureSensors);
        System.out.println("\n\nUnique items for Platform OS: " + uniquePlatformOS);
    }

    //Returns unique values for input data type of string
    public static Set<String> uniqueDataStrings(ArrayList<String> input){
        Set<String> uniqueValues = new HashSet<>();
        for(String item : input){
            String s = clean(item);
            uniqueValues.add(s);
        }

        return uniqueValues;
    }

    //Return unique values for input data type of flaot
    public static Set<Float> uniqueDataFloats(ArrayList<Float> input){
        Set<Float> uniqueValues = new HashSet<>();
        for(Float item : input){
            uniqueValues.add(item);
        }
        return uniqueValues;
    }

    public static Set<Integer> uniqueDataInteger(ArrayList<Integer> input){
        Set<Integer> uniqueValues = new HashSet<>();
        for(Integer item : input){
            uniqueValues.add(item);
        }
        return uniqueValues;
    }

    public static String clean(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        input = input.trim();
        return input;
    }

    public static String cleanPlatformOS(String input) {
        if (input.equals("-") || input.isEmpty()) {
            return null;
        }
        
        input = removeQuotes(input);

        // Regular expression pattern to match everything up to the first comma or end of string
        Pattern pattern = Pattern.compile("^(.*?)(?:,|$)");
        Matcher matcher = pattern.matcher(input);
        
        if (matcher.find()) {
            return matcher.group(1).trim();
        } else {
            return input.trim();
        }
    }

    public static String cleanStrings(String input){
        input = removeQuotes(input);
        if( input.equals("-") ||  input.isEmpty()){
            return null;
        }
        return input;
    }

    public static String cleanFeatureSensors(String input){
        input = removeQuotes(input);
        if(input.equals("-") || input.isEmpty()){
            return null;
        }
        if(onlyNumbers(input)){
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

    public static Float cleanBodyWeight(String input) {
        //Regular expression to match the weight in grams
        Pattern pattern = Pattern.compile("(\\d+) g");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String temp = matcher.group(1);
            return Float.parseFloat(temp);
        } else {
            return null; 
        }
    }

    public static Float cleanDisplaySize(String input) {
        //Regular expression to match the weight in grams
        Pattern pattern = Pattern.compile("(\\d+) inches");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String temp = matcher.group(1);
            return Float.parseFloat(temp);
        } else {
            return null; 
        }
    }

    public static String cleanLaunchStatus(String str) {
        //Regular expression to match a 4-digit year
        str = removeQuotes(str);
        Pattern pattern = Pattern.compile("\\b\\d{4}\\b");
        Matcher matcher = pattern.matcher(str);
        
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
    public static boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //This function essentially checks if the given string is made of only numbers. If it
    //parses to a double it is only numbers. 
    public static boolean onlyNumbers(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String cleanBodySim(String input) {
        input = removeQuotes(input);
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
    private Float body_weight;
    private String body_sim;
    private String display_type;
    private Float display_size;
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

    public Float getBodyWeight(){
        return body_weight;
    }

    public void setBodyWeight(Float bodyWeight){
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

    public Float getDisplaySize(){
        return display_size;
    }

    public void setDisplaySize(Float displaySize){
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