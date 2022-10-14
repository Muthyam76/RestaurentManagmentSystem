package restaurentManagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;



public class Controller_GUI {
	UserInterface_GUI cView;
	private String      currentUserName;
	 private int         currentUserID;
	 private String      errorMessage;
	 private int         userType;
	 private String      todaysDate;
	 public final static int USER_ANONYMOUS = 0;
	 public final static int USER_EMPLOYEE = 1;
	 public final static int USER_MANAGER = 2; 
	 private int         CancelCnt;
	 private int         todaysOrderCnt;     //Today's order count
	 private double      totalSales;         //Today's total sales
	 

    public Controller_GUI()
    {
        
        
        cView = new UserInterface_GUI( this);
        cView.setVisible(true);
        this.userType = USER_ANONYMOUS;
        
        Date date = new Date();
        SimpleDateFormat stf = new SimpleDateFormat("yyyy/MM/dd");
        todaysDate = stf.format(date);
    }
    
    
    private void  setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
    
    public String  getErrorMessage()
    {
        String result = this.errorMessage;
        this.errorMessage = "";
        return result;
    }
    public String getCurrentUserName()
    {
        return this.currentUserName;
    }
    public int getOrderState(int orderID)
    {
    	try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		String sql="select * from orders where id='"+orderID+"'";
    		ResultSet result=st.executeQuery(sql);
    		if(result.next())
    		{
    			return result.getInt("Status");
    		}
    		
    		
    		
    	}
    	catch(Exception e)
    	{
    		//throw new RuntimeException
    		JOptionPane.showMessageDialog(null, "enter correct");
    		
    	
    	}
    	return -1;
    }
    public int getprice(int id)
    {

    	try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		String sql="select * from menu where Id='"+id+"'";
    		ResultSet result=st.executeQuery(sql);
    		if(result.next())
    		{
    			
    			return result.getInt("Price");
    		}
    		
    		
    		
    	}
    	catch(Exception e)
    	{
    		//throw new RuntimeException
    		JOptionPane.showMessageDialog(null, "enter correct");
    		
    	
    	}
    	return -1;	
    }
    public int getQuantity(int orderId,int itemId)
    {

    	try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		String sql="select * from orderdetails where OrderId='"+orderId+"' and '"+itemId+"' ";
    		ResultSet result=st.executeQuery(sql);
    		if(result.next())
    		{
    			
    			return result.getInt("Quantity");
    		}
    		
    		
    		
    	}
    	catch(Exception e)
    	{
    		//throw new RuntimeException
    		JOptionPane.showMessageDialog(null, "enter correct");
    		
    	
    	}
    	return -1;	
    }
    /***********************************************************
     * Login 
     **********************************************************/
    //----------------------------------------------------------
    // Find user
    //----------------------------------------------------------  
    public boolean loginCheck( int inputID, String inputPassword, boolean isManager)
    {
        String searchClassName;
            
        //---------search user----------
        
        

        if(isManager)   searchClassName = "Manager";
        else            searchClassName = "Staff";
        try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		String sql="select * from employee where Id='"+inputID+"'";
    		ResultSet result=st.executeQuery(sql);
    		if(result.next())
    		{
    			if(!inputPassword.equals(result.getString("passwor")))
    			{
    			//setErrorMessage("Password unmatch.");
                 //printErrorMessageToView("Password unmatching.");
                 return false;
    			}
    			else if(!searchClassName.equals(result.getString("position")))
    			{
    			 setErrorMessage("Not found marching with position.");
              //printErrorMessageToView("Not found.");
              return false;
    			}
    			else
    			{
    				if(isManager)
                 {
                     userType = USER_MANAGER;
                     cView.changeMode(cView.MODE_MANAGER);
                 }
                 else 
                 {
                     userType = USER_EMPLOYEE;
                     cView.changeMode(cView.MODE_EMPLOYEE);
                 }
    				return true;
    			}
    		}
    		else
    		{
    			setErrorMessage("Not found.");
             return false;	
    		}
        }
    		  
        catch(Exception e)
    	{
    		//throw new RuntimeException
    		JOptionPane.showMessageDialog(null, "enter correct");
    		
    		
    	
    	}
        return false;
    }
    public void userLogout()
    {
        userType = USER_ANONYMOUS;
        currentUserID = 0;
        
    }

    
    public void addStaff(String id,String first,String last,String password,String position)
    {
    	
    	try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		
    		String sql="select Id from employee";
    		ResultSet result=st.executeQuery(sql);
    		while(result.next())
    		{
    			if(result.getString("Id").equals(id))
    			{
    				JOptionPane.showMessageDialog(null, "user id already exists ");
    				return ;
    			}
    		}
    	   sql="insert into employee values('"+id+"','"+first+"','"+last+"','"+password+"','"+position+"')";
    		int l=st.executeUpdate(sql);
    		JOptionPane.showMessageDialog(null, "sucess");
    		
    		System.out.println(l);
    	}
    	catch(Exception e)
    	{
    		//throw new RuntimeException
    		JOptionPane.showMessageDialog(null, "enter correct");
    		
    	
    	}
    }
    
    public boolean updateStaff( int id ,String newPassword, String newFirstName, String newLastName)
    {
    	boolean result=false;
    
    		try {
		Connection con=ConnectionProvider.getCon();
		Statement st=con.createStatement();
		String sql="";
		//"update  employee set  Id='"+80+"'where Id='"+id+"'";
		
	    //st.executeUpdate(sql);
	    sql="update  employee set  passwor='"+newPassword+"'where Id='"+id+"'";
	    st.executeUpdate(sql);
	    sql="update  employee set  Fname='"+newFirstName+"'where Id='"+id+"'";
	    st.executeUpdate(sql);
	    sql="update  employee set  Lname='"+newLastName+"'where Id='"+id+"'";
	    st.executeUpdate(sql);
	    
	    result=true;
	}
	catch(Exception e)
	{
		//throw new RuntimeException
		JOptionPane.showMessageDialog(null, "not updated");
		
	
	}
    return result;		
    }
    public boolean deleteStaff(int id)
    { 
    	boolean result=false;

    	try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		String sql="delete from employee where Id='"+id+"'";
    	    st.executeUpdate(sql);
    	    result=true;
    	}
    	catch(Exception e)
    	{
    		//throw new RuntimeException
    		JOptionPane.showMessageDialog(null, "enter correct");
    		
    	
    	}
    	
    	return result;
    }
    /***********************************************************
     * Menu management
     **********************************************************/
    public boolean addNewMenuItem(int newID, String newName, double newPrice, String menuType)
    {
    	boolean result=false;
    	try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		String sql="select id from menu";
    		ResultSet res=st.executeQuery(sql);
    		while(res.next())
    		{
    			if(res.getInt("id")==newID)
    			{
    				JOptionPane.showMessageDialog(null, "user id already exists ");
    				return false;
    			}
    		}
    		
    		 sql="insert into menu values('"+newID+"','"+newName+"','"+newPrice+"','"+menuType+"')";
    		st.executeUpdate(sql);
    		JOptionPane.showMessageDialog(null, "sucess");
    		result=true;
    		
    		
    	}
    	catch(Exception e)
    	{
    		//throw new RuntimeException
    		JOptionPane.showMessageDialog(null, "enter correct");
    		
    	
    	}
    	return result;
    }

    public boolean updateMenuItem(int id, String newName, double newPrice, String menuType)
    {

		try {
	Connection con=ConnectionProvider.getCon();
	Statement st=con.createStatement();
	String sql="";
	//"update  employee set  Id='"+80+"'where Id='"+id+"'";
	
    //st.executeUpdate(sql);
    sql="update  menu set  Name='"+newName+"'where Id='"+id+"'";
    st.executeUpdate(sql);
    sql="update  menu set  Price='"+newPrice+"'where Id='"+id+"'";
    st.executeUpdate(sql);
    sql="update  menu set  Type='"+menuType+"'where Id='"+id+"'";
    st.executeUpdate(sql);
    
    return true;
		}
       catch(Exception e)
		{
    

	//throw new RuntimeException
	JOptionPane.showMessageDialog(null, "not updated menu");
		}
    	return false;
    }
    
    public boolean deleteMenuItem(int id)
    {
    	boolean result=false;

    	try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		String sql="delete from menu where Id='"+id+"'";
    		 
    	    st.executeUpdate(sql);
    	    result=true;
    	}
    	catch(Exception e)
    	{
    		//throw new RuntimeException
    		JOptionPane.showMessageDialog(null, "enter correct");
    		
    	
    	}
    	
    	return result;
    }
    public int createOrder()
    {
    	int l=0;
    	try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		String name= currentUserName;
    		int id=0;
    		
    		int price=0;
    		
    		String sql="insert into orders(Staffname,Bill,Status,Date) values('"+name+"','"+price+"','"+0+"','"+todaysDate+"')";
    		st.executeUpdate(sql);
    	      sql="select id from orders order by id desc limit 1;";
    	      ResultSet result=st.executeQuery(sql);
    	      if(result.next())
    	      {
    	    	   l=result.getInt("id");
    	    	  cView.displayMessage( l+"");
    	      }
    	    
    	      
    		
    		
    	}
    	 catch(Exception e)
    	{
    		//throw new RuntimeException
    		JOptionPane.showMessageDialog(null, "orderid not implemented");
    		
    	
    	}
    	return l;
    	
        
    }
    public boolean addNewOrderItem(int orderID, int addItemID, byte Quantity)
    {
    	
    	try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		
    		String sql="select * from menu where id='"+addItemID+"'";
    		ResultSet result=st.executeQuery(sql);
    		
    		Connection co=ConnectionProvider.getCon();
    		Statement ste=co.createStatement();
    		sql="select * from orderdetails where Orderid='"+orderID+"' and Itemid='"+addItemID+"'";
    		ResultSet res=ste.executeQuery(sql);
    		
    		if(res.next())
    		{
    			
    			 int total=getprice(addItemID);
    			int price=res.getInt("Price");
    			int no=res.getInt("Id");
    			int quantity=Quantity+getQuantity(orderID,addItemID);
    			
    			price=price+(total*Quantity);
    			
    			Connection cone=ConnectionProvider.getCon();
        		Statement sti=cone.createStatement();
    			sql="update orderdetails set Price='"+price+"', Quantity='"+quantity+"' where Orderid='"+orderID+"' and Itemid='"+addItemID+"' ";
    			sti.executeUpdate(sql);
    			 
    			return true;
    		}
    		
    		while(result.next())
    		{
    			
    		String name=result.getString("Name");
			cView.displayMessage("hel");
			if(result.getInt("Id")==addItemID)
    		{
    			 int total=getprice(addItemID);
    			 total=Quantity*total;
    			
    			
    			 sql="insert into orderdetails(Orderid,Itemname,Itemid,Quantity,Price)values('"+orderID+"','"+name+"','"+addItemID+"','"+Quantity+"','"+total+"')";
    			 
    			
    			 
    			st.executeUpdate(sql);
    			return true;
    			
    			
    			
    		}
    		
    		
    		}
    	}
    	
    	catch(Exception e)
    	{
    		//throw new RuntimeException
    		JOptionPane.showMessageDialog(null, "orderedItem not added");
    		
    	
    	}
    	return true;
    	
    
    }
    public boolean deleteOrderItem(int orderID, int deleteNo)
    {

    	try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		cView.displayMessage(orderID+"bachavo"+deleteNo);
    		String sql="delete from orderdetails where Id='"+deleteNo+"' and Orderid='"+orderID+"'";
    	
    		st.executeUpdate(sql);
    		
    		///cView.displayMessage("deleted");
    		return true;
    		
    	}
    	
    	catch(Exception e)
    	{
    		//throw new RuntimeException
    		JOptionPane.showMessageDialog(null, "not deleted ordereditem ");
    		
    	
    	}
    	return false;
    	 
    }
    public boolean cancelOrder(int cancelOrderID)
    {
    	try {
    		
    		Connection con2=ConnectionProvider.getCon();
    		Statement st2=con2.createStatement();
    		 String sql="delete from orders where id='"+cancelOrderID+"' and Status='"+0+"'";
    		st2.executeUpdate(sql);
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		
    		  sql="delete from orderdetails where Orderid='"+cancelOrderID+"'";
    		st.executeUpdate(sql);
    		
    		
    		CancelCnt++;
    		
    		
    		return true;
    		
    		
    		
    		
    	}
    	 catch(Exception e)
    	{
    		//throw new RuntimeException
    		 
    		JOptionPane.showMessageDialog(null, "not cancel order");
    		 return false;
    		
    	
    	}
    }
    public boolean closeOrder(int closeOrderID)
    {
    	try {
    		Connection con=ConnectionProvider.getCon();
    		Statement st=con.createStatement();
    		String sql="update orders set Status='"+1+"' where id='"+closeOrderID+"'";
    		st.executeUpdate(sql);   		
    			sql ="select *,sum(Price) as total from orderdetails where Orderid='"+closeOrderID+"'";
    			ResultSet res=st.executeQuery(sql);
    			if(res.next())
    			{
    	    		 int Total=res.getInt("total");
    	    		// cView.displayMessage(Total+"");
    	    		 sql="update orders set Bill='"+Total+"'  where id='"+closeOrderID+"'";
    	    		 st.executeUpdate(sql);
    			}   		
    		return true;
    		
    		
    	}
    	 catch(Exception e)
    	{
    		//throw new RuntimeException
    		 return false;
    		//JOptionPane.showMessageDialog(null, "not cancel order");
    		
    	
    	} 
    }
    
    int getTodaysOrderCnt()
    {
 	   int count=0;

 	   	try {
 	   		Connection con=ConnectionProvider.getCon();
 	   		Statement st=con.createStatement();
 	   	 String sql="select Date from orders where Date='"+todaysDate+"'";
 	      ResultSet result=st.executeQuery(sql);
 	      while(result.next())
 	      {
 	    	  count++;
 	      }
 	   		
 	   		
 	   		
 	   		
 	   		
 	   	}
 	   	 catch(Exception e)
 	   	{
 	   		//throw new RuntimeException
 	   		
 	   		//JOptionPane.showMessageDialog(null, "no orders today");
 	   		
 	   	
 	   	}
 	   	return count;
 		
    }
    int getTotalSales()
    {
 	   int total=0;

 	   	try {
 	   		Connection con=ConnectionProvider.getCon();
 	   		Statement st=con.createStatement();
 	   	 String sql="select  Bill from orders where Date='"+todaysDate+"' ";
 	      ResultSet result=st.executeQuery(sql);
 	      while(result.next())
 	      {
 	    	  total=total+result.getInt("Bill");
 	      }
 	   		
 	   		
 	   		
 	   		
 	   		
 	   	}
 	   	 catch(Exception e)
 	   	{
 	   		//throw new RuntimeException
 	   		
 	   		JOptionPane.showMessageDialog(null, "no orders today");
 	   		
 	   	
 	   	}
 	   	return total;   
    }
    
   	/***********************************************************
        * Create stiring lists
        **********************************************************/
       public  ArrayList<String>  createStaffList()
       {
          // Iterator<Staff> it = cDatabase.getStaffList().iterator();
           ArrayList<String> initData = new ArrayList<String>();

       	try {
       		Connection con=ConnectionProvider.getCon();
       		Statement st=con.createStatement();
       		String sql="select * from employee";
       		ResultSet result=st.executeQuery(sql);
       		while(result.next())
       		{
       			int id=result.getInt("Id");
       			String fname=result.getString("Fname");
       			String lname=result.getString("Lname");
       			String fullname=fname+lname;
       			String output = String.format("Staff ID:%4d  Name:%-25s",
                           id, fullname);
       			String position=result.getString("position");
       			output=output+" "+position;
       			initData.add(output);
       		}
       		
       		
       	}
           catch(Exception e)
       	{
       		//throw new RuntimeException
       		JOptionPane.showMessageDialog(null, "enter correct");
       		
       	
       	}
           
           
           
          return initData;
       }
       public  ArrayList<String>  createMenuList(String a)
       {
       	ArrayList<String> initData = new ArrayList<String>();
       	try {
       		Connection con=ConnectionProvider.getCon();
       		Statement st=con.createStatement();
       		String sql="select * from menu";
       		ResultSet result=st.executeQuery(sql);
       		String strMenuType="";
       		while(result.next())
       		{
       			String menuType=result.getString("Type");
       			if((!(a.equals( "All"))) &&  (!a.equals(menuType)))
                       continue;
       			switch( menuType)
                   {
                       case "Main":
                       strMenuType = "Main";
                       break;
                       case "Drink":
                       strMenuType = "Drink";
                       break;
                       case "Alcohol":
                       strMenuType = "Alcohol";
                       break;
                       case "Dessert":
                       strMenuType = "Dessert";
                       break;
                       default:
                       strMenuType = "Undefined";
                       break;
                   }
       			int id=result.getInt("id");
       			String name=result.getString("Name");
       			float price=result.getInt("Price");
       			String type=result.getString("Type");
       			String output = String.format("Menu ID:%4d  Name:%-20s  Price:%5.2f Type:%s",
                           id,name,price,type);
       			
       			
       			initData.add(output);
       		}
       		
       		
       	}
           catch(Exception e)
       	{
       		//throw new RuntimeException
       		JOptionPane.showMessageDialog(null, "enter correct");
       		
       	
       	}
       	return initData;
       	
       }
       public ArrayList<String> createOrderItemlList(int orderID)
       //private void createOrderItemlList( int orderID, JList list)
       {
       	ArrayList<String> initData = new ArrayList<String>();
       	int count = 0;
       	try {
       		Connection con=ConnectionProvider.getCon();
       		Statement st=con.createStatement();
      
       		String sql="select * from orderdetails where Orderid='"+orderID+"'";
       		ResultSet result=st.executeQuery(sql);
       		String strMenuType="";
       		while(result.next())
       		{
       		int no=result.getInt("Id");	
       		String itemname=result.getString("Itemname");
       		int orderid=result.getInt("Orderid");
       		
       		
       		
       		
       		int quantity=result.getInt("Quantity");
       		float total=result.getInt("Price");
       		String output = String.format("%-4d|%-24s|%5d|%5.2f|%-4d",
                       no, itemname, quantity, total,orderid);
       		 initData.add(output);
       		
       		}
       	}
       	 catch(Exception e)
       	{
       		//throw new RuntimeException
       		JOptionPane.showMessageDialog(null, "enter correct");
       		
       	
       	}
       	return initData;
       }
      
       public ArrayList<String>  createOrderList()
       {
           
           String          state;
           ArrayList<String> initData = new ArrayList<String>();
           String          output;
           String sql="";
           try {
       		Connection con=ConnectionProvider.getCon();
       		Statement st=con.createStatement();
       		
       		sql="select * from orders";
       		ResultSet result=st.executeQuery(sql);
       		while(result.next())
       		{
       			int  l=result.getInt("id");
       		cView.displayMessage(l+"");
       			//initData.add(l+"");
       			Connection con2=ConnectionProvider.getCon();
           		Statement st2=con2.createStatement();
       			sql ="select *,sum(Price) as total from orderdetails where Orderid='"+l+"'";
       			ResultSet res=st2.executeQuery(sql);
       			if(res.next())
       			{
       			
       			String staffname=result.getString("Staffname");
       			
       			float price =res.getInt("total");
       			
       			int status=result.getInt("Status");
       			String s="";
       			if(status==0)
       			{
       				s="open";
       			}
       			else
       				s=" closed";
       			output = String.format("Order ID:%4d  StaffName:%-20s  Total:$%5.2f State:%-8s\n",
                           l,staffname,price,s);
       			l=res.getInt("price");
       			initData.add(output);
       			}
       			
       			
       			
       		}
       	
       		
           }
           catch(Exception e)
       	{
       		//throw new RuntimeException
       		JOptionPane.showMessageDialog(null, "enter correct bro");
       		
       	
       	}
           if(initData.isEmpty())
               initData.add("No order.");
           return initData;
           
       }

}
