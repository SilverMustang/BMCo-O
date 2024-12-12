package smelldetector.ast.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;

public class MethodInfoVisit extends ASTVisitor{
	
	public List<Modifier> modifiers;
	public boolean constructor;
	public Type returnType;
	public Type typeGenericity;
	public String name;
	public List<SingleVariableDeclaration> parameters;
	public List<Statement> statements;
	public MethodInfoVisit()
	{
		modifiers = new ArrayList<Modifier>();
		parameters = new ArrayList<SingleVariableDeclaration>();
		statements = new ArrayList<Statement>();
	}

	public boolean visit(MethodDeclaration node)
	{

		// 这里把注解过滤掉。
		for (Object item : node.modifiers())
		{
			if (item.toString().charAt(0) != '@')
				modifiers.add((Modifier) item);
		}

		constructor = node.isConstructor();
		returnType = node.getReturnType2();
		name = node.getName().toString();
		parameters = node.parameters();
		statements = node.getBody() == null ? new ArrayList<Statement>():node.getBody().statements();
		
		return false;
	}

}
