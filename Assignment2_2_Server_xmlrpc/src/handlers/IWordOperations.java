package handlers;

/*
 * Operations that operates on words & files provided by Server for Client.
 * The folder & files are provided by Server. Client could get copy of Hashtable of list of files, word lists.
 * The filePath is also provided by Server.
 */
public interface IWordOperations {
	public String findOccurrences(String keyword);
}
