/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.orm.impl;

import crise.studio.model.entity.UserGroup;
import crise.studio.orm.BaseDao;
import crise.studio.orm.UserGroupDao;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Devel
 */
@Repository
public class UserGroupDaoImpl extends BaseDao<UserGroup, Integer> implements UserGroupDao{
}
