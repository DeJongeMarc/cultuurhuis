package be.vdab.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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
 * Servlet implementation class NieuweKlantServlet
 */
@WebServlet("/nieuweklant.htm")
public class NieuweKlantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/nieuweklant.jsp";
	private static final String REDIRECT_URL = "/bevestiging.htm";
	private static final String GEBRUIKER = "gebruiker";
	private final transient KlantRepository klantRepository = new KlantRepository();

	@Resource(name = KlantRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantRepository.setDataSource(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Enumeration<String> parameterNamen = request.getParameterNames();
		List<String> fouten = new ArrayList<>();
		Map<String, String> parameters = new HashMap<>();
		while (parameterNamen.hasMoreElements()) {
			String paramNaam = parameterNamen.nextElement();
			String paramValue = request.getParameter(paramNaam).trim();
			parameters.put(paramNaam, paramValue);
			if (paramValue.isEmpty()) {
				fouten.add(paramNaam.substring(0, 1).toUpperCase() + paramNaam.substring(1) + " niet ingevuld.");
			}
		}
		if (fouten.isEmpty()) {
			if (!parameters.get("paswoord").equals(parameters.get("herhaalpaswoord"))) {
				fouten.add("Paswoord en Herhaal paswoord zijn verschillend.");
			} else {
				if (klantRepository.findByGebruikersnaam(parameters.get("gebruikersnaam")).isPresent()) {
					fouten.add("Een klant met Gebruikersnaam komt al voor in de database");
				}
			}
			if (fouten.isEmpty()) {
				HttpSession session = request.getSession();
				if (session.getAttribute(GEBRUIKER) == null) {
					Klant klant = new Klant(parameters.get("voornaam"), parameters.get("familienaam"),
							parameters.get("straat"), parameters.get("huisnr"), parameters.get("postcode"),
							parameters.get("gemeente"), parameters.get("gebruikersnaam"), parameters.get("paswoord"));
					klantRepository.create(klant);
					session.setAttribute(GEBRUIKER, klant.getGebruikersnaam());
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + REDIRECT_URL));
				} else {
					fouten.add("Er is al een gebruiker aangemeld, zie Bevestiging reservatie");
				}
			}
		}
		if (!fouten.isEmpty()) {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}
}

