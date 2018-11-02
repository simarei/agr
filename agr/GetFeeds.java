package dev.rei.agr;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.w3c.dom.Node;
import dev.rei.agr.objects.FeedChannel;
import dev.rei.agr.objects.FeedItem;
import dev.rei.agr.util.FeedItemComparator;
import dev.rei.agr.manager.FeedItemManager;
import dev.rei.agr.manager.FeedChannelManager;
/**
* DBよりRSSを取得するアドレス（=チャンネル）のリストを取得し、最新の記事リストをコンソールに出力する。
* 記事情報はDBに記録し、前回実行時から新たに追加された記事は区別する。
* 
* @author R.Oshima
*
*/
public class GetFeeds extends GetList {
/**
* generated serial id
*/
private static final long serialVersionUID = 2579551936119998979L;


// メンバ変数
// チャンネル情報のHashMap、および全アイテムリストはクラス内共通
private static HashMap<String,FeedChannel> channelList = new HashMap<String,FeedChannel>();
private static ArrayList allItemList = new ArrayList();

@Override
void initList() {
// 初期化
channelList.clear();
allItemList.clear();
}

@Override
String getSQLSelectChannel() {
String SQL = "SELECT id, url, is_active FROM channels WHERE is_active = 1";
return SQL;
}

@Override
void getObjectList(ResultSet rs, Connection con) throws IOException, SQLException {
while(rs.next()){
String channel_id = rs.getString(1);
String url = rs.getString(2);

// URLのXMLドキュメントを取得し、Nodeオブジェクトを生成
Node rootNode = FeedChannelManager.getNodeFromURL(url);

// NodeオブジェクトからFeedChannelオブジェクト生成、マップに追加
FeedChannel channel = FeedChannelManager.generateChannelFromNode(rootNode, true);
channelList.put(channel_id, channel);

// NodeオブジェクトからFeedItemのリストを生成
ArrayList itemList = new ArrayList();
itemList = FeedItemManager.generateItemListFromNode(rootNode, itemList, channel_id);

for (int i = 0; i < itemList.size(); i++) {
FeedItem item = itemList.get(i);

// DB問合せでアイテムの既読/未読をチェック、FeedItemのプロパティにセット
item = FeedItemManager.setIsReadFromDB(item, con);

// アイテムをDB登録（次回以降既読とカウントされる）
FeedItemManager.InsertItemToDB(item, con);

// アイテムを全アイテムリストに登録
allItemList.add(item);
}
}

// 取得した全アイテムをdate降順に並べ替え
Collections.sort(allItemList, new FeedItemComparator());
}

@Override
String getControlBar() {
String form = "リロード";
return form;
}
@Override
StringBuffer getBody() {

StringBuffer out = new StringBuffer();

out.append("
"); if (allItemList.size() == 0) { // 記事がなかった場合 out.append("no items\r\n"); } else { // 記事リストから最新の20件を出力する for ( int i = 0; i < allItemList.size(); i++ ){ FeedItem item = allItemList.get(i); // チャンネルタイトル String channel_id = item.getChannelId(); FeedChannel channel = channelList.get(channel_id); String channel_title = channel.getTitle(); String channel_link = channel.getLink(); // 記事情報 String title = item.getTitle(); if (title.length() > 40 ){ // タイトルが長い場合は省略して表示 title = title.substring(0, 39) + "..."; } String link = item.getLink(); String desc = item.getDesc(); String date = item.getPubDate().get(0); String time = item.getPubDate().get(1); boolean is_read = item.getIsRead(); // 出力内容 out.append(""); } } out.append("
");

// 未読・既読
if(is_read) {
out.append(""); } else { out.append(""); } // ブックマーク登録 out.append(""); // チャンネル情報 out.append(""); // 記事情報 out.append(""); if ( date != null) { out.append(""); } else { out.append("
"); } // 記事詳細があれば記事詳細 if ( desc != null ) { out.append(""); } out.append("
☆	" + channel_title + "	" + title + "	" + date + " " + time + "
 	" + desc + "
");
return out;
}
@Override
String getClassName() {
String className = "GetFeeds";
return className;
}

}