package crise.studio.service;

import crise.studio.model.TO.AdminTO;

public interface AdminService {

    AdminTO getOneAdminByAccount(String account);
    AdminTO getOneAdminWithAllByAccount(String account);
}
