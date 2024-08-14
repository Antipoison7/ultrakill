package app;

/**
 * Class represeting a Country from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class AdvancedRun {
   
   private int RunID;
   private String UserProfile;
   private String Name;
   private String Time;
   private String Difficulty;
   private String DifficultyDescription;
   private String Category;
   private String Comment;
   private String Video;
   private String LevelCode;
   private String LevelName;
   private String Exit;

   /**
    * Create a Country and set the fields
    */
   public AdvancedRun(int Runid, String userprofile, String name, String time, String difficulty, String difficultyDescription, String category, String comment, String video, String levelcode) {
      this.RunID = Runid;
      this.UserProfile = userprofile;
      this.Name = name;
      this.Time = time;
      this.Difficulty = difficulty;
      this.DifficultyDescription = difficultyDescription;
      this.Category = category;
      this.Comment = comment;
      this.Video = video;
      this.LevelCode = levelcode;
   }

   public AdvancedRun(int Runid, String userprofile, String name, String time, String difficulty, String difficultyDescription, String category, String comment, String video, String levelcode, String levelname, String exit) {
      this.RunID = Runid;
      this.UserProfile = userprofile;
      this.Name = name;
      this.Time = time;
      this.Difficulty = difficulty;
      this.DifficultyDescription = difficultyDescription;
      this.Category = category;
      this.Comment = comment;
      this.Video = video;
      this.LevelCode = levelcode;
      this.LevelName = levelname;
      this.Exit = exit;
   }

   public int getRunID(){ 
      return RunID; 
   }
   
   public String getPfp() {
      return UserProfile;
   }

   public String getName() {
      return Name;
   }

   public String getTime() {
      return Time;
   }

   public String getDifficulty() {
      return Difficulty;
   }
   
   public String getDifficultyDescription() {
      return DifficultyDescription;
   }

   public String getCategory() {
      return Category;
   }

   public String getComment() {
      return Comment;
   }

   public String getVideo() {
      return Video;
   }

   public String getLevelCode() {
      return LevelCode;
   }

   public String getLevelName() {
      return LevelName;
   }

   public String getExit() {
      return Exit;
   }
   
}
