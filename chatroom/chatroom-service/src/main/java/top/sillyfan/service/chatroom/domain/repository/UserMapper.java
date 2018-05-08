package top.sillyfan.service.chatroom.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import top.sillyfan.dao.mapper.BaseMapper;
import top.sillyfan.services.beans.chatroom.domain.model.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User, Long> {

    List<User> list();
}
