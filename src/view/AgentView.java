package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.Account;
import model.AccountModel;
import model.ModelEvent;
import controller.AccountController;

enum States {Running, Blocked, Stopped };
@SuppressWarnings("serial")
public class AgentView extends JFrameView implements Runnable{
	private AccountController controller;
	private ArrayList<JTextField> textFields; 
	private ArrayList<String> textFieldsNames; 
	private Integer agentID;
	private double amt;
	private int operationTime;
	private NumberFormat formatter = new DecimalFormat("#0.00");
	private String agentType;
	private Account account;
	private double totalAmount = 0;
	private int totalOperations = 0;
	public States currentState = States.Running; 
	private String[] states = {"Running", "Blocked", "Stopped"};
	private Handler handler = new Handler();
	private sleeper s;
	private Thread t0;
	
	public AgentView(AccountModel model, AccountController c, 
			int agentID, double amt, double ops, String agentType, Account account ) {
		
		super(model, c);
		this.agentType = agentType;
		this.account = account;
		this.agentID = agentID;
		this.amt = amt;
		this.controller = c;
		Double temp = (1000/ops);
		operationTime = temp.intValue();
		s = new sleeper(operationTime);
		t0 = new Thread(s);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		textFields = new ArrayList<JTextField>();
		textFieldsNames = new ArrayList<String>();
		this.setTitle(this.agentType+" agent "+ this.agentID+
				" for account "+this.account.getID());

		textFieldsNames.add("Amount in $: " +formatter.format(this.amt));
		textFieldsNames.add("State: "+getCurrentState());
		textFieldsNames.add("Operations per Second: "+formatter.format(ops));
		textFieldsNames.add("Amount in $ transferred: "+totalAmount);
		textFieldsNames.add("Operations completed: "+totalOperations);
		
		JPanel t = new JPanel();
		for(int i = 0; i < textFieldsNames.size(); i++){
			textFields.add(new JTextField(textFieldsNames.get(i)));
			textFields.get(i).setEditable(false);
			t.add(textFields.get(i));
		}
		
		//adds Start and stop buttons
		JPanel buttonPanel = new JPanel();
		
		JButton startAgent = new JButton("Stop agent");
		startAgent.addActionListener(handler);
		buttonPanel.add(startAgent);
		JButton dismissAgent = new JButton("Dismiss agent");
		dismissAgent.addActionListener(handler);
		buttonPanel.add(dismissAgent);
		
		getContentPane().add(t, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		pack();
			
		Thread t1 = new Thread((Runnable) this);
		t1.start();
		
	}
	public void run() {
		
	  while(currentState == States.Running){
		  t0 = new Thread(s);
		  t0.start();
		  if(!controller.agentOptions(agentType, amt, account)){
				currentState = States.Blocked;
				textFields.get(1).setText("State: "+getCurrentState());
			}
		  try {
			t0.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}	
	public void modelChanged(ModelEvent event) {
		System.out.println(totalOperations);
		totalAmount+=amt;
		totalOperations++;
		textFields.get(1).setText("State: "+getCurrentState());
		textFields.get(3).setText("Amount in $ transferred: "+totalAmount);
		textFields.get(4).setText("Operations completed: "+totalOperations);
	}
	
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Stop agent")){
				currentState = States.Stopped;
				textFields.get(1).setText("State: "+getCurrentState());
			}else if (e.getActionCommand().equals("Dismiss agent")){
				if(currentState == States.Stopped){
					dispose();
				}else{
					
				}
			}
		}
	}
	class sleeper implements Runnable {
		int operationTime;
		
		public sleeper(int oT){
			this.operationTime = oT;
		}
	    public void run() {
	    	try {
				Thread.sleep(operationTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	private String getCurrentState(){
		switch (currentState) {
        case Running:
        	return states[0];
                
        case Blocked:
        	return states[1];
                     
        case Stopped:
            return states[2];
                    
        default:
            return "Invalid state";
		}
	}
}
