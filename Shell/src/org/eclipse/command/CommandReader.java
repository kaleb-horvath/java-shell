
package org.eclipse.command;

import java.util.ArrayList;

import org.eclipse.shell.ShellError;

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
		this.isRepl = true; 
		this.stdin = new InputStreamReader(System.in);
		this.bufferedReader = new BufferedReader(stdin);
		this.content = new StringBuilder();
	}
	

	public CommandReader (String filePath)
	{
		this.stdin = new InputStreamReader(System.in);
		this.bufferedReader = new BufferedReader(stdin);
		this.lines = new ArrayList<>();

		// check file
		this.file = new File(filePath);
		if (file.exists() && !file.isDirectory()) 
		{
			System.out.println("Reading file");
			readFile(file);
			this.isRepl = false;
		} 
		else 
		{
			(new ShellError(
					String.format("File path not found or not a file \'%s\'", filePath),
					(String) this.getClass().getCanonicalName(),
					null, true)).call();
		}
	}
	
	
	public ArrayList<String> getLines()
	{
		if (!isRepl)
			return lines;
		else
			// is this fault tolerance necessary?
			return new ArrayList<String>();
	}
	

	public void readLineLoop (String prompt)
	{
		this.running = true;
		
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
					this.running = false; 
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
			(new ShellError(
					"Problem opening stdin for reading",
					(String) this.getClass().getCanonicalName(),
					e, true)).call();
		}
	}
	
	
	private void readFile (File file) 
	{
		this.running = true;
		String filePath = file.getPath();
		
		try {
			this.fileReader = new FileReader(filePath);
			this.bufferedReader = new BufferedReader(fileReader);	
			
			String line = bufferedReader.readLine();
			while (running)
			{
				if (line != null)
				{
					this.lines.add(line);
					line = bufferedReader.readLine();
				}
				else {
					this.running = false;
				}

			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			(new ShellError(
					"Problem opening file for reading. Check permissions.",
					(String) this.getClass().getCanonicalName(),
					e, true)).call();
		}
	}
}

