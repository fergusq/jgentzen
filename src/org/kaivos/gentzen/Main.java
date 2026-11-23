package org.kaivos.gentzen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Namespace namespace = new Namespace();
        namespace.importFile("test");
        Files.write(Path.of("test.ss"), namespace.toScheme());
    }
}
