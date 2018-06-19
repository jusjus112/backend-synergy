package usa.devrocoding.synergy.services.sql;

import lombok.Getter;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;

public class TableBuilder {

    private String tableName, query, query_update = "", specs;
    private int columns;
    private DatabaseManager databaseManager;

    public TableBuilder(String tableName){
        this.tableName = tableName;
        this.databaseManager = Core.getPlugin().getDatabaseManager();

        this.query = "CREATE TABLE IF NOT EXISTS "+tableName+" (";
    }

    public TableBuilder addColumn(String name, SQLDataType type, int amount, boolean allowNull, SQLDefaultType defaultType, boolean primary){

        {
            specs = name + " " + type;
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
            query_update += "IF COL_LENGTH('"+this.databaseManager.getSqlService().getDatabase()+"."+tableName+"', '"+name+"') IS NULL " +
                    "BEGIN " +
                    "ALTER TABLE " + tableName + " ADD " + specs;

            query_update += " END;";
        }

        return this;
    }

    public TableBuilder customColumn(String query){
        return this;
    }

    public void execute(){
        query += ")";

//        Synergy.debug(query, query_update);

        this.databaseManager.execute(query);
    }

}
