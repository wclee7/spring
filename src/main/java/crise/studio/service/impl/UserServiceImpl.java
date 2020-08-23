/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.service.impl;

import com.googlecode.genericdao.search.Search;
import java.util.List;
import crise.studio.model.entity.User;
import crise.studio.orm.UserDao;
import crise.studio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Devel
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
    
    @Override
    public void saveOfUser(User user) {
        userDao.save(user);
    }

    @Override
    public User getOneUserByEmail(String email) {
        return userDao.searchUnique(new Search(User.class).addFilterEqual("email", email));
    }

    @Override
    public List<User> getListUser() {
        return userDao.findAll();
    }
    
}
