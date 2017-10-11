package crise.studio.service;

import crise.studio.model.TO.BoardTO;
import java.util.List;

/**
 *
 * @author wclee
 */
public interface BoardService {
    public void saveOfBoard(BoardTO board);
    public BoardTO getOneBoard(Integer boardId);
    public List<BoardTO> getListBoard();
    public void removeOfBoard(Integer boardId);
}
