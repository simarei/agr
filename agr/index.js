function init() {
}
function display_feed() {
document.all['new_feeds'].style.display = 'block';
document.all['bookmarks'].style.display = 'none';
document.all['channels'].style.display = 'none';
document.getElementById('th_feeds').style.color = "#FFFFFF";
document.getElementById('th_feeds').style.backgroundColor = "#78BDC6";
document.getElementById('th_bookmarks').style.color = "#78BDC6";
document.getElementById('th_bookmarks').style.backgroundColor = "#FFFFFF";
document.getElementById('th_channels').style.color = "#78BDC6";
document.getElementById('th_channels').style.backgroundColor = "#FFFFFF";
}
function display_channel() {
document.all['new_feeds'].style.display = 'none';
document.all['channels'].style.display = 'block';
document.all['bookmarks'].style.display = 'none';
document.getElementById('th_feeds').style.color = "#78BDC6";
document.getElementById('th_feeds').style.backgroundColor = "#FFFFFF";
document.getElementById('th_bookmarks').style.color = "#78BDC6";
document.getElementById('th_bookmarks').style.backgroundColor = "#FFFFFF";
document.getElementById('th_channels').style.color = "#FFFFFF";
document.getElementById('th_channels').style.backgroundColor = "#78BDC6";
}
function display_bookmark() {
document.all['new_feeds'].style.display = 'none';
document.all['bookmarks'].style.display = 'block';
document.all['channels'].style.display = 'none';
document.getElementById('th_feeds').style.color = "#78BDC6";
document.getElementById('th_feeds').style.backgroundColor = "#FFFFFF";
document.getElementById('th_bookmarks').style.color = "#FFFFFF";
document.getElementById('th_bookmarks').style.backgroundColor = "#78BDC6";
document.getElementById('th_channels').style.color = "#78BDC6";
document.getElementById('th_channels').style.backgroundColor = "#FFFFFF";
}
function turn_isActive(channel_id, flg) {
// channel_idで該当する行のis_activeを、flgの値で更新する

$.post(
"update_channel_active",
{id : channel_id, is_active :flg},
function() {
location.reload();
}
);
}
function insert_channel() {
var url = document.getElementById('insert_url').value;
if ( url == undefined ) {
return;
} else {
$.post(
"insert_channel",
{url: url},
function() {
location.reload();
document.getElementById('insert_url').value = "";
}
);

}
}
function delete_channel(id) {
// TODO titleを渡そうとすると、Yahooニュースにあるハイフンのせいか反応しない。暫定的にタイトルなし
// if (confirm("以下のチャンネルを削除します。よろしいですか？

" + title)) {
if (confirm("チャンネルを削除します。よろしいですか？")) {
$.post(
"delete_channel",
{id : id},
function() {
location.reload();
}
);
}
}
function insert_bookmark(url,title,desc,pubdate,channel_id) {

if ( url == undefined ) {
return;
} else {
$.post(
"handle_feeds",
{
mode: "insert",
url: url,
title: title,
desc: desc,
pubdate: pubdate,
channel_id: channel_id
},
function() {
parent.frames.bookmark_list.location.reload();
}
);
}
}
function delete_bookmark(id) {
$.post(
"handle_feeds",
{
mode: "delete",
id : id
},
function() {
location.reload();
}
);
}