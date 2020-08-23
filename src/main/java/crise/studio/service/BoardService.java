/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.service;

import crise.studio.model.TO.BoardTO;
import java.util.List;
import crise.studio.model.entity.Board;

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
