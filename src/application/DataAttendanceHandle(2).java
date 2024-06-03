

package application;

public class DataAttendanceHandle {

	private String studentID;
	private String name;
	private int totalClasses;
	private int attendedClasses;
	private float attendancePercentage;

	public DataAttendanceHandle(String studentID, String name, int totalClasses, int attendedClasses,
			float attendancePercentage) {
		this.studentID = studentID;
		this.name = name;
		this.totalClasses = totalClasses;
		this.attendedClasses = attendedClasses;
		this.attendancePercentage = attendancePercentage;
	}

	// Getter methods

	public String getStudentID() {
		return studentID;
	}

	public String getName() {
		return name;
	}

	public int getTotalClasses() {
		return totalClasses;
	}

	public int getAttendedClasses() {
		return attendedClasses;
	}

	public float getAttendancePercentage() {
		return attendancePercentage;
	}
}
