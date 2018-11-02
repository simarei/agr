package dev.rei.agr.util;
import dev.rei.agr.objects.FeedItem;
/**
* FeedItemオブジェクトを、日付の降順に並べ替えるためのComparator
* 
* @author R.Oshima
*
*/
public class FeedItemComparator implements java.util.Comparator {
/**
* compareメソッドをオーバーライド
*/
public int compare(FeedItem a, FeedItem b) {
return (b.getPubDateString().compareTo(a.getPubDateString())); 
}
}