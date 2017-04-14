package message;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RecieveMessage extends JFrame{
	
	JPanel  p_south, p_center, p_north;
	JTextArea   area;
	JTable   table;
	JScrollPane  scroll;
	JTextField  t_input, t_title;
	JLabel  la_title;
	JButton  bt_search;
	
	public RecieveMessage() {
		
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		
		t_input = new JTextField(30);
		bt_search = new JButton("검색");
		table = new JTable(3,3);
		scroll = new JScrollPane(table);
		
		la_title = new JLabel("제목");
		t_title = new JTextField(40);
		area = new JTextArea();
		
		p_north.add(t_input);
		p_north.add(bt_search);
		
		p_center.setLayout(new BorderLayout());
		p_center.add(p_north, BorderLayout.NORTH);
		p_center.add(table);
		
		p_south.add(la_title);
		p_south.add(t_title);
		p_south.add(area);
		
		// size
		p_south.setPreferredSize(new Dimension(700, 100));
		p_north.setPreferredSize(new Dimension(700, 50));
		area.setPreferredSize(new Dimension(550, 50));

		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		
		setTitle("수신 메세지");
		setVisible(true);
		setSize(700, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	public static void main(String[] args) {
		new RecieveMessage();
	}

}
