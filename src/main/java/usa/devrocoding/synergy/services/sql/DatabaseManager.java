package usa.devrocoding.synergy.services.sql;

import lombok.Getter;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.services.SQLService;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;

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

    public void connect() throws SQLException, ClassNotFoundException{
        if (this.connection != null && !connection.isClosed()){
            return;
        }
        System.out.println("[Synergy] Connecting to SQL....");
        if (getSqlService().isIniatialized()) {

            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://" + getSqlService().getHost() + ":" + getSqlService().getPort()
                    + "/" + getSqlService().getDatabase(), getSqlService().getUsername(), getSqlService().getPassword());
            System.out.println("[Synergy] Connected to your SQL Service Provider");
        }else{
            throw new SQLException("SQL Information is wrong or empty! Check 'Settings.yml'");
        }
    }

    public void disconnect(){
        try{
            if (!this.connection.isClosed()) {
                this.connection.close();
            }
        }catch(SQLException e){
            Sam.getRobot().error("Connection cannot be closed", "Contact the server developer ASAP", e);
        }
    }

    public List<ResultSet> getResults(String query){
        List<ResultSet> results = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                results.add(resultSet);
            }
        }catch(SQLException e){
            Sam.getRobot().error("Statement cannot get result of query", "Contact the server developer ASAP", e);
        }
        return results;
    }

    public void execute(String... queries){
        Arrays.stream(queries).forEach(s -> {
            try {
                getConnection().createStatement().executeUpdate(s);
            }catch (SQLException e){
                Sam.getRobot().error("Statement cannot be executed", "Contact the server developer ASAP", e);
            }
        });
    }

}
