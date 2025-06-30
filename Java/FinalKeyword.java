final class Map {
    final String name;

    public Map(String name) {
        this.name = name;
    }

    public void display() {
        System.out.println("Map = " + name);
    }
}

class Role {
    final String generalPassive = "Health regen";
    final String rolePassive;
    private String role;

    public Role(String role, String rolePassive) {
        this.role = role;
        this.rolePassive = rolePassive;
    }

    final void display() {
        System.out.println("General Passive = " + generalPassive);
        System.out.println(role + " Passive = " + rolePassive);
    }
}

class Character extends Role {
    private String name;

    public Character(String role, String rolePassive, String name) {
        super(role, rolePassive);
        this.name = name;
    }

    public void show() {
        super.display();
        System.out.println("Character = " + name);
    }
}

public class FinalKeyword {
    public static void main(String[] args) {
        Map m = new Map("Hanamura");
        Character c = new Character("Tank", "Reduced knockback", "Doomfist");
        m.display();
        c.show();
    }
}
