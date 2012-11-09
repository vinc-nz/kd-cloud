package com.kdcloud.ext.rehab.db;


import java.util.Date;
import javax.persistence.Id;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Unindexed;

//@Unindexed
public class EsercizioRiferimento{
		
	@Id Long id;
	int timestamp;
	Key<Paziente> paziente;
	Date data;
	String raw;
	String angoli;
	int elbowknee;
	int numero;
	
	public EsercizioRiferimento(){}

	public EsercizioRiferimento(int timestamp, Key<Paziente> paziente, Date data,
			String raw, String angoli, int elbowknee, int numero) {
		super();
		this.timestamp = timestamp;
		this.paziente = paziente;
		this.data = data;
		this.raw = raw;
		this.angoli = angoli;
		this.elbowknee = elbowknee;
		this.numero = numero;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public Key<Paziente> getPaziente() {
		return paziente;
	}

	public void setPaziente(Key<Paziente> paziente) {
		this.paziente = paziente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public String getAngoli() {
		return angoli;
	}

	public void setAngoli(String angoli) {
		this.angoli = angoli;
	}

	public int getElbowknee() {
		return elbowknee;
	}

	public void setElbowknee(int elbowknee) {
		this.elbowknee = elbowknee;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public int getNumero() {
		return numero;
	}
	
		
	
	
	   

}