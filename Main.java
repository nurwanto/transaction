import java.util.*;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Main {
	public static void main(String args[]){
	    List<Transaction> transactions = new ArrayList(initialize().values());
	    Scanner scanner = new Scanner(System. in);
	    SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	    while(true){
	        System.out.println("\nPress Ctrl + C to exit!");
	        System.out.print("fromDate (e.g. 20/08/2018 12:00:00): ");
	        String startDate = scanner.nextLine();
	        System.out.print("toDate (e.g. 20/08/2018 13:00:00): ");
	        String endDate = scanner. nextLine();
	        System.out.print("merchant (e.g. Kwik-E-Mart): ");
	        String merchant = scanner. nextLine();
	        try{
	        	analize(transactions, formatter.parse(startDate), formatter.parse(endDate), merchant);
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	    }
	}

  	private static void analize(List<Transaction> data, Date startDate, Date endDate, String merchant){
	    double amount=0;
	    int count=0;
	    for(Transaction item : data){
	      	if(item.getMerchant().equals(merchant) && item.getDate().compareTo(startDate) >= 0 && item.getDate().compareTo(endDate) <= 0){
	        	count++;
	       		amount += item.getAmount();
	      	}
	    }
	    System.out.println("Result:");
	    System.out.println("Number of transactions = "+count);
	    System.out.println("Average Transaction Value = "+(amount/count));
	}

	private static HashMap initialize(){
		HashMap<String, Transaction> transactions = new HashMap<String, Transaction>();
    	SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    	String csvFile = "transaction.csv";
        BufferedReader br = null;
        String line = "";
        boolean isHeader=true;
        
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	if (isHeader){
            		isHeader=false;
            		continue;
            	}
                
                String[] temp = line.split(",");
		        if(temp[4].trim().equals("REVERSAL")){
		          	transactions.remove(temp[5].trim());
		        }else{
		          	transactions.put(temp[0], new Transaction(temp[0], formatter.parse(temp[1].trim()), Double.parseDouble(temp[2].trim()), temp[3].trim(), temp[4].trim(), temp.length == 6 ? temp[5].trim() : null));
		        }
            }
        } catch (Exception e){
        	e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	  	
	  	return transactions;
	}
}

class Transaction {
	private String Id;
	private Date Date;
	private double Amount;
	private String Merchant;
	private String Type;
	private String RelatedTransaction;
	
	Transaction(String id, Date date, double amount, String merchant, String type, String relatedTransction){
		this.Id = id;
		this.Date = date;
		this.Merchant = merchant;
		this.Amount = amount;
		this.Type = type;
		this.RelatedTransaction = relatedTransction;
	}

	public String getId(){
		return this.Id;
	}

	public void setId(String id){
		this.Id = id;
	}

	public Date getDate(){
		return this.Date;
	}

	public void setDate(Date date){
		this.Date = date;
	}

	public double getAmount(){
		return this.Amount;
	}

	public void setAmount(double amount){
		this.Amount = amount;
	}

	public String getMerchant(){
		return this.Merchant;
	}

	public void setMerchant(String merchant){
		this.Merchant = merchant;
	}

	public String getType(){
		return this.Type;
	}

	public void setType(String type){
		this.Type = type;
	}

	public String getRelatedTransaction(){
		return this.RelatedTransaction;
	}

	public void setRelatedTransaction(String relatedTransaction){
		this.RelatedTransaction = relatedTransaction;
	}

}