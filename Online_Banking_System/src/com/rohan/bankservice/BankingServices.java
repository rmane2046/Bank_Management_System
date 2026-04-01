package com.rohan.bankservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.UUID;

import com.rohan.accounts.AccountHolders;
import com.rohan.database.DatabaseConnection;

/*	CREATE TABLE bank (sr_no NUMBER(10) PRIMARY KEY, holder_name VARCHAR2(20) NOT NULL,
		account_number NUMBER (10) UNIQUE NOT NULL, email_id VARCHAR2(20), 
		phone_number NUMBER(10) NOT NULL, address VARCHAR2(100) NOT NULL, 
		account_type VARCHAR2(10) NOT NULL, balance NUMBER(7,2) NOT NULL);
*/
/*
 * 	CREATE TABLE transactions (
    txn_id VARCHAR2(20) PRIMARY KEY, user_account NUMBER(20) NOT NULL,      
    txn_type VARCHAR2(20) NOT NULL,amount NUMBER(15,2) NOT NULL,          
    other_account NUMBER(20),      
    txn_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

 */
public class BankingServices 
{
	DatabaseConnection dc = new DatabaseConnection();
	String txId ="TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
	public void acoountCreate(AccountHolders ah) throws Exception
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			con = dc.database();
			con.setAutoCommit(false);
			int rowCount = 0;
			String name = ah.getAccName();
			int accNumber = (int)(Math.random() * 1000000000);
			String mail = ah.getEmail();
			Long number = ah.getPhone();
			String address = ah.getAddress();
			String accType = ah.getAccType();
			int bal = ah.getBalance();
			String Query = """
							INSERT INTO bank(sr_no,holder_name, account_number,email_id, phone_number
							, address, account_type, balance) VALUES 
							(sr_no.NEXTVAL,?,?,?,?,?,?,?)
					""";
			pstmt = con.prepareStatement(Query);
			pstmt.setString(1, name);
			pstmt.setInt(2, accNumber);
			pstmt.setString(3, mail);
			pstmt.setLong(4, number);
			pstmt.setString(5, address);
			pstmt.setString(6, accType);
			pstmt.setInt(7, bal);
			rowCount += pstmt.executeUpdate();
			con.commit();
			if(rowCount>0)
			{
				System.out.println("Account Created Successfully, Check Your Details : ");
				pstmt.close();
				pstmt = con.prepareStatement("SELECT * FROM bank WHERE account_number = ?");
				pstmt.setInt(1, accNumber);
				ResultSet rs = pstmt.executeQuery();				
				ResultSetMetaData rsmd = rs.getMetaData();				
				for(int i=1;i<=rsmd.getColumnCount();i++)
				{
					System.out.print(rsmd.getColumnName(i)+"\t");
				}
				System.out.println();
				while(rs.next())
				{
					for(int i=1;i<=rsmd.getColumnCount();i++)
					{
						System.out.print(rs.getString(i)+"\t");
					}
					System.out.println();
				}
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			con.rollback();
		}
		finally {
			try
			{
				if(pstmt!=null)
				{
					pstmt.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				if(con!=null)
				{
					con.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public boolean accExist(long num)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			 con = new DatabaseConnection().database();
			 String query = "SELECT * FROM bank WHERE account_number = ?";
			 pstmt= con.prepareStatement(query);
			 pstmt.setLong(1, num);
			 rs = pstmt.executeQuery();			 
			 return rs.next();			 			 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally 
		{
			try {
				if(rs!= null)
				{
					rs.close();
				}
				
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(pstmt!= null)
				{
					pstmt.close();
				}
				
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(con!=null)
				{
					con.close();
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		return false;
	}
	public void depositMoney(long acNum, int bal) throws Exception
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			con = new DatabaseConnection().database();
			con.setAutoCommit(false);			
			int rowCount = 0;
			String query ="UPDATE bank SET balance = balance + ? WHERE account_number = ? ";
			String query2 ="INSERT INTO transactions(txn_id, user_account, txn_type, amount, txn_datetime) VALUES (?,?,?,?,?)";			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, bal);
			pstmt.setLong(2, acNum);
			rowCount += pstmt.executeUpdate();
			if(rowCount>0)
			{
				pstmt.close();
				pstmt = con.prepareStatement(query2);
				pstmt.setString(1, txId);
				pstmt.setLong(2, acNum);
				pstmt.setString(3, "Deposit");
				pstmt.setInt(4, bal);
				pstmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
				pstmt.executeUpdate();
				con.commit();
				System.out.println("Deposite Successfully.");
			}
			else 
			{
				System.out.println("Something is Wrong Try again Later.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		}
		finally {
			if(pstmt!=null)
			{
				try {
					pstmt.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(con!=null)
			{
				try {
					con.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
		}
		
		
		
		
	}
	public void withdrawMoney(long num, int money) throws SQLException
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = new DatabaseConnection().database();
			con.setAutoCommit(false);
			String query1="SELECT balance FROM bank WHERE account_number = ?";
			String transaction ="INSERT INTO transactions(txn_id, user_account, txn_type, amount, txn_datetime) VALUES (?,?,?,?,?)";	
			pstmt = con.prepareStatement(query1);
			pstmt.setLong(1,num);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				int acBal = rs.getInt("balance");
				int rowCount = 0;
				if(money<=acBal)
				{
					String query2 = """
								UPDATE bank SET balance = balance-? WHERE account_number = ?
							""";
					pstmt.close();
					pstmt = con.prepareStatement(query2);
					pstmt.setInt(1, money);
					pstmt.setLong(2, num);
					rowCount += pstmt.executeUpdate();
					if(rowCount>0)
					{
						pstmt.close();
						pstmt = con.prepareStatement(transaction);
						pstmt.setString(1, txId);
						pstmt.setLong(2, num);
						pstmt.setString(3, "Withdraw");
						pstmt.setInt(4, money);
						pstmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
						pstmt.executeUpdate();
						System.out.println("Money Withdraw Successfully.");
						if(con!=null)
						{
							con.commit();
						}
							
					}
					else {
						System.out.println("Money Not Withdraw Successfully Try again.");
					}
				}
				else
				{
					System.out.println("Insufficient Balance.");
					if (con != null) con.rollback();
				}
			}
			else {
				System.out.println("Account Not Found.");
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			if (con != null) con.rollback();
		}
		finally {
			if(rs!=null)
			{
				try {
					rs.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(pstmt!=null)
			{
				try {
					pstmt.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(con!=null)
			{
				try {
					con.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	/*
	 * public int transferAccCheck(long accSender, long accReceiver) throws
	 * ClassNotFoundException, SQLException { Connection con = null;
	 * PreparedStatement pstmt = null; ResultSet rs = null; try { con = new
	 * DatabaseConnection().database(); String query =
	 * "SELECT * FROM bank WHERE account_number = ? OR account_number = ?"; pstmt =
	 * con.prepareStatement(query); pstmt.setLong(1, accSender); pstmt.setLong(2,
	 * accReceiver); rs = pstmt.executeQuery(); int count = 0; while(rs.next()) {
	 * ++count; } if(count==2) { return 2; }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } finally { if(rs != null)
	 * rs.close(); if(pstmt != null) pstmt.close(); if(con != null) con.close(); }
	 * return 0; }
	 */
	public void transferMoney(int money, long sender, long receiver) throws SQLException
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = new DatabaseConnection().database();
			con.setAutoCommit(false);
			String query = "UPDATE bank SET balance = balance+? WHERE account_number=?";
			String query2 = "SELECT balance FROM bank WHERE account_number=?";
			String query3 ="INSERT INTO transactions(txn_id, user_account, txn_type, amount, other_account, txn_datetime) VALUES (?,?,?,?,?,?)";	
			pstmt = con.prepareStatement(query2);
			pstmt.setLong(1, sender);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				int balance = rs.getInt("balance");
				if(balance>=money)
				{
					pstmt.close();
					pstmt = con.prepareStatement(query);
					pstmt.setInt(1, -money);
					pstmt.setLong(2, sender);
					pstmt.addBatch();
					
					pstmt.setInt(1, money);
					pstmt.setLong(2, receiver);
					pstmt.addBatch();					
					
					pstmt.executeBatch();
					String txtID = txId;
					
					pstmt.close();
					pstmt = con.prepareStatement(query3);
					pstmt.setString(1, txtID);
					pstmt.setLong(2, sender);
					pstmt.setString(3, "Send");
					pstmt.setInt(4, money);
					pstmt.setLong(5, receiver);
					pstmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
					pstmt.addBatch();
					
					pstmt.setString(1, txtID);
					pstmt.setLong(2, receiver);
					pstmt.setString(3, "Received");
					pstmt.setInt(4, money);
					pstmt.setLong(5, sender);
					pstmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
					pstmt.addBatch();
					
					pstmt.executeBatch();					
					if(con!=null) con.commit();
					System.out.println("Money send Successfully.");
				}
				else
				{
					System.out.println("Insufficient Balance.");
					if(con!=null) con.rollback();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if(con!=null) con.rollback();
		}
	}
	public void viewTransaction(long account)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = dc.database();
			String query = "SELECT * FROM transactions WHERE user_account = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setLong(1, account);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++)
			{
				System.out.print(rsmd.getColumnName(i)+"\t  ");
			}
			System.out.println();
			while(rs.next())
			{
				for(int i=1;i<=rsmd.getColumnCount();i++)
				{
					System.out.print(rs.getString(i)+"\t  ");
				}
				System.out.println();
			}
			System.out.println();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally 
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(pstmt!=null)
			{
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(con!=null)
			{
				try {
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}
