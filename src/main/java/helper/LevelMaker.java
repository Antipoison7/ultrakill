package helper;

import app.JDBCConnection;
import app.LevelTemplate;

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
                            <button class="levelButton" onclick="">ANY%</button>
                            <button class="levelButton" onclick="">P%</button>
                        </div>
                        <div class="levelContainer" id='""";
                        generatedCode = generatedCode + thisLevel.getLevelCode() + "Any";
                        generatedCode = generatedCode + """
                        '>
                        
                            <div class="levelScoreInstance">
                                <div class="levelUserPfp"><img src="Pfp/Connor.jpg"></div>
                                <div class="levelUserName"><p>Connor</p></div>
                                <div class="levelUserDiff"><p>Brutal</p></div>
                                <div class="levelUserTime"><p>00:12.304</p></div>
                            </div>

                        </div>

                        <div class="levelContainer" id='""";
                        generatedCode = generatedCode + thisLevel.getLevelCode() + "P";
                        generatedCode = generatedCode + """
                        '>
                        
                            <div class="levelScoreInstance">
                                <div class="levelUserPfp"><img src="Pfp/Connor.jpg"></div>
                                <div class="levelUserName"><p>Connor</p></div>
                                <div class="levelUserDiff"><p>Brutal</p></div>
                                <div class="levelUserTime"><p>00:12.304</p></div>
                            </div>

                        </div>
                    </div>
                </div>
                """;

        return generatedCode;
    }
}