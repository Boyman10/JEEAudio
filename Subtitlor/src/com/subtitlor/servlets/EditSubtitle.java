package com.subtitlor.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitlor.model.entity.Traduction;
import com.subtitlor.model.service.TranslationService;
//import com.subtitlor.utilities.SubtitlesHandler;

@WebServlet("/EditSubtitle")
public class EditSubtitle extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String FILE_NAME = "/WEB-INF/password_presentation.srt";
	
	private TranslationService serviceT;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = getServletContext();
		
		System.out.println(context.getRealPath(FILE_NAME));
		
		//@Deprecated
		//SubtitlesHandler subtitles = new SubtitlesHandler(context.getRealPath(FILE_NAME));
		
		serviceT = new TranslationService();
		ArrayList<Traduction> subtitles = serviceT.getLastEntries(null);
		
		request.setAttribute("subtitles", subtitles);
		
		/*@Deprecated
		// Retrieve translated strings
		if (!subtitles.isEmpty())
		request.setAttribute("subtitles_en", serviceT.getLastEntries(subtitles.get(0).getFilename()));
		*/
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/edit_subtitle.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
