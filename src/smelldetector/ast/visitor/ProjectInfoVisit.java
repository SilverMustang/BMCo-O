package smelldetector.ast.visitor;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import smelldetector.util.CountLength;

public class ProjectInfoVisit extends ASTVisitor{
	
	public Javadoc javadoc;
	public Javadoc methodJavadoc;
	public String author;
	public String since ;
	public String version;
	public String isJavadoc;
	public List<TagElement> tagElementList;
	public int lineSum = 0;
	
//	@Override
//	public boolean visit(CompilationUnit node) {
//		//该方法可获得代码的总行数
//		CountLength cl = new CountLength();
//		cl.countLength(node.toString(), "\n");
//		lineSum = cl.getCodeLength();
//		return true;	
//	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		javadoc = node.getJavadoc();
		if(javadoc!=null) {
			tagElementList = javadoc.tags();
		}
		
		//该方法可获得代码的总行数
		CountLength cl = new CountLength();
		cl.countLength(node.toString(), "\n");
		lineSum = cl.getCodeLength();
		return true;
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		methodJavadoc = node.getJavadoc();
		if(methodJavadoc != null) {
			isJavadoc = "true";
		}else {
			isJavadoc = "false";
		}
		return true;
	}

}
