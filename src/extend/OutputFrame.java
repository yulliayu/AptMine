package extend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class OutputFrame extends JFrame implements ActionListener{
	
	JPanel  p_north, p_north_right, p_north_left, p_center;
	JButton  bt_retrieve, bt_exit, bt_excel, bt_print;
	JTable  table;
	
	public OutputFrame() {
		
		p_north = new JPanel();
		p_north_right = new JPanel();
		p_north_left = new JPanel();
		p_center = new JPanel();
		
		bt_retrieve = new JButton("¡∂»∏");
		bt_excel = new JButton("ø¢ºø");
		bt_print = new JButton("¿Œº‚");
		bt_exit = new JButton("¥›±‚");
		
		table = new JTable(1,1);
		
		/*
		// excel
		ImageIcon icon_excel = new ImageIcon(this.getClass().getResource("/excel2.png"));
		Image  img_excel = icon_excel.getImage().getScaledInstance(40, 25, Image.SCALE_SMOOTH);
		icon_excel.setImage(img_excel);
		bt_excel = new JButton(icon_excel);

		// exit
		ImageIcon icon_exit = new ImageIcon(this.getClass().getResource("/exit.png"));
		Image  img_exit = icon_exit.getImage().getScaledInstance(40, 25, Image.SCALE_SMOOTH);
		icon_exit.setImage(img_exit);
		bt_exit = new JButton(icon_exit);		
		*/
		
		p_north_left.setBackground(Color.yellow);
		p_north_right.setBackground(Color.PINK);
		
		p_north_right.add(bt_retrieve);
		p_north_right.add(bt_excel);
		p_north_right.add(bt_print);
		p_north_right.add(bt_exit);
		
		p_north.add(p_north_left);
		p_north.add(p_north_right);
		
		p_center.setLayout(new BorderLayout());
		p_center.add(table);
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		
		// ∏ÆΩ∫≥ 
		bt_retrieve.addActionListener(this);
		bt_excel.addActionListener(this);
		bt_print.addActionListener(this);
		bt_exit.addActionListener(this);
		
		setVisible(true);
		setBounds(400, 0, 700, 700);
		
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj==bt_retrieve){			
			retrieve();
		} else if (obj==bt_excel){
			excelDown();
		} else if (obj==bt_print){
			tablePrint();
		} else if (obj==bt_exit){			
			frameExit();
		}
	}
	
	public void retrieve(){
		
	}
	
	public void excelDown(){
		
	}
	
	public void tablePrint(){
		
	}
	
	public void frameExit(){
		System.exit(0);
	}
	
	public static void main(String[] args) {
		new OutputFrame();
	}

}
