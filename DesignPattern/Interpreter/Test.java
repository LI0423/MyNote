package DesignPattern.Interpreter;

import java.util.Stack;

public class Test {
    
    public static void main(String[] args) {
        final String tokenString = "4 3 2 - 1 + *";
        Stack<Expression> stack = new Stack<>();

        String[] tokenList = tokenString.split(" ");

        for(String s : tokenList){
            if (isOperator(s)) {
                Expression rightExpression = stack.pop();
                Expression leftExpression = stack.pop();
                System.out.println("popped from stack left: " + leftExpression.interpret() + " right: " + rightExpression.interpret());
                Expression operator = getOperatorInstance(s, leftExpression, rightExpression);
                System.out.println("operator: " + operator);
                int result = operator.interpret();
                NumberExpression resultExpression = new NumberExpression(result);
                stack.push(resultExpression);
                System.out.println("push result to stack: " + resultExpression.interpret());
            } else {
                NumberExpression i = new NumberExpression(s);
                stack.push(i);
                System.out.println("push to stack: " + i.interpret());
            }
        }
        System.out.println("result");
    }

    public static boolean isOperator(String s){
        return s.equals("+") || s.equals("-") || s.equals("*");
    }

    public static Expression getOperatorInstance(String s, Expression left, Expression right){
        return switch(s){
            case "+" -> new PlusExpression(left, right);
            case "-" -> new MinusExpression(left, right);
            default -> new MultiplyExpression(left, right);
        };
    }
}
