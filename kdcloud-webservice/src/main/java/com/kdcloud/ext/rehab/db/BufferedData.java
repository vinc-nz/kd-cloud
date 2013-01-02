//fabrix
package com.kdcloud.ext.rehab.db;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Serialized;

public class BufferedData {

	@Id
	Long id;
	Key<RehabUser> rehabuser;
	int elbowknee;
	Date insertdate;
	int length;
	@Serialized
	List<Integer[]> raw;
	@Serialized
	List<Integer[]> angles;



	public Key<RehabUser> getRehabUser() {
		return rehabuser;
	}

	public void setRehabUser(Key<RehabUser> rehabUser) {
		this.rehabuser = rehabUser;
	}

	public int getElbowknee() {
		return elbowknee;
	}

	public void setElbowknee(int elbowknee) {
		this.elbowknee = elbowknee;
	}

	public Date getInsertDate() {
		return insertdate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertdate = insertDate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public List<Integer[]> getRaw() {
		return raw;
	}

	public void setRaw(List<Integer[]> raw) {
		this.raw = raw;
	}

	public List<Integer[]> getAngles() {
		return angles;
	}

	public void setAngles(List<Integer[]> angles) {
		this.angles = angles;
	}





	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((angles == null) ? 0 : angles.hashCode());
		result = prime * result + ((insertdate == null) ? 0 : insertdate.hashCode());
		result = prime * result + elbowknee;
		result = prime * result + length;
		result = prime * result
				+ ((rehabuser == null) ? 0 : rehabuser.hashCode());
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
		BufferedData other = (BufferedData) obj;
		if (angles == null) {
			if (other.angles != null)
				return false;
		} else if (!angles.equals(other.angles))
			return false;
		if (insertdate == null) {
			if (other.insertdate != null)
				return false;
		} else if (!insertdate.equals(other.insertdate))
			return false;
		if (elbowknee != other.elbowknee)
			return false;
		if (length != other.length)
			return false;
		if (rehabuser == null) {
			if (other.rehabuser != null)
				return false;
		} else if (!rehabuser.equals(other.rehabuser))
			return false;
		if (raw == null) {
			if (other.raw != null)
				return false;
		} else if (!raw.equals(other.raw))
			return false;
		return true;
	}

	public Document toXMLDocument(Document d) {
		Element root = d.createElement("downloadbuffereddataOutput");
		d.appendChild(root);
		root.setAttribute("elbowknee", "" + elbowknee);
		root.setAttribute("date", "" + insertdate.toGMTString());
		root.setAttribute("length", "" + length);
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
		for (Integer[] angle_sample : angles) {
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
