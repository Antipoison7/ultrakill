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

public class Intermediate implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/intermediate.html";

    @Override
    public void handle(Context context) throws Exception {

        //Java Variables
        String Runner = context.formParam("Runner");
        String Category = context.formParam("Category");
        String Time = context.formParam("timeAchieved");
        String Video = context.formParam("videoLink");
        String Comment = context.formParam("runnerComment");
        String Level = context.formParam("level");
        String Difficulty = context.formParam("Difficulty");
        String Exit = context.formParam("Exit");
        boolean hasVideo = false;

        Speedrun theRun;

        if(Video != "")
        {
            hasVideo = true;
        }
        
        if(hasVideo = true) // Has a video
        {
            if(Comment != "") // Has A comment and a video
            {
                theRun = new Speedrun(Runner, Category, Time, Video, Level, Difficulty, Exit);
            }
            else // Has no comment and a video
            {
                theRun = new Speedrun(Runner, Category, Time, Video, Level, Difficulty, Exit, hasVideo);
            }
            
        }
        else // Has no video
        {
            if(Comment != "") // Has A comment and no video
            {
                theRun = new Speedrun(Runner, Category, Time, Comment,Level, Difficulty, Exit);
            }

            else // Has no comment and no video
            {
                theRun = new Speedrun(Runner, Category, Time, Level, Difficulty, Exit);
            }
        }
        
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Please Dont Break</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        html = html + """
                <p><a href="/">Damn, if you see this and it doesn't load click this. Do not refresh the page.</a></p>
                """;

        html = html + "<p>" + Runner + "</p>";
        html = html + "<p>" + Category + "</p>";
        html = html + "<p>" + Time + "</p>";
        html = html + "<p>" + Video + "</p>"; //Optional
        html = html + "<p>" + Comment + "</p>"; //Optional
        html = html + "<p>" + Level + "</p>";
        html = html + "<p>" + Difficulty + "</p>";
        html = html + "<p>" + Exit + "</p>";
        html = html + "<p>" + hasVideo + "</p>";

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
