package com.kdcloud.server.persistence.gae;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.kdcloud.server.entity.DataTable;
import com.kdcloud.server.persistence.InstancesSaver;

public class GAESaver implements InstancesSaver {
	
	
	@Override
	public void save(Instances instances, DataTable table) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key tableKey = KeyFactory.stringToKey(table.getEncodedKey());
		List<Entity> entities = new ArrayList<Entity>(instances.size());
		for (Instance inst : instances) {
			Entity e = new Entity(instances.relationName(), tableKey);
			for (int i = 0; i < inst.numAttributes(); i++) {
				e.setProperty(inst.attribute(i).name(), inst.value(i));
			}
			entities.add(e);
		}
		datastore.put(entities);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Instances load(DataTable table) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key tableKey = KeyFactory.stringToKey(table.getEncodedKey());
		PreparedQuery q = datastore.prepare(new Query(tableKey));
		Iterator<Entity> it = q.asIterator();
		it.next(); // table
		if (!it.hasNext())
			return null;
		Entity e = it.next();
		Instances instances = new Instances(e.getKind(), loadAttributes(e), q.countEntities());
		do {
			loadInstance(instances, e);
			e = (it.hasNext() ? it.next() : null);
		} while (e != null);
		return instances;
	}
	
	public ArrayList<Attribute> loadAttributes(Entity e) {
		ArrayList<Attribute> attrs = new ArrayList<Attribute>(e.getProperties().size());
		for (String property : e.getProperties().keySet()) {
			if (e.getProperty(property) instanceof Double) {
				attrs.add(new Attribute(property));
			}
		}
		return attrs;
	}
	
	public void loadInstance(Instances instances, Entity e) {
		double[] row = new double[instances.numAttributes()];
		for (int i = 0; i < row.length; i++) {
			String property = instances.attribute(i).name();
			row[i] = (Double) e.getProperty(property);
		}
		instances.add(new DenseInstance(1, row));
	}

}
