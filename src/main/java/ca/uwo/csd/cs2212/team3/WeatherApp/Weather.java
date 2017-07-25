package ca.uwo.csd.cs2212.team3.WeatherApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class is used for displaying a GUI window using SWING. This class extends JFrame
 * and implements ActionListener meaning that our GUI can do actions when clicking buttons
 * and so on.
 *
 * @author	Khaled Al-Yemni
 * @version	3.0
 *
 */
public class Weather extends JFrame implements ActionListener, FocusListener
{
    /**
     * Holds the JSON object for the current location
     */
    static WeatherJSON Location = new WeatherJSON("London","ca"); //default location (i.e. no user.prefs found)
    /**
     * Holds the JSON object for the hourly forecast
     */
    static HourlyForecast HF = new HourlyForecast("London", "ca");
    /**
     * Holds the JSON object for the daily forecast
     */
    static DailyForecast DF = new DailyForecast("London", "ca");
    /**
     * Holds the JSON object for mars
     */
    static MarsForecast Mars = new MarsForecast();
    /**
     * This is a flag to check if the data is loaded from Openweathermaps or not
     */
    static boolean Loaded = true;
    /**
     * This is a flag meaning we are using C of F metrics
     */
    static boolean C = true;
    /**
     * This Color type holds the background color when time is day
     */
    static Color day = new Color(108, 166, 205);
    /**
     * This Color type holds the background color when time is night
     */
    static Color night = new Color(119, 136, 153);
    /**
     * this number holds the number of forecast boxes
     */
    final private static int N = 16;
    /**
     * This array of panels are the panels for the forecast boxes
     */
    static JPanel forecastBox [] = new JPanel[N];
    /**
     * array of labels to hold a value to be placed in the north of the forecast box
     */
    static JLabel box_north [] = new JLabel[N];
    /**
     * array of labels to hold a value to be placed in the center of the forecast box
     */
    static JLabel box_center [] = new JLabel[N];
    /**
     * array of labels to hold a value to be placed in the south of the forecast box
     */
    static JLabel box_south [] = new JLabel[N];
    /**
     * array of labels to hold an icon to be placed in the center of the forecast box
     */
    static ImageIcon box_icon [] = new ImageIcon[N];
    
    //main panels ******************************************
    /**
     * This is the main panel that will hold all other panels and SWING objects
     */
    JPanel mainPanel = new JPanel();
    /**
     * This is the left panel that will hold other panels in the left (Current location)
     */
    static JPanel left = new JPanel(); 
    /**
     * This is the right panel that will hold other panels in the right (Forecast boxes)
     */
    JPanel right = new JPanel(); 
    
    //South Panel ******************************************
    /**
     * South panel that will hold 4 buttons 2 text fields and 1 label 
     */
    static JPanel south = new JPanel();
    JButton c = new JButton("C");
    JButton f = new JButton("F");
    JTextField city = new JTextField("City", 12);
    JTextField country = new JTextField("Country Code", 20);
    JButton changeLocation = new JButton("Change");
    static Calendar cal = Calendar.getInstance(); //get the current date and time
    static SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a"); //the format needed
    static String currentTime = sdf.format(cal.getTime());
    static JLabel lastUpdated = new JLabel(currentTime, JLabel.RIGHT);
    ImageIcon refresh = createImageIcon("Refresh.png");
    JButton update = new JButton(refresh);
    
    //left north ******************************************
    /**
     * This panel will be placed in the left north
     * it will contain 3 labels 
     */
    static JPanel left_north = new JPanel(); 
    static JLabel currentLocation = new JLabel("London", JLabel.CENTER);
    static JLabel skyCondition = new JLabel("", JLabel.CENTER);
    static ImageIcon image = createImageIcon("na.png");
    static JLabel icon = new JLabel(image);
    
