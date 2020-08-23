package crise.studio.service;

import crise.studio.model.TO.BbsTO;
import java.util.List;

/**
 *
 * @author wclee
 */
public interface BbsService {
    public void saveOfBbs(BbsTO bbs);
    public BbsTO getOneBbs(Integer bbsId);
    public List<BbsTO> getListBbs();
    public void removeOfBbs(Integer bbsId);
    //add
    public int getBbsCheckIdPwd(BbsTO bbsTO);
    public boolean removeOfBbsNew(BbsTO bbsTO);
}
