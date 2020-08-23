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
import crise.studio.model.entity.Notice;
import crise.studio.orm.NoticeDao;
import crise.studio.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Devel
 */
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Override
    public void saveOfNotice(Notice notice) {
        if (Objects.isNull(notice.getId())) {
            notice.setRegDate(LocalDateTime.now());
        }

        noticeDao.save(notice);
    }

    @Override
    public Notice getOneNotice(Integer noticeId) {
        Notice notice = noticeDao.find(noticeId);
        notice.setSimpleRegDate(DateUtil.getInstance().localDateTimeToSimpleString(notice.getRegDate()));
        return notice;
    }

    @Override
    public List<Notice> getListNotice() {
        List<Notice> notices = noticeDao.search(new Search(Notice.class).addSortDesc("regDate"));
        
        notices.stream().forEach(notice -> 
                notice.setSimpleRegDate(DateUtil.getInstance().localDateTimeToSimpleString(notice.getRegDate()))
        );
        return notices;
    }

    @Override
    public void removeOfNotice(Integer noticeId) {
        noticeDao.removeById(noticeId);
    }
}
