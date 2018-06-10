package usa.devrocoding.synergy.services.redis;

public class ConnectionData {

    private String host;
    private int port;
    private boolean master;

    public ConnectionData(String host, int port, boolean master) {
        this.host = host;
        this.port = port;
        this.master = master;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public boolean isMaster() {
        return this.master;
    }
}
