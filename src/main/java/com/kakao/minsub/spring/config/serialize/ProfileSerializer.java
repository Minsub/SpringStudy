package com.kakao.minsub.spring.config.serialize;

import com.google.common.collect.Maps;
import com.kakao.minsub.spring.model.Profile;

import java.util.Map;

public class ProfileSerializer extends ResponseMapSerializer<Profile> {
    
    @Override
    public Map<String, Object> serialize(final Profile profile) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", profile.getId());
        map.put("name", profile.getName());
        map.put("searchId", profile.getSearchId());
        map.put("serialize_test", true);
        return map;
    }
}
