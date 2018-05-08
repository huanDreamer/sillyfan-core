package top.sillyfan.service.chatroom.service;


import top.sillyfan.services.beans.chatroom.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUser(Long id);

    User create(User u);

    List<User> list();

}
