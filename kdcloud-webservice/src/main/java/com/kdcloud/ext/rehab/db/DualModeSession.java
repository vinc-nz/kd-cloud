package com.kdcloud.ext.rehab.db;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

public class DualModeSession {

	@Id
	Long id;
	Key<Paziente> paziente;
	Key<EsercizioCompleto> esercizio;
	Date dataInizio;

	public DualModeSession() {
	}

	public Key<EsercizioCompleto> getEsercizio() {
		return esercizio;
	}

	public void setEsercizio(Key<EsercizioCompleto> esercizio) {
		this.esercizio = esercizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataInizio == null) ? 0 : dataInizio.hashCode());
		result = prime * result
				+ ((esercizio == null) ? 0 : esercizio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((paziente == null) ? 0 : paziente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DualModeSession other = (DualModeSession) obj;
		if (dataInizio == null) {
			if (other.dataInizio != null)
				return false;
		} else if (!dataInizio.equals(other.dataInizio))
			return false;
		if (esercizio == null) {
			if (other.esercizio != null)
				return false;
		} else if (!esercizio.equals(other.esercizio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (paziente == null) {
			if (other.paziente != null)
				return false;
		} else if (!paziente.equals(other.paziente))
			return false;
		return true;
	}



}