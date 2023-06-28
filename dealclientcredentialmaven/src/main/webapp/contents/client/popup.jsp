<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="egate_dialog_wrap" id="listSupportProject" title="지원프로젝트"
	style="display: none;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<div class="dialog_data_head">
		<table class="dc_data_table tac">
			<colgroup>
				<col width="140px" />
				<col width="" />
				<!-- 						<col width="70px" /> -->
				<!-- 						<col width="70px" /> -->
				<!-- 						<col width="70px" /> -->
				<!-- 						<col width="85px" /> -->
				<col width="140px" />
			</colgroup>
			<tr>
				<th>Project Code</th>
				<th>Product</th>
				<!-- 						<th>Revenue</th> -->
				<!-- 						<th>Budget</th> -->
				<!-- 						<th>Total</th> -->
				<!-- 						<th>EL/TM/QRP</th> -->
				<th>서면조서</th>
			</tr>
		</table>
	</div>
	<div class="dialog_data_list">
		<!-- 테이블이 8행 이상 넘어가면 자동 스크롤 생김 -->
		<table class="dc_data_table_nobdr tac">
			<colgroup>
				<col width="140px" />
				<col width="" />
				<!-- 						<col width="70px" /> -->
				<!-- 						<col width="70px" /> -->
				<!-- 						<col width="70px" /> -->
				<!-- 						<col width="85px" /> -->
				<col width="140px" />
			</colgroup>
			<tbody>

			</tbody>
		</table>
	</div>
</div>
<div class="egate_dialog_wrap" id="userSearch" title="임직원 검색"
	style="display: none;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<div class="dc_top_box dc_pop_top_box">
		<div class="dc_top_box_lft" id="userSearchConditionBox">
			<div class="box_row">
				<span> <input type="radio" name="cos" id="paramEmpnm"
					value="empnm" checked> 성명 <input type="radio" name="cos"
					id="paramEmpno" value="empno"> 사번
				</span> <span> <input type="text" id="inputSearchParam" autofocus />
				</span>
				<!-- 						<span> -->
				<!-- 							 <input type="text" maxlength="20" id="nmUserSearch"/> -->
				<!-- 						</span> -->
			</div>
		</div>
		<div class="dc_top_box_rgt">
			<button type="button" class="btn_red_wide" id="btnUserSearch"
				title="검색">검색</button>
		</div>
	</div>
	<div class="mt10 dialog_data_head">
		<table class="dc_header_table tac">
			<colgroup>
				<col width="20%" />
				<col width="20%" />
				<col width="30%" />
				<col width="30%" />
			</colgroup>
			<tr>
				<td>사번</td>
				<td>이름</td>
				<td>직급</td>
				<td>부서</td>
			</tr>
		</table>
	</div>
	<div class="dialog_data_list">
		<table class="dc_data_table_nobdr tac">

		</table>
	</div>
</div>
<div class="egate_dialog_wrap" id="projectMemberHistory" title="변경 이력"
	style="display: none;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<h4 class="dialog_con_title"></h4>
	<div class="dialog_data_list_auto">
		<!-- 테이블이 8행 이상 넘어가면 자동 스크롤 생김 -->
		<table class="dc_data_table">
		</table>
	</div>
</div>
<div class="egate_dialog_wrap" id="readQRPHistory" title="QRP/SR 담당자 변경"
	style="display: none;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<dl class="dc_dl_frm">
		<dt>추가</dt>
		<dd>
			<div class="frm_user">
				<span class="data_input"> <input type="text" id="_added"
					name="qrphist" disabled="disabled" /> <span class="inp_user_btn"></span>
				</span>
			</div>
		</dd>
	</dl>
	<div class="" id="_QRPhistory"></div>
</div>
<div class="egate_dialog_wrap" id="importExcel" title="프로젝트 일괄 Import"
	style="display: none;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<div id="_importExcelFileContainer"></div>
</div>
<div class="egate_dialog_wrap" id="guideline"
	title="프로젝트 일괄 Import 결과 내역" style="display: none;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<div id="guideline_content_area"></div>
</div>
<div class="egate_dialog_wrap" id="exportExcelList"
	title="Download(List)" style="display: none;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<div id="exportExcelFileContainer"></div>
