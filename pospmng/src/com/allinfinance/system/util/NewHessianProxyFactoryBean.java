package com.allinfinance.system.util;

import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 *  NewHessianProxyFactoryBean.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年5月26日	  徐鹏飞          created this class.
 *    
 *  
 *  Copyright Notice:
 *   Copyright (c) 2009-2015   Allinpay Financial Services Co., Ltd. 
 *    All rights reserved.
 *
 *   This software is the confidential and proprietary information of
 *   allinfinance.com  Inc. ('Confidential Information').  You shall not
 *   disclose such Confidential Information and shall use it only in
 *   accordance with the terms of the license agreement you entered
 *   into with Allinpay Financial.
 *
 *  Warning:
 *  =========================================================================
 *  
 */
public class NewHessianProxyFactoryBean extends HessianProxyFactoryBean {
	private int readTimeOut = 5000;
    private int connectTimeOut = 5000;

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    @Override
    public void prepare() throws RemoteLookupFailureException {
		HessianProxyFactory proxyFactory = new HessianProxyFactory();
        if(this.readTimeOut >0){
            proxyFactory.setReadTimeout(this.readTimeOut);
        }
        if(this.connectTimeOut >0){
            proxyFactory.setConnectTimeout(this.connectTimeOut);
        }
        this.setProxyFactory(proxyFactory);

        super.prepare();
    }
}
