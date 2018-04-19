package com.subtitlor.model.entity;

import java.util.ArrayList;

/**
 * Class holding a traduction of a video loaded from a file
 * @author bob
 * @version 1.0
 */
public class Traduction {

	private int id ; 
	private String filename;// file / audio identification
	private String sequence;
	private ArrayList<String> strings;
	private String language;
	
	/**
	 * Empty constructor
	 */
	public Traduction() {
		
	}
	
	/**
	 * Constructor class
	 * @param id
	 * @param seq
	 * @param language
	 * @param strs
	 */
	public Traduction(int id, String seq, String language, ArrayList<String> strs, String filename) {
		
		this.id = id;
		this.sequence = seq;
		this.language = language;
		this.strings = strs;
		this.filename = filename;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public ArrayList<String> getStrings() {
		return strings;
	}
	public void setStrings(ArrayList<String> strings) {
		this.strings = strings;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String file) {
		this.filename = file;
	}

	@Override
	public String toString() {
		return "Traduction [id=" + id + ", filename=" + filename + ", sequence=" + sequence + ", strings=" + strings
				+ ", language=" + language + "]";
	}	
	
}
