package edu.ucalgary.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The facilitator of all of the GUI logic. 
 * @author Adam Yuen <a href="mailto:adam.yuen@ucalgary.ca">adam.yuen@ucalgary.ca</a>
 * @version 1.1
 * @since 1.0
 */
public class ScreenDisplay extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel advertisementPanel;
    private JPanel weatherPanel;
    private JPanel newsPanel;
    private JPanel trainPanel;
    
    private Timer adTimer;
    private Timer newsTimer;
    private Timer trainTimer;
    private JLabel newsLabel;
    private String timeToDisplay;
    private JLabel adLabel;
    private ImageIcon resizedAdIcon;

    private List<String> newsTitles;
    private int currentTitleIndex = 0;
    
    private List<String> imageURLs;
    private int currentURLIndex = -1;
    private final int numberOfAds;
    
    private static ArrayList<String> trainInfo;
    
    /**
     * The constructor will initialize the GUI and call all associated functions for displaying info.
     * @param cityName denotes which city the weather and news will be fetched for.
     * @param startDate denotes the start time for the news to be fetched from. (format: yyyy-mm-dd)
     * @param endDate denotes the end time for the news to be fetched from. (format: yyyy-mm-d)
     * @param sortOrder use default of publishedAt. 
     * @param sqlPassword is sourced from the command line; contains the user's sql root password
     * @param trainNumber contains the train number for the current train.
     */
    public ScreenDisplay(String cityName, String startDate, String endDate, String sortOrder, String sqlPassword, String trainNumber) {
        setTitle("Transit Information System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        
        //Initialize all of the JPanels
        advertisementPanel = new JPanel();
        weatherPanel = new JPanel();
        trainPanel = new JPanel();
        newsPanel = new JPanel(new BorderLayout());
        
        //Set background colours to make it look more like the transit screen
        advertisementPanel.setBackground(Color.decode("#9abed6"));
        weatherPanel.setBackground(Color.decode("#01205c"));
        newsPanel.setBackground(Color.BLACK);
        trainPanel.setBackground(Color.decode("#ba4a4a"));

        // GridBagLayout - Main Layout of Screen
        setLayout(new GridBagLayout());
        GridBagConstraints gridLayoutSetup = new GridBagConstraints();
        gridLayoutSetup.fill = GridBagConstraints.BOTH;

        //Advertisement screen grid formatting and adding
        gridLayoutSetup.gridx = 0; //Column Index
        gridLayoutSetup.gridy = 0; //Row index
        gridLayoutSetup.weightx = 0.999; //relative size in x direction
        gridLayoutSetup.weighty = 0.9; //relative size in y direction
        gridLayoutSetup.gridwidth = 1; //number of columns the component to be added will occupy
        gridLayoutSetup.gridheight = 1; //number of rows it will occupy
        add(advertisementPanel, gridLayoutSetup); //add the advertisements to the layout now that it is formatted.

        //Weather screen grid formatting and adding
        gridLayoutSetup.gridx = 1;
        gridLayoutSetup.gridy = 0;
        gridLayoutSetup.weightx = 0.001; 
        gridLayoutSetup.weighty = 0.9; 
        gridLayoutSetup.gridwidth = 1;
        gridLayoutSetup.gridheight = 1;
        add(weatherPanel, gridLayoutSetup);

        //News screen grid formatting and adding
        gridLayoutSetup.gridx = 0;
        gridLayoutSetup.gridy = 1;
        gridLayoutSetup.weightx = 1; 
        gridLayoutSetup.weighty = 0.6; 
        gridLayoutSetup.gridwidth = 2;
        gridLayoutSetup.gridheight = 1;
        add(newsPanel, gridLayoutSetup);

        //Train screen grid formatting and adding
        gridLayoutSetup.gridx = 0; 
        gridLayoutSetup.gridy = 2;
        gridLayoutSetup.weightx = 1; 
        gridLayoutSetup.weighty = 0.2;
        gridLayoutSetup.gridwidth = 2;
        gridLayoutSetup.gridheight = 1;
        add(trainPanel, gridLayoutSetup);
        
        //Train Map logic - create a new instance of the map.
        SubwayScreenApp mapLogic = new SubwayScreenApp(Integer.parseInt(trainNumber));      
	    adLabel = new JLabel();
	    setImage("src\\edu\\ucalgary\\ensf380\\placeholderAd.jpg");
        
        // swapping maps and images - will not be able to use the boolean for this one once we integrate all images
        advertisementPanel.setPreferredSize(new Dimension(500, 500));
        advertisementPanel.add(adLabel);

        //Handle the weather displaying. 
        weatherPanel.setLayout(new BorderLayout());
        WeatherFetcher weatherData = new WeatherFetcher(cityName);
        
        //Use getters to obtain all of the data fetched by the API.
        String displayCondition = weatherData.getCondition();
        String displayTemp = weatherData.getTemperature();
        String displayHumidity = weatherData.getHumidity();
        String displayWindSpeed = weatherData.getWind();
        String displayRain = weatherData.getRain();
        setTime();
        
        //Create a text label to add the weather.
        JTextArea weatherInfo = new JTextArea(
                timeToDisplay + 
                "\nCurrently: " + displayCondition + ", " + displayTemp + 
                "\nHumidity: " + displayHumidity + 
                "\nWind Speed: " + displayWindSpeed +
                "\nRain today: " + displayRain);
        insertText(weatherInfo, weatherPanel, 20, 1, 50, 20, 20);
        
        //Establish a link to the database, and retrieve the URLs to be used for displaying
        InsertImage insert = new InsertImage(sqlPassword);
        RetrieveImage relativeLinkDBRetriever = new RetrieveImage(sqlPassword);
        imageURLs = relativeLinkDBRetriever.getImagePaths();
        numberOfAds = imageURLs.size();

        //Updates the current train info every 5 seconds.
        trainTimer = new Timer(5000, new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		trainPanel.removeAll();
        		trainInfo = mapLogic.getTrainInfo();
                trainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
                
            	JLabel previousStation = new JLabel(trainInfo.get(0));
            	previousStation.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            	trainPanel.add(previousStation, BorderLayout.CENTER);
            	
            	JLabel nextStation1 = new JLabel(trainInfo.get(1));
            	nextStation1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            	trainPanel.add(nextStation1, BorderLayout.CENTER);
            	
            	JLabel nextStation2 = new JLabel(trainInfo.get(2));
            	nextStation2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            	trainPanel.add(nextStation2, BorderLayout.CENTER);
                
            	JLabel nextStation3 = new JLabel(trainInfo.get(3));
            	nextStation3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        	  	trainPanel.add(nextStation3, BorderLayout.CENTER);

            	JLabel nextStation4 = new JLabel(trainInfo.get(4));
            	nextStation4.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            	trainPanel.add(nextStation4, BorderLayout.CENTER);
        	}
        });
        trainTimer.start();
        
        //Logic for swapping the map and the ads.
        adTimer = new Timer(10000, new ActionListener() {
            boolean x = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (x) {
                	//Display the snapshot of the map at a given time.
                    String mapPath = mapLogic.getFrameImage().getPath();
                    ImageIcon mapIcon = new ImageIcon(mapPath); 
                    Image resizedMap = mapIcon.getImage().getScaledInstance(500, 275, Image.SCALE_SMOOTH);
                    Icon resizedMapIcon = new ImageIcon(resizedMap);
                    adLabel.setIcon(resizedMapIcon); //Display the map on the ad screen.
                    adTimer.setInitialDelay(5000); // display the map for 5 seconds
                } else {
                	System.out.println(currentURLIndex);
                	//Reset the advertisement index if it exceeds the size of the array.
                	if (currentURLIndex == (numberOfAds - 1)) {
                		currentURLIndex = 0;
                	}
                	else {
                		//Cycle through the paths.
                		currentURLIndex ++;
                	}
                	String currentURL = imageURLs.get(currentURLIndex);
                	setImage(currentURL);
                    adLabel.setIcon(resizedAdIcon);
                    adTimer.setInitialDelay(10000); // display the ad for 10 seconds
                }
                setTime();
                
                //Create a text label to add the weather.
                JTextArea weatherInfo = new JTextArea(
                        timeToDisplay + 
                        "\nCurrently: " + displayCondition + ", " + displayTemp + 
                        "\nHumidity: " + displayHumidity + 
                        "\nWind Speed: " + displayWindSpeed +
                        "\nRain today: " + displayRain);
                insertText(weatherInfo, weatherPanel, 20, 1, 50, 20, 20);
                x = !x;
            }
        });

        adTimer.start();
	  	
        //Create a new news instance and fetch the data. Use the getter to put it in a list.
        NewsFetcher newsApiClient = new NewsFetcher(cityName, startDate, endDate, sortOrder);

        // Get the list of news titles
        newsTitles = newsApiClient.getHeadlines();
        for (String title : newsTitles) {
            System.out.println(title); // Debug output to verify full headlines
        }
        
        //Run the news scrolling routine
        newsHeadlineScroller();
    }

    /**
     * The main function that will run the GUI. Invokes the GUI constructor, which runs everything else.
     * @param args will house the command line arguments of cityName, startDate, endDate, sortOrder, sqlPassword, and train_number
     */
    public static void main(String[] args) {
        if (args.length != 6) {
            System.out.println("Commandline arguments: <city_name> <start_date> <end_date> <sort_order>, <sql_password>, <train_number>");
            return;
        }
        String cityName = args[0];
        String startDate = args[1];
        String endDate = args[2];
        String sortOrder = args[3];
        String sqlPassword = args[4];
        String trainNumber = args[5];
        EventQueue.invokeLater(() -> {
            new ScreenDisplay(cityName, startDate, endDate, sortOrder, sqlPassword, trainNumber).setVisible(true);
        });
    } 

    /**
     * This method will place text at a given location on the screen.
     * @param current determines the JTextArea to be added
     * @param screen determines the panel the text should be added to
     * @param size determines the size of the text
     * @param top: width of top border
     * @param left: width of left border
     * @param bottom: width of bottom border
     * @param right width of right border
     */
    public void insertText(JTextArea current, JPanel screen, int size, int top, int left, int bottom, int right) {
        current.setFont(new Font("Ubuntu", Font.BOLD, size));
        current.setForeground(Color.WHITE);
        current.setBackground(Color.decode("#01266C"));
        current.setEditable(false);
        current.setOpaque(true);
        
        //Set margins to determine the position, and add it to the screen frame
        current.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        screen.add(current, BorderLayout.CENTER);
    }
    
    /**
     * Performs the scrolling text for the news headlines.
     */
    public void newsHeadlineScroller() {
        newsLabel = new JLabel(newsTitles.get(0));
        newsLabel.setFont(new Font("Ubuntu", Font.BOLD, 32));
        newsLabel.setForeground(Color.WHITE);
        newsLabel.setOpaque(true); 
        newsLabel.setBackground(Color.BLACK); 
        newsPanel.setLayout(null); 
        newsLabel.setSize(newsLabel.getPreferredSize()); 
        newsPanel.add(newsLabel);

        newsTimer = new Timer(30, new ActionListener() {
            private int x = newsPanel.getWidth();

            @Override
            public void actionPerformed(ActionEvent e) {
                x -= 3; // Adjust this value to change the scrolling step
                if (x < -newsLabel.getPreferredSize().width) {
                    currentTitleIndex = (currentTitleIndex + 1) % newsTitles.size();
                    String nextTitle = newsTitles.get(currentTitleIndex);
                    newsLabel.setText(nextTitle);
                    newsLabel.setSize(newsLabel.getPreferredSize()); // Update size based on new text
                    x = newsPanel.getWidth();
                }
                newsLabel.setLocation(x, newsLabel.getY());
            }
        });

        newsTimer.start();
    }
    
    /** Sets the time locally so it can be updated on the display. 
     * 	Does not accept parameters or return anything. 
     */
    public void setTime() {
    	String time = java.time.LocalDateTime.now().toString();
    	LocalDateTime dateObject = LocalDateTime.parse(time);
    	DateTimeFormatter targetFormat = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");
    	timeToDisplay = dateObject.format(targetFormat);
    }
    
    /**
     * This method will streamline the creation of placing images in the JPanel for advertisements
     * 
     * @param path accepts a file path for an image to be displayed.
     */
    public void setImage(String path) {
        String adPath = path;
        ImageIcon adIcon = new ImageIcon(adPath); 
        Image resizedAd = adIcon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        resizedAdIcon = new ImageIcon(resizedAd);
        adLabel.setIcon(resizedAdIcon);
    }

}