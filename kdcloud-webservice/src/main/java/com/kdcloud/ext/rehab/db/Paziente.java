package com.kdcloud.ext.rehab.db;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Transient;

//@Unindexed salva nel db ma nn � possibile fare query (applicabile anche alle singole propriet�)
public class Paziente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id private String username; //email di google
	//private String password;
	private String nome;
	private String cognome;
	@Transient private int elbowknee;//0: elbow, 1: knee
	@Transient private int localcloud;//0: local, 1: cloud
	@Transient private int storeRawData;//0: store raw NO, 1: store raw YES
//	@Transient private List<String> rawBuffer;
//	@Transient private List<String> angleBuffer;
	
	private int[] F_MIN  = {2115,	2180,	2062};
	private int[] F_MAX  = {2720,	2805,	2675};
	private int[] F_ZERO = {2410,	2485,	2380};
	private int[] B_MIN  = {2155,	2180,	2095};
	private int[] B_MAX  = {2770,	2820,	2660};
	private int[] B_ZERO = {2470,	2504,	2385};
	
	private int numeroEserciziRegistrati = 0;
	
	public Paziente() {}
	
	public Paziente (String user,  String nom, String cog) {
		username = user;
		//password = pwd;
		nome = nom;
		cognome = cog;
	}
	

	
	
	
//	public String getPassword() {
//		return password;
//	}
	public String getUsername() {
		return username;
	}
	public String getNome() {
		return nome;
	}
	public String getCognome() {
		return cognome;
	}
	public int getElbowknee() {
		return elbowknee;
	}
	public int getLocalcloud() {
		return localcloud;
	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public void setElbowknee(int elbowknee) {
		this.elbowknee = elbowknee;
	}
	public void setLocalcloud(int localcloud) {
		this.localcloud = localcloud;
	}
	
	public int getStoreRawData() {
		return storeRawData;
	}
	
	public void setStoreRawData(int storeRawData) {
		this.storeRawData = storeRawData;
	}
	
	public int getNumeroEserciziRegistrati() {
		return numeroEserciziRegistrati;
	}
	
	public void setNumeroEserciziRegistrati(int numeroEserciziRegistrati) {
		this.numeroEserciziRegistrati = numeroEserciziRegistrati;
	}
	
	
	

	public int[] getF_MIN() {
		return F_MIN;
	}

	public void setF_MIN(int[] f_MIN) {
		F_MIN = f_MIN;
	}

	public int[] getF_MAX() {
		return F_MAX;
	}

	public void setF_MAX(int[] f_MAX) {
		F_MAX = f_MAX;
	}

	public int[] getF_ZERO() {
		return F_ZERO;
	}

	public void setF_ZERO(int[] f_ZERO) {
		F_ZERO = f_ZERO;
	}

	public int[] getB_MIN() {
		return B_MIN;
	}

	public void setB_MIN(int[] b_MIN) {
		B_MIN = b_MIN;
	}

	public int[] getB_MAX() {
		return B_MAX;
	}

	public void setB_MAX(int[] b_MAX) {
		B_MAX = b_MAX;
		
	}

	public int[] getB_ZERO() {
		return B_ZERO;
	}

	public void setB_ZERO(int[] b_ZERO) {
		B_ZERO = b_ZERO;
		
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Paziente)) return false;
		Paziente p = (Paziente) obj;
		return username.equals(p.getUsername());
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return username + " " + nome + " " +cognome; 
	}
    

}
