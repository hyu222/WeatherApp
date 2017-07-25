package ca.uwo.csd.cs2212.team3.WeatherApp;

import java.io.*;

/**
 * This class allows the applet to remember the user's preferences (such as displaying
 * temperature in Celsius) after the program has been terminated, which can then be
 * restored once the applet is reopened.
 *
 * @author      Noah Murad
 * @version     1.0
 *
 */

public class UserPreferences implements java.io.Serializable{

    //private static final long serialVersionUID = 6348707169635115840L;
    /**
     * the file in which we will save this serializable object.
     * */
    private static final transient String FILENAME= "user.prefs";

    ////*the remaining fields are preferences which will be saved*////
    /**
     * sets whether the user is using celsius or not (fahrenheit).
     */
    boolean isCelsius;
    /**
     * saves the city in which the user was last viewing before closing the app.
     */
    String city;
    /**
     * saves the country in which the user was last viewing before closing the app.
     */
    String country;

    /**
     * sets the program defaults.
     */
    public UserPreferences(){
        this.isCelsius=true;
        this.city="London";
        this.country="ca";
    }


    /**returns the value of isCelsius
     *
     * @return  true when the user's preferences are set to celsius. False otherwise
     */
    public boolean isCelsius() {
        return isCelsius;
    }

    /**sets the value of isCelsius
     *
     * @param isCelsius sets the value of the field isCelsius
     */
    public void setCelsius(boolean isCelsius) {
        this.isCelsius = isCelsius;
    }

    /**gets the city of preference
     *
     * @return the city of preference
     */
    public String getCity() {
        return city;
    }

    /**sets the value of the preferred city
     *
     * @param city the city to set as the preferred city
     */
    public void setCity(String city) {
        city= city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase(); //only first letter capitalized
        this.city = city;
    }

    /**gets the country of preference
     *
     * @return the country of preference
     */
    public String getCountry() {
        return country;
    }

    /**sets the value of the preferred country
     *
     * @param country the city to set as the preferred city
     */
    public void setCountry(String country) {
        country= country.substring(0,1).toUpperCase() + country.substring(1).toLowerCase();//only first letter capitalized
        this.country = country;
    }

    /**
     * The state of the UserPreferences object which calls this method gets saved to
     * a file.
     *
     * @throws java.io.IOException if the file FILENAME cannot be written to
     */
    public void save_prefs() throws IOException{
            ObjectOutputStream obj_out= new ObjectOutputStream(new FileOutputStream(FILENAME));
            obj_out.writeObject(this);
            obj_out.close();
    }

    /**
     * loads the preferences stored in file user.prefs as a UserPreferences object
     *
     * @return a UserPreferences object with the same field values as the last UserPreferences object
     * to use the method save_prefs()
     */

    public static UserPreferences load_prefs() throws ClassNotFoundException {
        Object up=null;
        try{
            ObjectInputStream obj_in= new ObjectInputStream(new FileInputStream(FILENAME));
            up= obj_in.readObject();
            obj_in.close();
        }catch(IOException e){
            return new UserPreferences();
        }

        return (UserPreferences)up;
    }

}
