package be.vdab.servlets;

import java.io.IOException;
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
import be.vdab.repositories.VoorstellingRepository;

/**
 * Servlet implementation class OverzichtServlet
 */
@WebServlet("/overzicht.htm")
public class OverzichtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MANDJE = "mandje";
	private static final String GEBRUIKER = "gebruiker";
	private static final String VIEW = "/WEB-INF/JSP/overzicht.jsp";
	private final transient KlantRepository klantRepository = new KlantRepository();
	private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();

	@Resource(name = KlantRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantRepository.setDataSource(dataSource);
		voorstellingRepository.setDataSource(dataSource);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute(MANDJE) != null & session.getAttribute(GEBRUIKER) != null) {
			@SuppressWarnings("unchecked")
			Map<Long, Integer> mandje = (Map<Long, Integer>) session.getAttribute(MANDJE);
			String gebuirkersnaam = (String) session.getAttribute(GEBRUIKER);
			Klant klant = klantRepository.findByGebruikersnaam(gebuirkersnaam).get();
			for (long voorstellingId : mandje.keySet()) {
				voorstellingRepository.read(voorstellingId)
			}
		} else {
			request.setAttribute("fout", "U hebt nog geen reserveringen of bent nog niet aangemeld");
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
