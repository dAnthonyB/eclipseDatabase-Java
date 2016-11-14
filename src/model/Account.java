package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Account implements Serializable{
	int ID;
	String firstName;
	String lastName;
	double amount;
	public Account(){
		
	}
	public Account(int ID, String firstName, String lastName, double amount){
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.amount = amount;
	}
	public int getID(){
		return ID;
	}
	public String getFirstName(){
		return firstName;
	}
	public String getLastName(){
		return lastName;
	}
	public void setAmount(double amount){
		this.amount = amount;
	}
	public double getAmount(){
		return amount;
	}
	public String toString(){
		return "ID: "+ID+" firstName: "+firstName+" lastName: "+lastName+" amount: "+amount;
	}
}
