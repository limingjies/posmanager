package com.allinfinance.po.risk;

/**
 * TblRiskParamMng entity. @author MyEclipse Persistence Tools
 */

public class TblRiskParamMng  implements java.io.Serializable {


	private static final long serialVersionUID = -4362620018170748772L;
	private TblRiskParamMngPK id;
     private Double creditSingleAmt;
     private Double creditDayAmt;
     private Integer creditDayCount;
     private Double creditMonAmt;
     private Integer creditMonCount;
     private Double debitSingleAmt;
     private Double debitDayAmt;
     private Integer debitDayCount;
     private Double debitMonAmt;
     private Integer debitMonCount;
     private String remark;
     private String regTime;
     private String regOpr;
     private String updTime;
     private String updOpr;
     private String resved;
     private String resved1;
     private Double mchtAmt;
     private Double mchtDayAmt;
     private Double mchtPosAmt;

    // Constructors

    /** default constructor */
    public TblRiskParamMng() {
    }

	/** minimal constructor */
    public TblRiskParamMng(TblRiskParamMngPK id) {
        this.id = id;
    }
    
    /** full constructor */
    public TblRiskParamMng(TblRiskParamMngPK id, Double creditSingleAmt, Double creditDayAmt, Integer creditDayCount,
    		Double creditMonAmt, Integer creditMonCount, Double debitSingleAmt, Double debitDayAmt, Integer debitDayCount, 
    		Double debitMonAmt, Integer debitMonCount, String remark, String regTime, String regOpr, String updTime, 
    		String updOpr, String resved, String resved1,Double mchtAmt,Double mchtDayAmt,Double mchtPosAmt) {
        this.id = id;
        this.creditSingleAmt = creditSingleAmt;
        this.creditDayAmt = creditDayAmt;
        this.creditDayCount = creditDayCount;
        this.creditMonAmt = creditMonAmt;
        this.creditMonCount = creditMonCount;
        this.debitSingleAmt = debitSingleAmt;
        this.debitDayAmt = debitDayAmt;
        this.debitDayCount = debitDayCount;
        this.debitMonAmt = debitMonAmt;
        this.debitMonCount = debitMonCount;
        this.remark = remark;
        this.regTime = regTime;
        this.regOpr = regOpr;
        this.updTime = updTime;
        this.updOpr = updOpr;
        this.resved = resved;
        this.resved1 = resved1;
        this.mchtAmt = mchtAmt;
        this.mchtDayAmt = mchtDayAmt;
        this.mchtPosAmt = mchtPosAmt;
        
    }

   
    // Property accessors

    public TblRiskParamMngPK getId() {
        return this.id;
    }
    
    public void setId(TblRiskParamMngPK id) {
        this.id = id;
    }

    public Double getCreditSingleAmt() {
        return this.creditSingleAmt;
    }
    
    public void setCreditSingleAmt(Double creditSingleAmt) {
        this.creditSingleAmt = creditSingleAmt;
    }

    public Double getCreditDayAmt() {
        return this.creditDayAmt;
    }
    
    public void setCreditDayAmt(Double creditDayAmt) {
        this.creditDayAmt = creditDayAmt;
    }

    public Integer getCreditDayCount() {
        return this.creditDayCount;
    }
    
    public void setCreditDayCount(Integer creditDayCount) {
        this.creditDayCount = creditDayCount;
    }

    public Double getCreditMonAmt() {
        return this.creditMonAmt;
    }
    
    public void setCreditMonAmt(Double creditMonAmt) {
        this.creditMonAmt = creditMonAmt;
    }

    public Integer getCreditMonCount() {
        return this.creditMonCount;
    }
    
    public void setCreditMonCount(Integer creditMonCount) {
        this.creditMonCount = creditMonCount;
    }

    public Double getDebitSingleAmt() {
        return this.debitSingleAmt;
    }
    
    public void setDebitSingleAmt(Double debitSingleAmt) {
        this.debitSingleAmt = debitSingleAmt;
    }

    public Double getDebitDayAmt() {
        return this.debitDayAmt;
    }
    
    public void setDebitDayAmt(Double debitDayAmt) {
        this.debitDayAmt = debitDayAmt;
    }

    public Integer getDebitDayCount() {
        return this.debitDayCount;
    }
    
    public void setDebitDayCount(Integer debitDayCount) {
        this.debitDayCount = debitDayCount;
    }

    public Double getDebitMonAmt() {
        return this.debitMonAmt;
    }
    
    public void setDebitMonAmt(Double debitMonAmt) {
        this.debitMonAmt = debitMonAmt;
    }

    public Integer getDebitMonCount() {
        return this.debitMonCount;
    }
    
    public void setDebitMonCount(Integer debitMonCount) {
        this.debitMonCount = debitMonCount;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRegTime() {
        return this.regTime;
    }
    
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getRegOpr() {
        return this.regOpr;
    }
    
    public void setRegOpr(String regOpr) {
        this.regOpr = regOpr;
    }

    public String getUpdTime() {
        return this.updTime;
    }
    
    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public String getUpdOpr() {
        return this.updOpr;
    }
    
    public void setUpdOpr(String updOpr) {
        this.updOpr = updOpr;
    }

    public String getResved() {
        return this.resved;
    }
    
    public void setResved(String resved) {
        this.resved = resved;
    }

    public String getResved1() {
        return this.resved1;
    }
    
    public void setResved1(String resved1) {
        this.resved1 = resved1;
    }

	public Double getMchtAmt() {
		return mchtAmt;
	}

	public void setMchtAmt(Double mchtAmt) {
		this.mchtAmt = mchtAmt;
	}

	public Double getMchtDayAmt() {
		return mchtDayAmt;
	}

	public void setMchtDayAmt(Double mchtDayAmt) {
		this.mchtDayAmt = mchtDayAmt;
	}

	public Double getMchtPosAmt() {
		return mchtPosAmt;
	}

	public void setMchtPosAmt(Double mchtPosAmt) {
		this.mchtPosAmt = mchtPosAmt;
	}
    
  
}