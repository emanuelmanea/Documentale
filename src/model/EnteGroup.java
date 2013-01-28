package model;

import java.util.ArrayList;
import java.util.List;



public class EnteGroup extends AbstractModelObject {
	
	List<Ente> members = new ArrayList<Ente>();
	
	public List<Ente> getMembers() {
		return members;
	}

	public void setMembers(List<Ente> list) {
		this.members = list;
	}
	
	public void addEnte(Ente newValue) {
		List<Ente> oldValue = members;
		members = new ArrayList<Ente>(members);
		members.add(newValue);
		firePropertyChange("members", oldValue, newValue);
	}

	public void removeEnte(Ente newValue) {
		List<Ente> oldValue = members;
		members = new ArrayList<Ente>(members);
		members.remove(newValue);
		firePropertyChange("members", oldValue, newValue);
	}

	public List<Ente> getGroups() {
		return members;
	}
}
