package dev.rei.agr.manager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import dev.rei.agr.objects.FeedChannel;
/**
* FeedChannelオブジェクトに関する操作を行う。
* 
* @author R.Oshima
*
*/
public class FeedChannelManager {
// 定数定義
private static final String SELECT_CHANNELS = "SELECT id, url FROM channels WHERE is_active = 1;";
private static final String SELECT_ALL_CHANNELS = "SELECT id, url, is_active FROM channels";
private static final String UPDATE_CHANNELS_ISACTIVE = "UPDATE channels SET is_active = ? WHERE id = ?";
private static final String INSERT_CHANNEL = "INSERT IGNORE INTO channels (url) values(?)";
private static final String DELETE_CHANNEL = "DELETE FROM channels WHERE id = ?";
static final String NODE_NAME_CHANNEL = "channel";

/**
* URLにアクセスし、XMLを取得する。取得したXMLをNodeオブジェクトに変換する。
* 
* @param url
* @return rootNode
*/
public static Node getNodeFromURL(String url) {

Node rootNode = null;
Document doc = null;
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder;
try {
// URLからXMLドキュメントを生成
builder = factory.newDocumentBuilder();
doc = builder.parse(url);
} catch (ParserConfigurationException e) {
e.printStackTrace();
} catch (SAXException e) {
e.printStackTrace();
} catch (IOException e) {
e.printStackTrace();
}

if (doc != null) {
// Nodeオブジェクトを生成
rootNode = doc.getDocumentElement();
}
return rootNode;
}

/**
* チャンネルのid,URLのリストをDBから取得する。
* 
* @param con
* @return
* @throws SQLException 
*/
public static HashMap<String, String> getChannelURLsFromDB(Connection con) throws SQLException {
HashMap<String, String> urlMap = new HashMap<String, String>();

// 問合せ実行
Statement stmt;
stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(SELECT_CHANNELS);

// 結果をマップにセット
while (rs.next()) {
String channel_id = rs.getString(1);
String url = rs.getString(2);
urlMap.put(channel_id, url);
}
return urlMap;
}

public static ResultSet getChannelURLsFromDB2(Connection con) throws SQLException {
// 問合せ実行
Statement stmt;
stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(SELECT_CHANNELS);

return rs;
}

public static ResultSet getChannelURLsFromDB3(Connection con, String SQL) throws SQLException {
// 問合せ実行
Statement stmt;
stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(SQL);

return rs;
}
/**
* チャンネルのid,URLのリストをDBから取得する。is_activeを問わない。
* 
* @param con
* @return
* @throws SQLException 
*/
public static ResultSet getAllChannelURLsFromDB(Connection con) throws SQLException {

// 問合せ実行
Statement stmt;
stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(SELECT_ALL_CHANNELS);

return rs;
}

/**
* ルートノードオブジェクトからchannelノードを取り出し、FeedChannelオブジェクトを生成する。
* 
* 
* @param rootNode
* @return
*/
public static FeedChannel generateChannelFromNode(Node rootNode, boolean isActive) {

FeedChannel channel = null;

if (rootNode == null) {
return channel;
}

// 子ノードを持たないノードは除外
if (!rootNode.hasChildNodes() ) {
return channel;
}

// 子ノードのリストを取得
NodeList nodes = rootNode.getChildNodes();

for (int i = 0; i< nodes.getLength(); i++) {

Node node = nodes.item(i);

// タグ名（ノード名）で判別
if ( NODE_NAME_CHANNEL == node.getNodeName()){

// channelノードであれば、情報をセットしてインスタンスを生成する
channel = new FeedChannel(node);
channel.setIsActive(isActive); 

break;
}
}

return channel;
}

public static void updateChannelIsActive(Connection con, String channel_id, boolean is_active) throws SQLException {
// SQL定義
PreparedStatement pstmt = con.prepareStatement(UPDATE_CHANNELS_ISACTIVE);

// パラメータをセット
pstmt.clearParameters();
if (is_active) {
pstmt.setString(1, "1");
} else {
pstmt.setString(1, "0");
}
pstmt.setString(2, channel_id);

// 実行
pstmt.execute();
}
public static void insertChannel(Connection con, String url) throws SQLException {
// SQL定義
PreparedStatement pstmt = con.prepareStatement(INSERT_CHANNEL);

// パラメータをセット
pstmt.clearParameters();
pstmt.setString(1, url);

// 実行
pstmt.execute();
}
public static void deleteChannel(Connection con, String channel_id) throws SQLException {
// SQL定義
PreparedStatement pstmt = con.prepareStatement(DELETE_CHANNEL);

// パラメータをセット
pstmt.clearParameters();
pstmt.setString(1, channel_id);

// 実行
pstmt.execute();
}

}