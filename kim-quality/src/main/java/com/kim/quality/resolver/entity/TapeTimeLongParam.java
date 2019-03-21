package com.kim.quality.resolver.entity;

public class TapeTimeLongParam implements ResolverParam {
	
	private Integer start;
	private Integer end;
	
	@Override
	public String checkParam() {
		if(start == null && end == null){
			return "录音时长抽取器参数为空";
		}
		return null;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

}
