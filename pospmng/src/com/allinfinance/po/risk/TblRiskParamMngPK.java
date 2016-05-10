package com.allinfinance.po.risk;

import com.allinfinance.system.util.CommonFunction;

/**
 * TblRiskParamMngId entity. @author MyEclipse Persistence Tools
 */

public class TblRiskParamMngPK  implements java.io.Serializable {

	private static final long serialVersionUID = 902610649354128332L;
	private String mchtId;
     private String termId;
     private String riskType;


    // Constructors

    /** default constructor */
    public TblRiskParamMngPK() {
    }

    
    /** full constructor */
    public TblRiskParamMngPK(String mchtId, String termId, String riskType) {
        this.mchtId = mchtId;
        this.termId = CommonFunction.fillString(termId,' ',12,true);
        this.riskType = riskType;
    }

   
    // Property accessors

    public String getMchtId() {
        return this.mchtId;
    }
    
    public void setMchtId(String mchtId) {
        this.mchtId = mchtId;
    }

    public String getTermId() {
        return this.termId;
    }
    
    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getRiskType() {
        return this.riskType;
    }
    
    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TblRiskParamMngPK) ) return false;
		 TblRiskParamMngPK castOther = ( TblRiskParamMngPK ) other; 
         
		 return ( (this.getMchtId()==castOther.getMchtId()) || ( this.getMchtId()!=null && castOther.getMchtId()!=null && this.getMchtId().equals(castOther.getMchtId()) ) )
 && ( (this.getTermId()==castOther.getTermId()) || ( this.getTermId()!=null && castOther.getTermId()!=null && this.getTermId().equals(castOther.getTermId()) ) )
 && ( (this.getRiskType()==castOther.getRiskType()) || ( this.getRiskType()!=null && castOther.getRiskType()!=null && this.getRiskType().equals(castOther.getRiskType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMchtId() == null ? 0 : this.getMchtId().hashCode() );
         result = 37 * result + ( getTermId() == null ? 0 : this.getTermId().hashCode() );
         result = 37 * result + ( getRiskType() == null ? 0 : this.getRiskType().hashCode() );
         return result;
   }   





}