package javaResources;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/pick")
public class MemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hanahr?useUnicode=true&characterEncoding=utf8", "root", "1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("candidatePick.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String process = request.getParameter("process");
        String headCount = request.getParameter("headCount");

        UserDAO dao = new UserDAO();
        List<Map<String, String>> findResult = dao.pickPasser(process, headCount);
        request.setAttribute("passer", findResult);

        RequestDispatcher dispatcher = request.getRequestDispatcher("candidatePick.jsp");
        dispatcher.forward(request, response);
    }
}
