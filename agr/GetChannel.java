package dev.rei.agr;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.w3c.dom.Node;
import dev.rei.agr.manager.FeedChannelManager;
import dev.rei.agr.objects.FeedChannel;
public class GetChannels extends GetList {
/**
* generated id
*/
private static final long serialVersionUID = -5930724084999396481L;

private static HashMap<String,FeedChannel> channelList = new HashMap<String,FeedChannel>();
@Override
void initList() {
// 初期化
channelList.clear();
}

@Override
String getControlBar() {
String form = "RSSのURLを登録　
　登録";
return form;
}

@Override
void getObjectList(ResultSet rs, Connection con) throws IOException, SQLException {
while (rs.next()) {
String channel_id = rs.getString(1);
String url = rs.getString(2);
boolean isActive = false;
if ("1".equals(rs.getString(3))) {
isActive = true;
}

// URLのXMLドキュメントを取得し、Nodeオブジェクトを生成
Node rootNode = FeedChannelManager.getNodeFromURL(url);

// NodeオブジェクトからFeedChannelオブジェクト生成、マップに追加
FeedChannel channel = FeedChannelManager.generateChannelFromNode(rootNode,isActive);
channelList.put(channel_id, channel);
}

}
@Override
StringBuffer getBody() {

StringBuffer out = new StringBuffer();

if (channelList.size() == 0) {

// 記事がなかった場合
out.append("no channels\r\n");

} else {

out.append("
"); // HashMap用イテレータを用意 Set urlKeySet = channelList.keySet(); Iterator urlIt = urlKeySet.iterator(); // 各URLからNodeを作成、チャンネルおよびアイテムリストを取得 while (urlIt.hasNext()) { String channel_id = urlIt.next(); FeedChannel channel = channelList.get(channel_id); String title = channel.getTitle(); String url = channel.getLink(); boolean isActive = channel.getIsActive(); if (isActive) { out.append("
"); } else { out.append(""); } } out.append("
"); } if (isActive) { out.append(""); } else { out.append(""); } out.append(""); out.append(""); out.append("
無効化	有効化	削除	" + title + "
"); return out; } @Override String getSQLSelectChannel() { String SQL = "SELECT id, url, is_active FROM channels"; return SQL; } @Override String getClassName() { String className = "GetChannels"; return className; } }