package com.subtitlor.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.subtitlor.utilities.FileHandler;

/**
 * Servlet implementation class Test - our Controller Class
 * https://www.ntu.edu.sg/home/ehchua/programming/java/JavaServlets.html
 */
@WebServlet("/FileMe")
public class FileMe extends HttpServlet {
	
	private static final long serialVersionUID = 3L;
    

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileMe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Retrieve here the last file name and data :
		
		

		this.getServletContext().getRequestDispatcher("/WEB-INF/fileme.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		FileHandler fh = new FileHandler();
		
		// We retrieve the description field iow the name of the file/translation
        String description = request.getParameter("description");
        request.setAttribute("description", description );

        // Now the file field :
        Part part = request.getPart("myfile");
            
        // Check if file is correct :
        String fileName = fh.getFileName(part);

        // If we have a file :
        if (fileName != null && !fileName.isEmpty()) {
            String fieldName = part.getName();
            // fix IE problem
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1)
                    .substring(fileName.lastIndexOf('\\') + 1);

            if (fileIsValid(fileName)) {
            
	            // Store the file at home :
	            fh.writeFile(part, fileName);
	
	            request.setAttribute(fieldName, fileName);
            } else {
            	// file not valid :
            	request.setAttribute(fieldName, "ERROR");
            }
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/fileme.jsp").forward(request, response);		
	}

	/**
	 * Only allow srt files here
	 * @param filename
	 * @return
	 */
	public boolean fileIsValid(String filename) {
		
		int pos = filename.lastIndexOf('.');
		String extension = filename.substring(pos + 1);
		if (extension.equals("srt"))
			return true;
		else return false;
	}
 
}
