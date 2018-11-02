package dev.rei.agr.manager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import dev.rei.agr.objects.FeedItem;
/**
* FeedItemオブジェクトに関する操作を行う。
* 
* @author R.Oshima
*
*/
public class FeedItemManager {
// 定数定義
private static final String INSERT_ITEMS = "INSERT IGNORE INTO items (url) values (?)";
private static final String SELECT_ITEMS = "SELECT id FROM items where url = ?";
private static final String NODE_NAME_ITEM = "item";

/**
* アイテム情報をDBに登録する。
* コネクション生成、コミット、クローズは呼び出し元で行う。
* 
* @param item
* @param con
* @throws SQLException 
*/
public static void InsertItemToDB(FeedItem item, Connection con) throws SQLException{
// SQL定義
PreparedStatement pstmt = con.prepareStatement(INSERT_ITEMS);

// パラメータをセット
pstmt.clearParameters();
pstmt.setString(1, item.getLink());

// 実行
pstmt.execute();
}
/**
* itemを含む階層のNodeオブジェクトを再帰的に解析し、FeedItemのリストを生成する
* 
* @param rootNode
* @param items
* @param channel_id
* @return
*/
public static ArrayList generateItemListFromNode(Node rootNode, ArrayList items, String channel_id) {

// 子ノードを持たないノードは除外
if ( !rootNode.hasChildNodes() ) {
return items;
}

// 子ノードのリストを取得
NodeList nodes = rootNode.getChildNodes();

for (int i = 0; i< nodes.getLength(); i++){
Node node = nodes.item(i);

if ( node.hasChildNodes()) {

// タグ名（ノード名）で判別
if ( NODE_NAME_ITEM == node.getNodeName() ) {

// "item"タグのノードなので、FeedItemオブジェクトを生成
FeedItem fitem = new FeedItem(node, channel_id);
items.add(fitem);
continue;

} else {

// 次の階層を探査
generateItemListFromNode(node, items, channel_id);
}
}
}

return items;
}

/**
* 渡されたitemのURLをキーにDB検索を行い、既登録であればitemの既読フラグをtrueにセットする。
* コネクション生成、クローズは呼び出し元に手行う。
* 
* @param item
* @param con
* @return
* @throws SQLException 
*/
public static FeedItem setIsReadFromDB(FeedItem item, Connection con) throws SQLException {

// SQL準備
PreparedStatement pstmt;
pstmt = con.prepareStatement(SELECT_ITEMS);

ResultSet rs = null;

// パラメータをセット
pstmt.clearParameters();
pstmt.setString(1, item.getLink());
rs = pstmt.executeQuery();

// 結果があれば既読フラグをtrueにセット
while (rs.next()) {
item.setIsRead(true);
}

return item;
}

}