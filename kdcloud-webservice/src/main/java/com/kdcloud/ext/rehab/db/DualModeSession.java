package com.kdcloud.ext.rehab.db;


import java.util.Date;
import javax.persistence.Id;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Unindexed;

//@Unindexed
public class DualModeSession{
		
	@Id Long id;
	Key<Paziente> paziente;
	Date dataInizio;
	int numeroMovimento;
	//boolean isLoop;
	
	public DualModeSession(){}

	public DualModeSession(Key<Paziente> paziente, Date data,
			int numero) {
		super();
		this.paziente = paziente;
		this.dataInizio = data;
		this.numeroMovimento = numero;
	}



	public Key<Paziente> getPaziente() {
		return paziente;
	}

	public void setPaziente(Key<Paziente> paziente) {
		this.paziente = paziente;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setNumeroMovimento(int numeroMovimento) {
		this.numeroMovimento = numeroMovimento;
	}
	
	public int getNumeroMovimento() {
		return numeroMovimento;
	}
	
		
	
	
	   

}