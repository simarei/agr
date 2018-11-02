package dev.rei.agr.objects;
import java.util.ArrayList;
import java.util.Arrays;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import dev.rei.agr.util.FormatPubDate;
/**
* RSSフィードのアイテム（記事）情報を所持・管理するオブジェクト
* 
* @author R.Oshima
*
*/
public class FeedItem {
// 定数定義
static final String NODE_NAME_ITEM = "item";

private ArrayList tagnames = new ArrayList(Arrays.asList("title","link","category","pubDate","dc:date","description"));

// フィールド定義
private String channelId;
private String itemTitle;
private String itemLink;
private String itemCategory;
private String itemDesc;
private ArrayList itemPubDate = new ArrayList();
private String itemId;
private boolean isRead;

/**
* 空コンストラクタ
*/
public FeedItem(){
this.channelId = null;
this.itemTitle = null;
this.itemLink = null;
this.itemCategory = null;
this.itemDesc = null;
this.itemPubDate = new ArrayList();
this.isRead = false;
this.itemId = null;
}

/**
* コンストラクタ。XMLドキュメントのitemノードからFeedItemオブジェクトを生成する
* 
* @param rootNode
*/
public FeedItem(Node node, String channel_id) {

if ( node == null || node.getNodeName() != NODE_NAME_ITEM ) {
return;
}

if ( node.hasChildNodes()) {
NodeList children = node.getChildNodes();
for ( int i = 0; i < children.getLength(); i++) {


// NodeNameでswitchして、タイトル、URLなどをセット
Node child = children.item(i);
switch (tagnames.indexOf(child.getNodeName())){
case 0 : {
this.itemTitle = child.getTextContent();
break;
}
case 1 : {
this.itemLink = child.getTextContent();
break;
}
case 2 : {
this.itemCategory = child.getTextContent();
break;
}
case 3 : {
this.itemPubDate = FormatPubDate.formatPubDate(child.getTextContent());;
break;
}
case 4 : {
this.itemPubDate = FormatPubDate.formatDcDate(child.getTextContent());;
break;
}
case 5 : {
this.itemDesc = child.getTextContent();
break;
}
}
this.isRead = false;
this.channelId = channel_id;
}
}
}

/**
* 
* @return
*/
public String getTitle(){
return this.itemTitle;
}

/**
* 
* @return
*/
public String getLink(){
return this.itemLink;
}
/**
* 
* @return
*/
public String getCategory() {
return this.itemCategory;
}

/**
* 
* @return
*/
public String getDesc() {
return this.itemDesc;
}

/**
* 
* @return
*/
public ArrayList getPubDate() {
return this.itemPubDate;
}

public String getPubDateString() {
String dateStr = "";
dateStr += this.itemPubDate.get(0);
dateStr += this.itemPubDate.get(1);
return dateStr;
}

/**
* 
* @return
*/
public boolean getIsRead() {
return this.isRead;
}

/**
* 
* @return
*/
public String getChannelId() {
return this.channelId;
}


public String getId() {
return this.itemId;
}

/**
* 
* @param title
*/
public void setTitle(String title) {
this.itemTitle = title;
}

/**
* 
* @param link
*/
public void setLink(String link) {
this.itemLink = link;
}

/**
* 
* @param ctgr
*/
public void setCtgr(String ctgr) {
this.itemCategory = ctgr;
}

/**
* 
* @param desc
*/
public void setDesc(String desc) {
this.itemDesc = desc;
}

/**
* 
* @param date
*/
public void setPubDate(String date,String time) {
this.itemPubDate.clear();
this.itemPubDate.add(date);
this.itemPubDate.add(time);
}
/**
* 
* @param isRead
*/
public void setIsRead(String isRead) {
if ("1".equals(isRead)) {
this.isRead = true;
} else if ("0".equals(isRead)) {
this.isRead = false;
}
}

/**
* 
* @param isReadInt
*/
public void setIsRead(int isReadInt) {
if (isReadInt == 1) {
this.isRead = true;
} else if (isReadInt == 0) {
this.isRead = false;
}
}

/**
* 
* @param isRead
*/
public void setIsRead(boolean isRead) {
this.isRead = isRead;
}

public void setChannelId(String channel_id) {
this.channelId = channel_id;
}

public void setId(String id) {
this.itemId = id;
}
}