package com.allinfinance.po;

import java.io.Serializable;



public class TblTermCupInfoTmp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected void initialize () {}

	/**
	 * Constructor for primary key
	 */
	public TblTermCupInfoTmp (java.lang.String term_id) {
		this.setTerm_id(term_id);
		initialize();
	}

	public TblTermCupInfoTmp() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int hashCode = Integer.MIN_VALUE;

	private java.lang.String term_id;
	private java.lang.String mcht_no;
	private java.lang.String in_inst_id;
	private java.lang.String inst_id;
	private java.lang.String term_type ;
	private java.lang.String term_state;
	private java.lang.String out_flag;
	private java.lang.String IC_flag;
	private java.lang.String call_type;
	private java.lang.String currcy_code_trans;
	private java.lang.String key_index;
	private java.lang.String enc_key_mode;
	private java.lang.String trans_key_mode;
	private java.lang.String term_enc_type;
	private java.lang.String term_enc_key_1;
	private java.lang.String term_trans_key_1;
	private java.lang.String open_time;
	private java.lang.String close_time;
	private java.lang.String para_down_flag;
	private java.lang.String tms_down_flag;
	private java.lang.String pub_key_flag;
	private java.lang.String IC_para_flag;
	private java.lang.String emv_flag;
	private java.lang.String key_length;
	private java.lang.String pik_length;
	private java.lang.String mak_length;
	private java.lang.String mac_alg;
	private java.lang.String country_code;
	private java.lang.String connect_mode;
	private java.lang.String track_key_len;
	private java.lang.String term_share;
	private java.lang.String term_call_num;
	private java.lang.String address;
	private java.lang.String name;
	private java.lang.String tel_phone;
	private java.lang.String anhui_reserved;
	private java.lang.String crt_opr;
	private java.lang.String crt_date;
	private java.lang.String upd_opr;
	private java.lang.String upd_date;


	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	public java.lang.String getTerm_id() {
		return term_id;
	}

	public void setTerm_id(java.lang.String term_id) {
		this.term_id = term_id;
	}

	public java.lang.String getMcht_no() {
		return mcht_no;
	}

	public void setMcht_no(java.lang.String mcht_no) {
		this.mcht_no = mcht_no;
	}

	public java.lang.String getIn_inst_id() {
		return in_inst_id;
	}

	public void setIn_inst_id(java.lang.String in_inst_id) {
		this.in_inst_id = in_inst_id;
	}

	public java.lang.String getInst_id() {
		return inst_id;
	}

	public void setInst_id(java.lang.String inst_id) {
		this.inst_id = inst_id;
	}

	public java.lang.String getTerm_type() {
		return term_type;
	}

	public void setTerm_type(java.lang.String term_type) {
		this.term_type = term_type;
	}

	public java.lang.String getTerm_state() {
		return term_state;
	}

	public void setTerm_state(java.lang.String term_state) {
		this.term_state = term_state;
	}

	public java.lang.String getOut_flag() {
		return out_flag;
	}

	public void setOut_flag(java.lang.String out_flag) {
		this.out_flag = out_flag;
	}

	public java.lang.String getIC_flag() {
		return IC_flag;
	}

	public void setIC_flag(java.lang.String ic_flag) {
		IC_flag = ic_flag;
	}

	public java.lang.String getCall_type() {
		return call_type;
	}

	public void setCall_type(java.lang.String call_type) {
		this.call_type = call_type;
	}

	public java.lang.String getCurrcy_code_trans() {
		return currcy_code_trans;
	}

	public void setCurrcy_code_trans(java.lang.String currcy_code_trans) {
		this.currcy_code_trans = currcy_code_trans;
	}

	public java.lang.String getKey_index() {
		return key_index;
	}

	public void setKey_index(java.lang.String key_index) {
		this.key_index = key_index;
	}

	public java.lang.String getEnc_key_mode() {
		return enc_key_mode;
	}

	public void setEnc_key_mode(java.lang.String enc_key_mode) {
		this.enc_key_mode = enc_key_mode;
	}

	public java.lang.String getTrans_key_mode() {
		return trans_key_mode;
	}

	public void setTrans_key_mode(java.lang.String trans_key_mode) {
		this.trans_key_mode = trans_key_mode;
	}

	public java.lang.String getTerm_enc_type() {
		return term_enc_type;
	}

	public void setTerm_enc_type(java.lang.String term_enc_type) {
		this.term_enc_type = term_enc_type;
	}

	public java.lang.String getTerm_enc_key_1() {
		return term_enc_key_1;
	}

	public void setTerm_enc_key_1(java.lang.String term_enc_key_1) {
		this.term_enc_key_1 = term_enc_key_1;
	}

	public java.lang.String getTerm_trans_key_1() {
		return term_trans_key_1;
	}

	public void setTerm_trans_key_1(java.lang.String term_trans_key_1) {
		this.term_trans_key_1 = term_trans_key_1;
	}

	public java.lang.String getOpen_time() {
		return open_time;
	}

	public void setOpen_time(java.lang.String open_time) {
		this.open_time = open_time;
	}

	public java.lang.String getClose_time() {
		return close_time;
	}

	public void setClose_time(java.lang.String close_time) {
		this.close_time = close_time;
	}

	public java.lang.String getPara_down_flag() {
		return para_down_flag;
	}

	public void setPara_down_flag(java.lang.String para_down_flag) {
		this.para_down_flag = para_down_flag;
	}

	public java.lang.String getTms_down_flag() {
		return tms_down_flag;
	}

	public void setTms_down_flag(java.lang.String tms_down_flag) {
		this.tms_down_flag = tms_down_flag;
	}

	public java.lang.String getPub_key_flag() {
		return pub_key_flag;
	}

	public void setPub_key_flag(java.lang.String pub_key_flag) {
		this.pub_key_flag = pub_key_flag;
	}

	public java.lang.String getIC_para_flag() {
		return IC_para_flag;
	}

	public void setIC_para_flag(java.lang.String ic_para_flag) {
		IC_para_flag = ic_para_flag;
	}

	public java.lang.String getEmv_flag() {
		return emv_flag;
	}

	public void setEmv_flag(java.lang.String emv_flag) {
		this.emv_flag = emv_flag;
	}

	public java.lang.String getKey_length() {
		return key_length;
	}

	public void setKey_length(java.lang.String key_length) {
		this.key_length = key_length;
	}

	public java.lang.String getPik_length() {
		return pik_length;
	}

	public void setPik_length(java.lang.String pik_length) {
		this.pik_length = pik_length;
	}

	public java.lang.String getMak_length() {
		return mak_length;
	}

	public void setMak_length(java.lang.String mak_length) {
		this.mak_length = mak_length;
	}

	public java.lang.String getMac_alg() {
		return mac_alg;
	}

	public void setMac_alg(java.lang.String mac_alg) {
		this.mac_alg = mac_alg;
	}

	public java.lang.String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(java.lang.String country_code) {
		this.country_code = country_code;
	}

	public java.lang.String getConnect_mode() {
		return connect_mode;
	}

	public void setConnect_mode(java.lang.String connect_mode) {
		this.connect_mode = connect_mode;
	}

	public java.lang.String getTrack_key_len() {
		return track_key_len;
	}

	public void setTrack_key_len(java.lang.String track_key_len) {
		this.track_key_len = track_key_len;
	}

	public java.lang.String getTerm_share() {
		return term_share;
	}

	public void setTerm_share(java.lang.String term_share) {
		this.term_share = term_share;
	}

	public java.lang.String getTerm_call_num() {
		return term_call_num;
	}

	public void setTerm_call_num(java.lang.String term_call_num) {
		this.term_call_num = term_call_num;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getTel_phone() {
		return tel_phone;
	}

	public void setTel_phone(java.lang.String tel_phone) {
		this.tel_phone = tel_phone;
	}

	public java.lang.String getAnhui_reserved() {
		return anhui_reserved;
	}

	public void setAnhui_reserved(java.lang.String anhui_reserved) {
		this.anhui_reserved = anhui_reserved;
	}

	public java.lang.String getCrt_opr() {
		return crt_opr;
	}

	public void setCrt_opr(java.lang.String crt_opr) {
		this.crt_opr = crt_opr;
	}

	public java.lang.String getCrt_date() {
		return crt_date;
	}

	public void setCrt_date(java.lang.String crt_date) {
		this.crt_date = crt_date;
	}

	public java.lang.String getUpd_opr() {
		return upd_opr;
	}

	public void setUpd_opr(java.lang.String upd_opr) {
		this.upd_opr = upd_opr;
	}

	public java.lang.String getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(java.lang.String upd_date) {
		this.upd_date = upd_date;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblTermCupInfoTmp)) return false;
		else {
			com.allinfinance.po.TblTermCupInfoTmp TblTermCupInfoTmp = (com.allinfinance.po.TblTermCupInfoTmp) obj;
			if (null == this.getTerm_id() || null == TblTermCupInfoTmp.getTerm_id()) return false;
			else return (this.getTerm_id().equals(TblTermCupInfoTmp.getTerm_id()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getTerm_id()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getTerm_id().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}