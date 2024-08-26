package helper;

import app.JDBCConnection;
import app.LevelTemplate;

public class NumberConversion
{
    public Double ToSeconds(String TimeCode)
    {
        Double generatedTime = 0.0;

        Double minutes = Double.parseDouble(TimeCode.substring(0,2));
        Double secondsMs = Double.parseDouble(TimeCode.substring(3,9));

        System.out.println(minutes);
        System.out.println(secondsMs);

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

    public String ToDurationSpecial(String userSeconds)
    {
        Double seconds = Double.parseDouble(userSeconds);
        String durationString = "";

        String Minutes = Integer.toString((int)(seconds / 60));
        String Seconds = String.format("%06.3f",((double)(seconds % 60)));

        if(userSeconds.equals("0"))
        {
            durationString = "Not Complete";
        }
        else
        {
                if(Minutes.length() == 1)
            {
                durationString = "0" + Minutes;
            }
            else
            {
                durationString = Minutes;
            }

            durationString = durationString + ":" + Seconds;
        }
        

        return durationString;
    }

    public String ToYoutube(String ytURL)
    {
        YouTubeHelper youTubeHelper = new YouTubeHelper();
        String yts = "<iframe height = '100%' width = '100%' src='https://www.youtube.com/embed/" + youTubeHelper.extractVideoIdFromUrl(ytURL) + "' title='YouTube video player' frameborder='0' referrerpolicy='strict-origin-when-cross-origin' allowfullscreen></iframe>";

        return yts;
    }
}