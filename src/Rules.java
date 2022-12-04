import java.util.ArrayList;

public class Rules {
    //FORMAT[IN_variable1 FuzzySet1 operator IN_variable2 FuzzySet2 => OUT_variable FuzzySet3]
    String IN_variable1;
    String IN_variable2;
    String OUT_variable;
    String FuzzySet1;
    String FuzzySet2;
    String FuzzySet3;
    String operator;
    //WHERE WE WILL TAKE ALL RULES LINE BY LINE AND BREAK THEM DOWN
    ArrayList<String> rules=new ArrayList<String>();
}
