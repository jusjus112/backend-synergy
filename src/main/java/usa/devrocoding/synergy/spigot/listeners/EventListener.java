package usa.devrocoding.synergy.spigot.listeners;

import java.lang.reflect.ParameterizedType;

@SuppressWarnings("unchecked")
public abstract class EventListener<T> {

    public abstract void process(T event);

    public <G> boolean isType(Class<?> clazz) {
        Class<T> type = null;
        type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (clazz == null || type == null) {
            return false;
        }
        return clazz == type;
    }

}
