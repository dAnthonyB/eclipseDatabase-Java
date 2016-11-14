package view;

import controller.AccountController;
import model.AccountModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import model.ModelEvent;

@SuppressWarnings("serial")
public class AccountView extends JFrameView{
	private AccountModel model;
	private Handler handler = new Handler();
	public AccountView(AccountModel model, AccountController controller) {
		super(model, controller);
		this.model = model;
		BoxHandler bHandler = new BoxHandler();
		this.setTitle("Main Menu");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//adds drop down list
		JComboBox accountList = new JComboBox(model.getNames());
		accountList.addActionListener(bHandler);
		this.getContentPane().add(accountList, BorderLayout.CENTER);
		//adds buttonPanel
		JPanel buttonPanel = new JPanel();
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		//adds button Names into an array
		ArrayList<String> buttonNames = new ArrayList<String>();
		buttonNames.add("Edit in USD");
		buttonNames.add("Edit in Euros");
		buttonNames.add("Edit in Yuan");
		buttonNames.add("Create deposit agent");
		buttonNames.add("Create withdraw agent");
		buttonNames.add("Save");
		buttonNames.add("Exit");
		
		//takes the name of the buttons, adds a listener for 
		//each and add it to the button panel
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		for(int x = 0; x < buttonNames.size(); x++){
			buttons.add(new JButton(buttonNames.get(x)));
			buttons.get(x).addActionListener(handler);
			buttonPanel.add(buttons.get(x));
		}
		pack();
	}

	public void modelChanged(ModelEvent event) {
		
	}
	// Inner classes for Event Handling
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			((AccountController) getController()).mainMenuOperation(e.getActionCommand());
		}
	}
	class BoxHandler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			JComboBox cBox = (JComboBox)e.getSource();
			model.setCurrentID(cBox.getSelectedIndex());
		}
	}
	public static void main(String[] args) {
		if(args.length <= 1){
			try{
				new AccountController("testfileHW5.txt");
			}catch(Exception i){
				System.out.println("Making new model");
				new AccountController();
			}
		}
		if(args.length == 2){
			new AccountController();
			try{
				new AccountController(args[1]);
			}catch(Exception i){
				System.out.println("Making new model");
				new AccountController();
			}
		}
		
	}
}
