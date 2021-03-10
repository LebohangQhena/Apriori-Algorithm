import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Apriori {
	private ArrayList<String> arybaskets = new ArrayList<String>();
	private ArrayList<String> aryItems = new ArrayList<String>();
	private HashMap<String, Integer> itemcount = new HashMap<String, Integer>();
	private ArrayList<String> aryPairs = new ArrayList<String>();
	private HashMap<String, Integer> paircount = new HashMap<String, Integer>();
	private HashMap<String, Integer> pairc3 = new HashMap<String, Integer>();
	
	public Apriori() {
		
	}
	
	public void ReadFile(String txtfile) {
		File f = new File(txtfile);
		Scanner scRead;
		
		String basket = new String();
		try {
			scRead = new Scanner(f);
			
			while(scRead.hasNextLine()) {
				basket = scRead.nextLine();
				arybaskets.add(basket);
				getitems(basket);
			}
				

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(arybaskets.size());
		//System.out.println("Items" + aryItems.size());
	}
	
	private void getitems(String basket) {
		String[] items = basket.split(",");
		if(aryItems.size() == 0) {
			for(String i : items)
				aryItems.add(i);
		}else {
			for(String i : items) {
				if(!aryItems.contains(i)) {
					aryItems.add(i);
				}
			}
		}
		
	}
	
	public void countItems() {
		int counter = 0;
		for(String item : aryItems) {
			for(String basket : arybaskets) {
				if(basket.contains(item)) {
					counter++;
				}
			}
			itemcount.put(item, counter);
			counter = 0;
		}
		
		//System.out.println(itemcount.get("11888"));
		//System.out.println(aryItems.get(0)+", "+aryItems.get(1));
		System.out.println("L1 Generated");
	}
	
	public void generatepairs() {
		//int num = 33120025;
		
		
		boolean blnitemexists = false;
		
		for(int i = 0;i<(aryItems.size());i++) {
			//long starttime = System.currentTimeMillis();
			for(int c = 0;c<(aryItems.size());c++) {
					
				for(int j = 0;j<aryPairs.size();j++) {
					if((aryPairs.get(j).contains(aryItems.get(i)) && aryPairs.get(j).contains(aryItems.get(c)))) {
						blnitemexists = true;
						break;
					}
				}
				
				if(!blnitemexists && !(aryItems.get(i).equals(aryItems.get(c)))) {
					aryPairs.add(aryItems.get(i)+","+aryItems.get(c));
					//System.out.println(aryItems.get(i)+", "+aryItems.get(c));
				}
				blnitemexists = false;
			}
			//long stoptime = System.currentTimeMillis();
			//System.out.println(stoptime-starttime + " : position " + i);
		}
		
		System.out.println("C2 Generated");
	}
	
	public void countpairs() {
		int count = 0;
		boolean pairexists = false;
		
		for(String pair : aryPairs) {
			for(String basket : arybaskets) {
				String[] pairitems = pair.split(",");
				if(basket.contains(pairitems[0]) && basket.contains(pairitems[1])) {
					count++;
				}
			}
			paircount.put(pair, count);
			//System.out.println("Pair Count" + pair +" "+ count);
			count = 0;
		}
		
		Iterator pairit = paircount.entrySet().iterator();
		while(pairit.hasNext()) {
			Map.Entry<String, Integer> itempaircount = (Map.Entry)pairit.next();
			if(itempaircount.getValue().equals(0) | itempaircount.getValue().equals(1)) {
				pairit.remove();
			}
		}
		/*
		for(Map.Entry<String, Integer> pair : paircount.entrySet()) {
			System.out.println(pair.getKey() + " " + pair.getValue());
		}
		*/
		System.out.println("L2 Generated");
	}
	
	public void generateC3pairs() {
		int counter = 0;
		for(Map.Entry<String, Integer> pair : paircount.entrySet()) {
			String pairitems[] = pair.getKey().split(",");
			for(Map.Entry<String, Integer> pair2 : paircount.entrySet()) {
				String pair2items[] = pair2.getKey().split(",");
				if(pair2.getKey().contains(pairitems[0]) && !pair2.getKey().contains(pairitems[1])) {
					addc3pairs(pairitems,pair2items);
				}
				
				if(!pair2.getKey().contains(pairitems[0]) && pair2.getKey().contains(pairitems[1])) {
					addc3pairs(pairitems,pair2items);
					
					
				}
			}
		}
		
		System.out.println("C3 Generated");
	}
	
	public void addc3pairs(String pairitems[], String pair2items[]) {
		
		boolean ispresent = false;
		if(!pairc3.isEmpty()) {
			for(Map.Entry<String, Integer> c3pair : pairc3.entrySet()) {
				if(c3pair.getKey().contains(pairitems[0]) && c3pair.getKey().contains(pairitems[1]) && c3pair.getKey().contains(pair2items[0])) {
					ispresent = true;
				}
			}
		}
		
		if(!ispresent) {
			if(pairitems[0].equals(pair2items[1]) | pairitems[1].equals(pair2items[1])) {
				pairc3.put(pairitems[0] + "," + pairitems[1] + "," +pair2items[0], 0);
				//System.out.println(pairitems[0] + "," + pairitems[1] + "," +pair2items[0]);
			}else {
				pairc3.put(pairitems[0] + "," + pairitems[1] + "," +pair2items[1], 0);
				//System.out.println(pairitems[0] + "," + pairitems[1] + "," +pair2items[1]);
			}
		}
	}
	
	public void countc3pairs() {
		for(Map.Entry<String, Integer> pair : pairc3.entrySet()) {
			String[] c3item  = pair.getKey().split(",");
			for(String basket : arybaskets) {
				if(basket.contains(c3item[0]) && basket.contains(c3item[1]) && basket.contains(c3item[2])) {
					pair.setValue(pair.getValue()+1);
				}
			}
			//System.out.println(pair.getKey() + " - " + pair.getValue());
		}
		
		Iterator c3it = pairc3.entrySet().iterator();
		while(c3it.hasNext()) {
			Map.Entry<String, Integer> itemc3count = (Map.Entry)c3it.next();
			if(itemc3count.getValue().equals(0) | itemc3count.getValue().equals(1)) {
				c3it.remove();
			}
		}
		/*
		for(Map.Entry<String, Integer> pair : pairc3.entrySet()) {
			System.out.println(pair.getKey()+" "+pair.getValue());
		}
		*/
	}
	
	public void generatec4pairs() {
		for(Map.Entry<String, Integer> c3pairitem : pairc3.entrySet()) {
			for(Map.Entry<String, Integer> c3pairitem2 : pairc3.entrySet()) {
				String[] c3items = c3pairitem2.getKey().split(",");
				if(c3pairitem.getKey().contains(c3items[0]) && c3pairitem.getKey().contains(c3items[1]) | c3pairitem.getKey().contains(c3items[1]) && c3pairitem.getKey().contains(c3items[2]) | c3pairitem.getKey().contains(c3items[2]) && c3pairitem.getKey().contains(c3items[1])) {
					
				}
			}
		}
	}
	
	public void addc4pairs(Map.Entry<String, Integer> c3pairitem, String[] c3items2) {
		String[] c3items = c3pairitem.getKey().split(",");
		for(int i = 0; i<c3items2.length; i++) {
			if(!(c3items[0].contains(c3items2[i]) && c3items[1].contains(c3items2[i]) && c3items[2].contains(c3items2[i]))) {
				if(c3items[0].equals(c3items2[0]) && c3items[1].equals(c3items2[1])) {
					System.out.println(c3items[0] + " " + c3items[1] + " " +c3items[2] + " " +c3items2[i]);
				}else if(c3items[1].equals(c3items2[0]) && c3items[0].equals(c3items2[1])) {
					
				}else if(c3items[2].equals(c3items2[0]) && c3items[0].equals(c3items2[1])) {
					
				}
				for(int j = 0; j<c3items.length;j++) {
					if(c3items[j].equals(c3items2[0])){
						
					}
				}
			}
		}
		
	}
	
	public void confidence() {
		String confidence = "";
		writetofile("Association Rules",false);
		for(Map.Entry<String, Integer> c3pairitem : pairc3.entrySet()) {
			for(Map.Entry<String, Integer> c2pairitem : paircount.entrySet()) {
				confidence += associationc3nc2(c3pairitem, c2pairitem);
			}
			
			for(Map.Entry<String, Integer> c1item : itemcount.entrySet()) {
				confidence += associationc3nc1(c3pairitem,c1item);
			}
		}
		
		writetofile(confidence,true);
		System.out.println("Association Rules Written to file");
	}
	
	public String associationc3nc2(Map.Entry<String, Integer> c3pairitem,Map.Entry<String, Integer> c2pairitem) {
		String[] c3items = c3pairitem.getKey().split(",");
		String[] c2items = c2pairitem.getKey().split(",");
		
		double c3supcount = c3pairitem.getValue();
		double c2supcount = c2pairitem.getValue();
		
		int percentage = (int) ((c3supcount/c2supcount)*100);
		
		String AssociationRules = "";

		if(!(percentage<50))
			if(!(c2items[0].equals(c3items[0]) | c2items[1].equals(c3items[0]))) {
				AssociationRules = "["+c2items[0] + " ^ " +c2items[1] +"] => ["+c3items[0] + "] = " + percentage + System.lineSeparator();
				//System.out.println("["+c2items[0] + " ^ " +c2items[1] +"] => ["+c3items[0] + "] = " + (c3supcount/c2supcount)*100 + System.lineSeparator());
			}else if(!(c2items[0].equals(c3items[1]) | c2items[1].equals(c3items[1]))) {
				AssociationRules = "["+c2items[0] + " ^ " +c2items[1] +"] => ["+c3items[1]+ "] = " + percentage + System.lineSeparator();
				//System.out.println("["+c2items[0] + " ^ " +c2items[1] +"] => ["+c3items[1]+ "] = " + (c3supcount/c2supcount)*100 + System.lineSeparator());
			}else if(!(c2items[0].equals(c3items[2]) | c2items[1].equals(c3items[2]))) {
				AssociationRules = "["+c2items[0] + " ^ " +c2items[1] +"] => ["+c3items[2]+ "] = " + percentage + System.lineSeparator();
				//System.out.println("["+c2items[0] + " ^ " +c2items[1] +"] => ["+c3items[2]+ "] = " + (c3supcount/c2supcount)*100 + System.lineSeparator());
			}
		
		return AssociationRules;
	}
	
	public String associationc3nc1(Map.Entry<String, Integer> c3pairitem,Map.Entry<String, Integer> c1item) {
		String[] c3items = c3pairitem.getKey().split(",");
		
		double c3supcount = c3pairitem.getValue();
		double c1supcount = c1item.getValue();
		
		int percentage = (int) ((c3supcount/c1supcount)*100);
		
		String AssociationRules = "";
		
		if(!(percentage<50))
			if(c3items[0].equals(c1item.getKey())) {
				AssociationRules = "["+c1item.getKey()+"] => ["+c3items[1]+","+c3items[2] + "] = " + percentage + System.lineSeparator();
				//System.out.println("["+c1item.getKey()+"] => ["+c3items[1]+","+c3items[2] + "] = " + (c3supcount/c1supcount)*100)+System.lineSeparator();
			}else if(c3items[1].equals(c1item.getKey())) {
				AssociationRules = "["+c1item.getKey()+"] => ["+c3items[0]+","+c3items[2] + "] = " + percentage + System.lineSeparator();
				//System.out.println("["+c1item.getKey()+"] => ["+c3items[0]+","+c3items[2] + "] = " + (c3supcount/c1supcount)*100);
			}else if(c3items[2].equals(c1item.getKey())) {
				AssociationRules = "["+c1item.getKey()+"] => ["+c3items[0]+","+c3items[1] + "] = " + percentage + System.lineSeparator();
				//System.out.println("["+c1item.getKey()+"] => ["+c3items[0]+","+c3items[1] + "] = " + (c3supcount/c1supcount)*100);
			}
		
		return AssociationRules;
	}
	
	public void writetofile(String associationRule, boolean append) {
		try {
			FileWriter writer = new FileWriter("Out.txt",append);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(associationRule);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
            writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

