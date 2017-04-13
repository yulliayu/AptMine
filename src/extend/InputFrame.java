package extend;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

abstract public class InputFrame extends JFrame implements ActionListener{
	
	public JPanel  p_center, p_south;
	public JButton  bt_reset, bt_regist, bt_exit;
	
	public InputFrame() {
		
		p_center = new JPanel();
		p_south = new JPanel();
		
		bt_reset = new JButton("�ʱ�ȭ");
		bt_regist = new JButton("���");
		bt_exit = new JButton("�ݱ�");
		
		p_south.add(bt_reset);
		p_south.add(bt_regist);
		p_south.add(bt_exit);
		
		p_center.setLayout(new FlowLayout());
		
		add(p_center);
		add(p_south, BorderLayout.SOUTH);

		setVisible(true);
		setSize(700, 700);
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj==bt_reset){// �ʱ�ȭ
			reset();
		} else if (obj==bt_regist){ // ���
			regist();
		} else if (obj==bt_exit){ // �ݱ�
			frameExit();
		}
	}
	
	abstract public void reset()/*���*/;
	
	abstract public void regist();

	public void frameExit(){
		System.exit(0);
	}
 
}
