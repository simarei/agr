package dev.rei.agr;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dev.rei.agr.manager.FeedChannelManager;
import dev.rei.agr.util.DBManager;
public class DeleteChannel extends HttpServlet {
/**
* 
*/
private static final long serialVersionUID = 3366164972085041581L;
public void doPost(HttpServletRequest req, HttpServletResponse res) {

System.out.println("DeleteChannel ========= start");

String channel_id = req.getParameter("id");

// delete
try {
Connection con = DBManager.getConnection();
con.setAutoCommit(false);

FeedChannelManager.deleteChannel(con, channel_id);

con.commit();
con.close();
} catch (SQLException e) {
e.printStackTrace();
}
}
}