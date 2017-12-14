package ca.ece.ubc.cpen221.mp5;

// Generated from StructuredQueries.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link StructuredQueriesParser}.
 */
public interface StructuredQueriesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(StructuredQueriesParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(StructuredQueriesParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(StructuredQueriesParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(StructuredQueriesParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(StructuredQueriesParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(StructuredQueriesParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(StructuredQueriesParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(StructuredQueriesParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(StructuredQueriesParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(StructuredQueriesParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#or}.
	 * @param ctx the parse tree
	 */
	void enterOr(StructuredQueriesParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#or}.
	 * @param ctx the parse tree
	 */
	void exitOr(StructuredQueriesParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#and}.
	 * @param ctx the parse tree
	 */
	void enterAnd(StructuredQueriesParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#and}.
	 * @param ctx the parse tree
	 */
	void exitAnd(StructuredQueriesParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#ineq}.
	 * @param ctx the parse tree
	 */
	void enterIneq(StructuredQueriesParser.IneqContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#ineq}.
	 * @param ctx the parse tree
	 */
	void exitIneq(StructuredQueriesParser.IneqContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#gt}.
	 * @param ctx the parse tree
	 */
	void enterGt(StructuredQueriesParser.GtContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#gt}.
	 * @param ctx the parse tree
	 */
	void exitGt(StructuredQueriesParser.GtContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#gte}.
	 * @param ctx the parse tree
	 */
	void enterGte(StructuredQueriesParser.GteContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#gte}.
	 * @param ctx the parse tree
	 */
	void exitGte(StructuredQueriesParser.GteContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#lt}.
	 * @param ctx the parse tree
	 */
	void enterLt(StructuredQueriesParser.LtContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#lt}.
	 * @param ctx the parse tree
	 */
	void exitLt(StructuredQueriesParser.LtContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#lte}.
	 * @param ctx the parse tree
	 */
	void enterLte(StructuredQueriesParser.LteContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#lte}.
	 * @param ctx the parse tree
	 */
	void exitLte(StructuredQueriesParser.LteContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#eq}.
	 * @param ctx the parse tree
	 */
	void enterEq(StructuredQueriesParser.EqContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#eq}.
	 * @param ctx the parse tree
	 */
	void exitEq(StructuredQueriesParser.EqContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#in}.
	 * @param ctx the parse tree
	 */
	void enterIn(StructuredQueriesParser.InContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#in}.
	 * @param ctx the parse tree
	 */
	void exitIn(StructuredQueriesParser.InContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#category}.
	 * @param ctx the parse tree
	 */
	void enterCategory(StructuredQueriesParser.CategoryContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#category}.
	 * @param ctx the parse tree
	 */
	void exitCategory(StructuredQueriesParser.CategoryContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(StructuredQueriesParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(StructuredQueriesParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#rating}.
	 * @param ctx the parse tree
	 */
	void enterRating(StructuredQueriesParser.RatingContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#rating}.
	 * @param ctx the parse tree
	 */
	void exitRating(StructuredQueriesParser.RatingContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#price}.
	 * @param ctx the parse tree
	 */
	void enterPrice(StructuredQueriesParser.PriceContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#price}.
	 * @param ctx the parse tree
	 */
	void exitPrice(StructuredQueriesParser.PriceContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuredQueriesParser#orWithinAtom}.
	 * @param ctx the parse tree
	 */
	void enterOrWithinAtom(StructuredQueriesParser.OrWithinAtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuredQueriesParser#orWithinAtom}.
	 * @param ctx the parse tree
	 */
	void exitOrWithinAtom(StructuredQueriesParser.OrWithinAtomContext ctx);
}