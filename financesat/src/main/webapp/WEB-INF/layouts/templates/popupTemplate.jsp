<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:importAttribute name="content"/>

<div id="layPopup-${popupInfo.id}" class="layPopup ${popupInfo.popType == null ? 'layer': popupInfo.popType}${popupInfo.size == null ? 'M': popupInfo.size}" role="dialog" aria-label="${popupInfo.name}"
	style="height:${popupInfo.height == null ? 'auto' : popupInfo.height};width:${popupInfo.width};"
>
	<h2 class="layTit">${popupInfo.name}</h2>
	<tiles:insertAttribute name="content" />
	<button type="button" class="closeL">닫기</button>
</div>