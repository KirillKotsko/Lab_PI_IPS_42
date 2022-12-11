package ua.kotsko.project.Examinator.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ua.kotsko.project.Examinator.models.Assigment;
import ua.kotsko.project.Examinator.models.ResultExam;

@Component
public class ResultExamRepository extends AbstractRepository {

	private static final String SELECT_ALL_RESULTS =
			"SELECT * FROM results_exam";
	private static final String SELECT_ALL_WITH_USER_ID =
			"SELECT * FROM results_exam WHERE user_id = ?";
	private static final String SELECT_ALL_WITH_EXAM_ID =
			"SELECT * FROM results_exam WHERE exam_id = ?";
	private static final String INSERT =
			"INSERT INTO results_exam(user_id, exam_id, mark, when_pass) VALUES (?, ?, ?, ?)";
	private static final String DELETE_USER_ID =
			"DELETE FROM results_exam WHERE user_id = ?";
	private static final String DELETE_EXAM_ID =
			"DELETE FROM results_exam WHERE exam_id = ?";
	private static final String DELETE =
			"DELETE FROM results_exam WHERE user_id = ? AND exam_id = ?";

	@Autowired
	public ResultExamRepository(Environment environment) {
		super(environment);
	}

	public List<ResultExam> findAll() throws SQLException {
		List<ResultExam> resultExams = new ArrayList<>();
		try (Connection con = DriverManager.getConnection(URL);
			Statement statement = con.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_ALL_RESULTS);
			while (rs.next()) {
				Long user_id = rs.getLong("user_id");
				Long exam_id = rs.getLong("exam_id");
				Long mark = rs.getLong("mark");
				String date = rs.getString("when_pass");
				ResultExam resultExam = new ResultExam();
				resultExam.setUserId(user_id);
				resultExam.setExamId(exam_id);
				resultExam.setMark(mark);
				resultExam.setWhen(date);
				resultExams.add(resultExam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultExams;
	}
	
	public List<ResultExam> findByUserId(Long user_id) throws SQLException {
		List<ResultExam> resultExams = new ArrayList<>();
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement statement = con.prepareStatement(SELECT_ALL_WITH_USER_ID)) {
			statement.setLong(1, user_id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long exam_id = rs.getLong("exam_id");
				Long mark = rs.getLong("mark");
				String date = rs.getString("when_pass");
				ResultExam resultExam = new ResultExam();
				resultExam.setUserId(user_id);
				resultExam.setExamId(exam_id);
				resultExam.setMark(mark);
				resultExam.setWhen(date);
				resultExams.add(resultExam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultExams;
	}
	
	public List<ResultExam> findByExamId(Long exam_id) throws SQLException {
		List<ResultExam> resultExams = new ArrayList<>();
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement statement = con.prepareStatement(SELECT_ALL_WITH_EXAM_ID)) {
			statement.setLong(1, exam_id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long user_id = rs.getLong("user_id");
				Long mark = rs.getLong("mark");
				String date = rs.getString("when_pass");
				ResultExam resultExam = new ResultExam();
				resultExam.setUserId(user_id);
				resultExam.setExamId(exam_id);
				resultExam.setMark(mark);
				resultExam.setWhen(date);
				resultExams.add(resultExam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultExams;
	}

	public void save(ResultExam resultExam) throws SQLException {
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement statement = con.prepareStatement(INSERT))  {
			resultExam.setCurrentDate();
			statement.setLong(1, resultExam.getUserId());
			statement.setLong(2, resultExam.getExamId());
			statement.setLong(3, resultExam.getMark());
			statement.setString(4, resultExam.getWhen());
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

