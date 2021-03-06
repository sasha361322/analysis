import java.util.ArrayList;
import java.util.List;

public class Table {
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getColumnCount() {
        return columnCount;
    }
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
    public List getColumnNames() {
        return columnNames;
    }
    public void setColumnNames(ArrayList columnNames) {
        this.columnNames = columnNames;
    }
    public List getColumnTypes() {
        return columnTypes;
    }
    public void setColumnTypes(ArrayList columnTypes) {
        this.columnTypes = columnTypes;
    }
    public List getPrimaryKeys() {
        return primaryKeys;
    }
    public void setPrimaryKeys(ArrayList primaryKeys) {
        this.primaryKeys = primaryKeys;
    }
    public List getForeignKeys() {
        return foreignKeys;
    }
    public void setForeignKeys(ArrayList foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
    public int getLineCount() {
        return lineCount;
    }
    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", columnCount=" + columnCount +
                ", lineCount=" + lineCount +
                ", columnNames=" + columnNames +
                ", columnTypes=" + columnTypes +
                ", primaryKeys=" + primaryKeys +
                ", foreignKeys=" + foreignKeys +
                '}';
    }

    private String name;
    private int columnCount;
    private int lineCount;
    private List columnNames;
    private List columnTypes;
    private List primaryKeys;
    private List foreignKeys;
}
