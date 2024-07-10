package org.example.mapper;

import org.example.entity.User;
import org.example.entity.Workplace;
import org.example.model.UserDTO;
import org.example.model.WorkplaceDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        if (users == null) {
            return null;
        } else {
            List<UserDTO> list = new ArrayList(users.size());
            Iterator var3 = users.iterator();

            while(var3.hasNext()) {
                User user = (User)var3.next();
                list.add(this.userToUserDTO(user));
            }

            return list;
        }
    }
}
