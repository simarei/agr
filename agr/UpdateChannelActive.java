package dev.rei.agr;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dev.rei.agr.manager.FeedChannelManager;
import dev.rei.agr.util.DBManager;
public class UpdateChannelActive extends HttpServlet {
/**
* 
*/
private static final long serialVersionUID = -5211802115843766101L;

public void doPost(HttpServletRequest req, HttpServletResponse res) {

System.out.println("UpdateChannel ========= start"); 
try {
Connection con = DBManager.getConnection();
con.setAutoCommit(false);

String channel_id = req.getParameter("id");
boolean is_active = false;
if ( "true".equals(req.getParameter("is_active"))) {
is_active = true;
}

FeedChannelManager.updateChannelIsActive(con, channel_id, is_active);

con.commit();
con.close();
} catch (SQLException e) {
e.printStackTrace();
}
}
}