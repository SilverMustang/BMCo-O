package smelldetector.ast.visitor;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;

import smelldetector.metrics.McCabe;


public class MethodVariableVisit extends ASTVisitor{
	
	public int NOGV = 0;// Number of Global Variable
	public int cyclomatic = 1;//McCabe
	
	@Override
	public boolean visit(SimpleName node) {
		IBinding binding = node.resolveBinding();
//		System.out.println(binding);
		
		if(binding == null) {
			return false;
		}
		if(binding instanceof IVariableBinding) {
			
			IVariableBinding iVariableBinding = (IVariableBinding) node.resolveBinding();
			if(iVariableBinding.isField()) {
				NOGV+=1;
			}
			
		}
		return true;
	}
	
//	@Override
//	public boolean visit(MethodDeclaration node) {
//		McCabe mcCabe = new McCabe();
//		IBinding binding = node.resolveBinding();		
//		if(binding == null) {
//			return false;
//		}
//		System.out.println(binding.getKind());
//		try {
//			IMethod method = (IMethod) binding.getJavaElement();
//			System.out.println(binding.getJavaElement());
//			if(method!=null) {
//				cyclomatic = mcCabe.getMcCabe(node, method);
//			}
//		}catch(Exception e){
//			System.out.println(e);
//		}
//
//		return true;
//	}

}
