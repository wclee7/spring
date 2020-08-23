package crise.studio.controller.bnd;

import crise.studio.model.TO.BbsTO;
import java.util.HashMap;
import java.util.List;
import crise.studio.service.BbsService;
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
@RequestMapping(value = "/free/bbs")
public class BbsController {

    @Autowired
    private BbsService bbsService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //@PreAuthorize(value = "permitAll")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView bbsListView() {
        List<BbsTO> bbsList = bbsService.getListBbs();
        logger.debug("nListSize :: {}", bbsList.size() );
//        for(BoardTO bbsT : bbsList) {
//            logger.debug("bbsTO (in bbsList) :: {}", bbsT.getTitle());
//        }       
        
        ModelAndView modelAndView = new ModelAndView("bbs/listBbs");
        modelAndView.addObject("bbsList", bbsList);
        modelAndView.addObject("nListSize", bbsList.size());        

        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String bbsSaveView() {
        return "bbs/saveBbs";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public HashMap bbsSavePost(BbsTO bbs) {
        bbsService.saveOfBbs(bbs);
        HashMap<String, String> statusMap = new HashMap<>();
        statusMap.put("code", "N200");
        statusMap.put("message", "게시물이 저장되었습니다.");

        return statusMap;
    }

    @RequestMapping(value = "/detail/{bbsId}", method = RequestMethod.GET)
    public ModelAndView bbsDetailView(@PathVariable("bbsId") Integer bbsId) {
        ModelAndView modelAndView = new ModelAndView("bbs/detailBbs");
        modelAndView.addObject("bbs", bbsService.getOneBbs(bbsId));

        return modelAndView;
    }

    @RequestMapping(value = "/update/{bbsId}", method = RequestMethod.GET)
    public ModelAndView bbsUpdateView(@PathVariable("bbsId") Integer bbsId) {
        ModelAndView modelAndView = new ModelAndView("bbs/updateBbs");
        modelAndView.addObject("bbs", bbsService.getOneBbs(bbsId));
        return modelAndView;
    }

//    @RequestMapping(value="/update", method=RequestMethod.POST)
//    public ModelAndView bbsUpdateView(BbsTO bbsTO) {
//        String sWriter = bbsTO.getWriter();
//        String sBpass = bbsTO.getBpass();
//        Integer bbsId =  bbsTO.getId();
//        boolean result = false;
//        Integer iRst = bbsService.getBbsCheckIdPwd(bbsTO);
//        result = iRst>0;
//        if (result) {
//            ModelAndView mav = new ModelAndView("bbs/updateBbs");
//            mav.addObject("bbs", bbsService.getOneBbs(bbsId));
//            return mav;
//        } else {
//            ModelAndView mav = new ModelAndView("/bbs/bIdPwdChkFrm");
//            mav.addObject("bbsId",bbsId);
//            mav.addObject("errChk", "ERR_IDPWD");
//            return mav;
//        }
//    }
    
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public HashMap bbsDeletePost(Integer bbsId) {
        bbsService.removeOfBbs(bbsId);
        HashMap<String, String> statusMap = new HashMap<>();
        statusMap.put("code", "N200");
        statusMap.put("message", "게시물이 삭제되었습니다.");

        return statusMap;
    }

    @RequestMapping(value="/bIdPwdChkFrm/{bbsId}", method=RequestMethod.GET)
    public ModelAndView bIdPwdChkFrm(@PathVariable("bbsId") Integer bbsId) {
        
        ModelAndView mav = new ModelAndView("bbs/bIdPwdChkFrm");
        mav.addObject("bbsId",bbsId);
        return mav;
    }
    
    @RequestMapping(value="/bIdPwdChk", method=RequestMethod.POST)
    @ResponseBody
    public HashMap bIdPwdChk(BbsTO bbsTO) {
        Integer iRst = bbsService.getBbsCheckIdPwd(bbsTO);
        boolean result = iRst>0;
        HashMap<String, String> statusMap = new HashMap<>();
        if (result) {
            statusMap.put("code", "N200");
            statusMap.put("message", "아이디 패스워드가 확인되었습니다!");
        } else {
            statusMap.put("code", "E200");
            statusMap.put("message", "아이디/패스워드를 다시 입력해 주세요!");
        
        }
        return statusMap;
    }
    //bIdPwdChkFrm4del
  @RequestMapping(value="/bIdPwdChkFrm4del/{bbsId}", method=RequestMethod.GET)
    public ModelAndView bIdPwdChkFrm4del(@PathVariable("bbsId") Integer bbsId) {
        
        ModelAndView mav = new ModelAndView("bbs/bIdPwdChkFrm4del");
        mav.addObject("bbsId",bbsId);
        return mav;
    }
    
     @RequestMapping(value = "/deleteNew", method = RequestMethod.POST)
    @ResponseBody
    public HashMap bbsDeletePostNew(BbsTO bbsTO) {
         boolean result = false;
        result = bbsService.removeOfBbsNew(bbsTO);
        logger.debug("bbsService.removeOfBbsNew result ::"+result);
        HashMap<String, String> statusMap = new HashMap<>();
        if (result) {
            statusMap.put("code", "N200");
            statusMap.put("message", "게시물이 삭제되었습니다.");
        } else {
            statusMap.put("code", "E200");
            statusMap.put("message", "아이디/패스워드를 다시 입력해 주세요!");
        }
        return statusMap;
    }
    
}
