package scanner.generator.thompsons;

import scanner.language.Regex;
import scanner.model.FiniteAutomaton;
import scanner.model.State;

import java.util.Stack;

class RegexParser {
    private Stack<Character> symbols = new Stack<>();
    private Stack<FiniteAutomaton> nfas = new Stack<>();

    FiniteAutomaton parseExpression(Regex pattern) {
        processCharsInPattern(pattern);
        processRemainingSymbols();
        return resultOrEmpty();
    }

    private void processCharsInPattern(Regex pattern) {
        while (pattern.hasNextChar())
            processChar(pattern.getNextChar());
    }

    private void processRemainingSymbols() {
        while(!symbols.empty())
            processSymbolFromStack(symbols.pop());
    }

    private FiniteAutomaton resultOrEmpty() {
        return !nfas.empty() ? nfas.pop() :
                new FiniteAutomaton(new State(true), false);
    }

    private void processChar(char c) {

        if(!symbols.empty() && symbols.peek() == '*') {
            processSymbol(symbols.pop());
        }

        if (c == '(') {
            if(!symbols.empty() && symbols.peek() == '*')
                processSymbolFromStack(symbols.pop());
            symbols.push(c);
        } else if (isSymbol(c)) {
            processSymbol(c);
        } else {
            nfas.push(AutomataBuilder.buildSimple(c));
        }

        if (symbols.size() < nfas.size() - 1){
            symbols.push(' ');
        }
    }

    private void processSymbolFromStack(char symbol) {
        switch (symbol) {
            case '*':
                nfas.push(AutomataBuilder.buildClosure(nfas.pop()));
                break;
            case '(': // not sure about this
            case ' ':
                FiniteAutomaton andTemp = nfas.pop();
                if(!nfas.empty())
                    nfas.push(AutomataBuilder.buildAnd(nfas.pop(), andTemp));
                else
                    nfas.push(andTemp);
                break;
            case '|':
                FiniteAutomaton orTemp = nfas.pop();
                nfas.push(AutomataBuilder.buildOr(nfas.pop(), orTemp));
                break;
            case ')':
                break;
        }
    }

    private void processSymbol(char newSymbol) {
        Character lastPoppedSymbol = newSymbol;
        while(!symbols.empty() && precedenceOf(newSymbol) <= precedenceOf(symbols.peek())
                && lastPoppedSymbol != '(') {
            lastPoppedSymbol = symbols.pop();
            processSymbolFromStack(lastPoppedSymbol);
        }
        if(newSymbol != ')')
            symbols.push(newSymbol);
    }

    private boolean isSymbol(char c) {
        switch (c) {
            case '(':
            case ')':
            case '|':
            case '*':
            case ' ':
                return true;
            default:
                return false;
        }
    }

    private static int precedenceOf(char symbol) {
        switch (symbol) {
            case '*':
                return 3;
            case ' ':
                return 2;
            case '|':
                return 1;
            default:
                return 0;
        }
    }
}