package dev.rei.agr;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dev.rei.agr.manager.FeedChannelManager;
import dev.rei.agr.util.DBManager;
/**
スーパークラス

DBアクセス後、一覧をリスト表示する

SQL名はchannelとなっているが、bookmarkにも使用

/

public abstract class GetList extends HttpServlet{

/**
* generated id
*/
private static final long serialVersionUID = -388655148998679021L;

public void doGet(HttpServletRequest req, HttpServletResponse res) {

initList();

System.out.println(getClassName() + " ===== start");

// コネクションは使いまわす
Connection con = DBManager.getConnection();
try {
con.setAutoCommit(false);

String SQL = getSQLSelectChannel();

ResultSet rs_channels = FeedChannelManager.getChannelURLsFromDB3(con, SQL);
getObjectList(rs_channels, con);

StringBuffer outHtml = outputList();
res.setContentType("text/html; charset=UTF-8");
PrintWriter pw = res.getWriter();
pw.print(outHtml);

con.commit();
con.close();

} catch (SQLException e) {
e.printStackTrace();
} catch (IOException e) {
e.printStackTrace();
}

System.out.println(getClassName() + " ===== end");
}

StringBuffer outputList(){
StringBuffer out = new StringBuffer();

out.append(getHeader());
out.append(getControlBar());
out.append(getBody());
out.append(getFooter());

return out;
}

String getHeader() {
String head = ""; head += "";
head += "";
head += "";
head += "";
return head;
}

String getFooter() {
String foot = "";
return foot;
}

abstract void initList();

abstract String getSQLSelectChannel();
abstract void getObjectList(ResultSet rs, Connection con) throws IOException, SQLException;

abstract String getControlBar();

abstract StringBuffer getBody();

abstract String getClassName();
}