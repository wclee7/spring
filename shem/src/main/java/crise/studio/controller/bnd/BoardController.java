package crise.studio.controller.bnd;

import crise.studio.model.TO.BoardTO;
import java.util.HashMap;
import java.util.List;
import crise.studio.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author wclee
 */
@Controller
@RequestMapping(value = "/bnd/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //@PreAuthorize(value = "permitAll")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView boardListView() {
        List<BoardTO> boardList = boardService.getListBoard();
        logger.debug("nListSize :: {}", boardList.size() );
        
        ModelAndView modelAndView = new ModelAndView("board/board_list");
        modelAndView.addObject("boardList", boardList);
        modelAndView.addObject("nListSize", boardList.size());        

        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String boardSaveView() {
        return "board/board_save";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public HashMap boardSavePost(BoardTO board) {
        boardService.saveOfBoard(board);
        HashMap<String, String> statusMap = new HashMap<>();
        statusMap.put("code", "N200");
        statusMap.put("message", "게시물이 저장되었습니다.");

        return statusMap;
    }

    @RequestMapping(value = "/detail/{boardId}", method = RequestMethod.GET)
    public ModelAndView boardDetailView(@PathVariable("boardId") Integer boardId) {
        ModelAndView modelAndView = new ModelAndView("board/board_detail");
        modelAndView.addObject("board", boardService.getOneBoard(boardId));

        return modelAndView;
    }

    @RequestMapping(value = "/update/{boardId}", method = RequestMethod.GET)
    public ModelAndView boardUpdateView(@PathVariable("boardId") Integer boardId) {
        ModelAndView modelAndView = new ModelAndView("board/board_update");
        modelAndView.addObject("board", boardService.getOneBoard(boardId));
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public HashMap boardDeletePost(Integer boardId) {
        boardService.removeOfBoard(boardId);
        HashMap<String, String> statusMap = new HashMap<>();
        statusMap.put("code", "N200");
        statusMap.put("message", "게시물이 삭제되었습니다.");

        return statusMap;
    }

}
