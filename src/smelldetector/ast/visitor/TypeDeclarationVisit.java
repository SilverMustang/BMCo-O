package smelldetector.ast.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

import smelldetector.util.ASTUtil;

public class TypeDeclarationVisit extends ASTVisitor{
	
	public List<TypeDeclaration> typeDeclarationList;
	public int NMNOPARAM = 0;//类中无参数方法的个数
	public TypeDeclarationVisit () {typeDeclarationList=new ArrayList<TypeDeclaration>();}
	
	public boolean visit(TypeDeclaration node)
	{
		typeDeclarationList.add(node);
		return true;
	}
	
	//返回得到其他内部类
	public List<TypeDeclaration> getOtherNode()
	{
		List<TypeDeclaration> innerClassType=new ArrayList<TypeDeclaration>();
		for(TypeDeclaration item:typeDeclarationList)
		{
			if(!ASTUtil.judgeParent(item, "CompilationUnit"))
			{
				
				innerClassType.add(item);
			}
		}

		return innerClassType;
		
	}
	public TypeDeclaration getClassNode()
	{
		for(TypeDeclaration item:typeDeclarationList)
		{
			if(ASTUtil.judgeParent(item, "CompilationUnit"))
			{
			
				return item;
			}
		}

		return null;
		
	}
	public List<TypeDeclaration> getClassNodeList()
	{
		List<TypeDeclaration> mainClassType=new ArrayList<TypeDeclaration>();
		for(TypeDeclaration item:typeDeclarationList)
		{
			if(ASTUtil.judgeParent(item, "CompilationUnit"))
			{			
				mainClassType.add(item);
			}
		}

		return mainClassType;
		
	}

}
