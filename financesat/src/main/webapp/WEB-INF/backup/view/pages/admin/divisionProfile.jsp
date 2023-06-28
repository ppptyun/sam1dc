<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>

.radio-container.col-direction{
	display: blcok;
}
.radio-container {
	display: inline-block;
	margin-left: -20px;
}
.radio-container label{
	margin-left: 5px;
}
.radio-container input {
	cursor: pointer;
	margin-left: 15px;
}

</style>
<div class="boxWhite" id="profilePage">
	<div class="titArea">
		<h2 class="titDep2  marT30">Lock 정책</h2><span class="marT30" style="display: inline-block">(관리자 설정 전용)</span>
		<div v-if="authLevel <= 1" class="btnArea side">
			<button type="button" @click="btnSave" class="btnPwc btnM action">Save</button>
		</div>
	</div>
	<table class="tblH">
		<colgroup>
			<col style="width:250px;">
			<col style="width:180px;">
			<col style="width:auto;">
		</colgroup>
		<thead>
			<tr>
				<th>구분</th>
				<th>값</th>
				<th>설명</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th style="text-align:left">전체 Lock 적용 기준</th>
				<td>
					<calendar :is-read="authLevel !== 0" id="lockProfYymm" v-model="prof.lockProfYymm" format="yyyy-mm" min-date="2018-01"></calendar>
				</td>
				<td style="text-align:left">입력된 종료월 포함 그 이전의 사업연도 자료는 모두 Lock 이 됩니다.<br>(표준감사시간과 Budget 모두 Lock이 되며 최우선 함.) </td>
			</tr>
			<tr>
				<th style="text-align:left">본부별 프로파일 적용 기준<br>(Retain 제외 - Retain은 항상 적용)</th>
				<td>
					<div class="frmNowrap">
						<number :is-read="authLevel !== 0"  id="lockProfMonths" v-model="prof.lockProfMonths" ></number><span class="unit">개월</span>
					</div>
				</td>
				<td style="text-align:left">
					현재 날짜 &gt; (프로젝트의 사업연도 종료월 - 입력된 개월 수) 
					<br> ex)  6으로 설정되어 있고, 프로젝트의 사업연도 종료월이 2020-12라면 해당 프로젝트는 2020-07월부터 적용 됨
				</td>
			</tr>
			<tr>
				<th style="text-align:left">본부별 프로파일 적용 시<br>적용받는 Status</th>
				<td style="text-align:left" v-if="authLevel === 0">
					<span v-for="(item, idx) in prof.statList" class="radio-container" style="display:block">
						<input 
							type="checkbox"  
							name="trgtstat"
							:key="'trgtstat_' + item.cd"
							:id="'trgtstat_' + item.cd"
							:value="item.cd"
							v-model="prof.lockTrgtStat"
						 />
						<label :for="'trgtstat_' + item.cd">{{item.nm}}</label>
					</span>
				</td>
				<td v-else>
					{{prof.statList
						.filter(function(item){
							return prof.lockTrgtStat.includes(item.cd);
						})
						.map(function(item){
							return item.nm;
						})
						.join(', ')
					}}
				</td>
				<td style="text-align:left">
					등록된 프로젝트 중 선택된 Status만 본부별 프로파일을 적용 받음.
				</td>
			</tr>
		</tbody>
	</table>
	
	<div class="titArea">
		<h2 class="titDep2  marT30">본부별 프로파일</h2>
		<div class="side">
		<span class="marT30" style="display: inline-block">※ 음영 처리된 항목은 관리자에 의해 수정을 할 수 없도록 설정된 항목입니다.</span>
		</div>
	</div>
	<table id="retain-grid" class="tblH">
		<caption></caption>
		<colgroup>
			<col style="width:250px;" />
			<col style="width:auto;" />
			<col :style="{width: authLevel === 0?'120px':'150px'}" />
			<col v-if="authLevel === 0" style="width:80px;" />
			<col :style="{width: authLevel === 0?'120px':'150px'}" />
			<col v-if="authLevel === 0" style="width:80px;" />
			<col :style="{width: authLevel === 0?'120px':'150px'}" />
			<col v-if="authLevel === 0" style="width:80px;" />
		</colgroup>
		<thead>		
			<tr>
				<th scope="col">본부</th>
				<th scope="col">연관 팀코드</th>
				<th scope="col" :colspan="authLevel === 0?2:1">Retain 전송<br>허용 여부</th>
				<th scope="col" :colspan="authLevel === 0?2:1">표준감사시간 수정<br>허용 여부</th>
				<th scope="col" :colspan="authLevel === 0?2:1">Budget 수정<br>허용 여부</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(item, index) in prof.profAuthByBonb">
				<td style="text-align:left"><span class='bonbNm'>{{item.cd + '/' + item.nm}}</span></td>
				<td style="text-align:left"><span class='teamList'>{{item.teamList}}</span></td>
				<td style="text-align:center" :style="{backgroundColor: item.retainAdmYn ==='Y'? '#f5f5f5': 'white'}">
					<span class="radio-container"  v-if="authLevel <= 1">
						<input 
							:key="item.cd + '_retainYn_Y'"
							:id="item.cd + '_retainYn_Y'" 
							:name="item.cd + '_retainYn'" 
							type="radio" 
							value="Y" 
							v-model="item.retainYn" 
							:disabled="item.retainAdmYn==='Y'" />
						<label :for="item.cd + '_retainYn_Y'">Y</label>
						<input 
							:id="item.cd + '_retainYn_N'" 
							:name="item.cd + '_retainYn'"  
							type="radio" 
							value="N" 
							v-model="item.retainYn"  
							:disabled="item.retainAdmYn==='Y'" />
						<label :for="item.cd + '_retainYn_N'">N</label>
					</span>
					<span v-else> {{item.retainYn}} </span>
				</td>
				<td v-if="authLevel === 0">
					<span class="radio-container">
						<input 
							:key="item.cd + '_retainYn_lock'"
							:id="item.cd + '_retainYn_lock'" 
							type="checkbox" 
							@change="onChangeAdmin('retain', index, $event.target.checked)" 
							:checked="item.retainAdmYn==='Y'" />
						<label :for="item.cd + '_retainYn_lock'">잠금</label>
					</span>
				</td>
				<td style="text-align:center" :style="{backgroundColor: item.satAdmYn ==='Y'? '#f5f5f5': 'white'}">
					<span class="radio-container" v-if="authLevel <= 1">
						<input
							:key="item.cd + '_satYn_Y'"  
							:id="item.cd + '_satYn_Y'" 
							:name="item.cd + '_satYn'" 
							type="radio" 
							value="Y"  
							v-model="item.satYn" 
							:disabled="item.satAdmYn ==='Y'" />
						<label :for="item.cd + '_satYn_Y'">Y</label>
						<input
							:key="item.cd + '_satYn_N'"  
							:id="item.cd + '_satYn_N'" 
							:name="item.cd + '_satYn'"  
							type="radio" 
							value="N"  
							v-model="item.satYn" 
							:disabled="item.satAdmYn ==='Y'" />
						<label :for="item.cd + '_satYn_N'">N</label>
					</span>
					<span v-else> {{item.satYn}} </span>
				</td>
				<td v-if="authLevel === 0">
					<span class="radio-container">
						<input
							:key="item.cd + '_satYn_lock'" 
							:id="item.cd + '_satYn_lock'" 
							type="checkbox" 
							@change="onChangeAdmin('sat', index, $event.target.checked)" 
							:checked="item.satAdmYn ==='Y'" />
						<label :for="item.cd + '_satYn_lock'">잠금</label>
					</span>
				</td>
				<td style="text-align:center"  :style="{backgroundColor: item.bdgtAdmYn ==='Y'? '#f5f5f5': 'white'}">
					<span class="radio-container" v-if="authLevel <= 1">
						<input
							:key="item.cd + '_bdgtYn_Y'" 
							:id="item.cd + '_bdgtYn_Y'" 
							:name="item.cd + '_bdgtYnYn'" 
							type="radio" 
							value="Y"
							v-model="item.bdgtYn" 
							:disabled="item.bdgtAdmYn ==='Y'" />
						<label :for="item.cd + '_bdgtYn_Y'">Y</label>
						<input
							:key="item.cd + '_bdgtYn_N'" 
							:id="item.cd + '_bdgtYn_N'" 
							:name="item.cd + '_bdgtYnYn'"  
							type="radio" 
							value="N"  
							v-model="item.bdgtYn" 
							:disabled="item.bdgtAdmYn ==='Y'" />
						<label :for="item.cd + '_bdgtYn_N'">N</label>
					</span>
					<span v-else> {{item.bdgtYn}} </span>
				</td>
				<td v-if="authLevel === 0">
					<span class="radio-container">
						<input
							:key="item.cd + '_bdgtYn_lock'" 
							:id="item.cd + '_bdgtYn_lock'" 
							type="checkbox" 
							@change="onChangeAdmin('bdgt', index, $event.target.checked)" 
							:checked="item.bdgtAdmYn ==='Y'" />
						<label :for="item.cd + '_bdgtYn_lock'">잠금</label>
					</span>
				</td>
			</tr>
		</tbody>					
	</table>
