package smelldetector.ui.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;

public class ProgressBar extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebLookAndFeel.install();
					ProgressBar frame = new ProgressBar();
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
	public ProgressBar() {
		setTitle("解析中...");
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 185);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("正在解析文件，请稍等！");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 10, 170, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("（项目较大时，解析时间较长，请耐心等待！）");
		lblNewLabel_1.setBounds(10, 91, 415, 25);
		contentPane.add(lblNewLabel_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(Color.GREEN);
		progressBar.setBackground(Color.WHITE);
		progressBar.setIndeterminate(true);
		progressBar.setBounds(10, 56, 415, 25);
		contentPane.add(progressBar);
	}
}
