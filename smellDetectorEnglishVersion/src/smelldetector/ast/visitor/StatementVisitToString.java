package smelldetector.ast.visitor;

import org.eclipse.jdt.core.dom.*;

public class StatementVisitToString extends ASTVisitor{

	public StringBuilder javaAST=null;
	public StatementVisitToString(StringBuilder javaAST) {this.javaAST=javaAST;} 
	public StringBuilder getAst(){ return javaAST;}
	//这个是仅生成AST信息
	public boolean visit(IfStatement node)
	{
		javaAST.append("IfS ");
		//javaAST.append("IfStatement ");
		javaAST.append(node.getExpression().toString()+" ");
		return true;
	}

	
	public boolean visit(ExpressionStatement node)
	{
		javaAST.append("ExS ");
		//javaAST.append("ExpressionStatement ");
		javaAST.append(node.getExpression().toString()+" ");
		
		return true;
	}
	
	public boolean visit(BreakStatement node)
	{
		javaAST.append("BrS ");
		//javaAST.append("BreakStatement ");
		
		return true;
	}

	public boolean visit(ContinueStatement node)
	{
		javaAST.append("CoS ");
		//javaAST.append("ContinueStatement ");
	
		return true;
	}
	
	public boolean visit(ForStatement node)
	{
		javaAST.append("FoS ");
		//javaAST.append("ForStatement ");
		
		javaAST.append(node.getExpression().toString()+" ");
		return true;
	}
	
	public boolean visit(SwitchStatement node)
	{
		javaAST.append("SwS ");
		//javaAST.append("SwitchStatement ");
		javaAST.append(node.getExpression().toString()+" ");
		return true;
	}
	
	public boolean visit(ReturnStatement node)
	{
		javaAST.append("ReS ");
		
		//javaAST.append("ReturnStatement ");
		if(node.getExpression()!=null) javaAST.append(node.getExpression().toString()+" ");
		return true;
	}
	
	public boolean visit(TypeDeclarationStatement node)
	{
		javaAST.append("TyS ");
		//javaAST.append("TypeDeclarationStatement ");
		
		return true;
	}

	
	
	public boolean visit(VariableDeclarationStatement node)
	{
		javaAST.append("VaS ");
		javaAST.append("vaT ");
		//javaAST.append("VariableDeclarationStatement ");
		//javaAST.append("variableType ");
		javaAST.append(node.getType().toString()+" ");
		
		javaAST.append("vaF ");
		//javaAST.append("variableFragments ");
		javaAST.append(node.fragments().toString()+" ");

		return true;
	}
	
	public boolean visit(WhileStatement node)
	{
		javaAST.append("WhS ");
	//	javaAST.append("WhileStatement ");
		javaAST.append(node.getExpression().toString()+" ");
		
		return true;
	}
	
	public boolean visit(ThrowStatement node)
	{	
		javaAST.append("ThS ");
	//	javaAST.append("ThrowStatement ");
		javaAST.append(node.getExpression().toString()+" ");
		
		return true;
	}
}
