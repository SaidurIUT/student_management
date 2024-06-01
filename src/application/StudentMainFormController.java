package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class StudentMainFormController implements Initializable {

	@FXML
	private Circle circle_image;

	@FXML
	private Button courseInformation_btn;

	@FXML
	private Button logout_btn;

	@FXML
	private Button resultInformation_btn;

	@FXML
	private Button studentInformation_btn;

	@FXML
	private AnchorPane studentInformation_pan;

	@FXML
	private Label student_id;

	@FXML
	private Label student_name;

	@FXML
	private Label studentinfo_studentAge;

	@FXML
	private Label studentinfo_studentBD;

	@FXML
	private Label studentinfo_studentCourse;

	@FXML
	private Label studentinfo_studentEmail;

	@FXML
	private Label studentinfo_studentGender;

	@FXML
	private Label studentinfo_studentID;

	@FXML
	private Label studentinfo_studentSection;

	@FXML
	private Label studentinfo_studentSemister;

	@FXML
	private Label studentinfo_studentYear;

	@FXML
	private Label studentinfo_studentfullname;

	@FXML
	private TableColumn<DataStudentHandle, String> teacherInfo_col_teacherGender;

	@FXML
	private TableColumn<DataStudentHandle, String> teacherInfo_col_teacherID;

	@FXML
	private TableColumn<DataStudentHandle, String> teacherInfo_col_teacherName;

	@FXML
	private TableColumn<DataStudentHandle, java.sql.Date> teacherInfo_col_teacherYE;

	@FXML
	private TableColumn<DataSubject2Handle, String> CI_subject_code;

	@FXML
	private TableColumn<DataSubject2Handle, String> CI_subject_name;

	@FXML
	private TableColumn<DataSubject2Handle, String> CI_subject_teacher;

	@FXML
	private TableColumn<DataSubject2Handle, Float> CI_credit;

	@FXML
	private TableColumn<DataSubject2Handle, Integer> CI_total_class;

	@FXML
	private TableColumn<DataSubject2Handle, Integer> CI_attended_class;

	@FXML
	private TableColumn<DataSubject2Handle, Integer> CI_percentice;

	@FXML
	private Button teacherInformation_btn;

	@FXML
	private AnchorPane teacherInformation_pan;

	@FXML
	private Label teacher_date;

	@FXML
	private Label teacher_gender;

	@FXML
	private Label teacher_id;

	@FXML
	private Label teacher_name;

	@FXML
	private TableView<DataStudentHandle> teacher_table_view;

	@FXML
	private AnchorPane courseinfo_pan;

	@FXML
	private AnchorPane result_pan;

	@FXML
	private TableView<DataSubject2Handle> subject_table_view;

	@FXML
	private TableView<DataResultHandle> result_table_view;

	@FXML
	private TableColumn<DataResultHandle, String> result_col_subject_code;

	@FXML
	private TableColumn<DataResultHandle, Float> result_col_credit;

	@FXML
	private TableColumn<DataResultHandle, Float> result_col_q1;

	@FXML
	private TableColumn<DataResultHandle, Float> result_col_q2;

	@FXML
	private TableColumn<DataResultHandle, Float> result_col_q3;

	@FXML
	private TableColumn<DataResultHandle, Float> result_col_q4;

	@FXML
	private TableColumn<DataResultHandle, Float> result_col_final;

	@FXML
	private TableColumn<DataResultHandle, Float> result_col_mid;

	@FXML
	private TableColumn<DataResultHandle, Float> result_col_attend;

	@FXML
	private TableColumn<DataResultHandle, Float> result_col_total;

	@FXML
	private TableColumn<DataResultHandle, String> result_col_grade;

	@FXML
	private TableColumn<DataResultHandle, Float> result_col_gradePoint;

	@FXML
	void studentInformationBtn(ActionEvent event) {

	}

	@FXML
	void teacherInformationBtn(ActionEvent event) {

	}

	private Connection connect;
	private PreparedStatement prepare;
	private Statement statement;
	private ResultSet result;

	public void switchForm(ActionEvent event) {

		if (event.getSource() == studentInformation_btn) {

			studentInformation_pan.setVisible(true);
			teacherInformation_pan.setVisible(false);
			courseinfo_pan.setVisible(false);
			result_pan.setVisible(false);

		} else if (event.getSource() == teacherInformation_btn) {

			studentInformation_pan.setVisible(false);
			teacherInformation_pan.setVisible(true);
			courseinfo_pan.setVisible(false);
			result_pan.setVisible(false);

			teacherDisplayData();

		} else if (event.getSource() == courseInformation_btn) {

			studentInformation_pan.setVisible(false);
			teacherInformation_pan.setVisible(false);
			courseinfo_pan.setVisible(true);
			result_pan.setVisible(false);

			displaySubjectData();

		} else if (event.getSource() == resultInformation_btn) {

			studentInformation_pan.setVisible(false);
			teacherInformation_pan.setVisible(false);
			courseinfo_pan.setVisible(false);
			result_pan.setVisible(true);

			displayResultData();

		}
	}

	AlertMessage alert = new AlertMessage();

//	public ObservableList<DataStudentHandle> teacherSetData() {
//
//		ObservableList<DataStudentHandle> listData = FXCollections.observableArrayList();
//
//		String sql = "SELECT * FROM teacher_student WHERE stud_studentID = '" + student_id.getText()
//				+ "' AND date_delete IS NULL";
//
//		connect = Database.connectDB();
//
//		try {
//
//			prepare = connect.prepareStatement(sql);
//			result = prepare.executeQuery();
//
//			DataStudentHandle dsh;
//
//			while (result.next()) {
//				dsh = new DataStudentHandle(result.getString("teacher_id"), result.getString("stud_studentID"),
//						result.getString("stud_name"), result.getString("stud_gender"), result.getDate("date_insert"));
//				listData.add(dsh);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return listData;
//	}

	public ObservableList<DataStudentHandle> teacherSetData() {
		ObservableList<DataStudentHandle> listData = FXCollections.observableArrayList();

		String sql = "SELECT ts.teacher_id, ts.stud_studentID, ts.stud_name, ts.stud_gender, ts.date_insert, t.full_name, t.gender "
				+ "FROM teacher_student ts " + "JOIN teacher t ON ts.teacher_id = t.teacher_id "
				+ "WHERE ts.stud_studentID = ? AND ts.date_delete IS NULL";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			prepare.setString(1, student_id.getText());
			result = prepare.executeQuery();

			while (result.next()) {
				DataStudentHandle dsh = new DataStudentHandle(result.getString("teacher_id"),
						result.getString("stud_studentID"), result.getString("full_name"), result.getString("gender"),
						result.getDate("date_insert"));
				listData.add(dsh);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	private long student_id1;
	private String student_email;

	public void studentIDDisplay() {

		String sql = "SELECT * FROM users WHERE username = '" + ListData.student_username + "'";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			if (result.next()) {
				student_id.setText(result.getString("student_id"));
				student_name.setText(ListData.student_username);
				student_id1 = result.getInt("student_id");
				student_email = result.getString("email");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showStudentInforamtion() {

		String sql = "SELECT * FROM student WHERE student_id = '" + student_id1 + "'";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			if (result.next()) {
				studentinfo_studentfullname.setText(result.getString("full_name"));
				studentinfo_studentID.setText(String.valueOf(result.getLong("student_id")));
				studentinfo_studentGender.setText(result.getString("gender"));
				studentinfo_studentCourse.setText(result.getString("course"));
				studentinfo_studentYear.setText(result.getString("year"));
				studentinfo_studentSemister.setText(result.getString("semester"));
				studentinfo_studentSection.setText(result.getString("section"));
				studentinfo_studentEmail.setText(student_email);
				studentinfo_studentAge.setText(String.valueOf(result.getInt("age")));

				java.sql.Date birthDate = result.getDate("birth_date");
				if (birthDate != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					studentinfo_studentBD.setText(dateFormat.format(birthDate));
				} else {
					studentinfo_studentBD.setText("");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private ObservableList<DataStudentHandle> teacherListData;

	public void teacherDisplayData() {
		teacherListData = teacherSetData();

		teacherInfo_col_teacherID.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
		teacherInfo_col_teacherName.setCellValueFactory(new PropertyValueFactory<>("name"));
		teacherInfo_col_teacherGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		teacherInfo_col_teacherYE.setCellValueFactory(new PropertyValueFactory<>("dateInsert"));

		teacher_table_view.setItems(teacherListData);
	}

	private Image image;

	public void teacherSelectData() {
		DataStudentHandle dsh = teacher_table_view.getSelectionModel().getSelectedItem();
		int num = teacher_table_view.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}

		String sql = "SELECT * FROM teacher WHERE teacher_id = '" + dsh.getTeacherID() + "'";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			if (result.next()) {

				String path = "File:" + result.getString("image");

				image = new Image(path, 164, 73, false, true);
				circle_image.setFill(new ImagePattern(image));

				teacher_id.setText(result.getString("teacher_id"));
				teacher_name.setText(result.getString("full_name"));
				teacher_gender.setText(result.getString("gender"));
				teacher_date.setText(result.getString("date_insert"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logoutBtn() {

		try {
			if (alert.confirmMessage("Are you sure you want to logout?")) {
				Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

				Stage stage = new Stage();
				Scene scene = new Scene(root);

				stage.setScene(scene);
				stage.show();

				logout_btn.getScene().getWindow().hide();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ObservableList<DataSubject2Handle> subjectSetData() {
		ObservableList<DataSubject2Handle> listData = FXCollections.observableArrayList();

		String sql = "SELECT ts.subject_code, ts.total_classes, ts.total_attendance, ts.attendance_percentage, s.subject, s.credit, t.full_name AS teacher_name "
				+ "FROM teacher_student ts " + "JOIN subject s ON ts.subject_code = s.subject_code "
				+ "JOIN teacher t ON ts.teacher_id = t.teacher_id "
				+ "WHERE ts.stud_studentID = ? AND ts.date_delete IS NULL";

		connect = Database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			prepare.setString(1, String.valueOf(student_id1));
			result = prepare.executeQuery();

			while (result.next()) {
				DataSubject2Handle dsh = new DataSubject2Handle(result.getString("subject_code"),
						result.getString("subject"), result.getString("teacher_name"), result.getFloat("credit"),
						result.getInt("total_classes"), result.getInt("total_attendance"),
						result.getFloat("attendance_percentage"));
				listData.add(dsh);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	public void displaySubjectData() {
		ObservableList<DataSubject2Handle> subjectListData = subjectSetData();

		CI_subject_code.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
		CI_subject_name.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
		CI_subject_teacher.setCellValueFactory(new PropertyValueFactory<>("subjectTeacher"));
		CI_credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
		CI_total_class.setCellValueFactory(new PropertyValueFactory<>("totalClass"));
		CI_attended_class.setCellValueFactory(new PropertyValueFactory<>("attendedClass"));
		CI_percentice.setCellValueFactory(new PropertyValueFactory<>("percentice"));

		subject_table_view.setItems(subjectListData);
	}

	private ObservableList<DataResultHandle> fetchDataFromDatabase() {
		ObservableList<DataResultHandle> resultList = FXCollections.observableArrayList();

		try {
			// Prepare SQL query to select data for a specific student
			String sql = "SELECT ts.*, s.course, s.credit " + "FROM teacher_student ts "
					+ "JOIN subject s ON ts.subject_code = s.subject_code " + "WHERE ts.stud_studentID = ?"; // Filter
																												// by
																												// student_id

			// Prepare the statement
			prepare = connect.prepareStatement(sql);
			prepare.setLong(1, student_id1); // Set the student_id parameter

			// Execute query and get result set
			result = prepare.executeQuery();

			// Process result set
			while (result.next()) {
				// Extract data from result set
				String subjectCode = result.getString("subject_code");
				float credit = result.getFloat("credit");
				float quiz1 = result.getFloat("mark_quiz_1");
				float quiz2 = result.getFloat("mark_quiz_2");
				float quiz3 = result.getFloat("mark_quiz_3");
				float quiz4 = result.getFloat("mark_quiz_4");
				float finalExam = result.getFloat("mark_final");
				float midTerm = result.getFloat("mark_mid_term");
				float attendancePercentage = result.getFloat("attendance_percentage");

				// Create DataResultHandle object and add to result list
				DataResultHandle dataResultHandle = new DataResultHandle(subjectCode, credit, quiz1, quiz2, quiz3,
						quiz4, finalExam, midTerm, attendancePercentage);
				resultList.add(dataResultHandle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public void displayResultData() {
		ObservableList<DataResultHandle> resultList = fetchDataFromDatabase();

		result_col_subject_code.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
		result_col_credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
		result_col_q1.setCellValueFactory(new PropertyValueFactory<>("quiz1"));
		result_col_q2.setCellValueFactory(new PropertyValueFactory<>("quiz2"));
		result_col_q3.setCellValueFactory(new PropertyValueFactory<>("quiz3"));
		result_col_q4.setCellValueFactory(new PropertyValueFactory<>("quiz4"));
		result_col_final.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
		result_col_mid.setCellValueFactory(new PropertyValueFactory<>("midTerm"));
		result_col_attend.setCellValueFactory(new PropertyValueFactory<>("attendance"));
		result_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
		result_col_grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		result_col_gradePoint.setCellValueFactory(new PropertyValueFactory<>("gradePoint"));

		result_table_view.setItems(resultList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//        teacherDisplayData();
		studentIDDisplay();
		showStudentInforamtion();

	}

}
