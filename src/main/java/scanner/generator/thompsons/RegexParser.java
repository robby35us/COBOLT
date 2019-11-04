package scanner.generator.thompsons;

import scanner.language.Regex;
import scanner.model.automata.NDFA;
import scanner.model.state.NDFAState;

import java.util.Stack;

class RegexParser {
    private Stack<Character> symbols = new Stack<>();
    private Stack<NDFA> ndfas = new Stack<>();

    NDFA parseExpression(Regex pattern) {
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

    private NDFA resultOrEmpty() {
        return !ndfas.empty() ? ndfas.pop() :
                new NDFA(new NDFAState(true));
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
            ndfas.push(IntermediateNDFABuilder.buildSimple(c));
        }

        if (symbols.size() < ndfas.size() - 1){
            symbols.push(' ');
        }
    }

    private void processSymbolFromStack(char symbol) {
        switch (symbol) {
            case '*':
                ndfas.push(IntermediateNDFABuilder.buildClosure(ndfas.pop()));
                break;
            case '(': // not sure about this
            case ' ':
                NDFA andTemp = ndfas.pop();
                if(!ndfas.empty())
                    ndfas.push(IntermediateNDFABuilder.buildAnd(ndfas.pop(), andTemp));
                else
                    ndfas.push(andTemp);
                break;
            case '|':
                NDFA orTemp = ndfas.pop();
                ndfas.push(IntermediateNDFABuilder.buildOr(ndfas.pop(), orTemp));
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