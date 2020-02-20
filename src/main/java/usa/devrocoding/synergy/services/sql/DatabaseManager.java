package usa.devrocoding.synergy.services.sql;

import lombok.Getter;

import usa.devrocoding.synergy.assets.Cache;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.services.SQLService;
import usa.devrocoding.synergy.spigot.user.object.UserExperience;

import java.sql.*;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
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
            try{
                Class.forName("com.mysql.jdbc.Driver");
            }catch (ClassNotFoundException e){
                Synergy.error("We can't find a SQL server on this machine!", "Install one first in order to use MySQL.");
                return false;
            }

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

    public void loadDefaultTables(){
        // Generate Tables
        new TableBuilder("users", this)
                .addColumn("uuid", SQLDataType.VARCHAR, 300,false, SQLDefaultType.NO_DEFAULT, true)
                .addColumn("name", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("rank", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("user_experience", SQLDataType.VARCHAR, 100,false, SQLDefaultType.CUSTOM.setCustom(UserExperience.NOOB.toString().toUpperCase()), false)
                .addColumn("xp", SQLDataType.DOUBLE, -1,true, SQLDefaultType.CUSTOM.setCustom(0), false)
                .execute();
        new TableBuilder("user_achievements", this)
                .addColumn("uuid", SQLDataType.VARCHAR, 300,false, SQLDefaultType.NO_DEFAULT, true)
                .addColumn("achievement", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("achieved_on", SQLDataType.DATE, -1, false, SQLDefaultType.NO_DEFAULT, false)
                .execute();
        new TableBuilder("punishments", this)
                .addColumn("uuid", SQLDataType.VARCHAR, 300,false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("type", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("category", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("level", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("issued", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("till", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("punisher", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("active", SQLDataType.BIT, -1,false, SQLDefaultType.NO_DEFAULT, false)
                .execute();
    }

    public boolean update(String table, Map<String, Object> data, String where){
        HashMap<Integer, Object> indexed = new HashMap<>();
        try {
            connect();
            StringBuilder query = new StringBuilder("UPDATE synergy_"+table+" SET ");
            StringBuilder whereQuery = new StringBuilder(" WHERE "+where);

            int a=1;
            for(String key : data.keySet()){
                if (a>1) query.append(", ");
                Object value = data.get(key);
                query.append(key+"=?");

                indexed.put(a, value);
                a++;
            }

            query.append(whereQuery.toString());

//            Synergy.debug(query.toString());
            PreparedStatement preparedStatement = getConnection().prepareStatement(query.toString());

            for(Integer index : indexed.keySet()){
                Object value = indexed.get(index);

                preparedStatement.setObject(index, value);
            }

            preparedStatement.executeUpdate();
            disconnect();
            return true;
        }catch (SQLException e){
            Synergy.warn("Can't execute update statement. " + e.getMessage());
            return false;
        }
    }

    public ResultSet getResults(String table, String where, Map<Integer, Object> data) throws SQLException {
        ResultSet resultSet = getResults("synergy", table, where, data);
        return resultSet;
    }

    public ResultSet getResults(String tablePrefix, String table, String where, Map<Integer, Object> data) throws SQLException {
        connect();

        StringBuilder query = new StringBuilder(
                "SELECT * FROM "+(tablePrefix==null?"":tablePrefix+"_")+table+(where != null ? (" WHERE "+where) : "")
        );

//        Synergy.debug(query.toString());

        PreparedStatement statement = getConnection().prepareStatement(
            query.toString()
        );

        if (where != null) {
            for (int b : data.keySet()) {
                Object object = data.get(b);
                statement.setObject(b, object);
            }
        }

        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public void executeQuery(String query) {
        try {
            connect();
        }catch (SQLException e){
            Synergy.warn("Can't connect again... " + e.getMessage());
        }
        try {
            getConnection().prepareStatement(query).execute();
            disconnect();
        }catch (SQLException e){
            Synergy.warn("Can't executeQuery statement. " + e.getMessage());
        }
    }

    public boolean insert(String table, Map<String, Object> data){
        return execute("INSERT INTO", table, data, true);
    }

    public boolean execute(String prefix, String table, Map<String, Object> data, boolean insert) {
        HashMap<Integer, Object> indexed = new HashMap<>();
        try {
            connect();
            StringBuilder query = new StringBuilder(prefix+" synergy_"+table+" ("),
            values = new StringBuilder(") VALUES(");

            int a=1;
            for(String key : data.keySet()){
                if (a>1) {
                    query.append(", ");
                    values.append(", ");
                }
                query.append("`"+key+"`");
                values.append('?');

                indexed.put(a, data.get(key));
                a++;
            }

            values.append(")");
            query.append(values.toString());

            PreparedStatement preparedStatement = getConnection().prepareStatement(query.toString());

            for(Integer index : indexed.keySet()){
                Object value = indexed.get(index);

                preparedStatement.setObject(index, value);
            }

            preparedStatement.executeUpdate();
            disconnect();
            return true;
        }catch (SQLException e){
            Synergy.warn("Can't execute statement. " + e.getMessage());
        }
        return false;
    }

}
