package helper;

import org.eclipse.jetty.server.Authentication.User;

/**
 * Class represeting a Country from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class BasicRun {
   
   private String Category;
   private String Name;
   private String UserProfile;
   private String Time;
   private String Difficulty;
   private String DifficultyDescription;

   /**
    * Create a Country and set the fields
    */
   public BasicRun(String category, String name, String time, String difficulty, String difficultyDescription, String userprofile) {
      this.Category = category;
      this.Name = name;
      this.UserProfile = userprofile;
      this.Time = time;
      this.Difficulty = difficulty;
      this.DifficultyDescription = difficultyDescription;
   }

   public String getCategory() {
      return Category;
   }
   
   public String getName() {
      return Name;
   }

   public String getPfp() {
      return UserProfile;
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
}
