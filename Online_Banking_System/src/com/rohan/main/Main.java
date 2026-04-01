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
					
				case 2: /* AccountHolders ah1 = new AccountHolders(); */
					BankingServices bs1 = new BankingServices();
					System.out.println("Enter Account No. :");
					long acNo = sc.nextLong();
					boolean flag = bs1.accExist(acNo);
					
					if(flag)
					{
						System.out.println("Select the option :");
						System.out.println("a. Deposit Money \nb. Withdraw Money");
						char c =sc.next().charAt(0);
						if(c=='a')
						{
							System.out.println("Enter amount to Deposite :");
							int bal = sc.nextInt();
							bs1.depositMoney(acNo, bal);							
							
						}
						else if(c=='b')
						{
							System.out.println("Enter amount to Withdraw :");
							int amount = sc.nextInt();
							bs1.withdrawMoney(acNo, amount);
						}
						else
						{
							System.out.println("Invalid Input.");
						}
					}
					else
					{
						System.out.println("Account Not Found.");
					}
					
					break;
					
			case 3: BankingServices bs2 = new BankingServices();
					System.out.println("Enter Your Account Number :");
					long senderAccount = sc.nextLong();
					System.out.println("Enter Account Number Money Send To :");
					long receiverAccount = sc.nextLong();
					boolean flag1 = bs2.accExist(senderAccount);
					boolean flag2 = bs2.accExist(receiverAccount);
					if(flag1&&flag2)
					{
						System.out.println("Enter Amount :");
						int amt = sc.nextInt();
						bs2.transferMoney(amt ,senderAccount ,receiverAccount);
					}
					else
					{
						System.out.println("Account Not Found.");
					}
				
					break;
			case 4:	System.out.println("Enter Account Number :");	
					long accNum = sc.nextLong();
					BankingServices bs3 = new BankingServices();
					boolean exists = bs3.accExist(accNum);
					if(exists)
					{
						bs3.viewTransaction(accNum);
					}
					else
					{
						System.out.println("Account Not Found.");
					}
					
					break;
					
			case 5:	System.exit(0);
					break;
					
			default : System.out.println("Wrong Input Try again Later.");	
			}
			
			
		}
		
	}
}
