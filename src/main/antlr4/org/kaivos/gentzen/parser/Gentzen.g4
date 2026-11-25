grammar Gentzen;

@header {
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.kaivos.gentzen.DeclDef;
import org.kaivos.gentzen.DefinedType;
import org.kaivos.gentzen.Expression;
import org.kaivos.gentzen.ExpressionType;
import org.kaivos.gentzen.Kind;
import org.kaivos.gentzen.Rule;
import org.kaivos.gentzen.RuleSignature;
import org.kaivos.gentzen.TypeAlias;
import org.kaivos.gentzen.Typeclass;
import org.kaivos.gentzen.Utils;
}

gentzenFile returns [ArrayList<DeclDef> decldefs] :
    {$decldefs = new ArrayList<>();}
    (importDecl {$decldefs.add($importDecl.d);})*
    (
    ruleDecl {$decldefs.add($ruleDecl.r);}
    | ruleDef {$decldefs.add($ruleDef.rd);}
    | typealias {$decldefs.add($typealias.ta);}
    | typeclass {$decldefs.add($typeclass.tc);}
    )*
    (exportDecl {$decldefs.add($exportDecl.d);})*
    ;

importDecl returns [DeclDef.ImportDecl d] :
    '<-' STRING ';'
    {$d = new DeclDef.ImportDecl(Utils.parseString($STRING.text), $start, $stop);};

exportDecl returns [DeclDef.ExportDecl d]
    locals [ArrayList<String> names = new ArrayList<>()]
    @after {$d = new DeclDef.ExportDecl($names, $start, $stop);}
    :
    '->' (IDENT {$names.add($IDENT.text);})* ';'
    ;

ruleDecl returns [DeclDef.RuleDeclaration r, String name, RuleSignature rs]
    @after {$r = new DeclDef.RuleDeclaration($nametoken.text, $rulesig.rs, $start, $stop);
    $name=$nametoken.text; $rs=$rulesig.rs;}
    :
    nametoken=IDENT ':' rulesig=ruleSignature ';'
    ;

ruleDef returns [DeclDef.RuleDefinition rd]
    @after {$rd = new DeclDef.RuleDefinition($name.text, $expr.expr, $start, $stop);}
    :
    name=IDENT '=' expr=expression ';'
    ;

ruleSignature returns [RuleSignature rs] :
    {ArrayList<RuleSignature.Sequent> aboves = new ArrayList<>();}
    '(' (sequent {aboves.add($sequent.seq);} (';' sequent {aboves.add($sequent.seq);})*)? ')'
    '/'
    '(' sequent {RuleSignature.Sequent below = $sequent.seq;} ')'
    {$rs = new RuleSignature(aboves, below);};

sequent returns [RuleSignature.Sequent seq] :
    {
    ArrayList<RuleSignature.ContextVarParameter> contextVars = new ArrayList<>();
    HashMap<Integer, RuleSignature.SequentParameter> parameters = new HashMap<>();
    }
    (
        (
            '*' IDENT {contextVars.add(new RuleSignature.ContextVarParameter(new RuleSignature.ContextVar($IDENT.text, 0), true));}
            | '$' NUM '(' type ')' {parameters.put(Integer.parseInt($NUM.text), new RuleSignature.SequentParameter($type.typ, true));}
        )
        ( ',' (
            '*' IDENT {contextVars.add(new RuleSignature.ContextVarParameter(new RuleSignature.ContextVar($IDENT.text, 0), true));}
            | '$' NUM '(' type ')' {parameters.put(Integer.parseInt($NUM.text), new RuleSignature.SequentParameter($type.typ, true));}
        ))*
    )?
    '|-'
    (
        (
            '*' IDENT {contextVars.add(new RuleSignature.ContextVarParameter(new RuleSignature.ContextVar($IDENT.text, 0), false));}
            | '$' NUM '(' type ')' {parameters.put(Integer.parseInt($NUM.text), new RuleSignature.SequentParameter($type.typ, false));}
        )
        ( ',' (
            '*' IDENT {contextVars.add(new RuleSignature.ContextVarParameter(new RuleSignature.ContextVar($IDENT.text, 0), false));}
            | '$' NUM '(' type ')' {parameters.put(Integer.parseInt($NUM.text), new RuleSignature.SequentParameter($type.typ, false));}
        ))*
    )?
    {$seq = new RuleSignature.Sequent(parameters, Map.of(), contextVars);};

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

expression returns [Expression expr = null] :
    (expressionTermLeft '+' {$expr = $expressionTermLeft.expr;})?
    expressionTermRight {$expr = $expr == null ? $expressionTermRight.expr : new Expression.Application("cut", List.of($expr, $expressionTermRight.expr), $expressionTermLeft.start, $expressionTermRight.stop);}
    ;

