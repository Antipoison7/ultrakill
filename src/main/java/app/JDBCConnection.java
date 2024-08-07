package app;

import java.util.ArrayList;

import helper.BasicRun;
import helper.NumberConversion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/Ultrakill.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the Countries in the database.
     * @return
     *    Returns an ArrayList of Country objects
     */
    public ArrayList<Country> getAllCountries() {
        // Create the ArrayList of Country objects to return
        ArrayList<Country> countries = new ArrayList<Country>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Country";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String m49Code     = results.getString("m49code");
                String name  = results.getString("countryName");

                // Create a Country Object
                Country country = new Country(m49Code, name);

                // Add the Country object to the array
                countries.add(country);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return countries;
    }

    public void addARun(Speedrun submittedRun) {
        // Setup the variable for the JDBC connection
        Connection connection = null;
        NumberConversion numbs = new NumberConversion();

        String query = "";

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // System.out.println("Type = '"+ submittedRun.getType() + "'");

            Double runTime = numbs.ToSeconds(submittedRun.getTime());
            
            switch(submittedRun.getType())
            {
                case 1:
                query = "INSERT INTO Runs (Runner, Category, Time, Comment, Video, LevelCode, Difficulty, Exit) VALUES ('"+ submittedRun.getRunnerId() +"','"+ submittedRun.getCategory() +"','" + runTime + "','" + submittedRun.getComment() + "','" + submittedRun.getVideo()+ "','" + submittedRun.getLevel() + "','" + submittedRun.getDifficulty() + "','" + submittedRun.getExit() + "');";
                break;

                case 2:
                query = "INSERT INTO Runs (Runner, Category, Time, Video, LevelCode, Difficulty, Exit) VALUES ('"+ submittedRun.getRunnerId() +"','"+ submittedRun.getCategory() +"','" + runTime + "','" + submittedRun.getVideo()+ "','" + submittedRun.getLevel() + "','" + submittedRun.getDifficulty() + "','" + submittedRun.getExit() + "');";
                break;

                case 3:
                query = "INSERT INTO Runs (Runner, Category, Time, Comment ,LevelCode, Difficulty, Exit) VALUES ('"+ submittedRun.getRunnerId() +"','"+ submittedRun.getCategory() +"','" + runTime + "','" + submittedRun.getComment() + "','" + submittedRun.getLevel() + "','" + submittedRun.getDifficulty() + "','" + submittedRun.getExit() + "');";
                break;

                case 4:
                query = "INSERT INTO Runs (Runner, Category, Time, LevelCode, Difficulty, Exit) VALUES ('"+ submittedRun.getRunnerId() +"','"+ submittedRun.getCategory() +"','" + runTime + "','" + submittedRun.getLevel() + "','" + submittedRun.getDifficulty() + "','" + submittedRun.getExit() + "');";
                break;
            }
            
            statement.execute(query);            
            
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

    }

    public LevelTemplate getLevel(String levelId) {
        // Create the ArrayList of Country objects to return
        LevelTemplate levelDetails = new LevelTemplate("","");

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Level where LevelCode = '" + levelId + "';";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String levelCode     = results.getString("LevelCode");
                String levelName  = results.getString("LevelName");

                // Create a Country Object
                levelDetails = new LevelTemplate(levelCode, levelName);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return levelDetails;
    }

    public ArrayList<BasicRun> getBasicRuns(String LevelCode, String Category) {
        // Create the ArrayList of Country objects to return
        ArrayList<BasicRun> basicRuns = new ArrayList<BasicRun>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "";

            if(Category.equals("A"))
            {
                query = "SELECT runs.Category, Runners.name, Runners.profilepicture, min(runs.time) as time, Difficulty.DifficultyName, Difficulty.DifficultyDescription FROM runs LEFT JOIN Runners ON Runners.userid = runs.runner LEFT JOIN difficulty ON difficulty.DifficultyId = runs.Difficulty WHERE (Category = 'Any% OOB' OR Category = 'Any%') AND levelCode = '" + LevelCode + "' GROUP BY name ORDER BY time ASC;";
            }
            else
            {
                query = "SELECT runs.Category, Runners.name, Runners.profilepicture, min(runs.time) as time, Difficulty.DifficultyName, Difficulty.DifficultyDescription FROM runs LEFT JOIN Runners ON Runners.userid = runs.runner LEFT JOIN difficulty ON difficulty.DifficultyId = runs.Difficulty WHERE (Category = 'P% OOB' OR Category = 'P%') AND levelCode = '" + LevelCode + "' GROUP BY name ORDER BY time ASC;";
            }
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String runCategory  = results.getString("Category");
                String runName  = results.getString("name");
                String runUserProfile  = results.getString("profilepicture");
                String runTime  = results.getString("time");
                String runDifficulty  = results.getString("DifficultyName");
                String runDifficultyDescription  = results.getString("DifficultyDescription");

                // Create a Country Object
                BasicRun usersRun = new BasicRun(runCategory,runName,runTime,runDifficulty,runDifficultyDescription,runUserProfile);

                // Add the Country object to the array
                basicRuns.add(usersRun);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return basicRuns;
    }
}
