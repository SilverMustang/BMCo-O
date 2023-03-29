package smelldetector.ast.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;

public class HeadInfoVisitToDB extends ASTVisitor{
	
	public PackageDeclaration packageNode;
	public List<ImportDeclaration> importNodeList ;
	public HeadInfoVisitToDB()
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
