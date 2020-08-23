/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.service;

import java.util.List;
import crise.studio.model.entity.User;

/**
 *
 * @author Devel
 */
public interface UserService {
    
    
    void saveOfUser(User user);
    
    User getOneUserByEmail(String email);
    
    List<User> getListUser();
}
