package be.vdab.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.repositories.GenreRepository;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	private final transient GenreRepository genreRepository = new GenreRepository();
	
	@Resource(name = GenreRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		genreRepository.setDataSource(dataSource);
		}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("genres", genreRepository.findAll());
		if (request.getParameterValues("id") != null) {
			request.setAttribute("gekozenGenre", genreRepository.read(Long.parseLong(request.getParameterValues("id")[0])));
			/**request.setAttribute("genreVoorstellingen",
					Arrays.stream(request.getParameterValues("id")).map(id -> genreRepository.read(Long.parseLong(id)))
							.filter(optionalGenre -> optionalGenre.isPresent())
							.map(optionalGenre -> optionalGenre.get()).collect(Collectors.toList()));*/
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

}
