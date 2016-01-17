package com.zhouchaowei.repositories;

import com.zhouchaowei.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wee
 * @date 12/31/15.
 */

@Repository
@Transactional
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

}
