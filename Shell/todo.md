

### Ideas
* data structure to hold Token nodes so that robust error handling and feedback is possible even with duplicate tokens
* Should handle strings just fine :)
* Parser should use a full-blow Regex pattern
* In-memory command history with Linked List (See stack overflow article)
  * Serialize the LL and load from file at start-time (See CPSC2108 Graph project)
  * Raises the issue of a fully interactive shell allowing for Signal sending via keyboard shortcuts, how do we configure this?
    *  ^[[A ^[[B  (Key Up Key Down respectively)
    * Ctrl+D (EOF)
    * Ctrl+C (KeyboardInterrupt)
* Foreground/background process control with sleep functionality, making it a true Job control shell rather than a Command library binding
* Output redirection and piping (See Shell implementation that is starred on GitHub)
* Cached Tokenization method (LRU decorator and HashTable)

### OOD Stuffs
* How do we implement Built-ins using Command design pattern
* Visitor design pattern

### DevOps Stuff

* Possible complexity reduction by eliminating seperated passes in CommandLexer
* Should be an Error class and a debugMode in main class with a String long of some sort
* Every class should have an overriden toString method and Node/Tokens etc should have an overriden equals method
* Unit and regression tests in Java
  * Should regression test use a shell script to test program?
    * i.e. listen for piped input to the program and eval-print?
  * How are unit-tests best setup in Eclipse?
  * How do we target the whole package?
* DOC-String generation 
* Version Control with git 