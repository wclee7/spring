/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.service.impl;

import com.googlecode.genericdao.search.Search;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import crise.studio.common.utils.DateUtil;
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
//        Board board = new Board();
//        board.setId(boardTO.getId());
//        board.setContent(boardTO.getContent());
//        board.setHit(boardTO.getHit());
//        board.setRegDate(LocalDateTime.now());
//        board.setTitle(boardTO.getTitle());                
        Board board = boardMapper.boardTO2Board(boardTO);
        boardDao.save(board);
    }

    @Override
    public BoardTO getOneBoard(Integer boardId) {
        Board board = boardDao.find(boardId);
        board.setSimpleRegDate(DateUtil.getInstance().localDateTimeToSimpleString(board.getRegDate()));
//        BoardTO boardTO = new BoardTO();
//        boardTO.setId(board.getId());
//        boardTO.setContent(board.getContent());
//        boardTO.setHit(board.getHit());
//        boardTO.setRegDate(board.getRegDate().toString());
//        boardTO.setTitle(board.getTitle());
        BoardTO boardTO = boardMapper.board2BoardTO(board);
        return boardTO;
    }

    @Override
    public List<BoardTO> getListBoard() {
        List<Board> boardList = boardDao.search(new Search(Board.class).addSortDesc("regDate"));
        
//        boardList.stream().forEach(board -> 
//                board.setSimpleRegDate(DateUtil.getInstance().localDateTimeToSimpleString(board.getRegDate()))
//        );
        List<BoardTO> boardTOList = boardMapper.boardList2BoardTOList(boardList);
        return boardTOList;
    }

    @Override
    public void removeOfBoard(Integer boardId) {
        boardDao.removeById(boardId);
    }
}
