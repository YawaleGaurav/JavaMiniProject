package com.ecommerse.project;

import java.sql.*;
import java.util.Scanner;

public class AdminOperations {
	// CrudOperations co=new CrudOperations();
	ConnectionClass cc = new ConnectionClass();
	Scanner sc = new Scanner(System.in);

	// B3
	public void checkQuantity() {
		Connection con = cc.getConnection();
		PreparedStatement pst;
		ResultSet rs;
		System.out.println("Enter Product id : ");
		String pid = sc.nextLine();
		try {
			pst = con.prepareStatement("Select quantity from products where product_id=?;");
			pst.setString(1, pid);
			rs = pst.executeQuery();
			if (rs.next()) {
				System.out.println("Quantity  : " + rs.getInt("quantity"));
			} else {
				System.out.println("Wrong Product Id.....");
			}

			// System.out.println("Prss 1 for Go Back.");
			// int cho=sc.nextInt();
			// switch(cho)
			// {
			// case 1:
			// co.goBack(uid);
			// break;
			// default :
			// System.out.println("Invalid Input");
			// co.goBack(uid);
			// }

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	// B4
	public void showUserList() {
		Connection con = cc.getConnection();
		PreparedStatement pst;
		ResultSet rs;
		try {
			pst = con.prepareStatement("select * from user where user_type='customer';");
			rs = pst.executeQuery();
			while (rs.next()) {
				System.out.print(rs.getInt("user_id") + " || ");
				System.out.print(rs.getString("user_name") + " || ");
				System.out.print(rs.getString("city") + " || ");
				System.out.println(rs.getString("mobile"));
			}

			// System.out.println("Prss 1 for Go Back.");
			// int cho=sc.nextInt();
			// switch(cho)
			// {
			// case 1:
			// co.goBack(uid);
			// break;
			// default :
			// System.out.println("Invalid Input");
			// co.goBack(uid);
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// B5
	public void checkHistory() {
		Connection con = cc.getConnection();
		PreparedStatement pst;
		ResultSet rs;
		System.out.println("Enter User id : ");
		int user_id = sc.nextInt();
		try {
			pst = con.prepareStatement("select * from placed_order where user_id=?;");
			pst.setInt(1, user_id);
			rs = pst.executeQuery();
			if (rs != null) {

				while (rs.next()) {
					System.out.print(rs.getInt("user_id") + " || ");
					System.out.print(rs.getString("user_name") + " || ");
					System.out.print(rs.getString("products_name") + " || ");
					System.out.print(rs.getFloat("total_price") + " || ");
					System.out.print(rs.getString("address") + " || ");
					System.out.print(rs.getString("mobile") + " || ");
					System.out.print(rs.getString("pay_mode") + " || ");
					System.out.print(rs.getString("order_date") + " || ");
					System.out.println(rs.getString("Time"));
				}

			} else {
				System.out.println("---- No Order ----");
			}
			// System.out.println("Prss 1 for Go Back.");
			// int cho=sc.nextInt();
			// switch(cho)
			// {
			// case 1:
			// co.goBack(uid);
			// break;
			// default :
			// System.out.println("Invalid Input");
			// co.goBack(uid);
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
