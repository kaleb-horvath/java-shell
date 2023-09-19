
package org.eclipse.shell;

public class Driver {
	
	public static void main (String[] args)
	{
		CommandReader reader = new CommandReader();
		reader.readLoop(">");
	}
}
