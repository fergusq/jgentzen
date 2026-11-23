grammar Gentzen;
import org.kaivos.gentzen;

@header {
package org.kaivos.gentzen.parser;
}

ruleSignature returns [RuleSignature rs;] : '/' {$rs = new RuleSignature()};

WS : [ \n]+ -> skip ;
