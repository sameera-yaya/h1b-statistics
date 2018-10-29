package h1b_stats;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class h1b_stats {
	public static void main(String[] args) throws IOException {
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
				if(current.charAt(current.length()-1)== '"') {
					current=current.substring(1,current.length()-1);
				}
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
		
		Enumeration<String> temp=occupations.keys();
		ArrayList<String> temp1= new ArrayList<String>();
		ArrayList<Integer>temp2= new ArrayList<Integer>(occupations.values());
		Hashtable<Integer, Integer> temp3= new Hashtable<Integer, Integer>();
		for(int i=0; i < temp2.size(); i++) {
			if (temp3.containsKey(temp2.get(i))) {
				temp3.put(temp2.get(i), temp3.get(temp2.get(i))+1);
			}
			else
				temp3.put(temp2.get(i), 1);
		}
		Enumeration<Integer> temp4=temp3.keys();
		ArrayList<Integer> temp5= new ArrayList<Integer>();
		while(temp.hasMoreElements()) {
			temp1.add(temp.nextElement());
		}
		while(temp4.hasMoreElements()) {
			temp5.add(temp4.nextElement());
		}
		//System.out.println(temp1);
		//System.out.println(temp2);
		//System.out.println(temp1.get(2));
		//System.out.println(temp2.get(2));
		
		Collections.sort(temp2,Collections.reverseOrder());
		//System.out.println(temp2);
		
		
		String top10_occupations = "top_10_occupations.txt";
		FileWriter file1=new FileWriter(top10_occupations, true);
		try {
			BufferedWriter outputStream = new BufferedWriter(file1);
			outputStream.write("TOP_OCCUPATIONS;NUMBER_CERTIFIED_APPLICATIONS;PERCENTAGE");
			outputStream.newLine();
			for(int i= 0; i < temp5.size(); i++) {
				ArrayList<String> keys = new ArrayList<String>();
				for (String entry : temp1) {
					if(temp5.get(i)== occupations.get(entry)) {
						//System.out.println(temp5.get(i));
						//System.out.println(listoflists.size()-1);
						keys.add(entry);
					}
				}
				Collections.sort(keys);
				for(int j= 0; j < keys.size(); j++) {
					double num=temp5.get(i);
					double denom=listoflists.size()-1;
					double percent=(num/denom)*100;
					outputStream.write(keys.get(j) + ';' + temp5.get(i) + ';' + percent +"%");
					outputStream.newLine();

				}
			}
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		temp=states.keys();
		temp1.clear();
		temp2= new ArrayList<Integer>(states.values());
		for(int i=0; i < temp2.size(); i++) {
			if (temp3.containsKey(temp2.get(i))) {
				temp3.put(temp2.get(i), temp3.get(temp2.get(i))+1);
			}
			else
				temp3.put(temp2.get(i), 1);
		}
		temp4=temp3.keys();
		temp5.clear();
		while(temp.hasMoreElements()) {
			temp1.add(temp.nextElement());
		}
		while(temp4.hasMoreElements()) {
			temp5.add(temp4.nextElement());
		}
		//System.out.println(temp1);
		//System.out.println(temp2);
		//System.out.println(temp1.get(2));
		//System.out.println(temp2.get(2));
		
		Collections.sort(temp2,Collections.reverseOrder());
		//System.out.println(temp2);
		
		
		
		
		String top10_states = "top_10_states.txt";
		try {
			BufferedWriter outputStream = new BufferedWriter(new FileWriter(top10_states, true));
			outputStream.write("TOP_STATES;NUMBER_CERTIFIED_APPLICATIONS;PERCENTAGE");
			outputStream.newLine();
			for(int i= 0; i < temp5.size(); i++) {
				ArrayList<String> keys = new ArrayList<String>();
				for (String entry : temp1) {
					if(temp5.get(i)== states.get(entry)) {
						//System.out.println(temp5.get(i));
						//System.out.println(listoflists.size()-1);
						keys.add(entry);
					}
				}
				Collections.sort(keys);
				for(int j= 0; j < keys.size(); j++) {
					double num=temp5.get(i);
					double denom=listoflists.size()-1;
					double percent=(num/denom)*100;
					outputStream.write(keys.get(j) + ';' + temp5.get(i) + ';' + percent +"%");
					outputStream.newLine();
		
				}
			}
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
	}
}
