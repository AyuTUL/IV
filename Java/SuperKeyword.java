class Animal {
    public String status;

    public Animal(String status) {
        this.status = status;
    }

    public void printStatus() {
        System.out.println("Status = " + status);
    }

}

class WildAnimal extends Animal {
    public String name;

    public WildAnimal(String status, String name) {
        super(status);
        this.name = name;
    }

    public void printStatus() {
        super.printStatus();
        System.out.println("Name = " + name);
    }
}

class DomesticAnimal extends Animal {
    public String name;

    public DomesticAnimal(String status, String name) {
        super(status);
        this.name = name;
    }

    public void printStatus() {
        super.printStatus();
        System.out.println("Name = " + name);
    }
}

public class SuperKeyword {
    public static void main(String[] args) {
        WildAnimal w = new WildAnimal("alive", "Black Panther");
        DomesticAnimal d = new DomesticAnimal("dead", "Cat");
        w.printStatus();
        d.printStatus();
    }
}
