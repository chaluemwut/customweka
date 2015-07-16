package com.kku.customnb.bean;

import java.util.List;

import weka.core.Instances;

import com.kku.customnb.MeanSDBean;

public class ResultBean {
	private List<MeanSDBean> lstMean25 = null;
	private List<MeanSDBean> lstMean50 = null;
	private List<MeanSDBean> lstMean75 = null;
	private Instances training = null;
	
	public List<MeanSDBean> getLstMean25() {
		return lstMean25;
	}
	public void setLstMean25(List<MeanSDBean> lstMean25) {
		this.lstMean25 = lstMean25;
	}
	public List<MeanSDBean> getLstMean50() {
		return lstMean50;
	}
	public void setLstMean50(List<MeanSDBean> lstMean50) {
		this.lstMean50 = lstMean50;
	}
	public List<MeanSDBean> getLstMean75() {
		return lstMean75;
	}
	public void setLstMean75(List<MeanSDBean> lstMean75) {
		this.lstMean75 = lstMean75;
	}
	public Instances getTraining() {
		return training;
	}
	public void setTraining(Instances training) {
		this.training = training;
	}

	
}
