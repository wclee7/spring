/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.orm.impl;

import crise.studio.model.entity.User;
import crise.studio.orm.BaseDao;
import crise.studio.orm.UserDao;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Devel
 */
@Repository
public class UserDaoImpl extends BaseDao<User, Integer> implements UserDao{
}
