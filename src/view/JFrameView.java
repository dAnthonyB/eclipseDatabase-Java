package view;

import javax.swing.*;

import model.Model;
import model.AbstractModel;
import model.ModelListener;
import controller.Controller;

@SuppressWarnings("serial")
abstract public class JFrameView extends JFrame implements View, ModelListener {
	private Model model;
	private Controller controller;
	
	public JFrameView (Model model, Controller controller){
		setModel(model);
		setController(controller);
		setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public void registerWithModel(){
		((AbstractModel)model).addModelListener(this);
	}
	public Controller getController(){return controller;}
	
	public void setController(Controller controller){this.controller = controller;}
	
	public Model getModel(){return model;}
	
	public void setModel(Model model) {
		this.model = model;
		registerWithModel();
	}
}
