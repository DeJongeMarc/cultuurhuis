package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Genre;

public class GenreRepository extends AbstractRepository {
	private static final String BEGIN_SELECT = "select id, naam from genres ";
	private static final String FIND_ALL = BEGIN_SELECT + "order by naam";
	private static final String READ = BEGIN_SELECT + "where id=?";
	private final static Logger LOGGER = Logger.getLogger(GenreRepository.class.getName());
	
	public List<Genre> findAll() {
		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
			List<Genre> genres = new ArrayList<>();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			try (ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
				while (resultSet.next()) {
					genres.add(resultSetRijNaarGenre(resultSet));
				}
			}
			connection.commit();
			return genres;
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new RepositoryException(ex);
		}
	}
	
	private Genre resultSetRijNaarGenre(ResultSet resultSet) throws SQLException {
		return new Genre(resultSet.getLong("id"), resultSet.getString("naam"));
	}
	
	public Genre read(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(READ)) {
			Genre genre=null;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					genre = resultSetRijNaarGenre(resultSet);
				} else {
					
				}
			}
			connection.commit();
			return genre;
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new RepositoryException(ex);
		}
	}
}
