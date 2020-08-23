package crise.studio.common.handler;

import com.google.common.collect.Lists;
import com.googlecode.genericdao.search.Search;
import crise.studio.model.common.Enums;
import crise.studio.model.entity.*;
import crise.studio.orm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;

@Component
public class PostConstructHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${db.is-insert-necessary-datas}")
    private boolean isInsertDatas;

    @Autowired
    private HibernateTransactionManager hibernateTransactionManager;
    
    @Autowired
    private AdminDao adminDao;

    private TransactionCallbackWithoutResult transactionCallbackWithoutResult = new TransactionCallbackWithoutResult() {
        @Override
        protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
           
            this.saveAdmin();           
        }
        

        /**
         * 기본 관리자 테스트 계정
         */
        private void saveAdmin() {
//            Search s = new Search(AdminGroup.class);
//            s.addFilterEqual("name", "개발 테스트");

//            AdminGroup adminGroup = adminGroupDao.searchUnique(s);

            Admin admin = new Admin();
            admin.setAccount("devtest");
            admin.setPassword("$2a$10$bkmd.2FQthbSIQqFnQKhYuBrCxR8h84j.MtT.tE0uF3h5WIS3GijG");
            admin.setName("개발테스트");
            admin.setIsActive(true);
  //          admin.setAdminGroup(adminGroup);

             adminDao.save(admin);
        }       
    };

    @PostConstruct
    public void insertNecessaryDBData() {
        logger.debug("#### insertNecessaryDBData isInsertDatas :: {}", isInsertDatas);

        if (isInsertDatas == false) { return; }

        TransactionTemplate transactionTemplate = new TransactionTemplate(hibernateTransactionManager);
        transactionTemplate.execute(transactionCallbackWithoutResult);
    }
}
