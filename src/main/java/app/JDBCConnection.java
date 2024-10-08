package app;

import java.util.ArrayList;
import java.util.logging.Level;

import org.eclipse.jetty.server.Authentication.User;

import helper.BasicRun;
import helper.NumberConversion;
import helper.RunnerDetails;

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
                query = "SELECT runs.Category, Runners.displayname as name, Runners.profilepicture, min(runs.time) as time, Difficulty.DifficultyName, Difficulty.DifficultyDescription FROM runs LEFT JOIN Runners ON Runners.userid = runs.runner LEFT JOIN difficulty ON difficulty.DifficultyId = runs.Difficulty WHERE (Category = 'Any% OOB' OR Category = 'Any%') AND levelCode = '" + LevelCode + "' GROUP BY name ORDER BY time ASC;";
            }
            else
            {
                query = "SELECT runs.Category, Runners.displayname as name, Runners.profilepicture, min(runs.time) as time, Difficulty.DifficultyName, Difficulty.DifficultyDescription FROM runs LEFT JOIN Runners ON Runners.userid = runs.runner LEFT JOIN difficulty ON difficulty.DifficultyId = runs.Difficulty WHERE (Category = 'P% OOB' OR Category = 'P%') AND levelCode = '" + LevelCode + "' GROUP BY name ORDER BY time ASC;";
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
                query = "SELECT runs.rowid, runners.displayName as name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
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
                query = "SELECT runs.rowid, runners.displayName as name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
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
                query = "SELECT runs.rowid, runners.displayName as name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
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
                query = "SELECT runs.rowid, runners.displayName as name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
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
                query = "SELECT runs.rowid, runners.displayName as name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
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
                query = "SELECT runs.rowid, runners.displayName as name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
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

public ArrayList<AdvancedRun> getAllComplexRuns(String Levelid) {
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
                            query = "SELECT runs.rowid, runners.displayName as name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode\r\n" + //
                                                                "FROM runs\r\n" + //
                                                                "LEFT JOIN runners\r\n" + //
                                                                "ON runs.Runner = runners.UserID\r\n" + //
                                                                "LEFT JOIN difficulty\r\n" + //
                                                                "ON runs.difficulty = difficulty.DifficultyId\r\n" + //
                                                                "GROUP BY runners.Name,runs.LevelCode,runs.category\r\n" + //
                                                                "ORDER BY Time;";
            
        }
        else
        {
            query = "SELECT runs.rowid, runners.displayName as name, runners.ProfilePicture, min(runs.Time) as Time, difficulty.DifficultyName, difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode FROM runs LEFT JOIN runners ON runs.Runner = runners.UserID LEFT JOIN difficulty ON runs.difficulty = difficulty.DifficultyId WHERE levelcode = '" + Levelid + "' GROUP BY runners.Name,runs.LevelCode,runs.category ORDER BY Time;";
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

public AdvancedRun getIndividualRun(String runID) {
    // Create the ArrayList of Country objects to return
    AdvancedRun runDetails = new AdvancedRun(0,"","","","","","","","","","","");

    // Setup the variable for the JDBC connection
    Connection connection = null;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        // The Query
        String query = "SELECT Runs.rowId as runID,runners.ProfilePicture, Runs.Category, Runners.displayname as name, Runs.time, Runs.video, Runs.comment, Runs.levelCode, level.LevelName, Difficulty.DifficultyName, Difficulty.DifficultyDescription, Runs.exit \r\n" + //
                        "FROM Runs \r\n" + //
                        "LEFT JOIN Runners \r\n" + //
                        "ON Runners.userid = Runs.runner \r\n" + //
                        "LEFT JOIN Level \r\n" + //
                        "ON Level.LevelCode = Runs.LevelCode \r\n" + //
                        "LEFT JOIN difficulty \r\n" + //
                        "ON difficulty.DifficultyId = Runs.Difficulty \r\n" + //
                        "LEFT JOIN category \r\n" + //
                        "ON category.CategoryName = Runs.Category\r\n" + //
                        "WHERE runs.rowid='" + runID + "'";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            int runid = Integer.parseInt(results.getString("runID"));
            String UserProfile = results.getString("ProfilePicture");
            String Name = results.getString("name");
            String Time = results.getString("time");
            String Difficulty = results.getString("DifficultyName");
            String DifficultyDescription = results.getString("DifficultyDescription");
            String Category = results.getString("Category");
            String Comment = results.getString("comment");
            String Video = results.getString("video");
            String LevelCode = results.getString("levelCode");
            String LevelName = results.getString("levelName");
            String Exit = results.getString("exit");

            // Create a Country Object
            runDetails = new AdvancedRun(runid,UserProfile,Name,Time,Difficulty,DifficultyDescription,Category,Comment,Video,LevelCode,LevelName,Exit);
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
    return runDetails;
}

public RunnerDetails getUserDetails(int userId) {
    // Create the ArrayList of Country objects to return
    RunnerDetails details = new RunnerDetails("","","","","");

    // Setup the variable for the JDBC connection
    Connection connection = null;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        // The Query
        String query = "SELECT * FROM runners WHERE UserID = '" + userId + "';";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            String UserID     = results.getString("UserID");
            String Name     = results.getString("Name");
            String Steamid     = results.getString("SteamId");
            String DisplayName     = results.getString("DisplayName");
            String ProfilePicture     = results.getString("ProfilePicture");
            

            // Create a Country Object
            details = new RunnerDetails(UserID,Name,Steamid,DisplayName,ProfilePicture);
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
    return details;
}

public int getRunnerCount() {

    // Setup the variable for the JDBC connection
    Connection connection = null;
    int count = 0;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        // The Query
        String query = "SELECT count(Name) AS runnerCount FROM Runners;";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            count     = results.getInt("runnerCount");            
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
    return count;
}


public ArrayList<AdvancedRun> getUserLevels(int RunnerId) {
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
        String query = "SELECT runs.rowId as RunId ,Runner, min(time) as minTime, LevelCode \r\n" + //
                        "FROM runs\r\n" + //
                        "WHERE Exit = \"Normal\" \r\n" + //
                        "AND runner = " + RunnerId + "\r\n" + //
                        "GROUP BY runner, levelcode\r\n" + //
                        "\r\n" + //
                        "UNION\r\n" + //
                        "\r\n" + //
                        "SELECT  0 as RunId , " + RunnerId + " as Runner, 0 as minTime, LevelCode\r\n" + //
                        "FROM level \r\n" + //
                        "WHERE LevelCode \r\n" + //
                        "NOT IN (Select LevelCode FROM runs WHERE Exit = \"Normal\" AND runner = " + RunnerId + ")\r\n" + //
                        "\r\n" + //
                        "ORDER BY LevelCode;";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            int RunID = results.getInt("RunId");
            String Time = results.getString("minTime");
            String LevelCode = results.getString("LevelCode");

            // Create a Country Object
            AdvancedRun runReturn = new AdvancedRun(RunID,"Profile","Name",Time,"Hard","Big head man","Wouldn't you like to know, weather boy","Placeholder","Placeholder",LevelCode);

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

public void addARunner(Runner submittedRunner) {
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
        
        switch(submittedRunner.getRunnerType())
        {
            case 1:
            query = "INSERT INTO Runners (UserID, Name, DisplayName, SteamId) VALUES ((SELECT count(Name)+1 AS runnerCount FROM Runners),'" + submittedRunner.getRunnerName() + "','" + submittedRunner.getRunnerDisplayName() + "','" + submittedRunner.getSteamID() + "');";
            break;

            case 2:
            query = "INSERT INTO Runners (UserID, Name, DisplayName, SteamId) VALUES ((SELECT count(Name)+1 AS runnerCount FROM Runners),'" + submittedRunner.getRunnerName() + "','" + submittedRunner.getRunnerDisplayName() + "','n/a');";
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

public ArrayList<Runner> getAllRunners() {
    // Create the ArrayList of Country objects to return
    ArrayList<Runner> runners = new ArrayList<Runner>();

    // Setup the variable for the JDBC connection
    Connection connection = null;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        // The Query
        String query = "SELECT * FROM Runners;";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            String Runner     = results.getString("Name");
            String DisplayName  = results.getString("DisplayName");
            String SteamID  = results.getString("SteamId");
            int runnerID = results.getInt("UserID");

            // Create a Country Object
            Runner runner = new Runner(Runner, DisplayName, SteamID, runnerID);

            // Add the Country object to the array
            runners.add(runner);
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
    return runners;
}

public ArrayList<LevelTemplate> getLevelsCompleted(String userID) {
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
        String query = "SELECT runs.LevelCode, Level.LevelName FROM Runs LEFT JOIN Level ON Level.LevelCode = Runs.LevelCode WHERE Runner = '" + userID + "' GROUP BY runs.LevelCode Order By runs.LevelCode;";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            String levelCode     = results.getString("LevelCode");
            String levelName = results.getString("LevelName");

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

public ArrayList<AdvancedRun> getRunsForLevel(String userID, String LevelCode) {
    // Create the ArrayList of Country objects to return
    ArrayList<AdvancedRun> levels = new ArrayList<AdvancedRun>();

    // Setup the variable for the JDBC connection
    Connection connection = null;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        // The Query
        String query = "SELECT runs.rowID as runID, Runners.ProfilePicture, Runners.UserID as Name, runs.Time, Difficulty.DifficultyName, Difficulty.DifficultyDescription, runs.category, runs.comment, runs.video, runs.LevelCode, Level.LevelName, runs.Exit\r\n" + //
                        "FROM runs\r\n" + //
                        "LEFT JOIN Runners \r\n" + //
                        "ON runners.UserID = runs.Runner\r\n" + //
                        "LEFT JOIN Difficulty\r\n" + //
                        "ON difficulty.DifficultyId = runs.Difficulty\r\n" + //
                        "LEFT JOIN Level\r\n" + //
                        "ON level.LevelCode = runs.LevelCode\r\n" + //
                        "WHERE runs.LevelCode = \"" + LevelCode + "\"\r\n" + //
                        "AND runs.Runner = \"" + userID + "\" ORDER BY Time;";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            int RunID = results.getInt("runID");
            String UserProfile = results.getString("ProfilePicture");
            String Name = results.getString("Name");
            String Time = results.getString("Time");
            String Difficulty = results.getString("DifficultyName");
            String DifficultyDescription = results.getString("DifficultyDescription");
            String Category = results.getString("Category");
            String Comment = results.getString("Comment");
            String Video = results.getString("Video");
            String LevelCodeVal = results.getString("LevelCode");
            String LevelName = results.getString("LevelName");
            String Exit = results.getString("Exit");

            AdvancedRun level = new AdvancedRun(RunID,UserProfile,Name,Time,Difficulty,DifficultyDescription,Category,Comment,Video,LevelCodeVal,LevelName,Exit);

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

}
