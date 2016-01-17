package com.zhouchaowei.repositories;

import com.zhouchaowei.models.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wee
 * @date 1/11/16.
 */
@Repository
@Transactional
public interface SettingRepository extends JpaRepository<Setting, Long> {

    Setting findByKey(String key);


}
