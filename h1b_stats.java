package h1b_stats;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class h1b_stats {
	public static void main(String[] args) throws FileNotFoundException {
		String fileName = "Book1.csv";
		FileReader file = new FileReader(fileName);
		String line;
		ArrayList<ArrayList<String>> listoflists = new ArrayList<ArrayList<String>>();
		//String [] values = null;
		//ArrayList<String> values = new ArrayList<String>();
		String [] data;
		//ArrayList<String> list = new ArrayList<String>();
		String current;
		try {
			BufferedReader inputStream = new BufferedReader(file);
			//String line;
			//ArrayList<ArrayList<String>> listoflists = new ArrayList<ArrayList<String>>();
			//String [] values = null;
			//ArrayList<String> values = new ArrayList<String>();
			//String [] data;
			//ArrayList<String> list = new ArrayList<String>();
			//String current;
			/*String data = inputStream.readLine();
			//String[] values = data.split(";", 53);
			System.out.println(data);
			//System.out.println(values[52]);
			data = inputStream.readLine();
			System.out.println(data);
			//values = data.split(";", 53);
			//System.out.println(data);
			//System.out.println(values[1]);*/
			while((line = inputStream.readLine())!= null) {
				//String data = inputStream.readLine();
				//System.out.println(line);
				data = line.split(";");
				//System.out.println(values[4]);
				//System.out.println(line);
				//list.clear();
				ArrayList<String> list = new ArrayList<String>();

				for (int i=0; i<data.length; i++) {
					list.add(data[i]);
					//System.out.println(list.get(i));
				}
				if(line.charAt(line.length()-1) == ';')
					list.add("");
				listoflists.add(list);
				for(int i=0; i < list.size(); i++)
				{
					//System.out.println(listoflists.get(listoflists.size()-1).get(i));
				}
				//System.out.println("added");
				//System.out.println(values[22]);
			}
			
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0; i< listoflists.size(); i++){
			for (int j=0; j< listoflists.get(0).size(); j++) {
				//System.out.println(listoflists.get(i).get(j));
				//System.out.println(listoflists.get(1).get(j));
			}
		}
		//System.out.println(listoflists.get(listoflists.size()-5).get(listoflists.get(0).size()-2));
		//System.out.println(listoflists.size());
		//System.out.println(listoflists.get(0).size());
		//System.out.println(listoflists.get(5).get(24));
		//System.out.println(listoflists.get(8).get(24));
		//System.out.println(listoflists.get(0)[5]);
		//System.out.println(values[22]);
		
		Hashtable<String, Integer> occupations = new Hashtable<String, Integer>();
		for ( int i= 1; i < listoflists.size(); i++) {
			//System.out.println(listoflists.get(i).get(2));
			if(listoflists.get(i).get(2).equals("CERTIFIED")) {
				current = listoflists.get(i).get(24);
				//System.out.println(current);
				if (occupations.containsKey(current)){
					int count = occupations.get(current)+1;
					occupations.put(current,count);
					
				}
				else {
					occupations.put(current, 1);
				}
				//System.out.println("done");
			}
		}
		
		System.out.println(occupations);
		
		
		Hashtable<String, Integer> states = new Hashtable<String, Integer>();
		for ( int i= 1; i < listoflists.size(); i++) {
			if(listoflists.get(i).get(2).equals("CERTIFIED")) {
				current = listoflists.get(i).get(50);
				//System.out.println(current);
				if (states.containsKey(current)){
					int count = states.get(current)+1;
					states.put(current,count);
					
				}
				else {
					states.put(current, 1);
				}
				//System.out.println("done");
			}
		}
		
		System.out.println(states);
		
	}
}
