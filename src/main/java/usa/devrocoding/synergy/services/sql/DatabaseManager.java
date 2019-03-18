package usa.devrocoding.synergy.services.sql;

import lombok.Getter;

import usa.devrocoding.synergy.assets.Cache;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.services.SQLService;

import java.sql.*;
import java.util.Arrays;
import java.util.Map;

public class DatabaseManager {

    @Getter
    private Connection connection;
    @Getter
    private SQLService sqlService;

    public DatabaseManager(SQLService service){
        this.sqlService = service;
    }

    public boolean connect() throws SQLException{
        if (this.connection != null && !connection.isClosed()){
            return false;
        }
        if (getSqlService().isIniatialized()) {

//            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + getSqlService().getHost() + ":" + getSqlService().getPort()
                    + "/" + getSqlService().getDatabase(), getSqlService().getUsername(), getSqlService().getPassword());
            return true;
        }else{
            throw new SQLException("SQL Information is wrong or empty! Check 'Settings.yml'");
        }
    }

    public void disconnect() throws SQLException {
        if (!this.connection.isClosed()) {
            this.connection.close();
        }
    }

    public boolean update(String table, Map<String, Object> data, String where){
        StringBuilder query = new StringBuilder("UPDATE synergy_"+table+" SET ");
        StringBuilder whereQuery = new StringBuilder(" WHERE "+where);

        int a=0;
        for(String key : data.keySet()){
            if (a>0) query.append(", ");
            Object value = data.get(key);
            if (value instanceof String){
                query.append(key+" = '"+value+"'");
            }else{
                query.append(key+" = "+value.toString());
            }
            a++;
        }

        query.append(whereQuery.toString());

        try {
            getConnection().createStatement().executeUpdate(query.toString());
            return true;
        }catch (SQLException e){
            Synergy.warn("Can't execute statement. " + e.getMessage());
            return false;
        }
    }

    public ResultSet getResults(String query) throws SQLException {
        connect();
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet;
    }

    public void executeQuery(String query) {
        try {
            connect();
        }catch (SQLException e){
            Synergy.warn("Can't connect again... " + e.getMessage());
        }
        try {
            getConnection().createStatement().execute(query);
        }catch (SQLException e){
            Synergy.warn("Can't execute statement. " + e.getMessage());
        }
    }

    public boolean execute(String table, Map<String, Object> data) {
        StringBuilder query = new StringBuilder("INSERT INTO synergy_"+table+" (");
        StringBuilder values = new StringBuilder(") VALUES(");

        int a=0;
        for(String key : data.keySet()){
            if (a>0) {
                values.append(", ");
                query.append(", ");
            }
            Object value = data.get(key);
            query.append(key);
            if (value instanceof String){
                values.append("'"+value+"'");
            }else{
                values.append(value);
            }

            a++;
        }

        query.append(values.append(")").toString());

        try {
            connect();
        }catch (SQLException e){
            Synergy.warn("Can't connect again... " + e.getMessage());
            return false;
        }
        try {
            getConnection().createStatement().execute(query.toString());
            return true;
        }catch (SQLException e){
            Synergy.debug(query.toString());
            Synergy.warn("Can't execute statement. " + e.getMessage());
        }
        return false;
    }

}
