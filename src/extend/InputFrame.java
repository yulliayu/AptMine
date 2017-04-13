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
		
		bt_reset = new JButton("초기화");
		bt_regist = new JButton("등록");
		bt_exit = new JButton("닫기");
		
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
		if (obj==bt_reset){// 초기화
			reset();
		} else if (obj==bt_regist){ // 등록
			regist();
		} else if (obj==bt_exit){ // 닫기
			frameExit();
		}
	}
	
	abstract public void reset()/*등록*/;
	
	abstract public void regist();

	public void frameExit(){
		System.exit(0);
	}
 
}
