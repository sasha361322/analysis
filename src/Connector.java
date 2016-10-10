import java.sql.*;
import java.util.ArrayList;

public class Connector {
    Connector(String _url){
        this.url += _url;
        res = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        Connection cn = null;
        try {
            cn = DriverManager.getConnection(url, usr, pwd);
            res = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    Connector(String _url, String _usr, String _pwd){
        this.url += _url;
        this.usr = _usr;
        this.pwd = _pwd;
        res = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        Connection cn = null;
        try {
            cn = DriverManager.getConnection(url, usr, pwd);
            res = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public boolean Done() {
        return res;
    }
    public ArrayList<String> getTableNames() {
        ArrayList mas = new ArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
        }
        Connection cn = null;
        try {
            cn = DriverManager.getConnection(url, usr, pwd);
            DatabaseMetaData dbm = cn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbm.getTables(null, null, "%", types);
            mas = new ArrayList<String>();
            while (rs.next()) {
                String table = rs.getString("TABLE_NAME");
                mas.add(table);
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException ex) {
                //ex.printStackTrace();
            }
        }
        return mas;
    }
    public Table getTable(String tablename) {
        Table table = new Table();
        table.setName(tablename);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
        }
        Connection cn = null;
        try {
            cn = DriverManager.getConnection(url, usr, pwd);
            //getting primary keys
            ArrayList pkeys = new ArrayList();
            DatabaseMetaData dbm = cn.getMetaData();
            ResultSet rs = dbm.getPrimaryKeys("", "", tablename);
            while (rs.next()) {
                pkeys.add(rs.getString("COLUMN_NAME"));
            }
            table.setPrimaryKeys(pkeys);

            //getting foreign keys
            ArrayList fkeys = new ArrayList();
            rs = dbm.getExportedKeys("", "", tablename);
            while (rs.next()) {
                fkeys.add(rs.getString("COLUMN_NAME"));
            }
            table.setForeignKeys(fkeys);

            //getting line count
            Statement st = cn.createStatement();
            int lineCount = 0;
            rs = st.executeQuery("select count(*) from " + tablename);
            while (rs.next()) {
                lineCount = rs.getInt(1);
            }
            table.setLineCount(lineCount);

            //getting column count
            rs = st.executeQuery("select * from " + tablename);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = 0;
            columnCount = md.getColumnCount();
            table.setColumnCount(columnCount);

            //getting columnTypes
            ArrayList types = new ArrayList();
            for (int i = 0; i < columnCount; i++) {
                String type = md.getColumnTypeName(i + 1);
//                if (type.indexOf("VARCHAR") >= 0) {
//                    type += "(30)";
//                }
                types.add(type);
            }
            table.setColumnTypes(types);

            //getting column names
            ArrayList names = new ArrayList();
            names = new ArrayList();
            for (int i = 0; i < columnCount; i++) {
                names.add(md.getColumnName(i + 1));
            }
            table.setColumnNames(names);

        } catch (Exception ex) {
            //ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException ex) {
                //ex.printStackTrace();
            }
        }
        return table;
    }


    private String url= "jdbc:mysql://localhost:3306/";
    private String usr = "root";
    private String pwd = "";
    private boolean res;
}
