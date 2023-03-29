package smelldetector.ast.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.*;


public class FieldInfoVisit extends ASTVisitor{
	
	public List<Modifier> modifiers;
	public Type type;
	public List<VariableDeclarationFragment> fragemtns;
	public FieldInfoVisit()
	{
		modifiers = new ArrayList<Modifier>();
		fragemtns = new ArrayList<VariableDeclarationFragment>();
	}

	public boolean visit(FieldDeclaration node)
	{
		type = node.getType();
		// 这里把注解过滤掉。
		for (Object item : node.modifiers())
		{
			if (item.toString().charAt(0) != '@')
				modifiers.add((Modifier) item);
		}

		fragemtns = node.fragments();

		return true;
	}
}
