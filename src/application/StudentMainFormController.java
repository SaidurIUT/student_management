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
import javafx.scene.layout.AnchorPane;
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
	private TableColumn<?, ?> teacherInfo_col_teacherGender;

	@FXML
	private TableColumn<?, ?> teacherInfo_col_teacherID;

	@FXML
	private TableColumn<?, ?> teacherInfo_col_teacherName;

	@FXML
	private TableColumn<?, ?> teacherInfo_col_teacherYE;

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
	private TableView<?> teacher_table_view;

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

	AlertMessage alert = new AlertMessage();

	public ObservableList<DataStudentHandle> teacherSetData() {

		ObservableList<DataStudentHandle> listData = FXCollections.observableArrayList();

		String sql = "SELECT * FROM teacher_student WHERE stud_studentID = '" + student_id.getText()
				+ "' AND date_delete IS NULL";

		connect = Database.connectDB();

		try {

			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			DataStudentHandle dsh;

			while (result.next()) {
//                DataStudentHandle(String teacherID, String studentID
//            , String name, String gender, Date dateInsert)

				dsh = new DataStudentHandle(result.getString("teacher_id"), result.getString("stud_studentID"),
						result.getString("stud_name"), result.getString("stud_gender"), result.getDate("date_insert"));
				listData.add(dsh);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	private ObservableList<DataStudentHandle> teacherListData;

//	public void teacherDisplayData() {
//		teacherListData = teacherSetData();
//
//		studentInfo_col_teacherID.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
//		studentInfo_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
//		studentInfo_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
//		studentInfo_col_YE.setCellValueFactory(new PropertyValueFactory<>("dateInsert"));
//
//		table_view.setItems(teacherListData);
//	}

//	private Image image;
//
//	public void teacherSelectData() {
//		DataStudentHandle dsh = table_view.getSelectionModel().getSelectedItem();
//		int num = table_view.getSelectionModel().getSelectedIndex();
//
//		if ((num - 1) < -1) {
//			return;
//		}
//
//		String sql = "SELECT * FROM teacher WHERE teacher_id = '" + dsh.getTeacherID() + "'";
//
//		connect = Database.connectDB();
//
//		try {
//			prepare = connect.prepareStatement(sql);
//			result = prepare.executeQuery();
//
//			if (result.next()) {
//
//				String path = "File:" + result.getString("image");
//
//				image = new Image(path, 164, 73, false, true);
//				circle_image.setFill(new ImagePattern(image));
//
//				teacher_id.setText(result.getString("teacher_id"));
//				teacher_name.setText(result.getString("full_name"));
//				teacher_gender.setText(result.getString("gender"));
//				teacher_date.setText(result.getString("date_insert"));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

	public void teacherSelectData() {

	};

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

		// teacherDisplayData();
		studentIDDisplay();
		showStudentInforamtion();

	}

}