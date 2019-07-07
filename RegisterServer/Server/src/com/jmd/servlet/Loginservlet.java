package com.jmd.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jmd.db.DBUtils;
import com.jmd.domain.BaseBean;
import com.jmd.domain.UserBean;

public class Loginservlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request--->" + request.getRequestURL() + "====" + request.getParameterMap().toString());
		String username = request.getParameter("username"); // ��ȡ�ͻ��˴������Ĳ���
		String password = request.getParameter("password");
		response.setContentType("text/html;charset=utf-8");
		if (username==null||username.equals("")||password.equals("")|| password==null) {
			System.out.println("�û���������Ϊ��");

		} // �������ݿ�
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		// �����ݿ�����
		BaseBean basebean = new BaseBean(); // ������󣬻ش����ͻ��˵�json����
		UserBean userBean = new UserBean(); // user�Ķ���
		// �ж��˺��Ƿ����
		if (dbUtils.isExistInDB(username, password)) {
			//�˺Ŵ���
			basebean.setCode(0);
			//basebean.setData(userBean);
			basebean.setMsg("��¼�ɹ�");
		} else { //�˺Ų�����
			// ע��ɹ�
			basebean.setCode(-1);
			basebean.setMsg("��û��ע��������벻��ȷ!!");
			
			/*userBean.setUsername(username);
			userBean.setPassword(password);*/
			//basebean.setData(userBean);
			}
		 
		// ��Gson���������л���json
		Gson gson = new Gson();
		// ������ת����json�ַ���
		String json = gson.toJson(basebean);
		try {
			response.getWriter().println(json);
			// ��json���ݴ����ͻ���
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // �ر����������Ȼ�ᷢ�������
		}
		dbUtils.closeConnect(); // �ر����ݿ�����
	}

}