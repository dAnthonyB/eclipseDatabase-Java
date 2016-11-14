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

import model.Account;
import model.AccountModel;
import model.ModelEvent;
import controller.AccountController;

@SuppressWarnings("serial")
public class EditInView extends JFrameView{
	private JTextField accountBalance = new JTextField();
	private JTextField input = new JTextField();
	private NumberFormat formatter = new DecimalFormat("#0.00");
	private String currencyType;
	private Account account;
	private Handler handler = new Handler();
	
	public EditInView(AccountModel model, AccountController controller, String currencyType, Account account) {
		super(model, controller);
		this.currencyType = currencyType;
		this.account = account;
		
		setTitle(account.getFirstName()+account.getLastName()+" ID: "+account.getID()+"; Operation in"+currencyType);
		//adds text field
		accountBalance.setText(formatter.format(AccountModel.convertFromUSD(account.getAmount(), currencyType))+ currencyType);
		accountBalance.setEditable(false);
		getContentPane().add(accountBalance, BorderLayout.NORTH);
	
		input.setText(formatter.format(0));
		getContentPane().add(input, BorderLayout.CENTER);
		
		//adds button Names into an array
		ArrayList<String> buttonNames = new ArrayList<String>();
		buttonNames.add("Deposit");
		buttonNames.add("Withdraw");
		buttonNames.add("Dismiss");
		
		//takes the name of the buttons, adds a listener for each and add it to the button panel
		JPanel buttonPanel = new JPanel();
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		for(int x = 0; x < buttonNames.size(); x++){
			buttons.add(new JButton(buttonNames.get(x)));
			buttons.get(x).addActionListener(handler);
			buttonPanel.add(buttons.get(x), null);
		}
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}
	public void modelChanged(ModelEvent event) {
		accountBalance.setText(formatter.format(AccountModel.convertFromUSD(account.getAmount(), currencyType))+ currencyType);
		input.setText(formatter.format(0));
	}
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Dismiss"))
				dispose();
			((AccountController) getController()).editBalance(e.getActionCommand(), Double.parseDouble(input.getText()), currencyType);
		}
	}
	public String getType(){
		return currencyType;
	}
}
