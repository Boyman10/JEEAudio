package com.subtitlor.model.entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class holding a traduction of a video loaded from a file
 * @author bob
 * @version 1.0
 */
public class Traduction {

	private int id ; 

	private String sequence;
	private HashMap<String,ArrayList<String>> mapString; // ex : {"FRENCH", {"phrase 1","phrase 2","phrase 3"}}

	
	/**
	 * Empty constructor
	 */
	public Traduction() {
		
	}


	public Traduction(String sequence, HashMap<String, ArrayList<String>> mapString) {
		super();
		this.sequence = sequence;
		this.mapString = mapString;
	}


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
	 * @return the sequence
	 */
	public String getSequence() {
		return sequence;
	}


	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}


	/**
	 * @return the mapString
	 */
	public HashMap<String, ArrayList<String>> getMapString() {
		return mapString;
	}


	/**
	 * @param mapString the mapString to set
	 */
	public void setMapString(HashMap<String, ArrayList<String>> mapString) {
		this.mapString = mapString;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Traduction [id=" + id + ", sequence=" + sequence + ", mapString=" + mapString + "]";
	}
	

}
