package com.v2p.swp391.application.mapper;

import com.v2p.swp391.application.model.User;
import com.v2p.swp391.application.request.UserRequest;
import com.v2p.swp391.application.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserHttpMapper {
    UserHttpMapper INSTANCE = Mappers.getMapper(UserHttpMapper.class);
    User toModel(UserRequest request);
    UserRequest toRequest(User request);
    UserResponse toResponse(User request);
}
