webpackJsonp([5],{J5kI:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=s("ocgw"),n=s.n(o);for(var l in o)"default"!==l&&function(t){s.d(e,t,function(){return o[t]})}(l);var r=s("WXIb");var a=function(t){s("hZ4T")},i=s("VU/8")(n.a,r.a,!1,a,null,null);e.default=i.exports},WXIb:function(t,e,s){"use strict";var o={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("scroller",{ref:"scroller",attrs:{"lock-x":"","scrollbar-y":!1,"use-pulldown":!0,"use-pullup":t.hasMore,"pullup-config":t.upobj},on:{"on-pulldown-loading":function(e){t.pageNo=1,t.getArticleList()},"on-pullup-loading":function(e){t.pageNo++,t.getArticleList()}},model:{value:t.scrollerStatus,callback:function(e){t.scrollerStatus=e},expression:"scrollerStatus"}},[s("div",{staticStyle:{"box-sizing":"border-box"}},t._l(t.memberList,function(e){return s("div",{staticClass:"row join-member-list-box"},[s("img",{staticClass:"join-member-img",attrs:{src:e.avatar}}),t._v(" "),s("div",{staticClass:"join-info-box"},[s("span",{staticClass:"font-12 f-black span-1"},[t._v(t._s(e.name))]),t._v(" "),s("span",{staticClass:"fr font-12 f-light span-2"},[t._v(t._s(t._f("formatDateToCN")(e.joinTime)))])])])}))])},staticRenderFns:[]};e.a=o},hZ4T:function(t,e){},ocgw:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=l(s("S8Wg")),n=l(s("eOoE"));function l(t){return t&&t.__esModule?t:{default:t}}e.default={components:{Scroller:o.default},name:"join_activity_member_list",props:["result"],data:function(){return{activityId:this.$route.query.id,memberList:[],hasMore:!0,pageNo:1,scrollerStatus:{pullupStatus:"default"},upobj:{content:"请上拉刷新数据",pullUpHeight:60,height:40,autoRefresh:!1,downContent:"请上拉刷新数据",upContent:"请上拉刷新数据",loadingContent:'<span style="color: red">加载中...</span>',clsPrefix:"xs-plugin-pullup-"}}},methods:{getArticleList:function(){var t=10*(this.pageNo-1),e=this;n.default.getMoneyUserList({id:this.activityId,currentResult:t,pageSize:10},function(s){s.total>t+10?(e.scrollerStatus.pullupStatus="default",e.hasMore=!0):(e.scrollerStatus.pullupStatus="disabled",e.hasMore=!1),1==e.pageNo&&(e.memberList=[]),e.memberList=e.memberList.concat(s.data),e.$nextTick(function(){e.$refs.scroller.donePullup(),e.$refs.scroller.donePulldown()})})}},mounted:function(){this.getArticleList()}}}});
//# sourceMappingURL=5.b7a3b463e95991fde02b.js.map