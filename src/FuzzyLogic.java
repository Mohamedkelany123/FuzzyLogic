import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class FuzzyLogic {
    public void parse() throws IOException {
        File file = new File("E:\\Kelany\\Cairo_Uni_2022-2023\\Semester 1\\Soft Computing\\input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        //PARSE VARIABLES
        ArrayList<Variables> variables=new ArrayList<Variables>();
        ArrayList<Rule> rules=new ArrayList<Rule>();
        String variableParser;
        variableParser=br.readLine();
        System.out.println(variableParser);
        if(variableParser.equals("Variables:"))
        {
            while(!(variableParser=br.readLine()).equals("x"))
            {
                Variables variable=new Variables();
                String[] arrOfStr = variableParser.split(" ");
                variable.name=arrOfStr[0];
                variable.type=arrOfStr[1];
                variable.range.add(Integer.valueOf(arrOfStr[2]));
                variable.range.add(Integer.valueOf(arrOfStr[3]));
                System.out.println("Variable Name:" + variable.name);
                System.out.println("Variable Type:" + variable.type);
                System.out.println("Variable Range[Minimum]:" + variable.range.get(0));
                System.out.println("Variable Range[Max]:" + variable.range.get(1));
                System.out.println("--------------------------");
                variables.add(variable);
            }
        }


        //PARSE FUZZYSETS
        variableParser=br.readLine();
        //System.out.println(variableParser);
        if (variableParser.equals("FuzzySets:")) {
                for(int i=0;i<variables.size();i++) {
                variableParser = br.readLine();
                //System.out.println(variableParser);
                Variables temp = null;
                //TO GET THE VARIABLE BY ITS NAME
                for (int y = 0; y < variables.size(); y++) {
                    if (variables.get(y).name.equals(variableParser))
                        temp = variables.get(y);
                }
                //TO CHECK THAT THE VARIABLE WAS FOUND
                if (temp == null) {
                    System.out.println("VARIABLE NAME NOT FOUND IN VARIABLES ARRAY");
                    System.exit(1);
                }
                System.out.println(temp.name + ":");
                //TO SET VARIABLES FUZZY SET
                while (!(variableParser = br.readLine()).equals("x")) {
                    String[] arrOfStr = variableParser.split(" ");
                    int size = arrOfStr.length;
                    //System.out.println(size);
                    if (size == 5) {
                        TRI triangle = new TRI();
                        triangle.name = arrOfStr[0];
                        triangle.triangleFuzzySets.add(Integer.valueOf(arrOfStr[2]));
                        triangle.triangleFuzzySets.add(Integer.valueOf(arrOfStr[3]));
                        triangle.triangleFuzzySets.add(Integer.valueOf(arrOfStr[4]));
                        System.out.println("TRI NAME:" + triangle.name);
                        System.out.print("TRI SIDE 1:[" + triangle.triangleFuzzySets.get(0) + "],");
                        System.out.print("TRI SIDE 2:[" + triangle.triangleFuzzySets.get(1) + "],");
                        System.out.println("TRI SIDE 3:[" + triangle.triangleFuzzySets.get(2) + "]");
                        temp.triangles.add(triangle);
                    }
                    if (size == 6) {
                        TRAP trapezoid = new TRAP();
                        trapezoid.name = arrOfStr[0];
                        trapezoid.trapezoidFuzzySets.add(Integer.valueOf(arrOfStr[2]));
                        trapezoid.trapezoidFuzzySets.add(Integer.valueOf(arrOfStr[3]));
                        trapezoid.trapezoidFuzzySets.add(Integer.valueOf(arrOfStr[4]));
                        trapezoid.trapezoidFuzzySets.add(Integer.valueOf(arrOfStr[5]));
                        System.out.println("TRAP NAME:" + trapezoid.name);
                        System.out.print("TRAP SIDE 1:[" + trapezoid.trapezoidFuzzySets.get(0) + "],");
                        System.out.print("TRAP SIDE 2:[" + trapezoid.trapezoidFuzzySets.get(1) + "],");
                        System.out.println("TRAP SIDE 3:[" + trapezoid.trapezoidFuzzySets.get(2) + "]");
                        System.out.println("TRAP SIDE 4:[" + trapezoid.trapezoidFuzzySets.get(3) + "]");
                        temp.trapezoids.add(trapezoid);
                    }
                }
                System.out.println("--------------------------");
                }
            }


        //PARSE RULES
        //FORMAT[IN_variable1 FuzzySet1 operator IN_variable2 FuzzySet2 => OUT_variable FuzzySet3]
        variableParser=br.readLine();
        System.out.println(variableParser);
        if (variableParser.equals("Rules:")) {
            while (!(variableParser = br.readLine()).equals("x")){
            Rule rule = new Rule();
            String[] arrOfStr = variableParser.split(" ");
            //FORMAT[IN_variable1 FuzzySet1 operator IN_variable2 FuzzySet2 => OUT_variable FuzzySet3]
            rule.IN_variable1 = arrOfStr[0];
            rule.FuzzySet1 = arrOfStr[1];
            rule.operator = arrOfStr[2];
            rule.IN_variable2 = arrOfStr[3];
            rule.FuzzySet2 = arrOfStr[4];
            rule.OUT_variable = arrOfStr[6];
            rule.FuzzySet3 = arrOfStr[7];
            System.out.print(rule.IN_variable1 + " ");
            System.out.print(rule.FuzzySet1 + " ");
            System.out.print(rule.operator + " ");
            System.out.print(rule.IN_variable2 + " ");
            System.out.print(rule.FuzzySet2 + " ");
            System.out.print("=> ");
            System.out.print(rule.OUT_variable + " ");
            System.out.println(rule.FuzzySet3);
        }
        }


    }
}