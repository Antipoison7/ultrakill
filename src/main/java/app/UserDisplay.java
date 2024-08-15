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

public class UserDisplay implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/userDisplay.html";

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
            <a href="./runners.html"><button type="button" class="button">Back</button></a>
            <h1 class='ultrakillTitleText'>-- Level Select --</h1>
            <div style="width:15vw;height:4vw;padding: 0vw 2vw;"></div>
            </div>
            """;
        

        

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
