package be.vdab.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.entities.Klant;
import be.vdab.repositories.KlantRepository;

/**
 * Servlet implementation class BevestigingServlet
 */
@WebServlet("/bevestiging.htm")
public class BevestigingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/bevestiging.jsp";
	private static final String GEBRUIKER = "gebruiker";
	private final transient KlantRepository klantRepository = new KlantRepository();

	@Resource(name = KlantRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantRepository.setDataSource(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute(GEBRUIKER) != null) {
			String gebruikersnaam = (String) session.getAttribute(GEBRUIKER);
			klantRepository.findByGebruikersnaam(gebruikersnaam)
					.ifPresent(klant -> request.setAttribute("klant", klant));
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String gebruikersnaam = request.getParameter("gebruikersnaam").trim();
		String paswoord = request.getParameter("paswoord").trim();
		Map<String, String> fouten = new HashMap<>();
		if (gebruikersnaam.isEmpty()) {
			fouten.put("gebruikersnaam", "Geef een gebruikersnaam");
		}
		if (paswoord.isEmpty()) {
			fouten.put("paswoord", "Geef een paswoord");
		}
		if (fouten.isEmpty()) {
			klantRepository.findByGebruikersnaam(gebruikersnaam)
					.ifPresent(klant -> request.setAttribute("klant", klant));
			Klant klant = (Klant) request.getAttribute("klant");
			if (klant != null) {
				if (klant.getPaswoord().equals(paswoord) & klant.getGebruikersnaam().equals(gebruikersnaam)) {
					HttpSession session = request.getSession();
					session.setAttribute(GEBRUIKER, klant.getGebruikersnaam());
				} else {
					fouten.put("klant", "Verkeerde gebruikersnaam of paswoord.");
				}
			} else {
				fouten.put("klant", "Verkeerde gebruikersnaam of paswoord.");
			}
		}
		request.setAttribute("fouten", fouten);
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
