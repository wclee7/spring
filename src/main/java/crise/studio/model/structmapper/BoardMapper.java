package crise.studio.model.structmapper;

import crise.studio.model.TO.BoardTO;
import crise.studio.model.entity.Board;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface BoardMapper {

    BoardTO board2BoardTO(Board board);

    @InheritInverseConfiguration
    Board boardTO2Board(BoardTO boardTO);

    List<BoardTO> boardList2BoardTOList(List<Board> boadList);

}
