package Factory;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

	public DataSource dataSource;
	
	public ConnectionFactory() { 
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotel_alura_br?useTimeZone=true&serverTimezone=UTC");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("46L31CeS0US4*");
		
		this.dataSource = comboPooledDataSource;
	}
		public Connection iniciaConexao() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("Houve um erro na conexão");
			throw new RuntimeException(e);
		}
	}
}