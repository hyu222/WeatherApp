package ca.uwo.csd.cs2212.team3.WeatherApp;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.URL;


/**
 *  This class is made to hold and give the information for mars forecast. It uses the inherited methods from
 * WeatherJSON as those are the same. The only difference in the Mars information is that it comes from a different
 * parser.
 */
public class MarsForecast {
    
	String Url;
    JSONObject report;
    JSONObject mars;
    
    /**
     * Constructor to build an abject to hold the weather data for mar's forecast
     */
    MarsForecast()
    {
        this.Url = "http://marsweather.ingenology.com/v1/latest/?format=json";
        try{
			String JsonData = IOUtils.toString(new URL(Url));
			this.mars = (JSONObject) JSONValue.parseWithException(JsonData);
		}
		catch (IOException | ParseException p){
			System.out.println("Failed to Load mars data");
			}
        
        report = (JSONObject) mars.get("report");
    }
    /**
     * Retrieves new json data to update the information
     */
    void Update()
    {
        try{
        	String JsonData = IOUtils.toString(new URL(Url));
            this.mars = (JSONObject) JSONValue.parseWithException(JsonData);
        }
        catch (IOException | ParseException p){
            System.out.println("Failed to Load data");
        }
        
        report = (JSONObject) mars.get("report");
    }
    
    /**
     * gets the min and max temps and takes average 
     * @return average temp on mars in C
     */
    double GetTempC()
    {
        double temp1 = (double) report.get("min_temp");
        double temp2 = (double) report.get("max_temp");
        return RoundDouble((temp1+temp2)/2);
    }
    /**
     * gets the min and max temps and takes average 
     * @return average temp on mars in F
     */
    double GetTempF(){
    	double temp1 = (double) report.get("min_temp_fahrenheit");
        double temp2 = (double) report.get("max_temp_fahrenheit");
        return RoundDouble((temp1+temp2)/2);
    }
    
    /**
     * gets the max temp in C from the json data
     * @return mars max temp in C
     */
    double GetMaxTempC()
    {
        double temp2 = (double) report.get("max_temp");
        return RoundDouble(temp2);
    }
    
    /**
     * get the min temperature from the JSON info return it
     *
     * @return	the days min temperature in celsius
     *
     */
    double GetMinTempC()
    {
        double temp2 = (double) report.get("min_temp");
        return RoundDouble(temp2);
    }
    
    /**
     * get the max temperature from the JSON info and convert that to f 
     *
     * @return	the days max temperature in f
     *
     */
    double GetMaxTempF()
    {
    	double temp1 = (double) report.get("max_temp_fahrenheit");
        return RoundDouble(temp1);
    }
    /**
     * get the min temperature from the JSON info and convert that to f 
     *
     * @return	the days min temperature in f
     *
     */
    double GetMinTempF()
    {
    	double temp1 = (double) report.get("min_temp_fahrenheit");
        return RoundDouble(temp1);
    }
    
    
    String GetWindSpeed()
    {
        if(report.get("wind_speed") == null)
            return "--";
        else
            return (report.get("wind_speed").toString());
    }
    
    /**
     * retreive the wind direction from the JSON and returns it
     * 
     * @return	the string to show the wind direction 
     *
     */
    String GetWindDirection()
    {
        return (report.get("wind_direction").toString());
    }
    
    /**
     * gets the air pressure from the JSONObject and returns it
     *
     * @return	current air pressure in mb
     *
     */
    String GetAirPressure()
    {
        return report.get("pressure").toString();
    }
    
    /**
     * gets the humidity from the JSONObject and returns it
     *
     * @return	current humidity 
     *
     */
    String GetHumidity()
    {
        if(report.get("abs_humidity") == null)
            return "--";
        else
            return (report.get("abs_humidity").toString());
    }
    
    /**
     * accesses the JSON data and finds the sky condition(sunny,cloudy etc)
     * @return mars current sky condition
     */
    String GetSkyCondition()
    {
        return (report.get("atmo_opacity").toString());
    }
    
    /**
     * Rounds numbers to the nearest half
     *
     * @param d		The number that we wish to round
     * @return		the number d rounded to the nearest half
     *
     */
    double RoundDouble(double d)
    {	double r = 0.5;
        return Math.round(d / r) * r;
    }

    
    
}

