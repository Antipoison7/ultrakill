package helper;

import app.AdvancedRun;
import app.JDBCConnection;
import app.LevelTemplate;

import java.util.ArrayList;
import java.util.Objects;

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

    public String GetAllRuns(String selectedCategory)
    {
        String generatedString = "";
        int counter = 1;

        JDBCConnection jdbc = new JDBCConnection();
        NumberConversion numbs = new NumberConversion();

        ArrayList<AdvancedRun> aRun = jdbc.getComplexRuns(selectedCategory,"all");

        for (AdvancedRun userRun : aRun)
        {
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
            counter = counter + 1;
        }

        return generatedString;
    }

    public String GetLevelRunsAll(String level)
    {
        String generatedString = "";
        int counter = 1;

        JDBCConnection jdbc = new JDBCConnection();
        NumberConversion numbs = new NumberConversion();

        ArrayList<AdvancedRun> aRun = jdbc.getComplexRunsAll(level);

        for (AdvancedRun userRun : aRun)
        {
            generatedString = generatedString + "<div class='runTableContainerInstanceAny'>";
                generatedString = generatedString + "<div><div></div><div></div><div></div>" + counter + "</div>";
                generatedString = generatedString + "<div><img src='" + userRun.getPfp() + "'></div>";
                generatedString = generatedString + "<div>" + userRun.getName() + "</div>";
                generatedString = generatedString + "<div>" + numbs.ToDuration(userRun.getTime()) + "</div>";
                generatedString = generatedString + "<div>" + userRun.getDifficulty() + "</div>";
                generatedString = generatedString + "<div>";
                    if(userRun.getCategory().substring(1,1).equals("A"))
                    {
                        generatedString = generatedString + "Any%";
                    }
                    else if(userRun.getCategory().substring(1,1).equals("P"))
                    {
                        generatedString = generatedString + "P%";
                    }
                    else if(userRun.getCategory().substring(1,1).equals("N"))
                    {
                        generatedString = generatedString + "NoMo";
                    }
                generatedString = generatedString + "</div>";
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
            counter = counter + 1;
        }

        return generatedString;
    }
}