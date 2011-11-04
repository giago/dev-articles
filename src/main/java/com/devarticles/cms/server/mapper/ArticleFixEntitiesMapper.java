package com.devarticles.cms.server.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;
import com.google.appengine.tools.mapreduce.DatastoreMutationPool;

public class ArticleFixEntitiesMapper extends AppEngineMapper<Key, Entity, NullWritable, NullWritable> {

	private static final SimpleDateFormat YEAR_FORMATTER = new SimpleDateFormat("yyyy");
    private static final Logger logger = Logger.getLogger(TagCloudCleanerMapper.class.getName());

    @Override
    public void map(Key key, Entity entity, Context context) {
        logger.info("Fix Entities : " + key.getId());
        if(entity.hasProperty("createdDate")) {
            Date date = (Date)entity.getProperty("createdDate");
            String year = YEAR_FORMATTER.format(date);
            entity.setProperty("year", Integer.parseInt(year));
        }
        DatastoreMutationPool mutationPool = this.getAppEngineContext(context).getMutationPool();
        mutationPool.put(entity);
    }
    
}
