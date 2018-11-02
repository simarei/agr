package dev.rei.agr;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dev.rei.agr.manager.BookMarkManager;
import dev.rei.agr.util.DBManager;
// insert,delete,updateなどを行う
// DeleteChannelなどもこちらに統合予定
public class HandleFeeds extends HttpServlet {
/**
* generated id
*/
private static final long serialVersionUID = -1366063737580536927L;

public void doPost(HttpServletRequest req, HttpServletResponse res) {

System.out.println("HandleFeeds ========= start");

String mode = req.getParameter("mode");
if (mode == null) {
return;
}
System.out.println("mode:" + mode);

if ( "insert".equals(mode) ) {
insertBookmark(req, res);
} else if ( "delete".equals(mode) ){
deleteBookmark(req, res);
}

System.out.println("HandleFeeds ========= end");
}

private void deleteBookmark(HttpServletRequest req, HttpServletResponse res) {
// delete
String id = req.getParameter("id");

try {
Connection con = DBManager.getConnection();
con.setAutoCommit(false);

BookMarkManager.deleteBookMark(con, id);

con.commit();
con.close();
} catch (SQLException e) {
e.printStackTrace();
}
}

private void insertBookmark(HttpServletRequest req, HttpServletResponse res) {
String url = req.getParameter("url");
String title = req.getParameter("title");
String desc = req.getParameter("desc");
String pubdate = req.getParameter("pubdate");
String channel_id = req.getParameter("channel_id");

// insert
try {
Connection con = DBManager.getConnection();
con.setAutoCommit(false);

BookMarkManager.insertBookMark(con, url, title, desc, pubdate, channel_id);

con.commit();
con.close();
} catch (SQLException e) {
e.printStackTrace();
}
}
}