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

public class AddTimes implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/addTimes.html";

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
        


        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.1</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        html = html + """
                <div class="flexBox" style="justify-content:space-between; margin-bottom:5px;">
                <a href="./"><button type="button" class="button">Back</button></a>
                <h1 class='ultrakillTitleText'>-- ADD A RUN --</h1>
                <div style="width:15vw;height:4vw;padding: 0vw 2vw;"></div>
                </div>
                <form method="post" action="/intermediate.html">

                    <div class='settingChunk'>
                        <h3>Runner Name*</h3>

                        <select name="Runner" id="Runners" required>
                            <option value="" selected disabled hidden>Runner Name</option>
                            <option value="1">Connor</option>
                            <option value="2">James</option>
                            <option value="3">George</option>
                            <option value="4">Ryan</option>
                            <option value="5">AJ</option>
                        </select>
                    </div>

                    <div class='settingChunk'>
                        <h3>Category Select*</h3>

                        <select name="Category" id="Categories" required>
                            <option value="Any%">Any%</option>
                            <option value="Any% OOB">Any% OOB</option>
                            <option value="P%">P%</option>
                            <option value="P% OOB">P% OOB</option>
                            <option value="NoMo">NoMo</option>
                            <option value="NoMo OOB">NoMo OOB</option>
                        </select>
                    </div>

                    <div class='settingChunk'>
                        <h3>Time*</h3>

                        <input type="text" id="timesAchieved" name="timeAchieved" placeholder="mm:ss.mmm" pattern="[0-9]{2}[:][0-9]{2}[.][0-9]{3}" required>
                    </div>

                    <div class='settingChunk'>
                        <h3>Video Link</h3>

                        <input type="url" id="videoLinks" name="videoLink" placeholder="https://youtu.be/" pattern="https://.*">
                    </div>

                    <div class='settingChunk'>
                        <h3>Runner Comments</h3>

                        <input type="text" id="runnerComments" name="runnerComment" placeholder="Your comment here">
                    </div>

                    <div class='settingChunk'>
                        <h3>Select Level*</h3>

                        <select name="level" id="Levels" required>
                            <option value="" selected disabled hidden>Select Level</option>
                            <option value="0-1">0-1</option>
                            <option value="0-2">0-2</option>
                            <option value="0-3">0-3</option>
                            <option value="0-4">0-4</option>
                            <option value="0-5">0-5</option>
                            <option value="1-1">1-1</option>
                            <option value="1-2">1-2</option>
                            <option value="1-3">1-3</option>
                            <option value="1-4">1-4</option>
                            <option value="2-1">2-1</option>
                            <option value="2-2">2-2</option>
                            <option value="2-3">2-3</option>
                            <option value="2-4">2-4</option>
                            <option value="3-1">3-1</option>
                            <option value="3-2">3-2</option>
                            <option value="p-1">p-1</option>
                            <option value="4-1">4-1</option>
                            <option value="4-2">4-2</option>
                            <option value="4-3">4-3</option>
                            <option value="4-4">4-4</option>
                            <option value="5-1">5-1</option>
                            <option value="5-2">5-2</option>
                            <option value="5-3">5-3</option>
                            <option value="5-4">5-4</option>
                            <option value="6-1">6-1</option>
                            <option value="6-2">6-2</option>
                            <option value="p-2">p-2</option>
                            <option value="7-1">7-1</option>
                            <option value="7-2">7-2</option>
                            <option value="7-3">7-3</option>
                            <option value="7-4">7-4</option>
                        </select>
                    </div>

                    <div class='settingChunk'>
                        <h3>Difficulty*</h3>

                        <select name="Difficulty" id="Difficulties" required>
                            <option value="" selected disabled hidden>Choose Difficulty</option>
                            <option value="1">Harmless</option>
                            <option value="2">Lenient</option>
                            <option value="3">Standard</option>
                            <option value="4">Violent</option>
                            <option value="5">Brutal</option>
                        </select>
                    </div>

                    <div class='settingChunk'>
                        <h3>Exit*</h3>

                        <select name="Exit" id="Exits" required>
                            <option value="Normal">Normal</option>
                            <option value="Secret">Secret</option>
                            <option value="Prime Door">P-Door</option>
                        </select>
                    </div>

                    <div>
                        <input type="submit" value="Submit Run" class="button">
                    </div>

                </form>
                """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
