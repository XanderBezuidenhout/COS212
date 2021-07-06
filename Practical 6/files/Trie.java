//Name: Xander Bezuidenhout
//Student Number: 20425997

public class Trie {	
	protected char[] letters;
	protected Node root = null;
	private int numPtrs;

	public Trie(char[] letters) {
		this.letters = letters;
		this.numPtrs = letters.length + 1;
	}


	//Provided Helper functions
	
	private int index(char c) {
		for (int k = 0; k < letters.length; k++) {
			if (letters[k]== (c)) {
				return k+1;
			}
		}
		return -1;
	}
	
	private char character(int i) {
		if (i == 0) {
			return '#';
		} else {
			return letters[i-1];
		}
	}
	
	private String nodeToString(Node node, boolean debug) {
		if (node.isLeaf) {
			return node.key;
		}
		else {
			String res = "";
			for (int k = 0; k < node.ptrs.length; k++) {
				if (node.ptrs[k] != null) {
					res += " (" + character(k) + ",1) ";
				} else if (debug) {
					res += " (" + character(k) + ",0) ";
				}
			}
			return res;
		}
	}

	public void print(boolean debug) {
		Queue queue = new Queue();
		Node n = root;
		if (n != null) {
			n.level = 1;
			queue.enq(n);
			while (!queue.isEmpty()){
				n = queue.deq();
				System.out.print("Level " + n.level + " [");
				System.out.print(nodeToString(n, debug));
				System.out.println("]");
				for (int k = 0; k < n.ptrs.length; k++) {
					if (n.ptrs[k] != null) {
						n.ptrs[k].level = n.level+1;
						queue.enq(n.ptrs[k]);
					}
				}
			}
		}
	}


	////// You may not change any code above this line //////

	////// Implement the functions below this line //////

	
	// Function to insert the given key into the trie at the correct position.
	public void insert(String key) {

		// Your code goes here
		if (!shouldInsert(key)||contains(key))
		{
			return;
		}
		if (root==null)
		{
			root=new Node(key,numPtrs);
		}
		Node lastCommon=findLastCommonNode(key);
		int howFarWordComplete=howFarWordComplete(key);
		if (lastCommon==null)
		{
			return;
		}
		if (lastCommon==root)
		{
			char currchar=key.charAt(0);
			int letterindex=index(currchar);
			root.ptrs[letterindex]=new Node(key, numPtrs);
			root.isLeaf=false;
		}
		else if (lastCommon.isLeaf)//we convert this node into nonleaf and add references to leaf nodes for each of 2 words
		{
			insertLeaf(lastCommon, key);
		}
		else if (howFarWordComplete(key)==key.length()) //word will have endstring char at lastcommon
		{
			lastCommon.ptrs[0]=new Node(key, numPtrs); //no other endword reference (else this be duplicate), assign this key there as leaf
			lastCommon.endOfWord=true;
		}
		else //word goes on from a null reference pointer
		{
			int ptrindex=index(key.charAt(howFarWordComplete(key)));
			lastCommon.ptrs[ptrindex]=new Node(key, numPtrs); //assign key as leaf
		}
	}

	protected void insertLeaf(Node lastCommon,String key)
	{
			int howFarWordComplete=howFarWordComplete(key);
			int commonindices=findLastMatchedCharIndex(lastCommon.key,key);
			Node currNode=lastCommon;
			String oldkey=currNode.key;
			char currchar='%';
			int letterindex=-1;
			for (int i=howFarWordComplete-1;i<commonindices;i++) 
			{
				currchar=key.charAt(i+1);
				letterindex=index(currchar);
				currNode.ptrs[letterindex]=new Node(numPtrs);
				currNode=currNode.ptrs[letterindex];
			}
			if (oldkey.length()==commonindices+1)
			{
				currNode.ptrs[0]=new Node(oldkey, numPtrs);
				currNode.endOfWord=true;
			}
			else
			{
				currchar=oldkey.charAt(commonindices+1);
				letterindex=index(currchar);
				currNode.ptrs[letterindex]=new Node(oldkey, numPtrs);
			}

			if (key.length()==commonindices+1)
			{
				currNode.ptrs[0]=new Node(key, numPtrs);
				currNode.endOfWord=true;
			}
			else
			{
				currchar=key.charAt(commonindices+1);
				letterindex=index(currchar);
				currNode.ptrs[letterindex]=new Node(key, numPtrs);
			}
			lastCommon.isLeaf=false;
	}
	
