package usa.devrocoding.synergy.services.sql;

import lombok.Getter;
import lombok.Setter;

public enum SQLDataType {

    INTEGER("INTEGER"),
    SMALLINT("SMALLINT"),
    TINYINT("TINYINT"),
    MEDIUMINT("MEDIUMINT"),
    BIGINT("BIGINT"),
    DECIMAL("DECMINAL"),
    NUMERIC("NUMERIC"),
    FLOAT("FLOAT"),
    DOUBLE("DOUBLE"),
    BIT("BIT"),
    DATE("DATE"),
    DATETIME("DATETIME"),
    TIMESTAMP("TIMESTAMP"),
    TIME("TIME"),
    YEAR("YEAR"),
    CHAR("CHAR"),
    VARCHAR("VARCHAR"),
    BINARY("BINARY"),
    VARBINARY("VARBINARY"),
    BLOB("BLOB"),
    TEXT("TEXT"),
    ENUM("ENUM"),
    SET("SET"),
    ;

    @Getter
    private String sqlName;
    @Getter
    private int value;

    SQLDataType(String sqlName){
        this.sqlName = sqlName;
    }

    public SQLDataType setValue(int value){
        this.value = value;
        return this;
    }

}
