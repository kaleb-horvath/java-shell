
package org.eclipse.shell;

import org.eclipse.command.CommandLexer;

public class ShellDriver {
	
	public static void main (String[] args)
	{
		// CommandReader reader = new CommandReader();
		// reader.readLineLoop(">");
	
		CommandLexer lexer = new CommandLexer("echo--world--yes");
		lexer.lex();
		
		for (String token : lexer.getTokens())
		{
			System.out.println(token);
		}
	}
}
