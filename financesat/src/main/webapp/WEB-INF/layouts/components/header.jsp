<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<tiles:importAttribute name="appInfo" />
<tiles:importAttribute name="appConfig" />
<tiles:importAttribute name="authMenuList" />

<header>
	<p class="logo">PwC</p>
	<p class="pageTitle">
		<a href="${pageContext.request.contextPath}${appInfo.homeUri}">${appInfo.title}</a>
	</p>
	<ul class="topUtil">
		<li class="utilName tipClick"><a href="#">${fn:substring(sessionScope.SAMIL_SESSION_KEY.engNm, 0, 1)}</a>
			<div class="tipCont">
				<ul>
					<li>${sessionScope.SAMIL_SESSION_KEY.korNm} 선생님</li>
					<li>${sessionScope.SAMIL_SESSION_KEY.emplNo}</li>
					<c:if test="${sessionScope.SAMIL_SESSION_KEY.roleCd == 'sysadmin' || sessionScope.SAMIL_SESSION_KEY.emplNo != sessionScope.SAMIL_SESSION_KEY.originEmplNo}">
					<li style="color:grey">${sessionScope.SAMIL_SESSION_KEY.roleNm}(${sessionScope.SAMIL_SESSION_KEY.roleCd})</li>
					</c:if>
				</ul>
				<c:if test="${sessionScope.SAMIL_SESSION_KEY.roleCd == 'sysadmin'}">
					<c:if test="${sessionScope.SAMIL_SESSION_KEY.emplNo == sessionScope.SAMIL_SESSION_KEY.originEmplNo}">	
					<p style="margin-top:10px;"><input type="text" id="certId" placeholder="사번 또는 포탈 로그인 ID 입력" style="width:100%"></p>
					</c:if>
					<c:if test="${sessionScope.SAMIL_SESSION_KEY.emplNo == sessionScope.SAMIL_SESSION_KEY.originEmplNo}">
						<a href="javascript:void(0);" id="changeSession" class="btnPwc btnS action">로그인 변경</a>
						<script>
							$("#changeSession").off("click").on("click", function(e){
								e.preventDefault();
								let id = $.trim($("#certId").val());
								if(id===''){
									Helper.MessageBox("사번 또는 포탈 로그인 ID를 입력하세요.");
								}else{									
									location.href = "${pageContext.request.contextPath}/sysadmin/change/user?inteId=" + $("#certId").val();
								}
							})
						</script>
					</c:if>
					<a href="${appConfig.admUrl}" target="_blank" class="btnPwc btnS action">관리자사이트 이동</a>
				</c:if>
				<c:if test="${sessionScope.SAMIL_SESSION_KEY.emplNo != sessionScope.SAMIL_SESSION_KEY.originEmplNo}">
					<a href="${pageContext.request.contextPath}/sysadmin/init/user" class="btnPwc btnS action">초기화 하기</a>
				</c:if>
				<button type="button" class="close">닫기</button>
			</div>
		</li>
		<!-- <li class="videoHelp">
			<a id="videoHelpLink" href="https://sites.google.com/pwc.com/sat-edu"  target="_blank">도움말</a>
		</li>
		<li class="utilHelp">
			<a id="help" href="https://drive.google.com/file/d/12uQikj4KJjZAhMIbO9p9DoF528F_gfFL/view?usp=sharing" target="_blank">도움말</a>
		</li> -->
	</ul>
	<nav>
		<button type="button" class="navBtn">Menu</button>
		<div class="navArea">
			<ul class="snbList">
				<c:forEach var="menu" items="${authMenuList}">
					<li class="snb01 ${menu.menuIcon} ${menu.selected == 'Y' ? 'snbON' : ''}"><a href="" class="ctrl" id="menu-${menu.menuCd}" target="${subMenu.menuOpenTy}">${menu.menuNm}</a>
						<div class="snbDep2">
							<h2 class="snbTit">${menu.menuNm}</h2>
							<ul>
								<c:forEach var="subMenu" items="${menu.children}" >
									<li class="${subMenu.selected == 'Y' ? 'dep2ON' : ''}"><a href="${subMenu.uri}" id="menu-${subMenu.menuCd}" target="${subMenu.menuOpenTy}">${subMenu.menuNm}</a></li>
								</c:forEach>
							</ul>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</nav>
</header>
<script type="text/javascript">
$(".navArea a").each(function(){
	let url  = $(this).attr("href");
	let target  = $(this).attr("target");
	const menuCd  = $(this).attr("id");
	
	if( url === null ||  url === "" || menuCd === 'menu-prjt_form'){
		$(this).off('click').on('click', function(e){
			e.preventDefault();
		}).promise().done(function(){
			$(this).attr("href", "javascript:void(0);")
			$(this).removeAttr("target");
		})
	}else{
		$(this).off('click').on('click', function(e){
			e.preventDefault();
			SessionStorageCtrl.clear();
			Helper.goPage(url, null, target);
		}).promise().done(function(){
			$(this).attr("href", "javascript:void(0);");
			$(this).removeAttr("target");
		})
	}
})
</script>