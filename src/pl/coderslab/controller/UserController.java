package pl.coderslab.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.UserDao;
import pl.coderslab.domain.User;
import pl.coderslab.service.UserService;

@WebServlet("/")
public class UserController extends HttpServlet{
	
	UserService userService = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User user = new User("username","email","password");
		
		try {
			userService.saveOrUpdate(user);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Integer limit = Integer.parseInt(getServletContext().getInitParameter("number-users"));
		System.out.println("limit " + limit);
		try {
			System.out.println("znaleziono " + userService.findUsers(limit).length);
			
			req.setAttribute("users", userService.findUsers(limit)); 
		
			getServletContext()
				.getRequestDispatcher("/WEB-INF/jsp/index.jsp")
				.forward(req, resp);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}