package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Klant;

public class KlantRepository extends AbstractRepository {
	private static final String BEGIN_SELECT = "select id, voornaam, familienaam, straat, huisnr, postcode, gemeente, gebruikersnaam, paswoord from klanten ";
	private static final String READ = BEGIN_SELECT + "where id=?";
	private static final String FIND_BY_GEBRUIKERSNAAM = BEGIN_SELECT + "where gebruikersnaam=?";
	private final static Logger LOGGER = Logger.getLogger(GenreRepository.class.getName());

	private Klant resultSetRijNaarKlant(ResultSet resultSet) throws SQLException {
		return new Klant(resultSet.getLong("id"), resultSet.getString("voornaam"), resultSet.getString("familienaam"),
				resultSet.getString("straat"), resultSet.getString("huisnr"), resultSet.getString("postcode"),
				resultSet.getString("gemeente"), resultSet.getString("gebruikersnaam"),
				resultSet.getString("paswoord"));
	}

	public Optional<Klant> read(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(READ)) {
			Optional<Klant> klant;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					klant = Optional.of(resultSetRijNaarKlant(resultSet));
				} else {
					klant = Optional.empty();
				}
			}
			connection.commit();
			return klant;
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new RepositoryException(ex);
		}
	}
	
	public Optional<Klant> findByGebruikersnaam(String gebruikersnaam) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_BY_GEBRUIKERSNAAM)) {
			Optional<Klant> klant;
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setString(1, gebruikersnaam);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					klant = Optional.of(resultSetRijNaarKlant(resultSet));
				} else {
					klant = Optional.empty();
				}
			}
			connection.commit();
			return klant;
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new RepositoryException(ex);
		}
	}
}
