package smelldetector.util;

import smelldetector.ast.core.ComplexClassDe;
import smelldetector.ast.core.FeatureEnvyDe;
import smelldetector.ast.core.LargeClassDe;
import smelldetector.ast.core.LongMethodDe;
import smelldetector.ast.core.MessageChainDe;
import smelldetector.ast.core.RefusedBequestDe;
import smelldetector.ast.core.SpaghettiCodeDe;
import smelldetector.metrics.ExtractMetrics;

public class SmellDeUtil {
	
	public void smellDetection(String projectName) {
		LargeClassDe largeClassDe = new LargeClassDe(); 
		LongMethodDe longMethodDe = new LongMethodDe();
		MessageChainDe messageChainDe = new MessageChainDe();
		ExtractMetrics extractMetric = new ExtractMetrics();
		SpaghettiCodeDe spaghettiCodeDe = new SpaghettiCodeDe();
		ComplexClassDe complexClassDe = new ComplexClassDe();
		RefusedBequestDe refusedBequestDe = new RefusedBequestDe();
		FeatureEnvyDe featureEnvyDe = new FeatureEnvyDe();
		
		System.out.println("Extracting Metrics...");
		extractMetric.extractMetrics(projectName);
		System.out.println("Detecting Message Chain...");
		messageChainDe.messageChainAnalysis(projectName);
		System.out.println("Detecting Large Class...");
		largeClassDe.largeClassAnalysis(projectName);
		System.out.println("Detecting Spaghetti Code...");
		spaghettiCodeDe.spaghettiCodeAnalysis(projectName);
		System.out.println("Detecting Complex Class...");
		complexClassDe.complexClassAnalysis(projectName);
		System.out.println("Detecting Refused Bequest...");
		refusedBequestDe.refusedBequestAnalysis(projectName);
		System.out.println("Detecting Feature Envy...");
		featureEnvyDe.featureEnvyAnalysis(projectName);
		System.out.println("Detecting Long Method...");
		longMethodDe.longMethodAnalysis(projectName);
	}

}
