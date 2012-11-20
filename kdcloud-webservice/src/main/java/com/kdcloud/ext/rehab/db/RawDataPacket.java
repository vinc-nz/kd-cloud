package com.kdcloud.ext.rehab.db;


import java.util.Arrays;
import java.util.Date;
import javax.persistence.Id;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Unindexed;

//@Unindexed
public class RawDataPacket{
		
	@Id Long id;
	int timestamp;
	Key<Paziente> paziente;
	Date data;
	int[] raw;
	int[] angoli;
	int elbowknee;
	
	public RawDataPacket(){}

	public RawDataPacket(int timestamp, Key<Paziente> paziente, Date data,
			int[] raw, int[] angoli, int elbowknee) {
		super();
		this.timestamp = timestamp;
		this.paziente = paziente;
		this.data = data;
		this.raw = raw;
		this.angoli = angoli;
		this.elbowknee = elbowknee;
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

	public int[] getRaw() {
		return raw;
	}

	public void setRaw(int[] raw) {
		this.raw = raw;
	}

	public int[] getAngoli() {
		return angoli;
	}

	public void setAngoli(int[] angoli) {
		this.angoli = angoli;
	}

	public int getElbowknee() {
		return elbowknee;
	}

	public void setElbowknee(int elbowknee) {
		this.elbowknee = elbowknee;
	}
	
		
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return timestamp + " raw = " + Arrays.toString(raw) + " angles = " + Arrays.toString(angoli);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(angoli);
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + elbowknee;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((paziente == null) ? 0 : paziente.hashCode());
		result = prime * result + Arrays.hashCode(raw);
		result = prime * result + timestamp;
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
		RawDataPacket other = (RawDataPacket) obj;
		if (!Arrays.equals(angoli, other.angoli))
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
		if (paziente == null) {
			if (other.paziente != null)
				return false;
		} else if (!paziente.equals(other.paziente))
			return false;
		if (!Arrays.equals(raw, other.raw))
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}
	
	
	
	
	   

}