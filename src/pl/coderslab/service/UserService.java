package pl.coderslab.service;

import java.sql.SQLException;

import pl.coderslab.dao.UserDao;
import pl.coderslab.domain.User;

public class UserService {
	
	UserDao dao = new UserDao();
	
	public void saveOrUpdate(User user) throws SQLException {
		if (user.getId() == 0) {
			dao.saveNewUser(user);
		} else {
			dao.updateUser(user);
		}
	}
	
	public User [] findUsers(Integer limit) throws SQLException {
		return dao.loadAllUsers(limit);
	}
	
	public User [] findUsers() throws SQLException {
		return dao.loadAllUsers();
	}
	
	
	
}