/*******************************************************************************
 * Copyright 2017 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Element arithmetic.
 *
 * @author Kyle K. Lin
 *
 */
public final class ElemArithmetic {

    /**
     * Function.
     */
    public final String func;

    private final ArrayList<String> postFix;

    /**
     * Constructor.
     * @param func Function.
     */
    public ElemArithmetic(String func) {
        this.func = func.replace(" ", "");
        this.postFix = new ArrayList<String>();
        prepare();
    }

    /**
     * Calculate.
     * @return Result.
     */
    public double calculate() {
        return calculate(null);
    }

    /**
     * Calculate.
     * @param paramValues values of parameters.
     * @return Result.
     */
    public double calculate(Map<String, Object> paramValues) {
        if (paramValues == null) {
            paramValues = new TreeMap<String, Object>();
        }

        Stack<Double> value = new Stack<Double>();
        for (String elem : this.postFix) {
            if ("+".equals(elem)) {
                Double v1 = value.pop();
                Double v2 = value.pop();
                value.push(v2 + v1);
                //System.out.println(String.format("%s + %s", v2, v1));
            }
            else if ("-".equals(elem)) {
                Double v1 = value.pop();
                Double v2 = value.pop();
                value.push(v2 - v1);
                //System.out.println(String.format("%s - %s", v2, v1));
            }
            else if ("*".equals(elem)) {
                Double v1 = value.pop();
                Double v2 = value.pop();
                value.push(v2 * v1);
                //System.out.println(String.format("%s * %s", v2, v1));
            }
            else if ("/".equals(elem)) {
                Double v1 = value.pop();
                Double v2 = value.pop();
                value.push(v2 / v1);
                //System.out.println(String.format("%s / %s", v2, v1));
            }
            else if ("^".equals(elem)) {
                Double v1 = value.pop();
                Double v2 = value.pop();
                value.push(Math.pow(v2, v1));
                //System.out.println(String.format("%s ^ %s", v2, v1));
            }
            else {
                Object result = paramValues.get(elem);
                value.push(Double.parseDouble(result == null ? elem : "" + result));
            }
        }

        return value.pop();
    }

    /**
     * Print function.
     */
    public String path() {
        String result = "";
        for (String e : this.postFix) {
            result += " " + e;
        }
        return result.trim();
    }

    private void prepare() {

        String number = "";
        int valid = 0;
        Stack<Character> ops = new Stack<Character>();

        byte[] chs = this.func.getBytes();
        for (int i = 0; i < chs.length; i++) {
            char ch = (char) chs[i];

            int currType = getType(ch);
            if (currType == 0) {
                number += ch;
                continue;
            }
            if (ch == '-' && (i == 0 || getType((char) chs[i - 1]) > 1)) {
                number += ch;
                continue;
            }

            if (number.length() > 0) {
                this.postFix.add(number.trim());
                number = "";
            }

            if (ch == '(') {
                valid++;
                if (i != 0 && getType((char) chs[i - 1]) == 0) {
                    ops.push('*');
                }
                ops.push(ch);
            }
            else if (ch == ')') {
                valid--;
                if (valid < 0) {
                    throw new ArithmeticException(this.func + " is wrong");
                }

                char prevOp;
                while ((prevOp = ops.pop()) != '(') {
                    this.postFix.add("" + prevOp);
                }
            }
            else {
                boolean go = !ops.isEmpty();
                while (go) {
                    char prevOp = ops.peek();
                    int prevType = getType(prevOp);
                    if (currType > prevType) {
                        go = false;
                    }
                    else if (currType < prevType) {
                        this.postFix.add("" + ops.pop());
                        go = !ops.isEmpty();
                    }
                    else if (prevOp == '-' || prevOp == '/') {	// left to right
                        this.postFix.add("" + ops.pop());
                        go = false;
                    }
                    else {
                        go = false;
                    }
                }
                ops.push(ch);
            }
        }

        if (valid > 0) {
            throw new ArithmeticException(this.func + " is wrong");
        }

        if (number.length() > 0) {
            this.postFix.add(number.trim());
        }

        while (!ops.isEmpty()) {
            this.postFix.add("" + ops.pop());
        }
    }

    private int getType(char ch) {
        switch (ch) {
            case '^':
                return 5;
            case '*':
            case '/':
                return 4;
            case '+':
            case '-':
                return 3;
            case '(':
                return 2;
            case ')':
                return 1;
            default:
                return 0;
        }
    }
}
