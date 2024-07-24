package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = """
                <!DOCTYPE html>
                    <html>
                """;

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        html = html + """
                        <img src='ultrakillTitle.png' class='ultrakillTitleImage'>
                        <h1 class='ultrakillTitleText'>-- EARLY ACCESS --</h1>
                        <div class='flexBox' style='align-items:center;flex-direction:column;'>
                            <a href='./times.html' class='titleAnchor'><div class='ultrakillTitleButtonMain' id='times'><h2 id='timesText'>View Times</h2></div></a>
                            <a href='./advTimes.html' class='titleAnchor'><div class='ultrakillTitleButtonMain' id='advtimes'><h2 id='advtimesText'>Advanced Times</h2></div></a>
                            <a href='./addTimes.html' class='titleAnchor'><div class='ultrakillTitleButtonMain' id='addTimes'><h2 id='addTimesText'>Add Times</h2></div></a>
                            <a href='./userSettings.html' class='titleAnchor'><div class='ultrakillTitleButtonMain' id='settings'><h2 id='settingsText'>Settings</h2></div></a>
                            <a href='https://www.speedrun.com/ultrakill' class='titleAnchor' target='_blank'><div class='ultrakillTitleButtonMain' id='speedrun'><h2 id='speedText'>Speedrun.com</h2></div></a>
                        </div>
                """;

       
        // Finish the HTML webpage
        html = html + "</body>";

        html = html + """
                <script>
                document.getElementById('speedrun').onmouseover = function() \
                {
                    const speed = document.getElementById("speedText");
                    speed.style.color = 'black';
                }; 

                document.getElementById('speedrun').onmouseout = function() \
                {
                    const speed = document.getElementById("speedText");
                    speed.style.color = 'white';
                }; 


                document.getElementById('addTimes').onmouseover = function() \
                {
                    const times = document.getElementById("addTimesText");
                    times.style.color = 'black';
                }; 

                document.getElementById('addTimes').onmouseout = function() \
                {
                    const times = document.getElementById("addTimesText");
                    times.style.color = 'white';
                }; 


                document.getElementById('advtimes').onmouseover = function() \
                {
                    const times = document.getElementById("advtimesText");
                    times.style.color = 'black';
                }; 

                document.getElementById('advtimes').onmouseout = function() \
                {
                    const times = document.getElementById("advtimesText");
                    times.style.color = 'white';
                }; 


                document.getElementById('settings').onmouseover = function() \
                {
                    const addTimes = document.getElementById("settingsText");
                    addTimes.style.color = 'black';
                }; 

                document.getElementById('settings').onmouseout = function() \
                {
                    const addTimes = document.getElementById("settingsText");
                    addTimes.style.color = 'white';
                }; 


                document.getElementById('times').onmouseover = function() \
                {
                    const addTimes = document.getElementById("timesText");
                    addTimes.style.color = 'black';
                }; 

                document.getElementById('times').onmouseout = function() \
                {
                    const addTimes = document.getElementById("timesText");
                    addTimes.style.color = 'white';
                }; 
                </script>
                """;

        html = html + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }


    /**
     * Get the names of the countries in the database.
     */
    public ArrayList<String> getAllCountries() {
        // Create the ArrayList of String objects to return
        ArrayList<String> countries = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM country";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String countryName  = results.getString("countryName");

                // Add the country object to the array
                countries.add(countryName);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
            //e.printStackTrace();
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
                //e.printStackTrace();
            }
        }

        // Finally we return all of the countries
        return countries;
    }
}
