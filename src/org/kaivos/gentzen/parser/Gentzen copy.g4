grammar Gentzen;

@header {
package org.kaivos.gentzen.parser;

import java.util.HashMap;
import java.util.Map;

import org.kaivos.gentzen.Expression;
import org.kaivos.gentzen.ExpressionType;
import org.kaivos.gentzen.RuleSignature;
}

ruleSignature returns [RuleSignature rs] :
    {ArrayList<RuleSignature.Sequent> aboves = new ArrayList<>();}
    LPAREN (sequent {aboves.add($sequent.seq);} (SEMI sequent {aboves.add($sequent.seq);})*)? RPAREN
    SLASH
    LPAREN sequent {RuleSignature.Sequent below = $sequent.seq;} RPAREN
    {$rs = new RuleSignature(aboves, below);};

sequent returns [RuleSignature.Sequent seq] :
    inputs=seqParameters
    TURNSTILE
    outputs=seqParameters
    {$seq = new RuleSignature.Sequent($inputs.params, $outputs.params);};

seqParameters returns [RuleSignature.SequentParameters params] :
    {
    ArrayList<RuleSignature.ContextVar> contextVars = new ArrayList<>();
    HashMap<Integer, ExpressionType> parameters = new HashMap<>();
    }
    (
        STAR IDENT {contextVars.add(new RuleSignature.ContextVar($IDENT.text, 0));}
        | DOLLAR NUM LPAREN type RPAREN {parameters.put(Integer.parseInt($NUM.text), $type.typ);}
        ( COMMA (
            STAR IDENT {contextVars.add(new RuleSignature.ContextVar($IDENT.text, 0));}
            | DOLLAR NUM LPAREN type RPAREN {parameters.put(Integer.parseInt($NUM.text), $type.typ);}
        ))*
    )?
    {$params = new RuleSignature.SequentParameters(parameters, Map.of(), contextVars);};

type returns [ExpressionType typ] :
    QUES IDENT {$typ = new ExpressionType.TypeVar($IDENT.text, 0);}
    | NOT type {$typ = new ExpressionType.Not($type.typ);}
    | left=type AND right=type {$typ = new ExpressionType.And($left.typ, $right.typ);}
    | left=type OR right=type {$typ = new ExpressionType.Or($left.typ, $right.typ);}
    | AT type {$typ = new ExpressionType.Maybe($type.typ);}
    | BOLDN {$typ = new ExpressionType.Nat();}
    | PLUSPLUS {$typ = new ExpressionType.Unit();}
    | BOTTOM {$typ = new ExpressionType.Bottom();}
    | DOTS type {$typ = new ExpressionType.List($type.typ);}
    | BOX type {$typ = new ExpressionType.Box($type.typ);}
    | DIAMOND type {$typ = new ExpressionType.Not(new ExpressionType.Box(new ExpressionType.Not($type.typ)));}
    | EXCL IDENT {$typ = new ExpressionType.Atomic($IDENT.text);}
    | name=IDENT {ArrayList<ExpressionType> typeArgs = new ArrayList<>();} (type {typeArgs.add($type.typ);})* {$typ = new ExpressionType.CustomType($IDENT.text, typeArgs);};

expression returns [Expression expr] :
    arule {$expr = $arule.ar;}
    | application {$expr = $application.app;}
    | symbol {$expr = $symbol.sym;}
    | LPAREN expression RPAREN {$expr = $expression.expr;};

arule returns [Expression ar] :
    LBRACKET below=identlist RBRACKET body=expression {$ar = new Expression.AnonymousRule(List.of(), $below.list, $body.expr);}
    | LBRACKET above=identlist SLASH below=identlist RBRACKET body=expression {$ar = new Expression.AnonymousRule($above.list, $below.list, $body.expr);};

identlist returns [ArrayList<String> list] :
    {$list = new ArrayList<>();}
    IDENT {$list.add($IDENT.text);} (COMMA IDENT {$list.add($IDENT.text);})*;

symbol returns [Expression sym] :
    IDENT {$sym = new Expression.Symbol($IDENT.text);};

application returns [Expression app] :
    IDENT {String ruleName = $IDENT.text; ArrayList<Expression> args = new ArrayList<>();}
    (expression {args.add($expression.expr);})*
    {$app = new Expression.Application(ruleName, args);};

COMMA : ',' ;

LBRACKET : '[' ;
RBRACKET : ']' ;

LPAREN : '(' ;
RPAREN : ')' ;

SLASH : '/' ;
QUES : '?' ;
EXCL : '!' ;
SEMI : ';' ;
NOT : '~' ;
AT : '@' ;
STAR : '*' ;
DOLLAR : '$' ;
BOX : '[]' ;
DIAMOND : '<>' ;
DOTS : '..' ;
BOLDN : '|\\|' ;
PLUSPLUS : '++' ;
BOTTOM : '_|_' ;
TURNSTILE : '|-' ;

AND : '/\\' ;
OR : '\\/' ;

IDENT : [a-zA-Z][a-zA-Z0-9_\-/]+ ;
NUM : [0-9]+ ;
WS : [ \n]+ -> skip ;
