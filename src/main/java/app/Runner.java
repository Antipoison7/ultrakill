package app;

/**
 * Class represeting a Country from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class Runner {

   //Runner Id
   private String Runner;
   //Category Name
   private String DisplayName;
   //Time
   private String SteamID;

   private int RunnerType;



//A comment and A video
public Runner(String runner, String display, String steamID, int type) {
   this.Runner = runner;
   this.DisplayName = display;
   this.SteamID = steamID;
   this.RunnerType = type;

 }

   public String getRunnerName() {
      return Runner;
   }

   public String getRunnerDisplayName() {
      return DisplayName;
   }

   public String getSteamID() {
      return SteamID;
   }

   public int getRunnerType() {
      return RunnerType;
   }
}
