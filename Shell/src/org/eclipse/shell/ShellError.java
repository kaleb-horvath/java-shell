
package org.eclipse.shell;

public class ShellError {
	
	private String errorString;				// final String that is printed
	private String errorMessage;			// message passed from caller, formatted into errorString
	private String className;				// ShellError
	private String offendingClassName;		// Source of error
	private Exception exception;			// optional 
	private boolean exit;					// fatal error?

	
	public ShellError (String message, String offendingClass, Exception exception, boolean exit)
	{
		this.offendingClassName = offendingClass;
		this.className = this.getClass().getCanonicalName();
		this.exit = exit;
		this.exception = exception;
		this.errorString = "[!] %s: From %s: %s\n";
		
		// handle the case of no custom message passed
		if (message == null)
		{
			this.errorMessage = "";
		}
		else {
			this.errorMessage = message;
		}
		
		/**if (this.exception != null)
			// handle the case of an exception, should we stack trace?*/
	}
	
	@Override
	public String toString ()
	{
		this.errorString = String.format(this.errorString, 
				this.className,
				this.offendingClassName,
				this.errorMessage);
		
		return errorString;
	}
	
	public void call ()
	{
		System.out.print(this.toString());
		if (exit)
			System.exit(-1);
	}

}
