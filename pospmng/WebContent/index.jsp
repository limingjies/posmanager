<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>海南农信社IC卡多应用平台</TITLE>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<script type="text/javascript" src="<%= request.getContextPath()%>/ui/system/index.js"></script>
		<LINK href="<%= request.getContextPath()%>/ext/resources/css/global.css" type=text/css rel=STYLESHEET>
		
	</HEAD>
	<BODY bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0
		marginheight="0" marginwidth="0">
		<CENTER>
			<DIV style="WIDTH: 100%; BACKGROUND-COLOR: #ffffff">
				<IMG height=3 src="<%= request.getContextPath()%>/ext/resources/images/spacer.gif" width=1>
				<BR>
				<TABLE id="bd" cellSpacing=0 cellPadding=0 width="100%" border=0>
					<TBODY>
						<TR>
							<TD vAlign=top align=middle>
								<TABLE id=mouseovers cellSpacing=0 cellPadding=0 width="776"
									border=0>
									<TBODY>
										<TR>
											<TD vAlign=top noWrap align=center height=60></TD>
										</TR>
									</TBODY>
								</TABLE>
							</TD>
						</TR>
						<TR>
							<TD align=middle bgColor=#ffffff colSpan=4 height=3>
								<IMG height=10 src="<%= request.getContextPath()%>/ext/resources/images/spacer.gif" width=1>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</DIV>
			<DIV id=content>
				<DIV class=module_darkgray>
					<DIV class=bottomedge_darkgray>
						<DIV class=topleft_darkgray></DIV>
						<DIV class=topright_darkgray></DIV>
						<DIV class=moduleborder>
							<DIV class=module_inset_darkgray>
								<DIV class=bottomedge_inset_darkgray>
									<DIV class=topleft_inset_darkgray></DIV>
									<DIV class=topright_inset_darkgray></DIV>
									<DIV style="LEFT: 735px; PADDING-TOP: 5px; POSITION: absolute">
										<IMG height=20 src="<%= request.getContextPath()%>/ext/resources/images/white_lock.gif" width=14 border=0>
									</DIV>
									<DIV
										style="PADDING-LEFT: 35px; PADDING-BOTTOM: 20px; PADDING-TOP: 40px; align: left">
										<IMG src="<%= request.getContextPath()%>/ext/resources/images/text_dotmaclogin.png" border=0>
									</DIV>
									<DIV style="PADDING-RIGHT: 15px; PADDING-LEFT: 35px">
										<TABLE cellSpacing=0 cellPadding=0 width=689 border=0>
											<TBODY>
												<TR>
													<TD style="PADDING-RIGHT: 20px" width=318>
														<TABLE cellSpacing=0 cellPadding=0 border=0>
															<TBODY>
																<TR>
																	<TD style="MARGIN-BOTTOM: 10px" vAlign=top>
																		<strong>&nbsp;&nbsp;操作员登录</strong>
																	</TD>
																</TR>
																<TR>
																	<TD class=content_gray_bold>&nbsp;&nbsp;请输入操作员编号和授权码登录系统。
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
														<!-- Begin Form -->
														<TABLE cellSpacing=0 cellPadding=0 width=318 border=0>
															<TBODY>
																<TR>
																	<TD align=left>
																		<div id="loginForm"></div>
																	</TD>
																</TR>
																<TR>
																	<TD height=10>
																		<IMG height=10 alt=""
																			src="<%= request.getContextPath()%>/ext/resources/images/spacer.gif"
																			width=1 border=0>
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
													</TD>
													<!-- End Form -->
													<TD vAlign=top>
														<TABLE cellSpacing=0 cellPadding=0 border=0>
															<TBODY>
																<TR>
																	<TD style="BACKGROUND-COLOR: #e3e3e3" width=2
																		height=200></TD>
																</TR>
															</TBODY>
														</TABLE>
													</TD>
													<TD style="PADDING-LEFT: 30px" vAlign=top width=318>
														<!-- Message 2 -->
														<TABLE cellSpacing=0 cellPadding=0 border=0>
															<TBODY>
																<TR>
																	<TD class=content_gray vAlign=top>
																		<img width="330" height="152" src="<%= request.getContextPath()%>/ext/resources/images/banner.png" menu="false" />
																		
																		<br />
																		<br />
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</DIV>
								</DIV>
							</DIV>
						</DIV>
					</DIV>
				</DIV>
			</DIV>
			<DIV>
				<TABLE cellSpacing=0 cellPadding=0 width=776 align=center border=0>
					<TBODY>
						<TR>
							<TD vAlign=top align=middle width=776>
								海南农信社IC卡多应用平台
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</DIV>
			<!-- END content_gray -->
			<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
				<TBODY>
					<TR>
						<TD align=middle>
							<FONT class=disclaimer face="Geneva, Verdana, Arial, Helvetica"
								color=#999999 size=1>&nbsp;All rights reserved©2013. All In Finance 钱宝金融</FONT>
							<BR>
							<BR>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
		</CENTER>
	</BODY>
</HTML>
