package MessageProcessingApp;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;


/**@author Ted Okpoti - Paulo
 * J.P Morgan Java Technical Test
 * Message processing Application
*Date: 03/07/17
*
*This code was solely written from scratch, however some ideas 
*and parts of the code were taken from Internet sources 
**/

public class ProcessLineInMessage {
    
	// PRIVATE 
	  private final Path filePath;
	  private final static Charset ENCODING = StandardCharsets.UTF_8;
	  private static String item;
	  private static int itemQuantity;
	  private static int itemPrice;
	  private double totalItemPrice;
	  private ArrayList<String> Reports;
	  private Item itemObj;
	 
	//Constructor
	  public ProcessLineInMessage(String txtFileName) {
		  filePath = Paths.get(txtFileName);	  
		  this.Reports = new ArrayList<String>();
		
	}
	  
	  
	  public final void processEachLine() throws IOException {
		  try (Scanner scanner =  new Scanner(filePath, ENCODING.name())){
		      while (scanner.hasNextLine()){
		    	  reportProcessing(scanner.nextLine());
			      print();
		      }      
		    }
	      }   
	  
/*=============================================================================================
 Setting up  Item price, quantity
 ==============================================================================================*/ 
	 	    public boolean reportProcessing(String processing) {

	 	    	 ProcessLineInMessage.processLine(processing);  // process Line Method
	 	    		   
	 	    	String itemType = ProcessLineInMessage.getItem(); //getting type of item being sold
	 	    	
	 	    	if (itemType.isEmpty()) {
	 	            return false;          //Checking item being sold is empty or not    
	 	        }
	 	      
	 	        this.itemObj = getItem(itemType);
	 	        this.itemObj.setItemQuantity(ProcessLineInMessage.getItemQuantity());
	 	        this.itemObj.setTotalQuantity(ProcessLineInMessage.getItemQuantity()); //Set item quantity and price
	 	        this.itemObj.setItemPrice(ProcessLineInMessage.getItemPrice());
	 	        
	 	        
	 	        setItemTotalPrice(); // setting total Item Value
	 	        setReports(processing); 
	 	        mapItem(itemObj);

	 	        return true;
	 	    }  
		 
	 	    protected static boolean processLine(String Line){
			    //use a second Scanner to parse the content of each line 
			    Scanner scanner = new Scanner(Line);
			    String[] LineArray = Line.trim().split("\\s+"); 
			    String string1 = LineArray[0];
			    //scanner.useDelimiter("=");
			    // 1st MESSAGE TYPE
			    if (scanner.hasNext() && LineArray[1].contains("at") && LineArray.length == 3){
			    	if(LineArray.length > 3 || LineArray.length < 3){ scanner.close(); return false;}
			    	item = LineArray[0];
			    	itemPrice = Integer.parseInt(LineArray[2]);
			    	itemQuantity = 1;
			    	scanner.close();
			    	return true; 
			    	
			    //2nd MESSAGE TYPE
			    }else if( scanner.hasNext() && string1.matches("^\\d+")){
			    	if(LineArray.length > 7 || LineArray.length < 7) {scanner.close(); return false;}
			    	item = LineArray[3];
			    	itemPrice = Integer.parseInt(LineArray[5]);
			    	itemQuantity = Integer.parseInt(LineArray[0]);
			    	scanner.close();
			    	return true;
			    
			    //3RD MESSAGE TYPE
			    }else if (scanner.hasNext() && string1.matches("Add|Subtract|Multiply")) {
			    	if(LineArray.length > 3 || LineArray.length < 3) {scanner.close();return false;}
			    	item = LineArray[2];
			    	itemPrice = Integer.parseInt(LineArray[1]);
			    	itemQuantity = 0;
			    	scanner.close();
			    	return true;
			    }else {
			      log("Invalid line or probably empty. Message Unable to process.");
			      scanner.close();
			    }
			    return true;
			    
			  }
				 
	 	   
/*=============================================================================================
 Getting Item details
 ==============================================================================================*/
	    public static  String getItem() {
	        return item;               //get the item
	    }

