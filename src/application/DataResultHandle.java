package application;

public class DataResultHandle {
	private String subjectCode;
	private float credit;
	private float quiz1;
	private float quiz2;
	private float quiz3;
	private float quiz4;
	private float finalExam;
	private float midTerm;
	private float attendancePercentage;
	private float attendance;
	private float total;
	private String grade;
	private float gradePoint;

	public DataResultHandle(String subjectCode, float credit, float quiz1, float quiz2, float quiz3, float quiz4,
			float finalExam, float midTerm, float attendancePercentage) {
		this.subjectCode = subjectCode;
		this.credit = credit;
		this.quiz1 = quiz1;
		this.quiz2 = quiz2;
		this.quiz3 = quiz3;
		this.quiz4 = quiz4;
		this.finalExam = finalExam;
		this.midTerm = midTerm;
		this.attendancePercentage = attendancePercentage;
		this.attendance = calculateAttendance(attendancePercentage, credit);
		calculateTotalGradeAndGradePoint();
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public float getCredit() {
		return credit;
	}

	public void setCredit(float credit) {
		this.credit = credit;
	}

	public float getQuiz1() {
		return quiz1;
	}

	public void setQuiz1(float quiz1) {
		this.quiz1 = quiz1;
	}

	public float getQuiz2() {
		return quiz2;
	}

	public void setQuiz2(float quiz2) {
		this.quiz2 = quiz2;
	}

	public float getQuiz3() {
		return quiz3;
	}

	public void setQuiz3(float quiz3) {
		this.quiz3 = quiz3;
	}

	public float getQuiz4() {
		return quiz4;
	}

	public void setQuiz4(float quiz4) {
		this.quiz4 = quiz4;
	}

	public float getFinalExam() {
		return finalExam;
	}

	public void setFinalExam(float finalExam) {
		this.finalExam = finalExam;
	}

	public float getMidTerm() {
		return midTerm;
	}

	public void setMidTerm(float midTerm) {
		this.midTerm = midTerm;
	}

	public float getAttendancePercentage() {
		return attendancePercentage;
	}

	public void setAttendancePercentage(float attendancePercentage) {
		this.attendancePercentage = attendancePercentage;
		this.attendance = calculateAttendance(attendancePercentage, credit);
	}

	public float getAttendance() {
		return attendance;
	}

	public float getTotal() {
		return total;
	}

	public String getGrade() {
		return grade;
	}

	public float getGradePoint() {
		return gradePoint;
	}

	private void calculateTotalGradeAndGradePoint() {
		float[] quizzes = { quiz1, quiz2, quiz3, quiz4 };
		float bestThreeQuizzes = calculateBestThreeQuizzes(quizzes);
		this.total = finalExam + midTerm + bestThreeQuizzes + attendance;

		if ((total / credit) >= 80) {
			this.grade = "A+";
			this.gradePoint = 4.0f;
		} else if ((total / credit) >= 75) {
			this.grade = "A";
			this.gradePoint = 3.75f;
		} else if ((total / credit) >= 70) {
			this.grade = "A-";
			this.gradePoint = 3.5f;
		} else if ((total / credit) >= 65) {
			this.grade = "B+";
			this.gradePoint = 3.25f;
		} else if ((total / credit) >= 60) {
			this.grade = "B";
			this.gradePoint = 3.0f;
		} else if ((total / credit) >= 55) {
			this.grade = "B-";
			this.gradePoint = 2.75f;
		} else if ((total / credit) >= 50) {
			this.grade = "C+";
			this.gradePoint = 2.5f;
		} else if ((total / credit) >= 45) {
			this.grade = "C";
			this.gradePoint = 2.25f;
		} else if ((total / credit) >= 40) {
			this.grade = "D";
			this.gradePoint = 2.0f;
		} else {
			this.grade = "F";
			this.gradePoint = 0.0f;
		}
	}

	private float calculateBestThreeQuizzes(float[] quizzes) {
		float total = 0;
		int n = quizzes.length;

		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				if (quizzes[j] > quizzes[j + 1]) {
					float temp = quizzes[j];
					quizzes[j] = quizzes[j + 1];
					quizzes[j + 1] = temp;
				}
			}
		}

		for (int i = n - 1; i >= n - 3; i--) {
			total += quizzes[i];
		}

		return (total / 3) * credit;
	}

	private float calculateAttendance(float attendancePercentage, float credit) {
		return (attendancePercentage / 10) * credit;
	}
}
