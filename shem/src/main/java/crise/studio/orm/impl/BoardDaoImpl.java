package crise.studio.orm.impl;

import crise.studio.model.entity.Board;
import crise.studio.orm.BaseDao;
import crise.studio.orm.BoardDao;
import org.springframework.stereotype.Repository;

/**
 * @author wclee
 */
@Repository
public class BoardDaoImpl extends BaseDao<Board, Integer> implements BoardDao{
}
