<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.mail-area {
	height: 100%;
	width: 100%;
	padding: 5px 20px;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

.mail-area * {
	margin: 0;
	padding: 0;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

.mail-area textarea {
	width: 100%;
	max-width: 100%
}

.mail-area input[type="text"] {
	width: 100%
}

.mail-area input[type="radio"] {
	margin-right: 5px
}

.mail-area input[type="radio"] ~ label {
	margin-right: 20px
}

.mail-area div {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

.mail-area .mail-area-left {
	width: 40%;
	height: 100%;
	float: left;
}

.mail-area .mail-area-left>div {
	height: 100%;
	width: 100%;
	border: 1px solid #c7c8ca;
	padding: 5px;
}

.mail-area .mail-area-right {
	width: 60%;
	height: 100%;
	float: left;
	padding-left: 10px;
	float: right;
}

.mail-area .mail-area-right>div {
	height: 100%;
	width: 100%;
	border: 1px solid #c7c8ca;
	padding: 5px;
}

.mail-area .mail-area-top {
	width: 100%;
	height: 30px;
}

.mail-area .mail-area-content {
	width: 100%;
	height: calc(100% - 30px);
	overflow-x: hidden
}

.mail-area div.div-grid {
	height: 100%;
	width: 100%;
	border: 1px solid #c7c8ca;
}

.mail-area .mail-title {
	font-weight: bold;
	font-size: 14px;
	line-height: 30px;
}

.mail-area .mail-title::before {
	content: "▣  "
}

.mail-area .mail-contents .tbl {
	border: none 0;
	width: 100%;
	border-collapse: collapse;
	table-layout: fixed;
}

.mail-area .mail-contents .tbl>tbody>tr>td {
	padding: 2px 5px;
	vertical-align: top;
	border: 1px solid #c7c8ca;
}

.mail-area .mail-contents .tbl>tbody>tr>th {
	padding: 2px 5px;
	background-color: #f5f4f0;
	text-align: left;
	border: 1px solid #c7c8ca;
}

.mail-area .required::after {
	content: " *";
	color: red
}

.mail-area .align-right {
	display: inline-block;
	float: right
}

.mail-area .align-left {
	display: inline-block;
	float: left
}

.mail-area #input-rcpt {
	width: 200px;
	margin-right: 20px
}

.mail-area #input-subject {
	width: 100%;
}
</style>
<div class="mail-area" id="div-mail-area">
	<div class="mail-area-left">
		<div>
			<div class="mail-area-top">
				<div id="mailprofile-btn-area"></div>
			</div>
			<div class="mail-area-content">
				<div class="div-grid" id="div-grid-mail"></div>
			</div>
		</div>
	</div>
	<div class="mail-area-right">
		<div>
			<div class="mail-area-top">
				<div class="align-left">
					<span class="mail-title" id="mail-profile-title"></span>
				</div>
				<div class="align-right">
					<button type="button" class="btn_red_wide"
						id="btn_mailprofile_toggle">크게보기</button>
					<button type="button" class="btn_gray_wide"
						id="btn_mailprofile_send">메일 발송</button>
					<button type="button" class="btn_red_wide"
						id="btn_mailprofile_history" style="display: none">발송 이력
						보기</button>
					<button type="button" class="btn_red_wide"
						id="btn_mailprofile_save">저장</button>
				</div>
			</div>
			<div class="mail-area-content">
				<div class="mail-contents">
					<table class="tbl">
						<col width="20%">
						<col width="80%">
						<tr>
							<th class="required">메일ID</th>
							<td><input type="text" name="mailid" id="mailid" /></td>
						</tr>
						<tr>
							<th class="required">사용여부</th>
							<td><input type="radio" name="useyn" value="1" id="useyn-y"><label
								for="useyn-y">사용</label> <input type="radio" name="useyn"
								value="2" id="useyn-n"><label for="useyn-n">미사용</label>
							</td>
						</tr>
						<tr>
							<th>설명</th>
							<td><textarea name="des" id="des" rows="3"></textarea></td>
						</tr>
						<tr>
							<th class="required">제목</th>
							<td><input type="text" name="subject" id="subject"></td>
						</tr>
						<tr>
							<th rowspan="2">수신자</th>
							<td>
								<button type="button" class="btn_gray_wide" id="btn_member_add">추가</button>
								<button type="button" class="btn_red_wide"
									id="btn_member_remove">삭제</button>
							</td>
						</tr>
						<tr>
							<td style="height: 100px;">
								<div class="div-grid" id="div-grid-rcpt"></div>
							</td>
						</tr>
					</table>

					<table class="tbl">
						<col width="100%">
						<tr>
							<th style="text-align: center">본문 내용</th>
						</tr>
						<tr>
							<td style="padding: 0; border: 0px;"><textarea
									name="contents" id="contents"
									style="width: 100%; height: 300px; display: none;"></textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="external-library/editor/SmartEditor/js/HuskyEZCreator.js"></script>
<script src="js/contents/admin/mail.js" charset='utf-8'></script>