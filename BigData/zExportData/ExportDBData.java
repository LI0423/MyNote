package BigData.zExportData;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExportDBData {

    private static final String DB_URL = "jdbc:mysql://114.215.29.139:3401/sd_ring_csez?useUnicode=true&characterEncodicharacterEncodingng=utf-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false&allowMultiQueries=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String OUTPUT_DIR = "/Users/litengjiang/Desktop/WorkCode/export/";

    public static void main(String[] args) throws Exception {
        Connection conn = null;
        try {
            // 获取数据库连接
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // 获取数据库中的所有表
            List<String> tables = getTables(conn);

            // 遍历所有表并导出数据
            for (String table : tables) {
                exportTableData(conn, table);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 获取数据库中的所有表
     */
    private static List<String> getTables(Connection conn) throws SQLException {
        List<String> tables = new ArrayList<>();
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet rs = meta.getTables(null, null, null, new String[]{"TABLE"});
        while (rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }

    /**
     * 导出表数据到文件
     */
    private static void exportTableData(Connection conn, String table) throws SQLException, IOException {
        // 查询表数据
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM " + table;
        ResultSet rs = stmt.executeQuery(sql);

        // 生成输出文件
        String filename = OUTPUT_DIR + table + ".sql";
        FileWriter writer = new FileWriter(filename);

        // 遍历结果集并写入文件
        while (rs.next()) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(table).append(" VALUES (");
            ResultSetMetaData meta = rs.getMetaData();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                Object value = rs.getObject(i);
                if (value == null) {
                    sb.append("NULL");
                } else if (meta.getColumnType(i) == Types.NUMERIC) {
                    sb.append(value.toString());
                } else {
                    sb.append("'").append(value.toString().replace("'", "''")).append("'");
                }
                if (i < meta.getColumnCount()) {
                    sb.append(",");
                }
            }
            sb.append(");\n");
            writer.write(sb.toString());
        }

        // 关闭流
        writer.close();
        rs.close();
        stmt.close();
    }
}
