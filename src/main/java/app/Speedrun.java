package app;

/**
 * Class represeting a Country from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class Speedrun {

   //Runner Id
   private String Runner;
   //Category Name
   private String Category;
   //Time
   private String Time;
   //Video Link
   private String Video;
   //Runner Comment
   private String Comment;
   //Level code (eg. 1-1)
   private String Level;
   //Difficulty code (1,2,3,4,5)
   private String Difficulty;
   //Exit (Normal, Secret)
   private String Exit;

   private int SpeedrunType;



//A comment and A video
public Speedrun(String runner, String category, String time, String video, String comment, String level, String difficulties, String exit, int Type) {
   this.Runner = runner;
   this.Category = category;
   this.Time = time;
   this.Video = video;
   this.Comment = comment;
   this.Level = level;
   this.Difficulty = difficulties;
   this.Exit = exit;
   this.SpeedrunType = Type;
 }

   public String getRunnerId() {
      return Runner;
   }

   public String getCategory() {
      return Category;
   }

   public String getTime() {
      return Time;
   }

   public String getVideo() {
      return Video;
   }

   public String getComment() {
      return Comment;
   }

   public String getLevel() {
      return Level;
   }

   public String getDifficulty() {
      return Difficulty;
   }

   public String getExit() {
      return Exit;
   }

   public int getType() {
      return SpeedrunType;
   }
}
