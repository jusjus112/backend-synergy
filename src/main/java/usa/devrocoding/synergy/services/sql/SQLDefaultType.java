package usa.devrocoding.synergy.services.sql;

import lombok.Getter;

public enum SQLDefaultType {

    NO_DEFAULT,
    AUTO_INCREMENT,
    NULL,
    CUSTOM;

    @Getter
    private Object[] defaultObject;

    SQLDefaultType(Object... defaultObject){
        this.defaultObject = defaultObject;
    }

    public SQLDefaultType setCustom(Object... defaultObject){
        this.defaultObject = defaultObject;
        return this;
    }

}
