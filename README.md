# H1B Statistics
Sameera Yayavaram
Written in Java using Eclipse IDE

## Problem
The US Department of Labor and its Office of Foreign Labor Certification Performance Data have some statistics available of immigration data trends on H1B visa applications. A program should be created to analyze past years data and calculate the top 10 occupations and top 10 states for certified visa applications.

#### Input:
Data in the form of a csv file
#### Output:
Two text files (top_10_occupations.txt, top_10_states.txt)

## Approach
The program reads the file line by line and inserts the data into a ArrayList of ArrayLists essentially creating a 2D array so that the column and other application information is not lost. Using the ArrayList of ArrayLists, a hash table is created using only the certified applications in the "SOC_NAME" and "WORKSITE_STATE" columns. 

Using Enumerations and ArrayLists and Hashtables, the data is sorted while still keeping the key-value pairs from the original hashtable. The name of the occupation/state and the number of applications with the occupation/state along with the percentage of the entire data set is printed in the respective text files. 

## Run
The program was written in Java using Eclipse IDE in Windows OS. The program is not at its most optimized state but the directory structure and format are correct. 
