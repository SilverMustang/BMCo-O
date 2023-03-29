package smelldetector.ui.frame;

import static org.hamcrest.CoreMatchers.startsWith;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;

import smelldetector.ast.core.ASTAnalysis;
import smelldetector.ast.core.LargeClassDe;
import smelldetector.util.SmellDeUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class UploadFrame extends JFrame {

	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField rootTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebLookAndFeel.install();
					UploadFrame frame = new UploadFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UploadFrame() {
		setTitle("上传文件");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("上传Java项目");
		lblNewLabel.setFont(new Font("黑体", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 10, 180, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("请选择.zip压缩包格式的Java项目源代码文件");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(30, 36, 240, 25);
		contentPane.add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 59, 564, 2);
		contentPane.add(separator);
		
		JLabel nameLabel = new JLabel("项目名称：");
		nameLabel.setToolTipText("请输入项目名称");
		nameLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		nameLabel.setBounds(30, 100, 80, 25);
		contentPane.add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setFont(new Font("宋体", Font.PLAIN, 14));
		nameTextField.setBounds(103, 100, 471, 25);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel rootLabel = new JLabel("项目地址：");
		rootLabel.setToolTipText("请选择正确格式的项目文件");
		rootLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		rootLabel.setBounds(30, 160, 80, 25);
		contentPane.add(rootLabel);
		
		rootTextField = new JTextField();
		rootTextField.setFont(new Font("宋体", Font.PLAIN, 14));
		rootTextField.setBounds(103, 160, 375, 25);
		contentPane.add(rootTextField);
		rootTextField.setColumns(10);
		
		JButton chooseButton = new JButton("选择文件");
		chooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
		        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		        int result = chooser.showDialog(new JLabel(), "选择");
		        if(result == JFileChooser.APPROVE_OPTION) {
			        File file = chooser.getSelectedFile();
			        if(!file.getName().endsWith("zip")) {
			        	JOptionPane.showMessageDialog(null, "请选择.zip压缩文件", "文件类型不支持", JOptionPane.WARNING_MESSAGE);
			        }else {
			        	rootTextField.setText(file.getAbsoluteFile().toString());
			        }
		        }else if(result == JFileChooser.CANCEL_OPTION) {
		        	rootTextField.setText("未选择文件");
		        }
			}
		});
		chooseButton.setFont(new Font("宋体", Font.PLAIN, 12));
		chooseButton.setBounds(488, 160, 86, 25);
		contentPane.add(chooseButton);
		
		JButton cancelButton = new JButton("关闭");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setFont(new Font("SimSun", Font.PLAIN, 12));
		cancelButton.setBounds(514, 326, 60, 25);
		contentPane.add(cancelButton);
		
		JButton resetButton = new JButton("重置");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameTextField.setText(null);
				rootTextField.setText(null);
			}
		});
		resetButton.setFont(new Font("宋体", Font.PLAIN, 12));
		resetButton.setBounds(444, 326, 60, 25);
		contentPane.add(resetButton);
		
		JButton uploadButton = new JButton("上传");
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ASTAnalysis astAnalysis = new ASTAnalysis();
				SmellDeUtil smellDeUtil = new SmellDeUtil();
				ProgressBar progressBar = new ProgressBar();
				if(!nameTextField.getText().equals("") && !rootTextField.getText().equals("") && rootTextField.getText().endsWith(".zip")) {
					progressBar.setVisible(true);
					uploadButton.setEnabled(false);
					new Thread(new Runnable() {
						public void run() {
							try {
								String projectName = nameTextField.getText();
								String filePath = rootTextField.getText();
								filePath = filePath.replace("/", "\\");
								astAnalysis.ast(projectName, filePath);
								smellDeUtil.smellDetection(projectName);
								uploadButton.setEnabled(true);
								nameTextField.setText("");
								rootTextField.setText("");
								progressBar.dispose();
								JOptionPane.showMessageDialog(null, "解析成功", "提示", JOptionPane.INFORMATION_MESSAGE);
							}catch(Exception e) {
								uploadButton.setEnabled(true);
								progressBar.dispose();
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "解析失败，请重试！", "错误", JOptionPane.WARNING_MESSAGE);
							}
						}
					}).start();
				}else {
					JOptionPane.showMessageDialog(null, "请输入正确的文件路径和文件格式", "错误", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		uploadButton.setFont(new Font("宋体", Font.PLAIN, 12));
		uploadButton.setBounds(374, 326, 60, 25);
		contentPane.add(uploadButton);
	}
}
