package com.yf.mynote.utils;

import android.provider.Settings;
import android.util.Log;

import com.yf.mynote.model.Book;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */

public class JsonpUtils {
    /**
     * @param doc
     * @return  解析简书精选文章
     */
    public static List<Book> getBookList(Document doc) {
        List<Book>  books=new ArrayList<>();
        Elements lis = doc.getElementsByClass("have-img");
        for (int i = 0; i<lis.size() ; i++) {
            Element author=lis.get(i).getElementsByClass("author-name").get(0);
            Elements title= lis.get(i).getElementsByClass("title").get(0).getElementsByTag("a");
            String time=lis.get(i).getElementsByClass("time").get(0).attr("data-shared-at");
            String img=lis.get(i).getElementsByTag("img").get(0).attr("src");
            String auth = author.text();
             String titleName=title.text();
            String link=title.attr("href");
            Book book=new Book();
            book.setTitle(titleName);
            book.setAuto(auth);
            book.setLink(link);
            book.setImage(img);
            String t=time.split("T")[0];
            book.setTime(t);
            books.add(book);
        }
        return books;
    }

    /**
     * @param doc
     * @return   解析文章详细信息
     */
    public static Book getBookDetail(Document doc) {
       String title=doc.getElementsByTag("h1").get(0).text();
        Element bodyEle=doc.getElementsByClass("show-content").get(0);
        String body=resetImage(bodyEle);
        Book book=new Book();
        book.setBody(body);
        book.setTitle(title);
        return book;
    }

    public static String resetImage(Element bodyEle){
        Elements img = bodyEle.getElementsByTag("img");
        img.attr("width","100%");
        img.attr("height","auto");
        return  bodyEle.html();
    }


}
