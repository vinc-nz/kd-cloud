package com.kdcloud.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.MediaType;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import com.kdcloud.server.domain.Report;
import com.kdcloud.server.domain.ServerAction;
import com.kdcloud.server.domain.ServerMethod;
import com.kdcloud.server.domain.ServerParameter;
import com.kdcloud.server.domain.datastore.ModEntity;

public class ModalityExecution extends Thread {

	String serverUri;
	ModEntity modality;
	ChallengeResponse auth;
	

	public ModalityExecution(String serverUri, ModEntity modality) {
		super();
		this.serverUri = serverUri;
		this.modality = modality;
	}
	
	public ModalityExecution(String serverUri, ModEntity modality,
			ChallengeResponse auth) {
		super();
		this.serverUri = serverUri;
		this.modality = modality;
		this.auth = auth;
	}


	public void run() {
		Queue<ServerAction> queue = new LinkedList<ServerAction>(
				modality.getServerCommands());
		HashMap<String, String> map = new HashMap<String, String>();

		while (!queue.isEmpty()) {
			ServerAction action = queue.poll();
			this.executeAction(action, map);
			if (action.isRepeat())
				queue.add(action);
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
			}
			;
			// try { Thread.sleep(action.getSleepTime()); } catch
			// (InterruptedException e) {}

		}

	}

	protected void executeAction(ServerAction action,
			HashMap<String, String> map) {
		try {
			ServerAction executingAction = null;
			for (ServerParameter parameter : action.getParams()) {

				if (map.containsKey(parameter.getName())) {
					executingAction = action.setParameter(parameter,
							map.get(parameter.getName()));
				} else {

					executingAction = action.setParameter(parameter,
							this.getInput());
				}
			}

			if (executingAction == null)
				executingAction = action;

			ClientResource cr = new ClientResource(serverUri
							+ executingAction.getUri());
			cr.setChallengeResponse(auth);

			Representation representation = null;

			System.out.println("METHOD: " + executingAction.getMethod());

			if (executingAction.getMethod() == ServerMethod.GET) {
				System.out.println("ESEGUO IL GET");
				representation = cr.get(MediaType.APPLICATION_JAVA_OBJECT);
			} else if (executingAction.getMethod() == ServerMethod.PUT) {
				System.out.println("ESEGUO IL PUT");
				Instances instances = new Instances("lalala",
						executingAction.getDataSpec(), 0);

				for (double value : this.getDataToSend()) {
					Instance instance = new DenseInstance(1,
							new double[] { value });
					instances.add(instance);
				}

				representation = cr.put(
						executingAction.getPutRepresentation(instances),
						MediaType.APPLICATION_JAVA_OBJECT);
			} else if (executingAction.getMethod() == ServerMethod.POST) {
				System.out.println("ESEGUO LA POST");
				representation = cr.post(
						executingAction.getPostRepresentation(),
						MediaType.APPLICATION_JAVA_OBJECT);
			}

			try {
				ObjectRepresentation<Serializable> objectRepresentation = new ObjectRepresentation<Serializable>(
						representation);

				Serializable object = objectRepresentation.getObject();
				System.out
						.println("RICEVUTO UN " + object.getClass().getName());
				if (object instanceof Long) {
					System.out.println("INSERISCO NELLA MAPPA: "
							+ executingAction.getOutputLabel());
					map.put(executingAction.getOutputLabel(), object.toString());
					System.out.println("LONG: " + object);
				} else if (object instanceof Report) {

					// Instances instances = (
					// ((Report)object).getData().get(0));
					// System.out.println("REC REPORT - SIZE ISTANZA: " +
					// instances.size());
					Report report = (Report) object;
					report.setValue(modality.getName());
				} else if (object instanceof List<?>) {
					List<Report> reportList = (List<Report>) object;

					for (Report report : reportList) {
						System.out
								.println("ADDING REPORT: " + report.getName());
					}
				}

			} catch (Exception e) {
				// TODO: rimuovere la stampa, dovrebbe lanciare un'eccezione
				// solo se il server restituisce VOID.
				e.printStackTrace();
			}
			// ModalitiesResource resource = cr.wrap(ModalitiesResource.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private double[] getDataToSend() {
		return new double[] {2031, 2012, 1987, 2015, 1985, 1885};
	}

	private String getInput() {
		return "stub";
	}

}