</div>
<div class="egate_dialog_wrap" id="companySearch" title="기업 검색"
	style="display: none;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<div class="dc_top_box dc_pop_top_box">
		<div class="dc_top_box_lft" id="companySearchConditionBox">
			<div class="box_row">
				<span>회사명 (한글)</span> <span> <input type="text"
					id="inputCompanySearchParam" autofocus /> <input type="hidden"
					id="hiddenInputCompanySearchParam" autofocus />
				</span> <span><input type="checkbox" id="reSearchCheck" /><label
					for="reSearchCheck">결과 내 검색</label></span> <span id="firstSearchTerm">1차
					검색어: </span>
			</div>
		</div>
		<div class="dc_top_box_rgt">
			<button type="button" class="btn_red_wide" id="btnCompanySearch"
				title="검색">검색</button>
		</div>
	</div>
	<div class="mt10 dialog_data_head">
		<table class="dc_header_table tac">
			<colgroup>
				<col width="10%" />
				<col width="25%" />
				<col width="15%" />
				<col width="10%" />
				<col width="40%" />
			</colgroup>
			<tr>
				<td>고객ID</td>
				<td>기업명</td>
				<td>법인번호</td>
				<td>대표</td>
				<td>주소</td>
			</tr>
		</table>
	</div>
	<div class="dialog_data_list">
		<table class="dc_data_table_nobdr tac">
		</table>
	</div>
	<div class="dc_bottom_box">
		<span>* 직접입력할 경우, 체크를 유지하여 아래 회사명을 기입하여 '확인'을 클릭하시기 바랍니다.</span>
		<div class="dc_bottom_box_lft">
			<div class="box_row">
				<span><input type="checkbox" id="companyUserCheck" /><label
					for="companyUserCheck">직접입력</label></span> <span>회사명(한글) : <input
					type="text" id="companyUserKorNm" maxlength="325" disabled /></span> <span>회사명(영어)
					: <input type="text" id="companyUserEngNm" maxlength="250" disabled />
				</span>
			</div>
		</div>
	</div>
</div>
<div class="egate_dialog_wrap" id="consCompanySearch" title="자문사 검색"
	style="display: none;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<div class="dc_top_box dc_pop_top_box">
		<div class="dc_top_box_lft" id="consCompanySearchConditionBox">
			<div class="box_row">
				<span>회사명 (한글)</span> <span> <input type="text"
					id="inputConsCompanySearchParam" autofocus />
				</span>
			</div>
		</div>
		<div class="dc_top_box_rgt">
			<button type="button" class="btn_red_wide" id="btnConsCompanySearch"
				title="검색">검색</button>
		</div>
	</div>
	<div class="mt10 dialog_data_head">
		<table class="dc_header_table tac">
			<colgroup>
				<col width="20%" />
				<col width="40%" />
				<col width="40%" />
			</colgroup>
			<tr>
				<td>회사코드</td>
				<td>기업명(한글)</td>
				<td>기업명(영문)</td>
			</tr>
		</table>
	</div>
	<div class="dialog_data_list">
		<table class="dc_data_table_nobdr tac">
		</table>
	</div>
	<div class="dc_bottom_box">
		<span>* 직접입력할 경우, 체크를 유지하여 아래 회사명을 기입하여 '확인'을 클릭하시기 바랍니다.</span>
		<div class="dc_bottom_box_lft">
			<div class="box_row">
				<span><input type="checkbox" id="consCompanyUserCheck" /><label
					for="consCompanyUserCheck">직접입력</label></span> <span>회사명(한글) : <input
					type="text" id="consCompanyUserKorNm" maxlength="325" disabled /></span> <span>회사명(영어)
					: <input type="text" id="consCompanyUserEngNm" maxlength="250"
					disabled />
				</span>
			</div>
		</div>
	</div>
</div>
<div class="egate_dialog_wrap" id="industrySearch" title="Industry 검색"
	style="display: none;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<div id="industrySearchGrid" style="height: 400px"></div>
	<!-- <div class="dc_top_box dc_pop_top_box">
				<div class="dc_top_box_lft" id="industrySearchConditionBox">
					<div class="box_row">
						<span>Industry (한글)</span>
						<span>
							 <input type="text" id="inputIndustrySearchParam" autofocus/>
						</span>
					</div>
				</div>
				<div class="dc_top_box_rgt">
					<button type="button" class="btn_red_wide" id="btnIndustrySearch" title="검색">검색</button>
				</div>
			</div>
			<div class="mt10 dialog_data_head">
				<table class="dc_header_table tac">
					<colgroup>
						<col width="10%" />
						<col width="40%" />
						<col width="10%" />
						<col width="40%" />
					</colgroup>
					<tr>
						<td>코드</td>
						<td>그룹</td>
						<td>코드</td>
						<td>Industry</td>
					</tr>
				</table>
			</div>
			<div class="dialog_data_list">
				<table class="dc_data_table_nobdr tac">
				</table>
			</div> -->