	    public static int getItemQuantity() {
	        return itemQuantity;      //get the quantity
	    }
	    
	  
	    public static int getItemPrice() {
	        return itemPrice;         //get the Price
	    }
	

/*=============================================================================================
Creating HashMap to help in displaying all items(key) alongside the values
==============================================================================================*/  
 	    
	    private HashMap<String, Item> map = new HashMap<String, Item>();
	    
	    // Get particular item from each Line
	    public Item getItem(String type) {
	        return map.getOrDefault(type, new Item(type));
	    }
	    
	    // Set Key and value to help map each item to price and quantity
	    public void mapItem(Item item2){
	    	map.put(getItem(),item2);
	    }
	     
/*=============================================================================================
Setting and getting Report with Array list to help track/count number of lines processed
 ==============================================================================================*/	    
	    public void setReports(String Report) {
	        this.Reports.add(Report);
	    }
	    
	    public ArrayList<String> getReports() {
	        return Reports;
	    }
	    

/*=============================================================================================
 Printing out Notification
 ==============================================================================================*/     
	    public void print () {
	     if((Reports.size() % 10) == 0 && Reports.size() !=0) {
	   	System.out.println("\n\n============================================================================");
    	System.out.println("                        PROCESSED SALES NOTIFICATIONS                        ");
    	System.out.println("============================================================================");	    	
        System.out.printf("%5s %20s %22s %21s  ", "ITEM", "QUANTITY", "UNITPRICE(£)", "PRICE(£)");
        System.out.println();
        map.forEach((key,value) -> ReportStructure(key,value)); 
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println(String.format("|%-60s|%-11.2f|","Total Item Sales",getTotalPriceOfItemsSold()));
        
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Data Processed:"+ Reports.size() +"  "+ "Date/time:"+ new SimpleDateFormat("yyyy/MM/dd/HH:mm").format(new Date()));
	    	 }	    	 
	    
	    
        if((Reports.size() % 50) == 0 && Reports.size() !=0) {
	    System.out.print("\nAPPLICATION PAUSING, REACHED 50 MESSAGES!!! ");
	    System.out.println("..............Displaying adjustments made.............");
	    System.out.println("......");
	    System.out.println("............");
	    System.out.println("...................");
	    
/*=============================================================================================
Displaying adjustment log
==============================================================================================*/	    

	    System.out.println("Logging Adjustments log:");
	    for (String adjustments : getReports())
	    {
	        if (adjustments.contains("Add") || adjustments.contains("Subtract") || adjustments.contains("Multiply")) 
	        	
	            System.out.println(  adjustments + " Executed");
	    
	    }
	    System.out.println("\n.......EXISTING APPLICATION..........");
	    System.out.println("...................");
	    System.out.println("............");
	    System.out.println("......");
	    
	    System.exit(0);
	    }
	     
	 }
/*=============================================================================================
  Declaring Methods
==============================================================================================*/ 
	   	   //Method to set total item price 
	   	    	private void setItemTotalPrice() {
	   	        double ItemPrice;
	   	        ItemPrice = itemObj.calculatePrice(getItemQuantity(), getItemPrice()); //multiplies quantity with unitprice
	   	        itemObj.addTotalPrice(ItemPrice);    
	   	    }
	   	    	// Set and get the total price of the items
	    	    public void setTotalPrice(double totalItemPrice) { 
	    	    this.totalItemPrice = totalItemPrice;  
	    	   }public double getTotalPriceOfItemsSold() {
	   	        return totalItemPrice;
	   	    }
	    	    	    	    
	    	  // Add the value to the existing total price
	    	    public void addTotalSales(double ItemPrice) {
	    	        this.totalItemPrice += ItemPrice;
	    	    }
	    	    
	    	   //Method to print out log
	    	    private static void log(Object aObject){
	   		    System.out.println(String.valueOf(aObject));
	   		  }

	    	//Method to print out notification using Hashmap(key, value)
	    	public void ReportStructure(String type, Item item) {
	        String Item101 = String.format("|%-18s|%-20d|%-20d|%-11.2f|", type, item.getTotalQuantity(), item.getItemPrice(), item.getTotalPrice());
	        addTotalSales(item.getTotalPrice());
	        System.out.println(Item101);
	    }
	} 

