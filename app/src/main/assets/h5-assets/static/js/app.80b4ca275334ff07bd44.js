webpackJsonp([10],{"+H76":function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n={render:function(){var t=this.$createElement;return(this._self._c||t)("div",[this._v("\n    404\n")])},staticRenderFns:[]},a=i("VU/8")(null,n,!1,null,null,null);e.default=a.exports},"/8tN":function(t,e,i){"use strict";var n={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"bg",attrs:{id:"app"}},[e("transition",{attrs:{name:"vux-pop-"+("forward"===this.direction?"in":"out")}},[e("keep-alive",[e("router-view",{staticClass:"router-view vux-pop-out-enter-active"})],1)],1)],1)},staticRenderFns:[]};e.a=n},"/kga":function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=i("92Bb"),a=i.n(n);for(var o in n)"default"!==o&&function(t){i.d(e,t,function(){return n[t]})}(o);var s=i("4l25");var u=function(t){i("6Erz")},r=i("VU/8")(a.a,s.a,!1,u,null,null);e.default=r.exports},0:function(t,e){},"1cb1":function(t,e,i){"use strict";var n,a=i("7+uW");((n=a)&&n.__esModule?n:{default:n}).default.directive("dragEle",{bind:function(t){t.addEventListener("mousedown",function(e){var i=e||event,n=i.clientX-t.offsetLeft,a=i.clientY-t.offsetTop;document.onmousemove=function(e){var i=e||event;t.style.left=i.clientX-n+"px",t.style.top=i.clientY-a+"px"},document.onmouseup=function(){document.onmousemove=null,document.onmouseup=null}})}})},"2RPQ":function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n,a=i("/kga"),o=(n=a)&&n.__esModule?n:{default:n};e.default={name:"alert",components:{XDialog:o.default},created:function(){void 0!==this.value&&(this.showValue=this.value)},props:{value:Boolean,title:String,content:String,buttonText:String,hideOnBlur:{type:Boolean,default:!1},maskTransition:{type:String,default:"vux-mask"},dialogTransition:{type:String,default:"vux-dialog"},maskZIndex:[Number,String]},data:function(){return{showValue:!1}},methods:{_onHide:function(){this.showValue=!1}},watch:{value:function(t){this.showValue=t},showValue:function(t){this.$emit("input",t)}}}},"4l25":function(t,e,i){"use strict";var n={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"vux-x-dialog",class:{"vux-x-dialog-absolute":"VIEW_BOX"===this.layout}},[e("transition",{attrs:{name:this.maskTransition}},[e("div",{directives:[{name:"show",rawName:"v-show",value:this.show,expression:"show"}],staticClass:"weui-mask",style:this.maskStyle,on:{click:this.hide}})]),this._v(" "),e("transition",{attrs:{name:this.dialogTransition}},[e("div",{directives:[{name:"show",rawName:"v-show",value:this.show,expression:"show"}],class:this.dialogClass,style:this.dialogStyle},[this._t("default")],2)])],1)},staticRenderFns:[]};e.a=n},"5reh":function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});e.UPDATE_DIRECTION="UPDATE_DIRECTION",e.HISTORY="HISTORY",e.USER_TOKEN="USER_TOKEN",e.UPDATE_ACTIVITY_STATUS="UPDATE_ACTIVITY_STATUS",e.UPDATE_JOIN="UPDATE_JOIN",e.HTTP_LOADING="HTTP_LOADING"},"6Erz":function(t,e){},"7uHu":function(t,e){},"8vzP":function(t,e){},"92Bb":function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n,a=i("JkZY"),o=(n=a)&&n.__esModule?n:{default:n};e.default={mixins:[o.default],name:"x-dialog",model:{prop:"show",event:"change"},props:{show:{type:Boolean,default:!1},maskTransition:{type:String,default:"vux-mask"},maskZIndex:[String,Number],dialogTransition:{type:String,default:"vux-dialog"},dialogClass:{type:String,default:"weui-dialog"},hideOnBlur:Boolean,dialogStyle:Object,scroll:{type:Boolean,default:!0,validator:function(t){return!0}}},computed:{maskStyle:function(){if(void 0!==this.maskZIndex)return{zIndex:this.maskZIndex}}},mounted:function(){"undefined"!=typeof window&&window.VUX_CONFIG&&"VIEW_BOX"===window.VUX_CONFIG.$layout&&(this.layout="VIEW_BOX")},watch:{show:function(t){this.$emit("update:show",t),this.$emit(t?"on-show":"on-hide"),t?this.addModalClassName():this.removeModalClassName()}},methods:{shouldPreventScroll:function(){var t=/iPad|iPhone|iPod/i.test(window.navigator.userAgent),e=this.$el.querySelector("input")||this.$el.querySelector("textarea");if(t&&e)return!0},hide:function(){this.hideOnBlur&&(this.$emit("update:show",!1),this.$emit("change",!1),this.$emit("on-click-mask"))}},data:function(){return{layout:""}}}},"97Uv":function(t,e,i){"use strict";var n={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"vux-toast"},[e("div",{directives:[{name:"show",rawName:"v-show",value:this.isShowMask&&this.show,expression:"isShowMask && show"}],staticClass:"weui-mask_transparent"}),this._v(" "),e("transition",{attrs:{name:this.currentTransition}},[e("div",{directives:[{name:"show",rawName:"v-show",value:this.show,expression:"show"}],staticClass:"weui-toast",class:this.toastClass,style:{width:this.width}},[e("i",{directives:[{name:"show",rawName:"v-show",value:"text"!==this.type,expression:"type !== 'text'"}],staticClass:"weui-icon-success-no-circle weui-icon_toast"}),this._v(" "),this.text?e("p",{staticClass:"weui-toast__content",style:this.style,domProps:{innerHTML:this._s(this.text)}}):e("p",{staticClass:"weui-toast__content",style:this.style},[this._t("default")],2)])])],1)},staticRenderFns:[]};e.a=n},Bfwr:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=i("Z5JB"),a=i.n(n);for(var o in n)"default"!==o&&function(t){i.d(e,t,function(){return n[t]})}(o);var s=i("z0nY");var u=function(t){i("HdoX")},r=i("VU/8")(a.a,s.a,!1,u,null,null);e.default=r.exports},"GVd+":function(t,e){},HR9g:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.API_BASE_URL=e.GAODE_MAP_KEY=e.isIOS=e.isAndroid=e.PRODUCTION_ENV=e.LOG_LEVEL=void 0;var n=function(t){if(t&&t.__esModule)return t;var e={};if(null!=t)for(var i in t)Object.prototype.hasOwnProperty.call(t,i)&&(e[i]=t[i]);return e.default=t,e}(i("RnvR"));e.LOG_LEVEL=n.DEBUG;var a=e.PRODUCTION_ENV=0,o=navigator.userAgent;e.isAndroid=0==a&&(o.indexOf("Android")>-1||o.indexOf("Adr")>-1),e.isIOS=0==a&&!!o.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),e.GAODE_MAP_KEY="16d95a2f157db464a9aa3f3d3a7aa61e",e.API_BASE_URL="http://appapi.zhaocaiapp.local/"},HdoX:function(t,e){},ITB2:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=o(i("YaEn")),a=o(i("IcnI"));function o(t){return t&&t.__esModule?t:{default:t}}e.default=[{name:"goBack",methodIOS:function(t,e){var i=a.default.state.routerModule.history-1>1;alert(i),i&&(alert(a.default.state.routerModule.history),n.default.back(-1),a.default.commit("HISTORY",{num:-2})),e(i)}}]},IcnI:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=l(i("7+uW")),a=l(i("NYxO")),o=l(i("Z4bK")),s=l(i("Vz5h")),u=l(i("pEJw")),r=l(i("NxOS"));function l(t){return t&&t.__esModule?t:{default:t}}n.default.use(a.default),e.default=new a.default.Store({modules:{routerModule:o.default,activityModule:u.default,axiosModule:r.default,userModule:s.default}})},LaXf:function(t,e,i){"use strict";var n={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"vux-alert"},[i("x-dialog",{attrs:{"mask-transition":t.maskTransition,"dialog-transition":t.dialogTransition,"hide-on-blur":t.hideOnBlur,"mask-z-index":t.maskZIndex},on:{"on-hide":function(e){t.$emit("on-hide")},"on-show":function(e){t.$emit("on-show")}},model:{value:t.showValue,callback:function(e){t.showValue=e},expression:"showValue"}},[i("div",{staticClass:"weui-dialog__hd"},[i("strong",{staticClass:"weui-dialog__title"},[t._v(t._s(t.title))])]),t._v(" "),i("div",{staticClass:"weui-dialog__bd"},[t._t("default",[i("div",{domProps:{innerHTML:t._s(t.content)}})])],2),t._v(" "),i("div",{staticClass:"weui-dialog__ft"},[i("a",{staticClass:"weui-dialog__btn weui-dialog__btn_primary",attrs:{href:"javascript:;"},on:{click:t._onHide}},[t._v(t._s(t.buttonText||"确定"))])])])],1)},staticRenderFns:[]};e.a=n},M93x:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=i("xzd6"),a=i.n(n);for(var o in n)"default"!==o&&function(t){i.d(e,t,function(){return n[t]})}(o);var s=i("/8tN");var u=function(t){i("7uHu")},r=i("VU/8")(a.a,s.a,!1,u,null,null);e.default=r.exports},NHnr:function(t,e,i){"use strict";var n=v(i("7+uW")),a=v(i("M93x")),o=v(i("NYxO")),s=v(i("Peep")),u=v(i("3BeM")),r=v(i("Y+qm")),l=v(i("v5o6")),d=v(i("YaEn")),c=v(i("P8HK")),f=(v(i("1cb1")),v(i("bbc5")),v(i("IcnI"))),h=function(t){if(t&&t.__esModule)return t;var e={};if(null!=t)for(var i in t)Object.prototype.hasOwnProperty.call(t,i)&&(e[i]=t[i]);return e.default=t,e}(i("HR9g"));function v(t){return t&&t.__esModule?t:{default:t}}if(n.default.use(o.default),n.default.use(s.default),n.default.use(u.default),n.default.use(r.default),new n.default({el:"#app",router:d.default,store:f.default,template:"<App/>",components:{App:a.default}}),l.default.attach(document.body),2==h.PRODUCTION_ENV&&i("hKoQ").polyfill(),0==h.PRODUCTION_ENV){var p=0;h.isIOS&&(p=50),setTimeout(function(){c.default.method("getPage",function(t){t=JSON.parse(t),d.default.push({path:"/activity/detail",query:{id:t.id}}),f.default.commit("USER_TOKEN",{token:t.token})})},p)}},NxOS:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n,a=i("bOdI"),o=(n=a)&&n.__esModule?n:{default:n},s=function(t){if(t&&t.__esModule)return t;var e={};if(null!=t)for(var i in t)Object.prototype.hasOwnProperty.call(t,i)&&(e[i]=t[i]);return e.default=t,e}(i("5reh"));var u=(0,o.default)({},s.HTTP_LOADING,function(t,e){t.httpLoading=e.httpLoading});e.default={state:{httpLoading:!1},getters:{},mutations:u,actions:{}}},P8HK:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n,a=function(t){if(t&&t.__esModule)return t;var e={};if(null!=t)for(var i in t)Object.prototype.hasOwnProperty.call(t,i)&&(e[i]=t[i]);return e.default=t,e}(i("HR9g")),o=i("ITB2"),s=(n=o)&&n.__esModule?n:{default:n};if(a.isIOS){!function(t){if(window.WebViewJavascriptBridge)return t(WebViewJavascriptBridge);if(window.WVJBCallbacks)return window.WVJBCallbacks.push(t);window.WVJBCallbacks=[t];var e=document.createElement("iframe");e.style.display="none",e.src="wvjbscheme://__BRIDGE_LOADED__",document.documentElement.appendChild(e),setTimeout(function(){document.documentElement.removeChild(e)},0)}(function(t){for(var e=0;e<s.default.length;e++){var i=s.default[e];t.registerHandler(i.name,i.methodIOS)}})}e.default={method:function(t,e,i){if(a.isAndroid)i&&i.length>0?(1==i.length&&e(window.native[t](fileds[0])),2==i.length&&e(window.native[t](fileds[0],fileds[1])),3==i.length&&e(window.native[t](fileds[0],fileds[1],fileds[2])),4==i.length&&e(window.native[t](fileds[0],fileds[1],fileds[2],fileds[3]))):e(window.native[t]());else if(a.isIOS)try{WebViewJavascriptBridge.callHandler(t,i,e)}catch(t){alert(t)}}}},RnvR:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});e.DEBUG="DEBUG",e.INFO="INFO",e.WARN="WARN",e.ERROR="ERROR"},Vz5h:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n,a=i("bOdI"),o=(n=a)&&n.__esModule?n:{default:n},s=function(t){if(t&&t.__esModule)return t;var e={};if(null!=t)for(var i in t)Object.prototype.hasOwnProperty.call(t,i)&&(e[i]=t[i]);return e.default=t,e}(i("5reh"));var u=(0,o.default)({},s.USER_TOKEN,function(t,e){t.token=e.token});e.default={state:{token:null},getters:{},mutations:u,actions:{}}},YaEn:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=s(i("7+uW")),a=s(i("/ocq")),o=s(i("IcnI"));s(i("+H76"));function s(t){return t&&t.__esModule?t:{default:t}}n.default.use(a.default),window.vueHistory=0;var u=new a.default({routes:[{path:"*",component:function(t){new Promise(function(t){t()}).then(function(){var e=[i("+H76")];t.apply(null,e)}.bind(this)).catch(i.oe)}},{path:"/activity/detail",name:"activity",component:function(t){Promise.all([i.e(0),i.e(2)]).then(function(){var e=[i("oFIl")];t.apply(null,e)}.bind(this)).catch(i.oe)}},{path:"/activity/offline",name:"offline_activity",component:function(t){Promise.all([i.e(0),i.e(4)]).then(function(){var e=[i("nXLv")];t.apply(null,e)}.bind(this)).catch(i.oe)}},{path:"/activity/video",name:"video_activity",component:function(t){Promise.all([i.e(0),i.e(1)]).then(function(){var e=[i("vnRn")];t.apply(null,e)}.bind(this)).catch(i.oe)}},{path:"/activity/questionnaire",name:"questionnaire_activity",component:function(t){Promise.all([i.e(0),i.e(3)]).then(function(){var e=[i("JWrm")];t.apply(null,e)}.bind(this)).catch(i.oe)}},{path:"/activity/joinmembers",name:"join_activity_member_list",component:function(t){Promise.all([i.e(0),i.e(5)]).then(function(){var e=[i("J5kI")];t.apply(null,e)}.bind(this)).catch(i.oe)}},{path:"/activity/commitoffline",name:"commit_offline_activity",component:function(t){Promise.all([i.e(0),i.e(8)]).then(function(){var e=[i("9ACB")];t.apply(null,e)}.bind(this)).catch(i.oe)}},{path:"/",name:"HelloWorld",component:function(t){Promise.all([i.e(0),i.e(6)]).then(function(){var e=[i("gORT")];t.apply(null,e)}.bind(this)).catch(i.oe)}},{path:"/map/gaode",name:"gaodeMap",component:function(t){i.e(7).then(function(){var e=[i("b9bY")];t.apply(null,e)}.bind(this)).catch(i.oe)}}]}),r=window.sessionStorage;r.clear();var l=1*r.getItem("count")||0;r.setItem("/",0),u.beforeEach(function(t,e,i){var n=r.getItem(t.path),a=r.getItem(e.path);n?!a||parseInt(n,10)>parseInt(a,10)||"0"===n&&"0"===a?o.default.commit("UPDATE_DIRECTION",{direction:"forward"}):o.default.commit("UPDATE_DIRECTION",{direction:"reverse"}):(++l,r.setItem("count",l),"/"!==t.path&&r.setItem(t.path,l),o.default.commit("UPDATE_DIRECTION",{direction:"forward"})),i()}),u.afterEach(function(t,e,i){o.default.commit("HISTORY",{num:1})}),e.default=u},Z4bK:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n,a,o=i("bOdI"),s=(n=o)&&n.__esModule?n:{default:n},u=function(t){if(t&&t.__esModule)return t;var e={};if(null!=t)for(var i in t)Object.prototype.hasOwnProperty.call(t,i)&&(e[i]=t[i]);return e.default=t,e}(i("5reh"));var r=(a={},(0,s.default)(a,u.UPDATE_DIRECTION,function(t,e){t.direction=e.direction}),(0,s.default)(a,u.HISTORY,function(t,e){t.history=t.history+e.num}),a);e.default={state:{direction:"forward",history:0},getters:{},mutations:r,actions:{}}},Z5JB:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={name:"loading",model:{prop:"show",event:"change"},props:{show:Boolean,text:String,position:String,transition:{type:String,default:"vux-mask"}},watch:{show:function(t){this.$emit("update:show",t)}}}},bbc5:function(t,e,i){"use strict";var n,a=i("7+uW"),o=(n=a)&&n.__esModule?n:{default:n};o.default.filter("keepTwoNumber",function(t){return(t=Number(t)).toFixed(2)}),o.default.filter("formatDateToCN",function(t){return t}),o.default.filter("activityFlowName",function(t){var e="";switch(t){case 0:e="待交付";break;case 1:e="待审核";break;case 2:e="待领钱";break;case 3:e="未通过";break;case 4:e="已完成";break;case 5:e="已关闭"}return e})},mzja:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=i("2RPQ"),a=i.n(n);for(var o in n)"default"!==o&&function(t){i.d(e,t,function(){return n[t]})}(o);var s=i("LaXf");var u=function(t){i("GVd+")},r=i("VU/8")(a.a,s.a,!1,u,null,null);e.default=r.exports},nQpb:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n,a=i("xNvf"),o=(n=a)&&n.__esModule?n:{default:n};e.default={name:"toast",mixins:[o.default],props:{value:Boolean,time:{type:Number,default:2e3},type:{type:String,default:"success"},transition:String,width:{type:String,default:"7.6em"},isShowMask:{type:Boolean,default:!1},text:String,position:String},data:function(){return{show:!1}},created:function(){this.value&&(this.show=!0)},computed:{currentTransition:function(){return this.transition?this.transition:"top"===this.position?"vux-slide-from-top":"bottom"===this.position?"vux-slide-from-bottom":"vux-fade"},toastClass:function(){return{"weui-toast_forbidden":"warn"===this.type,"weui-toast_cancel":"cancel"===this.type,"weui-toast_success":"success"===this.type,"weui-toast_text":"text"===this.type,"vux-toast-top":"top"===this.position,"vux-toast-bottom":"bottom"===this.position,"vux-toast-middle":"middle"===this.position}},style:function(){if("text"===this.type&&"auto"===this.width)return{padding:"10px"}}},watch:{show:function(t){var e=this;t&&(this.$emit("input",!0),this.$emit("on-show"),this.fixSafariOverflowScrolling("auto"),clearTimeout(this.timeout),this.timeout=setTimeout(function(){e.show=!1,e.$emit("input",!1),e.$emit("on-hide"),e.fixSafariOverflowScrolling("touch")},this.time))},value:function(t){this.show=t}}}},pEJw:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n,a,o=i("bOdI"),s=(n=o)&&n.__esModule?n:{default:n},u=function(t){if(t&&t.__esModule)return t;var e={};if(null!=t)for(var i in t)Object.prototype.hasOwnProperty.call(t,i)&&(e[i]=t[i]);return e.default=t,e}(i("5reh"));var r=(a={},(0,s.default)(a,u.UPDATE_ACTIVITY_STATUS,function(t,e){t.activityStatus=e.activityStatus}),(0,s.default)(a,u.UPDATE_JOIN,function(t,e){t.join=e.join}),a);e.default={state:{activityStatus:null,join:0},getters:{},mutations:r,actions:{}}},rLAy:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=i("nQpb"),a=i.n(n);for(var o in n)"default"!==o&&function(t){i.d(e,t,function(){return n[t]})}(o);var s=i("97Uv");var u=function(t){i("8vzP")},r=i("VU/8")(a.a,s.a,!1,u,null,null);e.default=r.exports},xzd6:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=s(i("Dd8w")),a=i("NYxO"),o=s(i("7+uW"));function s(t){return t&&t.__esModule?t:{default:t}}e.default={name:"app",computed:(0,n.default)({},(0,a.mapState)({direction:function(t){return t.routerModule.direction},loading:function(t){return t.axiosModule.httpLoading}})),watch:{loading:function(t,e){t?o.default.$vux.loading.show({text:"Loading"}):o.default.$vux.loading.hide()}}}},z0nY:function(t,e,i){"use strict";var n={render:function(){var t=this.$createElement,e=this._self._c||t;return e("transition",{attrs:{name:this.transition}},[e("div",{directives:[{name:"show",rawName:"v-show",value:this.show,expression:"show"}],staticClass:"weui-loading_toast vux-loading"},[e("div",{staticClass:"weui-mask_transparent"}),this._v(" "),e("div",{staticClass:"weui-toast",style:{position:this.position}},[e("i",{staticClass:"weui-loading weui-icon_toast"}),this._v(" "),e("p",{staticClass:"weui-toast__content"},[this._v(this._s(this.text||"加载中")),this._t("default")],2)])])])},staticRenderFns:[]};e.a=n}},["NHnr"]);
//# sourceMappingURL=app.80b4ca275334ff07bd44.js.map