</div>
<div class="egate_dialog_wrap" id="nationSearch" title="국가 검색"
	style="display: none;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<div class="dc_top_box dc_pop_top_box">
		<div class="dc_top_box_lft" id="nationSearchConditionBox">
			<div class="box_row">
				<span>국가명 (한글)</span> <span> <input type="text"
					id="inputNationSearchParam" autofocus />
				</span>
			</div>
		</div>
		<div class="dc_top_box_rgt">
			<button type="button" class="btn_red_wide" id="btnNationSearch"
				title="검색">검색</button>
		</div>
	</div>
	<div class="mt10 dialog_data_head">
		<table class="dc_header_table tac">
			<colgroup>
				<col width="50%" />
				<col width="50%" />
			</colgroup>
			<tr>
				<td>국가코드</td>
				<td>국가명</td>
			</tr>
		</table>
	</div>
	<div class="dialog_data_list">
		<table class="dc_data_table_nobdr tac">
		</table>
	</div>
	<div class="dc_bottom_box">
		<span>* 직접입력할 경우, 체크를 유지하여 아래 국가명을 기입하여 '확인'을 클릭하시기 바랍니다.</span>
		<div class="dc_bottom_box_lft">
			<div class="box_row">
				<span><input type="checkbox" id="nationUserCheck" /><label
					for="nationUserCheck">직접입력</label></span> <span>국가명 : <input
					type="text" id="nationUserNm" maxlength="125" disabled /></span>
			</div>
		</div>
	</div>
