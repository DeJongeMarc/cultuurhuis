package be.vdab.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.repositories.GenreRepository;
import be.vdab.repositories.VoorstellingRepository;
import be.vdab.utils.StringUtils;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	private static final String MANDJE = "mandje";
	private final transient GenreRepository genreRepository = new GenreRepository();
	private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();
	
	@Resource(name = GenreRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		genreRepository.setDataSource(dataSource);
		voorstellingRepository.setDataSource(dataSource);
		}

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			if (session.getAttribute(MANDJE) != null) {
				request.setAttribute("mandjeAanwezig", true);
			}
		}
		request.setAttribute("genres", genreRepository.findAll());
		String genreId = request.getParameter("genre_id");
		if ( genreId != null) {
			if (StringUtils.isLong(genreId)) {
				long genreIdLong = Long.parseLong(genreId);
				genreRepository.read(genreIdLong).ifPresent(genre -> request.setAttribute("genre", genre));
				if (request.getAttribute("genre") != null) {
					request.setAttribute("genreVoorstellingen", voorstellingRepository.findByGenre(genreIdLong));
				} else {
					request.setAttribute("fout", "GenreId niet correct");
				}
				
			} else {
				request.setAttribute("fout", "GenreId niet correct");
			}
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

}

