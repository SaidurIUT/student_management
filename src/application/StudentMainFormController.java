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

		} else if (event.getSource() == resultInformation_btn) {

			studentInformation_pan.setVisible(false);
			teacherInformation_pan.setVisible(false);
			courseinfo_pan.setVisible(false);
			result_pan.setVisible(true);

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//        teacherDisplayData();
		studentIDDisplay();
		showStudentInforamtion();

	}

}
