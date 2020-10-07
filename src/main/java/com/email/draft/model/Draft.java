package com.email.draft.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "draft")
public class Draft {
	private Long id;
	private String subject;
	private String description;
	private String toEmail;
	private String cc;
	private String bc;
	private Date created;
	private Date updated;
	private String fileName;
	private String fileUrl;
		
	public Draft(){
		
	}

	public Draft(Long id, String subject, String description, String toEmail, String cc, String bc,
			Date created, Date updated, String fileName, String fileUrl) {
		super();
		this.id = id;
		this.subject = subject;
		this.description = description;
		this.toEmail = toEmail;
		this.cc = cc;
		this.bc = bc;
		this.created = created;
		this.updated = updated;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "subject", nullable = true)
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "toEmail", nullable = true)
	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	@Column(name = "cc", nullable = true)
	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	@Column(name = "bc", nullable = true)
	public String getBc() {
		return bc;
	}

	public void setBc(String bc) {
		this.bc = bc;
	}

	@Column(name = "created", nullable = true)
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "updated", nullable = true)
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	@Column(name = "fileName", nullable = true)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "fileUrl", nullable = true)
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Override
	public String toString() {
		return "Draft [id=" + id + ", subject=" + subject + ", description=" + description + ", toEmail=" + toEmail
				+ ", cc=" + cc + ", bc=" + bc + ", created=" + created + ", updated=" + updated + ", fileName=" + fileName + ", fileUrl=" + fileUrl+ "]";
	}

}