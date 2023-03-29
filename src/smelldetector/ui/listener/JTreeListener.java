package smelldetector.ui.listener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import smelldetector.dao.LargeClassDao;
import smelldetector.dao.MessageChainDao;
import smelldetector.pojo.LargeClass;
import smelldetector.pojo.MessageChain;

public class JTreeListener {
	
	public JPanel panel = new JPanel();
	
	public JPanel treeListener(String nodeName) {
		if(nodeName == "Message Chain") {
			panel.setLayout(new BorderLayout(0, 0));
			List<MessageChain> messageChainList = new ArrayList<>();
			String[] columnNames = {"id","项目名称","类名称","方法名称","mcc","可能性"};
			Object[][] tableValues = {};
			DefaultTableModel tableModel = new DefaultTableModel(tableValues, columnNames);
			MessageChainDao messageChainDao = new MessageChainDao();
			try {
				messageChainList = messageChainDao.findMessageChain();
				for(MessageChain messageChain : messageChainList) {
					tableModel.addRow(new Object[] {messageChain.getId(), messageChain.getProjectName(),messageChain.getClassName(),messageChain.getMethodName(),
							messageChain.getMCC(),messageChain.getPossibility()});
				}
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
			}
			JTable sourceTable = new JTable(tableModel);
			sourceTable.setPreferredScrollableViewportSize(new Dimension());
			sourceTable.getTableHeader().setReorderingAllowed(false);
			sourceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			sourceTable.getColumnModel().getColumn(2).setPreferredWidth(400);
			sourceTable.getColumnModel().getColumn(3).setPreferredWidth(200);
			
			JScrollPane scrollPane = new JScrollPane(sourceTable);
			panel.removeAll();
			panel.add(scrollPane, BorderLayout.CENTER);
		}
		
		if(nodeName == "Large Class") {
			panel.setLayout(new BorderLayout(0, 0));
			List<LargeClass> largeClassList = new ArrayList<>();
			String[] columnNames = {"id","项目名称","类名称","loc","nof","nom","td","tw","ns","mc","可能性"};
			Object[][] tableValues = {};
			DefaultTableModel tableModel = new DefaultTableModel(tableValues, columnNames);
			LargeClassDao largeClassDao = new LargeClassDao();
			try {
				largeClassList = largeClassDao.findLargeClass();
				for(LargeClass largeClass : largeClassList) {
					tableModel.addRow(new Object[] {largeClass.getId(), largeClass.getProjectName(),largeClass.getClassName(),largeClass.getCOL(),
							largeClass.getNOF(),largeClass.getNOM(),largeClass.getTreeDepth(),largeClass.getTreeWidth(),largeClass.getNodeSum(),
							largeClass.getMaxChildren(),largeClass.getPossibility()});
				}
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e, "错误", javax.swing.JOptionPane.ERROR_MESSAGE);
			}
			JTable sourceTable = new JTable(tableModel);
			sourceTable.setPreferredScrollableViewportSize(new Dimension());
			sourceTable.getTableHeader().setReorderingAllowed(false);
			sourceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			sourceTable.getColumnModel().getColumn(2).setPreferredWidth(400);
			sourceTable.getColumnModel().getColumn(3).setPreferredWidth(200);
			
			JScrollPane scrollPane = new JScrollPane(sourceTable);
			panel.removeAll();
			panel.add(scrollPane, BorderLayout.CENTER);
		}
		
		return panel;
	}
	

}
