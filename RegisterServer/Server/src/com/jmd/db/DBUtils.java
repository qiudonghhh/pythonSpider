package com.jmd.db;

import java.sql.Connection;
import java.util.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.jmd.domain.ExamBean;


public class DBUtils {
	private Connection conn;
	private String url = "jdbc:mysql://127.0.0.1:3306/registerdatabase?serverTimezone=UTC"; // ָ���������ݿ��URL
	private String user = "root"; // ָ���������ݿ���û���
	private String password = "123456"; // ָ���������ݿ������
	private Statement sta;
	private ResultSet rs; // �����ݿ�����
	private ResultSet rs_exam; // �����ݿ�����


	public void openConnect() {
	try {
	     // �������ݿ�����
	     Class.forName("com.mysql.jdbc.Driver");
	     conn = DriverManager.getConnection(url, user, password);// �������ݿ�����
	if (conn != null) {
	    System.out.println("���ݿ����ӳɹ�"); // ���ӳɹ�����ʾ��Ϣ
	}
	} catch (Exception e) {
	   System.out.println("ERROR: " + e.getMessage());
	}
	}


	// ��ò�ѯuser��������ݼ�
	public ResultSet getUser() {
	// ���� statement����
		try {
		   sta = conn.createStatement(); // ִ��SQL��ѯ���
		   rs = sta.executeQuery("select * from user");
		} catch (SQLException e) {
		   e.printStackTrace();
		}
		   return rs;
		}
	
	// ��ò�ѯexam��������ݼ�
	public ResultSet getExam() {
		// ���� statement����
		try {
		   sta = conn.createStatement(); // ִ��SQL��ѯ���
		   rs_exam = sta.executeQuery("select * from exam");
		} catch (SQLException e) {
		   e.printStackTrace();
		}
		   return rs_exam;
		}
	//
	public List<ExamBean> FindAll() {
		rs_exam = getExam();
		
		List<ExamBean> ebList=new ArrayList<>();
		if (rs_exam != null) {
			try {
				while(rs_exam.next()) {
					ExamBean eb=new ExamBean();
					int exam_id = rs_exam.getInt("exam_id");
					String exam_question = rs_exam.getString("exam_question");
					String exam_answera = rs_exam.getString("exam_answera");
					String exam_answerb = rs_exam.getString("exam_answerb");
					String exam_answerc = rs_exam.getString("exam_answerc");
					String exam_answerd = rs_exam.getString("exam_answerd");
					int exam_answer = rs_exam.getInt("exam_answer");
					String exam_explaination =  rs_exam.getString("exam_explaination");
					
					//��ʾû���κ�ѡ��(����Ϊ-1)
					eb.setSelectedAnswer(-1);
					eb.setId(exam_id);
					eb.setQuestion(exam_question);
					eb.setAnswera(exam_answera);
					eb.setAnswerb(exam_answerb);
					eb.setAnswerc(exam_answerc);
					eb.setAnswerd(exam_answerd);
					eb.setAnswer(exam_answer);
					eb.setExplaination(exam_explaination);
					
					ebList.add(eb);
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ebList;
	}
		

	// �ж����ݿ����Ƿ����ĳ���û�����������,ע��͵�¼��ʱ���ж�
	public boolean isExistInDB(String username, String password) {
	boolean isFlag = false; // ���� statement����
	try {
	     System.out.println("�ж��û�������");
	     sta = conn.createStatement(); // ִ��SQL��ѯ���
	     rs = sta.executeQuery("select * from user");// ��ý����
	if (rs != null) {
	   while (rs.next()) { // ���������
	        if (rs.getString("user_name").equals(username)) {
	        	if (rs.getString("user_pwd").equals(password)) {
	        		isFlag = true;
	        		break;
	      }
	    }
	   }
	  }
	} catch (SQLException e) {
	    e.printStackTrace();
	    isFlag = false;
	}
	   return isFlag;
	}


	// ע�� ���û�����������뵽���ݿ�(id���õ����������ģ���˲���Ҫ����)
	public boolean insertDataToDB(String username, String password) {
	     String sql = " insert into user ( user_name , user_pwd ) values ( " + "'" + username + "', " + "'" + password
	+ "' )";
	try {
	    sta = conn.createStatement();
	    System.out.println("�����ݿ��в�������");
	    // ִ��SQL��ѯ���
	    return sta.execute(sql);
	} catch (SQLException e) {
	  e.printStackTrace();
	}
	  return false;
	}

	

	// �ر����ݿ�����
	public void closeConnect() {
	try {
	    if (rs != null) {
	    rs.close();
	   }
	    if (sta != null) {
	    sta.close();
	   }
	   if (conn != null) {
	     conn.close();
	   }
	  System.out.println("�ر����ݿ����ӳɹ�");
	} catch (SQLException e) {
	   System.out.println("Error: " + e.getMessage());
	}
  }

}