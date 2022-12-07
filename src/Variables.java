import java.util.ArrayList;

public class Variables {
    //VARIABLES NAME
    String name;
    //VARIABLES TYPE [IN,OUT]
    String InOrOut;
    //CRISP VALUE
    int crispValue;
    //TO STORE THE VARIABLES RANGE [LOWER,UPPER]
    ArrayList<Integer> range = new ArrayList<>();
    //TO ADD TRIANGLE FIZZY SETS IF ITS A TRIANGLE
    ArrayList<TRI> triangles = new ArrayList<>();
    //ADD TRAPEZOIDAL FUZZY SETS IF ITS A TRAPEZOID
    ArrayList<TRAP> trapezoids = new ArrayList<>();
    String TriOrTrap;

}
