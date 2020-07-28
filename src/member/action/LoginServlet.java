package member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.dao.MemberDAO;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		
		String name = memberDAO.login(id, pwd);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		if(name!=null) out.println(name+"님 환영합니다!");
		else out.println("아이디 비밀번호를 확인해 주세요.");
		out.println("<br>");
		//out.println("<a href='http://localhost:8080/memberServlet/member/loginForm.html'>뒤로</a>");
		//out.println("<a href='javascript:history.go(-1)'>뒤로</a>");
		out.println("<input type = button value=뒤로 onclick='javascript:history.go(-1)'>");
		out.println("</body>");
		out.println("</html>");
	}
}
