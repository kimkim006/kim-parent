package com.kim.admin.controller;

import java.util.List;

import com.kim.admin.common.AdminModule;
import com.kim.admin.entity.PostEntity;
import com.kim.admin.service.PostService;
import com.kim.admin.vo.PostVo;
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
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.TokenUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;

/**
 * 职位表控制类
 * @date 2017-11-16 14:28:31
 * @author bo.liu01
 */
@OperateLog(module = AdminModule.POST)
@Controller
@RequestMapping("post")
public class PostController extends BaseController {
	
	@Autowired
	private PostService postService;
	   
	/**
	 * 分页查询
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(PostVo postVo){
		
		return new TableResponse(postService.listByPage(postVo));
	}
	
	/**
	 * 下拉列表查询
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_FOR_COM, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listForCom", method=RequestMethod.GET)
	public ResultResponse listForCom(PostVo postVo){

		List<PostEntity> list = postService.list(postVo);

		return new ObjectResponse(true).data(CollectionUtil.javaList2MapList(list,
				"name", "id"));
	}

	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(PostVo postVo){

		PostEntity postEntity = postService.find(postVo);
		if(postEntity != null){
			return new ObjectResponse(true).data(postEntity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	private String checkAddParam(PostEntity postEntity) {
		PostVo postVo = new PostVo();
		postVo.setName(postEntity.getName().trim());
		postVo.setTenantId(TokenUtil.getTenantId());
		if(postService.find(postVo) !=null){
			return "该职位名称已存在";
		}
		return checkParam(postEntity);
	}
	
	/**
	 * 新增记录
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody PostEntity postEntity) {

		String checkRes = checkAddParam(postEntity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		postEntity = postService.insert(postEntity);
		return new MsgResponse().rel(postEntity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	private String checkParam(PostEntity postEntity) {
		
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	private String checkUpdateParam(PostEntity postEntity) {
		
		PostVo postVo = new PostVo();
		postVo.setId(postEntity.getId());
		postVo.setName(postEntity.getName().trim());
		postVo.setTenantId(TokenUtil.getTenantId());
		if(postService.checkUnique(postVo) !=null){
			return "该职位名称已存在";
		}
		return checkParam(postEntity);
	}
	
	/**
	 * 修改记录
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody PostEntity postEntity) {

		String checkRes = checkUpdateParam(postEntity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = postService.update(postEntity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody PostVo postVo) {

		int n = postService.delete(postVo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
}