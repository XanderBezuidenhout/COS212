/**
 * A simplistic database table class. Uses the record class to store row data
 * and the index class to
 * maintain indexes for specific columns.
 * Class also implements basic SQL methods. Uses the error class for common
 * error messages.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Table {

	private String name;
	private String[] columns;
	private Record[] records;
	private Index[] indexes;
	private int rowId;
	private int recordCount;
	private int indexCount;
	private int indexOrder; // the BPlusTree order to make all of the indices for this table

	public Table(String name, String[] columns, int indexOrder) {
		this.rowId = 1; // start index in records array
		this.recordCount = 0;
		this.indexCount = 0;
		this.name = name; // name of the table
		this.columns = columns;
		this.indexOrder = indexOrder;
		this.records = new Record[1000]; // initial size of table. Assume this will not be exceeded
		this.indexes = new Index[10]; // initial number of indexes. Assume this will not be exceeded
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRecordCount() {
		return this.recordCount;
	}

	public int getIndexCount() {
		return this.indexCount;
	}

	// you can ignore this function
	public String debug() {
		String result = "";
		if (indexCount > 0) {
			for (int i = 0; i < indexCount; i++) {
				Index idx = indexes[i];
				result += idx.getColumnName() + "\n";
				result = result + idx.getIndex().getDebugString();
				if ((i + 1) < indexCount)
					result = result + " ";
			}
		} else {
			result = "No Indexes!";
		}
		return result;
	}

	////// You may not change any code above this line //////

	////// Implement the functions below this line //////

	/**
	 * Insert the given "rec" in this table. Every record needs to have a unique row id which increments after each insert. RowIDs are never reused (unless the table is reset).
	 * Should indexes be present, they need to be updated.
	 */
	// SQL: insert into table values (rec)
	public void insert(Record rec) 
	{
		records[recordCount]=rec;
		recordCount++;
		updateIndices();
	}

	public void updateIndices()
	{
		int columnindex=1;
		rowId=1;
		for (int i=0;i<indexCount;i++)
		{
			BPTree indexTree=new BPTree<>(indexOrder);
			for (String colname : columns) 
			{
				if (colname==indexes[i].getName())
				{
					break;
				}
				columnindex++;
			}
			if (columnindex>=columns.length)
			{
				continue;
			}
			for (Record currRec : records ) 
			{
				indexTree.insert((Comparable) currRec.getColumn(i), (Object) rowId++);
			}
			indexes[i]=new Index(indexes[i].getName(), indexes[i].getColumnName(), indexTree);
		}
		
	}


	/**
	   Print all the records in this table where the given "column" matches "value".
	   Should call the getValues method of the record class. Needs to use the index for "column" and call the
	   search method of the used B+ tree. If no index exists for "column", conventional record iteration and
	   search should be used.
	   If the table is empty, print Err1.
	   else if the column does not exist print Err5.
	   else if no record matches, print error Err4.
	 */
	// SQL: select * from table where column = value
	public void selectWhere(String column, Object value) 
	{
			boolean printed=false;
			int nummatches=0;
			int rowindex=1; //rows start a 1
			//int[] rowIds=new int[100];
			Record[] recToPrint=new Record[1000];
			if (recordCount==0)
			{
				System.out.println(Error.Err1);
			}
			int indexindex=0;
			int colindex=1;
			boolean colexist=false;
			for (String string : columns) {
				if (string==column) //column exists
				{
					colexist=true;
					break;
				}
				colindex++;
			}
			if (!colexist)
			{
				System.out.println(Error.Err5);
				return;
			}
			for (Index currindex : indexes) //first try to find index
			{
				if (currindex==null)
				{
					continue;
				}
				if (currindex.getColumnName()==column)
				{
					break;
				}
				indexindex++;
			}
			if (indexindex<indexCount)
			{
				BPTree tree= indexes[indexindex].getIndex();
				ValueNode currNode=null;
				if (tree.search((Comparable) value)==null)
				{
					System.out.println(Error.Err4);
					return;
				}
				else
				{
					currNode=tree.search((Comparable) value);
				}
				while (currNode!=null)
				{
					recToPrint[nummatches]=records[(int) currNode.value-1];
					currNode=currNode.next;
					nummatches++;
				}
			}
			else
			{
				for (Record rec : records) 
				{
					if (rec!=null&&rec.getColumn(colindex).equals(value))
					{
						recToPrint[nummatches]=rec;
						nummatches++;
						break;
					}	
				}
			}
			if (nummatches==0)
			{
				System.out.println(Error.Err4);
			}
			for (int i=0;i<nummatches;i++) 
			{
				if (recToPrint[i]!=null)
				{
					System.out.println(recToPrint[i].getValues()); //finally prints values in order of being found in BTree or in sequential order 
				}
				
			}
	}

	/**
	 * Print all the records in this table ordered by the given "ocolumn" in ascending order (only if there is an index for "ocolumn").
	 * Should call the getValues method of the record class. Needs to use the index for ocolumn
	 * and call the values method of the used B+ tree. Remember to print all the records based on the order given in the index.
	 * If the table is empty, print error message 1. 
	 * else if no indexes are present at all, print error message 2.
	 * else if there is no index available for "ocolumn", print error message 3.
	 */
	// SQL: select * from table order by ocolumn
	public void selectOrderBy(String ocolumn) 
	{
		if (recordCount==0)
		{
			System.out.println(Error.Err1);
			return;
		}
		else if (indexCount==0)
		{
			System.out.println(Error.Err2);
			return;
		}
		int indexindex=0;
		for (Index currindex : indexes) //first try to find index
		{
			if (currindex==null)
			{
				continue;
			}
			if (currindex.getColumnName()==ocolumn)
			{
				break;
			}
			indexindex++;
		}
		if (indexindex>=indexCount)
		{
			System.out.println(Error.Err3);
			return;
		}
		BPTree tree= indexes[indexindex].getIndex();
		ValueNode currNode=null;
		int nummatches=0;
		int[] rowIDs=new int[1000];
		ValueNode[] valueListsArray=tree.values();
		Record[] matches=null;
		for (ValueNode valueHead : valueListsArray) {
			currNode=valueHead;
			while (currNode!=null)
			{
				rowIDs[nummatches++]=(int) currNode.value;
				currNode=currNode.next;
			}
		}
		for (int i=0;i<nummatches;i++)
		{
			System.out.println(records[rowIDs[i]].getValues());
			matches[i]=records[rowIDs[i]];//populate with all results within index
		}
	}

	/**
	 * Print all the records in this table. Should call the getValues method of the
	 * record class. If the table is empty print error message 1.
	 */
	// SQL: select * from table
	public void selectAll() 
	{
		if (recordCount==0)
		{
			System.out.println(Error.Err1);
			return;
		}
		for (Record rec : records) 
		{
			if (rec!=null)
			{
				System.out.println(rec.getValues());
			}
				
		}

	}
	
	/**
	 * Delete all the records in this table. recordCount and row id should be reset. Should also clear all indexes.
	 */
	// SQL: delete from table
	public void deleteAll() 
	{
		recordCount=0;
		for (Record rec : records) {
			rec=null;
		}
		rowId=1;
		for (Index currindex : indexes) {
			currindex.setIndex(null);
		}
	}
	
	/**
	 * Delete all the records in this table where the given "column" matches "value".
	 * Needs to use the index for "column" and call the search method of the used B+ tree in order to find the rowIDs and set the corresponding records to null.
	 * If no index exists for "column", conventional record iteration and search should be used.
	 * Deleted rows remain empty and the records array should NOT be compacted. recordCount however should be updated.
	 * Should indexes be present, they need to be updated.
	 * For the index named "column" (if it exists), the delete function of the index can simply be used because ALL of the ValueNodes need to be deleted for the key named "value".
	 * However, the corresponding RowIDs need to be removed from ALL of the indexes where they occur, not just the "column" index.
	 * This may involve manually searching through the corresponding ValueNode linked lists for the rowIDs to be deleted.
	 * You may add a helper function(s) in your BPTree class to achieve this additional task. 
	 * If an entire ValueNode list is deleted, then the corresponding key is also deleted using the normal delete function.
	 * If the table is empty print Err1, 
	 * else if the column name is not found print Err5
	 * else if no records are found that equal the value print Err4
	 */
	// SQL: delete from table where column equals value
	public void deleteWhere(String column, Object value) 
	{
		int indexindex=0;
		int[] indicestodelete=new int[1000];
		int numdeletes=0;
		int colindex=1;
		int rowindex=1;
		if (recordCount==0)
		{
			System.out.println(Error.Err1);
			return;
		}
		for (String col:columns) {
			if (col.equals(column))
			{
				break;
			}
			colindex++;
		}
		if (colindex>=recordCount+1)
		{
			System.out.println(Error.Err5);
			return;
		}
		for (Record rec : records) {
			if (rec!=null&&rec.getColumn(colindex).equals(value))
			{
				indicestodelete[numdeletes++]=rowindex;
				rec=null;
				recordCount--;
			}
			rowindex++;
		}
		if (numdeletes==0)
		{
			System.out.println(Error.Err4);
			return;
		}
		for (Index currindex : indexes) //first try to find index
		{
			if (currindex==null)
			{
				continue;
			}
			if (currindex.getColumnName()==column)
			{
				break;
			}
			indexindex++;
		}
		if (indexindex>=indexCount)
		{
			System.out.println(Error.Err5);
			return;
		}
		
		BPTree tree=indexes[indexindex].getIndex();
		tree.delete((Comparable)value);
		for (Index currindex : indexes) 
		{
			if (currindex==indexes[indexindex]||currindex==null||currindex.getIndex()==null)
			{
				continue;
			}
			tree=currindex.getIndex();
			for (int i=0;i<numdeletes;i++)
			{
				tree.removeValue(indicestodelete[i]);
			}			
		}

	}

	/**
	 * Create an index called "name" using the record values from "column" as keys
	 * and the row id as value. Insert values into the B+ tree in the order of the records in the table.
	 * The created B+ tree must match the data type of "column". 
	 * Return true if successful and false if column does not exist.
	 */
	public boolean createIndex(String name, String column) 
	{
		int columnindex=0;
		for (String colname : columns) 
		{
			if (colname==column)
			{
				break;
			}
			columnindex++;
		}
		if (columnindex>=columns.length)
		{
			return false;
		}
		BPTree indexTree=new BPTree<>(indexOrder);
	
		for (Record currRec : records ) 
		{
			if (currRec==null)
			{
				break;
			}
			indexTree.insert((Comparable) currRec.getColumn(columnindex+1), (Object) rowId++);
		}
		indexes[indexCount]= new Index(name, column,indexTree);
		indexCount++;
		return true;
	}

	/**
	 * Print all the keys in the index "name". Should call the print method of the
	 * used B+ tree. If an index with name "name" doesn't exist, then do nothing.
	 */
	public void printIndex(String name) 
	{
		int indexindex=-1;
		for (Index currindex : indexes) //first try to find index
		{
			indexindex++;
			if (currindex==null)
			{
				break;
			}
			if (currindex.getName()==name)
			{
				break;
			}
		}
		if (indexindex>=indexCount||indexindex==-1)
		{
			return;
		}
		BPTree tree= indexes[indexindex].getIndex();
		tree.print();
		/*ValueNode currNode=null;
		int nummatches=0;
		Record[] recs=new Record[1000];
		ValueNode[] valueListsArray=tree.values();
		for (ValueNode valueHead : valueListsArray) {
			currNode=valueHead;
			while (currNode!=null)
			{
				recs[nummatches]=records[(int) currNode.value];
				nummatches++;
				currNode=currNode.next;
			}
		}
		for (int i=0;i<nummatches;i++)
		{
			if (recs[i]==null)
			{
				continue;
			}
			System.out.println(recs[i].getValues());
		}*/
	}

}