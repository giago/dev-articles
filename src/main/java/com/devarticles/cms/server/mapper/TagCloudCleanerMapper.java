package com.devarticles.cms.server.mapper;

import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.devarticles.cms.server.dao.jdo.JdoTagDao;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;

public class TagCloudCleanerMapper extends AppEngineMapper<Key, Entity, NullWritable, NullWritable> {

    private static final Logger logger = Logger.getLogger(TagCloudCleanerMapper.class.getName());

    private JdoTagDao jdoTagDao;
    
    public TagCloudCleanerMapper() {
        jdoTagDao = new JdoTagDao();
    }

    @Override
    public void map(Key key, Entity value, Context context) {
        logger.info("TagCloudCleanerMapper deleting : " + key.getId());
        jdoTagDao.delete(key.getId());
    }

}
