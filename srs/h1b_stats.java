package h1b_stats;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;

public class h1b_stats {
	public static void main(String[] args) throws IOException {
		String fileName = "h1b_input.csv";
		FileReader file = new FileReader(fileName);
		String line;
		ArrayList<ArrayList<String>> listoflists = new ArrayList<ArrayList<String>>(); //list of columns of the data given
		String [] data; // string array used for split()
		String current; // string variable used when inserting into hash table
		try {
			//read the file given
			BufferedReader inputStream = new BufferedReader(file);
			
			while((line = inputStream.readLine())!= null) {
				data = line.split(";");
				ArrayList<String> list = new ArrayList<String>(); // list of the column elements
				for (int i=0; i<data.length; i++) {
					list.add(data[i]); // converting the string array into an array list
				}
				// check to see if any of lines end in ';'
				// if they do, then it means that there needs to be another empty element at the end
				// if they do not, then there is no empty element at the end
				if(line.charAt(line.length()-1) == ';')
					list.add(""); // empty element added to the list
				listoflists.add(list); // list of elements added to the listoflists essentially creating a 2D array
			}
			// closing the input of the file after reading is done
			inputStream.close();
		} catch (IOException e) {
			// if there is an error with input file, then throw an exception
			e.printStackTrace();
		}
		
		int index=0; // index used to find the column of data needed as each year is different
		for (int i=0; i < listoflists.get(0).size(); i++) {
			// if "SOC_NAME" is found then that is the index used to find the data for all the applications given
			if(listoflists.get(0).get(i).equals("SOC_NAME")) {
				index=i;
			}
		}
		Hashtable<String, Integer> occupations = new Hashtable<String, Integer>(); // hash table used to organize the occupations and the number of them
		for ( int i= 1; i < listoflists.size(); i++) {
			// check to see if the application is certified
			if(listoflists.get(i).get(2).equals("CERTIFIED")) {
				current = listoflists.get(i).get(index); // get each element in the column of SOC_NAME
				if(current.charAt(current.length()-1)== '"') {
					// check to see if the element has " " around it
					// if so, do not input that into the hash table (get a substring)
					current=current.substring(1,current.length()-1);
				}
				// check to see if the hash table already contains the key
				if (occupations.containsKey(current)){
					// if it does then update the count value and insert back into hash table
					int count = occupations.get(current)+1;
					occupations.put(current,count);
				}
				else {
					// if it is not already in the hash table, insert it with count value 1
					occupations.put(current, 1);
				}
			}
		}
		
		//System.out.println(occupations);
		
		for (int i=0; i < listoflists.get(0).size(); i++) {
			// if "WORKSITE_STATE" is found then that is the index used to find the data for all the applications given
			if(listoflists.get(0).get(i).equals("WORKSITE_STATE")) {
				index=i;
			}
		}
		
		Hashtable<String, Integer> states = new Hashtable<String, Integer>(); // hash table used to organize the states and the number of them
		for ( int i= 1; i < listoflists.size(); i++) {
			// check to see if the application is certified
			if(listoflists.get(i).get(2).equals("CERTIFIED")) {
				current = listoflists.get(i).get(index); // get each element in the column of WORKSITE_STATE
				// check to see if the hash table already contains the key
				if (states.containsKey(current)){
					// if it does then update the count value and insert back into hash table
					int count = states.get(current)+1;
					states.put(current,count);	
				}
				else {
					// if it is not already in the hash table, insert it with count value 1
					states.put(current, 1);
				}
			}
		}
		
		//System.out.println(states);
		
		Enumeration<String> job_titles=occupations.keys(); // get all the various job titles in occupations
		ArrayList<String> job_array= new ArrayList<String>(); //array list of the job titles
		ArrayList<Integer>job_titles_num= new ArrayList<Integer>(occupations.values()); // array list of the number of each job title
		Hashtable<Integer, Integer> job_titles_num_hash= new Hashtable<Integer, Integer>(); // hash table to organize the number of each job title as the key
		for(int i=0; i < job_titles_num.size(); i++) {
			// check to see if the hash table already has the occupation value
			if (job_titles_num_hash.containsKey(job_titles_num.get(i))) {
				// if it does then update the count value
				job_titles_num_hash.put(job_titles_num.get(i), job_titles_num_hash.get(job_titles_num.get(i))+1);
			}
			else
				// if it does not then insert with count value 1
				job_titles_num_hash.put(job_titles_num.get(i), 1);
		}
		Enumeration<Integer> num_occupations_keys=job_titles_num_hash.keys(); // get all the keys of the number of each job title
		ArrayList<Integer> occupations_keys_array= new ArrayList<Integer>(); // array list of all the keys of the number of each job title
		// convert enumerations into array lists
		while(job_titles.hasMoreElements()) {
			job_array.add(job_titles.nextElement());
		}
		while(num_occupations_keys.hasMoreElements()) {
			occupations_keys_array.add(num_occupations_keys.nextElement());
		}
		
		// sort the number of each job title in descending order
		Collections.sort(job_titles_num,Collections.reverseOrder());
		
		// open a file named "top_10_occupations.txt" to write into
		String top10_occupations = "top_10_occupations.txt";
		FileWriter file1=new FileWriter(top10_occupations, true);
		try {
			BufferedWriter outputStream = new BufferedWriter(file1);
			outputStream.write("TOP_OCCUPATIONS;NUMBER_CERTIFIED_APPLICATIONS;PERCENTAGE"); // output the titles of each column
			outputStream.newLine();
			for(int i= 0; i < occupations_keys_array.size(); i++) {
				ArrayList<String> keys = new ArrayList<String>(); // array list used to keep track of the job titles for each count
				// for every entry in job_array, if the count is the same then add to the list
				for (String entry : job_array) {
					if(occupations_keys_array.get(i)== occupations.get(entry)) {
						keys.add(entry);
					}
				}
				Collections.sort(keys); // sort the values of the in descending order
				// for all the keys which are the job titles get the number of each
				for(int j= 0; j < keys.size(); j++) {
					double num=occupations_keys_array.get(i);
					double denom=listoflists.size()-1;
					double percent=((num/denom)*100); // get the percentage of each job title
					BigDecimal bd1=new BigDecimal (percent);
					bd1=bd1.setScale(1, RoundingMode.HALF_DOWN);
					outputStream.write(keys.get(j) + ';' + occupations_keys_array.get(i) + ';' + bd1 +"%"); // output into file
					outputStream.newLine();
				}
			}
			// close output
			outputStream.close();
		} catch (IOException e) {
			// if there is an error with the file output then throw an exception
			e.printStackTrace();
		}
		
		Enumeration<String> work_states=states.keys(); // get all the various states in states
		ArrayList<String> states_array= new ArrayList<String>(); //array list of the states
		ArrayList<Integer>states_num= new ArrayList<Integer>(states.values()); // array list of the number of each state
		Hashtable<Integer, Integer> states_num_hash= new Hashtable<Integer, Integer>(); // hash table to organize the number of each state as the key
		
		for(int i=0; i < states_num.size(); i++) {
			if (states_num_hash.containsKey(states_num.get(i))) {
				states_num_hash.put(states_num.get(i), states_num_hash.get(states_num.get(i))+1);
			}
			else
				states_num_hash.put(states_num.get(i), 1);
		}
		Enumeration<Integer> num_states_keys=states_num_hash.keys(); // get all the keys of the number of each job title
		ArrayList<Integer> states_keys_array= new ArrayList<Integer>(); // array list of all the keys of the number of each job title
		// convert enumerations into array lists
		while(work_states.hasMoreElements()) {
			states_array.add(work_states.nextElement());
		}
		while(num_states_keys.hasMoreElements()) {
			states_keys_array.add(num_states_keys.nextElement());
		}
		
		// sort the number of each state in descending order
		Collections.sort(states_num,Collections.reverseOrder());
		
		// open a file named "top_10_states.txt" to write into
		String top10_states = "top_10_states.txt";
		try {
			BufferedWriter outputStream = new BufferedWriter(new FileWriter(top10_states, true));
			outputStream.write("TOP_STATES;NUMBER_CERTIFIED_APPLICATIONS;PERCENTAGE"); // output the titles of each column
			outputStream.newLine();
		
			for(int i= 0; i < states_keys_array.size(); i++) {
				ArrayList<String> keys = new ArrayList<String>(); // array list used to keep track of the states for each count
				// for every entry in states_array, if the count is the same then add to the list
				for (String entry : states_array) {
					if(states_keys_array.get(i)== states.get(entry)) {
						keys.add(entry);
					}
				}
				Collections.sort(keys); // sort the values of the in descending order
				// for all the keys which are the states get the number of each
				for(int j= 0; j < keys.size(); j++) {
					double num=states_keys_array.get(i);
					double denom=listoflists.size()-1;
					double percent=(num/denom)*100;
					BigDecimal bd2=new BigDecimal (percent); // get the percentage of each state
					bd2=bd2.setScale(1, RoundingMode.HALF_DOWN);
					outputStream.write(keys.get(j) + ';' + states_keys_array.get(i) + ';' + bd2 +"%"); // output into file
					outputStream.newLine();
				}
			}
			// close output
			outputStream.close();
		} catch (IOException e) {
			// if there is an error with the file output then throw an exception
			e.printStackTrace();
		}
	}
}
