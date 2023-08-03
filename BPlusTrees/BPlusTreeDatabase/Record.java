/**
 * Class for a table row
 * Stores and manipulates all values for a row in a record.
 * There is no need to modify this class
 */
public class Record {

	private Object[] columns;
	private int columnCount;

	public Record(int count) {
		this.columnCount = count;
		this.columns = new Object[columnCount];
	}

	public Object getColumn(int idx) {
		return columns[idx - 1];
	}

	public void setColumn(int idx, Object obj) {

		columns[idx - 1] = obj;
	}

	public String getValues() {

		if (columnCount <= 0)
			return "";

		String result = "";
		for (int i = 0; i < columnCount - 1; i++) {
			result += columns[i].toString() + ", ";
		}
		result += columns[columnCount - 1];

		return result;
	}

}