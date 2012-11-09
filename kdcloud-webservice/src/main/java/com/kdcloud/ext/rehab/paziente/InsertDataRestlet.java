package com.kdcloud.ext.rehab.paziente;

import java.util.Date;
import java.util.StringTokenizer;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.angles.AngleController;
import com.kdcloud.ext.rehab.angles.CalibrationController;
import com.kdcloud.ext.rehab.angles.RawDataParser;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.ext.rehab.db.RawDataPacket;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class InsertDataRestlet extends KDServerResource {

	public static final String URI = "/rehab/insertdata";

	@Post
	protected String doPost(Form form) {

		User user = getUser();
		String username = user.getName();

		String timest = form.getFirstValue("timestamp");
		int timestamp = Integer.parseInt(timest);
		
		String ek = form.getFirstValue("elbowknee"); //TODO fix
		int elbowknee = Integer.parseInt(ek);
		
		String sr = form.getFirstValue("storeraw"); //TODO fix
		int storeRawData = Integer.parseInt(sr);
		
		String raw = form.getFirstValue("raw");
		raw = raw.trim();
		
		Date data = new Date();
		
		String f_min = form.getFirstValue("F_MIN");
		String f_max = form.getFirstValue("F_MAX");
		String f_zero = form.getFirstValue("F_ZERO");
		String b_min = form.getFirstValue("B_MIN");
		String b_max = form.getFirstValue("B_MAX");
		String b_zero = form.getFirstValue("B_ZERO");

		int [] F_MIN = parse(f_min);
		int [] F_MAX = parse(f_max);
		int [] F_ZERO = parse(f_zero);
		int [] B_MIN = parse(b_min);
		int [] B_MAX = parse(b_max);
		int [] B_ZERO = parse(b_zero);
		
		CalibrationController.F_MIN = F_MIN;
		CalibrationController.F_MAX = F_MAX;
		CalibrationController.F_ZERO = F_ZERO;
		CalibrationController.B_MIN = B_MIN;
		CalibrationController.B_MAX = B_MAX;
		CalibrationController.B_ZERO = B_ZERO;
		
		//calcolo angoli
		Integer[] rawData = RawDataParser.parseRawData(raw);
		AngleController controller;
		Integer[] angoli = null;
		controller = new AngleController();
		angoli = controller.computeAngles(rawData, 
					elbowknee);

		String angles = "["+angoli[0]+";"+angoli[1]+";"+angoli[2]+";"+angoli[3]+"]";

		//salvataggio nel DB
		try {
			ObjectifyService.register(RawDataPacket.class);
		} catch (Exception e) {
		}
		Objectify ofy = ObjectifyService.begin();
		RawDataPacket r;
		Key<Paziente> paz = new Key<Paziente>(Paziente.class, username);
		if (storeRawData == 0)// non salvare i raw data
			r = new RawDataPacket(timestamp, paz, data, null,
					angles, elbowknee);
		else
			r = new RawDataPacket(timestamp, paz, data, raw,
					angles, elbowknee);
		ofy.put(r);
		
		//risposta
		return (timestamp+"-"+angles);
		

		

	}
	
	private int[] parse(String vettore) {
		// [x;y;z]
		int[] ris = new int[3];
		StringTokenizer st = new StringTokenizer(vettore, "[];");
		while (st.hasMoreTokens()) {
			ris[0] = Integer.parseInt(st.nextToken());
			ris[1] = Integer.parseInt(st.nextToken());
			ris[2] = Integer.parseInt(st.nextToken());
		}
		return ris;
	}

}
