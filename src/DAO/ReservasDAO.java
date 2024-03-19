package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Modelo.Reservas;

public class ReservasDAO {
    
    private Connection con;

    public ReservasDAO(Connection con) {
        this.con = con;
    }
    
    public boolean guardarR(Reservas reservas) {
        try {
            String sql = "INSERT INTO reservas (data_entrada, data_saida, valor, forma_de_pag) VALUES (?,?,?,?)";
            try (PreparedStatement pstm = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                
                pstm.setObject(1, reservas.getDataE());
                pstm.setObject(2, reservas.getDataS());
                pstm.setString(3, reservas.getValor());
                pstm.setString(4, reservas.getFormaPag());
                
                int linhasAfetadas = pstm.executeUpdate();
                
                if (linhasAfetadas > 0) {
                    try (ResultSet rst = pstm.getGeneratedKeys()) {
                        while(rst.next()) {
                            reservas.setId(rst.getInt(1));
                        }
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao guardar reserva! " + e.getMessage(), e);
        }
        
        return false;
    }
    
    public List<Reservas> mostrarR() {
    	List<Reservas> reservas = new ArrayList<Reservas>();
    	try {
			String sql = "SELECT id, data_entrada, data_saida, valor, forma_de_pag FROM reservas";
			
			try (PreparedStatement pstm = con.prepareStatement(sql)) {
				pstm.execute();
				mostrarResultadoR(reservas, pstm);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao mostrar reservas! " + e.getMessage(), e);
			
		}
    }
    
    public List<Reservas> buscarIdR(String id) {
    	List<Reservas> reservas = new ArrayList<Reservas>();
    	try {
			String sql = "SELECT id, data_entrada, data_saida, valor, forma_de_pag FROM reservas WHERE id=?";
			
			try(PreparedStatement pstm = con.prepareStatement(sql)) {
				pstm.setString(1, id);
				pstm.execute();
				
				mostrarResultadoR(reservas, pstm);
			}
			return reservas;
		} catch (SQLException e) {
			 throw new RuntimeException("Erro ao buscar reserva por ID! " + e.getMessage(), e);
		}
    }
    
    public void atualizarR(LocalDate dataE, LocalDate dataS, String valor, String formaPag, Integer id) {
        try (PreparedStatement stm = con.prepareStatement("UPDATE reservas SET data_entrada=?, data_saida=?, valor=?, forma_de_pag=? WHERE id=?")) {
            stm.setObject(1, java.sql.Date.valueOf(dataE));
            stm.setObject(2, java.sql.Date.valueOf(dataS));
            stm.setString(3, valor);
            stm.setString(4, formaPag);
            stm.setInt(5, id);
            stm.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar reserva: " + e.getMessage(), e);
        }
    }

    public void excluirR (Integer id) {
        try (PreparedStatement stm = con.prepareStatement("DELETE FROM reservas WHERE id=?")) {
            stm.setInt(1, id);
            stm.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir hóspede: " + e.getMessage(), e);
        }
    }
    
    private void mostrarResultadoR(List<Reservas> reservas, PreparedStatement pstm) throws SQLException {
        try (ResultSet rst = pstm.getResultSet()) {
            while (rst.next()) {
                int id = rst.getInt("id");
                LocalDate dataE = rst.getDate("data_entrada").toLocalDate().plusDays(1);
                LocalDate dataS = rst.getDate("data_saida").toLocalDate().plusDays(1);
                String valor = rst.getString("valor");
                String formaPag = rst.getString("forma_de_pag");

                Reservas reserva = new Reservas(id, dataE, dataS, valor, formaPag);
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao mostrar resultados de reserva: " + e.getMessage(), e);
        }
    }
}