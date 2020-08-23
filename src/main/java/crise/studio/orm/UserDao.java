package crise.studio.orm;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import crise.studio.model.entity.Authority;
import crise.studio.model.entity.User;

public interface UserDao extends GenericDAO<User, Integer> {
}
