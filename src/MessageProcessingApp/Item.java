package MessageProcessingApp;

/**@author Ted Okpoti - Paulo
 * J.P Morgan Java Technical Test
 * Message processing Application
*Date: 04/07/17
*
*This code was solely written from scratch, however some ideas 
*and parts of the code were taken from Internet sources 
**/

public class Item {
	
	
/*=============================================================================================
   Declaring variables
==============================================================================================*/    
	 private double totalItemPrice; 
	 private int totalItemQuantity;
	 private int itemPrice;
	 private int itemQuantity;
	
	
	 // Constructor
	 	public Item(String type) {
        this.totalItemPrice = 0.0;
        this.totalItemQuantity = 0;
        
    }
    
    
 /*=============================================================================================
  Set and get price and quantity of item
 ==============================================================================================*/    
 	   
 	
 	    public void setItemPrice(int ItemPrice) {
 	    	this.itemPrice = ItemPrice;            // Setting the price 
 	    	}
 	
 	    public int getItemPrice() {                //Getting the price
 	        return this.itemPrice;
 	    }
	    
 	    public void setItemQuantity(int ItemQuantity) {    //Setting the quantity 
 	    	itemQuantity = ItemQuantity; 
 	    	}
 	
 	    public int getItemQuantity() {                    //Getting the quantity
 	        return this.itemQuantity;
 	    }

 /*=============================================================================================
  calculating  Item price
  ==============================================================================================*/ 
     
 	
 	    public double calculatePrice(int ItemQuantity, double ItemPrice){
 	        return ItemQuantity * ItemPrice;  // Multiply Unit price to item quantity for total price
 	    }

/*=============================================================================================
 Setting and getting total Quantity
 ==============================================================================================*/ 
 	      
 	    
 	    public void setTotalQuantity(int ItemQuantity){    //Setting total quantity by adding to what's there already
 	    	this.totalItemQuantity += ItemQuantity;
 	    }

 	   
 	    public int getTotalQuantity() {     //Getting total quantity
 	        return this.totalItemQuantity ;
 	    }
 	    
/*=============================================================================================
setting, adding and getting total price
 ==============================================================================================*/ 
 	      

 	    
 
 	    public void setTotalPrice(double totalPrice) {   //Setting total price
 	    	this.totalItemPrice = totalPrice;  
 	    	}

 	    public void addTotalPrice(double ItemPrice) {   //Adding total price to what is already there
 	        this.totalItemPrice += ItemPrice;
 	    }
 	    
 	    public double getTotalPrice() {  //Getting total price
 	    	return this.totalItemPrice; 
 	    }
}
