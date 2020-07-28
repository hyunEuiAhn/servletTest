package member.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import member.bean.MemberDTO;

public class MemberDAO {
	private static MemberDAO instance;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "java";
	private String password = "itbank";
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;	
	
	public static MemberDAO getInstance() {
		if(instance == null) {
			synchronized(MemberDAO.class) {//여러 사용자가 못 들어오게 막아라
				instance = new MemberDAO();
			}
		}
		return instance;
	}
	
	
	
	public MemberDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	}
	
	
	
	public void getConnection(){
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("접속 성공!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int write(MemberDTO memberDTO) {
		int su=0;
		String sql = "insert into member values(?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		
		getConnection(); //접속
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getName());
			pstmt.setString(2, memberDTO.getId());
			pstmt.setString(3, memberDTO.getPwd());
			pstmt.setString(4, memberDTO.getGender());
			pstmt.setString(5, memberDTO.getEmail1());
			pstmt.setString(6, memberDTO.getEmail2());
			pstmt.setString(7, memberDTO.getTel1());
			pstmt.setString(8, memberDTO.getTel2());
			pstmt.setString(9, memberDTO.getTel3());
			pstmt.setString(10, memberDTO.getZipcode());
			pstmt.setString(11, memberDTO.getAddr1());
			pstmt.setString(12, memberDTO.getAddr2());
			su = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();	//null일 때 에러가 발생할 수 있기 때문에 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return su;
	}



	public String login(String id, String pwd) {
		String name = null;
		String sql = "select name, id, pwd from member where id='"+id+"' and pwd='"+pwd+"'";
		
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				name=rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();	//null일 때 에러가 발생할 수 있기 때문에 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return name;
	}
	
}
