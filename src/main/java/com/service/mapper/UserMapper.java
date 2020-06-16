package com.service.mapper;

import com.model.UserInfo;
import com.view.UserView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserView map(UserInfo userInfo);

    default List<UserView> listMap(List<UserInfo> userInfoa) {
        if (userInfoa == null) {
            return null;
        }

        List<UserView> list = new ArrayList<>();

        for (UserInfo userInfo : userInfoa) {
            list.add(map(userInfo));
        }

        return list;
    }
}
