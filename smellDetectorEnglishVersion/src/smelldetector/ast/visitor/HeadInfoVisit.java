package smelldetector.ast.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

//提取头部的信息
public class HeadInfoVisit extends ASTVisitor{

	public PackageDeclaration packageNode;
	public List<ImportDeclaration> importNodeList ;
	public HeadInfoVisit()
	{
		importNodeList	= new ArrayList<ImportDeclaration>();		
	}
	

	public boolean visit(CompilationUnit node)
	{
		packageNode=node.getPackage();
		importNodeList=node.imports();
		return false;
	}
}
