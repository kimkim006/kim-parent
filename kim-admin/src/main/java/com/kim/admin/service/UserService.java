package com.kim.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kim.admin.entity.UserAgentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.common.CommonConstant;
import com.kim.admin.dao.LoginUserDao;
import com.kim.admin.dao.UserAgentDao;
import com.kim.admin.dao.UserDao;
import com.kim.admin.entity.DepartmentEntity;
import com.kim.admin.entity.DepartmentUserEntity;
import com.kim.admin.entity.TenantPolicyEntity;
import com.kim.admin.entity.UserEntity;
import com.kim.admin.entity.UserPostEntity;
import com.kim.admin.vo.AuthorityVo;
import com.kim.admin.vo.DepartmentUserVo;
import com.kim.admin.vo.UserPostVo;
import com.kim.admin.vo.UserRoleVo;
import com.kim.admin.vo.UserVo;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.base.BaseTable;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.Md5Algorithm;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.impexp.util.DownloadUtil;

/**
 * 用户表服务实现类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Service
public class UserService extends BaseService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private LoginUserDao loginUserDao;
	@Autowired
	private TenantPolicyService tenantPolicyService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DepartmentUserService departmentUserService;
	@Autowired
	private UserPostService userPostService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private UserAgentDao userAgentDao;
	
	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public UserEntity findById(String id) {
		
		return userDao.find(new UserVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param userVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public UserEntity find(UserVo userVo) {
		UserEntity user = userDao.find(userVo);
		if(user != null){
			TenantPolicyEntity tenantPolicy = tenantPolicyService.findByUsername(user.getUsername());
			if(tenantPolicy != null){
				user.setRightTenantId(tenantPolicy.getTenantId());
			}else{
				user.setRightTenantId(user.getTenantId());
			}
			
			if(StringUtil.isNotBlank(user.getDepartCode())){
				List<DepartmentEntity> departs = departmentService.getAllDepartment(user.getTenantId());
				for (DepartmentEntity entity : departs) {
					if(StringUtil.equals(user.getDepartCode(), entity.getCode())){
						user.setDepartName(entity.getFullName());
						break;
					}
				}
			}
		}
		return user;
	}
	
	/**
	 * 带分页的查询
	 * @param userVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public PageRes<UserEntity> listByPage(UserVo userVo) {
		
		PageRes<UserEntity> res = userDao.listByPage(userVo, userVo.getPage());
		if(res.getTotalSize() > 0){
			List<UserEntity> list = res.getList();
			Set<String> tenantIdSet = CollectionUtil.getPropertySet(list, UserEntity::getTenantId);
			List<DepartmentEntity> departs = departmentService.getAllDepartment(new ArrayList<>(tenantIdSet));
			Map<String, DepartmentEntity> departMap = CollectionUtil.getMap(departs, t->{return t.getTenantId() + t.getCode();});
			DepartmentEntity depart;
			for (UserEntity user : list) {
				depart = departMap.get(user.getTenantId() + user.getDepartCode());
				if(depart != null){
					user.setDepartName(depart.getFullName());
				}
			}
		}
		return res;
	}
	
	/**
	 * 不带带分页的查询
	 * @param userVo vo查询对象
	 * @return 
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public List<UserEntity> list(UserVo userVo) {
		
		return userDao.list(userVo);
	}
	
	/**
	 * 不带带分页的查询,可以根据部门ID查询
	 * @param userVo vo查询对象
	 * @return
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public PageRes<UserEntity> listByDepartment(UserVo userVo) {

		return userDao.listByDepartment(userVo, userVo.getPage());
	}
	
	public PageRes<UserEntity> listDepartUser_(UserVo userVo) {
		
		return userDao.listByDepartment_(userVo, userVo.getPage());
	}

	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public UserEntity insert(UserEntity userEntity) {
		
		String defaultPasswd = BaseCacheUtil.getParam(CommonConstant.PARAM_DEFAULT_PASSWORD, userEntity.getTenantId());
		if(StringUtil.isBlank(defaultPasswd)){
			defaultPasswd = CommonConstant.DEFAULT_PASSWD;
		}
		userEntity.setPassword(defaultPasswd);
		if(StringUtil.isBlank(userEntity.getJoinDate())){
			userEntity.setJoinDate(null);
		}
		if(StringUtil.isBlank(userEntity.getLeaveDate())){
			userEntity.setLeaveDate(null);
		}
		int n = userDao.insert(userEntity);
		userEntity.setPassword(Md5Algorithm.getInstance().md5Encode(userEntity.getPassword()));
		loginUserDao.insert(userEntity);
		
		updateTenantPolicy(userEntity, false);
		
		insertDepart(userEntity);

		insertPost(userEntity);
		
		insertAgent(userEntity);
		
		return n > 0 ? userEntity : null;
	}
	
	private int insertAgent(UserEntity userEntity){
		if(StringUtil.isNotBlank(userEntity.getCiscoAgentNo())){
			UserAgentEntity entity = new UserAgentEntity();
			entity.copyBaseField(userEntity);
			entity.setAtvFlag(BaseTable.ATV_FLAG_YES);
			entity.setUsername(userEntity.getUsername());
			entity.setAgentNo(userEntity.getCiscoAgentNo());
			entity.setPlatform(CommonConstant.PLATFORM_CISCO);
			return userAgentDao.insert(entity);
		}
		return 0;
	}
	
	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int importBatch(List<UserEntity> list) {
		
		List<UserEntity> userList = userDao.listAll();
		Map<String, UserEntity> userMap = CollectionUtil.getMapByProperty(userList, "username");
		
		String tenantId = TokenUtil.getTenantId();
		String operUser = TokenUtil.getUsername();
		String operName = TokenUtil.getCurrentName();
		
		UserEntity origUser = null;
		for (UserEntity entity : list) {
			entity.setTenantId(tenantId);
			entity.setOperName(operName);
			entity.setOperUser(operUser);
			
			origUser = userMap.get(entity.getUsername());
			if(origUser == null){
				entity.setStatus(UserEntity.STATUS_NORMAL);
				entity.setOrigins(UserEntity.ORIGINS_ICM_IMPORT);
				insert(entity);
			}else{
				if(StringUtil.equals(origUser.getTenantId(), tenantId)){
					entity.setId(origUser.getId());
					updateInfo(entity);
				}else{
					entity.addRes("该用户名已存在");
				}
			}
		}
		return list.size();
	}

	private void updateTenantPolicy(UserEntity userEntity, boolean needClear) {
		if(StringUtil.isNotBlank(userEntity.getRightTenantId())){
			TenantPolicyEntity entity = new TenantPolicyEntity();
			entity.setUsername(userEntity.getUsername());
			entity.setActualTenantId(userEntity.getTenantId());
			entity.setTenantId(userEntity.getRightTenantId());
			entity.setOperName(userEntity.getOperName());
			entity.setOperUser(userEntity.getOperUser());
			entity.setIsCurrent(TenantPolicyEntity.IS_CURRENT_YES);
			tenantPolicyService.insert(entity);
		}else{
			if(needClear){
				tenantPolicyService.clearCurrent(userEntity.getUsername());
			}
		}
	}

	/**
	 * 插入部门
	 * @param userEntity
	 */
	private void insertDepart(UserEntity userEntity) {
		if(StringUtil.isNotBlank(userEntity.getDepartCode())){
			//插入部门
			DepartmentUserEntity departmentUser = new DepartmentUserEntity();
			departmentUser.setUsername(userEntity.getUsername());
			departmentUser.setDepartmentCode(userEntity.getDepartCode());
			departmentUser.copyBaseField(userEntity);
			departmentUserService.insert(departmentUser);
		}
	}

	/**
	 * 插入职位
	 * @param userEntity
	 */
	private void insertPost(UserEntity userEntity) {
		if(StringUtil.isNotBlank(userEntity.getPostId())){
			//插入职位
			UserPostEntity userPost = new UserPostEntity();
			userPost.setUsername(userEntity.getUsername());
			userPost.setPostId(userEntity.getPostId());
			userPost.copyBaseField(userEntity);
			userPostService.insert(userPost);
		}
	}
	
	private int updateInfo(UserEntity userEntity) {
		int n = userDao.update(userEntity);
		if(n > 0 && StringUtil.isNotBlank(userEntity.getCiscoAgentNo())){
			UserAgentEntity entity = new UserAgentEntity();
			entity.copyBaseField(userEntity);
			entity.setUsername(userEntity.getUsername());
			entity.setAgentNo(userEntity.getCiscoAgentNo());
			entity.setPlatform(CommonConstant.PLATFORM_CISCO);
			userAgentDao.update(entity);
		}
		return n;
	}

	/**
	 * 修改记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int uploadPortrait(String username, String path) {
		UserEntity user = new UserEntity();
		user.setTenantId(TokenUtil.getActualTenantId());
		user.setUsername(username);
		user.setPortrait(path);
		int n = userDao.updatePortrait(user);
		if(n > 0){
			user.setSign(DownloadUtil.sign(user.getPortrait()));
			user.setPath(DownloadUtil.encode(user.getPortrait()));
			TokenUtil.setLoginInfo(TokenUtil.getToken(), user);
		}
		return n;
	}
	/**
	 * 修改记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(UserEntity userEntity) {

		int n = updateInfo(userEntity);
		loginUserDao.update(userEntity);
		
		updateTenantPolicy(userEntity, true);
		
		//判断是否需要更新部门
		DepartmentUserVo departmentUserVo = new DepartmentUserVo();
		departmentUserVo.setUsername(userEntity.getUsername());
		departmentUserVo.setTenantId(userEntity.getTenantId());
		if(StringUtil.isNotBlank(userEntity.getDepartCode())){
			departmentUserService.deleteLogicByUser(departmentUserVo);
			departmentUserVo.setDepartmentCode(userEntity.getDepartCode());
			insertDepart(userEntity);
		}else{
			departmentUserService.deleteLogicByUser(departmentUserVo);
		}
		
		//判断是否需要更新职位
		UserPostVo userPostVo = new UserPostVo();
		userPostVo.setUsername(userEntity.getUsername());
		userPostVo.setTenantId(userEntity.getTenantId());
		if(StringUtil.isNotBlank(userEntity.getPostId())){
			userPostService.delete(userPostVo);
			userPostVo.setPostId(userEntity.getPostId());
			insertPost(userEntity);
		}else{
			userPostService.delete(userPostVo);
		}
		
		return n;
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(UserVo userVo) {

		String[] usernames = userVo.getUsernames().split(",");
		UserVo v = new UserVo();
		v.setTenantId(TokenUtil.getTenantId());
		int n = 0;
		for (String username : usernames) {
			v.setUsername(username);
			n += deleteSingle(v);
		}
		return n;
	}
	
	private int deleteSingle(UserVo v){
		if(userDao.deleteLogic(v) > 0){
			loginUserDao.deleteLogic(v);
			//删除职位
			userPostService.delete(v.getUsername());
			//删除部门
			DepartmentUserVo departmentUserVo = new DepartmentUserVo();
			departmentUserVo.setUsername(v.getUsername());
			departmentUserVo.setTenantId(v.getTenantId());
			departmentUserService.deleteLogicByUser(departmentUserVo);
			//删除角色
			UserRoleVo userRoleVo = new UserRoleVo();
			userRoleVo.setUsernames(v.getUsername());
			userRoleVo.setTenantId(v.getTenantId());
			userRoleService.delete(userRoleVo);
			//删除权限
			AuthorityVo authorityVo = new AuthorityVo();
			authorityVo.setUsername(v.getUsername());
			authorityVo.setTenantId(v.getTenantId());
			authorityService.delete(authorityVo);
			return 1;
		}
		return 0;
	}

	public PageRes<UserEntity> listByRole(UserVo userVo) {
		
		return userDao.listByRole(userVo, userVo.getPage());
	}

	public PageRes<UserEntity> listByRole_(UserVo userVo) {
		
		return userDao.listByRole_(userVo, userVo.getPage());
	}

	public PageRes<UserEntity> listByGroup(UserVo userVo) {

		return userDao.listByGroup(userVo, userVo.getPage());
	}
	
	public PageRes<UserEntity> listByGroup_(UserVo userVo) {
		
		return userDao.listByGroup_(userVo, userVo.getPage());
	}

}
