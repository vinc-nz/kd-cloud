package com.kdcloud.ext.rehab.db;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Serialized;

public class EsercizioCompleto {

	@Id
	Long id;
	String nome;
	int numero;
	Key<Paziente> paziente;
	int elbowknee;
	Date data;
	int lenght;
	@Serialized
	List<Integer[]> raw;
	@Serialized
	List<Integer[]> angoli;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Key<Paziente> getPaziente() {
		return paziente;
	}

	public void setPaziente(Key<Paziente> paziente) {
		this.paziente = paziente;
	}

	public int getElbowknee() {
		return elbowknee;
	}

	public void setElbowknee(int elbowknee) {
		this.elbowknee = elbowknee;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public List<Integer[]> getRaw() {
		return raw;
	}

	public void setRaw(List<Integer[]> raw) {
		this.raw = raw;
	}

	public List<Integer[]> getAngoli() {
		return angoli;
	}

	public void setAngoli(List<Integer[]> angoli) {
		this.angoli = angoli;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((angoli == null) ? 0 : angoli.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + elbowknee;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + lenght;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + numero;
		result = prime * result
				+ ((paziente == null) ? 0 : paziente.hashCode());
		result = prime * result + ((raw == null) ? 0 : raw.hashCode());
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
		EsercizioCompleto other = (EsercizioCompleto) obj;
		if (angoli == null) {
			if (other.angoli != null)
				return false;
		} else if (!angoli.equals(other.angoli))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (elbowknee != other.elbowknee)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lenght != other.lenght)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (numero != other.numero)
			return false;
		if (paziente == null) {
			if (other.paziente != null)
				return false;
		} else if (!paziente.equals(other.paziente))
			return false;
		if (raw == null) {
			if (other.raw != null)
				return false;
		} else if (!raw.equals(other.raw))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EsercizioCompleto [nome=" + nome + ", numero=" + numero
				+ ", paziente=" + paziente + ", elbowknee=" + elbowknee
				+ ", data=" + data + ", lenght=" + lenght + ", raw=" + raw
				+ ", angoli=" + angoli + "]";
	}

	public Document toXMLDocument(Document d) {
		Element root = d.createElement("downloadeserciziocompletoOutput");
		d.appendChild(root);
		root.setAttribute("nome", "" + nome);
		root.setAttribute("numero", "" + numero);
		root.setAttribute("elbowknee", "" + elbowknee);
		root.setAttribute("data", "" + data.toGMTString());
		root.setAttribute("lenght", "" + lenght);
		int i = 0;
		for (Integer[] raw_sample : raw) {
			Element rawdata = d.createElement("raw_data");
			rawdata.setAttribute("timestamp", "" + i++);
			rawdata.setAttribute("bx", "" + raw_sample[0]);
			rawdata.setAttribute("by", "" + raw_sample[1]);
			rawdata.setAttribute("bz", "" + raw_sample[2]);
			rawdata.setAttribute("fx", "" + raw_sample[3]);
			rawdata.setAttribute("fy", "" + raw_sample[4]);
			rawdata.setAttribute("fz", "" + raw_sample[5]);
			root.appendChild(rawdata);
		}
		i = 0;
		for (Integer[] angle_sample : angoli) {
			Element angle = d.createElement("angles_data");
			angle.setAttribute("timestamp", "" + i++);
			angle.setAttribute("elbowknee", "" + angle_sample[0]);
			angle.setAttribute("backline", "" + angle_sample[1]);
			angle.setAttribute("foreline", "" + angle_sample[2]);
			angle.setAttribute("sideangle", "" + angle_sample[3]);
			root.appendChild(angle);
		}

		d.normalizeDocument();
		return d;

	}

}
