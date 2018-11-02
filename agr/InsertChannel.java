package dev.rei.agr;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Node;
import dev.rei.agr.manager.FeedChannelManager;
import dev.rei.agr.objects.FeedChannel;
import dev.rei.agr.util.DBManager;
public class InserteChannel extends HttpServlet {
/**
* 
*/
private static final long serialVersionUID = 7780052218360288085L;
public void doPost(HttpServletRequest req, HttpServletResponse res) {

System.out.println("InserteChannel ========= start");

String url = req.getParameter("url");

// check url
// URLのXMLドキュメントを取得し、Nodeオブジェクトを生成
Node rootNode = FeedChannelManager.getNodeFromURL(url);

// NodeオブジェクトからFeedChannelオブジェクト生成、マップに追加
FeedChannel channel = FeedChannelManager.generateChannelFromNode(rootNode,true);

if (channel == null) {
return;
}

// insert
try {
Connection con = DBManager.getConnection();
con.setAutoCommit(false);

FeedChannelManager.insertChannel(con, url);

con.commit();
con.close();
} catch (SQLException e) {
e.printStackTrace();
}
}

}