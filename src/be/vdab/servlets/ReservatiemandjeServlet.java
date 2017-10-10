package be.vdab.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
 * Servlet implementation class ReservatiemandjeServlet
 */
@WebServlet("/reservatiemandje.htm")
public class ReservatiemandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/reservatiemandje.jsp";
	private static final String MANDJE = "mandje";
	private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();

	@Resource(name = VoorstellingRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		voorstellingRepository.setDataSource(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		@SuppressWarnings("unchecked")
		Map<Long, Integer> mandje = (Map<Long, Integer>) session.getAttribute(MANDJE);
		if (mandje != null) {
			String[] verwijderdeIds = request.getParameterValues("verwijderd_Id");
			if (verwijderdeIds != null) {
				for (String verwijderdId : verwijderdeIds) {
					if (StringUtils.isLong(verwijderdId)) {
						long verwijderdIdLong = Long.parseLong(verwijderdId);
						mandje.remove(verwijderdIdLong);
						if (mandje.isEmpty()) {
							session.removeAttribute(MANDJE);
						}
					}
				}
			}
			request.setAttribute("reservatieMandje", mandje);
			Set<Voorstelling> voorstellingen = new TreeSet<>();
			BigDecimal teBetalen = BigDecimal.ZERO;
			for (long eenVoorstellingId : mandje.keySet()) {
				Voorstelling voorstelling = voorstellingRepository.read(eenVoorstellingId).get();
				voorstellingen.add(voorstelling);
				teBetalen = voorstelling.getPrijs().multiply(BigDecimal.valueOf(mandje.get(eenVoorstellingId)))
						.add(teBetalen);
			}
			request.setAttribute("voorstellingenReservatie", voorstellingen);
			request.setAttribute("teBetalen", teBetalen);
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
