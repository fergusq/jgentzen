grammar Gentzen;

@header {
package org.kaivos.gentzen.parser;

import java.util.HashMap;
import java.util.Map;

import org.kaivos.gentzen.DeclDef;
import org.kaivos.gentzen.Expression;
import org.kaivos.gentzen.ExpressionType;
import org.kaivos.gentzen.Rule;
import org.kaivos.gentzen.RuleSignature;
}

gentzenFile returns [ArrayList<DeclDef> decldefs] :
    {$decldefs = new ArrayList<>();}
    (ruleDecl {$decldefs.add($ruleDecl.r);} | ruleDef {$decldefs.add($ruleDef.rd);})*
    ;

ruleDecl returns [DeclDef.RuleDeclaration r] :
    IDENT ':' ruleSignature ';'
    {$r = new DeclDef.RuleDeclaration($IDENT.text, $ruleSignature.rs);};

ruleDef returns [DeclDef.RuleDefinition rd] :
    IDENT '=' expression ';'
    {$rd = new DeclDef.RuleDefinition($IDENT.text, $expression.expr);};

ruleSignature returns [RuleSignature rs] :
    {ArrayList<RuleSignature.Sequent> aboves = new ArrayList<>();}
    '(' (sequent {aboves.add($sequent.seq);} (';' sequent {aboves.add($sequent.seq);})*)? ')'
    '/'
    '(' sequent {RuleSignature.Sequent below = $sequent.seq;} ')'
    {$rs = new RuleSignature(aboves, below);};

sequent returns [RuleSignature.Sequent seq] :
    inputs=seqParameters
    '|-'
    outputs=seqParameters
    {$seq = new RuleSignature.Sequent($inputs.params, $outputs.params);};

seqParameters returns [RuleSignature.SequentParameters params] :
    {
    ArrayList<RuleSignature.ContextVar> contextVars = new ArrayList<>();
    HashMap<Integer, ExpressionType> parameters = new HashMap<>();
    }
    (
        (
            '*' IDENT {contextVars.add(new RuleSignature.ContextVar($IDENT.text, 0));}
            | '$' NUM '(' type ')' {parameters.put(Integer.parseInt($NUM.text), $type.typ);}
        )
        ( ',' (
            '*' IDENT {contextVars.add(new RuleSignature.ContextVar($IDENT.text, 0));}
            | '$' NUM '(' type ')' {parameters.put(Integer.parseInt($NUM.text), $type.typ);}
        ))*
    )?
    {$params = new RuleSignature.SequentParameters(parameters, Map.of(), contextVars);};

type returns [ExpressionType typ] :
    '(' type ')' {$typ = $type.typ;}
    | '?' IDENT {$typ = new ExpressionType.TypeVar($IDENT.text, 0);}
    | '~' type {$typ = new ExpressionType.Not($type.typ);}
    | left=type '/\\' right=type {$typ = new ExpressionType.And($left.typ, $right.typ);}
    | left=type '\\/' right=type {$typ = new ExpressionType.Or($left.typ, $right.typ);}
    | '@' type {$typ = new ExpressionType.Maybe($type.typ);}
    | '|\\|' {$typ = new ExpressionType.Nat();}
    | '++' {$typ = new ExpressionType.Unit();}
    | '_|_' {$typ = new ExpressionType.Bottom();}
    | '..' type {$typ = new ExpressionType.List($type.typ);}
    | '[]' type {$typ = new ExpressionType.Box($type.typ);}
    | '<>' type {$typ = new ExpressionType.Not(new ExpressionType.Box(new ExpressionType.Not($type.typ)));}
    | '!' IDENT {$typ = new ExpressionType.Atomic($IDENT.text);}
    | name=IDENT {ArrayList<ExpressionType> typeArgs = new ArrayList<>();} (type {typeArgs.add($type.typ);})* {$typ = new ExpressionType.CustomType($name.text, typeArgs);};

expression returns [Expression expr] :
    arule {$expr = $arule.ar;}
    | application {$expr = $application.app;}
    | symbol {$expr = $symbol.sym;}
    | '(' expression ')' {$expr = $expression.expr;};

expressionArg returns [Expression expr] :
    arule {$expr = $arule.ar;}
    | symbol {$expr = $symbol.sym;}
    | '(' expression ')' {$expr = $expression.expr;};

arule returns [Expression ar] :
    '[' below=identlist ']' body=expression {$ar = new Expression.AnonymousRule(List.of(), $below.list, $body.expr, $start, $stop);}
    | '[' above=identlist '/' below=identlist ']' body=expression {$ar = new Expression.AnonymousRule($above.list, $below.list, $body.expr, $start, $stop);};

identlist returns [ArrayList<String> list] :
    {$list = new ArrayList<>();}
    IDENT {$list.add($IDENT.text);} (',' IDENT {$list.add($IDENT.text);})*;

symbol returns [Expression sym] :
    IDENT {$sym = new Expression.Symbol($IDENT.text, $start, $stop);};

application returns [Expression app] :
    IDENT {String ruleName = $IDENT.text; ArrayList<Expression> args = new ArrayList<>();}
    (expressionArg {args.add($expressionArg.expr);})*
    {$app = new Expression.Application(ruleName, args, $start, $stop);};

IDENT : [a-zA-Z_][a-zA-Z0-9_\-/]* ;
NUM : [0-9]+ ;
WS : [ \n\r\t]+ -> skip ;
