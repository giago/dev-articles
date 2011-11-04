
package com.devarticles.cms.server.mapper;

import java.util.List;
import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.devarticles.cms.server.dao.jdo.JdoTagDao;
import com.devarticles.cms.server.model.Article;
import com.devarticles.cms.server.model.Tag;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;

public class TagCloudGeneratorMapper extends AppEngineMapper<Key, Entity, NullWritable, NullWritable> {

    private static final Logger log = Logger.getLogger(TagCloudGeneratorMapper.class.getName());

    private JdoTagDao jdoTagDao;
    
    public TagCloudGeneratorMapper() {
        jdoTagDao = new JdoTagDao();
    }

    @Override
    public void map(Key key, Entity value, Context context) {
        log.info("TagCloudGeneratorMapper Mapping key: " + key);
        @SuppressWarnings("unchecked")
        List<String> tags = (List<String>)value.getProperty(Article.Property.tags);
        for(String tag : tags) {
            jdoTagDao.save(new Tag(tag));
        }
    }

}
