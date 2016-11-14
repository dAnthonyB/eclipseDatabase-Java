package model;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.AgentView;
import view.EditInView;
import view.startAgentView;

import controller.AccountController;

@SuppressWarnings("serial")
public class AccountModel extends AbstractModel implements Serializable {
	private int currentID;
	private double currentInput;
	private ArrayList<Account> accounts = new ArrayList<Account>();
	private String[] accountNames;
	public ArrayList<Integer> agentIDs = new ArrayList<Integer>();
	private final static double EURO = 0.88;
	private final static double YUAN = 6.47;
	
	public AccountModel(){
		accountNames = new String[10];
		for(int i = 0; i<10; i++){
			addAccount("FirstName"+i, "LastName"+i, i*10);
			accountNames[i] = "FirstName"+i+" LastName"+i;
		}
	}
	public double getCurrentInput(){
		return currentInput;
	}
	public Integer numAgents(){
		if(agentIDs == null)
			return 0;
		
		return agentIDs.size();
	}
	public void setCurrentInput(double amt){
		currentInput = amt;
	}
	public void setCurrentID(int i){
		currentID = i;
	}
	public int getCurrentID(){
		return currentID;
	}
	public String[] getNames(){
		return accountNames;
	}
	public Account getAccount(String name){
		for(int i = 0; i< accounts.size(); i++){
			if(getName(i) == name)
				return accounts.get(i);
		}
		return null;
	}
	public Account getAccount(int ID){
		return accounts.get(ID);
	}
	public String getName(int ID){
		return (accounts.get(ID).getFirstName()+" "+accounts.get(ID).getLastName());
	}
	public void addAccount(Account a){
		accounts.add(a);
	}
	public void addAccount(String firstName, String lastName, double amount){
		int numIDs;
		if(accounts == null)
			numIDs = 0;
		numIDs = accounts.size();
		accounts.add(new Account(numIDs, firstName, lastName, amount));
	}
	//functionality for account system
	public static double convertFromUSD(double amt, String convertTo){
		if(convertTo.equals("EUR")){
			amt *= EURO;
		}else if(convertTo.equals("YUAN")){
			amt *= YUAN;
		}
		return amt;
	}
	public static double convertToUSD(double amt, String oldCurrency){
		if(oldCurrency.equals("EUR")){
			amt /= EURO;
		}else if(oldCurrency.equals("YUAN")){
			amt /= YUAN;
		}
		return amt;
	}
	public void startAgentWindow(AccountController controller,String type){
		new startAgentView(this, controller, type, accounts.get(currentID));
	}
	public void startAgent(AccountController controller,  
			int agentID, double amt, double ops, String agentType, Account account){
		new AgentView(this, controller, agentID, amt, ops, agentType, account);
	}
	public void editWindow(AccountController controller, String type){
		new EditInView(this, controller, type, accounts.get(currentID));
	}
	public void deposit(double amt){
		if(amt<=0){
			System.out.println("can't deposit 0 or less currency");
		}else{
			double balance = accounts.get(currentID).getAmount();
			accounts.get(currentID).setAmount(balance+amt);
			ModelEvent me = new ModelEvent(this, 1, "", amt);
			notifyChanged(me);
		}
	}
	public void withdraw(double amt){
		if(amt<=0){
			JOptionPane.showMessageDialog(null,"can't withdraw 0 or less currency");
		}else if(amt >= accounts.get(currentID).getAmount()){
			JOptionPane.showMessageDialog(null,"not enough currency in bank");
		}else{
			double balance = accounts.get(currentID).getAmount();
			accounts.get(currentID).setAmount(balance-amt);
			ModelEvent me = new ModelEvent(this, 1, "", amt);
			notifyChanged(me);
		}
	}
	
	public boolean agentIDUnique(int id){
		if (agentIDs == null){
			return true;
		}
		else if (agentIDs.contains(id)){
			JOptionPane.showMessageDialog(null,"Not a unique ID");
			return false;
		}
		else{
			return true;
		}
		
	}
	public void addAgentID(int id){
		try{
			agentIDs.add(id);
		}catch(NullPointerException e){
			JOptionPane.showMessageDialog(null,"agentIDs failed to add");
		}
	}
	public void saveDataToFile (String fileName){
		try{
		   FileOutputStream fileOut = new FileOutputStream(fileName);
		   ObjectOutputStream out = new ObjectOutputStream(fileOut);
		   out.writeObject(this);
		   out.close();
		   fileOut.close();
		   System.out.println(fileName + " has been saved");
		}catch(IOException i){
		   System.out.println("Error: " + i);
		}
	}
}
