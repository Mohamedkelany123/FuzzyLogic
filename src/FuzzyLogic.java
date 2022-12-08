import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FuzzyLogic {

    ArrayList<Variables> variables=new ArrayList<>();
    ArrayList<Rule> rules=new ArrayList<>();
    public void parse() throws IOException {
        File file = new File("input2.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String variableParser;
        variableParser=br.readLine();
        System.out.println(variableParser);

        //PARSE VARIABLES
        if(variableParser.equals("Variables:"))
        {
            while(!(variableParser=br.readLine()).equals("x"))
            {
                Variables variable=new Variables();
                String[] arrOfStr = variableParser.split(" ");
                variable.name=arrOfStr[0];
                variable.InOrOut=arrOfStr[1];
                variable.range.add(Integer.valueOf(arrOfStr[2]));
                variable.range.add(Integer.valueOf(arrOfStr[3]));
                System.out.println("Variable Name:" + variable.name);
                System.out.println("Variable Type:" + variable.InOrOut);
                System.out.println("Variable Range[Minimum]:" + variable.range.get(0));
                System.out.println("Variable Range[Max]:" + variable.range.get(1));
                System.out.println("--------------------------");
                variables.add(variable);
            }
        }


        variableParser=br.readLine();


        //PARSE FUZZYSETS
        if (variableParser.equals("FuzzySets:")) {
                for(int i=0;i<variables.size();i++) {
                variableParser = br.readLine();
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
                //TO GET VARIABLE BY ITS NAME
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
            if (temp.triangles.size() != 0 && temp.InOrOut.equals("IN")) {
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
                            double slope = (x2 - x1) / ((double) b - (double) a);
                            //c=y-mx
                            double c = x2 - (slope * b);
                            temp.triangles.get(y).degreeOfMemberShip = (slope * crispValue) + c;
                            System.out.println(temp.name+": " + temp.triangles.get(y).name);
                            System.out.println("Degree of membership: " + temp.triangles.get(y).degreeOfMemberShip);
                            System.out.println("--------------------------");
                        }
                    }
                }
            }

            //TO GET DEGREE OF MEMBERSHIP FOR TRAPERZOIDS
            if (temp.trapezoids.size() != 0 && temp.InOrOut.equals("IN")) {
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
                            double slope = (x2 - x1) / ((double) b - (double) a);
                            double c = x2 - (slope * b);
                            temp.trapezoids.get(y).degreeOfMemberShip = (slope * crispValue) + c;
                            System.out.println(temp.name+": " + temp.trapezoids.get(y).name);
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
            double IN_variable1Degree = 0;
            double IN_variable2Degree = 0;
            //TO GET THE VARIABLES IN THE RULES
            for (Variables variable : variables) {
                if (variable.name.equals(rule.IN_variable1)) {
                    IN_variable1 = variable;
                } else if (variable.name.equals(rule.IN_variable2)) {
                    IN_variable2 = variable;
                }
            }
            //TO GET THE INDEX OF TRIANGLE OR TRAP TO SET DEGREE GET MEMBERSHIP FOR VAR1
            if (Objects.requireNonNull(IN_variable1).TriOrTrap.equals("TRAP")) {
                for (int y = 0; y < IN_variable1.trapezoids.size(); y++) {
                    if (IN_variable1.trapezoids.get(y).name.equals(rule.FuzzySet1)) {
                        IN_variable1Degree = IN_variable1.trapezoids.get(y).degreeOfMemberShip;
                    }
                }
            } else {
                for (int y = 0; y < IN_variable1.triangles.size(); y++) {
                    if (IN_variable1.triangles.get(y).name.equals(rule.FuzzySet1)) {
                        IN_variable1Degree = IN_variable1.triangles.get(y).degreeOfMemberShip;
                    }
                }
            }
            //TO GET THE INDEX OF TRIANGLE OR TRAP TO GET DEGREE OF MEMBERSHIP FOR VAR2
            if (Objects.requireNonNull(IN_variable2).TriOrTrap.equals("TRAP")) {
                for (int y = 0; y < IN_variable2.trapezoids.size(); y++) {
                    if (IN_variable2.trapezoids.get(y).name.equals(rule.FuzzySet2)) {
                        IN_variable2Degree = IN_variable2.trapezoids.get(y).degreeOfMemberShip;
                    }
                }
            } else {
                for (int y = 0; y < IN_variable2.triangles.size(); y++) {
                    if (IN_variable2.triangles.get(y).name.equals(rule.FuzzySet2)) {
                        IN_variable2Degree = IN_variable2.triangles.get(y).degreeOfMemberShip;
                    }
                }
            }
            //FOR OPERATOR AND [MIN]
            if (rule.operator.equals("and")) {
                rule.value = Math.min(IN_variable1Degree, IN_variable2Degree);
            }
            //FOR OPERATOR OR [MAX]
            if (rule.operator.equals("or")) {
                rule.value = Math.max(IN_variable1Degree, IN_variable2Degree);
            }
            //FOR OPERATOR AND_NOT
            if (rule.operator.equals("and_not")) {
                if (IN_variable1Degree <= IN_variable2Degree) {
                    rule.value = IN_variable1Degree;
                } else {
                    rule.value = 1-IN_variable2Degree;
                }
            }
        }
    }

    public void defuzzification(PrintWriter out){
        ArrayList<Variables> out_variables=new ArrayList<>();
        //GET ALL OUT VARIABLES
        for (Variables variable : variables) {
            if (variable.InOrOut.equals("OUT")) {
                out_variables.add(variable);
            }
        }
        //CALCULATE WHEIGHTED AVERAGE
        for (Variables outTemp : out_variables) {
            //CALCULATE IF TRIANGLE ELSE CALCULATE FOR TRAPEZOID
            if (outTemp.triangles.size() != 0) {
                for (int i = 0; i < outTemp.triangles.size(); i++) {
                    double temp = 0;
                    for (int y = 0; y < outTemp.triangles.get(i).triangleFuzzySets.size(); y++) {
                        temp += outTemp.triangles.get(i).triangleFuzzySets.get(y);
                    }
                    outTemp.triangles.get(i).averageWeight = temp / outTemp.triangles.get(i).triangleFuzzySets.size();
                }
            } else {
                for (int i = 0; i < outTemp.trapezoids.size(); i++) {
                    double temp = 0;
                    for (int y = 0; y < outTemp.trapezoids.get(i).trapezoidFuzzySets.size(); y++) {
                        temp += outTemp.trapezoids.get(i).trapezoidFuzzySets.get(y);
                    }
                    outTemp.trapezoids.get(i).averageWeight = temp / outTemp.trapezoids.get(i).trapezoidFuzzySets.size();
                }
            }

        }


        double dividend;
        double divisor=0.0;
        //TO SET DIVISORS FOR EACH OUT VARIABLE
        ArrayList<Double> divisors=new ArrayList<>();
        divisors.add(0.0);
        for (int i=0;i<out_variables.size();i++){
            divisors.add(0.0);
        }
        double totalTemp=0.0;

        double total;

        //SUBSTITUTE IN THE WEIGHTED AVERAGE EQUATION
        for (Rule rule : rules) {
            double triOrTrapTemp;
            int triangleIndex=-1;
            int trapezoidIndex=-1;
            int outIndex=-1;
            //Get index of what to multiply by
            for (int i=0;i<out_variables.size();i++){
                Variables outTemp=out_variables.get(i);
                if(outTemp.triangles.size()!=0){
                    for (int y=0;y<outTemp.triangles.size();y++){
                        if (outTemp.triangles.get(y).name.equals(rule.FuzzySet3) && outTemp.name.equals(rule.OUT_variable)){
                            triangleIndex=y;
                            outIndex=i;
                        }
                    }
                }else if(outTemp.trapezoids.size()!=0){
                    for (int y=0;y<outTemp.trapezoids.size();y++){
                        if (outTemp.trapezoids.get(y).name.equals(rule.FuzzySet3) && outTemp.name.equals(rule.OUT_variable)){
                            trapezoidIndex=y;
                            outIndex=i;
                        }
                    }
                }
            }
            if (triangleIndex!=-1){
                triOrTrapTemp= rule.value * out_variables.get(outIndex).triangles.get(triangleIndex).averageWeight;
                totalTemp+= rule.value*out_variables.get(outIndex).triangles.get(triangleIndex).averageWeight;
                out_variables.get(outIndex).triangles.get(triangleIndex).total+=triOrTrapTemp;
                divisor += rule.value;
                double temp=divisors.get(outIndex);
                temp+= rule.value;
                divisors.set(outIndex,temp);
            } else if (trapezoidIndex!=-1) {
                triOrTrapTemp= rule.value*out_variables.get(outIndex).trapezoids.get(triangleIndex).averageWeight;
                totalTemp+= rule.value*out_variables.get(outIndex).trapezoids.get(triangleIndex).averageWeight;
                out_variables.get(outIndex).trapezoids.get(triangleIndex).total+=triOrTrapTemp;
                divisor += rule.value;
                double temp=divisors.get(outIndex);
                temp+= rule.value;
                divisors.set(outIndex,temp);
            }
        }
        //OUTPUT FOR EVERY FUZZYSET
        for (int y=0;y<out_variables.size();y++) {
            if (out_variables.get(y).TriOrTrap.equals("TRI")) {
                for (int i = 0; i < out_variables.get(y).triangles.size(); i++) {
                    System.out.println(out_variables.get(y).name +": Equation for " + out_variables.get(y).triangles.get(i).name + "=" + out_variables.get(y).triangles.get(i).total + "/" + divisors.get(y));
                    System.out.println(out_variables.get(y).name +": Total=" + out_variables.get(y).triangles.get(i).total / divisors.get(y));
                    out.println(out_variables.get(y).name +": The predicted risk for " + out_variables.get(y).triangles.get(i).name + "= " + out_variables.get(y).triangles.get(i).total / divisors.get(y));
                }
            } else if (out_variables.get(y).TriOrTrap.equals("TRAP")) {
                for (int i = 0; i < out_variables.get(y).trapezoids.size(); i++) {
                    System.out.println(out_variables.get(y).name +": Equation for " + out_variables.get(y).trapezoids.get(i).name + "=" + out_variables.get(y).trapezoids.get(i).total + "/" + divisors.get(y));
                    System.out.println(out_variables.get(y).name +": Total=" + out_variables.get(y).trapezoids.get(i).total / divisor);
                    out.println(out_variables.get(y).name +": The predicted risk for " + out_variables.get(y).trapezoids.get(i).name + "= " + out_variables.get(y).trapezoids.get(i).total / divisors.get(y));
                }
            }
        }

        dividend=totalTemp;
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