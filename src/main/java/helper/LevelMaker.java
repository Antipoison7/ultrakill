package helper;

import app.AdvancedRun;
import app.JDBCConnection;
import app.LevelTemplate;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.Objects;

//Makes the level blocks for ViewTimes / Simple Runs View
public class LevelMaker
{
    public String GetLevel(String levelCode)
    {
        String generatedCode = "";

        JDBCConnection jdbc = new JDBCConnection();

        LevelTemplate thisLevel = new LevelTemplate("","");
        thisLevel = jdbc.getLevel(levelCode);

        generatedCode = generatedCode + """
                                <div class="levelSelect">
                                    <div class="levelSelectThumbnail">
                                        <h2>""" + thisLevel.getLevelCode() + ": " +  thisLevel.getLevelName() + "</h2>";
       generatedCode = generatedCode + "<img src='./Thumbnails/" + thisLevel.getLevelCode() + ".webp'>";
        generatedCode = generatedCode + """
                                    </div>

                                    <div>
                                        <div class="flexBox" style="margin-bottom:5px; justify-content: space-around;">
                                            <button class="levelButtonSelected" onclick="toAny('""";
        generatedCode = generatedCode + thisLevel.getLevelCode();
        generatedCode = generatedCode + """
        ')"
                """;
        generatedCode = generatedCode + "id='aSelect" + thisLevel.getLevelCode() + "'";
        generatedCode = generatedCode + ">ANY%</button>";
        generatedCode = generatedCode + """
                <button class="levelButton" onclick="toP('""";
        generatedCode = generatedCode + thisLevel.getLevelCode();
                                    
        generatedCode = generatedCode + """
        ')"
                """;
        generatedCode = generatedCode + "id='pSelect" + thisLevel.getLevelCode() + "'";
        
        generatedCode = generatedCode + """
                
                >P%</button>
                        </div>
                        <div class="levelContainer" id='""";
                        generatedCode = generatedCode + thisLevel.getLevelCode() + "Any";
                        generatedCode = generatedCode + """
                        ' style="display:block;">""";
                                
                                
                        ArrayList<BasicRun> aRun = jdbc.getBasicRuns(levelCode, "A");
                        NumberConversion numbs = new NumberConversion();

                        for (BasicRun userRun : aRun)
                        {
                            generatedCode = generatedCode + "<div class='levelScoreInstance'>";
                                generatedCode = generatedCode + "<div class='levelUserPfp'><img src='" + userRun.getPfp() + "'></div>";
                                generatedCode = generatedCode + "<div class='levelUserName'><p>" + userRun.getName() + "</p></div>";
                                generatedCode = generatedCode + "<div class='levelUserDiff'><p>" + userRun.getDifficulty() + "</p></div>";
                                generatedCode = generatedCode + "<div class='levelUserTime'><p>" + numbs.ToDuration(userRun.getTime()) + "</p></div>";
                            generatedCode = generatedCode + "</div>";
                        }

        generatedCode = generatedCode + """
                        </div>

                        <div class="levelContainer" id='""";
                        generatedCode = generatedCode + thisLevel.getLevelCode() + "P";
                        generatedCode = generatedCode + "' style='display:none;'>";
                        
                        ArrayList<BasicRun> pRun = jdbc.getBasicRuns(levelCode, "P");

                        for (BasicRun userRun : pRun)
                        {
                        generatedCode = generatedCode + "<div class='levelScoreInstance'>";
                            generatedCode = generatedCode + "<div class='levelUserPfp'><img src='" + userRun.getPfp() + "'></div>";
                            generatedCode = generatedCode + "<div class='levelUserName'><p>" + userRun.getName() + "</p></div>";
                            generatedCode = generatedCode + "<div class='levelUserDiff'><p>" + userRun.getDifficulty() + "</p></div>";
                            generatedCode = generatedCode + "<div class='levelUserTime'><p>" + numbs.ToDuration(userRun.getTime()) + "</p></div>";
                        generatedCode = generatedCode + "</div>";
                        }
                                                        
                        generatedCode = generatedCode + """
                        </div>
                    </div>
                </div>
                """;
        
        return generatedCode;
    }

