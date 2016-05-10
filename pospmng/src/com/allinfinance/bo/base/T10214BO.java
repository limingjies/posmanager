package com.allinfinance.bo.base;

import java.io.File;
import java.util.List;


public interface T10214BO {

	public String formatFile(List<File> fileList, List<String> fileNameList,
			String fileType,String amtUp,String amtRes)throws Exception;
}
