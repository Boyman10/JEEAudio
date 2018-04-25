package com.subtitlor.model.entity;

import java.util.ArrayList;

/**
 * Entity class representing the file
 * @author bob
 *
 */
public class FileToTranslate {

	private int id;
	private String date;
	private String fileName; // file / audio identification
	private ArrayList<Traduction> sequences;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the sequences
	 */
	public ArrayList<Traduction> getSequences() {
		return sequences;
	}
	/**
	 * @param sequences the sequences to set
	 */
	public void setSequences(ArrayList<Traduction> sequences) {
		this.sequences = sequences;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileToTranslate [id=" + id + ", date=" + date + ", fileName=" + fileName + ", sequences=" + sequences
				+ "]";
	}
	
	
}
