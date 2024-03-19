package Modelo;

import java.time.LocalDate;

public class Reservas {
    private static int contadorIds = 0;
    private int id;
    private LocalDate dataE;
    private LocalDate dataS;
    private String valor;
    private String formaPag;

    public Reservas(LocalDate dataE, LocalDate dataS, String valor, String formaPag) {
        this.id = ++contadorIds;
        this.dataE = dataE;
        this.dataS = dataS;
        this.valor = valor;
        this.formaPag = formaPag;
    }
    
    
    public Reservas(int id, LocalDate dataE, LocalDate dataS, String valor, String formaPag) {
		super();
		this.id = id;
		this.dataE = dataE;
		this.dataS = dataS;
		this.valor = valor;
		this.formaPag = formaPag;
	}

    
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataE() {
        return dataE;
    }

    public void setDataE(LocalDate dataE) {
        this.dataE = dataE;
    }

    public LocalDate getDataS() {
        return dataS;
    }

    public void setDataS(LocalDate dataS) {
        this.dataS = dataS;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getFormaPag() {
        return formaPag;
    }

    public void setFormaPag(String formaPag) {
        this.formaPag = formaPag;
    }
}
