package codesver.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Lombok {
    private String name;
    private int age;

    public static void main(String[] args) {
        Lombok lombok = new Lombok();
        lombok.setName("codesver");
        lombok.setAge(25);
        System.out.println(lombok);
    }
}
