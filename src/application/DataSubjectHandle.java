package application;

import java.sql.Date;

public class DataSubjectHandle {

	private Integer id;
	private String subjectCode;
	private String subject;
	private Date insertDate;
	private String status;

	public DataSubjectHandle(Integer id, String subjectCode, String subject, Date insertData, String status) {
		this.id = id;
		this.subjectCode = subjectCode;
		this.subject = subject;
		this.insertDate = insertData;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public String getSubject() {
		return subject;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public String getStatus() {
		return status;
	}

}