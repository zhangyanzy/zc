webpackJsonp([4],{IasW:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o,s=n("7+uW"),i=(o=s)&&o.__esModule?o:{default:o};e.default={data:function(){return{code:this.$route.query.code}},methods:{copyCode:function(){this.$refs.code.select();try{if(!document.execCommand("Copy"))throw DOMException;i.default.$vux.toast.show({text:"复制成功"})}catch(t){i.default.$vux.toast.show({text:"复制失败",type:"warn"})}}}}},KKnE:function(t,e,n){t.exports=n.p+"static/invite-user.829e979.png"},dnkk:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n("IasW"),s=n.n(o);for(var i in o)"default"!==i&&function(t){n.d(e,t,function(){return o[t]})}(i);var r=n("s1m7"),u=n("VU/8")(s.a,r.a,!1,null,null,null);e.default=u.exports},s1m7:function(t,e,n){"use strict";var o={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticStyle:{width:"100vw",overflow:"hidden"}},[e("img",{staticStyle:{width:"100%",height:"100%"},attrs:{src:n("KKnE")}}),this._v(" "),e("input",{ref:"code",attrs:{type:"hidden"},domProps:{value:this.code}}),this._v(" "),e("a",{on:{click:this.copyCode}},[this._v("复制")])])},staticRenderFns:[]};e.a=o}});
//# sourceMappingURL=4.e830667e5a6423b08157.js.map