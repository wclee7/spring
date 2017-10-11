package crise.studio.orm.impl;

import crise.studio.model.entity.Admin;
import crise.studio.orm.AdminDao;
import crise.studio.orm.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDaoImpl extends BaseDao<Admin, Integer> implements AdminDao {
}