expressionTermLeft returns [Expression expr] :
    expressionArg {$expr = $expressionArg.expr;}
    | application {$expr = $application.app;};

expressionTermRight returns [Expression expr] :
    expressionArg {$expr = $expressionArg.expr;}
    | application {$expr = $application.app;}
    | arule {$expr = $arule.ar;};

expressionArg returns [Expression expr] :
    symbol {$expr = $symbol.sym;}
    | STRING {$expr = new Expression.StringLiteral($STRING.text, $STRING, $STRING);}
    | NUM {$expr = new Expression.NaturalLiteral(Integer.parseInt($NUM.text), $NUM, $NUM);}
    | CHAR {$expr = new Expression.NaturalLiteral($CHAR.text.charAt(1), $CHAR, $CHAR);}
    | '(' expression ')' {$expr = $expression.expr;};

arule returns [Expression ar] :
    open='[' below=identlist ']' body=expression {$ar = new Expression.AnonymousRule(List.of(), $below.list, $body.expr, $open, $body.stop);}
    | open='[' above=identlist '/' below=identlist ']' body=expression {$ar = new Expression.AnonymousRule($above.list, $below.list, $body.expr, $open, $body.stop);};

identlist returns [ArrayList<String> list] :
    {$list = new ArrayList<>();}
    IDENT {$list.add($IDENT.text);} (',' IDENT {$list.add($IDENT.text);})*;

symbol returns [Expression sym] :
    IDENT {$sym = new Expression.Symbol($IDENT.text, $IDENT, $IDENT);};

application returns [Expression app]
    locals [ArrayList<Expression> args = new ArrayList<>()]
    @after {$app = new Expression.Application($name.text, $args, $start, $stop);}
    :
    name=IDENT {}
    (expressionArg {$args.add($expressionArg.expr);})*
    (arule {$args.add($arule.ar);})?
    ;

typealias returns [DeclDef.TypeAliasDecl ta]
    locals [TypeAlias alias]
    @after {$ta = new DeclDef.TypeAliasDecl($alias, $start, $stop);}
    :
    {ArrayList<String> params = new ArrayList<>();}
    name=IDENT
    (
    IDENT {params.add($IDENT.text);}
    )*
    ':=' (
    type {$alias = new TypeAlias($name.text, params, Optional.of($type.typ));}
    | '???' {$alias = new TypeAlias($name.text, params, Optional.empty());}
    )
    ';'
    ;

typeclass returns [DeclDef.TypeclassDecl tc]
    locals [
        ArrayList<DefinedType.TypeParam> params = new ArrayList<>(),
        HashMap<String, RuleSignature> rs = new HashMap<>(),
        ArrayList<Typeclass.CutEliminationRule> cers = new ArrayList<>()
    ]
    @after {$tc = new DeclDef.TypeclassDecl(new Typeclass($name.text, $params, $rs, $cers), $start, $stop);}
    :
    '{'
    name=IDENT
    (
    '{' IDENT ':' ruleSignature '}' {$params.add(new DefinedType.TypeParam.ValueParam($IDENT.text, $ruleSignature.rs));}
    | '[' IDENT kind ']' {$params.add(new DefinedType.TypeParam.ParamWithKind($IDENT.text, $kind.knd));}
    )*
    ';'
    (
    ruleDecl {$rs.put($ruleDecl.name, $ruleDecl.rs);}
    | cutEliminationRuleDef {$cers.add($cutEliminationRuleDef.cer);}
    )*
    '->' (IDENT | '&' IDENT | '%' IDENT | '?' IDENT)* ';'
    '}'
    ;

kind returns [Kind knd] :
    {ArrayList<Kind> subkinds = new ArrayList<>();}
    ('(' kind ')' {subkinds.add($kind.knd);})*
    {$knd = new Kind.TypeKind(subkinds);}
    |
    '{' ruleSignature '}'
    {$knd = new Kind.RuleKind($ruleSignature.rs);};


cutEliminationRuleDef returns [Typeclass.CutEliminationRule cer]:
    prim=IDENT '$' primArg=NUM sec=IDENT '$' secArg=NUM '=' body=expression ';'
    {$cer = new Typeclass.CutEliminationRule($prim.text, Integer.parseInt($primArg.text), $sec.text, Integer.parseInt($secArg.text), $body.expr);};

IDENT : [a-zA-Z_][a-zA-Z0-9_\-/]* ;
NUM : [0-9]+ ;
CHAR : '\'' . '\'' ;
STRING : '"' (~["]|'\\'[nrt"])* '"';
WS : [ \n\r\t]+ -> skip ;
EQ : '==' ;

LINE_COMMENT : '#' ~[\r\n]* -> skip ;
