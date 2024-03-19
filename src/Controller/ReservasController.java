package Controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import DAO.ReservasDAO;
import Factory.ConnectionFactory;
import Modelo.Hospedes;
import Modelo.Reservas;
import DAO.HospedesDAO;

public class ReservasController {

    private ReservasDAO reservaD;
    private HospedesDAO hospedesD;

    public ReservasController() {
        Connection con = new ConnectionFactory().iniciaConexao();
        this.reservaD = new ReservasDAO(con);
        this.hospedesD = new HospedesDAO(con);
    }

    public boolean guardarR(Reservas reservas) {
        return this.reservaD.guardarR(reservas);
    }
    
    public List<Reservas> mostrarR() {
    	return this.reservaD.mostrarR();
    }
    
    public List<Reservas> buscarR(String id) {
    	return this.reservaD.buscarIdR(id);
    }
    
    public void atualizarR(LocalDate dataE, LocalDate dataS, String valor, String formaPag, Integer id) {
    	this.reservaD.atualizarR(dataE, dataS, valor, formaPag, id);
    }
    
    public void excluirR(Integer id) {
        List<Hospedes> hospedesList = this.hospedesD.buscarIdH(id.toString());
        for (Hospedes hospede : hospedesList) {
            this.hospedesD.excluirH(hospede.getId());
        }
        this.reservaD.excluirR(id);
    }

}
