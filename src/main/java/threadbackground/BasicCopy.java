package threadbackground;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class BasicCopy extends JFrame {

	public final String MAIN_DIR = "C:\\SourceIt\\task1Catalan";
	public final String BACKUP_DIR = "D:\\test";

	private JPanel contentPane;
	private JTextArea txtCopiedDirs;
	private JButton btnCancel;
	private JProgressBar progressBar;
	private JLabel lblCopying;

	long totalSize = 0; // total size of directories/files
	long currentSize = 0; // current size of files counting up to ONE_PERCENT
	int currentPercent = 0; // current progress in %
	long ONE_PERCENT; // totalSize / 100

	public BasicCopy() {
	}

	public BasicCopy(long space) {
		totalSize = space;
		ONE_PERCENT = totalSize / 100;
		createGUI();

		Task task = new Task(this);
		task.execute();
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void createGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Backup Progress");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		txtCopiedDirs = new JTextArea();
		txtCopiedDirs.setBounds(10, 56, 414, 125);
		contentPane.add(txtCopiedDirs);
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(169, 227, 89, 23);
		contentPane.add(btnCancel);
		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(10, 192, 414, 24);
		progressBar.setValue(0);
		contentPane.add(progressBar);
		lblCopying = new JLabel("Now backing up your files....");
		lblCopying.setBounds(10, 11, 157, 34);
		contentPane.add(lblCopying);
		setVisible(true);
	}

	public static Long getDirSize(File directory) {
		long size = 0L;
		if (directory.listFiles() != null) {
			for (File file : directory.listFiles()) {
				size += file.isDirectory() ? getDirSize(file) : file.length();
			}
		}
		return size;
	}

	/* Close current window */
	public void closeWindow() {
		WindowEvent close = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(close);
		System.exit(0);
	}
}
