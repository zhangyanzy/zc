webpackJsonp([4],{"5SyZ":function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=l(i("Dd8w")),u=l(i("eOoE")),n=l(i("IcnI")),r=i("NYxO"),c=l(i("CE8n")),s=l(i("NdN2")),d=l(i("ObEq"));function l(t){return t&&t.__esModule?t:{default:t}}e.default={name:"offline_activity",data:function(){return{result:{},activityId:this.$route.query.id}},computed:(0,a.default)({},(0,r.mapState)({activityStatus:function(t){return t.activityModule.activityStatus}})),watch:{activityStatus:{handler:function(t,e){this.result.activityStatus=t},deep:!1}},components:{activityMemberHeader:c.default,activityPicture:s.default,activityTab:d.default},created:function(){this.$nextTick(function(){var t=this;u.default.activityDetail(this.activityId,function(e){t.result=e.data,document.title=t.result.name,n.default.commit("UPDATE_ACTIVITY_STATUS",{activityStatus:e.data.activityStatus}),u.default.getMoneyUserList({id:t.activityId,currentResult:0,pageSize:5},function(e){t.result.finishUser=e.data})})})}}},HATb:function(t,e){},nXLv:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=i("5SyZ"),u=i.n(a);for(var n in a)"default"!==n&&function(t){i.d(e,t,function(){return a[t]})}(n);var r=i("xKES");var c=function(t){i("HATb")},s=i("VU/8")(u.a,r.a,!1,c,null,null);e.default=s.exports},xKES:function(t,e,i){"use strict";var a={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",[e("div",{staticClass:"activity-header-box"},[e("activity-member-header",{attrs:{result:this.result}}),this._v(" "),e("activity-picture",{attrs:{result:this.result}}),this._v(" "),e("activity-tab",{attrs:{result:this.result}})],1)])},staticRenderFns:[]};e.a=a}});
//# sourceMappingURL=4.b51d2813df35c7a70311.js.map