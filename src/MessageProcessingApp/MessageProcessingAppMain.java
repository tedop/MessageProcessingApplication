package MessageProcessingApp;


/**@author Ted Okpoti - Paulo
 * J.P Morgan Java Technical Test
 * Message processing Application
*Date: 03/07/17
*
*This code was solely written from scratch, however some ideas 
*and parts of the code were taken from Internet sources 
**/

public class MessageProcessingAppMain {

	
	public static void main (String[] args) throws Exception{
		
		 try {
		//Creating object
		ProcessLineInMessage obj = new ProcessLineInMessage("BestBuy1.txt");
		
		//Calling "processEachLine" method from "ProcessLineInMessage" class 
		obj.processEachLine();
		
		 } catch (java.io.IOException e) {
	            e.printStackTrace();
	        }

	}

}
