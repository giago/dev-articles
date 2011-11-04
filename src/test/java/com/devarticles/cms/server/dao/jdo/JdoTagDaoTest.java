package com.devarticles.cms.server.dao.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import com.giago.appengine.commons.test.BaseDatastoreTestCase;
import org.junit.Test;

import com.devarticles.cms.server.model.Tag;

public class JdoTagDaoTest extends BaseDatastoreTestCase {
    
    private JdoTagDao jdoTagDao = new JdoTagDao();
    
    @Test
    public void shouldGetTheMostPopularTag() {
        Tag tag1 = new Tag("gwt");
        tag1.setCounter(new Long(12));
        Tag tag2 = new Tag("eclipse");
        tag2.setCounter(new Long(10));
        jdoTagDao.save(tag1);
        jdoTagDao.save(tag2);
        
        String name = jdoTagDao.getMostPopularTag();
        assertEquals("gwt", name);
    }
    
    @Test
    public void shouldGetTheMostPopularReturnNullWithoutExploding() {
        String name = jdoTagDao.getMostPopularTag();
        assertNull(name);
    }
    
    @Test
    public void shouldGetTheMostCommonTags() {
        Tag tag1 = new Tag("gwt");
        tag1.setCounter(new Long(12));
        Tag tag2 = new Tag("eclipse");
        tag2.setCounter(new Long(10));
        Tag tag3 = new Tag("agile");
        tag3.setCounter(new Long(2));
        Tag tag4 = new Tag("patterns");
        tag4.setCounter(new Long(1));
        jdoTagDao.save(tag2);
        jdoTagDao.save(tag1);
        jdoTagDao.save(tag3);
        jdoTagDao.save(tag4);
        
        List<String> tags = jdoTagDao.getMostCommonTags();
        assertNotNull(tags);
        assertEquals(4, tags.size());
        assertEquals(Arrays.asList("gwt", "eclipse", "agile", "patterns"), tags);
    }

    @Test
    public void shouldGetTheMostCommonTagsWithoutTagsShouldNotExplode() {
        List<String> tags = jdoTagDao.getMostCommonTags();
        assertNotNull(tags);
        assertEquals(0, tags.size());
    }
    

}
