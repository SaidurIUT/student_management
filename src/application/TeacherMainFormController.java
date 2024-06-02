package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TeacherMainFormController implements Initializable {

	@FXML
	private AnchorPane main_form;

	@FXML
	private Label greet_name;

	@FXML
	private Label teacher_id;

	@FXML
	private Button addStudent_btn;

	@FXML
	private Button subjectHandle_btn;

	@FXML
	private Button logout_btn;

	@FXML
	private Button attendance_btn;

	@FXML
	private Button mark_btn;

	@FXML
	private Label current_form;

	@FXML
	private AnchorPane addStudents_form;

	@FXML
	private ComboBox<String> addStudents_course;

	@FXML
	private ComboBox<String> addStudents_year;

	@FXML
	private Label addStudents_label_fullName;

	@FXML
	private Label addStudents_label_gender;

	@FXML
	private ComboBox<String> addStudents_studentID;

	@FXML
	private ComboBox<String> addStudents_subject;

	@FXML
	private Label addStudents_label_semester;

	@FXML
	private Label addStudents_label_year;

	@FXML
	private Label addStudents_label_course;

	@FXML
	private Button addStudents_addBtn;

	@FXML
	private Button addStudents_clearBtn;

	@FXML
	private Button addStudents_removeBtn;

	@FXML
	private TableView<DataStudentHandle> addStudents_tableView;

	@FXML
	private TableColumn<DataStudentHandle, String> addStudents_col_studentID;

	@FXML
	private TableColumn<DataStudentHandle, String> addStudents_col_name;

	@FXML
	private TableColumn<DataStudentHandle, String> addStudents_col_gender;

	@FXML
	private TableColumn<DataStudentHandle, String> addStudents_col_course;

	@FXML
	private TableColumn<DataStudentHandle, String> addStudents_col_year;

	@FXML
	private TableColumn<DataStudentHandle, String> addStudents_col_semester;

	@FXML
	private TableColumn<DataStudentHandle, String> addStudents_col_date;

	@FXML
	private TableColumn<DataStudentHandle, String> addStudents_col_subject;

	@FXML
	private AnchorPane subjectHandle_form;

	@FXML
	private ComboBox<String> subjecthandle_subject;

	@FXML
	private Button subjecthandle_addBtn;

	@FXML
	private Button subjecthandle_clearBtn;

	@FXML
	private Button subjecthandle_removeBtn;

	@FXML
	private ComboBox<String> subjecthandle_code;

	@FXML
	private ComboBox<String> subjecthandle_status;

	@FXML
	private TableView<DataSubjectHandle> subjecthandle_tableView;

	@FXML
	private TableColumn<DataSubjectHandle, String> subjecthandle_col_subjectCode;

	@FXML
	private TableColumn<DataSubjectHandle, String> subjecthandle_col_subjectName;

	@FXML
	private TableColumn<DataSubjectHandle, String> subjecthandle_col_dateInsert;

	@FXML
	private TableColumn<DataSubjectHandle, String> subjecthandle_col_status;

	@FXML
	private AnchorPane attendance_form;

	// add attendance form attributes here

	@FXML
	private Button take_attendance_btn;

	@FXML
	private Button attendance_present_btn;

	@FXML
	private Button attendance_absent_btn;

	@FXML
	private Label attendance_sID;

	@FXML
	private ComboBox<String> attendance_course;

	@FXML
	private TableView<DataAttendanceHandle> attendance_table_view;

	@FXML
	private TableColumn<DataAttendanceHandle, String> attendance_col_studentID;

	@FXML
	private TableColumn<DataAttendanceHandle, String> attendance_col_name;

	@FXML
	private TableColumn<DataAttendanceHandle, Integer> attendance_col_total_class;

	@FXML
	private TableColumn<DataAttendanceHandle, Integer> attendance_col_attended;

	@FXML
	private TableColumn<DataAttendanceHandle, Float> attendance_col_percentice;

	@FXML
	private AnchorPane mark_form;

	// add mark form attributes here

	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	private Statement statement;

	private AlertMessage alert = new AlertMessage();

	public void switchForm(ActionEvent event) {

		if (event.getSource() == addStudent_btn) {
			addStudents_form.setVisible(true);
			subjectHandle_form.setVisible(false);
			attendance_form.setVisible(false);
			mark_form.setVisible(false);

			addStudentCourseList();
			addStudentsYearList();
			addStudentDisplayData();

			current_form.setText("Add Students form");
		} else if (event.getSource() == subjectHandle_btn) {
			addStudents_form.setVisible(false);
			subjectHandle_form.setVisible(true);
			attendance_form.setVisible(false);
			mark_form.setVisible(false);

			subjectHandleSubjectCodeList();
			subjectHandleStatusList();
			subjectHandleDisplayData();

			current_form.setText("Subject Handles form");
		} else if (event.getSource() == attendance_btn) {
			addStudents_form.setVisible(false);
			subjectHandle_form.setVisible(false);
			attendance_form.setVisible(true);
			mark_form.setVisible(false);

			loadAttendanceCourses();

			current_form.setText("Attendance form");
		} else if (event.getSource() == mark_btn) {
			addStudents_form.setVisible(false);
			subjectHandle_form.setVisible(false);
			attendance_form.setVisible(false);
			mark_form.setVisible(true);

			current_form.setText("Mark form");
		}

	}

	public void addStudentsAddBtn() {

		if (addStudents_course.getSelectionModel().getSelectedItem().isEmpty()
				|| addStudents_year.getSelectionModel().getSelectedItem().isEmpty()
				|| addStudents_studentID.getSelectionModel().getSelectedItem().isEmpty()) {
			alert.errorMessage("Please fill all blank fields");
		} else {

			String insertData = "INSERT INTO teacher_student " + "(teacher_id, stud_studentID, stud_name, stud_gender"
					+ ", stud_year, stud_course, stud_semester"
					+ ", date_insert, status, subject_code) VALUES(?,?,?,?,?,?,?,?,?,?)";
			connect = Database.connectDB();

			Date date = new Date();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());

			try {
				prepare = connect.prepareStatement(insertData);
				prepare.setString(1, teacher_id.getText());
				prepare.setString(2, addStudents_studentID.getSelectionModel().getSelectedItem());
				prepare.setString(3, addStudents_label_fullName.getText());
				prepare.setString(4, addStudents_label_gender.getText());
				prepare.setString(5, addStudents_year.getSelectionModel().getSelectedItem());
				prepare.setString(6, addStudents_course.getSelectionModel().getSelectedItem());
				prepare.setString(7, addStudents_label_semester.getText());
				prepare.setString(8, String.valueOf(sqlDate));
				prepare.setString(9, "Active");
				prepare.setString(10, addStudents_subject.getSelectionModel().getSelectedItem());

				prepare.executeUpdate();

				addStudentDisplayData();

				alert.successMessage("Added successfully!");

				addStudentClearBtn();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addStudentsRemoveBtn() {

		if (addStudents_course.getSelectionModel().getSelectedItem() == null
				|| addStudents_year.getSelectionModel().getSelectedItem() == null
				|| addStudents_studentID.getSelectionModel().getSelectedItem() == null) {
			alert.errorMessage("Please fill all blank fields");
		} else {
			if (alert.confirmMessage("Are you sure you want to delete Student ID:"
					+ addStudents_studentID.getSelectionModel().getSelectedItem())) {
				String insertData = "UPDATE teacher_student SET date_delete = ?, status = ? "
						+ "WHERE stud_studentID = ?";
				connect = Database.connectDB();

				Date date = new Date();
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());

				try {
					prepare = connect.prepareStatement(insertData);
					prepare.setString(1, String.valueOf(sqlDate));
					prepare.setString(2, "Inactive");
					prepare.setString(3, addStudents_studentID.getSelectionModel().getSelectedItem());

					prepare.executeUpdate();

					addStudentDisplayData();

					alert.successMessage("Deleted successfully!");

					addStudentClearBtn();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				alert.errorMessage("Cancelled.");
			}

		}
	}

	public void addStudentClearBtn() {
		addStudents_course.getSelectionModel().clearSelection();
		addStudents_year.getSelectionModel().clearSelection();
		addStudents_studentID.getSelectionModel().clearSelection();

		addStudents_label_fullName.setText("----------");
		addStudents_label_gender.setText("----------");
		addStudents_label_semester.setText("----------");
		addStudents_label_year.setText("----------");
		addStudents_label_course.setText("----------");
	}

	public ObservableList<DataStudentHandle> addStudentListData() {

		ObservableList<DataStudentHandle> listData = FXCollections.observableArrayList();
		String sql = "SELECT * FROM teacher_student WHERE teacher_id = '" + teacher_id.getText()
				+ "' AND date_delete IS NULL AND status = 'Active'";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			DataStudentHandle dshData;

			while (result.next()) {
//                DataStudentHandle(String studentID, String name, String gender,
//             String course, String year, String semester, Date dateInsert,
//             Date dateDelete, String status)
				dshData = new DataStudentHandle(result.getString("stud_studentID"), result.getString("stud_name"),
						result.getString("stud_gender"), result.getString("stud_course"), result.getString("stud_year"),
						result.getString("stud_semester"), result.getDate("date_insert"), result.getDate("date_delete"),
						result.getString("status"), result.getString("subject_code"));

				listData.add(dshData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	public ObservableList<DataStudentHandle> addStudentGetData;

	public void addStudentDisplayData() {
		addStudentGetData = addStudentListData();

		addStudents_col_studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		addStudents_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		addStudents_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		addStudents_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
		addStudents_col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
		addStudents_col_semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
		addStudents_col_date.setCellValueFactory(new PropertyValueFactory<>("dateInsert"));
		addStudents_col_subject.setCellValueFactory(new PropertyValueFactory<>("subject"));

		addStudents_tableView.setItems(addStudentGetData);

	}

	public void addStudentSelectitem() {
		DataStudentHandle dshData = addStudents_tableView.getSelectionModel().getSelectedItem();
		int num = addStudents_tableView.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}

		addStudents_course.getSelectionModel().select(dshData.getCourse());
		addStudents_year.getSelectionModel().select(dshData.getYear());
		addStudents_studentID.getSelectionModel().select(dshData.getStudentID());

		addStudents_label_fullName.setText(dshData.getName());
		addStudents_label_gender.setText(dshData.getGender());
		addStudents_label_semester.setText(dshData.getSemester());
		addStudents_label_year.setText(dshData.getYear());
		addStudents_label_course.setText(dshData.getCourse());

	}

	public void addStudentCourseList() {

		String sql = "SELECT * FROM course WHERE date_delete IS NULL";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			ObservableList listData = FXCollections.observableArrayList();

			while (result.next()) {
				listData.add(result.getString("course"));
			}

			addStudents_course.setItems(listData);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addStudentSubjectList() {

		String sql = "SELECT * FROM subject WHERE date_delete IS NULL";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			ObservableList listData = FXCollections.observableArrayList();

			while (result.next()) {
				listData.add(result.getString("subject_code"));
			}

			addStudents_subject.setItems(listData);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addStudentsYearList() {

		List<String> listY = new ArrayList<>();

		for (String data : ListData.year) {
			listY.add(data);
		}

		ObservableList listData = FXCollections.observableArrayList(listY);
		addStudents_year.setItems(listData);

		addStudentsStudentNumber();

	}

	public void addStudentsStudentNumber() {

		String sql = "SELECT * FROM student WHERE course = '" + addStudents_course.getSelectionModel().getSelectedItem()
				+ "' AND year = '" + addStudents_year.getSelectionModel().getSelectedItem()
				+ "' AND date_delete IS NULL";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			ObservableList listData = FXCollections.observableArrayList();

			while (result.next()) {
				listData.add(result.getString("student_id"));
			}

			addStudents_studentID.setItems(listData);

			addStudentsDisplayFields();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addStudentsDisplayFields() {

		String sql = "SELECT * FROM student WHERE student_id = '"
				+ addStudents_studentID.getSelectionModel().getSelectedItem() + "'";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			if (result.next()) {
				addStudents_label_fullName.setText(result.getString("full_name"));
				addStudents_label_gender.setText(result.getString("gender"));
				addStudents_label_semester.setText(result.getString("semester"));
				addStudents_label_year.setText(result.getString("year"));
				addStudents_label_course.setText(result.getString("course"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

//	public void subjectHandleAddBtn() {
//
//		if (subjecthandle_code.getSelectionModel().getSelectedItem().isEmpty()
//				|| subjecthandle_subject.getSelectionModel().getSelectedItem().isEmpty()
//				|| subjecthandle_status.getSelectionModel().getSelectedItem().isEmpty()) {
//			alert.errorMessage("Please fill all blank fields");
//		} else {
//			String insertData = "INSERT INTO teacher_handle (subject_code, subject, date, status) " + "VALUES(?,?,?,?)";
//			connect = Database.connectDB();
//
//			Date date = new Date();
//			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//
//			try {
//				prepare = connect.prepareStatement(insertData);
//				prepare.setString(1, subjecthandle_code.getSelectionModel().getSelectedItem());
//				prepare.setString(2, subjecthandle_subject.getSelectionModel().getSelectedItem());
//				prepare.setString(3, String.valueOf(sqlDate));
//				prepare.setString(4, subjecthandle_status.getSelectionModel().getSelectedItem());
//
//				prepare.executeUpdate();
//
//				subjectHandleDisplayData();
//
//				alert.successMessage("Added successfully!");
//
//				subjectHandleClearBtn();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public void subjectHandleRemoveBtn() {
//
//		if (subjecthandle_code.getSelectionModel().getSelectedItem().isEmpty()
//				|| subjecthandle_subject.getSelectionModel().getSelectedItem().isEmpty()
//				|| subjecthandle_status.getSelectionModel().getSelectedItem().isEmpty()) {
//			alert.errorMessage("Please fill all blank fields");
//		} else {
//
//			if (alert.confirmMessage("Are you sure you want to Remove Subject Code: "
//					+ subjecthandle_code.getSelectionModel().getSelectedItem() + "?")) {
//				String deleteData = "DELETE FROM teacher_handle WHERE subject_code = '"
//						+ subjecthandle_code.getSelectionModel().getSelectedItem() + "'";
//				connect = Database.connectDB();
//
//				try {
//					prepare = connect.prepareStatement(deleteData);
//
//					prepare.executeUpdate();
//
//					subjectHandleDisplayData();
//
//					alert.successMessage("Removed successfully!");
//
//					subjectHandleClearBtn();
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else {
//				alert.errorMessage("Cancelled");
//			}
//
//		}
//	}

	public void subjectHandleAddBtn() {
		if (subjecthandle_code.getSelectionModel().getSelectedItem() == null
				|| subjecthandle_subject.getSelectionModel().getSelectedItem() == null
				|| subjecthandle_status.getSelectionModel().getSelectedItem() == null) {
			alert.errorMessage("Please fill all blank fields");
		} else if (subjecthandle_code.getSelectionModel().getSelectedItem().isEmpty()
				|| subjecthandle_subject.getSelectionModel().getSelectedItem().isEmpty()
				|| subjecthandle_status.getSelectionModel().getSelectedItem().isEmpty()) {
			alert.errorMessage("Please fill all blank fields");
		} else {
			String insertData = "INSERT INTO teacher_handle (subject_code, subject, date, status) VALUES(?,?,?,?)";
			connect = Database.connectDB();

			Date date = new Date();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());

			try {
				prepare = connect.prepareStatement(insertData);
				prepare.setString(1, subjecthandle_code.getSelectionModel().getSelectedItem());
				prepare.setString(2, subjecthandle_subject.getSelectionModel().getSelectedItem());
				prepare.setString(3, String.valueOf(sqlDate));
				prepare.setString(4, subjecthandle_status.getSelectionModel().getSelectedItem());

				prepare.executeUpdate();

				subjectHandleDisplayData();

				alert.successMessage("Added successfully!");

				subjectHandleClearBtn();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void subjectHandleRemoveBtn() {
		if (subjecthandle_code.getSelectionModel().getSelectedItem() == null
				|| subjecthandle_subject.getSelectionModel().getSelectedItem() == null
				|| subjecthandle_status.getSelectionModel().getSelectedItem() == null) {
			alert.errorMessage("Please fill all blank fields");
		} else if (subjecthandle_code.getSelectionModel().getSelectedItem().isEmpty()
				|| subjecthandle_subject.getSelectionModel().getSelectedItem().isEmpty()
				|| subjecthandle_status.getSelectionModel().getSelectedItem().isEmpty()) {
			alert.errorMessage("Please fill all blank fields");
		} else {
			if (alert.confirmMessage("Are you sure you want to Remove Subject Code: "
					+ subjecthandle_code.getSelectionModel().getSelectedItem() + "?")) {
				String deleteData = "DELETE FROM teacher_handle WHERE subject_code = '"
						+ subjecthandle_code.getSelectionModel().getSelectedItem() + "'";
				connect = Database.connectDB();

				try {
					prepare = connect.prepareStatement(deleteData);

					prepare.executeUpdate();

					subjectHandleDisplayData();

					alert.successMessage("Removed successfully!");

					subjectHandleClearBtn();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				alert.errorMessage("Cancelled");
			}
		}
	}

	public void subjectHandleClearBtn() {
		subjecthandle_code.getSelectionModel().clearSelection();
		subjecthandle_subject.getSelectionModel().clearSelection();
		subjecthandle_status.getSelectionModel().clearSelection();
	}

	public ObservableList<DataSubjectHandle> subjectHandleGetData() {

		ObservableList<DataSubjectHandle> listData = FXCollections.observableArrayList();

		String sql = "SELECT * FROM teacher_handle";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			DataSubjectHandle dshData;

			while (result.next()) {
//                DataSubjectHandle(Integer id, String subjectCode,
//            String subject, Date insertData, String status)
				dshData = new DataSubjectHandle(result.getInt("id"), result.getString("subject_code"),
						result.getString("subject"), result.getDate("date"), result.getString("status"));

				listData.add(dshData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	private ObservableList<DataSubjectHandle> subjectHandleListData;

	public void subjectHandleDisplayData() {
		subjectHandleListData = subjectHandleGetData();

		subjecthandle_col_subjectCode.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
		subjecthandle_col_subjectName.setCellValueFactory(new PropertyValueFactory<>("subject"));
		subjecthandle_col_dateInsert.setCellValueFactory(new PropertyValueFactory<>("insertDate"));
		subjecthandle_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

		subjecthandle_tableView.setItems(subjectHandleListData);
	}

	public void subjectHandleSelectItem() {

		DataSubjectHandle dshData = subjecthandle_tableView.getSelectionModel().getSelectedItem();
		int num = subjecthandle_tableView.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}

		subjecthandle_code.getSelectionModel().select(dshData.getSubjectCode());
		subjecthandle_subject.getSelectionModel().select(dshData.getSubject());
		subjecthandle_status.getSelectionModel().select(dshData.getStatus());

	}

	public void subjectHandleSubjectCodeList() {
		String sql = "SELECT * FROM subject WHERE date_delete IS NULL";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			ObservableList listData = FXCollections.observableArrayList();

			while (result.next()) {
				listData.add(result.getString("subject_code"));
			}

			subjecthandle_code.setItems(listData);

			subjectHandleSubjectList();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void subjectHandleSubjectList() {
		String sql = "SELECT * FROM subject WHERE date_delete IS NULL AND " + "subject_code = '"
				+ subjecthandle_code.getSelectionModel().getSelectedItem() + "'";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			ObservableList listData = FXCollections.observableArrayList();

			while (result.next()) {
				listData.add(result.getString("subject"));
			}

			subjecthandle_subject.setItems(listData);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void subjectHandleStatusList() {

		List<String> listS = new ArrayList<>();

		for (String data : ListData.status) {
			listS.add(data);
		}

		ObservableList listData = FXCollections.observableArrayList(listS);
		subjecthandle_status.setItems(listData);

	}

	public void logoutBtn() {
		try {
			if (alert.confirmMessage("Are you sure you want to logout?")) {

				Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
				Stage stage = new Stage();

				stage.setScene(new Scene(root));
				stage.show();

				logout_btn.getScene().getWindow().hide();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void displayTeacher() {

		String sql = "SELECT * FROM users WHERE username = '" + ListData.teacher_username + "'";

		connect = Database.connectDB();

		String temp_teacherName = "";
		String temp_teacherID = "";

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			if (result.next()) {
				temp_teacherID = result.getString("teacher_id");
			}

			String selectData = "SELECT * FROM teacher WHERE teacher_id = '" + temp_teacherID + "'";

			statement = connect.createStatement();
			result = statement.executeQuery(selectData);

			if (result.next()) {
				temp_teacherName = result.getString("full_name");
			}

			greet_name.setText("Welcome, " + temp_teacherName);
			teacher_id.setText(temp_teacherID);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadAttendanceCourses() {
		ObservableList<String> courses = FXCollections.observableArrayList();

		String sql = "SELECT DISTINCT subject_code FROM teacher_student WHERE teacher_id = ?";
		try {
			connect = Database.connectDB();
			prepare = connect.prepareStatement(sql);
			prepare.setString(1, teacher_id.getText());
			result = prepare.executeQuery();

			while (result.next()) {
				courses.add(result.getString("subject_code"));
			}

			attendance_course.setItems(courses);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updateAttendanceTableView(String selectedCourse) {
		ObservableList<DataAttendanceHandle> attendanceData = getAttendanceData(selectedCourse);
		attendance_table_view.setItems(attendanceData);
	}

	private ObservableList<DataAttendanceHandle> getAttendanceData(String selectedCourse) {
		ObservableList<DataAttendanceHandle> attendanceData = FXCollections.observableArrayList();

		String sql = "SELECT * FROM teacher_student WHERE teacher_id = ? AND stud_course = ?";
		try {
			connect = Database.connectDB();
			prepare = connect.prepareStatement(sql);
			prepare.setString(1, teacher_id.getText());
			prepare.setString(2, selectedCourse);
			result = prepare.executeQuery();

			while (result.next()) {

				DataAttendanceHandle attendance = new DataAttendanceHandle(result.getString("stud_studentID"),
						result.getString("stud_name"), result.getInt("total_classes"),
						result.getInt("total_attendance"), calculateAttendancePercentage(
								result.getInt("total_attendance"), result.getInt("total_classes")));
				attendanceData.add(attendance);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
		}

		return attendanceData;
	}

	private float calculateAttendancePercentage(int attended, int total) {
		return (float) attended / total * 100;
	}

	public void loadAttendanceData() {
		ObservableList<DataAttendanceHandle> attendanceData = FXCollections.observableArrayList();
		String sql = "SELECT * FROM teacher_student WHERE subject_code = ? AND teacher_id = ?";

		try {
			connect = Database.connectDB();
			prepare = connect.prepareStatement(sql);
			prepare.setString(1, attendance_course.getSelectionModel().getSelectedItem());
			prepare.setString(2, teacher_id.getText());
			result = prepare.executeQuery();

			while (result.next()) {
				DataAttendanceHandle data = new DataAttendanceHandle(result.getString("stud_studentID"),
						result.getString("stud_name"), result.getInt("total_classes"),
						result.getInt("total_attendance"), calculateAttendancePercentage(
								result.getInt("total_attendance"), result.getInt("total_classes")));
				attendanceData.add(data);
			}

			attendance_col_studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
			attendance_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
			attendance_col_total_class.setCellValueFactory(new PropertyValueFactory<>("totalClasses"));
			attendance_col_attended.setCellValueFactory(new PropertyValueFactory<>("attendedClasses"));
			attendance_col_percentice.setCellValueFactory(new PropertyValueFactory<>("attendancePercentage"));

			attendance_table_view.setItems(attendanceData);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void takeAttendence(ActionEvent event) {
		if (attendance_course.getSelectionModel().isEmpty()) {
			alert.errorMessage("Please select a course to take attendance for.");
		} else {
			// update the database teacher_student table with total_classes = total_classes
			// + 1;
			String sql = "UPDATE teacher_student SET total_classes = total_classes + 1 WHERE subject_code = ? AND teacher_id = ?";
			try {
				connect = Database.connectDB();
				prepare = connect.prepareStatement(sql);
				prepare.setString(1, attendance_course.getSelectionModel().getSelectedItem());
				prepare.setString(2, teacher_id.getText());
				prepare.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			loadAttendanceData();
		}
	}

	public void attendenceStudentIDShow() {
		DataAttendanceHandle tData = attendance_table_view.getSelectionModel().getSelectedItem();
		int num = attendance_table_view.getSelectionModel().getSelectedIndex();

		if (num < 0 || tData == null) {
			return;
		}

		attendance_sID.setText(tData.getStudentID());
	}

	public void markAttendance(ActionEvent event) {
		if (attendance_sID.getText().isEmpty()) {
			alert.errorMessage("Please select a student to mark attendance for.");
		} else {
			// update the database teacher_student table with total_attendance =
			// total_attendance
			// + 1;
			String sql = "UPDATE teacher_student SET total_attendance = total_attendance + 1 WHERE stud_studentID = ? AND subject_code = ?";

			try {
				connect = Database.connectDB();
				prepare = connect.prepareStatement(sql);
				prepare.setString(1, attendance_sID.getText());
				prepare.setString(2, attendance_course.getSelectionModel().getSelectedItem());
				System.out.println(attendance_sID.getText());
				System.out.println("Attendence added");
				prepare.executeUpdate();

				String selectSQL = "SELECT total_attendance, total_classes FROM teacher_student WHERE stud_studentID = ?";
				prepare = connect.prepareStatement(selectSQL);
				prepare.setString(1, attendance_sID.getText());
				ResultSet rs = prepare.executeQuery();

				if (rs.next()) {
					int totalAttendance = rs.getInt("total_attendance");
					int totalClasses = rs.getInt("total_classes");

					// Step 3: Calculate attendance_percentage
					double attendancePercentage = (double) totalAttendance / totalClasses * 100;

					// Step 4: Update attendance_percentage
					String updatePercentageSQL = "UPDATE teacher_student SET attendance_percentage = ? WHERE stud_studentID = ?";
					prepare = connect.prepareStatement(updatePercentageSQL);
					prepare.setDouble(1, attendancePercentage);
					prepare.setString(2, attendance_sID.getText());
					prepare.executeUpdate();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			loadAttendanceData();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		displayTeacher();

		addStudentCourseList();
		addStudentsYearList();
		addStudentDisplayData();
		addStudentSubjectList();
		loadAttendanceCourses();
		attendance_course.setOnAction(event -> loadAttendanceData());

	}

}
