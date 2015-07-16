package com.kku.customnb;

public class ProbBean {
	private String attrName;
	private double[] zeroProb;
	private double[] oneProb;
	private double dataValue;

	public ProbBean(String attrName, double[] zeroProb, double[] oneProb,
			double dataValue) {
		super();
		this.attrName = attrName;
		this.zeroProb = zeroProb;
		this.oneProb = oneProb;
		this.dataValue = dataValue;
	}

	public double getDataValue() {
		return dataValue;
	}

	public void setDataValue(double dataValue) {
		this.dataValue = dataValue;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public double[] getZeroProb() {
		return zeroProb;
	}

	public void setZeroProb(double[] zeroProb) {
		this.zeroProb = zeroProb;
	}

	public double[] getOneProb() {
		return oneProb;
	}

	public void setOneProb(double[] oneProb) {
		this.oneProb = oneProb;
	}

}
