package com.kdcloud.ext.rehab.db;


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
	
		
	
	
	   

}