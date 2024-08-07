package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import helper.LevelMaker;
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

public class ViewTimes implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/times.html";
    LevelMaker elements = new LevelMaker();

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = """
        <!DOCTYPE html>
        <html>
        """;

        // Add some Head information
        html = html + "<head>" + 
               "<title>Runs</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";
        html = html + """
                <div class="flexBox" style="justify-content:space-between; margin-bottom:10px;">
                <a href="./"><button type="button" class="button">Back</button></a>
                <h1 class='ultrakillTitleText'>-- Level Select --</h1>
                <div style="width:15vw;height:4vw;padding: 0vw 2vw;"></div>
                </div>
                """;

        html = html + "<h2 class='layerHeading'>OVERTURE: THE MOUTH OF HELL</h2>";
        html = html + "<div class='levelRows' style='grid-template-columns: auto auto auto auto auto'>";
        html = html + elements.GetLevel("0-1");
        html = html + elements.GetLevel("0-2");
        html = html + elements.GetLevel("0-3");
        html = html + elements.GetLevel("0-4");
        html = html + elements.GetLevel("0-5");
        html = html + "</div>";

        html = html + "<h2 class='layerHeading'>LAYER 1: LIMBO</h2>";
        html = html + "<div class='levelRows' style='grid-template-columns: auto auto auto auto'>";
        html = html + elements.GetLevel("1-1");
        html = html + elements.GetLevel("1-2");
        html = html + elements.GetLevel("1-3");
        html = html + elements.GetLevel("1-4");
        html = html + "</div>";

        html = html + "<h2 class='layerHeading'>LAYER 2: LIMBO</h2>";
        html = html + "<div class='levelRows' style='grid-template-columns: auto auto auto auto'>";
        html = html + elements.GetLevel("2-1");
        html = html + elements.GetLevel("2-2");
        html = html + elements.GetLevel("2-3");
        html = html + elements.GetLevel("2-4");
        html = html + "</div>";

        html = html + "<h2 class='layerHeading'>LAYER 3: LIMBO</h2>";
        html = html + "<div class='levelRows' style='grid-template-columns: auto auto'>";
        html = html + elements.GetLevel("3-1");
        html = html + elements.GetLevel("3-2");

        html = html + "</div>";

        html = html + "<h2 class='layerHeading'>LAYER 4: LIMBO</h2>";
        html = html + "<div class='levelRows' style='grid-template-columns: auto auto auto auto'>";
        html = html + elements.GetLevel("4-1");
        html = html + elements.GetLevel("4-2");
        html = html + elements.GetLevel("4-3");
        html = html + elements.GetLevel("4-4");
        html = html + "</div>";

        html = html + "<h2 class='layerHeading'>LAYER 5: LIMBO</h2>";
        html = html + "<div class='levelRows' style='grid-template-columns: auto auto auto auto'>";
        html = html + elements.GetLevel("5-1");
        html = html + elements.GetLevel("5-2");
        html = html + elements.GetLevel("5-3");
        html = html + elements.GetLevel("5-4");
        html = html + "</div>";

        html = html + "<h2 class='layerHeading'>LAYER 6: LIMBO</h2>";
        html = html + "<div class='levelRows' style='grid-template-columns: auto auto'>";
        html = html + elements.GetLevel("6-1");
        html = html + elements.GetLevel("6-2");
        html = html + "</div>";

        html = html + "<h2 class='layerHeading'>LAYER 7: LIMBO</h2>";
        html = html + "<div class='levelRows' style='grid-template-columns: auto auto auto auto'>";
        html = html + elements.GetLevel("7-1");
        html = html + elements.GetLevel("7-2");
        html = html + elements.GetLevel("7-3");
        html = html + elements.GetLevel("7-4");
        html = html + "</div>";

        html = html + "<h2 class='layerHeading'>Prime Sanctums</h2>";
        html = html + "<div class='levelRows' style='grid-template-columns: auto auto'>";
        html = html + elements.GetLevel("p-1");
        html = html + elements.GetLevel("p-2");
        html = html + "</div>";

        // Finish the HTML webpage
        html = html + "</body>";

        html = html + """
                <script>
                    function toAny(idCode){
                        document.getElementById(idCode + "Any").style.display = "block";
                        document.getElementById(idCode + "P").style.display = "none";
                        document.getElementById("aSelect" + idCode).className = "levelButtonSelected";
                        document.getElementById("pSelect" + idCode).className = "levelButton";
                    }

                    function toP(idCode){
                        document.getElementById(idCode + "Any").style.display = "none";
                        document.getElementById(idCode + "P").style.display = "block";
                        document.getElementById("aSelect" + idCode).className = "levelButton";
                        document.getElementById("pSelect" + idCode).className = "levelButtonSelected";
                    }
                </script>
                """;

        html = html + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
