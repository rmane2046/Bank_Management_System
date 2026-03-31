package com.rohan.main;

import java.util.Scanner;

import com.rohan.accounts.AccountHolders;
import com.rohan.bankservice.BankingServices;

public class Main 
{
	public static void main(String[] args) throws Exception 
	{		
		BankingServices bs = new BankingServices();
		
		Scanner sc = new Scanner(System.in);
		while(true)
		{
			System.out.println("================================================");
			System.out.println("----------Select The Following Action-----------");
			System.out.println("1. Create Account");
			System.out.println("2. Deposit / Withdraw Money");
			System.out.println("3. Transfer Money");
			System.out.println("4. View Transaction History");
			System.out.println("5. Exit");
			System.out.println("================================================");
			
			int input = sc.nextInt();
			sc.nextLine();
			switch(input)
			{
			case 1:	AccountHolders ah = new AccountHolders();
					System.out.println("Enter Full Name :");
					ah.setAccName(sc.nextLine());
					System.out.println("Enter Email :");
					ah.setEmail(sc.nextLine());
					System.out.println("Enter Phone No. :");
					ah.setPhone(sc.nextLong());
					sc.nextLine();
					System.out.println("Enter Address :");
					ah.setAddress(sc.nextLine());
					System.out.println("Enter Account Type (Saving/Current) :");
					ah.setAccType(sc.next());
					System.out.println("Enter Inital Balance :");
					ah.setBalance(sc.nextInt());
					bs.acoountCreate(ah);
					break;
					
			case 2: AccountHolders ah1 = new AccountHolders();
					BankingServices bs1 = new BankingServices();
					System.out.println("Enter Account No. :");
					long acNo = sc.nextLong();
					boolean flag = new BankingServices().accExist(acNo);
					
					if(flag)
					{
						System.out.println("Select the option :");
						System.out.println("a. Deposit Money \n b. Withdraw Money");
						String c =sc.nextLine();
						if(c.equalsIgnoreCase("a"))
						{
							System.out.println("Enter amount to Deposite :");
							int bal = sc.nextInt();
							ah1.setInitialBalance(bal);
							
							
						}
						else if(c.equalsIgnoreCase("b"))
						{
							
						}
						else
						{
							System.out.println("Invalid Input.");
						}
					}
					
					
					
			
					break;
			case 3:break;
			case 4:	break;
			case 5:	System.exit(0);
					break;
			default : System.out.println("Wrong Input Try again Later.");	
			}
			
		}
	}
}
