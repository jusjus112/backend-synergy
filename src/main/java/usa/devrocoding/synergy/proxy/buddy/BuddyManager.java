package usa.devrocoding.synergy.proxy.buddy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.buddy.command.CommandBuddy;
import usa.devrocoding.synergy.proxy.buddy.command.CommandMessage;
import usa.devrocoding.synergy.proxy.buddy.object.BuddyRequest;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;
import usa.devrocoding.synergy.services.sql.UtilSQL;

@Getter
public class BuddyManager extends ProxyModule {

  private final Map<ProxyUser, List<BuddyRequest>> buddyRequests;
  private final Map<ProxyUser, List<UUID>> friendsToUpdate;

  public BuddyManager(Core plugin){
    super(plugin, "Buddy Manager", false);

    new TableBuilder("friends", getPlugin().getDatabaseManager())
        .addColumn("uuid", SQLDataType.BINARY, 16, false, SQLDefaultType.NO_DEFAULT, false)
        .addColumn("friend", SQLDataType.BINARY, 16, false, SQLDefaultType.NO_DEFAULT, false)
        .setConstraints("uuid", "friend")
        .execute();

    this.buddyRequests = Maps.newHashMap();
    this.friendsToUpdate = Maps.newHashMap();

    registerCommands(
        new CommandBuddy(),
        new CommandMessage()
    );
  }

  @Override
  public void reload() {

  }

  @Override
  public void deinit() {

  }

  public void addFriend(ProxyUser proxyUser, ProxyUser targetUser){
    List<UUID> friends = this.friendsToUpdate.getOrDefault(proxyUser, Lists.newArrayList());
    friends.add(targetUser.getUuid());

    this.friendsToUpdate.put(proxyUser, friends);

    proxyUser.getFriends().add(targetUser.getUuid());
  }

  public BuddyRequest getRequestByUser(ProxyUser proxyUser, ProxyUser targetUser){
    if (this.buddyRequests.containsKey(targetUser)){
      for(BuddyRequest buddyRequest : buddyRequests.get(targetUser)){
        if (buddyRequest.getTargetUser().getUuid().equals(proxyUser.getUuid())){
          return buddyRequest;
        }
      }
    }
    return null;
  }

  public void addBuddyRequest(BuddyRequest buddyRequest){
    List<BuddyRequest> buddyRequests = this.buddyRequests.getOrDefault(
        buddyRequest.getSenderUser(), Lists.newArrayList());
    buddyRequests.add(buddyRequest);
    this.buddyRequests.put(buddyRequest.getSenderUser(), buddyRequests);
  }

  public List<UUID> getFriendsForUser(UUID uuid){
    List<UUID> friends = Lists.newArrayList();
    try{
      ResultSet resultSet = getPlugin().getDatabaseManager().getResults(
          "friends", "uuid=?", new HashMap<Integer, Object>(){{
            put(1, UtilSQL.convertUniqueId(uuid));
          }});

      if (resultSet.next()){
        friends.add(UtilSQL.convertBinaryStream(resultSet.getBinaryStream("friend")));
      }
      return friends;
    }catch (SQLException e){
      e.printStackTrace();
    }
    return friends;
  }

  public void updateFriendsForUser(ProxyUser proxyUser){
    getPlugin().getProxy().getScheduler().runAsync(getPlugin(), () -> {
      this.friendsToUpdate.getOrDefault(proxyUser, Lists.newArrayList()).forEach(uuid -> {
        getPlugin().getDatabaseManager().insert("friends", new HashMap<String, Object>(){{
          put("uuid", UtilSQL.convertUniqueId(proxyUser.getUuid()));
          put("friend", UtilSQL.convertUniqueId(uuid));
        }});
      });
    });
  }

}
