
package org.eclipse.command;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.shell.ShellError;

/**
 * Responsible for tokenizing a command and verifying grammars.
 * 
 * 
 * TODO:
 * - How do we get command?
 * - Tokenize a command using Regexp 
 */
public class CommandLexer {

	private boolean passing;
	
	private String command;					// 'input'
	private ArrayList<String> tokens;		// 'output'
	private Matcher matcher;				// recompiles a grammar on each pass
	private StringTokenizer tokenizer;		// only runs once 
	private ArrayList<String> matches;
	
	private final String commandGrammar = "^(\\S+(\\s+|[\\t\\n]+))*\\S+$";
	private final String tokenGrammar = "[^\\ \"]*|[+-]?[0-9]+(?:\\\\.[0-9]+)(?:[eE]-?[0-9]+)?|&|>|\\|\\b\\w+\\b";		// no string no escape etc.
	//private final String pipeGrammar = "";
	//private final String outputRedirectGrammar = "";
	//private final String stringLiteralGrammar = "";
	
	public CommandLexer (String readerString)
	{
		this.command = readerString;
		this.matches = new ArrayList<String>();
		this.tokens = new ArrayList<String>();
	}
		
	public ArrayList<String> getTokens ()
	{
		if (passing)
			return tokens;
		
		return new ArrayList<String>();
	}
	 
	public void lex ()
	{
		if (!initialPass())
		{
			
			(new ShellError(
					"Command string does not match expected grammar",
					(String) this.getClass().getCanonicalName(),
					null,
					true)).call();
		}
		else
		{
			System.out.println("Passed initial pass.");
			tokenizeCommand();
			if (tokens.size() == 0)
			{
				System.out.println("No tokens to match.");
			}
			else 
			{
				if (!finalPass())
				{
					// this is why I did not remove any tokens in finalPass()
					// does this break for duplicate tokens?
					// perhaps using a data structure that stores token nodes is better suited to robust feedback
					// secondly, this only reports first occurrence of bad token grammar
					for (String token : tokens)
					{
						if (matches.contains(token))
							continue;
						
						System.out.println(String.format("Token \'%s\' at %d does not match expected grammar.",
								token, tokens.indexOf(token)));   
					}
					System.out.println("Failed final pass but not due to any specific token.");
				} 
				else
				{
					this.passing = true;
					System.out.println("Passed final pass.");
				}
			}
		}
	}
	
	private boolean initialPass ()
	{
		boolean pass;
		this.matcher = Pattern.compile(commandGrammar).matcher(command);
		
		// perform command pattern matching with Matcher object 
		while (matcher.find())
		{
			this.matches.add(matcher.group(0));
		}
		
		pass = matches.size() >= 1;
	

		return pass;
	}
	
	private boolean finalPass ()
	{	
		boolean pass;
		this.matcher = Pattern.compile(tokenGrammar).matcher(tokens.get(0));
		this.matches = new ArrayList<String>();
		
		int n = tokens.size();
		
		// perform token pattern matching with Matcher object 
		for (short i = 1; i < n; i++)
		{
			if (matcher.find())
			{
				this.matches.add(matcher.group(0));
			}
			this.matcher.reset(tokens.get(i));
		}
		
		pass = (matches.size() == (n - 1));
		
		System.out.println(String.format("%d %d", matches.size(), n - 1));
		
		return pass;
	}
	
	private void tokenizeCommand ()
	{
		String command = matches.get(0);
		this.tokenizer = new StringTokenizer(command);
		
		while (tokenizer.hasMoreTokens())
		{
			this.tokens.add(tokenizer.nextToken());
		}
		/**
		 *  String[] tokens = command.split("[ \t\n]+");
		 *  // convert to ArrayList here
		 */
	}
}
