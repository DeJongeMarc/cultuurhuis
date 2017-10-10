package be.vdab.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.entities.Klant;
import be.vdab.entities.Reservatie;
import be.vdab.entities.Voorstelling;
import be.vdab.repositories.KlantRepository;
import be.vdab.repositories.ReservatieRepository;
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
	private final transient ReservatieRepository reservatieRepository = new ReservatieRepository();

	@Resource(name = KlantRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantRepository.setDataSource(dataSource);
		voorstellingRepository.setDataSource(dataSource);
		reservatieRepository.setDataSource(dataSource);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute(MANDJE) != null & session.getAttribute(GEBRUIKER) != null) {
			@SuppressWarnings("unchecked")
			Map<Long, Integer> mandje = (Map<Long, Integer>) session.getAttribute(MANDJE);
			request.setAttribute("overzichtmandje", mandje);
			String gebuirkersnaam = (String) session.getAttribute(GEBRUIKER);
			Klant klant = klantRepository.findByGebruikersnaam(gebuirkersnaam).get();
			List<Voorstelling> gelukteReserveringen = mandje.entrySet().stream()
					.map(entry -> voorstellingRepository.update(entry.getKey(), entry.getValue()))
					.filter(optionalVoorstelling -> optionalVoorstelling.isPresent())
					.map(optionalVoorstelling -> optionalVoorstelling.get()).collect(Collectors.toList());
			request.setAttribute("gelukteReserveringen", gelukteReserveringen);
			List<Voorstelling> mislukteReserveringen = mandje.keySet().stream()
					.map(id -> voorstellingRepository.read(id))
					.filter(optionalVoorstelling -> optionalVoorstelling.isPresent())
					.map(optionalVoorstelling -> optionalVoorstelling.get())
					.filter(voorstelling -> !gelukteReserveringen.contains(voorstelling)).collect(Collectors.toList());
			request.setAttribute("mislukteReserveringen", mislukteReserveringen);
			for (Voorstelling eenVoorstelling : gelukteReserveringen) {
				Reservatie reservatie = new Reservatie(klant.getId(), eenVoorstelling.getId(),mandje.get(eenVoorstelling.getId()));
				reservatieRepository.create(reservatie);
			}
			session.removeAttribute(MANDJE);
		} else {
			request.setAttribute("fout", "U hebt nog geen reserveringen of bent nog niet aangemeld");
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
