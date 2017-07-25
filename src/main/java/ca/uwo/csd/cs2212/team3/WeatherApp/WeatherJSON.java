package ca.uwo.csd.cs2212.team3.WeatherApp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.*;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.net.URL;

/**
 * This class is used to get and store the information from open weather maps and to       
 * implement functions to convert and return data values in the format we want, such as        
 * converting the temperature in kelvin to celsius.   
 *
 * @author	Liam Jowahir-Sindrey
 * @version	1.5
 *
 */

public class WeatherJSON {
    
    /**
     * Holds the city location
     */
    String City;
    /**
     * Holds the country location
     */
    String CountryCode;
    
    /**
     * Holds the JSON data for the current location
     */
    JSONObject Weather;
    /**
     * keeps track of the url used to get information for current location from openweathermaps
     */
    String Url;
    /**
     * determines how many decimal places we want displayed to the user
     */
    final int SigDecimals=1;
    
    
    /**
     * Constructs object by loading appropriate weather data from openweathermap
     *<p>
     * This constructor is made with inputs for the city and country code. These allow us to   
     * create the proper url for receiving the JSON data. Once we have the url we convert the 		
     * information at that url into a string and then parse the string turning it into a JSON      
     * Object which is stored in the class. As a backup in case the url returns nothing or returns 
     * information that is not JSON we have a catch that puts default values into the JSON object
     *<p>
     *@param City		The name of the city we want weather data for
     *@param CountryCode	The code for the country the city is in
     *
     */
    WeatherJSON(String City,String CountryCode) 
    {
        this.City=City;
        this.CountryCode=CountryCode;
        this.Url = "http://api.openweathermap.org/data/2.5/weather?q="+City+","+CountryCode;
        try{
            String JsonData = IOUtils.toString(new URL(Url));
            this.Weather = (JSONObject) JSONValue.parseWithException(JsonData);
        }
        catch (IOException | ParseException p){
            System.out.println("Failed to Load data");
            this.Weather = (JSONObject) JSONValue.parse("{\"coord\":{\"lon\":0,\"lat\":0},\"sys\":{\"type\":3,\"id\":-1,\"message\":0,\"country\":\"None\",\"sunrise\":0,\"sunset\":0},\"weather\":[{\"id\":0,\"main\":\"NA\",\"description\":\"NA\",\"icon\":\"NULL\"}],\"base\":\"cmc stations\",\"main\":{\"temp\":0,\"humidity\":0,\"pressure\":0,\"temp_min\":0,\"temp_max\":0},\"wind\":{\"speed\":0,\"deg\":0},\"rain\":{\"3h\":0},\"clouds\":{\"all\":0},\"dt\":0,\"id\":0,\"name\":\"InvalidInput\",\"cod\":200}");
            System.err.println("ERROR: Internet connection or OpenWeatherMaps is down.");
            System.exit(1);
        }
    }
    
    /**
     * Constructs blank object
     *<p>
     * This constructor is blank and will simply create the object without loading any data
     *<p>
     *
     */
    WeatherJSON() 
    {
        
    }
    
    /**
     * Updates the information stored in the class with new information from openweathermaps.org
     */
    void Update()
    {
        try{
            String JsonData = IOUtils.toString(new URL(Url));
            this.Weather = (JSONObject) JSONValue.parseWithException(JsonData);
        }
        catch (IOException | ParseException p)
        {
            System.err.println("ERROR: Failed to update data");
        }
        
    }
    
    /**
     * Rounds numbers to certain decimal places specified by SigDecimals
     *
     * @param d		The number that we wish to round
     * @return		the number d with only (SigDecimals) decimal places
     *
     */
    double RoundDouble(double d)
    {	double r = 0.5;
        return Math.round(d / r) * r;
        //	return (Math.round(d*Math.pow(10, SigDecimals))/Math.pow(10, SigDecimals));
    }
    
