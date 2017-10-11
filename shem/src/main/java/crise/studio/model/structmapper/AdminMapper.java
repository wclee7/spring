package crise.studio.model.structmapper;

import crise.studio.model.TO.AdminTO;
import crise.studio.model.entity.Admin;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper
public interface AdminMapper {

    AdminTO admin2AdminTO(Admin admin);

    @InheritInverseConfiguration
    Admin adminTO2Admin(AdminTO adminTO);

    List<AdminTO> adminList2AdminTOList(List<Admin> adminList);
}
