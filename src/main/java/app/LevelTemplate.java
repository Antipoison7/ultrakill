package app;

/**
 * Class represeting a Country from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class LevelTemplate {

   private String LevelCode;
   private String LevelName;


public LevelTemplate(String levelcode, String levelname) {
   this.LevelCode = levelcode;
   this.LevelName = levelname;
 }

   public String getLevelCode() {
      return LevelCode;
   }

   public String getLevelName() {
      return LevelName;
   }
}
