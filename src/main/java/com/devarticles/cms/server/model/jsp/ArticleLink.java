
package com.devarticles.cms.server.model.jsp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.devarticles.cms.server.model.Article;

public class ArticleLink implements Serializable {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
            "yyyy-MM-dd hh:mm:ss");

    private static final long serialVersionUID = 1L;

    private String url;

    private String title;

    private String description;

    private Date modifiedDate;

    public ArticleLink(Article article) {
        this.setUrl(article.getTitle().replaceAll(" ", "-") + "-" + article.getId());
        this.setTitle(article.getTitle());
        this.setDescription(article.getDescription());
        this.setModifiedDate(article.getModifiedDate());
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return limit(title);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    private String limit(String title) {
        if (title.length() < 43) {
            return title;
        }
        String limitedUrl = title.substring(0, 40);
        return limitedUrl + "...";
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public String getFormattedModifiedDate() {
        return formatDate(modifiedDate);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        String formatted = DATE_FORMATTER.format(date);
        return formatted.replaceFirst(" ", "T") + "Z";
    }
}
