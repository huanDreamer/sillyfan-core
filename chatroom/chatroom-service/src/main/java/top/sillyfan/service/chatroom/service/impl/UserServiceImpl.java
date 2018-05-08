package top.sillyfan.service.chatroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sillyfan.service.chatroom.domain.repository.UserMapper;
import top.sillyfan.service.chatroom.service.UserService;
import top.sillyfan.services.beans.chatroom.domain.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Optional<User> getUser(Long id) {
        return Optional.ofNullable(userMapper.selectByPrimaryKey(id));
    }

    @Override
    public User create(User u) {

        userMapper.insert(u);

        return u;
    }

    @Override
    public List<User> list() {
        return userMapper.list();
    }
}
