package usa.devrocoding.synergy.spigot.utilities;

import java.lang.reflect.Field;

public class Test {

    int x;
    int y;

    public void getClassName() {
        String className = this.getClass().getSimpleName();
        System.out.println("Name:" + className);
    }

    public void getAttributes() {
        Field[] attributes = this.getClass().getDeclaredFields();
        for(int i = 0; i < attributes.length; i++) {
            System.out.println("Declared Fields" + attributes[i]);
        }
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.getClassName();
        t.getAttributes();
    }

}
