package com.cognizant.truyum.dao;


import java.sql.Date;


import java.sql.*;
import java.util.*;

import com.cognizant.truyum.model.MenuItem;
public class MenuItemDaoSqlImpl implements MenuItemDao {
	
	
	
	public ArrayList<MenuItem> getMenuItemListAdmin()
	{
		ArrayList<MenuItem> MenuItemList=new ArrayList<MenuItem>();
		Connection con = ConnectionHandler.getConnection();
		try
		{
			PreparedStatement preparedStatement=con.prepareStatement("select * from MenuItems");
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next())
			{
				long id=rs.getLong("id");
				String name  = rs.getString("name");
				float price = rs.getFloat("price");
				String active = rs.getString("active");
				Date dateOfLaunch = rs.getDate("dateOfLaunch");
				String category = rs.getString("category");
				String freeDelivery = rs.getString("freeDelivery");
				boolean act=false;
				if(active.equalsIgnoreCase("yes"))
				{
					act = true;
				}
				boolean free=false;
				if(freeDelivery.equalsIgnoreCase("yes"))
				{
					free = true;
				}
				MenuItem m=new MenuItem(id, name,  category, price, act, free,dateOfLaunch);
				MenuItemList.add(m);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return MenuItemList;
	}
	
	
	public ArrayList<MenuItem> getMenuItemListCustomer()
	{
		ArrayList<MenuItem> MenuItemList=new ArrayList<MenuItem>();
		Connection con = ConnectionHandler.getConnection();
		try
		{
			PreparedStatement preparedStatement=con.prepareStatement("select * from MenuItems where active='Yes' AND dateOfLaunch < '2021-05-19'");
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next())
			{
				long id=rs.getLong("id");
				String name  = rs.getString("name");
				float price = rs.getFloat("price");
				String active = rs.getString("active");
				Date dateOfLaunch = rs.getDate("dateOfLaunch");
				String category = rs.getString("category");
				String freeDelivery = rs.getString("freeDelivery");
				boolean act=false;
				if(active.equalsIgnoreCase("yes"))
				{
					act = true;
				}
				boolean free=false;
				if(freeDelivery.equalsIgnoreCase("yes"))
				{
					free = true;
				}
				MenuItem m=new MenuItem(id, name,  category, price, act, free,dateOfLaunch);
				MenuItemList.add(m);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return MenuItemList;
	}


	@Override
	public MenuItem getMenuItem(long menuItemId) {
		// TODO Auto-generated method stub
		
		Connection con = ConnectionHandler.getConnection();
		MenuItem m1=null;
		final String query = "select * from MenuItems where id=?";
		try
		{
			
			PreparedStatement preparedStatement=con.prepareStatement(query);
			preparedStatement.setLong(1, menuItemId);
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next())
			{
				long id=rs.getLong("id");
				String name  = rs.getString("name");
				float price = rs.getFloat("price");
				String active = rs.getString("active");
				Date dateOfLaunch = rs.getDate("dateOfLaunch");
				String category = rs.getString("category");
				String freeDelivery = rs.getString("freeDelivery");
				boolean act=false;
				if(active.equalsIgnoreCase("yes"))
				{
					act = true;
				}
				boolean free=false;
				if(freeDelivery.equalsIgnoreCase("yes"))
				{
					free = true;
				}
				m1=new MenuItem(id, name,  category, price, act, free,dateOfLaunch);
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return m1;
		
	}


	@Override
	public void modifyMenuItem(MenuItem menuItem) {
		// TODO Auto-generated method stub
		Connection con = ConnectionHandler.getConnection();
		long id = menuItem.getId();
		String name = menuItem.getName();
		float price = menuItem.getPrice();
		boolean active = menuItem.isActive();
		
		java.sql.Date dateOfLaunch =new Date( menuItem.getDateOfLaunch().getTime());
		String category = menuItem.getCategory();
		boolean free = menuItem.isFreeDelivery();
		String act;
		if(active == true)
		{
			act = "Yes";
		}
		else
		{
			act = "No";
		}
		String fd;
		if(free == true)
		{
			fd = "Yes";
		}
		else
		{
			fd = "No";
		}
		final String query ="update MenuItems set name = ?,price = ?,active = ?,dateOfLaunch = ?,category = ?,freeDelivery = ? where id = ?";
		 //String query = "update MenuItems" + "set name = ?,price = ?,active = ?,dateOofLaunch = ?,category = ?,freeDelivery = ?"+"where id = ?";
		try 
		{
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setFloat(2, price);
			stmt.setString(3, act);
			stmt.setDate(4, dateOfLaunch);
			stmt.setString(5,category);
			stmt.setString(6, fd);
			stmt.setLong(7, id);
			stmt.executeUpdate();
			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	


}