</div>
<div class="egate_dialog_wrap" id="editCredential" title="수정"
	style="display: none;">
	<div style="height: 500px; overflow: hidden; overflow-y: scroll;">
		<div class="dc_con_section">
			<h3 class="dc_con_title font_eng">Project</h3>
			<table class="dc_view_table">
				<colgroup>
					<col width="12%" />
					<col width="12%" />
					<col width="26%" />
					<col width="24%" />
					<col width="26%" />
				</colgroup>
				<tbody>
					<tr id="credtgt">
						<th colspan="2">대상 여부</th>
						<td id="credtgt_list"></td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<th colspan="2"><span class="font_eng">Product Code</span>(최초)</th>
						<td><input type="text" readonly="readonly" class="inp_change"
							id="pdt_orig"></input></td>
						<th><span class="font_eng">Product Code</span>(변경)</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="pdt" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제" style="display: none">삭제</button>
								<button type="button" class="btn_pdt_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
					<tr>
						<th class="font_eng">Client</th>
						<th>국가</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="cli_nat" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_nation_sel btn btn_red">선택</button>
							</div>
						</td>
						<th class="font_eng">Industry</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly"
									class="inp_change industryNm" id="cli_ind" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_industry_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
					<tr>
						<th rowspan="7" class="font_eng">Target</th>
						<th rowspan="2">기업명</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="tar0_comkr" seq="" code="" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_company_sel btn btn_red">선택</button>
							</div>
						</td>
						<th>국가</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="tar0_nat" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_nation_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
					<tr>
						<td><input type="text" readonly="readonly"
							class="companyEngNm inp_change" id="tar0_comen" /></td>
						<th class="font_eng">Industry</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly"
									class="inp_change industryNm" id="tar0_ind" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_industry_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
					<tr>
						<th rowspan="2">기업명</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="tar1_comkr" seq="" code="" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_company_sel btn btn_red">선택</button>
							</div>
						</td>
						<th>국가</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="tar1_nat" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_nation_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
					<tr>
						<td><input type="text" readonly="readonly"
							class="companyEngNm inp_change" id="tar1_comen" /></td>
						<th class="font_eng">Industry</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly"
									class="inp_change industryNm" id="tar1_ind" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_industry_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
					<tr>
						<th rowspan="2">기업명</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="tar2_comkr" seq="" code="" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_company_sel btn btn_red">선택</button>
							</div>
						</td>
						<th>국가</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="tar2_nat" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_nation_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
					<tr>
						<td><input type="text" readonly="readonly"
							class="companyEngNm inp_change" id="tar2_comen" /></td>
						<th class="font_eng">Industry</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly"
									class="inp_change industryNm" id="tar2_ind" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_industry_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="3"><input type="text" class="inp_change"
							id="tar_etc" /></td>
					</tr>
					<tr>
						<th rowspan="2">이해관계자</th>
						<th rowspan="2">기업명</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="iter_comkr" seq="" code="" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_company_sel btn btn_red">선택</button>
							</div>
						</td>
						<th>국가</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="iter_nat" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_nation_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
					<tr>
						<td><input type="text" readonly="readonly"
							class="companyEngNm inp_change" id="iter_comen" /></td>
						<th class="font_eng">Industry</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly"
									class="inp_change industryNm" id="iter_ind" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_industry_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
					<tr>
						<th colspan="2">상세업무내용</th>
						<td colspan="3"><input type="text" class="inp_change"
							id="detail_work" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="dc_con_section" id="brs_section" style="display: none;">
			<h3 class="dc_con_title">BRS</h3>
			<table class="dc_view_table">
				<colgroup>
					<col width="12%" />
					<col width="12%" />
					<col width="26%" />
					<col width="24%" />
					<col width="26%" />
				</colgroup>
				<tbody>
					<tr>
						<th rowspan="2">채권종류</th>
						<th>담보</th>
						<td id="security_list" colspan="3"></td>
					</tr>
					<tr>
						<th>무담보</th>
						<td id="unsecured_list" colspan="3"></td>
					</tr>
					<tr>
						<th colspan="2">매각방식</th>
						<td id="disposal_list"></td>
						<th>OPB</th>
						<td><input type="number" class="inp_change" id="opb_data" />
						</td>
					</tr>
					<tr>
						<th>매수처</th>
						<th>기업명</th>
						<td colspan="3">
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="brs_comkr" seq="" code="" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_company_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="dc_con_section" id="rcf_section" style="display: none;">
			<h3 class="dc_con_title">RCF</h3>
			<table class="dc_view_table">
				<colgroup>
					<col width="12%" />
					<col width="12%" />
					<col width="26%" />
					<col width="24%" />
					<col width="26%" />
				</colgroup>
				<tbody>
					<tr>
						<th colspan="2" rowspan="2">구분</th>
						<td rowspan="2" id="rcf_list"><select id="rcf_type"
							class="inp_change">
						</select></td>
						<th>구분상세</th>
						<td id="rcf_detail_list"><select id="rcf_detail_type"
							class="inp_change"></select></td>
					</tr>
					<tr>
						<th>구분기타</th>
						<td><input type="text" class="inp_change" id="rcf_etc" /></td>
					</tr>
					<tr>
						<th colspan="2">토지면적</th>
						<td><input type="number" class="inp_change" id="land_area" />
						</td>
						<th>연면적</th>
						<td><input type="number" class="inp_change" id="total_area" />
						</td>
					</tr>
					<tr>
						<th rowspan="2">RCF</th>
						<th>기업명</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="rcf_comkr" seq="" code="" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_company_sel btn btn_red">선택</button>
							</div>
						</td>
						<th>국적</th>
						<td>
							<div class="data_cell edit_mode rgt_btn">
								<input type="text" readonly="readonly" class="inp_change"
									id="rcf_nat" code="" />
								<button type="button" class="btn ico_delete btn_remove_sel"
									title="삭제">삭제</button>
								<button type="button" class="btn_nation_sel btn btn_red">선택</button>
							</div>
						</td>
					</tr>
					<tr>
						<th>도시</th>
						<td><input type="text" class="inp_change" id="rcf_city" /></td>
						<th>주소</th>
						<td><input type="text" class="inp_change" id="rcf_addr" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div class="egate_dialog_wrap" id="mailHistory" title="메일 발송 History"
	style="display: none; box-sizing: border-box;">

	<!-- title -->
	<div style="clear: both; height: 30px;">
		<div
			style="float: left; width: 30%; height: 100%; padding: 5px; box-sizing: border-box; font-weight: bold;">■
			메일 발송 내역</div>
		<div
			style="float: left; width: 70%; height: 100%; padding: 5px; box-sizing: border-box; font-weight: bold;">■
			메일 수신 리스트</div>
	</div>

	<!-- history contents -->
	<div style="clear: both; height: 200px;">
		<div
			style="float: left; width: 30%; height: 100%; padding: 5px; box-sizing: border-box;">
			<div class="div-grid" id="popup-grid-mailhistory"
				style="border: 1px solid #c7c8ca; height: 100%; width: 100%"></div>
		</div>

		<div
			style="float: right; width: 70%; height: 100%; padding: 5px; box-sizing: border-box;">
			<div class="div-grid" id="popup-grid-rcpt"
				style="border: 1px solid #c7c8ca; height: 100%; width: 100%"></div>
		</div>
	</div>

	<!-- title -->
	<div style="clear: both; height: 30px;">
		<div
			style="float: left; width: 100%; height: 100%; padding: 5px; box-sizing: border-box; font-weight: bold;">■
			발송된 메일 내용</div>
	</div>

	<!-- mail contents -->
	<div style="clear: both; height: 250px;">
		<div
			style="clear: both; width: 100%; height: auto; padding: 0px 5px; box-sizing: border-box;">
			<table
				style="border-collapse: collapse; border: 0px; margin: 0px; padding: 0px; width: 100%">
				<col width="12%" />
				<col width="88%" />
				<tr>
					<td
						style="border: 1px solid #c7c8ca; padding-left: 10px; font-weight: bold;">Subject</td>
					<td style="border: 1px solid #c7c8ca"><div class=""
							id="popup-mail-subject"
							style="height: 30px; width: 100%; padding: 2px 5px; line-height: 30px;"></div></td>
				</tr>
				<tr>
					<td
						style="border: 1px solid #c7c8ca; padding-left: 10px; font-weight: bold;">Contents</td>
					<td style="border: 1px solid #c7c8ca;"><div class=""
							id="popup-mail-contents"
							style="height: auto; min-height: 200px; width: 100%; padding: 2px 5px;"></div></td>
				</tr>
			</table>
		</div>
	</div>
