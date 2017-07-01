package lab2swing;

import java.awt.BorderLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.KeyStroke;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TestFrame extends JFrame {

	public static File currentRoot = null;

	public TestFrame() {
		super("‘айловый менеджер");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final File[] roots = File.listRoots();
		// menu
		JMenuBar menuBar = new JMenuBar();
		// —оздадим вертикальное меню верхнего уровн€
		JMenu m = new JMenu("File");
		// —оздадим каскадное вертикальное меню
		JMenu m1 = new JMenu("New");

		// ƒобавим пункты к каскадному вертикальному меню
		JMenuItem folder = new JMenuItem("Folder");
		m1.add(folder);
		JMenuItem textFile = new JMenuItem("Text File");
		m1.add(textFile);
		m.add(m1);
		m.addSeparator();
		menuBar.add(m);
		setJMenuBar(menuBar);
		m.add(new JMenuItem("Copy"));

		JMenuItem delItem = new JMenuItem("Delete");
		m.add(delItem);
		// ƒобавим шорткат к пункту меню
		delItem.setAccelerator(KeyStroke.getKeyStroke('D', CTRL_DOWN_MASK));
		m.add(new JMenuItem("Rename"));
		// —оздадим вертикальное меню
		JMenu m2 = new JMenu("View");
		// ƒобавим переключаемые пункты к вертикальному меню
		m2.add(new JCheckBoxMenuItem("Small"));
		m2.add(new JCheckBoxMenuItem("Medium"));
		m2.add(new JCheckBoxMenuItem("Large"));
		menuBar.add(m2);
		// ƒобавим модальные окна к вертикальному меню
		JMenu m3 = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		m3.add(about);
		// добавим модальное окно

		menuBar.add(m3);

		// показать модальное окно при клике 'about'
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "All right reserved.");
			}
		});
		// удалить файл
		delItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String textFileName = JOptionPane.showInputDialog("Delete text file");
				System.out.println(TestFrame.currentRoot);
				try {
					File file = new File(
							TestFrame.currentRoot + /* "textjava\\" + */ textFileName + ".txt");
					// удалить файл
					if (file.delete()) {
						System.out.println(file.getName() + " is deleted!");
					} else {
						System.out.println("Delete operation is failed.");
					}

				} catch (Exception ex) {

					ex.printStackTrace();

				}
			}
		});
		// установим менеджер размещений
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		// создать две панели
		final JPanel rootContent = new JPanel(); // главную
		rootContent.setLayout(new BoxLayout(rootContent, BoxLayout.Y_AXIS));
		// и панель со скролбаром
		final JScrollPane rootContentScroll = new JScrollPane(rootContent);

		final JList list = new JList(roots);
		list.setVisibleRowCount(5); // кол-во видимых линий
		// show modal with text box
		// JOptionPane.showInputDialog(this,"Enter your message","Messages",2);
		textFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String textFileName = JOptionPane.showInputDialog("Create text file");
				// String path = TestFrame.currentRoot + "\\" + textFileName +
				// ".txt";
				System.out.println(TestFrame.currentRoot);
				File file = new File(
						TestFrame.currentRoot + /* "textjava\\" + */ textFileName + ".txt");

				// Create the file

				try {
					if (file.createNewFile()) {
						System.out.println("File is created!");
					} else {
						System.out.println("File already exists.");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				rootContentScroll.repaint();
				// System.out.println(textFileName);
				// System.out.println(roots[0]);
				// System.out.println(TestFrame.currentRoot);
			}
		});
		// слушатель при выборе корневого каталога
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				rootContent.removeAll();
				File root = (File) list.getSelectedValue();
				TestFrame.currentRoot = root;
				File[] children = root.listFiles();
				if (children != null) {
					// показывает содержимое
					for (int i = 0; i < children.length; i++) {
						// формирую поле с меткой
						JLabel label = new JLabel(children[i].getName());
						rootContent.add(label);
					}
				}
				rootContent.repaint();
				rootContentScroll.revalidate();
			}
		});

		mainPanel.add(new JScrollPane(list), BorderLayout.NORTH);
		mainPanel.add(rootContentScroll, BorderLayout.CENTER);

		getContentPane().add(mainPanel);

		setPreferredSize(new Dimension(260, 420));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				new TestFrame();
			}
		});
	}
}
