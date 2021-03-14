package usa.devrocoding.synergy.services.sql;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;

import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.services.SQLService;
import usa.devrocoding.synergy.spigot.user.object.UserExperience;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Getter
public class DatabaseManager {

    /**
     * TODO: Disconnect connection after a while with no queries
     */

    private Connection connection;
    private final SQLService sqlService;

    public DatabaseManager(SQLService service){
        this.sqlService = service;
    }

    public boolean connect() throws SQLException {
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

            try{
                this.connection = DriverManager.getConnection("jdbc:mysql://" + getSqlService().getHost() + ":" + getSqlService().getPort()
                        + "/" + getSqlService().getDatabase() + "?autoReconnect=true&useUnicode=yes", getSqlService().getUsername(), getSqlService().getPassword());
            }catch (Exception e){
                throw new SQLException("Unable to connect, SQL server might be down..");
            }
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
            .addColumn("uuid", SQLDataType.BINARY, 16,false, SQLDefaultType.NO_DEFAULT, true)
            .addColumn("name", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("rank", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("user_experience", SQLDataType.VARCHAR, 100,false, SQLDefaultType.CUSTOM.setCustom(UserExperience.NOOB.toString().toUpperCase()), false)
            .addColumn("joined_on", SQLDataType.DATETIME, -1, true, SQLDefaultType.NO_DEFAULT, false)
            .execute();
        new TableBuilder("punishments", this)
            .addColumn("uuid", SQLDataType.BINARY, 16,false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("type", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("category", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("level", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("issued", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("till", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("punisher", SQLDataType.BINARY, 16,false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("active", SQLDataType.BIT, -1,false, SQLDefaultType.NO_DEFAULT, false)
            .execute();
    }

    public boolean update(String table, Map<String, Object> data, Map<String, Object> whereData){
        HashMap<Integer, Object> indexed = new HashMap<>();
        try {
            connect();
            StringBuilder query = new StringBuilder("UPDATE synergy_"+table+" SET ");

            data.remove("uuid");
            data.remove("id");

            final int[] a = {1};
            data.forEach((s, o) -> {
                if (a[0] >1) query.append(", ");
                query.append("`").append(s).append("`").append("=?");
                indexed.put(a[0], o);
                a[0]++;
            });

            query.append(" WHERE ");

            AtomicInteger i = new AtomicInteger();
            whereData.forEach((s, o) -> {
                if (i.get() > 0) query.append(" AND ");
                query.append("`").append(s).append("`").append("=?");
                indexed.put(a[0], o);
                a[0]++;
                i.getAndIncrement();
            });

            Synergy.debug("QUERY", query.toString());

            Synergy.debug(data + " = DATA");
            PreparedStatement preparedStatement = getConnection().prepareStatement(query.toString());

            for(Integer index : indexed.keySet()){
                Object value = indexed.get(index);

                if (value instanceof InputStream){
                    preparedStatement.setBinaryStream(index, (InputStream) value);
                    continue;
                }
                preparedStatement.setObject(index, value);
            }

            preparedStatement.executeUpdate();
//            disconnect();
            return true;
        }catch (SQLException e){
            Synergy.warn("Can't execute update statement. " + e.getMessage());
            e.printStackTrace();
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

                if (object instanceof InputStream){
                    statement.setBinaryStream(b, (InputStream) object);
                    continue;
                }
                statement.setObject(b, object);
            }
        }

        return statement.executeQuery();
    }

    public ResultSet executeQuery(String query) {
        try {
            connect();
        }catch (SQLException e){
            Synergy.warn("Can't connect again... " + e.getMessage());
        }
//        Synergy.debug(query);
        try {
            ResultSet resultSet = getConnection().prepareStatement(query).executeQuery();
//            disconnect();
            return resultSet;
        }catch (SQLException e){
//            e.printStackTrace();
            Synergy.warn("Can't executeQuery statement. " + e.getMessage());
        }
        return null;
    }

    public void execute(String query){
        try {
            connect();
        }catch (SQLException e){
            Synergy.warn("Can't connect again... " + e.getMessage());
        }
//        Synergy.debug(query);
        try {
            getConnection().prepareStatement(query).execute();
//            disconnect();
        }catch (SQLException e){
//            e.printStackTrace();
            Synergy.warn("Can't execute statement. " + e.getMessage());
        }
    }

    public void executeUpdate(String query){
        try {
            connect();
        }catch (SQLException e){
            Synergy.warn("Can't connect again... " + e.getMessage());
        }
        try {
            getConnection().prepareStatement(query).executeUpdate();
//            disconnect();
        }catch (SQLException e){
//            e.printStackTrace();
            Synergy.warn("Can't executeUpdate statement. " + e.getMessage());
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
                query.append("`").append(key).append("`");
                values.append('?');

                indexed.put(a, data.get(key));
                a++;
            }

            values.append(")");
            query.append(values.toString());

            Synergy.debug(query.toString());
            Synergy.debug(data + " = VALUES data");

            PreparedStatement preparedStatement = getConnection().prepareStatement(query.toString());

            for(Integer index : indexed.keySet()){
                Object value = indexed.get(index);

                if (value instanceof InputStream){
                    preparedStatement.setBinaryStream(index, (InputStream) value);
                    continue;
                }
                preparedStatement.setObject(index, value);
            }

            preparedStatement.executeUpdate();
//            disconnect();
            return true;
        }catch (SQLException e){
//            Synergy.warn("Can't execute statement. " + e.getMessage());
//            e.printStackTrace();
            return false;
        }
    }

}
