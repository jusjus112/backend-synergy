package usa.devrocoding.synergy.services.clients;

import com.github.steveice10.mc.auth.service.AuthenticationService;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.SubProtocol;
import com.github.steveice10.mc.protocol.data.message.Message;
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.mc.protocol.data.status.handler.ServerPingTimeHandler;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.ProxyInfo;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;

import java.net.Proxy;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ProtocolClient {

  // https://github.com/Steveice10/MCProtocolLib

  private final static int clients = 1;
  private final static int limitPerJoin = 1;
  private final static int threadLimit = 1000;

//  private static final String HOST = "127.0.0.1";
  private static final String HOST = "51.195.148.172";
  private static final String register = "";
  private static final int PORT = 25565;
  private static final Proxy AUTH_PROXY = Proxy.NO_PROXY;
  private static final ProxyInfo PROXY = null;

  // Insert if in need of premium account
  private static final String USERNAME = null;
  private static final String PASSWORD = null;

  public static void main(String[] args) {
    int number = 0;
    for (int i=1;i<=clients;i++){
      String name = getRandom();
      login(name);
      number++;
      if (number >= limitPerJoin){
        try{
          Thread.sleep(threadLimit);
          number = 0;
        }catch (Exception e){
          e.printStackTrace();
        }
      }
    }
  }

  public static String getRandom(){
    int length = ThreadLocalRandom.current().nextInt(5, 15);
    String alp = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < length; i++) {
      builder.append(alp.toCharArray()[ThreadLocalRandom.current().nextInt(alp.length())]);
    }

    return builder.toString();
  }

  private static void status(){
    SessionService sessionService = new SessionService();
    sessionService.setProxy(AUTH_PROXY);

    MinecraftProtocol protocol = new MinecraftProtocol(SubProtocol.STATUS);
    Client client = new Client(HOST, PORT, protocol, new TcpSessionFactory(PROXY));
    client.getSession().setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY, new ServerInfoHandler() {
      @Override
      public void handle(Session session, ServerStatusInfo info) {
        System.out.println("Version: " + info.getVersionInfo().getVersionName() + ", " + info.getVersionInfo().getProtocolVersion());
        System.out.println("Player Count: " + info.getPlayerInfo().getOnlinePlayers() + " / " + info.getPlayerInfo().getMaxPlayers());
        System.out.println("Players: " + Arrays.toString(info.getPlayerInfo().getPlayers()));
        System.out.println("Description: " + info.getDescription());
        System.out.println("Icon: " + info.getIconPng());
      }
    });

    client.getSession().setFlag(MinecraftConstants.SERVER_PING_TIME_HANDLER_KEY, new ServerPingTimeHandler() {
      @Override
      public void handle(Session session, long pingTime) {
        System.out.println("Server ping took " + pingTime + "ms");
      }
    });

    client.getSession().connect();
    while(client.getSession().isConnected()) {
      try {
        Thread.sleep(5);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private static void login(String username) {
    MinecraftProtocol protocol = null;
    if(PASSWORD != null) {
      AuthenticationService authService = new AuthenticationService();
      authService.setUsername(USERNAME);
      authService.setPassword(PASSWORD);
      authService.setProxy(AUTH_PROXY);
//        authService.login();

      // Can also use "new MinecraftProtocol(USERNAME, PASSWORD)"
      // if you don't need a proxy or any other customizations.
      protocol = new MinecraftProtocol(String.valueOf(authService));
      System.out.println("Successfully authenticated user.");

//      try {
//        AuthenticationResponse authResponse = OpenMCAuthenticator.authenticate("username", "password");
//        String authToken = authResponse.getAccessToken();
//
//        AuthenticationService authService = new AuthenticationService(authToken);
//        authService.setUsername(USERNAME);
//        authService.setPassword(PASSWORD);
//        authService.setProxy(AUTH_PROXY);
//        authService.login();
//
//        System.out.println("LOGGED IN");
//      } catch (RequestException e) {
//        System.out.println("Bad username or password");
//      } catch (AuthenticationUnavailableException e){
//        System.out.println("Authentication servers unavailable");
//      } catch (com.github.steveice10.mc.auth.exception.request.RequestException e) {
//        e.printStackTrace();
//      }
    } else {
      username = username.replaceAll("-", "_"); // - is not allowed
      protocol = new MinecraftProtocol(username);
    }

    SessionService sessionService = new SessionService();
    sessionService.setProxy(AUTH_PROXY);

    Client client = new Client(HOST, PORT, protocol, new TcpSessionFactory(PROXY));
    client.getSession().setFlag(MinecraftConstants.GAME_VERSION, MinecraftConstants.PROTOCOL_VERSION);
    client.getSession().addListener(new SessionAdapter() {
      @Override
      public void packetReceived(PacketReceivedEvent event) {
        if(event.getPacket() instanceof ServerJoinGamePacket) {
          event.getSession().send(new ClientChatPacket(register));
//          event.getSession().send(new ClientPlayerPositionPacket(true, 2, 0, 2));
//          for (int i = 0; i < 1000; i++) {
//            event.getSession().send(new ClientChatPacket("Yerravia is gay"));
//          }
        } else if(event.getPacket() instanceof ServerChatPacket) {
          Message message = event.<ServerChatPacket>getPacket().getMessage();
//          System.out.println("Received Message: " + message);
//          event.getSession().disconnect("Finished");
        }
      }

      @Override
      public void disconnected(DisconnectedEvent event) {
        System.out.println("Disconnected: " + event.getReason());
        if(event.getCause() != null) {
          event.getCause().printStackTrace();
        }
      }
    });

    client.getSession().connect();
  }

}
