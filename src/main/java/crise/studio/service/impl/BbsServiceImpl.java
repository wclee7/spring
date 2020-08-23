package crise.studio.service.impl;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import crise.studio.common.utils.DateUtil;
import crise.studio.model.TO.BbsTO;
import crise.studio.model.entity.Bbs;
import crise.studio.model.structmapper.BbsMapper;
import crise.studio.orm.BbsDao;
import crise.studio.service.BbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wclee
 */
@Service
@Transactional
public class BbsServiceImpl implements BbsService {

    @Autowired
    private BbsDao bbsDao;
    
    @Autowired
    private BbsMapper bbsMapper;

    @Override
    public void saveOfBbs(BbsTO bbsTO) {
        if (Objects.isNull(bbsTO.getId())) {
            bbsTO.setRegDate(LocalDateTime.now().toString());
        }
//        Board board = new Board();
//        board.setId(boardTO.getId());
//        board.setContent(boardTO.getContent());
//        board.setHit(boardTO.getHit());
//        board.setRegDate(LocalDateTime.now());
//        board.setTitle(boardTO.getTitle());                
        Bbs bbs = bbsMapper.bbsTO2Bbs(bbsTO);
        bbsDao.save(bbs);
    }

    @Override
    public BbsTO getOneBbs(Integer bbsId) {
        Bbs bbs = bbsDao.find(bbsId);
        bbs.setSimpleRegDate(DateUtil.getInstance().localDateTimeToSimpleString(bbs.getRegDate()));
//        BoardTO boardTO = new BoardTO();
//        boardTO.setId(board.getId());
//        boardTO.setContent(board.getContent());
//        boardTO.setHit(board.getHit());
//        boardTO.setRegDate(board.getRegDate().toString());
//        boardTO.setTitle(board.getTitle());
        BbsTO bbsTO = bbsMapper.bbs2BbsTO(bbs);
        return bbsTO;
    }

    @Override
    public List<BbsTO> getListBbs() {
        List<Bbs> bbsList = bbsDao.search(new Search(Bbs.class).addSortDesc("regDate"));
        
//        boardList.stream().forEach(board -> 
//                board.setSimpleRegDate(DateUtil.getInstance().localDateTimeToSimpleString(board.getRegDate()))
//        );
        List<BbsTO> bbsTOList = bbsMapper.bbsList2BbsTOList(bbsList);
        return bbsTOList;
    }

    @Override
    public void removeOfBbs(Integer bbsId) {
        bbsDao.removeById(bbsId);
    }
    
    @Override
    public int getBbsCheckIdPwd(BbsTO bbsTO) {
        Search search = new Search();
        Filter fWriter = Filter.equal("writer", bbsTO.getWriter());
        Filter fBpass = Filter.equal("bpass", bbsTO.getBpass());
        Filter fBid = Filter.equal("id", bbsTO.getId());
        search.addFilterAnd(fBid, fWriter, fBpass);
        Integer i = bbsDao.count(search);
        return i;
    }
    
     @Override
    public boolean removeOfBbsNew(BbsTO bbsTO) {
        Search search = new Search();
        Filter fWriter = Filter.equal("writer", bbsTO.getWriter());
        Filter fBpass = Filter.equal("bpass", bbsTO.getBpass());
        Filter fBid = Filter.equal("id", bbsTO.getId());
        search.addFilterAnd(fBid, fWriter, fBpass);
        Integer i = bbsDao.count(search);
        boolean bRst = false;
        if (i>0) {
            Bbs bbs = bbsMapper.bbsTO2Bbs(bbsTO);        
            bRst = bbsDao.remove(bbs);
        }
        return bRst;
    }
}
