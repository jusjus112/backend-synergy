package usa.devrocoding.synergy.services.sql;

import lombok.Getter;

public class TableBuilder {

    @Getter
    private String tableName, query;

    public TableBuilder(String tableName){
        this.tableName = tableName;
        this.query = "CREATE TABLE IF NOT EXISTS "+tableName;
    }

    public TableBuilder addColumn(String name, SQLDataType type, int amount, boolean allowNull, SQLDefaultType defaultType){
        return this;
    }

    public void finialize(){
        //TODO Excecute query
    }

}
