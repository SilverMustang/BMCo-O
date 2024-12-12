package smelldetector.ui.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.alee.laf.WebLookAndFeel;

import smelldetector.dao.ProjectInfoDao;
import smelldetector.pojo.ProjectInfo;
import smelldetector.ui.listener.JTreeListener;

import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class MainFrame {

	private JFrame mainFrame;
	private DefaultTreeModel dt;
	private JTree tree;
	private JScrollPane scrollPane;
	DefaultMutableTreeNode root;
	private JButton checkButton;
	private JPanel dataPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
					MainFrame window = new MainFrame();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("SmellDetector");
		mainFrame.setBounds(100, 100, 1000, 680);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		mainFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
//		JButton uploadButton = new JButton("上传");
		JButton uploadButton = new JButton("upload");
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UploadFrame uploadFrame = new UploadFrame();
				uploadFrame.setVisible(true);
				uploadFrame.setLocationRelativeTo(null);
			}
		});
		uploadButton.setFont(new Font("宋体", Font.PLAIN, 14));
		toolBar.add(uploadButton);
		
//		checkButton = new JButton("查看");
		checkButton = new JButton("check");
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		checkButton.setFont(new Font("宋体", Font.PLAIN, 14));
		toolBar.add(checkButton);
		
//		root = new DefaultMutableTreeNode("已上传项目");
		root = new DefaultMutableTreeNode("uploaded projects");
		dt = new DefaultTreeModel(root);
		tree = new JTree(dt);
		updateTree();
//		tree.setToolTipText("双击根节点刷新内容");
		tree.setToolTipText("Double-click the root node to refresh the content.");
		tree.setFont(new Font("宋体", Font.PLAIN, 14));
	    scrollPane = new JScrollPane(tree);
		mainFrame.getContentPane().add(scrollPane, BorderLayout.WEST);
		
		dataPanel = new JPanel();
		mainFrame.getContentPane().add(dataPanel, BorderLayout.CENTER);
		dataPanel.setLayout(new BorderLayout(0, 0));
		
		JTreeListener jtl = new JTreeListener();
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 如果在这棵树上点击了2次,即双击
                if (e.getSource() == tree && e.getClickCount() == 2) {
                    // 按照鼠标点击的坐标点获取路径
                    TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                    if (selPath != null)// 谨防空指针异常!双击空白处是会这样
                    {
                        // 获取这个路径上的最后一个组件,也就是双击的地方
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
//                        if(node.toString().equals("已上传项目"))
                        if(node.toString().equals("uploaded projects"))
                        {
                        	updateTree();
                        }else if(node.toString().equals("Message Chain")) {	
                        	jtl.treeListener(node.toString());
                        	System.out.println(node.toString());
                        }else if(node.toString().equals("Large Class")) {	
                        	jtl.treeListener(node.toString());
                        	System.out.println(node.toString());
                        }
                        
                    }
                }

            }
        });
        
		JPanel tablePanel = new JPanel();
		tablePanel = jtl.panel;
		dataPanel.add(tablePanel, BorderLayout.CENTER);
		dataPanel.repaint();		
	}
	
	public void updateTree() {
		List<ProjectInfo> projectInfoList = new ArrayList<>();
		String[] badSmell = new String[] {"Message Chain","Large Class","Complex Class","Refused Bequest",
				"Spaghetti Code","Feature Envy","Long Method"};
		ProjectInfoDao projectInfoDao = new ProjectInfoDao();
		try {
			projectInfoList = projectInfoDao.findAllProjectInfo();
			for(ProjectInfo projectInfo : projectInfoList) {
				String projectName = projectInfo.getProjectName();
				DefaultMutableTreeNode n_1 = new DefaultMutableTreeNode(projectName);
				for(int i=0; i<7; i++) {
					n_1.add(new DefaultMutableTreeNode(badSmell[i]));
				}
				root.removeAllChildren();
				root.add(n_1);
				dt.reload();
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
