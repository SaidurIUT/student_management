package application;

public class DataSubject2Handle {
	private String subjectCode;
	private String subjectName;
	private String subjectTeacher;
	private Float credit;
	private Integer totalClass;
	private Integer attendedClass;
	private Float percentice;

	public DataSubject2Handle(String subjectCode, String subjectName, String subjectTeacher, Float credit,
			Integer totalClass, Integer attendedClass, Float percentice) {
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.subjectTeacher = subjectTeacher;
		this.credit = credit;
		this.totalClass = totalClass;
		this.attendedClass = attendedClass;
		this.percentice = percentice;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public String getSubjectTeacher() {
		return subjectTeacher;
	}

	public Float getCredit() {
		return credit;
	}

	public Integer getTotalClass() {
		return totalClass;
	}

	public Integer getAttendedClass() {
		return attendedClass;
	}

	public Float getPercentice() {
		return percentice;
	}
}