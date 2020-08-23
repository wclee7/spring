/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.orm.impl;

import crise.studio.model.entity.Authority;
import crise.studio.orm.AuthorityDao;
import crise.studio.orm.BaseDao;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Devel
 */
@Repository
public class AuthorityDaoImpl extends BaseDao<Authority, Integer> implements AuthorityDao{
}
