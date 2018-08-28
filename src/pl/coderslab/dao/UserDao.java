package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.coderslab.dao.DbUtils;
import pl.coderslab.domain.User;

public class UserDao {

	private static final String INSERT_USER_STATEMENT = "INSERT INTO Users(username, email, password) VALUES (?, ?, ?)";

	private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM Users where id=?";

	private static final String FIND_ALL_USERS = "SELECT * FROM Users";
	
	private static final String FIND_ALL_USERS_LIMIT = "SELECT * FROM Users LIMIT ?";

	private static final String UPDATE_USER_STATEMENT = "UPDATE Users SET username=?, email=?, password=? where id = ?";

	private static final String DELETE_USER_STATEMENT = "DELETE FROM Users WHERE id= ?";

	private static final String PASSWORD_COLUMN_NAME = "password";
	private static final String ID_COLUMN_NAME = "ID";
	private static final String USERNAME_COLUMN_NAME = "username";
	private static final String EMAIL_COLUMN_NAME = "email";
	
	public void saveNewUser(User user) throws SQLException {
		Connection conn = DbUtils.getConn();

		String generatedColumns[] = { ID_COLUMN_NAME };
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(INSERT_USER_STATEMENT, generatedColumns);
		preparedStatement.setString(1, user.getUsername());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.getPassword());
		preparedStatement.executeUpdate();

		ResultSet rs = preparedStatement.getGeneratedKeys();
		if (rs.next()) {
			user.setId(rs.getLong(1));
		}
		conn.close();
	}

	public void updateUser(User user) throws SQLException {
		Connection conn = DbUtils.getConn();

		PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_USER_STATEMENT);
		preparedStatement.setString(1, user.getUsername());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.getPassword());
		preparedStatement.setLong(4, user.getId());
		preparedStatement.executeUpdate();
		conn.close();
	}

	public void delete(User user) throws SQLException {
		if (user.getId() != 0) {
			Connection conn = DbUtils.getConn();
			
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER_STATEMENT);
			preparedStatement.setLong(1, user.getId());
			preparedStatement.executeUpdate();
			user.setId(1L);
			
			conn.close();
		}
	}

	public static User loadUserById(long id) throws SQLException {
		Connection conn = DbUtils.getConn();
		PreparedStatement preparedStatement = conn.prepareStatement(FIND_USER_BY_ID_QUERY);
		preparedStatement.setLong(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();

		conn.close();
		if (resultSet.next()) {
			return createUser(resultSet);
		}

		return null;
	}

	public static User createUser(ResultSet resultSet) throws SQLException {
		String username = resultSet.getString(USERNAME_COLUMN_NAME);
		String password = resultSet.getString(PASSWORD_COLUMN_NAME);
		String email = resultSet.getString(EMAIL_COLUMN_NAME);
		User loadedUser = new User(username, email, password);
		loadedUser.setId(resultSet.getLong(ID_COLUMN_NAME));
		return loadedUser;
	}

	public static User[] loadAllUsers() throws SQLException {
		Connection conn = DbUtils.getConn();
		List<User> users = new ArrayList<>();
		PreparedStatement preparedStatement = conn.prepareStatement(FIND_ALL_USERS);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			users.add(createUser(resultSet));
		}

		User[] uArray = new User[users.size()];
		
		conn.close();
		return users.toArray(uArray);
	}
	
	public static User[] loadAllUsers(Integer limit) throws SQLException {
		Connection conn = DbUtils.getConn();
		List<User> users = new ArrayList<>();
		PreparedStatement preparedStatement = conn.prepareStatement(FIND_ALL_USERS_LIMIT);
		preparedStatement.setInt(1, limit);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			users.add(createUser(resultSet));
		}

		User[] uArray = new User[users.size()];
		
		conn.close();
		return users.toArray(uArray);
	}

}