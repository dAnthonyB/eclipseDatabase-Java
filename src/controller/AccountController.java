package controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Account;
import model.AccountModel;
import view.AccountView;
import view.JFrameView;

public class AccountController extends AbstractController {
	private AccountModel model;
	
	public AccountController(){
		model = new AccountModel();
		setModel(model);
		setView(new AccountView((AccountModel)getModel(), this));
		((JFrameView)getView()).setVisible(true);
	}
	public AccountController(String fileName){
		model = loadDataFromFile(fileName);
		setModel(model);
		setView(new AccountView((AccountModel)getModel(), this));
		((JFrameView)getView()).setVisible(true);
	}
	public void mainMenuOperation(String option){
		if(option.equals("Edit in USD")){
			getThisModel().editWindow(this, "USD");
		}else if(option.equals("Edit in Euros")){
			getThisModel().editWindow(this, "EUR");
		}else if(option.equals("Edit in Yuan")){
			getThisModel().editWindow(this, "YUAN");
		}else if(option.equals("Create withdraw agent")){
			getThisModel().startAgentWindow(this, "withdraw");
		}else if(option.equals("Create deposit agent")){
			getThisModel().startAgentWindow(this, "deposit");
		}else if(option.equals("Save")){
			getThisModel().saveDataToFile("testfileHW5.txt");
		}else if(option.equals("Exit")){
			getThisModel().saveDataToFile("testfileHW5.txt");
			System.exit(0);
		}else{
			JOptionPane.showMessageDialog(null,"not a valid option");
		}
	}
	public void editBalance(String option, double amt, String currencyType){
		if(option.equals("Deposit")){
			getThisModel().deposit(AccountModel.convertToUSD(amt, currencyType));
		}else if(option.equals("Withdraw")){
			getThisModel().withdraw(AccountModel.convertToUSD(amt, currencyType));
		}else{
			System.out.println("unknown operation option");
		}
	}
	public boolean startAgent(int id, double amt, double ops, String type, Account account){
		if(getThisModel().agentIDUnique(id)){
			try{
				if(type.equals("withdraw") && amt > account.getAmount()){
					System.out.println("not enough money");
					return false;
				}else{
					getThisModel().startAgent(this, id, amt, ops, type, account);
					try{
						getThisModel().addAgentID(id);
					}catch(Exception e){
						System.out.println("adding agentID failed"+e);
					}
					return true;
				}
			}catch(NullPointerException e){
				System.out.println("controller start Agent");
			}
		}
		return false;
	}
	public boolean agentOptions(String agentType, double amt, Account account){
		if(agentType.equals("deposit") && amt > 0){
			getThisModel().deposit(amt);
			return true;
		}else if (agentType.equals("withdraw")&& amt > 0 && account.getAmount() >= amt){
			getThisModel().withdraw(amt);
			return true;
		}else{
			return false;
		}
	}
	private AccountModel getThisModel(){
		return model;
	}
	public AccountModel loadDataFromFile(String fileName){
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
	         fis = new FileInputStream(fileName);
	         ois = new ObjectInputStream(fis);
	         AccountModel model = (AccountModel) ois.readObject();
	         ois.close();
			 fis.close();
			 System.out.println("Loaded " + fileName);
			 model.agentIDs = new ArrayList<Integer>();
			 return model;
	      }catch(IOException i){
	    	  System.out.println("Error: " + i);
	      }catch(ClassNotFoundException c) {
	    	  System.out.println("Error: " + c);
	      }
	      return null;
	}
}
