package org.kaivos.gentzen;

import java.util.List;
import java.util.Map;

import org.kaivos.gentzen.Expression.TypeCheckException;

public class Main {
    public static void main(String[] args) {
        /*
        cut : (*a |- *b, $1(?x); *c, $1(?x) |- *d) / (*a, *c |- *b, *d)
        outtext : () / ($1(iosys), $2(string) |- $3(++))
        main : () / ($1(iosys) |- $2(++));
        main = [x,y] cut "Hello, World!" [z] outtext x z y;
        */
        var cut = new Rule(new RuleSignature(
            List.of(
                new RuleSignature.Sequent(
                    new RuleSignature.SequentParameters(Map.of(), Map.of(), List.of(new RuleSignature.ContextVar("a", 0))),
                    new RuleSignature.SequentParameters(Map.of(1, new ExpressionType.TypeVar("x", 0)), Map.of(), List.of(new RuleSignature.ContextVar("b", 0)))
                ),
                new RuleSignature.Sequent(
                    new RuleSignature.SequentParameters(Map.of(1, new ExpressionType.TypeVar("x", 0)), Map.of(), List.of(new RuleSignature.ContextVar("c", 0))),
                    new RuleSignature.SequentParameters(Map.of(), Map.of(), List.of(new RuleSignature.ContextVar("d", 0)))
                )
            ),
            new RuleSignature.Sequent(
                new RuleSignature.SequentParameters(Map.of(), Map.of(), List.of(new RuleSignature.ContextVar("a", 0), new RuleSignature.ContextVar("c", 0))),
                new RuleSignature.SequentParameters(Map.of(), Map.of(), List.of(new RuleSignature.ContextVar("b", 0), new RuleSignature.ContextVar("d", 0)))
            )
        ));
        var outtext = new Rule(new RuleSignature(
            List.of(),
            new RuleSignature.Sequent(
                new RuleSignature.SequentParameters(Map.of(1, new ExpressionType.CustomType("iosys", List.of()), 2, new ExpressionType.CustomType("string", List.of())), Map.of(), List.of()),
                new RuleSignature.SequentParameters(Map.of(3, new ExpressionType.Unit()), Map.of(), List.of())
            )
        ));
        var helloworld = new Rule(new RuleSignature(
            List.of(),
            new RuleSignature.Sequent(
                new RuleSignature.SequentParameters(Map.of(), Map.of(), List.of()),
                new RuleSignature.SequentParameters(Map.of(1, new ExpressionType.CustomType("string", List.of())), Map.of(), List.of())
            )
        ));
        var main = new Rule(new RuleSignature(
            List.of(),
            new RuleSignature.Sequent(
                new RuleSignature.SequentParameters(Map.of(1, new ExpressionType.CustomType("iosys", List.of())), Map.of(), List.of()),
                new RuleSignature.SequentParameters(Map.of(2, new ExpressionType.Unit()), Map.of(), List.of())
            )
        ));
        var body = new Expression.AnonymousRule(
            List.of(),
            List.of("x", "y"),
            new Expression.Application(
                "cut",
                List.of(
                    new Expression.Symbol("helloworld"),
                    new Expression.AnonymousRule(
                        List.of(),
                        List.of("z"),
                        new Expression.Application(
                            "outtext",
                            List.of(
                                new Expression.Symbol("x"),
                                new Expression.Symbol("z"),
                                new Expression.Symbol("y")
                            )
                        )
                    )
                )
            )
        );
        main.setBody(body);
        var ns = new Namespace(Map.of(
            "cut", cut.getSignature(),
            "outtext", outtext.getSignature(),
            "helloworld", helloworld.getSignature(),
            "main", main.getSignature()
        ), Map.of());

        try {
            var type = body.typeCheck(ns, ns.resolveRule("main").get());
            System.out.println("main body type: " + type.toGentzen());
            System.out.println("all types: " + body.toGentzenWithType());
        } catch (TypeCheckException e) {
            e.printStackTrace();
        }
    }
}
