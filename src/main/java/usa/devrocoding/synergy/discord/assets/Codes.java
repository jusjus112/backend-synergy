package usa.devrocoding.synergy.discord.assets;

public class Codes {

  // MAIN DISCORD
  public static String MIRAGE_PRISONS_DISCORD_ID = "628005578878353462";
  public static String SUPPORT_CHANNEL_ID = "628166680228397076";
  public static String TICKET_CATEGORY_ID = "770374872978292737";
  public static String DISCORD_SUGGESTIONS_CHANNEL_ID = "630423204468490240";
  public static String PRISONER_ROLE_ID = "708890429625729045";
  public static String BETA_ROLE_ID = "781470466434924564";
  public static String CHANGELOG_ROLE_ID = "708889907514441731";

  // STAFF DISCORD
  public static String MIRAGE_PRISONS_STAFF_DISCORD_ID = "732884672333217931";
  public static String STAFF_DISCORD_SUGGESTIONS_CHANNEL_ID = "732884672542933000";
  public static String STAFF_DISCORD_ERROR_CHANNEL_ID = "769987717290262559";
  public static String STAFF_NO_ROLE_ID = "732884672333217932";

  // ICONS
  public static String MIRAGE_PRISONS_ICON_ID = "mirage:726934665134604340";
  public static String YES_ICON_ID = "yes:771334946781397007";
  public static String NO_ICON_ID = "no:771335012528160769";

  public static Long getIDWithoutName(String id){
    return Long.parseLong(id.split(":")[1]);
  }

}
