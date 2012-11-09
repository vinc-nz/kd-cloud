package com.kdcloud.ext.rehab.angles;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class RawDataParser {
	
	
	public static Integer [] parseRawData(String campione) {
		Integer [] datiCampione = null;
		StringTokenizer st = new StringTokenizer(campione, ";[] ");
		while (st.hasMoreTokens()){
			int bx = Integer.parseInt(st.nextToken());
			int by = Integer.parseInt(st.nextToken());
			int bz = Integer.parseInt(st.nextToken());
			int fx = Integer.parseInt(st.nextToken());
			int fy = Integer.parseInt(st.nextToken());
			int fz = Integer.parseInt(st.nextToken());
			datiCampione = new Integer[] {bx, by, bz, fx, fy, fz};
			
		}
		return datiCampione;
		
	}

	
	// lista dei 10 campioni raw data che rappresentano una tupla
	// ogni campione � formato da 6 valori interi {fx, fy, fz, bx, by, bz}
	// utili a calcolare gli angoli
	public static List<Integer[]> parsePacket(String raw){
		List<Integer[]> block = new LinkedList<Integer[]>();
		StringTokenizer st = new StringTokenizer(raw, "-");
		Integer[] datiCampione = null;
		while (st.hasMoreTokens()){
			String campione = st.nextToken();
			datiCampione = parseCampione(campione);
			block.add(datiCampione);
		}
		
		
		
		return block;
		
	}

	private static Integer [] parseCampione(String campione) {
		Integer [] datiCampione = null;
		StringTokenizer st = new StringTokenizer(campione, ";, ");
		while (st.hasMoreTokens()){
			int fx = Integer.parseInt(st.nextToken());
			int fy = Integer.parseInt(st.nextToken());
			int fz = Integer.parseInt(st.nextToken());
			int bx = Integer.parseInt(st.nextToken());
			int by = Integer.parseInt(st.nextToken());
			int bz = Integer.parseInt(st.nextToken());
			datiCampione = new Integer[] {fx, fy, fz, bx, by, bz};
			
		}
		return datiCampione;
		
	}

}
