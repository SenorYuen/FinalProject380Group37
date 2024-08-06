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

## Inserting Image Paths

The `InsertImage` class inserts paths of advertisement images into the database. 

Ensure sure you are in the `src` directory when compiling and running the Java files.

1. Navigate to the `MessageBoard` directory:

    ```
    cd Path/to/project/MessageBoard/
    ```

2. Compile the `InsertImage` class:

    ```
    javac src/edu/ucalgary/ensf380/InsertImage.java
    ```

3. Run the `InsertImage` class, passing your MySQL root password as an argument:

    ```
    java src/edu.ucalgary.ensf380.InsertImage <your_mysql_password>
    ```

## Retrieving and Displaying Image Paths

The `RetrieveImage` Grabs the images that have just been fetched, and displays  them

1. Compile the `RetrieveImage` class:

    ```
    javac src/edu/ucalgary/ensf380/RetrieveImage.java
    ```

2. Run the `RetrieveImage` class, passing your MySQL root password as an argument:

    ```
    java src/edu.ucalgary.ensf380.RetrieveImage <your_mysql_password>
    ```

3. The images will be fetched from the database and displayed.




