package dev.rei.agr.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
public class FormatPubDate {

// テスト用
static final String DATESTRING = "Wed, 08 Jan 2014 13:55:00 +0900";
static final String DATESTRINGDC = "2014-01-08T12:45:00+09:00";

// テスト用
public static void main(String[] args){
// String datestr = formatPubDate(DATESTRING);
// System.out.println(datestr);
// 
// String datestr = formatDcDate(DATESTRINGDC);
// System.out.println(datestr);
}
/**
* XMLのpubDateを変換する。
* 
* @param pubDate ex.) "Wed, 08 Jan 2014 13:55:00 +0900"
* @return date ex.) "2014/01/08 13:55:00 JST"
*/
public static ArrayList formatPubDate(String str){

DateFormat input = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
DateFormat outputDate = new SimpleDateFormat("yyyy/MM/dd");
DateFormat outputTime = new SimpleDateFormat("HH:mm");

String dateStr = null;
String timeStr = null;

try {
Date d = input.parse(str);
dateStr = outputDate.format(d);
timeStr = outputTime.format(d);

} catch (ParseException e) {
e.printStackTrace();
}

ArrayList datetime = new ArrayList();
datetime.add(dateStr);
datetime.add(timeStr);
return datetime;
}

/**
* RDFのDcDateを変換する。
* 
* @param pubDate ex.) "2014-01-08T12:45:00+09:00"
* @return date ex.) "2014/01/08 13:55:00 JST"
*/
public static ArrayList formatDcDate(String str){

DateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
DateFormat outputDate = new SimpleDateFormat("yyyy/MM/dd");
DateFormat outputTime = new SimpleDateFormat("HH:mm");

String dateStr = null;
String timeStr = null;

str = str.substring(0, 21);

try {
Date d = input.parse(str);
dateStr = outputDate.format(d);
timeStr = outputTime.format(d);

} catch (ParseException e) {
e.printStackTrace();
}

ArrayList datetime = new ArrayList();
datetime.add(dateStr);
datetime.add(timeStr);
return datetime;
}
}