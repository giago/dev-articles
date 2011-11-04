package com.devarticles.cms.server.mapper;

import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.devarticles.cms.server.model.Article;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;

public class ArchiveMapper extends AppEngineMapper<Key, Entity, NullWritable, NullWritable> {

    private static final Logger log = Logger.getLogger(ArchiveMapper.class.getName());

//    private JdoArchiveDao jdoArchiveDao;
    
    public ArchiveMapper() {
//        jdoArchiveDao = new JdoArchiveDao();
    }

    @Override
    public void map(Key key, Entity value, Context context) {
        log.info("ArchiveMapper: " + key);
        Long articleId = key.getId();
        String title = (String)value.getProperty(Article.Property.title);
        log.info("articleId : " + articleId + " title : " + title);
        //jdoArchiveDao.save(articleId, title);
    }
}
