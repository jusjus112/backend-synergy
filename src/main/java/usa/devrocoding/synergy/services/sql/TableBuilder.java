package usa.devrocoding.synergy.services.sql;

import java.util.Arrays;
import usa.devrocoding.synergy.assets.Synergy;

import java.sql.SQLException;
import java.util.HashMap;

public class TableBuilder {

    private String tableName, query, query_update = "", specs;
    private int columns;
    private DatabaseManager databaseManager;

    public TableBuilder(String tableName, DatabaseManager databaseManager){
        this.tableName = tableName;
        this.databaseManager = databaseManager;

        this.query = "CREATE TABLE IF NOT EXISTS synergy_"+tableName+" (";
    }

    public TableBuilder addColumn(String name, SQLDataType type, int amount, boolean allowNull, SQLDefaultType defaultType, boolean primary){
        {
            specs = "`" + name + "` " + type;
            if (!(amount <= 0)) {
                specs += "(" + amount + ")";
            }
            if (!allowNull) {
                specs += " NOT NULL";
            }
            if (defaultType == SQLDefaultType.CUSTOM) {
                if (defaultType.getDefaultObject()[0] instanceof Enum || defaultType.getDefaultObject()[0] instanceof  String){
                    specs += " DEFAULT '" + defaultType.getDefaultObject()[0] + "'";
                }else {
                    specs += " DEFAULT " + defaultType.getDefaultObject()[0];
                }
            } else if (defaultType == SQLDefaultType.AUTO_INCREMENT) {
                specs += " AUTO_INCREMENT";
            } else if (defaultType == SQLDefaultType.NULL) {
                specs += " DEFAULT NULL";
            }
            if (primary) {
                specs += " PRIMARY KEY";
            }
        }

        {
            if (columns > 0) {
                query += ", ";
            }

            query += specs;

            columns++;
        }

        {

            try {
                if (!
                    this.databaseManager.getResults(null,
                            "information_schema.COLUMNS",
                            "COLUMN_NAME=? AND TABLE_NAME=? AND TABLE_SCHEMA=?",
                            new HashMap<Integer, Object>(){{
                                put(1, name);
                                put(2, "synergy_"+tableName);
                                put(3, databaseManager.getSqlService().getDatabase());
                            }}
                    ).next()) {
                    query_update += "ALTER TABLE synergy_"+this.tableName+" ADD "+specs+";";
                }
            }catch(SQLException e){
                e.printStackTrace();
                Synergy.error(e.getMessage());
            }
        }
        return this;
    }

    public TableBuilder setConstraints(String... columnName){
        StringBuilder constraint = new StringBuilder(", UNIQUE (");
        for (int i = 0; i < columnName.length; i++) {
            if (i>0){
                constraint.append(",");
            }
            constraint.append(columnName[i]);
        }
        this.query += constraint.toString() + ")";

        Synergy.debug(this.query + "");
        return this;
    }

    public void execute(){
        query += ")";
        this.databaseManager.execute(query);
        if (!(query_update.equals(""))){
            String[] queries = query_update.split(";");
            for(String query_u : queries) {
                if (!query_u.equals("")) {
                    this.databaseManager.executeUpdate(query_u);
                }
            }
        }
    }

}
