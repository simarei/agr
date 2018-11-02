package dev.rei.agr;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import dev.rei.agr.objects.FeedItem;
import dev.rei.agr.util.FeedItemComparator;
public class GetBookMarks extends GetList {
/**
* generated id
*/
private static final long serialVersionUID = 5719512097615974325L;
private ArrayList allItemList = new ArrayList();

@Override
void getObjectList(ResultSet rs, Connection con) throws SQLException {

while(rs.next()){
String id = rs.getString(1);
String channel_id = rs.getString(2);
String url = rs.getString(3);
String title = rs.getString(4);
String desc = rs.getString(5);
String date = rs.getString(6).substring(0, 9);
String time = rs.getString(6).substring(10);

FeedItem item = new FeedItem();
item.setId(id);
item.setChannelId(channel_id);
item.setLink(url);
item.setTitle(title);
item.setDesc(desc);
item.setPubDate(date, time);

allItemList.add(item);
}

// 取得した全アイテムをdate降順に並べ替え
Collections.sort(allItemList, new FeedItemComparator()); 
}
@Override
String getControlBar() {
String controlbar = "";
return controlbar;
}
@Override
StringBuffer getBody() {
StringBuffer out = new StringBuffer();

out.append("
"); if (allItemList.size() == 0) { // 記事がなかった場合 out.append("no items\r\n"); } else { for ( int i = 0; i < allItemList.size(); i++ ){ FeedItem item = allItemList.get(i); // 記事情報 String title = item.getTitle(); if (title.length() > 40 ){ // タイトルが長い場合は省略して表示 title = title.substring(0, 39) + "..."; } String link = item.getLink(); String desc = item.getDesc(); String date = item.getPubDate().get(0); String time = item.getPubDate().get(1); String id = item.getId(); // 出力内容 out.append(""); } // 記事詳細があれば記事詳細 if ( desc != null && !"null".equals(desc) && !"".equals(desc)) { out.append(""); } out.append("
");
out.append(""); // 削除ボタンを out.append(""); // 記事情報 out.append(""); if ( date != null) { out.append(""); } else { out.append("
削除	" + title + "	" + date + " " + time + "
 	" + desc + "
"); } } out.append("");
return out;
}
@Override
String getSQLSelectChannel() {
String SQL = "SELECT id, channel_id, url, title, txt_desc, pub_date FROM bookmarks";
return SQL;
}
@Override
String getClassName() {
String className = "GetBookMarks";
return className;
}
@Override
void initList() {
this.allItemList.clear();
}
}