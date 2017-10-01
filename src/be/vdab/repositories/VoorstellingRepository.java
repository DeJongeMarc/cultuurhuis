package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Voorstelling;

public class VoorstellingRepository extends AbstractRepository {
	private static final String BEGIN_SELECT = "select id, titel, uitvoerders, datum, genreId, prijs, vrijePlaatsen from voorstellingen ";
	private static final String READ = BEGIN_SELECT + "where genreid = ? and datum > {fn now()} order by datum";
	private static final String READ_ONE = BEGIN_SELECT + "where genreid = ?";
	private final static Logger LOGGER = Logger.getLogger(GenreRepository.class.getName());

	public List<Voorstelling> read(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(READ)) {
			List<Voorstelling> voorstellingen = new ArrayList<>();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					voorstellingen.add(resultSetRijNaarVoorstelling(resultSet));
				}
			}
			connection.commit();
			return voorstellingen;
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new RepositoryException(ex);
		}
	}
	
	public Voorstelling readOne(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(READ_ONE)) {
			Voorstelling voorstellingen=null;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					voorstellingen = resultSetRijNaarVoorstelling(resultSet);
				}
			}
			connection.commit();
			return voorstellingen;
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new RepositoryException(ex);
		}
	}

	private Voorstelling resultSetRijNaarVoorstelling(ResultSet resultSet) throws SQLException {
		Voorstelling voorstelling = new Voorstelling(resultSet.getLong("id"), resultSet.getString("titel"),
				resultSet.getString("uitvoerders"), resultSet.getTimestamp("datum").toLocalDateTime(), resultSet.getLong("genreId"),
				resultSet.getBigDecimal("prijs"), resultSet.getInt("vrijePlaatsen"));
		return voorstelling;
	}
}