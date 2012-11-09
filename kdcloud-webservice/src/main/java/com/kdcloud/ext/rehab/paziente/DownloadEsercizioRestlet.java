package com.kdcloud.ext.rehab.paziente;

import java.util.List;

import org.restlet.data.Form;
import org.restlet.resource.Post;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kdcloud.ext.rehab.db.EsercizioRiferimento;
import com.kdcloud.ext.rehab.db.Paziente;
import com.kdcloud.server.entity.User;
import com.kdcloud.server.rest.resource.KDServerResource;

public class DownloadEsercizioRestlet extends KDServerResource {

	public static final String URI = "/rehab/downloadesercizio";

	@Post
	protected String doPost(Form form) {

		User user = getUser();
		String username = user.getName();
		
		
		String es = form.getFirstValue("esercizio");
		int esercizio = Integer.parseInt(es);
		
		//Paziente p = getPazienteByUsername(username);
		String ris = getEsercizioByPazienteAndNumero(username, esercizio);
		return "" + ris;
			
			
			
		}
		
	private String getEsercizioByPazienteAndNumero(String username, int esercizio) {
		
		Key<Paziente> paz = new Key<Paziente>(Paziente.class,
				username);

		try {
			ObjectifyService.register(EsercizioRiferimento.class);
		} catch (Exception e) {

		}
		
		Objectify ofy = ObjectifyService.begin();
		List<EsercizioRiferimento> list = ofy.query(EsercizioRiferimento.class).order("timestamp").filter("numero", esercizio).list();

		StringBuilder sb = new StringBuilder();
		
		for(EsercizioRiferimento e : list){
			sb.append(e.getRaw()+" - "+e.getAngoli()+"timestamp = "+ e.getTimestamp()+" numero = "+e.getNumero()+"*");
		}
		return sb.toString();
		
	}


}
