
package org.eclipse.shell;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.regex.Pattern;

import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Responsible for reading simple shell commands as Strings into our program.
 * 
 * NOTE: For now, it contains the read-eval-print loop and will orchestrate 
 * other functionality such as tokenization, parsing, and evaluation.
 * 
 * TODO:
 * - Implement content method with StringBuilder
 *  
 * 
 * @author kaleb-horvath
 * @license GPLv3
 * @email kalebhorvath23@gmail.com
 * @version 0.0.1
 */

public class CommandReader {
	
	private boolean isRepl;
	private boolean running;
	
	private Reader stdin;
	private BufferedReader bufferedReader;
	private FileReader fileReader;
	private File file;
	private ArrayList<String> lines;
	
	private StringBuilder content;
	
	
	// default constructor requires no arguments (REPL case)
	public CommandReader ()
	{
		isRepl = true; 
		
		// allocate necessary objects
		stdin = new InputStreamReader(System.in);
		bufferedReader = new BufferedReader(stdin);
		content = new StringBuilder();
	}
	
	// secondary constructor (file case)
	public CommandReader (String filePath)
	{
		// allocate necessary objects
		bufferedReader = new BufferedReader(stdin);
		
		// check file
		file = new File(filePath);
		if (file.exists() && !file.isDirectory()) 
		{
			readFile(file);
			isRepl = false;
			file = null; 	// mark for GC
		} 
		else 
		{
			System.out.println("File path not found or not a file.");
			System.exit(-1);
		}
	}
	
	
	public ArrayList<String> getLines()
	{
		if (!isRepl)
			return lines;
		else
			// is this fault tolerance necessary?
			System.out.println("This Reader is a REPL. Returning empty List.");
			return new ArrayList<String>();
	}
	

	public void readLoop (String prompt)
	{
		running = true;
		
		try {
			String str;
			
			while (running) 
			{
				// simple echo 
				System.out.print(prompt + " ");
				str = bufferedReader.readLine();
				
				if (str == null) 
				{
					// will catch EOF even if 'Shell' is not interactive
					running = false; 
				}
				/**
				 * Instead of simply echoing, lets put the str somewhere 
				 */
				content.append(str);
				System.out.println("--> " + str);
				// X.tokenize(str)...
				// i.eval(tokens); .. or something
			}
			
		} catch (Exception e) {
			System.out.println("Problem opening stdin for reading.");
		}
	}
	
	
	private void readFile (File file) 
	{
		String filePath = file.getPath();
		
		try {
			fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);	
			
			String line = bufferedReader.readLine();
			while (line != null)
			{
				lines.add(line);
				line = bufferedReader.readLine();
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			System.out.println("Problem opening file for reading.");
			System.exit(-1);
		}
	}
}
