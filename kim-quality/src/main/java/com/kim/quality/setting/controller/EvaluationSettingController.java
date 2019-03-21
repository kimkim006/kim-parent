package com.kim.quality.setting.controller;

import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.quality.common.QualityModule;
import com.kim.quality.setting.entity.EvaluationSettingEntity;
import com.kim.quality.setting.service.EvaluationSettingService;
import com.kim.quality.setting.vo.EvaluationSettingVo;

/**
 * 质检评分选项表控制类
 *
 * @author jianming.chen
 * @date 2018-9-10 14:40:52
 */
@OperateLog(module = QualityModule.EVALUATION_SETTING)
@Controller
@RequestMapping("evaluationSetting")
public class EvaluationSettingController extends BaseController {

    @Autowired
    private EvaluationSettingService evaluationSettingService;

    /**
     * 分页查询
     *
     * @date 2018-9-10 14:40:52
     * @author jianming.chen
     */
    @OperateLog(oper = Operation.QUERY_BY_PAGE, parameterType = ParameterType.QUERY)
    @ResponseBody
    //@RequestMapping(value="listByPage", method=RequestMethod.GET)
    public ResultResponse listByPage(EvaluationSettingVo vo) {

        return new TableResponse(evaluationSettingService.listByPage(vo));
    }


    /**
     * 分页查询
     *
     * @date 2018-9-10 14:40:52
     * @author jianming.chen
     */
    @OperateLog(oper = Operation.QUERY_TREE, parameterType = ParameterType.QUERY)
    @ResponseBody
    @RequestMapping(value = "listTree", method = RequestMethod.GET)
    public ResultResponse listTree(EvaluationSettingVo vo) {

        return new ObjectResponse(true).data(evaluationSettingService.listTree(vo));
    }

    /**
     * 下拉列表查询
     *
     * @date 2017-11-6 14:06:20
     * @author bo.liu01
     */
    @OperateLog(oper = Operation.QUERY_FOR_COM, parameterType = ParameterType.QUERY)
    @ResponseBody
    @RequestMapping(value = "listForCom", method = RequestMethod.POST)
    public ResultResponse listForCom(EvaluationSettingVo vo) {

        return new ObjectResponse(true).data(evaluationSettingService.listForCom(vo));
    }
    
    /**
     * 不带分页的查询（此方法使用时请注意性能）
     * @date 2018-9-10 14:40:52
     * @author jianming.chen
     */
        /* 
        @OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
        @ResponseBody
        @RequestMapping(value="list", method=RequestMethod.GET)
        public ResultResponse list(EvaluationSettingVo vo){
            
            return new ObjectResponse(true).data(evaluationSettingService.list(vo));
        }
        */

    /**
     * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
     *
     * @date 2018-9-10 14:40:52
     * @author jianming.chen
     */
    @OperateLog(oper = Operation.SINGLE_QUERY, parameterType = ParameterType.QUERY)
    @ResponseBody
    @RequestMapping(value = "find", method = RequestMethod.GET)
    public ResultResponse find(EvaluationSettingVo vo) {

        EvaluationSettingEntity entity = evaluationSettingService.find(vo);
        if (entity != null) {
            return new ObjectResponse(true).data(entity);
        } else {
            return new ObjectResponse(false).msg("该记录不存在");
        }
    }

    /**
     * 新增时的参数校验
     *
     * @date 2018-9-10 14:40:52
     * @author jianming.chen
     */
    private String checkAddParam(EvaluationSettingEntity entity) {

        return checkParam(entity);
    }

    /**
     * 新增记录
     *
     * @return
     * @date 2018-9-10 14:40:52
     * @author jianming.chen
     */
    @OperateLog(oper = Operation.ADD, operType = OperationType.INSERT,
            parameterType = ParameterType.BODY)
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultResponse add(@RequestBody EvaluationSettingEntity entity) {

        String checkRes = checkAddParam(entity);
        if (checkRes != null) {
            logger.error(checkRes);
            return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
        }
        entity = evaluationSettingService.insert(entity);
        return new MsgResponse().rel(entity != null);
    }

    /**
     * 新增和修改时的共用参数校验
     *
     * @date 2018-9-10 14:40:52
     * @author jianming.chen
     */
    private String checkParam(EvaluationSettingEntity entity) {

        return null;
    }

    /**
     * 修改时的参数校验
     *
     * @date 2018-9-10 14:40:52
     * @author jianming.chen
     */
    private String checkUpdateParam(EvaluationSettingEntity entity) {

        return checkParam(entity);
    }

    /**
     * 修改记录
     *
     * @return
     * @date 2018-9-10 14:40:52
     * @author jianming.chen
     */
    @OperateLog(oper = Operation.UPDATE, operType = OperationType.UPDATE,
            parameterType = ParameterType.BODY)
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultResponse update(@RequestBody EvaluationSettingEntity entity) {

        String checkRes = checkUpdateParam(entity);
        if (checkRes != null) {
            logger.error(checkRes);
            return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
        }
        if(StringUtil.isBlank(entity.getParentId())) {
            entity.setParentId("noParent");
        }
        int n = evaluationSettingService.update(entity);
        return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
    }

    /**
     * 根据ID删除记录，默认为逻辑删除
     *
     * @date 2018-9-10 14:40:52
     * @author jianming.chen
     */
    @OperateLog(oper = Operation.DELETE, operType = OperationType.DELETE,
            parameterType = ParameterType.BODY)
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultResponse delete(@RequestBody EvaluationSettingVo vo) {

        EvaluationSettingVo tmpvo = new EvaluationSettingVo().tenantId(vo.getTenantId());
        tmpvo.setParentId(vo.getId());
		List<EvaluationSettingEntity> list = evaluationSettingService.list(tmpvo);
        if (CollectionUtil.isNotEmpty(list)) {
            return new MsgResponse(MsgCode.FAIL).msg("该节点下存在子节点，不允许删除");
        }
        
        int n = evaluationSettingService.delete(vo);
        return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
    }

}