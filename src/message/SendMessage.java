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
	JTextField  t_input;
	
	public SendMessage() {
		
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		
		t_input = new JTextField(20);
		bt_search = new JButton("검색");
		
		area = new JTextArea();
		bt_send = new JButton("보내기");
		
		table = new JTable();
		scroll = new JScrollPane(table);
		
		// color
		p_north.setBackground(Color.PINK);
		p_center.setBackground(Color.YELLOW);
		
		// size
		p_south.setPreferredSize(new Dimension(700, 70));
		p_north.setPreferredSize(new Dimension(700, 40));
		area.setPreferredSize(new Dimension(550, 50));
		
		p_north.add(t_input);
		p_north.add(bt_search);
		
		p_center.setLayout(new BorderLayout());
		p_center.add(p_north, BorderLayout.NORTH);
		p_center.add(scroll);
		
		p_south.add(area);
		p_south.add(bt_send);		
		
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		
		// 리스너 연결
		bt_send.addActionListener(this);
		bt_search.addActionListener(this);
		
		init();
		
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
			
			//pstmt = null;
			sql = " insert into send_message (msg_send_id, msg_sendtime, msg_send_content) "
			     + " values (?, sysdate, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, msg_send_id);
			pstmt.setString(2, msg_content);
			int result = pstmt.executeUpdate();
			StringBuilder  sb= new StringBuilder();
			int user_id_col = model.findColumn("user_id");
			if (result !=0){
				// send 메세지 등록 성공하면, recieve msg 등록
				sql = "insert into recieve_message (msg_recieve_id, msg_send_id, user_id, msg_recieve_time, msg_regdate) "
					 + " values (seq_recieve_message.nextval, ?, ?, null, sysdate)";
				for (int i=0; i<rows.length;i++){
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, msg_send_id);
					int user_id = (Integer)table.getValueAt(rows[i], user_id_col);
					pstmt.setInt(2, user_id);
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
		
		
		
		
		for (int i=0; i<rows.length;i++){
			System.out.println("length = "+rows.length  + ", i = "+i + "-"+rows[i]);
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
