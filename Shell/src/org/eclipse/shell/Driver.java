
package org.eclipse.shell;

public class Driver {
	
	public static void main (String[] args)
	{
		// CommandReader reader = new CommandReader();
		// reader.readLineLoop(">");
	
		CommandLexer lexer = new CommandLexer("echo world | grep world");
		lexer.lex();
		
		for (String token : lexer.getTokens())
		{
			System.out.println(token);
		}
	}
}
