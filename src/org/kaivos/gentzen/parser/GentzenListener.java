// Generated from Gentzen.g4 by ANTLR 4.13.2

package org.kaivos.gentzen.parser;

import java.util.HashMap;
import java.util.Map;

import org.kaivos.gentzen.DeclDef;
import org.kaivos.gentzen.Expression;
import org.kaivos.gentzen.ExpressionType;
import org.kaivos.gentzen.Rule;
import org.kaivos.gentzen.RuleSignature;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GentzenParser}.
 */
public interface GentzenListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GentzenParser#gentzenFile}.
	 * @param ctx the parse tree
	 */
	void enterGentzenFile(GentzenParser.GentzenFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#gentzenFile}.
	 * @param ctx the parse tree
	 */
	void exitGentzenFile(GentzenParser.GentzenFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void enterImportDecl(GentzenParser.ImportDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void exitImportDecl(GentzenParser.ImportDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#exportDecl}.
	 * @param ctx the parse tree
	 */
	void enterExportDecl(GentzenParser.ExportDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#exportDecl}.
	 * @param ctx the parse tree
	 */
	void exitExportDecl(GentzenParser.ExportDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#ruleDecl}.
	 * @param ctx the parse tree
	 */
	void enterRuleDecl(GentzenParser.RuleDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#ruleDecl}.
	 * @param ctx the parse tree
	 */
	void exitRuleDecl(GentzenParser.RuleDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#ruleDef}.
	 * @param ctx the parse tree
	 */
	void enterRuleDef(GentzenParser.RuleDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#ruleDef}.
	 * @param ctx the parse tree
	 */
	void exitRuleDef(GentzenParser.RuleDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#ruleSignature}.
	 * @param ctx the parse tree
	 */
	void enterRuleSignature(GentzenParser.RuleSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#ruleSignature}.
	 * @param ctx the parse tree
	 */
	void exitRuleSignature(GentzenParser.RuleSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#sequent}.
	 * @param ctx the parse tree
	 */
	void enterSequent(GentzenParser.SequentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#sequent}.
	 * @param ctx the parse tree
	 */
	void exitSequent(GentzenParser.SequentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#seqParameters}.
	 * @param ctx the parse tree
	 */
	void enterSeqParameters(GentzenParser.SeqParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#seqParameters}.
	 * @param ctx the parse tree
	 */
	void exitSeqParameters(GentzenParser.SeqParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(GentzenParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(GentzenParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(GentzenParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(GentzenParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#expressionArg}.
	 * @param ctx the parse tree
	 */
	void enterExpressionArg(GentzenParser.ExpressionArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#expressionArg}.
	 * @param ctx the parse tree
	 */
	void exitExpressionArg(GentzenParser.ExpressionArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#arule}.
	 * @param ctx the parse tree
	 */
	void enterArule(GentzenParser.AruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#arule}.
	 * @param ctx the parse tree
	 */
	void exitArule(GentzenParser.AruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#identlist}.
	 * @param ctx the parse tree
	 */
	void enterIdentlist(GentzenParser.IdentlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#identlist}.
	 * @param ctx the parse tree
	 */
	void exitIdentlist(GentzenParser.IdentlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#symbol}.
	 * @param ctx the parse tree
	 */
	void enterSymbol(GentzenParser.SymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#symbol}.
	 * @param ctx the parse tree
	 */
	void exitSymbol(GentzenParser.SymbolContext ctx);
	/**
	 * Enter a parse tree produced by {@link GentzenParser#application}.
	 * @param ctx the parse tree
	 */
	void enterApplication(GentzenParser.ApplicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GentzenParser#application}.
	 * @param ctx the parse tree
	 */
	void exitApplication(GentzenParser.ApplicationContext ctx);
}