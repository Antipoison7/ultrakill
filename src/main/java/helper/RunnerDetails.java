package helper;

import org.eclipse.jetty.server.Authentication.User;

/**
 * Class represeting a Country from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class RunnerDetails {
   
   private String RunnerId;
   private String Name;
   private String SteamId;
   private String DisplayName;
   private String ProfilePicture;

   /**
    * Create a Country and set the fields
    */
   public RunnerDetails(String runnerid, String name, String steamid, String displayname, String profilepicture) {
      this.RunnerId = runnerid;
      this.Name = name;
      this.SteamId = steamid;
      this.DisplayName = displayname;
      this.ProfilePicture = profilepicture;
   }

   
   public String getRunnerId() {
      return RunnerId;
   }

   public String getName() {
      return Name;
   }

   public String getSteamId() {
      return SteamId;
   }

   public String getDisplayName() {
      return DisplayName;
   }

   public String getPfp() {
      return ProfilePicture;
   }

}