    /**
     * get the kelvin temperature from the JSON info and convert that to celsius then round
     *
     * @return	the current temperature in celsius
     *
     */
    double GetTempC()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("main");
        String temp2 = temp.get("temp").toString();
        return (RoundDouble(Double.parseDouble(temp2)-273.15));
        
    }
    
    /**
     * get the kelvin temperature from the JSON info and convert that to fehrenheit then round
     *
     * @return	the current temperature in f
     *
     */
    double GetTempF()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("main");
        String temp2 = temp.get("temp").toString();
        return (RoundDouble(9/5.0*(Double.parseDouble(temp2)-273.15)+32));
    }
    
    /**
     * get the kelvin max temperature from the JSON info and convert that to celsius then round
     *
     * @return	the days max temperature in celsius
     *
     */
    double GetMaxTempC()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("main");
        String temp2 = temp.get("temp_max").toString();
        return (RoundDouble(Double.parseDouble(temp2)-273.15));
    }
    
    /**
     * get the kelvin min temperature from the JSON info and convert that to celsius then round
     *
     * @return	the days min temperature in celsius
     *
     */
    double GetMinTempC()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("main");
        String temp2 = temp.get("temp_min").toString();
        return (RoundDouble(Double.parseDouble(temp2)-273.15));
    }
    
    /**
     * get the kelvin max temperature from the JSON info and convert that to f then round
     *
     * @return	the days max temperature in f
     *
     */
    double GetMaxTempF()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("main");
        String temp2 = temp.get("temp_max").toString();
        return (RoundDouble(9/5.0*(Double.parseDouble(temp2)-273.15)+32));
    }
    /**
     * get the kelvin min temperature from the JSON info and convert that to f then round
     *
     * @return	the days min temperature in f
     *
     */
    double GetMinTempF()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("main");
        String temp2 = temp.get("temp_min").toString();
        return (RoundDouble(9/5.0*(Double.parseDouble(temp2)-273.15)+32));
    }
    
    
    
    /**
     * gets the wind speed from the JSONObject and returns it
     *
     * @return	current wind speed rounded
     *
     */
    double GetWindSpeed()
    {
        JSONObject temp = (JSONObject) this.Weather.get("wind");
        String temp2 = temp.get("speed").toString();
        return (RoundDouble(Double.parseDouble(temp2)));
    }
    
    /**
     * gets the wind direction from the JSONObject and returns it
     * <p>
     * Converts the current angle of wind direction into a compass direction
     * then returns a string corresponding to this
     * <p>
     *
     * @return	current wind direction
     *
     */
    String GetWindDirection()
    {
        JSONObject temp = (JSONObject) this.Weather.get("wind");
        String text = temp.get("deg").toString();
        double dgree =  Double.parseDouble(text);
        String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        return directions[ (int)Math.round(( ((double)dgree % 360) / 45)) % 8 ];
        
    }
    
    /**
     * gets the air pressure from the JSONObject and returns it
     *
     * @return	current air pressure in mb
     *
     */
    String GetAirPressure()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("main");
        return temp.get("pressure").toString();
    }
    
    /**
     * gets the humidity from the JSONObject and returns it
     *
     * @return	current humidity rounded
     *
     */
    double GetHumidity()
    {
        JSONObject temp =  (JSONObject) this.Weather.get("main");
        String temp2 = temp.get("humidity").toString();
        return (RoundDouble(Double.parseDouble(temp2)));
    }
    
    /**
     * tells us what the sky is like in the current location
     *
     * @return	a string describing the sky condition
     *
     */
    String GetSkyCondition()
    {
        JSONArray temp =  (JSONArray)this.Weather.get("weather");
        JSONObject sky = (JSONObject) temp.get(0);
        return (sky.get("description").toString());
    }
    
    /**
     * This function gets a code to represent the condition of the sky
     *
     * @return	a code which translates to a certain weather pattern
     *
     */
    long GetSkyCode()
    {
        JSONArray temp =  (JSONArray)this.Weather.get("weather");
        JSONObject sky = (JSONObject) temp.get(0);
        return ((long)sky.get("id"));
    }
    
    
    
    
    /**
     * Gets the sunrise time for the current day
     * <p>
     * take the time in seconds since epoch and converts it to time in the current day
     * <p>
     *
     * @return	string representing time of day sunrise happens
     *
     */
    String GetSunrise()
    {
        JSONObject sys =  (JSONObject) this.Weather.get("sys");
        long time = (long)sys.get("sunrise");
        return formatHHMM(time);
    }
    String GetSunriseHH()
    {
        JSONObject sys =  (JSONObject) this.Weather.get("sys");
        long time = (long)sys.get("sunrise");
        return formatHH(time);
    }
    
    
    /**
     * Gets the sunset time for the current day
     * <p>
     * take the time in seconds since epoch and converts it to time in the current day
     * <p>
     *
     * @return	string representing time of day sunset happens
     *
     */
    String GetSunset()
    {
        JSONObject sys =  (JSONObject) this.Weather.get("sys");
        long time = (long)sys.get("sunset");
        return formatHHMM(time);
    }
    String GetSunsetHH()
    {
        JSONObject sys =  (JSONObject) this.Weather.get("sys");
        long time = (long)sys.get("sunset");
        return formatHH(time);
    }
    
    /**
     * takes a time in seconds and converts it to am,pm time
     * 
     * @param seconds	the amount of seconds to convert into am,pm time
     * @return	string representing time in clock time instead of seconds
     *
     */
    public String formatHHMM(long seconds){
        
        String date = new java.text.SimpleDateFormat("hh:mm a").format(new java.util.Date(seconds*1000));
        return date;
    }
    
    public String formatHH(long seconds){
        
        String date = new java.text.SimpleDateFormat("HH:mm a").format(new java.util.Date(seconds*1000));
        return date;
    }
    
    String getCity(){
        return (Weather.get("name").toString());
    }
    
    
    
}