    //left Center ******************************************
    /**
     * This is the left center panel that will hold 18 labels to display the weather data
     */
    static JPanel left_center = new JPanel();
    JLabel temperature = new JLabel("Temperature",JLabel.CENTER);
    static JLabel temperature2 = new JLabel("--",JLabel.CENTER);
    JLabel minTemp = new JLabel("Min",JLabel.CENTER);
    static JLabel minTemp2 = new JLabel("--",JLabel.CENTER);    
    JLabel maxTemp = new JLabel("Max",JLabel.CENTER);
    static JLabel maxTemp2 = new JLabel("--",JLabel.CENTER);    
    JLabel sunrise = new JLabel("Sun rise",JLabel.CENTER);
    static JLabel sunrise2 = new JLabel("--",JLabel.CENTER);
    JLabel sunset = new JLabel("Sun set",JLabel.CENTER);
    static JLabel sunset2 = new JLabel("--",JLabel.CENTER);
    JLabel windSpeed = new JLabel("Wind Speed",JLabel.CENTER);
    static JLabel windSpeed2 = new JLabel("--",JLabel.CENTER);
    JLabel windDirection = new JLabel("Wind Direction",JLabel.CENTER);
    static JLabel windDirection2 = new JLabel("--",JLabel.CENTER);
    JLabel humidity = new JLabel("Humidity",JLabel.CENTER);
    static JLabel humidity2 = new JLabel("--",JLabel.CENTER);
    JLabel airPressure = new JLabel("Air Pressure",JLabel.CENTER);
    static JLabel airPressure2 = new JLabel("--",JLabel.CENTER);
    
    //Right North ******************************************
    /**
     * this is the right north panel that will hold the label "Hourly" and the hourly forecast boxes 
     */
    static JPanel right_north = new JPanel();
    JLabel Hourly = new JLabel("Hourly");
    
    //Right South ******************************************
    /**
     * this panel is the right south and it will hold the label daily and the daily forecast boxes
     */
    static JPanel right_south = new JPanel();
    JLabel Daily = new JLabel("Daily");
    
    //Right North Center "Hourly" ******************************************
    /**
     * this is the panel that will contain all the forecast boxes "hourly"
     */
    JPanel right_north_center = new JPanel();
    
    //Right South Center "Weekly" ******************************************
    /**
     * this is the panel that will contain all the forecast boxes "daily" 
     */
    JPanel right_south_center = new JPanel();
    
    //Empty Labels for making a margin ******************************************
    /**
     * the following are empty labels that will be placed for alignment purposes 
     */
    JLabel empty = new JLabel("                    ");
    JLabel empty2 = new JLabel("                    ");
    JLabel empty3 = new JLabel("                    ");
    JLabel empty4 = new JLabel("                    ");
    
    /**
     * this is a user preferences variable 
     */
    static UserPreferences prefs;
    
    /**
     * This is the main method that will call the Weather class constructor
     * @param args n/a
     */
    public static void main (String args[])
    {
        for(int i = 0 ; i < N ; i++)
        {
            box_north[i]= new JLabel("day/hour", JLabel.CENTER);
            box_south[i]= new JLabel("forecast", JLabel.CENTER);
            box_icon[i]= createImageIcon("na.png");
            box_center[i]= new JLabel(box_icon[i], JLabel.CENTER);
            
        }
        new Weather();   
    }
    