	protected int findLastMatchedCharIndex(String key1,String key2)
	{
		int i=0;
		for (i=0;key1.length()>i&&key2.length()>i&&key1.charAt(i)==key2.charAt(i);i++);
		return (i-1);
	}

	protected Node findLastCommonNode(String key)
	{
		Node lastCommon=root;
		char currchar='%';
		int letterindex=-1;
		for (int i=0;i<key.length();i++)
		{
			currchar=key.charAt(i);
			letterindex= index(currchar);
			if (lastCommon.isLeaf)
			{
				return lastCommon; //the node is a leaf and must be converted to nonleaf with 2 leaf references added, check using isleaf
			}
			else if (lastCommon.ptrs[letterindex]==null)
			{
				return lastCommon; //a node does not continue with the word, but perhaps with other. Check by looking if lastletter null when back
			}
			else
			{
				lastCommon=lastCommon.ptrs[letterindex];
			}
		}
		return lastCommon; //the word is shorter than branch length of node or exact same length, check by looking if hashtag is null (already checked for duplicate)
	}

	protected int howFarWordComplete(String key)
	{
		Node currNode=root;
		Node lastCommon=findLastCommonNode(key);
		char currchar='%';
		int letterindex=-1;
		int i=0;
		for (i=0;i<key.length()&&currNode!=lastCommon;i++)
		{
			currchar=key.charAt(i);
			letterindex= index(currchar);
			currNode=currNode.ptrs[letterindex];
		}
		return i;
	}
	// Function to determine if a node with the given key exists.
	public boolean contains(String key) {

		if (root==null||key.length()<1||!shouldInsert(key))
		{
			return false;
		}
		Node currNode=root;
		char currchar='%';
		int letterindex=-1;
		for (int i=0;i<key.length();i++)
		{
			if (currNode==null)
			{
				return false;
			}
			currchar=key.charAt(i);
			letterindex=index(currchar);
			if (currNode.isLeaf)//encountered leaf before end of word
			{
				return (currNode.key==key); //must contain word as key else key does not exist
			}
			currNode=currNode.ptrs[letterindex];
		}
		if (currNode==null||!currNode.endOfWord) //checks whether node actually ends this key, or any key
		{
			return false;
		}
		if (currNode.ptrs[0]==null||currNode.ptrs[0].key!=key)//must contain reference leaf containing word at end of word
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	
	// Function to print all the keys in the trie in alphabetical order.
	public void printKeyList() {

		// Your code goes here
		String output=printRecursive(root);
		output.trim();
		System.out.println(output);
	}

	public String printRecursive(Node printNode)
	{
		String output="";
		if (printNode==null)
		{
			return output;
		}
		for (int i=0;i<numPtrs;i++)
		{
			if (printNode.ptrs[i]!=null&&printNode.ptrs[i].isLeaf)
			{
				output+=printNode.ptrs[i];
				output+=" ";
			}
			else if (printNode.ptrs[i]!=null)
			{
				output+=printRecursive(printNode.ptrs[i]);
			}
		}
		return output;
	}
	
	//Helper functions
	public boolean shouldInsert(String key)  //determines whether we have the chars available to accomodate word
	{
		boolean valid=false;
		for (int i = 0; i < key.length(); i++) 
		{
			if (index(key.charAt(i))==-1)
			{
				return false;
			}
		}
		return true;
	}

	
}