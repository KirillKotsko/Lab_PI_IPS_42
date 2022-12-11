package ua.kotsko.project.Examinator.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ua.kotsko.project.Examinator.models.Assigment;

@Component
public class AssigmentRepository extends AbstractRepository {

	private static final String SELECT_ALL_ASSIGMENTS =
			"SELECT * FROM assignments";
	private static final String SELECT_ALL_WITH_USER_ID =
			"SELECT * FROM assignments WHERE user_id = ?";
	private static final String SELECT_ALL_WITH_EXAM_ID =
			"SELECT * FROM assignments WHERE exam_id = ?";
	private static final String INSERT =
			"INSERT INTO assignments(user_id, exam_id, description) VALUES (?, ?, ?)";
	private static final String DELETE_USER_ID =
			"DELETE FROM assignments WHERE user_id = ?";
	private static final String DELETE_EXAM_ID =
			"DELETE FROM assignments WHERE exam_id = ?";
	private static final String DELETE =
			"DELETE FROM assignments WHERE user_id = ? AND exam_id = ?";

	@Autowired
	public AssigmentRepository(Environment environment) {
		super(environment);
	}

	public List<Assigment> findAll() throws SQLException {
		List<Assigment> assigments = new ArrayList<>();
		try (Connection con = DriverManager.getConnection(URL);
			Statement statement = con.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_ALL_ASSIGMENTS);
			while (rs.next()) {
				Long user_id = rs.getLong("user_id");
				Long exam_id = rs.getLong("exam_id");
				String description = rs.getString("description");
				Assigment assigment = new Assigment();
				assigment.setUserId(user_id);
				assigment.setExamId(exam_id);
				assigment.setDescription(description);
				assigments.add(assigment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return assigments;
	}
	
	public List<Assigment> findByUserId(Long user_id) throws SQLException {
		List<Assigment> assigments = new ArrayList<>();
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement statement = con.prepareStatement(SELECT_ALL_WITH_USER_ID)) {
			statement.setLong(1, user_id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long exam_id = rs.getLong("exam_id");
				String description = rs.getString("description");
				Assigment assigment = new Assigment();
				assigment.setUserId(user_id);
				assigment.setExamId(exam_id);
				assigment.setDescription(description);
				assigments.add(assigment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return assigments;
	}
	
	public List<Assigment> findByExamId(Long exam_id) throws SQLException {
		List<Assigment> assigments = new ArrayList<>();
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement statement = con.prepareStatement(SELECT_ALL_WITH_EXAM_ID)) {
			statement.setLong(1, exam_id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long user_id = rs.getLong("user_id");
				String description = rs.getString("description");
				Assigment assigment = new Assigment();
				assigment.setUserId(user_id);
				assigment.setExamId(exam_id);
				assigment.setDescription(description);
				assigments.add(assigment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return assigments;
	}

	public void save(Assigment assigment) throws SQLException {
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement statement = con.prepareStatement(INSERT))  {
			statement.setLong(1, assigment.getUserId());
			statement.setLong(2, assigment.getExamId());
			statement.setString(3, assigment.getDescription());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(Long user_id, Long exam_id) throws SQLException {
		try (Connection con = DriverManager.getConnection(URL);
			 PreparedStatement statement = con.prepareStatement(DELETE)) {
			statement.setLong(1, user_id);
			statement.setLong(2, exam_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUI(Long user_id) throws SQLException {
		try (Connection con = DriverManager.getConnection(URL);
			 PreparedStatement statement = con.prepareStatement(DELETE_USER_ID)) {
			statement.setLong(1, user_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEI(Long exam_id) throws SQLException {
		try (Connection con = DriverManager.getConnection(URL);
			 PreparedStatement statement = con.prepareStatement(DELETE_EXAM_ID)) {
			statement.setLong(1, exam_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

