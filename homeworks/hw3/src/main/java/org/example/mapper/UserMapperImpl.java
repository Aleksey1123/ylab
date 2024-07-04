package org.example.mapper;

import org.example.entity.User;
import org.example.model.UserDTO;

public class UserMapperImpl implements UserMapper {
    public UserMapperImpl() {
    }

    public UserDTO userToUserDTO(User user) {
        if (user == null) {
            return null;
        } else {
            UserDTO.UserDTOBuilder userDTO = UserDTO.builder();
            if (user.getId() != null) {
                userDTO.id(user.getId());
            }

            userDTO.username(user.getUsername());
            userDTO.password(user.getPassword());
            return userDTO.build();
        }
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User.UserBuilder user = User.builder();
            user.id(userDTO.getId());
            user.username(userDTO.getUsername());
            user.password(userDTO.getPassword());
            return user.build();
        }
    }
}
