package message;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import db.DBManager;

public class SendMessage extends JFrame implements ActionListener{
	
	DBManager  instance = DBManager.getInstance();
	Connection con;
	
	JPanel  p_south, p_center, p_north;
	JTextArea   area;
	Vector<ComplexDto>  complexList = new Vector<ComplexDto>();
	Vector<Checkbox>  chkList = new Vector<Checkbox>();
	JButton  bt_send, bt_search;
	JTable   table;
	JScrollPane  scroll;
	CompUnitModel  model;
	JTextField  t_input, t_title;
	JLabel  la_title;
	
	public SendMessage() {
		
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		
		t_input = new JTextField(20);
		bt_search = new JButton("검색");
		
		la_title = new JLabel("제목");
		t_title = new JTextField(40);
		area = new JTextArea();
		bt_send = new JButton("보내기");
		
		table = new JTable();
		scroll = new JScrollPane(table);
		
		// color
		p_north.setBackground(Color.PINK);
		p_center.setBackground(Color.YELLOW);
		
		// size
		p_south.setPreferredSize(new Dimension(700, 100));
		p_north.setPreferredSize(new Dimension(700, 50));
		area.setPreferredSize(new Dimension(550, 50));
		
		p_north.add(t_input);
		p_north.add(bt_search);
		
		p_center.setLayout(new BorderLayout());
		p_center.add(p_north, BorderLayout.NORTH);
		p_center.add(scroll);
		
		p_south.add(la_title);
		p_south.add(t_title);
		p_south.add(area);
		p_south.add(bt_send);		
		
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		
		// 리스너 연결
		bt_send.addActionListener(this);
		bt_search.addActionListener(this);
		
		init();
		
		setTitle("송신 메세지");
		setVisible(true);
		setSize(700, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	// 초기 작업
	public void init(){
		
		// DB Connect
		con = instance.getConnection();
		
		model = new CompUnitModel(con);
		table.setModel(model);
		
		table.updateUI();
		
	}
	
	// 메세지 보내기
	public void send(){
		
		//System.out.println(.length);
		int[] rows = table.getSelectedRows();
		
		if (rows.length ==0){
			JOptionPane.showMessageDialog(this, "메세지 보낼 호수를 선택해 주세요?");
			return;
		}
		
		PreparedStatement pstmt=null;
		ResultSet  rs = null;
		int msg_send_id=0;
		String msg_content = area.getText();
		
		// msg_send_id 받아 놓기
		String sql = "select seq_send_message.nextval msg_send_id from dual ";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				msg_send_id = rs.getInt("msg_send_id");
			}
			
			//보내는 쪽지
			String send_user_id = "test"; // 임시로 사용. 나중에 로그인 아이디 받아 와야 함.
			sql = " insert into send_message (msg_send_id, msg_sendtime, msg_send_content, user_id) "
			     + " values (?, sysdate, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, msg_send_id);
			pstmt.setString(2, msg_content);
			pstmt.setString(3, send_user_id);
			int result = pstmt.executeUpdate();
			
			int user_id_col = model.findColumn("user_id");
			int count=0;
			if (result !=0){
				// send 메세지 등록 성공하면, 받는 쪽지 (recieve msg) 등
				for (int i=0; i<rows.length;i++){
					String sql_in = "insert into recieve_message (msg_recieve_id, msg_send_id, user_id, msg_recieve_time, msg_regdate) "
							           + " values (seq_recieve_message.nextval, ?, ?, null, sysdate)";
					pstmt = con.prepareStatement(sql_in);
					pstmt.setInt(1, msg_send_id);
					System.out.println("id = "+msg_send_id+", row = "+rows[i]+", col="+user_id_col+", value = "+table.getValueAt(rows[i], user_id_col));
					String user_id = (String)table.getValueAt(rows[i], user_id_col);
					pstmt.setString(2, user_id);
					System.out.println(sql_in);
					count+=pstmt.executeUpdate();
					System.out.println(count);
				}
				
				if (count !=0){
					JOptionPane.showMessageDialog(this, count+"건 insert");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
	}
	
	// 검색
	public void search(){
		String srch = t_input.getText();
		model.getList(srch);
		table.updateUI();
		
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj==bt_send){
			send();
		} else if (obj==bt_search){
			search();
		}
		
	}

	public static void main(String[] args) {
		new SendMessage();

	}

}
