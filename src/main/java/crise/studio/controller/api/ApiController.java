package crise.studio.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import crise.studio.common.util.ExceptionUtils;
import crise.studio.model.VO.BaseVO;
import crise.studio.model.VO.ResultVO;
import java.util.Arrays;
import java.util.List;
//import crise.studio.facade.TemplateApiFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@Api(description = "랜딩, 편성, 가입안내 관련 API")
@RestController
@RequestMapping(value = "/api/*")
public class ApiController {

//    @Autowired
//    private TemplateApiFacade facade;
    
//    @RestController
//    @ApiIgnore
//    public class ApiTestController {

    @RequestMapping(
        value = "/test",
        method = RequestMethod.GET,
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(value = HttpStatus.OK)
//    @JsonView(Views.TVA.class)
    public BaseVO getBaseFormation() {
        BaseVO baseVO = new BaseVO();
        String[] myArray = { "Apple", "Banana", "Orange" };
        List dataList = Arrays.asList(myArray);
        baseVO.setDataList(dataList);
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg("success");
        baseVO.setResult(resultVO);
        return baseVO;
    }   

    @ApiOperation(value = "랜딩 편성 조회 (with 메타)", notes = "랜딩 편성 조회")
    @ApiResponse(message = "존재하지 않는 플랫폼 요청, 랜딩 편성에 요청한 페이지 정보가 없음", code = 404)
    @RequestMapping(
            value = "/formation/landingWithMeta",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(value = HttpStatus.OK)
    public void landingFormationWithMeta(
        BindingResult result
    ) {
        //ExceptionUtils.invalidParamException(result);
        ExceptionUtils.notExistException(true, result.toString());
        //sreturn facade.landingFormationWithMeta(formationTO);
    }

}
