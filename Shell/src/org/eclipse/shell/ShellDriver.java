
package org.eclipse.shell;

import org.eclipse.command.CommandLexerDepricated;
import org.eclipse.command.CommandReader;

public class ShellDriver {
	
	public static void main (String[] args)
	{
		CommandReader reader = new CommandReader("/home/chairs/workspace/personal-projects/eclipse-workspace/java-shell/Shell/src/org/eclipse/shell/test.script");
		System.out.println(reader.getLines());
		
		/**CommandLexerDepricated lexer = new CommandLexerDepricated("0-");
		lexer.lex();
		
		for (String token : lexer.getTokens())
		{
			System.out.println(token);
		}*/
	}
}
