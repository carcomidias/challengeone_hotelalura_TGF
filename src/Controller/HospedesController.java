package Controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import DAO.HospedesDAO;
import Factory.ConnectionFactory;
import Modelo.Hospedes;

import DAO.ReservasDAO;

public class HospedesController {
    
    private HospedesDAO hospedesD;
    private ReservasDAO reservasD;
    
    public HospedesController() {
        Connection con = new ConnectionFactory().iniciaConexao();
        this.hospedesD = new HospedesDAO(con);
        this.reservasD = new ReservasDAO(con);
    }

    public void guardarH(Hospedes hospedes) {
        this.hospedesD.guardarH(hospedes);
    }
    
    public List<Hospedes> mostrarH() {
        return this.hospedesD.mostrarH();
    }
    
    public List<Hospedes> buscarH(String id) {
        return this.hospedesD.buscarIdH(id);
    }
    
    public void atualizarH(String nome, String sobrenome, LocalDate dataNascimento, String nacionalidade, String telefone,
			Integer idReserva, Integer id) {
    	this.hospedesD.atualizarH(nome, sobrenome, dataNascimento, nacionalidade, telefone, idReserva, id);
    }
    
    public void excluirH(Integer id) {
        try {
            List<Hospedes> hospedesList = this.hospedesD.buscarIdH(id.toString());
            for (Hospedes hospede : hospedesList) {
                if (hospede.getIdReserva() != null) {
                    this.reservasD.excluirR(hospede.getIdReserva());
                }
            }
            this.hospedesD.excluirH(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir hóspede: " + e.getMessage(), e);
        }
    }

   
}