    //This generates the AdvancedTimes tables
    public String GetAllRuns(String selectedCategory, String level, String type)
    {
        String generatedString = "";
        int counter = 1;

        JDBCConnection jdbc = new JDBCConnection();
        NumberConversion numbs = new NumberConversion();

        ArrayList<AdvancedRun> aRun = jdbc.getComplexRuns(selectedCategory,level);
        if(type.equals("any"))
        {
            generatedString = generatedString + "<div class='runTableContainer'>";
            generatedString = generatedString + """
                <div style='grid-template-columns: 4% 5% 25% 16% 13% 5% 7% 7% 10%;'>
                    <div>Rank</div>
                    <div>Pfp</div>
                    <div>Player</div>
                    <div>Time</div>
                    <div>Difficulty</div>
                    <div>OOB?</div>
                    <div>Comment</div>
                    <div>Video</div>
                    <div class='runTableContainerLevel'>Level</div>
                </div>""";

        for (AdvancedRun userRun : aRun)
        {
                generatedString = generatedString + "<a href='/userRuns.html?runId=" + userRun.getRunID() + "&prev=All'>";
                generatedString = generatedString + "<div class='runTableContainerInstance' style='grid-template-columns: 4% 5% 25% 16% 13% 5% 7% 7% 10%;'>";
                    generatedString = generatedString + "<div class='runTableContainerLevel'>" + counter + "</div>";
                    generatedString = generatedString + "<div><img src='" + userRun.getPfp() + "'></div>";
                    generatedString = generatedString + "<div>" + userRun.getName() + "</div>";
                    generatedString = generatedString + "<div>" + numbs.ToDuration(userRun.getTime()) + "</div>";
                    generatedString = generatedString + "<div>" + userRun.getDifficulty() + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getCategory().equals("Any% OOB")||userRun.getCategory().equals("P% OOB")||userRun.getCategory().equals("NoMo OOB"))
                        {
                            generatedString = generatedString + "<img src='OutOfBounds.png' alt='Out Of Bounds'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getComment() != null)
                        {
                            generatedString = generatedString + "<img src='Comment.png' alt='Contains Comment'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getVideo() != null)
                        {
                            generatedString = generatedString + "<img src='Video.png' alt='Contains Video'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div class='runTableContainerLevel'>" + userRun.getLevelCode() + "</div>";
                generatedString = generatedString + "</div>";
            generatedString = generatedString + "</a>";
            counter = counter + 1;
        }
    }

    else if(type.equals("level"))
    {
        generatedString = generatedString + "<div class='runTableContainer'>";
        generatedString = generatedString + """
            <div style='grid-template-columns: 4% 5% 25% 16% 13% 5% 7% 7%;'>
                <div>Rank</div>
                <div>Pfp</div>
                <div>Player</div>
                <div>Time</div>
                <div>Difficulty</div>
                <div>OOB?</div>
                <div>Comment</div>
                <div>Video</div>
            </div>""";

        for (AdvancedRun userRun : aRun)
        {
            generatedString = generatedString + "<a href='/userRuns.html?runId=" + userRun.getRunID() + "&prev=" + level + "'>";
                generatedString = generatedString + "<div class='runTableContainerInstance' style='grid-template-columns: 4% 5% 25% 16% 13% 5% 7% 7%;'>";
                    generatedString = generatedString + "<div class='runTableContainerLevel'>" + counter + "</div>";
                    generatedString = generatedString + "<div><img src='" + userRun.getPfp() + "'></div>";
                    generatedString = generatedString + "<div>" + userRun.getName() + "</div>";
                    generatedString = generatedString + "<div>" + numbs.ToDuration(userRun.getTime()) + "</div>";
                    generatedString = generatedString + "<div>" + userRun.getDifficulty() + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getCategory().equals("Any% OOB")||userRun.getCategory().equals("P% OOB")||userRun.getCategory().equals("NoMo OOB"))
                        {
                            generatedString = generatedString + "<img src='OutOfBounds.png' alt='Out Of Bounds'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getComment() != null)
                        {
                            generatedString = generatedString + "<img src='Comment.png' alt='Contains Comment'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getVideo() != null)
                        {
                            generatedString = generatedString + "<img src='Video.png' alt='Contains Video'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "</div>";
                generatedString = generatedString + "</a>";
            counter = counter + 1;
        }
    }

    else if(type.equals("allAny"))
        {
            aRun = jdbc.getAllComplexRuns(level);
            generatedString = generatedString + "<div class='runTableContainer'>";
            generatedString = generatedString + """
                <div style='grid-template-columns: 4% 5% 22% 14% 13% 5% 7% 7% 5% 10%;'>
                    <div>Rank</div>
                    <div>Pfp</div>
                    <div>Player</div>
                    <div>Time</div>
                    <div>Difficulty</div>
                    <div>OOB?</div>
                    <div>Comment</div>
                    <div>Video</div>
                    <div>Category</div>
                    <div class='runTableContainerLevel'>Level</div>
                </div>""";

        for (AdvancedRun userRun : aRun)
        {
                generatedString = generatedString + "<a href='/userRuns.html?runId=" + userRun.getRunID() + "&prev=All'>";
                generatedString = generatedString + "<div class='runTableContainerInstance' style='grid-template-columns: 4% 5% 22% 13% 13% 5% 7% 7% 10% 5%;'>";
                    generatedString = generatedString + "<div class='runTableContainerLevel'>" + counter + "</div>";
                    generatedString = generatedString + "<div><img src='" + userRun.getPfp() + "'></div>";
                    generatedString = generatedString + "<div>" + userRun.getName() + "</div>";
                    generatedString = generatedString + "<div>" + numbs.ToDuration(userRun.getTime()) + "</div>";
                    generatedString = generatedString + "<div>" + userRun.getDifficulty() + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getCategory().equals("Any% OOB")||userRun.getCategory().equals("P% OOB")||userRun.getCategory().equals("NoMo OOB"))
                        {
                            generatedString = generatedString + "<img src='OutOfBounds.png' alt='Out Of Bounds'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getComment() != null)
                        {
                            generatedString = generatedString + "<img src='Comment.png' alt='Contains Comment'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getVideo() != null)
                        {
                            generatedString = generatedString + "<img src='Video.png' alt='Contains Video'>";
                        }
                    generatedString = generatedString + "</div>";
                    if(userRun.getCategory().substring(0,1).equals("A"))
                    {
                        generatedString = generatedString + "<div class='runTableContainerLevel'>Any%</div>";
                    }
                    else if(userRun.getCategory().substring(0,1).equals("P"))
                    {
                        generatedString = generatedString + "<div class='runTableContainerLevel' style='color:gold;'>P%</div>";
                    }
                    else if(userRun.getCategory().substring(0,1).equals("N"))
                    {
                        generatedString = generatedString + "<div class='runTableContainerLevel' style='color:lime'>NoMo</div>";
                    }
                    generatedString = generatedString + "<div class='runTableContainerLevel'>" + userRun.getLevelCode() + "</div>";
                generatedString = generatedString + "</div>";
            generatedString = generatedString + "</a>";
            counter = counter + 1;
        }
    }

    else if(type.equals("allLevel"))
        {
            aRun = jdbc.getAllComplexRuns(level);
            generatedString = generatedString + "<div class='runTableContainer'>";
            generatedString = generatedString + """
                <div style='grid-template-columns: 4% 6% 25% 17% 13% 5% 8% 8% 6%;'>
                    <div>Rank</div>
                    <div>Pfp</div>
                    <div>Player</div>
                    <div>Time</div>
                    <div>Difficulty</div>
                    <div>OOB?</div>
                    <div>Comment</div>
                    <div>Video</div>
                    <div>Category</div>
                </div>""";

        for (AdvancedRun userRun : aRun)
        {
                generatedString = generatedString + "<a href='/userRuns.html?runId=" + userRun.getRunID() + "&prev=All'>";
                generatedString = generatedString + "<div class='runTableContainerInstance' style='grid-template-columns: 4% 6% 25% 17% 13% 5% 8% 8% 6%;'>";
                    generatedString = generatedString + "<div class='runTableContainerLevel'>" + counter + "</div>";
                    generatedString = generatedString + "<div><img src='" + userRun.getPfp() + "'></div>";
                    generatedString = generatedString + "<div>" + userRun.getName() + "</div>";
                    generatedString = generatedString + "<div>" + numbs.ToDuration(userRun.getTime()) + "</div>";
                    generatedString = generatedString + "<div>" + userRun.getDifficulty() + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getCategory().equals("Any% OOB")||userRun.getCategory().equals("P% OOB")||userRun.getCategory().equals("NoMo OOB"))
                        {
                            generatedString = generatedString + "<img src='OutOfBounds.png' alt='Out Of Bounds'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getComment() != null)
                        {
                            generatedString = generatedString + "<img src='Comment.png' alt='Contains Comment'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getVideo() != null)
                        {
                            generatedString = generatedString + "<img src='Video.png' alt='Contains Video'>";
                        }
                    generatedString = generatedString + "</div>";
                    if(userRun.getCategory().substring(0,1).equals("A"))
                    {
                        generatedString = generatedString + "<div class='runTableContainerLevel'>Any%</div>";
                    }
                    else if(userRun.getCategory().substring(0,1).equals("P"))
                    {
                        generatedString = generatedString + "<div class='runTableContainerLevel' style='color:gold;'>P%</div>";
                    }
                    else if(userRun.getCategory().substring(0,1).equals("N"))
                    {
                        generatedString = generatedString + "<div class='runTableContainerLevel' style='color:lime'>NoMo</div>";
                    }
                generatedString = generatedString + "</div>";
            generatedString = generatedString + "</a>";
            counter = counter + 1;
        }
    }

        return generatedString;
    }


    public String GetAllRunstemp(String selectedCategory)
    {
        String generatedString = "";
        int counter = 1;

        JDBCConnection jdbc = new JDBCConnection();
        NumberConversion numbs = new NumberConversion();

        ArrayList<AdvancedRun> aRun = jdbc.getComplexRuns(selectedCategory,"all");

        for (AdvancedRun userRun : aRun)
        {
            generatedString = generatedString + "<a href='/userRuns.html?runId=" + userRun.getRunID() + "&prev=All'>";
                generatedString = generatedString + "<div class='runTableContainerInstance'>";
                    generatedString = generatedString + "<div><div></div><div></div><div></div>" + counter + "</div>";
                    generatedString = generatedString + "<div><img src='" + userRun.getPfp() + "'></div>";
                    generatedString = generatedString + "<div>" + userRun.getName() + "</div>";
                    generatedString = generatedString + "<div>" + numbs.ToDuration(userRun.getTime()) + "</div>";
                    generatedString = generatedString + "<div>" + userRun.getDifficulty() + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getCategory().equals("Any% OOB")||userRun.getCategory().equals("P% OOB")||userRun.getCategory().equals("NoMo OOB"))
                        {
                            generatedString = generatedString + "<img src='OutOfBounds.png' alt='Out Of Bounds'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getComment() != null)
                        {
                            generatedString = generatedString + "<img src='Comment.png' alt='Contains Comment'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getVideo() != null)
                        {
                            generatedString = generatedString + "<img src='Video.png' alt='Contains Video'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div class='runTableContainerLevel'>" + userRun.getLevelCode() + "</div>";
                generatedString = generatedString + "</div>";
            generatedString = generatedString + "</a>";
            counter = counter + 1;
        }

        return generatedString;
    }

    public String GetLevelRuns(String selectedCategory, String level)
    {
        String generatedString = "";
        int counter = 1;

        JDBCConnection jdbc = new JDBCConnection();
        NumberConversion numbs = new NumberConversion();

        ArrayList<AdvancedRun> aRun = jdbc.getComplexRuns(selectedCategory,level);

        for (AdvancedRun userRun : aRun)
        {
            generatedString = generatedString + "<a href='/userRuns.html?runId=" + userRun.getRunID() + "&prev=" + level + "'>";
                generatedString = generatedString + "<div class='runTableContainerInstance'>";
                    generatedString = generatedString + "<div><div></div><div></div><div></div>" + counter + "</div>";
                    generatedString = generatedString + "<div><img src='" + userRun.getPfp() + "'></div>";
                    generatedString = generatedString + "<div>" + userRun.getName() + "</div>";
                    generatedString = generatedString + "<div>" + numbs.ToDuration(userRun.getTime()) + "</div>";
                    generatedString = generatedString + "<div>" + userRun.getDifficulty() + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getCategory().equals("Any% OOB")||userRun.getCategory().equals("P% OOB")||userRun.getCategory().equals("NoMo OOB"))
                        {
                            generatedString = generatedString + "<img src='OutOfBounds.png' alt='Out Of Bounds'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getComment() != null)
                        {
                            generatedString = generatedString + "<img src='Comment.png' alt='Contains Comment'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "<div>";
                        if(userRun.getVideo() != null)
                        {
                            generatedString = generatedString + "<img src='Video.png' alt='Contains Video'>";
                        }
                    generatedString = generatedString + "</div>";
                    generatedString = generatedString + "</div>";
                generatedString = generatedString + "</a>";
            counter = counter + 1;
        }

        return generatedString;
    }

    public String getRunners(int runnerId)
    {
        String html = "";

        JDBCConnection jdbc = new JDBCConnection();
        NumberConversion numbs = new NumberConversion();

        RunnerDetails runner = jdbc.getUserDetails(runnerId);

        // ArrayList<AdvancedRun> uRuns = jdbc.getUserBosses(runnerId);

    
        html = html + "<a href='./userDisplay.html?user=" + runnerId + "'>";
        html = html + "    <div style='width:20vw' class='playerBox'>";
        html = html + "        <h2>" + runner.getName() + "</h2>";
        html = html + "        <img src='" + runner.getPfp() + "' width='40%'>";
        html = html + "        <h3>Boss Times</h3>";
        html = html + "        <div class='flexBox'>";
        html = html + "            <div>";
        html = html + "                <p>1-4: 01:03.078</p>";
        html = html + "                <p>2-4: 01:03.078</p>";
        html = html + "                <p>3-2: 01:03.078</p>";
        html = html + "                <p>4-4: 01:03.078</p>";
        html = html + "            </div>";
        html = html + "            <div>";
        html = html + "                <p>5-4: 01:03.078</p>";
        html = html + "                <p>6-2: 01:03.078</p>";
        html = html + "                <p>7-4: 01:03.078</p>";
        html = html + "                <p>p-1: 01:03.078</p>";
        html = html + "            </div>";
        html = html + "        </div>";
        html = html + "    </div>";
        html = html + "</a>";
            
        return html;
    }

}