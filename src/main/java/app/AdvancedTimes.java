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

public class AdvancedTimes implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/advTimes.html";

    @Override
    public void handle(Context context) throws Exception {
        JDBCConnection jdbc = new JDBCConnection();
        LevelTemplate selectedLevel = jdbc.getLevel(context.queryParam("level"));
        // Create a simple HTML webpage in a String
        String html = "<html>";


        // Add some Head information
        html = html + "<head>" + 
               "<title>Advanced Times</title>";

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
        html = html + "<div class='runSelectContainer'>";

        html = html + """
                    <div class="dropdown">
                        <button onclick="myFunction()" class="dropbtn">
                                """;

        System.out.println("contextPath= " + context.queryParam("level"));
        if(context.queryParam("level").equals("all"))
        {
            html = html + "SELECT A LEVEL";
        }
        else
        {
            html = html + selectedLevel.getLevelCode() + ": " + selectedLevel.getLevelName();
        }
        // html = html + ;

        html = html + """
                        </button>
                        <div id="myDropdown" class="dropdown-content">
                        """;

        html = html + dropdownHandler();

        html = html + """
                        </div>
                    </div> 
                """;
        html = html + "<div class='flexBox'style='margin-bottom:5px; margin-top: 5px; justify-content: space-around;'>";
        html = html + "<button class='categoryButtonSelected' id='anyButton' onclick='toAny()'>Any %</button>";
        html = html + "<button class='categoryButton' id='pButton' onclick='toP()'>P %</button>";
        html = html + "<button class='categoryButton' id='noMoButton' onclick='toNoMo()'>NoMo</button>";
        html = html + "</div>";
        html = html + "</div>";

        html = html + "<div class='runTableContainer'>";
        html = html + "<table>";
        if(context.queryParam("level").equals("all"))
        {
            html = html + """
                <tr>
                    <th>Rank</th>
                    <th>Pfp</th>
                    <th>Player</th>
                    <th>Time</th>
                    <th>Difficulty</th>
                    <th>Inbounds</th>
                    <th>Comment</th>
                    <th>Video</th>
                    <th>Level</th>
                </tr>""";
        }
        else
        {
            html = html + """
                <tr>
                    <th>Rank</th>
                    <th>Pfp</th>
                    <th>Player</th>
                    <th>Time</th>
                    <th>Difficulty</th>
                    <th>Inbounds</th>
                    <th>Comment</th>
                    <th>Video</th>
                </tr>""";
        }

        html = html + "</table>";
        
        html = html + "</div>";

        // Finish the HTML webpage
        html = html + "</body>";
        
        html = html + """
                <script>
                /* When the user clicks on the button,
                    toggle between hiding and showing the dropdown content */
                    function myFunction() 
                    {
                    document.getElementById("myDropdown").classList.toggle("show");
                    }

                    // Close the dropdown menu if the user clicks outside of it
                    window.onclick = function(event) 
                    {
                        if (!event.target.matches('.dropbtn')) 
                        {
                            var dropdowns = document.getElementsByClassName("dropdown-content");
                            var i;
                            for (i = 0; i < dropdowns.length; i++) 
                            {
                                var openDropdown = dropdowns[i];
                                if (openDropdown.classList.contains('show')) 
                                {
                                    openDropdown.classList.remove('show');
                                }
                            }
                        }
                    } 

                    function toAny(){
                        document.getElementById("anyButton").className = "categoryButtonSelected";
                        document.getElementById("pButton").className = "categoryButton";
                        document.getElementById("noMoButton").className = "categoryButton";
                    }

                    function toP(){
                        document.getElementById("anyButton").className = "categoryButton";
                        document.getElementById("pButton").className = "categoryButtonSelected";
                        document.getElementById("noMoButton").className = "categoryButton";
                    }

                    function toNoMo(){
                        document.getElementById("anyButton").className = "categoryButton";
                        document.getElementById("pButton").className = "categoryButton";
                        document.getElementById("noMoButton").className = "categoryButtonSelected";
                    }
                </script>
                """;

        html = html + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    public String dropdownHandler()
    {
        String dropdownGenerator = "<a href='advTimes.html?level=all'>All</a>";

        JDBCConnection jdbc = new JDBCConnection();

        ArrayList <LevelTemplate> levels = jdbc.getAllLevels();

        for(LevelTemplate thisLevel:levels)
        {
            dropdownGenerator = dropdownGenerator + "<a href='advTimes.html?level=" + thisLevel.getLevelCode() + "'>" + thisLevel.getLevelCode() + ": " + thisLevel.getLevelName() + "</a>";
        }

        return dropdownGenerator;
    }

}
