
package org.eclipse.command;

import java.util.ArrayList;

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
 */
public class CommandReader {
	
	private boolean isRepl;
	private boolean reading;
	private Reader stdin;
	private BufferedReader bufferedReader;
	private FileReader fileReader;
	private File file;
	private ArrayList<String> lines;
	private StringBuilder content;
	
	
	// default constructor requires no arguments (REPL case)
	CommandReader ()
	{
		this.isRepl = true; 
		
		// allocate necessary objects
		this.stdin = new InputStreamReader(System.in);
		this.bufferedReader = new BufferedReader(stdin);
		this.content = new StringBuilder();
	}
	
	// secondary constructor (file case)
	CommandReader (String filePath)
	{
		// allocate necessary objects
		this.bufferedReader = new BufferedReader(stdin);
		
		// check file
		this.file = new File(filePath);
		if (file.exists() && !file.isDirectory()) 
		{
			readFile(file);
			this.isRepl = false;
			this.file = null; 	// mark for GC
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
	

	public void readLineLoop (String prompt)
	{
		this.reading = true;
		
		try {
			String str;
			
			while (reading) 
			{
				// simple echo 
				System.out.print(prompt + " ");
				str = bufferedReader.readLine();
				
				if (str == null) 
				{
					// will catch EOF even if 'Shell' is not interactive
					this.reading = false; 
				}
				/**
				 * Instead of simply echoing, lets put the str somewhere 
				 */
				this.content.append(str);
				System.out.println("--> " + content);
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
			this.fileReader = new FileReader(filePath);
			this.bufferedReader = new BufferedReader(fileReader);	
			
			String line = bufferedReader.readLine();
			while (line != null)
			{
				this.lines.add(line);
				line = bufferedReader.readLine();
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			System.out.println("Problem opening file for reading.");
			System.exit(-1);
		}
	}
}
