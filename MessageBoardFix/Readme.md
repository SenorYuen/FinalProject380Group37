# Subway Screen Project Database Setup

This Readme is for setting up an SQL database to store and pull advertisment images for the Final Subway screen display.

This Readme File is dedicated to show the following:
1. setting up the database
2. Inserting images into the database
3. inserting image paths
4. ajd Retrieving the images to display

## Prerequisites

- Java Development Kit 
- MySQL server installed and running
-SQL client



## Setting Up the Database

1. Open a 
MySQL client
2. Execute the following SQL commands to set up the `SubwayScreenAdvertisements` database and input the following:

    ```sql
    DROP DATABASE IF EXISTS SubwayScreenAdvertisements;
    CREATE DATABASE SubwayScreenAdvertisements;
    USE SubwayScreenAdvertisements;

    CREATE TABLE advertisements (
        id INT AUTO_INCREMENT PRIMARY KEY,
        ad_name VARCHAR(255) NOT NULL,
        ad_file LONGBLOB NOT NULL
    );

    CREATE TABLE images (
        id INT AUTO_INCREMENT PRIMARY KEY,
        image_path VARCHAR(255) NOT NULL
    );
    ```

## Running the Program:
1. Using Exlipse, navigate to the ScreenDisplay.java class.
2. Press the arrow next to the Run icon, and press "Run Configurations..."
3. Navigate to arguments, and fill in the following information separated by spaces:
- City startDate endDate "publishedAt" sqlDatabasePassword trainNumber
- An example of using this format can be:
- "Calgary 2024-08-03 20204-08-04 publishedAt pleasework 6"






