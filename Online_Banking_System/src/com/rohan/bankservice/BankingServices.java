package com.rohan.bankservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.rohan.accounts.AccountHolders;
import com.rohan.database.DatabaseConnection;

/*	CREATE TABLE bank (sr_no NUMBER(10) PRIMARY KEY, holder_name VARCHAR2(20) NOT NULL,
		account_number NUMBER (10) UNIQUE NOT NULL, email_id VARCHAR2(20), 
		phone_number NUMBER(10) NOT NULL, address VARCHAR2(100) NOT NULL, 
		account_type VARCHAR2(10) NOT NULL, balance NUMBER(7,2) NOT NULL);
*/
public class BankingServices 
{
	DatabaseConnection dc = new DatabaseConnection();
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
			 if(rs.next())
			 
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
	public void depositMoney(AccountHolders ah) throws Exception
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			con = dc.database();
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
	}
}
