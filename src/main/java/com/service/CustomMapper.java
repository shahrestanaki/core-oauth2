package com.service;

import com.tools.ICustomMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomMapper implements ICustomMapper {
    @Autowired(
            required = false
    )
    private Mapper mapper;

    public CustomMapper() {
    }

    public <T> Collection<T> mapCollection(Collection<?> list, Class<T> viewClass) {
        Set<T> retValues = new HashSet();
        Iterator var5 = list.iterator();

        while(var5.hasNext()) {
            Object entity = var5.next();
            retValues.add(this.onlyMap(entity, viewClass));
        }

        return retValues;
    }

    public <T> List<T> mapList(Collection<?> list, Class<T> viewClass) {
        List<T> retValues = new LinkedList();
        Iterator var5 = list.iterator();

        while(var5.hasNext()) {
            Object entity = var5.next();
            retValues.add(this.onlyMap(entity, viewClass));
        }

        return retValues;
    }

    public <T> T onlyMap(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        } else {
            T model = this.mapper.map(source, destinationClass);
            return model;
        }
    }
}