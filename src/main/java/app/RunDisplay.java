package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import helper.LevelMaker;
import helper.NumberConversion;
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

public class RunDisplay implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/userRuns.html";

    @Override
    public void handle(Context context) throws Exception {
        JDBCConnection jdbc = new JDBCConnection();
        AdvancedRun userRun = new AdvancedRun(0,"","","","","","","","","","","");
        NumberConversion converter = new NumberConversion();
        userRun = jdbc.getIndividualRun(context.queryParam("runId"));
        // Create a simple HTML webpage in a String
        String html = "<html>";


        // Add some Head information
        html = html + "<head>" + 
               "<title>User Runs</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        html = html + """
                <div class="flexBox" style="justify-content:space-between; margin-bottom:10px;">
                <a href="./advTimes.html?level=""";

        html = html + (context.queryParam("prev")).toLowerCase();

        html = html + """
        "><button type="button" class="button">Back</button></a>
                <h1 class='ultrakillTitleText'>-- Level Select --</h1>
                <div style="width:15vw;height:4vw;padding: 0vw 2vw;"></div>
                </div>
                """;
        html = html + "<div class='flexBox' style = 'justify-content:center;'>";
        html = html + "<div class='userRun'>";

            html = html + "<h2><b>" + userRun.getLevelCode()+ ": " + userRun.getLevelName() + "</b> in <b>" + converter.ToDuration(userRun.getTime()) + "</b> by <b>" + userRun.getName() + "</b></h2>";

            html = html + "<div style='width:48vw; height:27vw;'>";
            if(userRun.getVideo() != null){
                html = html + converter.ToYoutube(userRun.getVideo()); 
            }
            else{
                html = html + "<img width = '100%' height = '100%' src='/NoVideo.png' alt='No Video Loaded'>";
            }
            html = html + "</div>";

            html = html + "<div>";
            html = html + "<h3><img src='Comment.png' class='userRunComment'>Run Description / Comments</h3>";
            if(userRun.getComment()!= null)
            {
                html = html + "<p>" + userRun.getComment() + "</p>";
            }
            else
            {
                html = html + "<p>No Comment :(</p>";
            }
        html = html + "</div>";
        html = html + "<div>";
            html = html + "<h3>Category: " + userRun.getCategory() + "</h3>";
            html = html + "<h3>Time: " + converter.ToDuration(userRun.getTime()) + "</h3>";
            html = html + "<h3>Difficulty: " + userRun.getDifficulty() + "</h3>";
            html = html + "<h3>Exit: " + userRun.getExit() + "</h3>";
        html = html + "</div>";
        html = html + "</div>";

        

        // Finish the HTML webpage
        html = html + "</body>";
        
        html = html + """
                <script>
            
                </script>
                """;

        html = html + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
