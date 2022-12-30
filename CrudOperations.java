package com.ecommerse.project;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CrudOperations {
	// CrudOperations co=new CrudOperations();
	ConnectionClass cc = new ConnectionClass();
	AdminOperations ao = new AdminOperations();
	Scanner sc = new Scanner(System.in);

	// first indexPage() method will called
	public void indexPage() {
		int ch;
		System.out.println(" --------- Welcome to E-Commers Project ---------");
		System.out.println();
		System.out.println("1) Press 1 for Products List.");
		System.out.println("2) Press 2 for Login.");
		System.out.println("3) Press 3 for Register.");
		System.out.println();
		try {
			ch = sc.nextInt();

			switch (ch) {
				case 1:
					showProductList(); // A
					break;
				case 2:
					userLogin(); // B
					break;
				case 3:
					userRegisration(); // C
					break;
				default:
					System.out.println("Invalid input");
			}

		} catch (Exception e) {
			System.out.println("Invalid Input....Try Again");
		}

	}

	// A
	public void showProductList() {
		Connection con = cc.getConnection();
		Statement st;
		ResultSet rs;
		// int user_id=0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from products order by product_name;");
			while (rs.next()) {
				System.out.print("Id : " + rs.getString("product_id") + " || ");
				System.out.print("Name : " + rs.getString("product_name") + " || ");
				System.out.print("Desc : " + rs.getString("product_description") + " || ");
				System.out.print("Price : " + rs.getFloat("price") + " || ");
				System.out.println("Quantity : " + rs.getInt("quantity"));
			}
			con.close();
			// Register First
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(" Press 1 for Back.");
		int ch = sc.nextInt();
		switch (ch) {
			case 1:
				indexPage();
				break;
			default:
				System.out.println("Invalid input.");
				indexPage();
		}

	}

	// C
	public void userRegisration() throws Exception {
		String unm, pass, city, mob;
		Scanner sc = new Scanner(System.in);
		ConnectionClass cc = new ConnectionClass();
		Connection con = cc.getConnection();
		PreparedStatement pst;
		ResultSet rs;
		try {
			System.out.println("----- User Registration -----");
			System.out.println();
			System.out.println("Enter Name : ");
			unm = sc.nextLine();
			System.out.println("Enter Password : ");
			pass = sc.nextLine();
			System.out.println("Enter City : ");
			city = sc.nextLine();
			System.out.println("Enter Mobile No. : ");
			mob = sc.nextLine();

			pst = con.prepareStatement("insert into user(user_name,password,city,mobile) values(?,?,?,?);");
			pst.setString(1, unm);
			pst.setString(2, pass);
			pst.setString(3, city);
			pst.setString(4, mob);
			int cnt = pst.executeUpdate();
			if (cnt == 1) {
				System.out.println("User Registration success.....");
				System.out.println();

				retId(mob); // Print Id

				System.out.println();

				System.out.println("Press 1 for Login");
				int ch1 = sc.nextInt();
				CrudOperations co = new CrudOperations();
				switch (ch1) {
					case 1:
						co.userLogin();
						break;
					default:
						co.userLogin();
				}

			} else {
				System.out.println("Failed to Register,Please try Again...");
			}
			con.close();
		} catch (Exception e) {
			System.out.println("invalid Input..");
		}

	}

	public void retId(String mob) {
		Connection con = cc.getConnection();
		PreparedStatement pst;
		ResultSet rs;
		int userid = 0;
		try {
			pst = con.prepareStatement("select * from user where mobile=?;");
			pst.setString(1, mob);
			rs = pst.executeQuery();

			if (rs.next()) {
				userid = rs.getInt("user_id");
				System.out.println(
						"# your user_id is : " + userid + "  [ please keep it in mind, it will help you for login.]");

			}
			// String usernm=rs.getString("user_nm");
			// System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// B
	public void userLogin() throws Exception {
		int uid;
		String pass;
		// CrudOperations co=new CrudOperations();
		Scanner sc = new Scanner(System.in);
		ConnectionClass cc = new ConnectionClass();
		Connection con = cc.getConnection();
		PreparedStatement pst;
		ResultSet rs;
		try {
			System.out.println("----- User Login -----");
			System.out.println();
			System.out.println("Enter User Id : ");
			uid = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter Password : ");
			pass = sc.nextLine();

			pst = con.prepareStatement("select * from user where user_id=? and password=?");
			pst.setInt(1, uid);
			pst.setString(2, pass);

			rs = pst.executeQuery();
			if (rs.next()) {
				String utype;
				utype = rs.getString("user_type");
				String unm = rs.getString("user_name");

				if (utype.equalsIgnoreCase("customer")) {
					// System.out.println("Welcome");
					int ch;
					System.out.println("----- Welcome " + unm + " to Customer Page -----");
					System.out.println();
					System.out.println("1) Press 1 for Products List.");
					System.out.println("2) Press 2 for Your Orders.");
					System.out.println("3) Press 3 for Logout.");

					try {
						ch = sc.nextInt();

						switch (ch) {
							case 1:
								showProductsList(uid); // B1
								// method overloading
								break;
							case 2:
								checkOrders(uid); // B2
								break;
							case 3:
								indexPage(); // Return to index page.
								break;

							default:
								System.out.println("Invalid input");
								indexPage();
						}
						con.close();

					} catch (Exception e) {
						System.out.println("Invalid Input....Try Again");
					}

				} else {
					System.out.println();
					System.out.println("----- Welcome " + unm + " To Admin Page -----");
					System.out.println();
					System.out.println("1) Press 1 for Check Quantity Of Product.");
					System.out.println("2) Press 2 for Registered Users List.");
					System.out.println("3) Press 3 To See the History of User.");
					int choice = sc.nextInt();
					switch (choice) {
						case 1:
							ao.checkQuantity(); // B3
							break;
						case 2:
							ao.showUserList(); // B4
							break;
						case 3:
							ao.checkHistory(); // B5
							break;
						default:
							System.out.println("Invalid Input.");
							indexPage();

					}
				}
			} else {
				System.out.println("Wrong User Id & Password.");
				userLogin();
			}
		} catch (Exception e) {
			System.out.println("invalid Input..");
		}

	}

	// B1
	public void showProductsList(int uid) {
		Connection con = cc.getConnection();
		Scanner sc = new Scanner(System.in);
		Statement st;
		ResultSet rs;
		// int user_id=uid;
		ArrayList<String> al = new ArrayList();
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from products order by product_name;");
			while (rs.next()) {
				System.out.print("Id : " + rs.getString("product_id") + " || ");
				System.out.print("Name : " + rs.getString("product_name") + " || ");
				System.out.print("Price : " + rs.getFloat("price") + " || ");
				System.out.print("Quantity : " + rs.getInt("quantity"));
				System.out.println("Desc : " + rs.getString("product_description") + " || ");

			}
			// //int user_id;
			if (uid != 0) {
				System.out.println();
				System.out.println("----- Select Products -----");
				selectProduct(uid); // B1,1 select product method will called.

			} else {
				System.out.println("Please Register First..");
				userRegisration(); // if user not exist, user registration method will called.
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// B2
	public void checkOrders(int uid) {
		Connection con = cc.getConnection();
		PreparedStatement pst;
		ResultSet rs;
		try {
			pst = con.prepareStatement("select * from placed_order where user_id=?;");
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			System.out.println("----- Your Orders -----");
			System.out.println();
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

					System.out.println();

				}
			} else {
				System.out.println("---- No Order ----");
				userLogin();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	// B1,1
	public void selectProduct(int uid) {
		PreparedStatement pst;
		ResultSet rs;
		String product = "";

		int count_total_item = 0;
		float total_price = 0.0f;
		Connection con = cc.getConnection();
		Scanner sc = new Scanner(System.in);
		ArrayList<String> al = new ArrayList<String>();
		ArrayList<String> al1 = new ArrayList<String>();

		String choice = "yes";
		while (choice.equalsIgnoreCase("yes")) {
			try {

				System.out.println("Enter Products Name : ");
				product = sc.nextLine();

				// System.out.println("Enter Quantity : ");
				// int q=sc.nextInt();

				pst = con.prepareStatement("select * from products where soundex(product_name)=soundex(?);");
				pst.setString(1, product);
				rs = pst.executeQuery();
				if (rs.next()) {
					String prodnm = rs.getString("product_name");
					String prodid = rs.getString("product_id");
					int quantity = rs.getInt("quantity");
					// float p=rs.getFloat("price");
					// float tp=q*p;

					if (quantity >= 1) {
						total_price = total_price + (rs.getFloat("price"));
						count_total_item++;
						// count_total_item=count_total_item + q;
						al.add(prodnm);
						al1.add(prodid);
						System.out.println("Item Added Into Cart : " + al);
						System.out.println("Total Price : " + total_price);
						System.out.println("Total Items : " + count_total_item);
					} else {
						System.out.println("Sorry! " + prodnm + " is out of stock.");
					}

					// System.out.println(al1);
				} else {
					System.out.println("product Does Not Available.");
				}

			} catch (Exception e) {
				System.out.println();
			}

			System.out.println("Do you want add another product into cart. (yes/no) : ");
			choice = sc.nextLine();

		}
		// Loop over
		System.out.println();
		System.out.println("Press 1 for Place the Order.");
		System.out.println("Press 2 for Remove product.");
		System.out.println("Press 3 for Home.");
		int ch = sc.nextInt();
		switch (ch) {

			case 1:
				placeOrder(uid, count_total_item, total_price, al, al1); // B1,1,1
				break;
			case 2:
				removeProducts(uid, count_total_item, total_price, al, al1); // B1,1,2
				break;
			case 3:
				goBack(uid); // B1,1,3
				break;
			default:
				System.out.println("Invalid Input");
				System.out.println("press 1 for Back.");
				int ch1 = sc.nextInt();
				switch (ch1) {
					case 1:
						indexPage();
						break;
					default:
						indexPage();
				}

		}

	}

	// B1,1,1
	public void placeOrder(int uid, int item, float price, ArrayList al, ArrayList al1) {
		Connection con = cc.getConnection();
		Scanner sc = new Scanner(System.in);
		// String prodid=new String();
		String prodid = "";
		String prodnm = "";
		String pid = "";

		CallableStatement cs;

		for (int i = 0; i < al.size(); i++) {
			// updateQuantity();
			pid = (String) al1.get(i); // Use al1
			try {
				cs = con.prepareCall("call updateQuantity(?);");
				cs.setString(1, pid);
				cs.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}

			prodnm = prodnm.concat(al.get(i) + ",");
			prodid = prodid.concat(al1.get(i) + ",");
		}
		try {
			System.out.println("Enter Name : ");
			String nm = sc.nextLine();

			System.out.println("Enter Address : ");
			String address = sc.nextLine();

			System.out.println("Enter Mobile no. : ");
			String mob = sc.nextLine();

			System.out.println("Enter Pay Mode(Udhari Band) : ");
			String paymode = sc.nextLine();

			// Connection con=cc.getConnection();
			PreparedStatement pst;
			pst = con.prepareStatement(
					"insert into placed_order(user_id,user_name,product_id,products_name ,total_items,total_price,address,mobile,pay_mode,order_date,Time) values(?,?,?,?,?,?,?,?,?,Curdate(),Curtime());");
			pst.setInt(1, uid);
			pst.setString(2, nm);
			pst.setString(3, prodid);
			pst.setString(4, prodnm);
			pst.setInt(5, item);
			pst.setFloat(6, price);
			pst.setString(7, address);
			pst.setString(8, mob);
			pst.setString(9, paymode);
			int cnt = pst.executeUpdate();
			if (cnt == 1) {
				System.out.println("Your order is placed.");
				System.out.println("---- visit again ----");
				System.out.println("Prss 1 for Go Back.");
				int cho = sc.nextInt();
				switch (cho) {
					case 1:
						indexPage();
						break;
					default:
						indexPage();

				}
				// int cho=sc.nextInt();
				// switch(cho)
				// {
				// case 1:
				// goBack(uid);
				// break;
				// default :
				// System.out.println("Invalid Input");
				// goBack(uid);
				// }

			} else {
				System.out.println("order is not placed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// B1,1,2
	public void removeProducts(int uid, int item, float price, ArrayList al, ArrayList al1) {
		String prodnm;
		Scanner sc = new Scanner(System.in);

		Connection con = cc.getConnection();
		PreparedStatement pst;
		ResultSet rs;
		try {
			String ch = "yes";
			while (ch.equalsIgnoreCase("yes")) {
				System.out.println("Enter Product Name : ");
				prodnm = sc.nextLine();
				pst = con.prepareStatement("select * from products where product_name=?;");
				pst.setString(1, prodnm);
				rs = pst.executeQuery();
				if (rs.next()) {
					ch = "no"; // No need of these
					item--;
					price = price - (rs.getFloat("price"));
					al.remove(rs.getString("product_name"));
					al1.remove(rs.getString("product_id"));
					System.out.println("Product remove from List.");

					System.out.println("Product in Cart : " + al);
					System.out.println("Total Price     : " + price);
					System.out.println("Items           : " + item);

					System.out.println();
					System.out.println("1) Press 1 for Placed the order.");
					System.out.println("2) Press 2 for Back.");
					switch (1) {
						case 1:
							placeOrder(uid, item, price, al, al1);
					}

				} else {
					System.out.println("Product is not in List.");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// B1,1,3
	public void goBack(int uid) {
		Connection con = cc.getConnection();
		PreparedStatement pst;
		ResultSet rs;
		try {
			pst = con.prepareStatement("select * from user where user_id=?");
			pst.setInt(1, uid);
			// pst.setString(2, pass);

			rs = pst.executeQuery();
			if (rs.next()) {
				String utype;
				utype = rs.getString("user_type");
				String unm = rs.getString("user_name");

				if (utype.equalsIgnoreCase("customer")) {
					// System.out.println("Welcome");
					int ch;
					System.out.println("----- Welcome to User Page -----");
					System.out.println();
					System.out.println("1) Press 1 for Products List.");
					System.out.println("2) Press 2 for Your Orders.");
					System.out.println("3) Press 3 for Logout.");

					try {
						ch = sc.nextInt();

						switch (ch) {
							case 1:
								showProductsList(uid);
								// method overloading
								break;
							case 2:
								checkOrders(uid);
								break;
							case 3:
								indexPage();
								break;

							default:
								System.out.println("Invalid input");
						}
						con.close();

					} catch (Exception e) {
						System.out.println("Invalid Input....Try Again");
					}

				} else {
					System.out.println();
					System.out.println("----- Welcome To Admin Page -----");
					System.out.println();
					System.out.println("1) Press 1 for Check Quantity Of Product.");
					System.out.println("2) Press 2 for Registered Users List.");
					System.out.println("3) Press 3 To See the History of User.");
					int choice = sc.nextInt();
					switch (choice) {
						case 1:
							ao.checkQuantity();
							break;
						case 2:
							ao.showUserList();
							break;
						case 3:
							ao.checkHistory();
							break;
						default:
							System.out.println("Invalid Input.");

					}
				}

			} else {
				System.out.println("User Does Not Exist...");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
