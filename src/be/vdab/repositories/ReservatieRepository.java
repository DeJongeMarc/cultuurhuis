package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Reservatie;

public class ReservatieRepository extends AbstractRepository {
	private static final String CREATE = "insert into reservaties(klantid, voorstellingsid, plaatsen) values (?, ?, ?)";
	private final static Logger LOGGER = Logger.getLogger(GenreRepository.class.getName());
	
	public void create(Reservatie reservatie ) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, reservatie.getKlantId());
			statement.setLong(2, reservatie.getVoorstellingsId());
			statement.setInt(3, reservatie.getPlaatsen());
			statement.executeUpdate();
			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				resultSet.next();
				reservatie.setId(resultSet.getLong(1));
			}
			connection.commit();
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, "Probleem met database cultuurhuis", ex);
			throw new RepositoryException(ex);
		}
	}
}
