package usa.devrocoding.synergy.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import usa.devrocoding.synergy.assets.Synergy;

public class SQLService {

    private static String HOST;
    public static String DATABASE_NAME;
    private static String USERNAME;
    private static String PASSWORD;
    private static Integer PORT;

    private static HikariDataSource dataSource1, dataSource2;

    public SQLService(String host, String databaseName, int port, String user, String password){
        HOST = host;
        DATABASE_NAME = databaseName;
        PORT = port;
        USERNAME = user;
        PASSWORD = password;
    }

    public static Connection connection() throws SQLException{
        return getDataSource().getConnection();
    }

    private static HikariDataSource getDataSource() {
        if (dataSource1 == null) {
            generateNewDataSource(true);
        }
        if (dataSource2 == null) {
            generateNewDataSource(false);
        }
        try{
            return dataSource1;
        }catch (Exception e){
            return dataSource2;
        }
    }

    private static void generateNewDataSource(boolean sourceOne) {
        HikariConfig hikariConfig = getConfig();
        if (sourceOne) {
            dataSource1 = new HikariDataSource(hikariConfig);
        }else{
            dataSource2 = new HikariDataSource(hikariConfig);
        }
    }

    private static HikariConfig getConfig(){
        String jdbcUrl = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE_NAME;
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(USERNAME);
        hikariConfig.setPassword(PASSWORD);
        hikariConfig.setMaximumPoolSize(25);
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setConnectionTimeout(1000);
        hikariConfig.setMaxLifetime(300000);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true" );
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250" );
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048" );

        return hikariConfig;
    }

}
