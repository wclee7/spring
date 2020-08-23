/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.service;

import java.util.List;
import crise.studio.model.entity.Notice;

/**
 *
 * @author Devel
 */
public interface NoticeService {
    public void saveOfNotice(Notice notice);
    public Notice getOneNotice(Integer noticeId);
    public List<Notice> getListNotice();
    public void removeOfNotice(Integer noticeId);
}
