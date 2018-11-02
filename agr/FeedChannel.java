package dev.rei.agr.objects;
import java.util.ArrayList;
import java.util.Arrays;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
* RSSフィードのチャンネル情報を所持・管理するオブジェクト
* 
* @author R.Oshima
*
*/
public class FeedChannel {
// 定数定義
static final String NODE_NAME_ITEM = "item";
static final String NODE_NAME_CHANNEL = "channel";

private ArrayList tagnames = new ArrayList(Arrays.asList("title","link","pubDate","dc:date","description"));

// フィールド定義
private String channelTitle;
private String channelLink;
private String channelDesc;
private String channelPubDate;
private boolean isActive;


/**
* 空コンストラクタ
*/
public FeedChannel(){
this.channelTitle = null;
this.channelLink = null;
this.channelDesc = null;
this.channelPubDate = null;
}

/**
* コンストラクタ。XMLドキュメントのchannelからFeedChannelオブジェクトを生成する
* 
* @param node
*/
public FeedChannel(Node node) {

// channelノードでなければ何もしない
if ( node == null || node.getNodeName() != NODE_NAME_CHANNEL ) {
return;
}

if ( node.hasChildNodes()) {

// 子ノードのリストを取得
NodeList children = node.getChildNodes();

for ( int i = 0; i < children.getLength(); i++) {

Node child = children.item(i);

// NodeNameでswitchして、タイトル、URLなどをセット
switch (tagnames.indexOf(child.getNodeName())){
case 0 : {
this.channelTitle = child.getTextContent();
break;
}
case 1 : {
this.channelLink = child.getTextContent();
break;
}
case 2 : {
this.channelPubDate = child.getTextContent();
break;
}
case 3 : {
this.channelPubDate = child.getTextContent();
break;
}
case 4 : {
this.channelDesc = child.getTextContent();
break;
}
}
}
}
}


/**
* 
* @return
*/
public String getTitle(){
return this.channelTitle;
}

/**
* 
* @return
*/
public String getLink(){
return this.channelLink;
}
/**
* 
* @return
*/
public String getDesc() {
return this.channelDesc;
}

/**
* 
* @return
*/
public String getPubDate() {
return this.channelPubDate;
}

public boolean getIsActive() {
return this.isActive;
}

public void setIsActive(boolean isActive) {
this.isActive = isActive;
}

}