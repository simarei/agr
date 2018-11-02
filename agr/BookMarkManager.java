package dev.rei.agr.manager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class BookMarkManager {

private static final String INSERT_BOOKMARK = "INSERT IGNORE INTO bookmarks (channel_id, url, title, txt_desc, pub_date) values (?,?,?,?,?)";
private static final String DELETE_BOOKMARK = "DELETE FROM bookmarks WHERE id = ?";

public static void deleteBookMark(Connection con, String id) throws SQLException {
// SQL定義
PreparedStatement pstmt = con.prepareStatement(DELETE_BOOKMARK);

// パラメータをセット
pstmt.clearParameters();
pstmt.setString(1, id);

// 実行
pstmt.execute();

}
public static void insertBookMark(Connection con, String url, String title,
String desc, String pubdate, String channel_id) throws SQLException {
// SQL定義
PreparedStatement pstmt = con.prepareStatement(INSERT_BOOKMARK);

// パラメータをセット
pstmt.clearParameters();
pstmt.setString(1, channel_id);
pstmt.setString(2, url);
pstmt.setString(3, title);
pstmt.setString(4, desc);
pstmt.setString(5, pubdate);

// 実行
pstmt.execute();

}
}