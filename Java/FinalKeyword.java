//final varaible,final class,final method
class Role{
    final String generalPassive="health regen";
    final String rolePassive;
    public Role(String rolePassive){
        this.rolePassive=rolePassive;
    }
}
class Tank extends Role{
    public Tank(String rolePassive)
    {
        super(rolePassive);
    }
}
public class FinalKeyword {
    public static void main(String[] args)
    {

    }
}
