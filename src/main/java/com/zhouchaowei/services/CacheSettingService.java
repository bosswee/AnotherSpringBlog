package com.zhouchaowei.services;

import com.zhouchaowei.models.Setting;
import com.zhouchaowei.repositories.SettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author wee
 * @date 1/11/16.
 */
@Service
public class CacheSettingService implements SettingService {

    private SettingRepository settingRepository;


    @Autowired
    public CacheSettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(SettingService.class);

    @Override
    public Serializable get(String key) {
        Setting setting = settingRepository.findByKey(key);
        Serializable value = null;
        try {
            value = setting == null ? null : setting.getValue();
        } catch (Exception ex) {
            logger.info("Cannot deserialize setting value with key = " + key);
        }

        logger.info("Get setting " + key + " from database. Value = " + value);

        return value;

    }

    @Override
    public Serializable get(String key, Serializable defaultValue) {
        Serializable value = get(key);
        return value == null ? defaultValue : value;
    }

    @Override
    public void put(String key, Serializable value) {
        logger.info("Update setting " + key + " to database. Value = " + value);

        Setting setting = settingRepository.findByKey(key);
        if (setting == null) {
            setting = new Setting();
            setting.setKey(key);
        }
        try {
            setting.setValue(value);
            settingRepository.save(setting);
        } catch (Exception ex) {

            logger.info("Cannot save setting value with type: " + value.getClass() + ". key = " + key);
        }
    }
}
