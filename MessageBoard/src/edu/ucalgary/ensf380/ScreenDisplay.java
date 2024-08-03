package edu.ucalgary.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ScreenDisplay extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel advertisementPanel;
    private JPanel weatherScreen;
    private JPanel newsScreen;
    private JPanel trainScreen;
    
    private Timer adTimer;
    private Timer newsTimer;
    private JLabel newsLabel;

    private List<String> newsTitles;
    private int currentTitleIndex = 0;
    
    public ScreenDisplay(String cityName, String startDate, String endDate, String sortOrder) {
        setTitle("Transit Information System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        advertisementPanel = new JPanel();
        weatherScreen = new JPanel();
        trainScreen = new JPanel();
        newsScreen = new JPanel(new BorderLayout());
        
        // Backgrounds
        advertisementPanel.setBackground(Color.decode("#A9CCE3"));
        weatherScreen.setBackground(Color.decode("#01266C"));
        newsScreen.setBackground(Color.BLACK);
        trainScreen.setBackground(Color.decode("#CD5C5C"));

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
        add(weatherScreen, gridLayoutSetup);

        //News screen grid formatting and adding
        gridLayoutSetup.gridx = 0;
        gridLayoutSetup.gridy = 1;
        gridLayoutSetup.weightx = 1; 
        gridLayoutSetup.weighty = 0.6; 
        gridLayoutSetup.gridwidth = 2;
        gridLayoutSetup.gridheight = 1;
        add(newsScreen, gridLayoutSetup);

        //Train screen grid formatting and adding
        gridLayoutSetup.gridx = 0; 
        gridLayoutSetup.gridy = 2;
        gridLayoutSetup.weightx = 1; 
        gridLayoutSetup.weighty = 0.2;
        gridLayoutSetup.gridwidth = 2;
        gridLayoutSetup.gridheight = 1;
        add(trainScreen, gridLayoutSetup);
        
        // Display one ad
        String adPath = "src\\edu\\ucalgary\\ensf380\\testImage.jpg";
        ImageIcon adIcon = new ImageIcon(adPath); 
        Image resizedAd = adIcon.getImage().getScaledInstance(733, 500, Image.SCALE_SMOOTH);
        ImageIcon resizedAdIcon = new ImageIcon(resizedAd);
        JLabel adLabel = new JLabel();
        adLabel.setIcon(resizedAdIcon);
        
        // Map image - will need to be replaced later. 
        JLabel mapLabel = new JLabel();
        String mapPath = "src\\edu\\ucalgary\\ensf380\\testImage.jpg";
        ImageIcon mapIcon = new ImageIcon(mapPath); 
        Image resizedMap = mapIcon.getImage().getScaledInstance(680, 430, Image.SCALE_SMOOTH);
        ImageIcon resizedMapIcon = new ImageIcon(resizedMap);
        mapLabel.setIcon(resizedMapIcon);
        
        // swapping maps and images - will not be able to use the boolean for this one.
        advertisementPanel.setPreferredSize(new Dimension(633, 400));
        advertisementPanel.add(adLabel);
        adTimer = new Timer(10000, new ActionListener() {
            private boolean x = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (x) {
                    adLabel.setIcon(resizedMapIcon);
                    adTimer.setInitialDelay(5000); // map?
                } else {
                    adLabel.setIcon(resizedAdIcon);
                    adTimer.setInitialDelay(10000); // ad
                }
                x = !x;
            }
        });

        adTimer.start();

        //Handle the weather displaying. 
        weatherScreen.setLayout(new BorderLayout());
        WeatherFetcher weatherData = new WeatherFetcher(cityName);
     
        //Use getters to obtain all of the data fetched by the API.
        String displayCondition = weatherData.getCondition();
        String displayTemp = weatherData.getTemperature();
        String displayHumidity = weatherData.getHumidity();
        String displayWindSpeed = weatherData.getWind();
        String displayRain = weatherData.getRain();
        String displayTime = java.time.LocalDateTime.now().toString(); //TODO: UPDATE THIS THING LATER.
        
        //Create a text label to add the weather.
        JTextArea weatherInfo = new JTextArea(
                displayTime + 
                "\nCurrently: " + displayCondition + ", " + displayTemp + 
                "\nHumidity: " + displayHumidity + 
                "\nWind Speed: " + displayWindSpeed +
                "\nRain today: " + displayRain);
        insertText(weatherInfo, weatherScreen, 20, 1, 50, 20, 20);
        
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

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: SubwayScreenPrac <city_name> <start_date> <end_date> <sort_order>");
            return;
        }
        String cityName = args[0];
        String startDate = args[1];
        String endDate = args[2];
        String sortOrder = args[3];
        EventQueue.invokeLater(() -> {
            new ScreenDisplay(cityName, startDate, endDate, sortOrder).setVisible(true);
        });
    } 

    //Place text on the screen - function to format everything
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
    
    //Place image on the screen
    public void insertPicture(JLabel weatherLabel, String weatherPath, int w, int h) {
        ImageIcon weatherIcon = new ImageIcon(weatherPath); 
        Image resizedWeather = weatherIcon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        ImageIcon resizedWeatherIcon = new ImageIcon(resizedWeather);
        weatherLabel.setIcon(resizedWeatherIcon);
    }

    public void newsHeadlineScroller() {
        newsLabel = new JLabel(newsTitles.get(0));
        newsLabel.setFont(new Font("Ubuntu", Font.BOLD, 32));
        newsLabel.setForeground(Color.WHITE);
        newsLabel.setOpaque(true); 
        newsLabel.setBackground(Color.BLACK); 
        newsScreen.setLayout(null); 
        newsLabel.setSize(newsLabel.getPreferredSize()); 
        newsScreen.add(newsLabel);

        newsTimer = new Timer(50, new ActionListener() {
            private int x = newsScreen.getWidth();

            @Override
            public void actionPerformed(ActionEvent e) {
                x -= 2; // Adjust this value to change the scrolling step
                if (x < -newsLabel.getPreferredSize().width) {
                    currentTitleIndex = (currentTitleIndex + 1) % newsTitles.size();
                    String nextTitle = newsTitles.get(currentTitleIndex);
                    newsLabel.setText(nextTitle);
                    newsLabel.setSize(newsLabel.getPreferredSize()); // Update size based on new text
                    x = newsScreen.getWidth();
                }
                newsLabel.setLocation(x, newsLabel.getY());
            }
        });

        newsTimer.start();
    }

}