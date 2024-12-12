package smelldetector.ast.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import smelldetector.util.ASTUtil;

public class StatementVisitToXML extends ASTVisitor{
	
	public boolean visit(IfStatement node)
	{
		System.out.println("<IfStatement>");
		
		//System.out.println("<condition>");
		System.out.println(ASTUtil.changeCode(node.getExpression().toString()));
		//System.out.println("</condition>");

		return true;
	}

	public void endVisit(IfStatement node) 
	{
		System.out.println("</IfStatement>");
	}

	
	public boolean visit(ExpressionStatement node)
	{
		System.out.println("<ExpressionStatement>");
		System.out.println(ASTUtil.changeCode(node.getExpression().toString()));
		return true;
	}
	public void endVisit(ExpressionStatement node) 
	{
		System.out.println("</ExpressionStatement>");
	}
	
	public boolean visit(BreakStatement node)
	{
		System.out.println("<BreakStatement>");
		
		return true;
	}
	public void endVisit(BreakStatement node) 
	{
		System.out.println("</BreakStatement>");
	}
	
	public boolean visit(ContinueStatement node)
	{
		System.out.println("<ContinueStatement>");
		return true;
	}
	public void endVisit(ContinueStatement node) 
	{
		System.out.println("</ContinueStatement>");
	}
	
	public boolean visit(ForStatement node)
	{
		System.out.println("<ForStatement>");
		//可能会有空指针
		if(node.getExpression() != null) {
		System.out.println(ASTUtil.changeCode(node.getExpression().toString()));}
		return true;
	}
	
	public void endVisit(ForStatement node) 
	{
		System.out.println("</ForStatement>");
	}
	public boolean visit(SwitchStatement node)
	{
		System.out.println("<SwitchStatement>");
		System.out.println(ASTUtil.changeCode(node.getExpression().toString()));
		return true;
	}
	public void endVisit(SwitchStatement node) 
	{
		System.out.println("</SwitchStatement>");
	}
	
	public boolean visit(ReturnStatement node)
	{
		System.out.println("<ReturnStatement>");
		System.out.println(ASTUtil.changeCode(node.getExpression() == null ? "" : node.getExpression().toString()));
		return true;
	}
	public void endVisit(ReturnStatement node) 
	{
		System.out.println("</ReturnStatement>");
	}
	
	public boolean visit(TypeDeclarationStatement node)
	{
		System.out.println("<TypeDeclarationStatement>");
		return true;
	}
	public void endVisit(TypeDeclarationStatement node) 
	{
		System.out.println("</TypeDeclarationStatement>");
	}
	
	
	public boolean visit(VariableDeclarationStatement node)
	{
		System.out.println("<VariableDeclarationStatement>");
		System.out.println("<variableType>");
		System.out.println(ASTUtil.changeCode(node.getType().toString()));
		System.out.println("</variableType>");

		System.out.println("<variableFragments>");
		System.out.println(ASTUtil.changeCode(node.fragments().toString()));
		System.out.println("</variableFragments>");
		return true;
	}
	public void endVisit(VariableDeclarationStatement node) 
	{
		System.out.println("</VariableDeclarationStatement>");
	}
	
	public boolean visit(WhileStatement node)
	{
		System.out.println("<WhileStatement>");
		System.out.println(ASTUtil.changeCode(node.getExpression().toString()));
		
		return true;
	}
	public void endVisit(WhileStatement node) 
	{
		System.out.println("</WhileStatement>");
	}
	
	public boolean visit(ThrowStatement node)
	{
		System.out.println("<ThrowStatement>");
		System.out.println(ASTUtil.changeCode(node.getExpression().toString()));
		
		return true;
	}
	public void endVisit(ThrowStatement node) 
	{
		System.out.println("</ThrowStatement>");
	}

}
