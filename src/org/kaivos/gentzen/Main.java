package org.kaivos.gentzen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.kaivos.gentzen.parser.GentzenLexer;
import org.kaivos.gentzen.parser.GentzenParser;

public class Main {
    public static void main(String[] args) throws IOException {
        Namespace namespace = new Namespace();
        loadFile(namespace, "lib/stdlib.gentzen");
        loadFile(namespace, "lib/test.gentzen");
        Files.write(Path.of("test.ss"), namespace.toScheme());
    }

    private static void loadFile(Namespace namespace, String filename) {
        try {
            GentzenLexer lexer = new GentzenLexer(new ANTLRFileStream(filename));
            GentzenParser parser = new GentzenParser(new CommonTokenStream(lexer));
            var decldefs = parser.gentzenFile().decldefs;
            System.err.println(decldefs);
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
