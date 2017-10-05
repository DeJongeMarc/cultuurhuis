package be.vdab.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.entities.Voorstelling;
import be.vdab.repositories.VoorstellingRepository;
import be.vdab.utils.StringUtils;

/**
 * Servlet implementation class ReserverenServlet
 */
@WebServlet("/reserveren.htm")
public class ReserverenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/reserveren.jsp";
	private static final String REDIRECT_URL ="/reservatiemandje.htm";
	private static final String MANDJE = "mandje";
	private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();

	@Resource(name = VoorstellingRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		voorstellingRepository.setDataSource(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String voorstellingId = request.getParameter("voorstelling_id");
		if (StringUtils.isLong(voorstellingId)) {
			long voorstellingIdLong = Long.parseLong(voorstellingId);
			Voorstelling voorstelling = voorstellingRepository.readOne(voorstellingIdLong);
			request.setAttribute("voorstellingReserveren", voorstelling);
			HttpSession session = request.getSession(false);
			if (session != null) {
				@SuppressWarnings("unchecked")
				Map<Long, Long> mandje = (Map<Long, Long>) session.getAttribute(MANDJE);
				if (mandje != null) {
					request.setAttribute("reservatiesInMandje", mandje);
				}
			}
		} else {
			request.setAttribute("foutId", "VoorstellingId niet correct");
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String aantalPlaatsen = request.getParameter("aantalPlaatsen");
		String reservatieId = request.getParameter("reservatie_id");
		long reservatieIdLong = Long.parseLong(reservatieId);
		System.out.println("aantalplaatsen--" + aantalPlaatsen);
		System.out.println("reservatieID--" + reservatieId);
		Voorstelling voorstelling = voorstellingRepository.readOne(reservatieIdLong);
		System.out.println("reservatie titel--"+voorstelling.getTitel());
		if (StringUtils.isInteger(aantalPlaatsen)) {
			int aantalPlaatsenInt = Integer.parseInt(aantalPlaatsen);
			if (aantalPlaatsenInt > 0 && aantalPlaatsenInt <= voorstelling.getVrijePlaatsen()) {
				HttpSession session = request.getSession();
				@SuppressWarnings("unchecked")
				Map<Long, Integer> mandje = (Map<Long, Integer>) session.getAttribute(MANDJE);
				if (mandje == null) {
					mandje = new LinkedHashMap<>();
				}
				mandje.put(voorstelling.getId(), aantalPlaatsenInt);
				session.setAttribute(MANDJE, mandje);
				request.setAttribute("voorstellingReserveren", voorstelling);
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + REDIRECT_URL));
			} else {
				request.setAttribute("foutAantal", "Tik een getal tussen 1 en " + String.valueOf(voorstelling.getVrijePlaatsen()));
				request.setAttribute("voorstellingReserveren", voorstelling);
				request.getRequestDispatcher(VIEW).forward(request, response);
			}
			
		} else {
			request.setAttribute("foutAantal", "Tik een getal tussen 1 en " + String.valueOf(voorstelling.getVrijePlaatsen()));
			request.setAttribute("voorstellingReserveren", voorstelling);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
		
	}
}
