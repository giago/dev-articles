package com.devarticles.cms.server.mapper;

import java.util.logging.Logger;

import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;
import com.google.appengine.tools.mapreduce.DatastoreMutationPool;

public class ProfileFixEntitiesMapper extends AppEngineMapper<Key, Entity, NullWritable, NullWritable> {

    private static final Logger logger = Logger.getLogger(TagCloudCleanerMapper.class.getName());

    @Override
    public void map(Key key, Entity entity, Context context) {
        logger.info("Fix Entities : " + key.getId());
      
        entity.setProperty("twitterPluginEnabled", true);
        entity.setProperty("invitePluginEnabled", true);
        
        DatastoreMutationPool mutationPool = this.getAppEngineContext(context).getMutationPool();
        mutationPool.put(entity);
    }
    
}
