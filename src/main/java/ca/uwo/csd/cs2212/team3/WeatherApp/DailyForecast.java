package ca.uwo.csd.cs2212.team3.WeatherApp;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;

/**
 *  This class holds and distributes information about the daily forecast. It is a child of HourlyForecast
 * and uses its Update() function and it's ChangeDataSet(int) function. Being a grandchild of WeatherJSON
 * this class also uses it's functions GetSkyCondition(),GetSkyCode(),and RoundDouble(double).\
 * 
 * @author Liam Jowahir-Sindrey
 *
 */
public class DailyForecast extends HourlyForecast {
    
    
    /**
     * Constructs the object using the name of a city and its country code
     * 
     * @param City		Name of the city
     * @param CountryCode		Code for the country the city is in
     */
    DailyForecast(String City,String CountryCode) 
    {
        this.Url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+City+","+CountryCode;
        try{
            String JsonData = org.apache.commons.io.IOUtils.toString(new URL(Url));
            this.WeatherSet = (JSONObject) JSONValue.parseWithException(JsonData);
            
            
        }
        catch (IOException | ParseException p){
            System.out.println("Failed to Load data");
            this.WeatherSet = (JSONObject) JSONValue.parse("{\"coord\":{\"lon\":0,\"lat\":0},\"sys\":{\"type\":3,\"id\":-1,\"message\":0,\"country\":\"None\",\"sunrise\":0,\"sunset\":0},\"weather\":[{\"id\":0,\"main\":\"NA\",\"description\":\"NA\",\"icon\":\"NULL\"}],\"base\":\"cmc stations\",\"main\":{\"temp\":0,\"humidity\":0,\"pressure\":0,\"temp_min\":0,\"temp_max\":0},\"wind\":{\"speed\":0,\"deg\":0},\"rain\":{\"3h\":0},\"clouds\":{\"all\":0},\"dt\":0,\"id\":0,\"name\":\"InvalidInput\",\"cod\":200}");
        }
    }
    
    
    
    /**
     * returns the day of the current forecast being used
     */
    String GetTime()
    {
        long time = (long) this.Weather.get("dt");
        return FormatDate(time);
    }
    
    
    /**
     * formats the date in seconds to show the information we want
     * 
     * @param seconds	the date we want formatted in seconds
     */
    public String FormatDate(long seconds)
    {
        
        String date = new java.text.SimpleDateFormat("E d MMM").format(new java.util.Date(seconds*1000));
        return date;
    }
    
    
    /**
     * returns the rounded temperature for the current day in celcius
     */
    double GetTempC()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("temp");
        String temp2 = temp.get("day").toString();
        return (RoundDouble(Double.parseDouble(temp2)-273.15));
        
    }
    /**
     * returns the rounded temperature for the current day in f
     */
    double GetTempF()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("temp");
        String temp2 = temp.get("day").toString();
        return (RoundDouble(9/5.0*(Double.parseDouble(temp2)-273.15)+32));
    }
    /**
     * returns maximum temperature for the current day in C
     */
    double GetMaxTempC()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("temp");
        String temp2 = temp.get("max").toString();
        return (RoundDouble(Double.parseDouble(temp2)-273.15));
    }
    /**
     * returns minimum temperature for the current day in F
     */
    double GetMinTempC()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("temp");
        String temp2 = temp.get("min").toString();
        return (RoundDouble(Double.parseDouble(temp2)-273.15));
    }
    /**
     * returns maximum temperature for the current day in F
     */
    double GetMaxTempF()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("temp");
        String temp2 = temp.get("max").toString();
        return (RoundDouble(9/5.0*(Double.parseDouble(temp2)-273.15)+32));
    }
    /**
     * returns minimum temperature for the current day in F
     */
    double GetMinTempF()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("temp");
        String temp2 = temp.get("min").toString();
        return (RoundDouble(9/5.0*(Double.parseDouble(temp2)-273.15)+32));
    }
    
    
    
}