</div>
<div class="egate_dialog_wrap" id="pdtCategory" title="카테고리 관리"
	style="display: none; box-sizing: border-box;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<div class="dc_top_box dc_pop_top_box">
		<div class="dc_top_box_lft" id="pdtCategoryConditionBox">
			<div class="box_row">
				<span>참조년도</span> <span> <select id="dialog_sel_refyearly"
					name="dialog_sel_refyearly"></select>
				</span>
			</div>
		</div>
		<div class="dc_top_box_rgt">
			<button type="button" class="btn_red_wide"
				id="dialog_btn_category_save" title="저장">저장</button>
		</div>
	</div>
	<div class="dc_scroll_table_area" id="div-dialog-category-grid-area"
		style="clear: both; height: 300px;"></div>
	<div class="dc_top_box dc_pop_top_box">
		<div class="dc_top_box_lft" id="pdtCategoryConditionBox">
			<div class="box_row">
				<span>시스템 카테고리</span> <span> <select
					id="dialog_system_category" name="dialog_system_category"
					style="width: 300px;"></select>
				</span>
			</div>
		</div>
		<div class="dc_top_box_rgt">
			<button type="button" class="btn_gray_wide"
				id="dialog_btn_category_add" title="추가">추가</button>
		</div>
	</div>
</div>
<div class="egate_dialog_wrap" id="pdtAdd" title="PDT 추가"
	style="display: none; box-sizing: border-box;">
	<!-- 클래스 감싸는 기본 클래스 + 특정클래스명으로 진행한다. -->
	<div class="dc_scroll_table_area" id="div-dialog-pdt-grid-area"
		style="clear: both; height: 300px;"></div>
</div>
<div class="egate_dialog_wrap" id="sendMail" title="메일 발송"
	style="display: none; box-sizing: border-box;">
	<div style="width: 100%; height: 500px">
		<div class="div-grid" id="popup-grid-mail-targetlist"
			style="border: 1px solid #c7c8ca; height: 100%; width: 100%"></div>
	</div>
</div>
<div class="egate_dialog_wrap" id="excel_except_credential"
	title="Credential 대상 제외" style="display: none; box-sizing: border-box;">
	<div id="excel_except_credential_container"></div>
</div>
<div class="egate_dialog_wrap" id="select_pdt" title="Product Code 선택"
	style="display: none; box-sizing: border-box;">
	<div id="select_pdt_container" style="width: 100%; height: 500px">
		<div style="height: 30px;">
			<div
				style="display: inline-block; float: left; line-height: 30px; width: 60px">참조년도</div>
			<div id="select_pdt_yearly"
				style="display: inline-block; float: left"></div>
		</div>
		<div style="height: 470px; clear: both;">
			<div id="select_pdt_grid" style="width: 100%; height: 100%"></div>
		</div>
	</div>
</div>
