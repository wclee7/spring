package crise.studio.orm;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import crise.studio.model.entity.Board;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wclee
 */
public interface BoardDao extends GenericDAO<Board, Integer> {
}
