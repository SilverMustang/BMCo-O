package smelldetector.ast.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class TypeInfoVisitToDB extends ASTVisitor{
	
	public List<Modifier> modifiers;
	public String name;
	public int NMNOPARAM = 0;
	public ASTNode superClass;//数据类型不确定
	public List<ASTNode> superInterface;//数据类型不确定
	public List<MethodDeclaration> methodList;
	public List<FieldDeclaration> FieldList;
	public List<FieldDeclaration> BasicFieldList;//基本数据类型的成员变量
	public List<FieldDeclaration> OtherFieldList;//其他引用成员
	public List<TypeDeclaration> InnerClassList;
	public List<String> basicType = new ArrayList<>();
	
	public TypeInfoVisitToDB()
	{
		superInterface=new ArrayList<ASTNode>();
		modifiers=new ArrayList<Modifier>();
		FieldList = new ArrayList<FieldDeclaration>();
		methodList = new ArrayList<MethodDeclaration>();
		InnerClassList = new ArrayList<TypeDeclaration>();
		BasicFieldList = new ArrayList<FieldDeclaration>();
		OtherFieldList = new ArrayList<FieldDeclaration>();
		//添加基本的数据类型
		basicType.add("int");
		basicType.add("Integer");
		basicType.add("byte");
		basicType.add("Byte");
		basicType.add("float");
		basicType.add("Float");
		basicType.add("double");
		basicType.add("Double");
		basicType.add("char");
		basicType.add("Character");
		basicType.add("short");
		basicType.add("Short");
		basicType.add("long");
		basicType.add("Long");
		basicType.add("boolean");
		basicType.add("Boolean");
		basicType.add("String");
	}

	
	public void setInnerClass(List<TypeDeclaration> InnerClassList)
	{		
		this.InnerClassList=InnerClassList;
	}
	
	public boolean visit(TypeDeclaration node)
	{
		
		// 这里把注解过滤掉。
		for (Object item : node.modifiers())
		{
			if (item.toString().charAt(0) != '@')
				modifiers.add((Modifier) item);
		}

		name=node.getName().toString();
		superClass=node.getSuperclassType();
		superInterface=node.superInterfaceTypes();
		MethodDeclaration meth[]=node.getMethods();
		for(MethodDeclaration item:meth){methodList.add(item);}
		FieldDeclaration fie[]=node.getFields();
		for(FieldDeclaration item:fie){
			FieldList.add(item);
			if(basicType.contains(item.getType().toString())) {
				BasicFieldList.add(item);
			}else {
				OtherFieldList.add(item);
			}
		}

		//只访问一次
		return false;
	}
	
//	@Override
//	public boolean visit(MethodDeclaration node) {
//		if(node.parameters().size() == 0) {
//			NMNOPARAM++;
//		}
//		return true;
//	}

}
