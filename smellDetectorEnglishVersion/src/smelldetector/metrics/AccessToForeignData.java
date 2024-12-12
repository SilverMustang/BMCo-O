package smelldetector.metrics;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;

public class AccessToForeignData {
	
	String nameOfParentClass  = null;
	
	public int calculate_ATFD(MethodDeclaration methodNode) {
		if(methodNode.resolveBinding()!=null) {
			nameOfParentClass  = methodNode.resolveBinding().getDeclaringClass().getQualifiedName();
		}	
		ATFDVisitor visitor = new ATFDVisitor();
		methodNode.accept(visitor);
		int atfd = visitor.atfd;
		
		return atfd;
	}
	
	private class ATFDVisitor extends ASTVisitor{
		
		int atfd = 0;
		
		@Override
		public boolean visit(MethodInvocation node) {
			if(node.resolveMethodBinding()!=null) {
				String nameOfClass = node.resolveMethodBinding().getDeclaringClass().getQualifiedName();
				if(!nameOfClass.equals(nameOfParentClass)) {
					atfd++;
				}
			}
			return true;
		}
		
		@Override
		public boolean visit(FieldAccess node) {
			if(node.resolveFieldBinding()!=null && node.resolveFieldBinding().getDeclaringClass()!=null) {
				String nameOfClass = node.resolveFieldBinding().getDeclaringClass().getQualifiedName();
				if(!nameOfClass.equals(nameOfParentClass)) {
					atfd++;
				}
			}
			return true;
		}
		
		@Override
		public boolean visit(QualifiedName node) {
			if(node.getQualifier().resolveTypeBinding()!=null) {
				String nameOfClass = node.getQualifier().resolveTypeBinding().getQualifiedName();
				if(!nameOfClass.equals(nameOfParentClass)) {
					atfd++;
				}
			}
			
			return true;
		}
		
	}

}
