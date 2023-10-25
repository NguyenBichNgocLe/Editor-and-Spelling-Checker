package uwf.project2;
/***************************************************************
Student Name: Ari Le
File Name: Dictionary.java
Assignment number: 2

A Dictionary class to create a dictionary from dictionary.txt
and methods to check if a word is in the dictionary
***************************************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Dictionary {
	
	private HashSet<String> dictionary;
	private Scanner input;
	private File file;
	
	/*
	 * Constructor
	 */
	public Dictionary()
	{
		dictionary = new HashSet<String>();
		file = new File("dictionary.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
     * Method to add the words in dictionary.txt into hashSet dictionary
     */
	public void loadInDictionary()
    {
		while(input.hasNext())
		{
			String data = input.next();
			dictionary.add(data);
		}	
    }
	
	 /*
     * Method to check if the dictionary has the string
     * @param str
     * @return true or false
     */
    public boolean isContained(String str)
    {
    	str = str.toLowerCase();
    	for(String i: dictionary)
    	{
    		String tmp = i;
    		tmp = tmp.toLowerCase();
    		if(tmp.equals(str))
    			return true;
    	}
    	return false;
    }
	
}
