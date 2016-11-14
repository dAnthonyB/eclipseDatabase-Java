package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Account;
import model.AccountModel;
import model.ModelEvent;
import controller.AccountController;

@SuppressWarnings("serial")
public class startAgentView extends JFrameView{
	private JTextField agentIDText = new JTextField();
	private JTextField amountInDollars = new JTextField();
	private JTextField opsPerSecondText = new JTextField();
	private JTextField agentIDString = new JTextField();
	private JTextField opsPerSecondString = new JTextField();
	private JTextField amountString = new JTextField();
	private Integer agentID;
	private double amt;
	private double opsPerSecond;
	private NumberFormat formatter = new DecimalFormat("#0.00");
	private String agentType;
	private Account account;
	private Handler handler = new Handler();
	
	
	public startAgentView(AccountModel model, AccountController controller, 
			String agentType, Account account) {
		super(model, controller);
		this.agentType = agentType;
		this.account = account;
		this.setTitle("Start "+agentType+" agent for account: "+account.getID());
		
		//adds text fields
		JPanel t = new JPanel();
		AccountModel am = (AccountModel)getModel();
		agentIDString.setText("Agent ID");
		agentIDString.setEditable(false);
		t.add(agentIDString);
		
		amountString.setText(agentType+ " in $");
		amountString.setEditable(false);
		t.add(amountString);
		
		opsPerSecondString.setText("Operations/s");
		opsPerSecondString.setEditable(false);
		t.add(opsPerSecondString);
		
		JPanel inputPanel = new JPanel();
		agentIDText.setText(am.numAgents().toString());
		inputPanel.add(agentIDText);
		amountInDollars.setText(formatter.format(0.0));
		inputPanel.add(amountInDollars);
		opsPerSecondText.setText("0.0");
		inputPanel.add(opsPerSecondText);
		
		//adds Start agent button
		
		JButton startAgent = new JButton("Start agent");
		startAgent.addActionListener(handler);
		this.getContentPane().add(t, BorderLayout.NORTH);
		this.getContentPane().add(inputPanel, BorderLayout.CENTER);
		this.getContentPane().add(startAgent, BorderLayout.SOUTH);
		
		pack();
	}
	public void modelChanged(ModelEvent event) {
		
	}
	
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			try{
				agentID = Integer.parseInt(agentIDText.getText());
			}catch(NumberFormatException i){
				JOptionPane.showMessageDialog(null,"agent ID not a integer");
				return;
			}
			try{
				amt = Double.parseDouble(amountInDollars.getText());
			}catch(NumberFormatException i){
				JOptionPane.showMessageDialog(null,"amount not a double");
				return;
			}
			try{
				opsPerSecond = Double.parseDouble(opsPerSecondText.getText());
			}catch(NumberFormatException i){
				JOptionPane.showMessageDialog(null,"Op/s not a double");
				return;
			}
			if(!((AccountController) getController()).startAgent(agentID, amt, opsPerSecond, agentType, account)){
				JOptionPane.showMessageDialog(null,"Invalid input");
				return;
			}
			dispose();
		}
	}
	public String getType(){
		return agentType;
	}
}
