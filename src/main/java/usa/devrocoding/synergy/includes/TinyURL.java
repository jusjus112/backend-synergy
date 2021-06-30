package usa.devrocoding.synergy.includes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@RequiredArgsConstructor
public class TinyURL {

  private final String url;

  public String generateURL(){
    try{
      String tinyUrl1 = "http://tinyurl.com/api-create.php?url=";
      String tinyUrlLookup = tinyUrl1 + url;
      BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(tinyUrlLookup).openStream()));
      return reader.readLine();
    }catch (Exception e){
      e.printStackTrace();
    }
    return ChatColor.RED +  "Something went wrong! Contact a dev!";
  }



}