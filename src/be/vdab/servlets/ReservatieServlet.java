package be.vdab.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.entities.Voorstelling;
import be.vdab.repositories.VoorstellingRepository;

/**
 * Servlet implementation class ReservatieServlet
 */
@WebServlet(name = "ReserverenServlet", urlPatterns = { "/reserveren.htm" })
public class ReservatieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/reserveren.jsp";
	private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();

	@Resource(name = VoorstellingRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		voorstellingRepository.setDataSource(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long voorstellingId = Long.parseLong(request.getParameter("voorstelling_id"));
		Voorstelling voorstelling = voorstellingRepository.readOne(voorstellingId);
		request.setAttribute("voorstellingReserveren", voorstelling);
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
	
}
