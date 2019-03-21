package com.kim.admin.service;

import java.util.List;

import com.kim.admin.dao.GroupUserDao;
import com.kim.admin.entity.GroupEntity;
import com.kim.admin.vo.GroupUserVo;
import com.kim.admin.vo.UserOrgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.dao.UserOrgDao;
import com.kim.admin.entity.GroupUserEntity;
import com.kim.admin.entity.UserOrgEntity;
import com.kim.base.service.BaseUserGroupService;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.NumberUtil;

/**
 * 质检小组人员表服务实现类
 *
 * @author jianming.chen
 * @date 2018-8-21 10:28:49
 */
@Service
public class GroupUserService extends BaseService {

    @Autowired
    private GroupUserDao groupUserDao;
    @Autowired
    private BaseUserGroupService baseUserGroupService;
    @Autowired
    private UserOrgDao userOrgDao;

    private void removeUserOrg(GroupUserVo groupUserVo, String[] usernames) {
		if(NumberUtil.equals(GroupEntity.USER_TYPE_LEADER, groupUserVo.getType())){
			UserOrgVo userOrg = new UserOrgVo().tenantId(groupUserVo.getTenantId());
    		userOrg.setUpperSuperior(groupUserVo.getUsername());
    		userOrg.setCodeType(UserOrgEntity.CODE_TYPE_USER);
			userOrgDao.deleteLogicByLeader(userOrg);
        }else{
        	UserOrgVo userOrg = new UserOrgVo().tenantId(groupUserVo.getTenantId());
        	userOrg.setCodeType(UserOrgEntity.CODE_TYPE_USER);
        	for (String username : usernames) {
        		userOrg.setItemCode(username);
        		userOrgDao.deleteLogic(userOrg);
			}
        }
	}

    /**
     * 逻辑删除记录，如有修改，请在此备注
     *
     * @date 2018-8-21 10:28:49
     * @author jianming.chen
     */
    @Transactional(readOnly = false)
    public int delete(GroupUserVo groupUserVo) {
        String[] usernames = groupUserVo.getUsername().split(",");
        int n = 0;
        for (String username : usernames) {
            groupUserVo.setUsername(username);
            n += groupUserDao.deleteLogic(groupUserVo);
        }
        removeUserOrg(groupUserVo, usernames);
        baseUserGroupService.reloadGroupCache(groupUserVo.getTenantId());
        return n;
    }

    @Transactional(readOnly = false)
    public int addUser(GroupUserVo groupUserVo) {
    	if(groupUserVo.getType() == GroupEntity.USER_TYPE_LEADER){
    		GroupUserVo vo = new GroupUserVo().tenantId(groupUserVo.getTenantId());
        	vo.setGroupCode(groupUserVo.getGroupCode());
        	vo.setType(GroupEntity.USER_TYPE_LEADER);
			List<GroupUserEntity> leaderList = groupUserDao.list(vo);
			if(CollectionUtil.isNotEmpty(leaderList)){
				logger.error("该小组已经存在组长, groupCode:{}, tenantId:{}", groupUserVo.getGroupCode(), groupUserVo.getTenantId());
				throw new BusinessException("该小组已经存在组长!");
			}
    	}
        String[] usernames = groupUserVo.getUsername().split(",");
        int n = 0;
        for (String username : usernames) {
            groupUserVo.setUsername(username);
            n += groupUserDao.insertGroupUser(groupUserVo);
        }
        
        insertUserOrg(groupUserVo, usernames);
        baseUserGroupService.reloadGroupCache(groupUserVo.getTenantId());
        return n;
    }

	private void insertUserOrg(GroupUserVo groupUserVo, String[] usernames) {
		if(NumberUtil.equals(GroupEntity.USER_TYPE_LEADER, groupUserVo.getType())){
			GroupUserVo vo = new GroupUserVo().tenantId(groupUserVo.getTenantId());
        	vo.setGroupCode(groupUserVo.getGroupCode());
        	vo.setType(GroupEntity.USER_TYPE_MEMBER);
			List<GroupUserEntity> memberList = groupUserDao.list(vo);
			if(CollectionUtil.isNotEmpty(memberList)){
				UserOrgEntity userOrg = new UserOrgEntity();
	    		userOrg.copyBaseField(groupUserVo);
	    		userOrg.setUpperSuperior(groupUserVo.getUsername());
	    		userOrg.setCodeType(UserOrgEntity.CODE_TYPE_USER);
	    		for (GroupUserEntity entity : memberList) {
	    			userOrg.setItemCode(entity.getUsername());
	    			userOrgDao.insert(userOrg);
				}
			}
        }else{
        	GroupUserVo vo = new GroupUserVo().tenantId(groupUserVo.getTenantId());
        	vo.setGroupCode(groupUserVo.getGroupCode());
        	vo.setType(GroupEntity.USER_TYPE_LEADER);
			List<GroupUserEntity> leaderList = groupUserDao.list(vo);
			if(CollectionUtil.isNotEmpty(leaderList)){
				if(leaderList.size() > 1){
					logger.warn("该小组有多个组长，默认将取第一个组长，groupCode:{}", groupUserVo.getGroupCode());
				}
				UserOrgEntity userOrg = new UserOrgEntity();
	    		userOrg.copyBaseField(groupUserVo);
	    		userOrg.setUpperSuperior(leaderList.get(0).getUsername());
	    		userOrg.setCodeType(UserOrgEntity.CODE_TYPE_USER);
				for (String username : usernames) {
	    			userOrg.setItemCode(username);
	    			userOrgDao.insert(userOrg);
				}
			}
        }
	}
}
