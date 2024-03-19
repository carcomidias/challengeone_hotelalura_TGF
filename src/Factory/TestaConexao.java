package Factory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestaConexao {

	public static void main(String[] args) throws SQLException {
		
		ConnectionFactory con = new ConnectionFactory();
		Connection cone = con.iniciaConexao();
		System.out.println("iniando a conexão");
		
		cone.close();
		System.out.println("Fechando a conexão");	}
}