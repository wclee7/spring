/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.orm.impl;

import crise.studio.model.entity.Bbs;
import crise.studio.orm.BaseDao;
import crise.studio.orm.BbsDao;
import org.springframework.stereotype.Repository;


/**
 *
 * @author wclee
 */
@Repository
public class BbsDaoImpl extends BaseDao<Bbs, Integer> implements BbsDao{
}
