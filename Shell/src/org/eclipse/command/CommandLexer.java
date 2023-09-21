
package org.eclipse.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
	
	private boolean running;
	
	private String command;
	private ArrayList<String> tokens;		// 'output'
	private Matcher matcher;				// recompiles a grammar on each pass
	private StringTokenizer tokenizer;		// only runs once? needed?
	private ArrayList<String> matches;		// assists matcher on each pass to store match objects
	
	private Map<String, String> grammarPatterns;
 	
	
	public CommandLexer (String readerString)
	{
		this.command = readerString;
		this.matches = new ArrayList<String>();
		this.tokens = new ArrayList<String>();
		
		// load necessary grammars
		/**
		 * The string must begin or end with one or more non-whitespace characters.
		 * Between the start and end characters, there can be one or more of
		 * One or more whitespace characters
		 * One or more tabs or newline characters.
		 * Using a group, start/end assertion, and multiple occurrences operator.
		 * 
		 * NOTE: Ensures no empty whitespace commands and a typical shell command grammar.
		 */
		this.grammarPatterns.put("command-grammar", "^(\\\\S+(\\\\s+|[\\\\t\\\\n]+))*\\\\S+$");
		
		/**
		 * Possible token grammars:
		 * 
		 * token-grammar-program (either an executable or its parameters, this is words/numbers/characters/etc.)
		 * token-grammar-string	(non-escaped opening quotes or end quotes positive look-ahead assertion)
		 * token-grammar-brackets (non-escaped opening brackets or closed brackets positive look-ahead assertion)
		 * token-grammar-sync (non-escaped semicolon, Shell operator) [separates the logical shell line, which is a queue entry in our design ]
		 * token-grammar-async (non-escaped ampersand, Shell operator) [separates the logical shell line, which is a queue entry in our design ]
		 * token-grammar-redirect (output redirection, Shell operator)
		 * token-grammar-pipe (input direction, Shell operator)
		 * 
		 * NOTE: Do we take the Regex optional OR/keep-looking approach?
		 * NOTE: If a token does not match one of these patterns, throw an error!
		 * NOTE: If a token does match the string or parens pattern, but doesnt terminate, throw an error!
		 * NOTE: If a token matches an operator pattern but there are no following tokens, throw an error!
		 * NOTE: Once a string is recognized the tokens will be concatenated and made into one. At this point it becomes a program token.
		 */
		
	}
		
}