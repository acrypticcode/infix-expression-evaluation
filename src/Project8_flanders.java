//Project 8 CISS 111
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Project8_flanders {
    private static GenStack<Character> oStack = new GenStack<>();
    private static GenStack<Integer> vStack = new GenStack<>();

    public static int eval(String s){
        char ch;
        int sum;
        int len = s.length();


        for (int i=0;i<len;i++){
            ch = s.charAt(i);
            if (ch==' '){
                continue;
            }
            else if (ch=='('){
                oStack.push(ch);
                }
            else if (ch == ')') {
                while (oStack.peek()!='(') {
                    apply(vStack,oStack);
                }
                oStack.pop();//gets rid of open paren
            }
            else if (ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='%'){
                while (!oStack.isEmpty()&&prec(ch)<prec(oStack.peek())){
                    apply(vStack,oStack);
                }
                oStack.push(ch);
            }

            else {//if ch is a number
                sum = ch-'0';
                while (i+1<len && s.substring(i+1,i+2).matches("\\d")) {//if the next character exists and is a number
                    sum = sum * 10 + s.charAt(i + 1)-'0';
                    i++;//intentionally changes counter in outer loop
                }
                vStack.push(sum);
            }
        }
        //after going through the loop once, applies until the operator stack runs out and returns final value
        while (!oStack.isEmpty()){
            apply(vStack,oStack);
        }
        return vStack.pop();
    }

    public static void apply(GenStack<Integer> val, GenStack<Character> op){
        char operator = op.pop();
        int val2 = val.pop();
        int val1 = val.pop();
        int result;
        if (operator=='*'){
            result = val1*val2;
        }
        else if (operator=='/'){
            result = val1/val2;
        }
        else if (operator=='%'){
            result = val1%val2;
        }
        else if (operator=='+'){
            result = val1+val2;
        }
        else {//if it's a - sign
            result = val1-val2;
        }
        vStack.push(result);
    }


    public static int prec(char op){
        if (op=='*'||op=='/'||op=='%'){
            return 2;
        }
        else if (op=='+'||op=='-'){
            return 1;
        }
        else{
            return 0;
        }
    }


    public static void main(String args[]) throws FileNotFoundException{
        int result;
        String expression;
        Scanner inFile = new Scanner(new FileReader(args[0]));
        while (inFile.hasNext()){
            expression = inFile.nextLine();
            result = eval(expression);
            System.out.println(expression+" = "+result);
        }
    }
}