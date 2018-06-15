package usa.devrocoding.synergy.services;

import lombok.Getter;
import usa.devrocoding.synergy.services.sql.DatabaseManager;

public class SQLService {

    @Getter
    private String host, username, password, database;
    @Getter
    private int port;
    @Getter
    private boolean iniatialized;

    public SQLService(String host, String database, String username, String password, Integer port){
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;

        if (host != null && database != null && username != null)
            this.iniatialized = true;
    }

}
