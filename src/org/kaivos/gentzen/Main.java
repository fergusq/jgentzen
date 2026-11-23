package org.kaivos.gentzen;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.kaivos.gentzen.parser.GentzenLexer;
import org.kaivos.gentzen.parser.GentzenParser;

public class Main {
    public static void main(String[] args) {
        Namespace namespace = new Namespace();
        loadFile(namespace, "stdlib.gentzen");
        loadFile(namespace, "test.gentzen");
    }

    private static void loadFile(Namespace namespace, String filename) {
        try {
            GentzenLexer lexer = new GentzenLexer(new ANTLRFileStream(filename));
            GentzenParser parser = new GentzenParser(new CommonTokenStream(lexer));
            var decldefs = parser.gentzenFile().decldefs;
            for (var decldef : decldefs) {
                try {
                    decldef.register(namespace);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
