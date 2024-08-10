package helper;

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
}