package com.allinfinance.po;

import java.io.Serializable;

public class PrimaryKey  implements Serializable{

	private static final long serialVersionUID = 4054540444772757547L;

	/**
	 * 模板ID
	 */
	private Integer modelId ;
	
	/**
	 * 序列号
	 */
	private Integer parameterId;

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public Integer getParameterId() {
		return parameterId;
	}

	public void setParameterId(Integer parameterId) {
		this.parameterId = parameterId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modelId == null) ? 0 : modelId.hashCode());
		result = prime * result
				+ ((parameterId == null) ? 0 : parameterId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrimaryKey other = (PrimaryKey) obj;
		if (modelId == null) {
			if (other.modelId != null)
				return false;
		} else if (!modelId.equals(other.modelId))
			return false;
		if (parameterId == null) {
			if (other.parameterId != null)
				return false;
		} else if (!parameterId.equals(other.parameterId))
			return false;
		return true;
	}
	
}
