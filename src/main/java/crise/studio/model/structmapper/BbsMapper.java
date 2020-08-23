package crise.studio.model.structmapper;

import crise.studio.model.TO.BbsTO;
import crise.studio.model.entity.Bbs;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BbsMapper {

    BbsTO bbs2BbsTO(Bbs bbs);

    @InheritInverseConfiguration
    Bbs bbsTO2Bbs(BbsTO bbsTO);

    List<BbsTO> bbsList2BbsTOList(List<Bbs> bbsList);

}
