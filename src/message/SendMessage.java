package message;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import db.DBManager;

public class SendMessage extends JFrame{
	
	DBManager  instance = DBManager.getInstance();
	Connection con;
	
	JPanel  p_north, p_center;
	JTextArea   area;
	Vector<ComplexDto>  complexList = new Vector<ComplexDto>();
	Vector<Checkbox>  chkList = new Vector<Checkbox>();
	JButton  bt_send;
	Choice   ch_complex;
	
	public SendMessage() {
		
		p_north = new JPanel();
		p_center = new JPanel();
		
		area = new JTextArea();
		bt_send = new JButton("º¸³»±â");
		ch_complex = new Choice();
		
		p_north.add(area);
		p_north.add(bt_send);
		
		p_center.add(ch_complex);
		
		area.setPreferredSize(new Dimension(150, 120));
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		
		init();
		
		setVisible(true);
		setSize(700, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public void init(){
		
		// DB Connect
		con = instance.getConnection();
		
		getComplex();
	}
	
	public void getComplex(){
		PreparedStatement  pstmt=null;
		ResultSet  rs=null;
		
		String sql = "select * from complex order by complex_id ";
		
		complexList.removeAll(complexList);
		ch_complex.removeAll();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				ComplexDto  dto = new ComplexDto();
				dto.setComplex_id(rs.getInt("complex_id"));
				dto.setComplex_name(rs.getString("complex_name"));
				
				complexList.add(dto);
				ch_complex.add(dto.getComplex_name());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void getUnits(){
		
	}

	public static void main(String[] args) {
		new SendMessage();

	}

}
