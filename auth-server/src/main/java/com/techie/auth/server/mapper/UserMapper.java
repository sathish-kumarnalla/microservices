package com.techie.auth.server.mapper;


import com.techie.auth.server.dto.UserDTO;
import com.techie.auth.server.entity.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(UserEntity user);
    List<UserDTO> toDtoList(List<UserEntity> users); // ← Add this here
}