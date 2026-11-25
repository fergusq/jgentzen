package org.kaivos.gentzen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException {
        Files
            .list(Path.of("./tests"))
            .map(p -> p.getName(p.getNameCount()-1))
            .map(Path::toString)
            .map(s -> s.replaceFirst("\\..*", ""))
            .forEach(testCase -> {
                loadTest(testCase.toString()).ifPresent(scheme -> {
                    var outPath = Path.of("./tests-out").resolve(testCase + ".ss");
                    try {
                        Files.write(outPath, scheme);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
    }

    private static Optional<Iterable<String>> loadTest(String name) {
        Namespace namespace = new Namespace();
        namespace.addSearchPath(Path.of("lib"), Path.of("tests"));
        if (namespace.importFile(name)) {
            return Optional.of(namespace.toScheme());
        } else {
            return Optional.empty();
        }
    }
}
