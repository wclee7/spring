package crise.studio.model.structmapper;

//import kr.co.tlab.cptv.model.TO.AdminGroupTO;
//import kr.co.tlab.cptv.model.TO.AdminMenuTO;
import crise.studio.model.TO.AdminTO;
import crise.studio.model.entity.Admin;
//import kr.co.tlab.cptv.model.entity.AdminGroup;
//import kr.co.tlab.cptv.model.entity.AdminMenu;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface AdminMapper {

//    @Mappings({ @Mapping(target = "adminGroup") })
    AdminTO admin2AdminTO(Admin admin);

    @InheritInverseConfiguration
    Admin adminTO2Admin(AdminTO adminTO);

//    AdminGroupTO adminGroup2AdminGroupTO(AdminGroup adminGroup);
//
//    AdminGroup adminGroupTO2AdminGroup(AdminGroupTO adminGroupTO);

//    @Mapping(source = "parentMenu",target = "parent")
//    AdminMenuTO adminMenuTO2AdminMenu(AdminMenu adminMenu);
//
//    List<AdminMenuTO> adminMenuList2AdminMenuTOList(List<AdminMenu> adminMenuList);

    List<AdminTO> adminList2AdminTOList(List<Admin> adminList);

}
