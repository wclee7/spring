package crise.studio.service.impl;

import com.googlecode.genericdao.search.Search;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import crise.studio.common.util.DateUtil;
import crise.studio.model.TO.BoardTO;
import crise.studio.model.entity.Board;
import crise.studio.model.structmapper.BoardMapper;
import crise.studio.orm.BoardDao;
import crise.studio.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wclee
 */
@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;
    
    @Autowired
    private BoardMapper boardMapper;

    @Override
    public void saveOfBoard(BoardTO boardTO) {
        if (Objects.isNull(boardTO.getId())) {
            boardTO.setRegDate(LocalDateTime.now().toString());
        }
            
        Board board = boardMapper.boardTO2Board(boardTO);
        boardDao.save(board);
    }

    @Override
    public BoardTO getOneBoard(Integer boardId) {
        Board board = boardDao.find(boardId);
        board.setSimpleRegDate(DateUtil.getInstance().localDateTimeToSimpleString(board.getRegDate()));

        BoardTO boardTO = boardMapper.board2BoardTO(board);
        return boardTO;
    }

    @Override
    public List<BoardTO> getListBoard() {
        List<Board> boardList = boardDao.search(new Search(Board.class).addSortDesc("regDate"));
        List<BoardTO> boardTOList = boardMapper.boardList2BoardTOList(boardList);
        return boardTOList;
    }

    @Override
    public void removeOfBoard(Integer boardId) {
        boardDao.removeById(boardId);
    }
}
