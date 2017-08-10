package com.ronald8192.randomStringGenerator;

import com.ronald8192.advancedRandom.Range;

/**
 * Created by ronald8192 on 09/08/2017.
 */
public enum PreDefinedGroup {

    NUMBER(new Range(0, 9, 0)), //0-9
    LOWERCASE(new Range('a', 'z', 0)), //97-123
    UPPERCASE(new Range('A', 'Z', 0)), //65-91
    SYMBOL1(new Range(125, 125 + Symbol.SYMBOL1.symbols.length, 0)), //125-138
    SYMBOL2(new Range(139, 139 + Symbol.SYMBOL2.symbols.length, 0)), //139-149
    SYMBOL3(new Range(150, 150 + Symbol.SYMBOL3.symbols.length, 0)), //150-155
    ;

    private Range range;

    PreDefinedGroup(Range range) {
        this.range = range;
    }

    public Range range(){
        return this.range;
    }

    public static char getCharById(int id){
        if (id >= 0 && id <= 9) {
            return (char) (id + 48);
        } else if (id >= 'a' && id <= 'z') {
            return (char) id;
        } else if (id >= 'A' && id <= 'Z') {
            return (char) id;
        } else if (id >= 125 && id <= 125 + Symbol.SYMBOL1.symbols.length -1) {
            return Symbol.SYMBOL1.symbols[id - 125];
        } else if (id >= 139 && id <= 139 + Symbol.SYMBOL2.symbols.length -1) {
            return Symbol.SYMBOL2.symbols[id - 139];
        } else if (id >= 150 && id <= 150 + Symbol.SYMBOL3.symbols.length -1) {
            return Symbol.SYMBOL2.symbols[id - 150];
        } else {
            return ' ';
        }
    }


    public enum Symbol{
        SYMBOL1("`~!@#$%^&*-=_+".toCharArray()),
        SYMBOL2("()[]{}\\|/<>".toCharArray()),
        SYMBOL3(";:\"',.".toCharArray()),
        ;

        private char[] symbols;

        Symbol(char[] symbols){
            this.symbols = symbols;
        }
    }
}
