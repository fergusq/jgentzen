package org.kaivos.gentzen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.kaivos.gentzen.Namespace.MetaNamespace;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            loadTest(args[0], ".").ifPresent(lines -> lines.forEach(System.out::println));
        } else {
            Files
                .list(Path.of("./tests"))
                .map(p -> p.getName(p.getNameCount()-1))
                .map(Path::toString)
                .map(s -> s.replaceFirst("\\..*", ""))
                .forEach(testCase -> {
                    System.out.println("Compiling " + testCase + "...");
                    loadTest(testCase.toString(), "tests").ifPresent(scheme -> {
                        var outPath = Path.of("./tests-out").resolve(testCase + ".ss");
                        try {
                            Files.write(outPath, scheme);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
        }
    }

    private static Optional<List<String>> loadTest(String name, String cwd) {
        Namespace namespace = new MetaNamespace().getNamespace(name);
        namespace.addSearchPath(Path.of("lib"), Path.of(cwd));
        if (namespace.loadFile(name)) {
            var list = namespace.toScheme();
            list.add("(import (" + name + "))");
            list.add("(call/cc (lambda (c) (g/main 'iosys c)))");
            return Optional.of(list);
        } else {
            return Optional.empty();
        }
    }
}
