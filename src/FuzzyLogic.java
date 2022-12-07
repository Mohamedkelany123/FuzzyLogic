import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FuzzyLogic {

    ArrayList<Variables> variables=new ArrayList<>();
    ArrayList<Rule> rules=new ArrayList<>();
    public void parse() throws IOException {
        File file = new File("input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        //PARSE VARIABLES

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
                    for (Variables variable : variables) {
                        if (variable.name.equals(variableParser))
                            temp = variable;
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
                        temp.TriOrTrap="TRI";
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
                        temp.TriOrTrap="TRAP";
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
            rules.add(rule);
        }
            System.out.println("--------------------------");
        }

        //PARSE CRISP VALUES
        variableParser=br.readLine();
        System.out.println(variableParser);
        Variables temp=null;
        if (variableParser.equals("CrispValues:")){

            while(!(variableParser=br.readLine()).equals("x")){
                String[] arrOfStr = variableParser.split(" ");
                for (Variables variable : variables) {
                    if (variable.name.equals(arrOfStr[0]))
                        temp = variable;
                }
                Objects.requireNonNull(temp).crispValue=Integer.parseInt(arrOfStr[1]);
                System.out.print(temp.name + " ");
                System.out.println(temp.crispValue);
            }
            System.out.println("--------------------------");
        }
    }

    public void fuzzification(){
        Variables temp;
        for (Variables variable : variables) {
            temp = variable;
            int crispValue = temp.crispValue;

            //TO GET DEGREE OF MEMBERSHIP FOR TRIANGLES
            if (temp.triangles.size() != 0 && temp.type.equals("IN")) {
                for (int y = 0; y < temp.triangles.size(); y++) {
                    for (int x = 0; x < 2; x++) {
                        int a = temp.triangles.get(y).triangleFuzzySets.get(x);
                        int b = temp.triangles.get(y).triangleFuzzySets.get(x + 1);
                        if (a <= temp.crispValue && temp.crispValue <= b) {
                            double x1, x2;
                            if (x == 0) {
                                x1 = 0;
                                x2 = 1;
                            } else {
                                x1 = 1;
                                x2 = 0;
                            }
                            System.out.println(temp.triangles.get(y).name);
                            //System.out.println(x2 + "-" + x1 + "/" + b + "-" + a);
                            double slope = (x2 - x1) / ((double) b - (double) a);
                            //System.out.println("Slope=" + slope);
                            //c=y-mx
                            double c = x2 - (slope * b);
                            //System.out.println("Slope: " +slope +" C: "  + c);
                            temp.triangles.get(y).degreeOfMemberShip = (slope * crispValue) + c;
                            System.out.println("Degree of membership: " + temp.triangles.get(y).degreeOfMemberShip);
                            System.out.println("--------------------------");
                        }
                    }
                }
            }

            //TO GET DEGREE OF MEMBERSHIP FOR TRAPERZOIDS
            if (temp.trapezoids.size() != 0 && temp.type.equals("IN")) {
                for (int y = 0; y < temp.trapezoids.size(); y++) {
                    for (int x = 0; x < 3; x++) {
                        int a = temp.trapezoids.get(y).trapezoidFuzzySets.get(x);
                        int b = temp.trapezoids.get(y).trapezoidFuzzySets.get(x + 1);
                        if (a <= temp.crispValue && temp.crispValue <= b) {
                            double x1, x2;
                            if (x == 0) {
                                x1 = 0;
                                x2 = 1;
                            } else if (x == 1) {
                                x1 = 1;
                                x2 = 1;
                            } else {
                                x1 = 1;
                                x2 = 0;
                            }
                            System.out.println(temp.trapezoids.get(y).name);
                            //System.out.println(x2 + "-" + x1 + "/" + b + "-" + a);
                            double slope = (x2 - x1) / ((double) b - (double) a);
                            double c = x2 - (slope * b);
                            //System.out.println("Slope: " +slope +" C: "  + c);
                            temp.trapezoids.get(y).degreeOfMemberShip = (slope * crispValue) + c;
                            System.out.println("Degree of membership: " + temp.trapezoids.get(y).degreeOfMemberShip);
                            System.out.println("--------------------------");

                        }
                    }
                }
            }

        }

    }

    public void inference() {
        //[AND,MIN] [OR,MAX] [NOT,1-X]
        for (Rule rule : rules) {
            Variables IN_variable1 = null;
            Variables IN_variable2 = null;
            Variables out_temp = null;
            int IN_variable1Index;
            int IN_variable2Index;
            double IN_variable1Degree = 0;
            double IN_variable2Degree = 0;
            int out_index = -1;
            //TO GET THE VARIABLES IN THE RULES
            for (Variables variable : variables) {
                if (variable.name.equals(rule.IN_variable1)) {
                    IN_variable1 = variable;
                } else if (variable.name.equals(rule.IN_variable2)) {
                    IN_variable2 = variable;
                } else if (variable.type.equals("OUT")) {
                    out_temp = variable;
                }
            }
            //TO GET THE INDEX OF TRIANGLE OR TRAP TO GET DEGREE OF MEMBERSHIP FOR VAR1
            if (Objects.requireNonNull(IN_variable1).TriOrTrap.equals("TRAP")) {
                for (int y = 0; y < IN_variable1.trapezoids.size(); y++) {
                    if (IN_variable1.trapezoids.get(y).name.equals(rule.FuzzySet1)) {
                        IN_variable1Index = y;
                        IN_variable1Degree = IN_variable1.trapezoids.get(IN_variable1Index).degreeOfMemberShip;
                        //System.out.println(IN_variable1.trapezoids.get(IN_variable1Index).name);

                    }
                }
            } else {
                for (int y = 0; y < IN_variable1.triangles.size(); y++) {
                    if (IN_variable1.triangles.get(y).name.equals(rule.FuzzySet1)) {
                        IN_variable1Index = y;
                        IN_variable1Degree = IN_variable1.triangles.get(IN_variable1Index).degreeOfMemberShip;
                        //System.out.println(IN_variable1.triangles.get(IN_variable1Index).name);
                    }
                }
            }
            //TO GET THE INDEX OF TRIANGLE OR TRAP TO GET DEGREE OF MEMBERSHIP FOR VAR2
            if (Objects.requireNonNull(IN_variable2).TriOrTrap.equals("TRAP")) {
                for (int y = 0; y < IN_variable2.trapezoids.size(); y++) {
                    if (IN_variable2.trapezoids.get(y).name.equals(rule.FuzzySet2)) {
                        IN_variable2Index = y;
                        IN_variable2Degree = IN_variable2.trapezoids.get(IN_variable2Index).degreeOfMemberShip;
                        //System.out.println(IN_variable2.trapezoids.get(IN_variable2Index).name);
                    }
                }
            } else {
                for (int y = 0; y < IN_variable2.triangles.size(); y++) {
                    if (IN_variable2.triangles.get(y).name.equals(rule.FuzzySet2)) {
                        IN_variable2Index = y;
                        IN_variable2Degree = IN_variable2.triangles.get(IN_variable2Index).degreeOfMemberShip;
                        //System.out.println(IN_variable2.triangles.get(IN_variable2Index).name);

                    }
                }
            }
            //TO GET THE INDEX OF TRIANGLE OR TRAP TO GET DEGREE OF MEMBERSHIP FOR OUT_TEMP
            if (Objects.requireNonNull(out_temp).TriOrTrap.equals("TRAP")) {
                for (int y = 0; y < out_temp.trapezoids.size(); y++) {
                    if (out_temp.trapezoids.get(y).name.equals(rule.FuzzySet3)) {
                        out_index = y;
                        //System.out.println(out_temp.trapezoids.get(out_index).name);
                    }
                }
            } else {
                for (int y = 0; y < out_temp.triangles.size(); y++) {
                    if (out_temp.triangles.get(y).name.equals(rule.FuzzySet3)) {
                        out_index = y;
                        //System.out.println(out_temp.triangles.get(out_index).name);

                    }
                }
            }
            //FOR OPERATOR AND [MIN]
            if (rule.operator.equals("and")) {
                if (IN_variable1Degree <= IN_variable2Degree) {
                    rule.value = IN_variable1Degree;
                    out_temp.triangles.get(out_index).degreeOfMemberShip = IN_variable1Degree;
                } else {
                    out_temp.triangles.get(out_index).degreeOfMemberShip = IN_variable2Degree;
                    rule.value = IN_variable2Degree;
                }
            }
            //FOR OPERATOR OR [MAX]
            if (rule.operator.equals("or")) {
                if (IN_variable1Degree <= IN_variable2Degree) {
                    rule.value = IN_variable2Degree;
                    out_temp.triangles.get(out_index).degreeOfMemberShip = IN_variable2Degree;
                } else {
                    rule.value = IN_variable1Degree;
                    out_temp.triangles.get(out_index).degreeOfMemberShip = IN_variable1Degree;
                }
            }
        }
    }

    public void defuzzification(PrintWriter out){
        Variables out_variable=null;

        for (Variables variable : variables) {
            if (variable.type.equals("OUT")) {
                out_variable = variable;
            }
        }

        //WEIGHTED AVERAGE
        for (int i = 0; i< Objects.requireNonNull(out_variable).triangles.size(); i++){
            double temp=0;
            for (int y=0;y<out_variable.triangles.get(i).triangleFuzzySets.size();y++){
                temp+=out_variable.triangles.get(i).triangleFuzzySets.get(y);
            }
            out_variable.triangles.get(i).averageWeight=temp/out_variable.triangles.get(i).triangleFuzzySets.size();
            /* System.out.println(out_variable.triangles.get(i).averageWeight); */
        }

        //SUBSTITUTE THE WEIGHTED AVERAGE EQUATION
        double dividend;
        double divisor=0.0;
        double temp=0.0;
        double temp2;
        double total;
        int triangleIndex=-1;
        for (Rule rule : rules) {
            //get index of what to multiply by
            for (int y = 0; y < out_variable.triangles.size(); y++) {
                if (out_variable.triangles.get(y).name.equals(rule.FuzzySet3)) {
                    triangleIndex = y;
                }
            }
            temp2=rule.value * out_variable.triangles.get(triangleIndex).averageWeight;
            temp += rule.value * out_variable.triangles.get(triangleIndex).averageWeight;
            out_variable.triangles.get(triangleIndex).total+=temp2;
            System.out.println(rule.value);
            divisor += rule.value;
        }
        for (int i=0;i<out_variable.triangles.size();i++){
            System.out.println("Equation "+out_variable.triangles.get(i).name+"="+out_variable.triangles.get(i).total+"/"+divisor);
            System.out.println("Total="+out_variable.triangles.get(i).total/divisor);
            out.println("The predicted risk for "+ out_variable.triangles.get(i).name + "= "+out_variable.triangles.get(i).total/divisor);
        }
        dividend=temp;
        total=dividend/divisor;
        System.out.println("Equation for Total= " +dividend + "/" + divisor);
        System.out.println("Total=" + total);
        out.println("The predicted total for all="+ total);
    }

    public void run() throws IOException {

        PrintWriter out = new PrintWriter("output.txt");
        out.println("Running the simulation....");
        parse();
        out.print("Fuzzification => ");
        fuzzification();
        out.println("done");
        out.print("Inference => ");
        inference();
        out.println("done");
        out.println("Defuzzification => done ");
        defuzzification(out);
        out.close();
    }
}