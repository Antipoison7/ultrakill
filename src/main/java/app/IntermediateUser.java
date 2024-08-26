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

public class IntermediateUser implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/intermediateUser.html";

    @Override
    public void handle(Context context) throws Exception {

        //Java Variables
        String Runner = context.formParam("runnerName");
        String Display = context.formParam("runnerDisplayName");
        String SteamID = context.formParam("runnerSteamId");

        Runner theRunner;

        // System.out.println("Video = '" + Video + "'");
        // System.out.println("Comment = '" + Comment + "'");

        // System.out.println("Does Video = " + !Video.equals(""));
        // System.out.println("Does Comment = "+ !Comment.equals(""));

        
        if(!SteamID.equals("")) // Has a video
        {
            theRunner = new Runner(Runner, Display, SteamID, 1);
        }
        else // Has no video
        {
            theRunner = new Runner(Runner, Display, SteamID, 2);
        }

        JDBCConnection jdbc = new JDBCConnection();
    
        jdbc.addARunner(theRunner);
        
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Please Dont Break</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        // html = html + "<meta http-equiv='refresh' content='5'; url ='./'/>";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        html = html + """
                <p><a href="/">Thanks for registering. To set a profile picture or request other changes, message Connor on discord @antipoison or email at orders.connor@gmail.com</a></p>
                <p><a href="/">Click the text to go back to the home page</a></p>
                """;

        // html = html + "<p>" + Runner + "</p>";
        // html = html + "<p>" + Category + "</p>";
        // html = html + "<p>" + Time + "</p>";
        // html = html + "<p>" + Video + "</p>"; //Optional
        // html = html + "<p>" + Comment + "</p>"; //Optional
        // html = html + "<p>" + Level + "</p>";
        // html = html + "<p>" + Difficulty + "</p>";
        // html = html + "<p>" + Exit + "</p>";

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
