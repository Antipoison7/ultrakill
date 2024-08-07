package helper;

import app.JDBCConnection;
import app.LevelTemplate;

public class NumberConversion
{
    public Double ToSeconds(String TimeCode)
    {
        Double generatedTime = 0.0;

        Double minutes = Double.parseDouble(TimeCode.substring(1,2));
        Double secondsMs = Double.parseDouble(TimeCode.substring(3,9));

        generatedTime = (minutes * 60) + secondsMs;

        return generatedTime;
    }

    public String ToDuration(String userSeconds)
    {
        Double seconds = Double.parseDouble(userSeconds);
        String durationString = "";

        String Minutes = Integer.toString((int)(seconds / 60));
        String Seconds = String.format("%06.3f",((double)(seconds % 60)));

        if(Minutes.length() == 1)
        {
            durationString = "0" + Minutes;
        }
        else
        {
            durationString = Minutes;
        }

        durationString = durationString + ":" + Seconds;

        return durationString;
    }
}