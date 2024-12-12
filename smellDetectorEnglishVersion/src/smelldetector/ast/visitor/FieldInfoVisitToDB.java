package smelldetector.ast.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class FieldInfoVisitToDB extends ASTVisitor{
	
	public List<Modifier> modifiers;
	public Type type;
	public List<VariableDeclarationFragment> fragemtns;
	public Map<String, Object> fieldInfoMap = new HashMap<>();	
	public FieldInfoVisitToDB()
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
	
		if(fragemtns != null && fragemtns.size() > 0) {
			fieldInfoMap.put(fragemtns.get(0).getName().toString(), type.toString());
		}
		
		return true;
	}

}
