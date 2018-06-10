package usa.devrocoding.synergy.services.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import usa.devrocoding.synergy.services.SQLService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    private HikariConfig config;
    private HikariDataSource dataSource;
    @Getter
    private SQLService sqlService;

    public DatabaseManager(SQLService service){
        this.sqlService = service;
    }

    public void connect() {
        try {
            System.out.println("Connecting to SQL database");
            this.config = new HikariConfig();
            this.config.setJdbcUrl("jdbc:mysql://" + getSqlService().getHost() + ":" + getSqlService().getPort() + "/" + getSqlService().getDatabase());
            this.config.setUsername(getSqlService().getUsername());
            this.config.setPassword(getSqlService().getPassword());
            this.config.setPoolName("SQLPool");
            this.config.setMaximumPoolSize(10);
            this.config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            this.config.addDataSourceProperty("autoReconnect", true);
            this.config.addDataSourceProperty("databaseName", getSqlService().getDatabase());

            this.dataSource = new HikariDataSource(this.config);

            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try {
                connection = dataSource.getConnection();
                System.out.println("[Synergy] Connected SQL database");
//                preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS settings(id VARCHAR(36) " +
//                        "PRIMARY KEY NOT NULL, owner VARCHAR(36) NOT NULL, level VARCHAR(5) NOT NULL, inventory VARCHAR(256) NOT NULL)");

                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.close();
                }

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
