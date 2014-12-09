/*
 * @(#)CsvRecord.java
 *
 * Copyright(c) 2010 NTTDATA Corporation.
 */

package jp.terasoluna.fw.collector.file;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;

/**
 * CSV�t�@�C��1���R�[�h���}�b�s���O����Bean
 * 
 * 
 * 
 */
@FileFormat(lineFeedChar = "\r\n", fileEncoding = "UTF-8")
public class B000001Data {

	// ID (1�Ԗڂ̃J����)
	@InputFileColumn(columnIndex = 0)
	private int id = 0;

	// ���� (2�Ԗڂ̃J����)
	@InputFileColumn(columnIndex = 1)
	private String familyname = null;

	// ���O (3�Ԗڂ̃J����)
	@InputFileColumn(columnIndex = 2)
	private String firstname = null;

	// �N�� (4�Ԗڂ̃J����)
	@InputFileColumn(columnIndex = 3)
	private int age = 0;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the familyname
	 */
	public String getFamilyname() {
		return familyname;
	}

	/**
	 * @param familyname
	 *            the familyname to set
	 */
	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

}
