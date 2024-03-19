package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Modelo.Hospedes;

public class HospedesDAO {
    
    private Connection con;

    public HospedesDAO(Connection con) {
        this.con = con;
    }

    public boolean guardarH(Hospedes hospedes) {
        try {
            String sql = "INSERT INTO hospedes (nome, sobrenome, data_nascimento, nacionalidade, telefone, id_reserva) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement pstm = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                pstm.setString(1, hospedes.getNome());
                pstm.setString(2, hospedes.getSobrenome());
                pstm.setObject(3, hospedes.getDataNascimento());
                pstm.setString(4, hospedes.getNacionalidade());
                pstm.setString(5, hospedes.getTelefone());
                pstm.setInt(6, hospedes.getIdReserva());

                int linhasAfetadas = pstm.executeUpdate();
                
                if (linhasAfetadas > 0) {
                    try (ResultSet rst = pstm.getGeneratedKeys()) {
                        while(rst.next()) {
                            hospedes.setId(rst.getInt(1));
                        }
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao guardar hóspede! " + e.getMessage(), e);
        }
        
        return false;
    }

    public List<Hospedes> mostrarH() {
        List<Hospedes> hospedesList = new ArrayList<>();
        try {
            String sql = "SELECT id, nome, sobrenome, data_nascimento, nacionalidade, telefone, id_reserva FROM hospedes";

            try (PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.execute();
                mostrarResultadoH(hospedesList, pstm);
            }
            return hospedesList;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao mostrar hóspedes! " + e.getMessage(), e);
        }
    }

    
    public List<Hospedes> buscarIdH(String id) {
        List<Hospedes> hospedesList = new ArrayList<>();
        try {
            String sql = "SELECT id, nome, sobrenome, data_nascimento, nacionalidade, telefone, id_reserva FROM hospedes WHERE id=?";
            try (PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.setString(1, id);
                try (ResultSet rst = pstm.executeQuery()) {
                    while (rst.next()) {
                        int idHospede = rst.getInt("id");
                        String nome = rst.getString("nome");
                        String sobrenome = rst.getString("sobrenome");
                        LocalDate dataNascimento = rst.getDate("data_nascimento").toLocalDate().plusDays(1);
                        String nacionalidade = rst.getString("nacionalidade");
                        String telefone = rst.getString("telefone");
                        int idReserva = rst.getInt("id_reserva");

                        Hospedes hospede = new Hospedes(idHospede, nome, sobrenome, dataNascimento, nacionalidade, telefone, idReserva);
                        hospedesList.add(hospede);
                    }
                }
            }
            return hospedesList;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar hóspede por ID! " + e.getMessage(), e);
        }
    }

    
    public void atualizarH(String nome, String sobrenome, LocalDate dataNascimento, String nacionalidade, String telefone,
			Integer idReserva, Integer id) {
    	try(PreparedStatement stm = con.prepareStatement("UPDATE hospedes SET nome=?, sobrenome=?, data_nascimento=?, "
    			+ "nacionalidade=?, telefone=?, id_reserva=? Where id=?")) {
    		stm.setString(1, nome);
    		stm.setString(2, sobrenome);
    		stm.setObject(3, dataNascimento);
    		stm.setString(4, nacionalidade);
    		stm.setString(5, telefone);
    		stm.setInt(6, idReserva);
    		stm.setInt(7, id);
    		stm.execute();
    		
    	}catch(SQLException e) {
    		throw new RuntimeException("Erro ao atualizar hóspede! " + e.getMessage(), e);
    	}
    }
    
    public void excluirH(Integer id) {
        try (PreparedStatement stm = con.prepareStatement("DELETE FROM hospedes WHERE id=?")) {
            stm.setInt(1, id);
            stm.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir hóspede: " + e.getMessage(), e);
        }
    }
    
    private void mostrarResultadoH(List<Hospedes> hospedesList, PreparedStatement pstm) throws SQLException {
    	try(ResultSet rst = pstm.executeQuery()){
    		while(rst.next()) {
    			int id = rst.getInt("id");
    			String nome = rst.getString("nome");
    			String sobrenome = rst.getString("sobrenome");
    			LocalDate dataNascimento = rst.getDate("data_nascimento").toLocalDate().plusDays(1);
    			String nacionalidade = rst.getString("nacionalidade");
    			String telefone = rst.getString("telefone");
    			int idReserva = rst.getInt("id_reserva");

    			Hospedes hospede = new Hospedes(id, nome, sobrenome, dataNascimento, nacionalidade, telefone, idReserva);
    			hospedesList.add(hospede);
    		}
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao mostrar resultados de hóspede: " + e.getMessage(), e);
        }
    }
}


