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

    public ArrayList<LevelTemplate> getAllLevels() {
        // Create the ArrayList of Country objects to return
        ArrayList<LevelTemplate> levels = new ArrayList<LevelTemplate>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT levelcode, levelname FROM Level;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String levelCode     = results.getString("levelcode");
                String levelName     = results.getString("levelname");

                // Create a Country Object
                LevelTemplate level = new LevelTemplate(levelCode, levelName);

                // Add the Country object to the array
                levels.add(level);
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
        return levels;
    }

public ArrayList<AdvancedRun> getComplexRuns(String identification, String Levelid) {
    // Create the ArrayList of Country objects to return
    ArrayList<AdvancedRun> runs = new ArrayList<AdvancedRun>();

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

        if(Levelid.equals("all"))
        {
            if(identification.equals("Any"))
            {
                query = "SELECT runs.rowid, runners.Name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
                                        "FROM runs\r\n" + //
                                        "LEFT JOIN runners\r\n" + //
                                        "ON runs.Runner = runners.UserID\r\n" + //
                                        "LEFT JOIN difficulty\r\n" + //
                                        "ON runs.difficulty = difficulty.DifficultyId\r\n" + //
                                        "WHERE category = 'Any%' \r\n" + //
                                        "OR category = 'Any% OOB'\r\n" + //
                                        "GROUP BY runners.Name,runs.LevelCode\r\n" + //
                                        "ORDER BY Time;";
            }
            else if(identification.equals("P"))
            {
                query = "SELECT runs.rowid, runners.Name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
                                        "FROM runs\r\n" + //
                                        "LEFT JOIN runners\r\n" + //
                                        "ON runs.Runner = runners.UserID\r\n" + //
                                        "LEFT JOIN difficulty\r\n" + //
                                        "ON runs.difficulty = difficulty.DifficultyId\r\n" + //
                                        "WHERE category = 'P%' \r\n" + //
                                        "OR category = 'P% OOB'\r\n" + //
                                        "GROUP BY runners.Name,runs.LevelCode\r\n" + //
                                        "ORDER BY Time;";
            }
            else if(identification.equals("NoMo"))
            {
                query = "SELECT runs.rowid, runners.Name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
                                        "FROM runs\r\n" + //
                                        "LEFT JOIN runners\r\n" + //
                                        "ON runs.Runner = runners.UserID\r\n" + //
                                        "LEFT JOIN difficulty\r\n" + //
                                        "ON runs.difficulty = difficulty.DifficultyId\r\n" + //
                                        "WHERE category = 'NoMo' \r\n" + //
                                        "OR category = 'NoMo OOB'\r\n" + //
                                        "GROUP BY runners.Name,runs.LevelCode\r\n" + //
                                        "ORDER BY Time;";
            }
        }
        else
        {
            if(identification.equals("Any"))
            {
                query = "SELECT runs.rowid, runners.Name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
                                        "FROM runs\r\n" + //
                                        "LEFT JOIN runners\r\n" + //
                                        "ON runs.Runner = runners.UserID\r\n" + //
                                        "LEFT JOIN difficulty\r\n" + //
                                        "ON runs.difficulty = difficulty.DifficultyId\r\n" + //
                                        "WHERE (category = 'Any%' \r\n" + //
                                        "OR category = 'Any% OOB')\r\n" + //
                                        "AND levelCode = '" + Levelid + "'\r\n" + //
                                        "GROUP BY runners.Name,runs.LevelCode\r\n" + //
                                        "ORDER BY Time;";
            }
            else if(identification.equals("P"))
            {
                query = "SELECT runs.rowid, runners.Name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
                                        "FROM runs\r\n" + //
                                        "LEFT JOIN runners\r\n" + //
                                        "ON runs.Runner = runners.UserID\r\n" + //
                                        "LEFT JOIN difficulty\r\n" + //
                                        "ON runs.difficulty = difficulty.DifficultyId\r\n" + //
                                        "WHERE (category = 'P%' \r\n" + //
                                        "OR category = 'P% OOB')\r\n" + //
                                        "AND levelCode = '" + Levelid + "'\r\n" + //
                                        "GROUP BY runners.Name,runs.LevelCode\r\n" + //
                                        "ORDER BY Time;";
            }
            else if(identification.equals("NoMo"))
            {
                query = "SELECT runs.rowid, runners.Name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
                                        "FROM runs\r\n" + //
                                        "LEFT JOIN runners\r\n" + //
                                        "ON runs.Runner = runners.UserID\r\n" + //
                                        "LEFT JOIN difficulty\r\n" + //
                                        "ON runs.difficulty = difficulty.DifficultyId\r\n" + //
                                        "WHERE (category = 'NoMo' \r\n" + //
                                        "OR category = 'NoMo OOB')\r\n" + //
                                        "AND levelCode = '" + Levelid + "'\r\n" + //
                                        "GROUP BY runners.Name,runs.LevelCode\r\n" + //
                                        "ORDER BY Time;";
            }
        }
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            int RunID = results.getInt("rowid");
            String UserProfile = results.getString("ProfilePicture");
            String Name = results.getString("Name");
            String Time = results.getString("Time");
            String Difficulty = results.getString("DifficultyName");
            String DifficultyDescription = results.getString("DifficultyDescription");
            String Category = results.getString("category");
            String Comment = results.getString("comment");
            String Video = results.getString("video");
            String LevelCode = results.getString("levelcode");

            // Create a Country Object
            AdvancedRun runReturn = new AdvancedRun(RunID,UserProfile,Name,Time,Difficulty,DifficultyDescription,Category,Comment,Video,LevelCode);

            // Add the Country object to the array
            runs.add(runReturn);
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
    return runs;
}

public ArrayList<AdvancedRun> getComplexRunsAll(String Levelid) {
    // Create the ArrayList of Country objects to return
    ArrayList<AdvancedRun> runs = new ArrayList<AdvancedRun>();

    // Setup the variable for the JDBC connection
    Connection connection = null;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        // The Query
        String query = "SELECT runs.rowid, runners.Name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
                        "FROM runs\r\n" + //
                        "LEFT JOIN runners\r\n" + //
                        "ON runs.Runner = runners.UserID\r\n" + //
                        "LEFT JOIN difficulty\r\n" + //
                        "ON runs.difficulty = difficulty.DifficultyId\r\n" + //
                        "WHERE levelCode='" + Levelid + "'\r\n" + //
                        "GROUP BY runners.Name,runs.category\r\n" + //
                        "ORDER BY Time;";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            int RunID = results.getInt("rowid");
            String UserProfile = results.getString("ProfilePicture");
            String Name = results.getString("Name");
            String Time = results.getString("Time");
            String Difficulty = results.getString("DifficultyName");
            String DifficultyDescription = results.getString("DifficultyDescription");
            String Category = results.getString("category");
            String Comment = results.getString("comment");
            String Video = results.getString("video");
            String LevelCode = results.getString("levelcode");

            // Create a Country Object
            AdvancedRun runReturn = new AdvancedRun(RunID,UserProfile,Name,Time,Difficulty,DifficultyDescription,Category,Comment,Video,LevelCode);

            // Add the Country object to the array
            runs.add(runReturn);
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
    return runs;
}
}
