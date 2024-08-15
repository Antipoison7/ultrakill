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

public class Runners implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/runners.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Runner Lookup</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        html = html + "<body>";
        html = html + """
                <div class="flexBox" style="justify-content:space-between; margin-bottom:10px;">
                <a href="./"><button type="button" class="button">Back</button></a>
                <h1 class='ultrakillTitleText'>-- Level Select --</h1>
                <a href="./addRunner.html"><button type="button" class="button">+ Add Runner</button></a>
                </div>
                """;

        html = html + "<div class = 'playersLayout'>";
        html = html + """
            <div style='width:20vw'>
                <h2>Connor Orders</h2>
                <img src='Pfp/Connor.jpg' width='40%'>
                <h3>Boss Times</h3>
                <div class='flexBox'>
                    <div>
                        <p>1-4: 01:03.078</p>
                        <p>2-4: 01:03.078</p>
                        <p>3-2: 01:03.078</p>
                        <p>4-4: 01:03.078</p>
                    </div>
                    <div>
                        <p>5-4: 01:03.078</p>
                        <p>6-2: 01:03.078</p>
                        <p>7-4: 01:03.078</p>
                        <p>p-1: 01:03.078</p>
                    </div>
                </div>
            </div>
            """;

                    

                        
        html = html + "</div>";

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
