package usa.devrocoding.synergy.services.sql;

import lombok.Getter;
import usa.devrocoding.synergy.services.SQLService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseManager {

    @Getter
    private Connection connection;
    @Getter
    private SQLService sqlService;

    public DatabaseManager(SQLService service){
        this.sqlService = service;
    }

    public void connect() {
        System.out.println("[Synergy] Connecting to SQL....");
        if (getSqlService().isIniatialized()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + getSqlService().getHost() + ":" + getSqlService().getPort()
                        + "/" + getSqlService().getDatabase(), getSqlService().getUsername(), getSqlService().getPassword());
                System.out.println("[Synergy] Connected to your SQL Service Provider");
            } catch (SQLException e) {
                System.out.println("[Synergy ERROR] Can't connect to SQL");
                System.out.println("[Synergy ERROR] " + e.getMessage());
            }
        }
    }

    public void disconnect(){
        try{
            if (!this.connection.isClosed()) {
                this.connection.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<ResultSet> getResults(String query){
        List<ResultSet> results = new ArrayList<>();
        try {
            ResultSet resultSet = getConnection().prepareStatement(query).getResultSet();
            while(resultSet.next()){
                results.add(resultSet);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return results;
    }

    public void updateStatement(String... queries){
        Arrays.stream(queries).forEach(s -> {
            try {
                getConnection().prepareStatement(s);
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }

}
