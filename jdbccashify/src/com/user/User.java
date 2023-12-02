package com.user;
import java.sql.*;
import java.util.*;

public class User {
	
	public Product sell() {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter user id: ");
		int id=sc.nextInt();
		System.out.println("Enter Name :");
		String name=sc.next();
		System.out.println("Enter Price:");
		int price=sc.nextInt();		
		System.out.println("Enter Cat:");
		String cat=sc.next();
		Product p=new Product(id, name, price, cat);
		return p;
	}
	
	public static Product buy() {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Id :");
		int id=sc.nextInt();
		Product p1=new Product(id, null, id, null);
		return p1;
	}
	public Product Update() {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Price:");
		int price=sc.nextInt();
		System.out.println("Enter Id:");
		int id=sc.nextInt();
		
		Product p2=new Product(id, null, price, null);
		return p2;
	}
	public void retrive(Connection conn) throws Exception {
		String retrive="Select * from product";
		PreparedStatement ps=conn.prepareStatement(retrive);
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			System.out.print(rs.getInt(1)+" ");
			System.out.print(rs.getString(2)+" ");
			System.out.print(rs.getInt(3)+" ");
			System.out.print(rs.getString(4)+" ");
			System.out.println();
			
		}
		ps.close();
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		User u=new User();
		Scanner sc=new Scanner(System.in);
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/cashify","root","Roat@123");
		
		for(;;) {
		System.out.println(".......OPTION.......");
		System.out.println("1.Selling Product.\n2.Buy Product.\n3.Update Product.\n4.Retriver.");
		System.out.println("........Enter your choice.......");
		int no=sc.nextInt();
		
		switch(no) {
		
		case 1:	System.out.println(".Selling Product.");
				Product pro=u.sell();
				String insert="insert into product value(?,?,?,?)";
				PreparedStatement pse=conn.prepareStatement(insert);
				pse.setInt(1, pro.id);
				pse.setString(2, pro.name);
				pse.setInt(3, pro.price);
				pse.setString(4, pro.cat);
				pse.execute();
				pse.close();
				break;
				
		case 2:
				System.out.println(".Buy Product.");
				Product by=u.buy();
				String delete="delete from product where id=?";
				PreparedStatement psd=conn.prepareStatement(delete);
				psd.setInt(1, by.id);
				psd.execute();
				psd.close();	
				break;
				
		case 3:
				System.out.println(".Update Product.");
				Product up=u.Update();
				String Update="update product set price=? where id=?";
				PreparedStatement psu=conn.prepareStatement(Update);
				psu.setInt(1, up.price);
				psu.setInt(2, up.id);
				psu.execute();
				psu.close();
				break;
		case 4:
				System.out.println("Retrive all data form Sql tables");
				u.retrive(conn);
				break;
				
		default:
				System.out.println("..End..");
				conn.close();			
			}
		}
	}
}
