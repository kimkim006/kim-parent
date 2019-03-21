package com.kim.quality.business.enums;

import java.util.List;
import java.util.Map;

import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.business.entity.MainTaskEntity;

public class MainTask {
	
	/** 任务状态, 0待分配 */
	public static final int STATUS_TO_ALLOCATE = 0;
	/** 任务状态, 1已回收 */
	public static final int STATUS_RECYCLED = 1;
	/** 任务状态, 2待质检 */
	public static final int STATUS_TO_EVALUATION = 2;
	/** 任务状态, 3待审批 */
	public static final int STATUS_TO_APPROVAL = 3;
	/** 任务状态, 4反馈结果 */
	public static final int STATUS_RESULT_FEEDBACK = 4;
	/** 任务状态, 5审批驳回 */
	public static final int STATUS_APPROVAL_REJECTED = 5;
	/** 任务状态, 6申诉待审批 */
	public static final int STATUS_TO_APPROVAL_APPEAL = 6;
	/** 任务状态, 7申诉待处理 */
	public static final int STATUS_TO_APPEAL_DEAL = 7;
	/** 任务状态, 8申诉通过 */
	public static final int STATUS_APPEAL_PASS = 8;
	/** 任务状态, 9申诉驳回 */
	public static final int STATUS_APPEAL_REJECTED = 9;
	/** 任务状态, 10坐席已确认 */
	public static final int STATUS_RESULT_CONFIRM = 10;
	
	/** 抽检数据类型, 1录音 */
	public static final int ITEM_TYPE_TAPE = 1;
	/** 抽检数据类型, 2文本 */
	public static final int ITEM_TYPE_TEXT = 2;
	
	/** 任务关联item 是否该任务下最后面的记录, 1是  */
	public static final int ITEM_IS_LAST_Y = 1;
	/** 任务关联item 是否该任务下最后面的记录, 0否  */
	public static final int ITEM_IS_LAST_N = 0;
	
	/** 任务关联的item的id值类型, INFO任务基本信息(录音信息) */
	public static final String KEY_TYPE_INFO = "INFO";
	/** 任务关联的item的id值类型, EVAL评分 */
	public static final String KEY_TYPE_EVALUATION = "EVAL";
	/** 任务关联的item的id值类型, APPRL审核 */
	public static final String KEY_TYPE_APPROVAL = "APPRL";
	/** 任务关联的item的id值类型, APPEAL申诉 */
	public static final String KEY_TYPE_APPEAL = "APPEAL";
	/** 任务关联的item的id值类型, ADJUST_SCORE调整分数记录 */
	public static final String KEY_TYPE_ADJUST_SCORE = "ADJUST_SCORE";
	/** 任务关联的审核，评分，申诉id值类型, OTHER其他操作 */
	public static final String KEY_TYPE_OTHER = "OTHER";
	
	public static PageRes<MainTaskEntity> convertStatus(PageRes<MainTaskEntity> pageRes){
		if(pageRes == null){
			return null;
		}
		if(CollectionUtil.isEmpty(pageRes.getList())){
			return pageRes;
		}
		convertStatus(pageRes.getList());
		return pageRes;
	}
	
	public static List<MainTaskEntity> convertStatus(List<MainTaskEntity> list){
		if(CollectionUtil.isEmpty(list)){
			return list;
		}
		Map<Integer, String> map = MainTaskStatusEnum.getMap();
		String name;
		for (MainTaskEntity entity : list) {
			if(entity.getStatus() != null){
				name = map.get(entity.getStatus());
				entity.setStatusName(StringUtil.isNotBlank(name)?name:entity.getStatus().toString());
			}
		}
		return list;
	}
	
}
