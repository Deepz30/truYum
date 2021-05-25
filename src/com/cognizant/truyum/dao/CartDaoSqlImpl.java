package com.cognizant.truyum.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;
public class CartDaoSqlImpl implements CartDao{
	@Override
	public void addCartItem(long userId, Long menuItemId) 
	{
		Connection con = ConnectionHandler.getConnection();
		
		long cartId = 1;
		try 
		{
			PreparedStatement stmt = con.prepareStatement("insert into cart(userId,menuitemId)"+"values (?,?)");
			stmt.setLong(1, userId);
			stmt.setLong(2, menuItemId);
			stmt.execute();
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		
	}
	
	
public ArrayList getAllCartItems(long userId) throws CartEmptyException {
		
		Connection con = ConnectionHandler.getConnection();
		ArrayList<MenuItem> menuItemList = new ArrayList<MenuItem>();
		
		Cart cart = new Cart(0,menuItemList);
		
		try
		{
			PreparedStatement stmt = con.prepareStatement("select * from MenuItems JOIN cart ON MenuItems.id = cart.menuitemId where cart.userId = ?");
			stmt.setLong(1, userId);
			ResultSet rs = stmt.executeQuery();
			float value  = 0.00f;
			while(rs.next())
			{
				long id = rs.getLong("id");
				String name  = rs.getString("name");
				float price = rs.getFloat("price");
				value = value + price;
				String active = rs.getString("active");
				Date dateOfLaunch = rs.getDate("dateOfLaunch");
				String category = rs.getString("category");
				String freeDelivery = rs.getString("freeDelivery");
				boolean act = false;
				if(active.equalsIgnoreCase("yes"))
				{
					act = true;
				}
				boolean free=false;
				if(freeDelivery.equalsIgnoreCase("yes"))
				{
					free = true;
				}
				MenuItem m1  = new MenuItem(id,name,category,price,act,free,dateOfLaunch);
				menuItemList.add(m1);
				
			}
			//total =  value;
			cart.setTotal(value);
			cart.setMenuItemList(menuItemList);
			
		} 
		catch (SQLException e) 
		{
		
			e.printStackTrace();
		}
		return menuItemList;
	}


public void removeCartItem(long userId, long menuItemId)
{
	Connection con = ConnectionHandler.getConnection();
	
	try {
		PreparedStatement stmt = con.prepareStatement("delete from  cart where userId = ? AND menuitemId = ?");
		stmt.setLong(1,userId);
		stmt.setLong(2, menuItemId);
		stmt.executeUpdate();
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
}




}