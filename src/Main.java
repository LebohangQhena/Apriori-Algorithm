import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		Apriori apriori = new Apriori();
		apriori.ReadFile("who_rated_what_2006_baskets_singles_-_Copy.txt");
		apriori.countItems();
		apriori.generatepairs();
		apriori.countpairs();
		apriori.generateC3pairs();
		apriori.countc3pairs();
		apriori.confidence();
		*/
		
		ArrayList<Integer> a = new ArrayList();
		a.add(1);
		a.add(5);
		a.add(7);
		a.add(7);
		
		ArrayList<Integer> b = new ArrayList();
		b.add(0);
		b.add(1);
		b.add(2);
		b.add(3);
		
		ArrayList newlist = new ArrayList();
		
		
		for(int i = 0;i<a.size();i++){
	        for(int c = 0;c<b.size();c++){
	        	
	            if(b.get(c) < a.get(i) ){
	                newlist.add(b.get(c));
	                System.out.println(b.get(c)+"<-- b");
	                b.remove(c);
	                System.out.println(b + " Size "+c);
	            }else{
	                newlist.add(a.get(i));
	                //System.out.println(a.get(i)+"<-- a");
	                a.remove(i);
	                break;
	            }
	            c=0;
	        }
	    }
	    
			for(int i = 0; i<a.size(); i++) {
				newlist.add(a.get(i));
				a.remove(i);
			}
		
		System.out.println(newlist);
	}

}
