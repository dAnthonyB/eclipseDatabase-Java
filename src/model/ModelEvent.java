package model;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ModelEvent extends ActionEvent{
	private double amt;
	public ModelEvent(Object obj, int id, String message, double amount){
		super(obj, id, message);
		this.amt = amount;
	}
	public double getAmt(){return amt;}
}
