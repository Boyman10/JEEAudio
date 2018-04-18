package com.subtitlor.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.Part;

public class FileHandler {

	// A few configuration parameters for file upload :
	public static final int SIZE_TAMPON = 10240;
	
	// Storing folder from tmp to here :
	public static final String FILE_FOLDER = "/home/bob/Downloads/";
	
	
	public ArrayList<String> wholeFile = new ArrayList<>();
	
	   public void writeFile( Part part, String fileName) throws IOException {
	        BufferedInputStream entree = null;
	        BufferedOutputStream sortie = null;
	        
	    	File myFile = new File(FILE_FOLDER, fileName);

	        try {
	            entree = new BufferedInputStream(part.getInputStream(), SIZE_TAMPON);
	            sortie = new BufferedOutputStream(new FileOutputStream(myFile), SIZE_TAMPON);

	            byte[] tampon = new byte[SIZE_TAMPON];
	            int longueur;
	            while ((longueur = entree.read(tampon)) > 0) {
	            	
	            	// Writing to array to use it later ??
	            	wholeFile.add(new String(tampon, 0, longueur));
	            
	            	System.out.println(new String(tampon, 0, longueur));
	            	
	                sortie.write(tampon, 0, longueur);
	            }
	        } finally {
	            try {
	                sortie.close();
	            } catch (IOException ignore) {
	            }
	            try {
	                entree.close();
	            } catch (IOException ignore) {
	            }
	        }
	    }
	    
	   public String getFileName( Part part ) {
	        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
	            if ( contentDisposition.trim().startsWith( "filename" ) ) {
	                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
	            }
	        }
	        return null;
	    }   
	    
	    
}