</div>
<script type="text/javascript">
let oData = {};
const divisionProfileObj = new Vue({
	el: '#profilePage',
	data: {
		isBonbAdmin: "${sessionScope.SAMIL_SESSION_KEY.val1 == 'role_admin'}" === "true",
		isAdmin: "${sessionScope.SAMIL_SESSION_KEY.roleCd == 'sysadmin' || sessionScope.SAMIL_SESSION_KEY.roleCd == 'admin'}" === "true", 
		prof: {
			lockProfYymm: '',
			lockProfMonths: 0,
			lockTrgtStat: [],
			profAuthByBonb: [],
			statList: [],
		}
	},
	created: function() {
		this.loadData();
	},
	computed: {
		authLevel : function() {
			//return 1 
			return this.isAdmin ? 0 : this.isBonbAdmin ? 1 : 2;
		}
	},
	mounted: function(){
		
	},
	methods: {
		btnSave: function() {
			const thisObj = this;
			const origin = JSON.parse(oData);
			const param = {};
			
			if(this.isNull(this.prof.lockProfYymm)) {
				Heler.MessageBox('[전체 Lock 적용 기준] 값을 입력하세요.');
				return;
			}
			
			if(this.isNull(this.prof.lockProfMonths)) {
				Heler.MessageBox('[본부별 프로파일 적용 기준] 값을 입력하세요.');
				return;
			}
			
			if(this.isNull(this.prof.lockTrgtStat)) {
				Heler.MessageBox('[적용 Status] 값을 선택하세요.');
				return;
			}
			
			if(origin.lockProfYymm !== this.prof.lockProfYymm){
				param.lockProfYymm = this.prof.lockProfYymm;
			} 
			if(origin.lockProfMonths !== this.prof.lockProfMonths) {
				param.lockProfMonths = this.prof.lockProfMonths;
			}
			if(!this.isEqaualArray(origin.lockTrgtStat, this.prof.lockTrgtStat)) {
				param.lockTrgtStat = this.prof.lockTrgtStat.join(",");
			}
			
			
			// 본부별 프로파일에서 변경된 목록 Filter
			const props = ['retainYn', 'retainAdmYn', 'satYn', 'satAdmYn', 'bdgtYn', 'bdgtAdmYn']
			const saveList = this.prof.profAuthByBonb.filter(function(data, idx){
				// 순서가 유지되기때문에...
				for(let i=0 ; i<props.length; i++){
					if(data[props[i]] !== origin.profAuthByBonb[idx][props[i]])
						return true
				}
				return false;
			})
			if(saveList.length > 0) {param.profAuthByBonb = saveList;}
			
			let keys = Object.keys(param); // 저장할 대상이 있는지 확인하기 위함 
			if(keys.length > 0) {
				Helper.post("/admin/save/divisionProfile", param)
				.done(function(){
					Helper.MessageBox('저장 완료 되었습니다.')
					thisObj.loadData();
				})
			}else{
				Helper.MessageBox('변경된 항목이 없습니다.');
			}
		},
		isNull: function(value) {
			return value === null || value === undefined || $.trim(value) === '';
		},
		loadData: function() {
			let thisObj = this;
			Helper.post("/admin/get/divisionProfile").done(function(data){
				data.lockTrgtStat = data.lockTrgtStat.split(',');
				oData = JSON.stringify(data);
				Object.assign(thisObj.prof, data);
			});
		},
		onChangeAdmin: function(profType, idx, isChecked) {
			switch(profType){
			case 'retain':
				this.prof.profAuthByBonb[idx].retainAdmYn = isChecked?'Y':'N';
				break;
			case 'sat':
				this.prof.profAuthByBonb[idx].satAdmYn = isChecked?'Y':'N';
				break;
			case 'bdgt':
				this.prof.profAuthByBonb[idx].bdgtAdmYn = isChecked?'Y':'N';
				break;
			}
		},
		onChangeLogTrgtStat: function () {
			
		},
		isEqaualArray: function(array1, array2) {
			const arr1 = array1.sort();
			const arr2 = array2.sort();
			return arr1.length === arr2.length && arr1.every(function(value, index) { return value === arr2[index]});
		}
	},
	watch: {
		lockTrgtStat: function(value) {
			console.log(value)
		}
	}
	
});
</script>
