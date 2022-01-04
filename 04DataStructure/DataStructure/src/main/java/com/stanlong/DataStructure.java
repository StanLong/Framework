package com.stanlong;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 逆波兰表达式（后缀表达式）
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        String expression = "1+((2+3)*4)-5";
        List<String> list = toInfixExpressionList(expression); // 将中缀表达式拆成一个列表

        List<String> parseSuffixExpression = parseSuffixExpressionList(list); // 将中缀表达式转成后缀表达式

        System.out.println(parseSuffixExpression);

        int result = calculate(parseSuffixExpression); // 计算后缀表达式
        System.out.println(result);


    }

    // 将中缀表达式转成list
    // Infix:中缀
    public static List<String> toInfixExpressionList(String s){
        List<String> ls = new ArrayList<>();
        int i = 0; // 帮助遍历中缀表达式
        String str=""; // 拼接多位数
        char c=' '; // 遍历中缀表达式取出的字符
        do{
            if((c=s.charAt(i))<48 || (c=s.charAt(i))>57){ // 如果扫描到的是非数字
                ls.add("" + c);
                i++;
            }else{
                while (i<s.length() && (c=s.charAt(i))>=48 && (c=s.charAt(i))<=57){
                    str = str+c;
                    i++;
                }
                ls.add(str);
                str="";
            }
        }while (i<s.length());
        return ls;
    }

    // 将中缀表达式的 list 转成后缀表达式对应的 list
    public static List<String> parseSuffixExpressionList(List<String> list){
        // 定义符号栈
        Stack<String> stack = new Stack<>();

        // 定义一个 List 用来保存中间结果
        List<String> middleResult = new ArrayList<>();

        for(String item: list){
            if(item.matches("\\d+")){
                middleResult.add(item);
            }else if(item.equals("(")){
                stack.push(item);
            }else if(item.equals(")")){
                while (!stack.peek().equals("(")){
                    middleResult.add(stack.pop());
                }
                stack.pop();
            }else{
                while (stack.size() != 0 && Operation.getValue(stack.peek()) >= Operation.getValue(item)){
                    middleResult.add(stack.pop());
                }
                stack.push(item);
            }
        }

        while (stack.size()!=0){
            middleResult.add(stack.pop());
        }
        return middleResult;
    }

    /**
     * 计算后缀表达式
     *
     * 思路：
     * 遍历后缀表达式
     * 如果是数字，就将数字入栈
     * 如果是操作符，则弹出栈中的数字进行运算，然后再将运算结果入栈
     */
    public static int calculate(List<String> list){
        Stack<String> stack = new Stack<>();
        for(String item: list){
            if(item.matches("\\d+")){
                stack.push(item);
            }else{
                int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                int result = 0;
                if(item.equals("+")){
                    result = num1 + num2;
                }else if(item.equals("-")){
                    result = num2 - num1;
                }else if(item.equals("*")){
                    result = num1 * num2;
                }else if(item.equals("/")){
                    result = num2 / num1;
                }else{
                    throw new RuntimeException("错误的操作符");
                }
                stack.push("" + result);
            }
        }
        return Integer.parseInt(stack.pop());
    }
}

// 定义操作符优先级
class Operation{
    private static int ADD=1;
    private static int SUB=1;
    private static int MUL=2;
    private static int DIV=2;

    public static int getValue(String operation){
        int result = 0;
        switch (operation){
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }
}
