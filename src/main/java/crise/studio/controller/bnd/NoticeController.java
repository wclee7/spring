/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.controller.bnd;

import java.util.HashMap;
import java.util.List;
import crise.studio.common.utils.DateUtil;
import crise.studio.model.entity.Notice;
import crise.studio.service.NoticeService;
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
 * @author Devel
 */
@Controller
@RequestMapping(value = "/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView noticeListView() {
        List<Notice> notices = noticeService.getListNotice();

        ModelAndView modelAndView = new ModelAndView("notice/notice_list");
        modelAndView.addObject("noticeList", notices);
        modelAndView.addObject("nListSize", notices.size());

        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String noticeSaveView() {
        return "notice/notice_save";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public HashMap noticeSavePost(Notice notice) {
        noticeService.saveOfNotice(notice);
        HashMap<String, String> statusMap = new HashMap<>();
        statusMap.put("code", "N200");
        statusMap.put("message", "공지사항이 저장되었습니다.");

        return statusMap;
    }

    @RequestMapping(value = "/detail/{noticeId}", method = RequestMethod.GET)
    public ModelAndView noticeDetailView(@PathVariable("noticeId") Integer noticeId) {
        ModelAndView modelAndView = new ModelAndView("notice/notice_detail");
        modelAndView.addObject("notice", noticeService.getOneNotice(noticeId));

        return modelAndView;
    }

    @RequestMapping(value = "/update/{noticeId}", method = RequestMethod.GET)
    public ModelAndView noticeUpdateView(@PathVariable("noticeId") Integer noticeId) {
        ModelAndView modelAndView = new ModelAndView("notice/notice_update");
        modelAndView.addObject("notice", noticeService.getOneNotice(noticeId));
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public HashMap noticeDeletePost(Integer noticeId) {
        noticeService.removeOfNotice(noticeId);
        HashMap<String, String> statusMap = new HashMap<>();
        statusMap.put("code", "N200");
        statusMap.put("message", "공지사항이 삭제되었습니다.");

        return statusMap;
    }

}
