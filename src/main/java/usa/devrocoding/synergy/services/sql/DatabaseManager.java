package usa.devrocoding.synergy.services.sql;

import lombok.Getter;

import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.services.SQLService;

import java.sql.*;
import java.util.Arrays;

public class DatabaseManager {

    @Getter
    private Connection connection;
    @Getter
    private SQLService sqlService;

    public DatabaseManager(SQLService service){
        this.sqlService = service;
    }

    public void connect() throws SQLException{
        if (this.connection != null && !connection.isClosed()){
            return;
        }
        if (getSqlService().isIniatialized()) {

//            Class.forName("com.mysql.jdbc.Driver");

            this.connection = DriverManager.getConnection("jdbc:mysql://" + getSqlService().getHost() + ":" + getSqlService().getPort()
                    + "/" + getSqlService().getDatabase(), getSqlService().getUsername(), getSqlService().getPassword());
        }else{
            throw new SQLException("SQL Information is wrong or empty! Check 'Settings.yml'");
        }
    }

    public void disconnect() throws SQLException {
        if (!this.connection.isClosed()) {
            this.connection.close();
        }
    }

    public ResultSet getResults(String query) throws SQLException {
        connect();
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet;
    }

    public void execute(String... queries) {
        try {
            connect();
        }catch (SQLException e){
            Synergy.warn("Can't connect again... " + e.getMessage());
        }
        Arrays.stream(queries).forEach(s -> {
            try {
                getConnection().createStatement().executeUpdate(s);
            }catch (SQLException e){
                Synergy.warn("Can't execute statement. " + e.getMessage());
            }
        });
    }

}
