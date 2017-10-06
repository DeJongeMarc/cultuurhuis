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
			voorstellingRepository.read(voorstellingIdLong).ifPresent(voorstelling -> request.setAttribute("voorstellingReserveren", voorstelling));;
			HttpSession session = request.getSession(false);
			Voorstelling voorstelling = (Voorstelling) request.getAttribute("voorstellingReserveren");
			if (session != null && voorstelling != null) {
				@SuppressWarnings("unchecked")
				Map<Long, Integer> mandje = (Map<Long, Integer>) session.getAttribute(MANDJE);
				if (mandje != null) {
					if (mandje.get(voorstelling.getId()) != null) {
						int plaatsen = mandje.get(voorstelling.getId());
						request.setAttribute("aantalPlaatsen", plaatsen);
					}
				}
			}
		} else {
			request.setAttribute("foutId", "Gekozen voorstelling is niet correct, kies een andere voorstelling.");
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String aantalPlaatsen = request.getParameter("aantalPlaatsen");
		String reservatieId = request.getParameter("reservatie_id");
		if (StringUtils.isLong(reservatieId)) {
			long reservatieIdLong = Long.parseLong(reservatieId);
			/*Optional<Voorstelling> optionalVoorstelling = voorstellingRepository.read(reservatieIdLong);*/
			
			Voorstelling voorstelling = voorstellingRepository.read(reservatieIdLong).get();
			request.setAttribute("voorstellingReserveren", voorstelling);
			if (voorstelling != null) {
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
						response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + REDIRECT_URL));
					} else {
						request.setAttribute("foutAantal", "Tik een getal tussen 1 en " + String.valueOf(voorstelling.getVrijePlaatsen()));
						request.getRequestDispatcher(VIEW).forward(request, response);
					}
					
				} else {
					request.setAttribute("foutAantal", "Tik een getal tussen 1 en " + String.valueOf(voorstelling.getVrijePlaatsen()));
					request.getRequestDispatcher(VIEW).forward(request, response);
			}
			
			} else {
				request.setAttribute("foutId", "Gekozen voorstelling is niet correct, kies een andere voorstelling.");
				request.getRequestDispatcher(VIEW).forward(request, response);
			}
		} else {
			request.setAttribute("foutId", "Gekozen voorstelling is niet correct, kies een andere voorstelling.");
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}
}
