package ca.uwo.csd.cs2212.team3.WeatherApp;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;





/**
 * 	This class is made to hold and give out the information for the 3 hour forecasts. It uses the 
 * inherited methods Update(),GetTempC(),GetTempF(),GetSkyCondition(),GetSkyCode(). All other inherited
 * methods should not be used as most of the information is not available. To change which 3 hour 
 * increment you want call the function ChangeHour(int). Example: ChangeHour(4);return HourlyForecast.GetTempC();
 * will give you the temperature in degrees Celsius 12(4*3) hours from now.
 *
 * @author	Liam Jowahir-Sindrey
 * @version	1.0
 *
 */
public class HourlyForecast extends WeatherJSON {
    
    JSONObject WeatherSet;
    
    HourlyForecast() 
    {
    }
    
    
    /**
     * Constructs the object and loads the JSON data into it from openweathermaps based on the city and country
     * @param City		name of city we want to store weather data for
     * @param CountryCode	country code of country city is in
     */
    HourlyForecast(String City,String CountryCode) 
    {
        this.Url = "http://api.openweathermap.org/data/2.5/forecast?q="+City+","+CountryCode;
        try{
            String JsonData = IOUtils.toString(new URL(Url));
            this.WeatherSet = (JSONObject) JSONValue.parseWithException(JsonData);
        }
        catch (IOException | ParseException p){
            System.out.println("Failed to Load data");
            this.WeatherSet = (JSONObject) JSONValue.parse("{\"coord\":{\"lon\":0,\"lat\":0},\"sys\":{\"type\":3,\"id\":-1,\"message\":0,\"country\":\"None\",\"sunrise\":0,\"sunset\":0},\"weather\":[{\"id\":0,\"main\":\"NA\",\"description\":\"NA\",\"icon\":\"NULL\"}],\"base\":\"cmc stations\",\"main\":{\"temp\":0,\"humidity\":0,\"pressure\":0,\"temp_min\":0,\"temp_max\":0},\"wind\":{\"speed\":0,\"deg\":0},\"rain\":{\"3h\":0},\"clouds\":{\"all\":0},\"dt\":0,\"id\":0,\"name\":\"InvalidInput\",\"cod\":200}");
        }
    }
    
    
    
    
    /**
     * reloads the data from openweathermaps into the JSON string
     */
    void Update()
    {
        try{
            String JsonData = IOUtils.toString(new URL(Url));
            this.WeatherSet = (JSONObject) JSONValue.parseWithException(JsonData);
        }
        catch (IOException | ParseException p)
        {
            
        }
        
    }
    
    /**
     * gets the date in seconds of the current forecast from the JSON data
     * @return date in seconds since epoch
     */
    String GetTime()
    {
        long time = (long) this.Weather.get("dt");
        return FormatDate(time);
    }
    
    /**
     * formats the date to show what hour the forecast is for instead fo time in seconds
     * @param seconds		the date we want formatted in seconds since epoch
     * @return 		formatted string of the date fo the forecast
     */
    public String FormatDate(long seconds)
    {       
        String date = new java.text.SimpleDateFormat("ha EEE MMM d").format(new java.util.Date(seconds*1000));
        return date;
    }
    
    /**
     *  This function changes which hourly data is stored in the JSONObject Weather. only data every 3 hours
     *is available so the time given will be (Hour*3) hours in the future. This is also used by the class
     *DailyForecast and the only difference is that it switches the data stores by days instead of in 3 hour
     *increments
     */
    public void ChangeDataSet(int HourOrDay)
    {
        JSONArray temp =  (JSONArray)this.WeatherSet.get("list");
        Weather= (JSONObject) temp.get(HourOrDay-1);
    }
    
    
}
