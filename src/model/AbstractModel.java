package model;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractModel implements Model {
	private ArrayList<ModelListener> listeners = new ArrayList<ModelListener>(5);
	
	@SuppressWarnings("unchecked")
	public void notifyChanged(ModelEvent event){
		ArrayList<ModelListener> list = (ArrayList<ModelListener>) listeners.clone();
		Iterator<ModelListener> it = list.iterator();
		while(it.hasNext()){
			ModelListener ml = it.next();
			ml.modelChanged(event);
		}
	}
	public void addModelListener(ModelListener l){
		listeners.add(l);
	}
	public void removeModelListener(ModelListener l){
		listeners.remove(l);
	}
}