    /**
     * Constructs the frame and adds all SWING components to it
     *
     * This constructor makes the GUI and adds all SWING components to their
     * correct position and finally sets the window to visible
     *
     *
     */
    public Weather()
    {
        super("Weather Application team 3");
        setSize(1100, 500);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setGUITextColor(Color.WHITE);
        
        //load the user preferences
        try
        {
            prefs = UserPreferences.load_prefs();
        }
        catch(ClassNotFoundException e)
        {
            Loaded = false;
            e.printStackTrace();
        }
        
        //set the location from the user preferences
        if(prefs.getCity().equalsIgnoreCase("mars"))
        {
            Mars = new MarsForecast();
        }
        else
        {
            try{
                Location = new WeatherJSON(prefs.getCity(),prefs.getCountry());
                HF = new HourlyForecast(prefs.getCity(),prefs.getCountry());
                DF = new DailyForecast(prefs.getCity(),prefs.getCountry());
                currentLocation.setText(prefs.getCity());
            }catch (Exception e){
                System.err.println("Could not load data...");
                Loaded = false;
                System.exit(EXIT_ON_CLOSE);
            }
        }
        
        //set the temperature unit from user preferences
        if (prefs.isCelsius()) 
        {
            this.toC();
        }
        else
        {
            this.toF();
        }
        
        //main panel ******************************************
        mainPanel.setLayout(new BorderLayout());
        left.setLayout(new BorderLayout());
        right.setLayout(new GridLayout(2, 1));
        
        //South Panel ******************************************
        south.setLayout(new FlowLayout(FlowLayout.LEADING));
        c.addActionListener(this);
        f.addActionListener(this);
        update.addActionListener(this);
        c.setBorderPainted(false);
        c.setOpaque(false);
        c.setContentAreaFilled(false);
        f.setBorderPainted(false);
        f.setOpaque(false);
        f.setContentAreaFilled(false);
        changeLocation.addActionListener(this);
        update.setBorderPainted(false);
        south.add(lastUpdated);
        south.add(update);
        city.addFocusListener(this);
        country.addFocusListener(this);
        south.add(city);
        south.add(country);
        south.add(changeLocation);
        south.add(c); 
        south.add(f);
        south.add(lastUpdated, BorderLayout.NORTH);
        south.add(update, BorderLayout.NORTH);
        
        //left north ******************************************
        left_north.setLayout(new BorderLayout());
        currentLocation.setFont(new Font("", Font.PLAIN, 50));
        left_north.add(currentLocation,BorderLayout.NORTH );
        left_north.add(icon, BorderLayout.CENTER);
        left_north.add(skyCondition, BorderLayout.SOUTH);
        left_north.add(empty3, BorderLayout.WEST);
        left_north.add(empty4, BorderLayout.EAST);
        
        //left Center ******************************************
        left_center.setLayout(new GridLayout(9,2));
        left_center.add(temperature);
        left_center.add(temperature2);
        left_center.add(minTemp);
        left_center.add(minTemp2);
        left_center.add(maxTemp);
        left_center.add(maxTemp2);
        left_center.add(sunrise);
        left_center.add(sunrise2);
        left_center.add(sunset);
        left_center.add(sunset2);
        left_center.add(windSpeed);
        left_center.add(windSpeed2);
        left_center.add(windDirection);
        left_center.add(windDirection2);
        left_center.add(humidity);
        left_center.add(humidity2);
        left_center.add(airPressure);
        left_center.add(airPressure2);
        
        //Right North ******************************************
        right_north.setLayout(new BorderLayout());
        Hourly.setFont(new Font("", Font.ITALIC, 20));
        right_north.add(Hourly, BorderLayout.NORTH);
        right_north.add(right_north_center, BorderLayout.CENTER);
        
        //Right South ******************************************
        right_south.setLayout(new BorderLayout());
        Daily.setFont(new Font("", Font.ITALIC, 20));
        right_south.add(Daily, BorderLayout.NORTH);
        right_south.add(right_south_center, BorderLayout.CENTER);
        
        //Right North Center "Hourly" ******************************************
        right_north_center.setLayout(new GridLayout(1, 4));
        
        //Right South Center "Weekly" ******************************************
        right_south_center.setLayout(new GridLayout(1, 4));
        
        //Creating Forecast boxes 
        for(int i = 0 ; i < N ; i++)
        {
            forecastBox[i]= new JPanel();
            forecastBox[i].setLayout(new BorderLayout());
            forecastBox[i].add(box_north[i], BorderLayout.NORTH);
            forecastBox[i].add(box_center[i], BorderLayout.CENTER);
            forecastBox[i].add(box_south[i], BorderLayout.SOUTH);
        }
        
        //Adding forecast boxes into right north center (Hourly)
        for(int i = 0 ; i < (N/2) ; i++)
        {
            right_north_center.add(forecastBox[i]);
        }
        
        //Adding forecast boxes into right south center (Daily)
        for(int i = (N/2) ; i < N-3 ; i++)
        {
            right_south_center.add(forecastBox[i]);
        }
        
        left.add(left_north, BorderLayout.NORTH);
        left.add(left_center, BorderLayout.CENTER);
        
        right.add(right_north, BorderLayout.NORTH);
        right.add(right_south, BorderLayout.SOUTH);
        
        mainPanel.add(right, BorderLayout.CENTER);
        mainPanel.add(left, BorderLayout.WEST);
        mainPanel.add(south, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        setVisible(true);
        
        //save the user preferences before closing
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() 
                                                        {
            @Override
            public void run() 
            {
                try
                {
                    prefs.save_prefs();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }));
    }
    
    
    /**
     * This method listens to which button is selected and acts accordingly
     *
     * @param e	takes an ActionEvent object that was sent by and action listener
     */
    public void actionPerformed(ActionEvent e) 
    {
        Object src = e.getSource();
        if(src.equals(c))
        {
            this.toC();
        }
        if(src.equals(f))
        {
            this.toF();
        }
        if(src.equals(update))
        {
            update();
        }
        if(src.equals(changeLocation))
        {
            this.changeLocation();
        }
    }
    
    /**
     * This method is to change the icon for the sky condition
     * @param skyCode that holds a sky code long number
     */
    public static void ChangeIcon(long skyCode)
    {
        image = createImageIcon(whichIcon(skyCode));
        icon.setIcon(image); //change the icon}
    }
    
    
    
    
    /**
     * This method is to update the current data stored in the JSON Weather object
     * and then update the data presented on the GUI
     */
    public static void update()
    {
        //Changes the time to current time
        cal = Calendar.getInstance();
        currentTime = sdf.format(cal.getTime());
        lastUpdated.setText(currentTime);
        
        if(prefs.getCity().equalsIgnoreCase("mars"))
        {
            UpdateMarsGUI();
        }
        else if (Loaded==false)
        {
            
        }
        else
        {
            //Update Location
            Location.Update();
            if(C)
            {
                temperature2.setText(String.valueOf(Location.GetTempC()));
                minTemp2.setText(String.valueOf(Location.GetMinTempC()));
                maxTemp2.setText(String.valueOf(Location.GetMaxTempC()));
            }
            else
            {
                temperature2.setText(String.valueOf(Location.GetTempF()));
                minTemp2.setText(String.valueOf(Location.GetMinTempF()));
                maxTemp2.setText(String.valueOf(Location.GetMaxTempF()));
            }
            
            sunrise2.setText(Location.GetSunrise());
            sunset2.setText(Location.GetSunset());
            windSpeed2.setText(String.valueOf(Location.GetWindSpeed())+" kph");
            windDirection2.setText(Location.GetWindDirection());
            humidity2.setText(String.valueOf(Location.GetHumidity())+"%");
            airPressure2.setText(String.valueOf(Location.GetAirPressure())+" hpa");
            skyCondition.setText(Location.GetSkyCondition());
            skyCondition.setFont(new Font("", Font.ITALIC, 10));
            
            //Adding forecast boxes into right north center (Hourly)
            for(int i = 0 ; i < (N/2) ; i++)
            {
                HF.ChangeDataSet(i+1); 	
                box_north[i].setText(String.valueOf("<html><html dir=\"ltr\">"+
                                                    "<head></head><body contenteditable=\"true\">"+
                                                    "<p style=\"text-align: center;\"><font face=\"Lucida Grande\" size=\"4\">"+HF.GetTime().substring(0, 4)+"</font></p>"+
                                                    "<p style=\"text-align: center;\"><font face=\"Lucida Grande\" size=\"3\"><i>"+HF.GetSkyCondition()+"</i></font></p></body></html><html>"));
                if(C)
                    box_south[i].setText(String.valueOf(HF.GetTempC()));
                else
                    box_south[i].setText(String.valueOf(HF.GetTempF()));
                box_icon[i]= createImageIcon("small/"+whichIcon(HF.GetSkyCode()));
                box_center[i].setIcon(box_icon[i]);
            }
            
            //Adding forecast boxes into right south center (Daily)
            int j = 1;
            for(int i = (N/2) ; i < (N-3) ; i++)
            {
                DF.ChangeDataSet(j);
                box_north[i].setText(String.valueOf("<html><html dir=\"ltr\">"+
                                                    "<head></head><body contenteditable=\"true\">"+
                                                    "<p style=\"text-align: center;\"><font face=\"Lucida Grande\" size=\"4\">"+DF.GetTime().substring(0, 4)+"</font></p>"+
                                                    "<p style=\"text-align: center;\"><font face=\"Lucida Grande\" size=\"3\"><i>"+DF.GetSkyCondition()+"</i></font></p></body></html><html>"));
                if(C)
                {
                    box_south[i].setText(String.valueOf(
                                                        "<html><html dir=\"ltr\"><head></head><body contenteditable=\"true\" size=\"2\">"
                                                        + "<p style=\"text-align: center;\"><font face=\"Lucida Grande\" size=\"2\">"
                                                        + "Min: "+DF.GetMinTempC()+"</font></p>"
                                                        + "<p style=\"text-align: center;\"><font face=\"Lucida Grande\" size=\"2\">"
                                                        + "Max: "+DF.GetMaxTempC()+"</font></p>"
                                                        + "<p style=\"text-align: center;\"><font face=\"Lucida Grande\" size=\"2\">"
                                                        + "Temp: "+DF.GetTempC()+"</font></p>"
                                                        + "</body></html>"));
                }
                else
                {
                    box_south[i].setText(String.valueOf(
                                                        "<html><html dir=\"ltr\"><head></head><body contenteditable=\"true\" size=\"2\">"
                                                        + "<p style=\"text-align: center;\"><font face=\"Lucida Grande\" size=\"2\">"
                                                        + "Min: "+DF.GetMinTempF()+"</font></p>"
                                                        + "<p style=\"text-align: center;\"><font face=\"Lucida Grande\" size=\"2\">"
                                                        + "Max: "+DF.GetMaxTempF()+"</font></p>"
                                                        + "<p style=\"text-align: center;\"><font face=\"Lucida Grande\" size=\"2\">"
                                                        + "Temp: "+DF.GetTempF()+"</font></p>"
                                                        + "</body></html>"));
                }
                box_icon[i]= createImageIcon(whichIcon(DF.GetSkyCode()));
                box_center[i].setIcon(box_icon[i]);
                j++;
            }
            ChangeIcon(Location.GetSkyCode());
            changeBackGround();
        }
    }
    
    /**
     * This method is to change the data to C 
     * It also changes the selected button to BOLD
     */
    public void toC ()
    {
        f.setFont(new Font("", Font.PLAIN, 10));
        c.setFont(new Font("", Font.BOLD, 16));
        C=true;
        update();
        prefs.setCelsius(true);
    }
    
    /**
     * This method is to change the data to F 
     * It also changes the selected button to BOLD
     */
    public void toF ()
    {
        c.setFont(new Font("", Font.PLAIN, 10));
        f.setFont(new Font("", Font.BOLD, 16));
        C=false;
        update();
        prefs.setCelsius(false);
    }
    
    
    /**
     * This method is to change the location in the JSON Weather object 
     * and updates the data on the GUI
     *
     */
    public void changeLocation ()
    {
        String oldCity = Location.City;
        String oldCountry = Location.CountryCode;
        String enteredCity = city.getText();
        String enteredCountry = country.getText();
        if(enteredCity.toLowerCase().equals("mars") || enteredCountry.toLowerCase().equals("mars"))
        {
            prefs.setCity("Mars");
            prefs.setCountry("Mars");
            update();
        }
        else
        {
            try
            {
                Location = new WeatherJSON(enteredCity,enteredCountry);
                HF = new HourlyForecast(enteredCity,enteredCountry);
                DF = new DailyForecast(enteredCity,enteredCountry);
                prefs.setCity(Location.getCity());
                prefs.setCountry(enteredCountry);
                update();
                enteredCity= enteredCity.substring(0,1).toUpperCase() + enteredCity.substring(1).toLowerCase();//make only first char capitalized
                currentLocation.setText(Location.getCity());
                Loaded=true;
                //currentLocation.setText(enteredCity);
            }
            catch(Exception e)
            {
                city.setText("ERROR: City not found");
                country.setText("Try two letter code e.g. Canada = ca");
                prefs.setCity(oldCity);
                prefs.setCountry(oldCountry);
                Loaded=false;
            }
        }
    }
    
    /**
     * This is the update method to update all data on the GUI from the MarsForecast object
     */
    private static void UpdateMarsGUI() 
    {
        currentLocation.setText("Mars");
        Mars.Update();
        if(C)
        {
            temperature2.setText(String.valueOf(Mars.GetTempC()));
            minTemp2.setText(String.valueOf(Mars.GetMinTempC()));
            maxTemp2.setText(String.valueOf(Mars.GetMaxTempC()));
        }
        else
        {
            temperature2.setText(String.valueOf(Mars.GetTempF()));
            minTemp2.setText(String.valueOf(Mars.GetMinTempF()));
            maxTemp2.setText(String.valueOf(Mars.GetMinTempF()));
        }
        sunrise2.setText("--");
        sunset2.setText("--");
        windSpeed2.setText(String.valueOf(Mars.GetWindSpeed())+" kph");
        windDirection2.setText(Mars.GetWindDirection());
        humidity2.setText(String.valueOf(Mars.GetHumidity())+"%");
        humidity2.setText("--");
        airPressure2.setText(String.valueOf(Mars.GetAirPressure())+" hpa");
        skyCondition.setText(Mars.GetSkyCondition());
        skyCondition.setFont(new Font("", Font.ITALIC, 10));
        String sky = Mars.GetSkyCondition();
        if(sky.toLowerCase().contains("sunny"))
        {
            image = createImageIcon("01d.png");
            icon.setIcon(image);
        }
        else if(sky.toLowerCase().contains("cloudy"))
        {
            image = createImageIcon("03d.png");
            icon.setIcon(image);
        }
        else if(sky.toLowerCase().contains("snowy"))
        {
            image = createImageIcon("13d.png");
            icon.setIcon(image);
        }
        else if(sky.toLowerCase().contains("rain"))
        {
            image = createImageIcon("09d.png");
            icon.setIcon(image);
        }
        else
        {
            image = createImageIcon("na.png");
            icon.setIcon(image);
        }
        left_north.setBackground(day);
        south.setBackground(day);
        left_center.setBackground(day);
        right_north.setBackground(day);
        right_south.setBackground(day);
        for(int i = 0 ; i < N ; i++)
        {
            box_north[i].setText("n/a");
            box_south[i].setText("n/a");
            box_icon[i]= createImageIcon("na.png");
            box_center[i].setIcon(box_icon[i]);
        }
    }
    
    
    /**
     * Returns a new ImageIcon object with the given image path
     * or it will throw and catch an exception if the path was invalid.
     *
     * @param path	The image path
     * @return		ImageIcon object contains an image that is found in the given path
     *
     */
    protected static ImageIcon createImageIcon(String path) {
        try
        {
            return new ImageIcon(Weather.class.getResource("/"+path));
        }
        catch (Exception e)
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    /**
     * This method will take a color and updates all text on the GUI to that new color
     * @param Color 	x 
     */
    private void setGUITextColor(Color x)
    {
        temperature.setForeground(x);
        temperature2.setForeground(x);
        minTemp.setForeground(x);
        minTemp2.setForeground(x);
        maxTemp.setForeground(x);
        maxTemp2.setForeground(x);
        sunrise.setForeground(x);
        sunrise2.setForeground(x);
        sunset.setForeground(x);
        sunset2.setForeground(x);
        windSpeed.setForeground(x);
        windSpeed2.setForeground(x);
        windDirection.setForeground(x);
        windDirection2.setForeground(x);
        humidity.setForeground(x);
        humidity2.setForeground(x);
        airPressure.setForeground(x);
        airPressure2.setForeground(x);
        skyCondition.setForeground(x);
        lastUpdated.setForeground(x);
        currentLocation.setForeground(x);
        c.setForeground(x);
        f.setForeground(x);
        Daily.setForeground(x);
        Hourly.setForeground(x);
    }
    
    /**
     * This method changes to back ground of the the GUI after checking if the now is day or night
     */
    private static void changeBackGround()
    {
        //To set the color of the background according to the current time and considering sunset and sun rise time
        if (isDay()) 
        {
            left_north.setBackground(day);
            south.setBackground(day);
            left_center.setBackground(day);
            right_north.setBackground(day);
            right_south.setBackground(day);
        }
        else
        {
            left_north.setBackground(night);
            south.setBackground(night);
            left_center.setBackground(night);
            right_north.setBackground(night);
            right_south.setBackground(night);
        }	
    }
    
    /**
     * This method checks if now is day or night and then it returns true if its day, false if not
     * @return  true if now is day, false otherwise
     */
    private static boolean isDay()
    {
        try 
        {
            SimpleDateFormat HH = new SimpleDateFormat("HH:mm a");
            int SUNSET = Integer.parseInt(Location.GetSunsetHH().substring(0, 2));
            int SUNRISE = Integer.parseInt(Location.GetSunriseHH().substring(0, 2));
            int now = Integer.parseInt(HH.format(cal.getTime()).toString().substring(0, 2));
            if (now < SUNSET && now > SUNRISE) {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e) 
        {
            return false;
        }		
    }
    
    /**
     * This method gets a sky code and compare it with different icons and it will return the name of the icon
     * that matches the sky code
     * @param skyCode  that holds a sky code number in a long number format
     * @return	icon name 
     */
    public static String whichIcon(long skyCode)
    {
        if (skyCode==800)
        {
            if(isDay())
                return ("01d.png");
            else
                return ("01n.png");
        }
        else if(skyCode==801)
        {
            if(isDay())
                return ("02d.png");
            else
                return ("02n.png");
        }
        else if(skyCode==802)
        {
            if(isDay())
                return ("03d.png");
            else
                return ("03n.png");
        }
        else if(skyCode==804||skyCode==803)
        {
            if(isDay())
                return ("04d.png");
            else
                return ("04n.png");
        }
        else if (skyCode>700&&skyCode<782)
        {
            if(isDay())
                return ("50d.png");
            else
                return ("50n.png");
        }
        else if (skyCode>599&&skyCode<623)
        {
            if(isDay())
                return ("13d.png");
            else
                return ("13n.png");
        }
        else if (skyCode>510&&skyCode<532)
        {
            if(isDay())
                return ("09d.png");
            else
                return ("09n.png");
        }
        else if (skyCode>499&&skyCode<505)
        {
            if(isDay())
                return ("10d.png");
            else
                return ("10n.png");
        }
        else if (skyCode>299&&skyCode<322)
        {
            if(isDay())
                return ("09d.png");
            else
                return ("09n.png");
        }
        else if (skyCode>199&&skyCode<233)
        {
            if(isDay())
                return("11d.png");
            else
                return ("11n.png");
        }
        return "na.png";
    }
    
    /**
     * This method listens if the text field is on focus if so it will clear it out
     * @param e takes a focus event that is sent by the focus listener
     */
    public void focusGained(FocusEvent e) 
    {
        if(e.getSource().equals(city))
            city.setText("");
        else
            country.setText("");
    }
    
    /**
     * This method listens if the text field is out focus if so it will do nothing
     * @param e takes a focus event that is sent by the focus listener
     */
    public void focusLost(FocusEvent e) 
    {
        //do nothing
    }
}
