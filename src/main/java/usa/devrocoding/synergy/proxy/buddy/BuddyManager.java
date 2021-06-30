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
import usa.devrocoding.synergy.includes.Pair;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.buddy.command.CommandBuddy;
import usa.devrocoding.synergy.proxy.buddy.command.CommandMessage;
import usa.devrocoding.synergy.proxy.buddy.command.CommandReply;
import usa.devrocoding.synergy.proxy.buddy.object.BuddyRequest;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;
import usa.devrocoding.synergy.services.sql.UtilSQL;

@Getter
public class BuddyManager extends ProxyModule {

  private final Map<ProxyUser, List<BuddyRequest>> buddyRequests;
  private final Map<ProxyUser, List<Pair<UUID, String>>> friendsToUpdate;

  public BuddyManager(Core plugin){
    super(plugin, "Buddy Manager", false);

    new TableBuilder("friends", getPlugin().getDatabaseManager())
        .addColumn("uuid", SQLDataType.BINARY, 16, false, SQLDefaultType.NO_DEFAULT, false)
        .addColumn("friend", SQLDataType.BINARY, 16, false, SQLDefaultType.NO_DEFAULT, false)
        .addColumn("name", SQLDataType.VARCHAR, 100, false, SQLDefaultType.NO_DEFAULT, false)
        .setConstraints("uuid", "friend", "name")
        .execute();

    this.buddyRequests = Maps.newHashMap();
    this.friendsToUpdate = Maps.newHashMap();

    registerCommands(
        new CommandBuddy(),
        new CommandMessage(),
        new CommandReply()
    );
  }

  @Override
  public void reload() {

  }

  @Override
  public void deinit() {

  }

  public void privateMessage(ProxyUser proxyUser, ProxyUser targetUser, String message){
    targetUser.setLastMessageReceived(proxyUser);
    targetUser.getProxiedPlayer().sendMessage(
        "§d[Private] §c" + proxyUser.getProxiedPlayer().getName() + ": §f" + message
    );
    proxyUser.getProxiedPlayer().sendMessage(
        "§d[Private] §c" + proxyUser.getProxiedPlayer().getName() + ": §f" + message
    );
  }

  public void addFriend(ProxyUser proxyUser, ProxyUser targetUser){
    List<Pair<UUID, String>> friends = this.friendsToUpdate.getOrDefault(proxyUser, Lists.newArrayList());

    friends.add(new Pair<UUID, String>(){{
      setLeft(targetUser.getUuid());
      setRight(targetUser.getDisplayName());
    }});

    this.friendsToUpdate.put(proxyUser, friends);

    proxyUser.getFriends().add(new Pair<UUID, String>(){{
      setLeft(targetUser.getUuid());
      setRight(targetUser.getDisplayName());
    }});
  }

  public boolean removeFriend(ProxyUser proxyUser, String friendName){
    Pair<UUID, String> friend = proxyUser.getFriends().stream().filter(uuidStringPair ->
      uuidStringPair.getRight().contains(friendName)
    ).findFirst().orElse(null);

    if (friend != null){
      proxyUser.getFriends().remove(friend);

      ProxyUser targetUser = getPlugin().getUserManager().getUser(friend.getLeft());
      if (targetUser != null){
        targetUser.getFriends().stream().filter(uuidStringPair ->
            uuidStringPair.getLeft().equals(proxyUser.getUuid()))
              .findFirst().ifPresent(uuidStringPair ->
                targetUser.getFriends().remove(uuidStringPair));
      }

      getPlugin().getProxy().getScheduler().runAsync(getPlugin(), () -> {
        getPlugin().getDatabaseManager().remove("friends", new HashMap<String, Object>(){{
          put("uuid", UtilSQL.convertUniqueId(proxyUser.getUuid()));
          put("friend", UtilSQL.convertUniqueId(friend.getLeft()));
          put("name", friend.getRight());
        }});
        getPlugin().getDatabaseManager().remove("friends", new HashMap<String, Object>(){{
          put("uuid", UtilSQL.convertUniqueId(friend.getLeft()));
          put("friend", UtilSQL.convertUniqueId(proxyUser.getUuid()));
          put("name", proxyUser.getDisplayName());
        }});
      });
      return true;
    }
    return false;
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

  public List<Pair<UUID, String>> getFriendsForUser(UUID uuid){
    List<Pair<UUID, String>> friends = Lists.newArrayList();
    try{
      ResultSet resultSet = getPlugin().getDatabaseManager().getResults(
          "friends", "uuid=?", new HashMap<Integer, Object>(){{
            put(1, UtilSQL.convertUniqueId(uuid));
          }});

      if (resultSet.next()){
        friends.add(new Pair<UUID, String>(){{
          setLeft(UtilSQL.convertBinaryStream(resultSet.getBinaryStream("friend")));
          setRight(resultSet.getString("name"));
        }});
      }
      return friends;
    }catch (SQLException e){
      e.printStackTrace();
    }
    return friends;
  }

  public void updateFriendsForUser(ProxyUser proxyUser){
    getPlugin().getProxy().getScheduler().runAsync(getPlugin(), () -> {
      this.friendsToUpdate.getOrDefault(proxyUser, Lists.newArrayList()).forEach(uuidStringPair -> {
        getPlugin().getDatabaseManager().insert("friends", new HashMap<String, Object>(){{
          put("uuid", UtilSQL.convertUniqueId(proxyUser.getUuid()));
          put("friend", UtilSQL.convertUniqueId(uuidStringPair.getLeft()));
          put("name", uuidStringPair.getRight());
        }});
      });
    });
  }

}
