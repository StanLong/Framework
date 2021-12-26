package com.stanlong;

/**
 * 使用栈计算中缀表达式
 *
 * 中缀表达式：运算符在数字中间
 *
 * 比如使用栈计算 3+2*6-2 的结果
 * 1. 通过 index 索引来遍历表达式
 * 2. 如果是数字就直接入栈
 * 3. 如果是一个符号
 *   3.1 如果当前的符号栈为空，就直接入栈
 *   3.2 如果符号栈中有操作符，就比较操作符的优先级。
 *      如果小于或等于符号栈中的优先级，那就从数栈中pop出两个数，从符号栈中 pop 出一个操作数，进行运算。将得到的结果入数栈，然后将当前操作符入符号栈
 *      如果大于当前符号栈中的优先级，就直接入符号栈
 * 4. 当表达式扫描完毕，就顺序从数栈和符号栈中pop相应的数和符号，并运算
 * 5. 最后在数栈中只有一个数字，就是表达式的结果
 */
public class DataStructure {

    public static void main(String[] args) throws Exception {
        String exception = "100+1*100";
        ArrayStack numStack = new ArrayStack(10); // 数栈
        ArrayStack operStack = new ArrayStack(10); // 符号栈

        int index = 0; // 用于扫描输入的表达式
        int num1 = 0; // 操作数
        int num2 = 0; // 操作数
        int oper = 0; // 操作符
        int res = 0; // 计算结果
        char ch = ' '; // 从表达式上截取的字符
        String keyNum = ""; // 用于拼接多位数

        while (true){
            ch = exception.substring(index, index+1).charAt(0);
            if(operStack.isOper(ch)){ // 如果是操作符
                if (!operStack.isEmpty()) { // 如果符号栈非空
                    // 如果小于或等于符号栈中的优先级，那就从数栈中pop出两个数，从符号栈中 pop 出一个数，进行运算。将得到的结果入数栈，然后将当前操作符入符号栈
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);
                        numStack.push(res);
                    }
                }
                operStack.push(ch);
            }else{ // 如果是数, 则进行多位数拼接后直接入栈
                keyNum = keyNum + ch;

                if (index == exception.length() -1){
                    numStack.push(Integer.parseInt(keyNum));
                }else {
                    if(operStack.isOper((exception.substring(index+1, index+2).charAt(0)))){
                        numStack.push((Integer.parseInt(keyNum)));
                        keyNum = "";
                    }
                }

            }
            index = index +1;
            if (index >= exception.length()){
                break;
            }
        }

        while (true){
            if(operStack.isEmpty()){
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            numStack.push(res);

        }
        System.out.printf("表达式 %s = %d \n", exception, numStack.pop());
    }
}

/**
 * 用数组模拟栈
 */
class ArrayStack{
    private int maxSize; // 栈的大小
    private int[] stack; // 模拟栈的数组
    private int top =-1; // 栈顶，初始值为 -1

    public ArrayStack(int maxSize){
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    public boolean isFull(){
        return top == maxSize -1;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    // 入栈
    public void push(int value){
        if(isFull()){
            System.out.println("栈满了");
            return;
        }
        top = top + 1;
        stack[top] = value;
    }

    // 出栈
    public int pop(){
        if(isEmpty()){
            throw new RuntimeException("栈是空的");
        }
        int value = stack[top];
        top = top -1;
        return value;
    }

    // 遍历栈里的元素
    public void show(){
        if(isEmpty()){
            throw new RuntimeException("栈是空的");
        }
        for(int i=top; i>=0; i--){
            System.out.printf("stack[%d]=%d \n", i, stack[i]);
        }
    }

    // 返回栈顶的值
    public int peek(){
        return stack[top];
    }

    // 定义操作符的优先级
    public int priority(int oper){
        if(oper == '*' || oper == '/'){
            return 1;
        } else if (oper == '+' || oper == '-'){
            return 0;
        }else {
            return -1;
        }
    }

    // 判断是否为操作符
    public boolean isOper(char val){
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    // 计算操作
    public int cal(int num1, int num2, int oper){
        int result = 0;
        switch (oper){
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num2 - num1;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num2 /  num1;
                break;
            default:
                break;
        }
        return result;
    }
}