package be.vdab.servlets;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.entities.Genre;
import be.vdab.entities.Voorstelling;
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
		request.setAttribute("genres", genreRepository.findAll());
		String genreId = request.getParameter("genre_id");
		if ( genreId != null) {
			if (StringUtils.isLong(genreId)) {
				long genreIdLong = Long.parseLong(genreId);
				Genre genre = genreRepository.read(genreIdLong);
				if (genre != null) {
					request.setAttribute("genreNaam", genre.getNaam());
					List<Voorstelling> listVoorstellingen = voorstellingRepository.read(genreIdLong);
					request.setAttribute("genreVoorstellingen", listVoorstellingen );
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

