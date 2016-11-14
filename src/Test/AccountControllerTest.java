package Test;


import model.AccountModel;
import static org.junit.Assert.*;
import org.junit.Test;
import controller.AccountController;

public class AccountControllerTest {

	@Test
	public void testWithdraw() {
		AccountController accCont = new AccountController();
		AccountModel accModel = new AccountModel();
		accCont.setModel(accModel);
		
		accModel.deposit(1000.00);
		accModel.withdraw(100.00);
		
		double bal = accModel.getAccount(0).getAmount();
		assertEquals("Dep. $1000.00, Withdraw $100.00, remaining $900.00", 900.00, bal);
		
	}
	
	@Test
	public void testWithdrawError() {
		AccountController accCont = new AccountController();
		AccountModel accModel = new AccountModel();
		
		accCont.setModel(accModel);
		
		accModel.deposit(100.00);
		accModel.withdraw(101.00);
		
		double bal = accModel.getAccount(0).getAmount();
		assertEquals("Dep. $100.00, Withdraw $101.00, remaining $100.00", 100.00, bal);
	}
}
