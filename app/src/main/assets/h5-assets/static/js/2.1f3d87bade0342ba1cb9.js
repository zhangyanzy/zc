webpackJsonp([2],{"63CM":function(t,i,e){"use strict";var a=e("BEQ0");function n(t){return void 0===t?document.body:"string"==typeof t&&0===t.indexOf("?")?document.body:("string"==typeof t&&t.indexOf("?")>0&&(t=t.split("?")[0]),"body"===t||!0===t?document.body:t instanceof window.Node?t:document.querySelector(t))}var s={inserted:function(t,i,e){var a=i.value;t.className=t.className?t.className+" v-transfer-dom":"v-transfer-dom";var s=t.parentNode,o=document.createComment(""),u=!1;!1!==a&&(s.replaceChild(o,t),n(a).appendChild(t),u=!0),t.__transferDomData||(t.__transferDomData={parentNode:s,home:o,target:n(a),hasMovedOut:u})},componentUpdated:function(t,i){var e=i.value;if(function(t){if(!t)return!1;if("string"==typeof t&&t.indexOf("?")>0)try{return JSON.parse(t.split("?")[1]).autoUpdate||!1}catch(t){return!1}return!1}(e)){var s=t.__transferDomData,o=s.parentNode,u=s.home,r=s.hasMovedOut;!r&&e?(o.replaceChild(u,t),n(e).appendChild(t),t.__transferDomData=a({},t.__transferDomData,{hasMovedOut:!0,target:n(e)})):r&&!1===e?(o.replaceChild(t,u),t.__transferDomData=a({},t.__transferDomData,{hasMovedOut:!1,target:n(e)})):e&&n(e).appendChild(t)}},unbind:function(t,i){t.className=t.className.replace("v-transfer-dom",""),t.__transferDomData&&!0===t.__transferDomData.hasMovedOut&&t.__transferDomData.parentNode&&t.__transferDomData.parentNode.appendChild(t),t.__transferDomData=null}};t.exports=s},"9ACB":function(t,i,e){"use strict";Object.defineProperty(i,"__esModule",{value:!0});var a=e("mu+s"),n=e.n(a);for(var s in a)"default"!==s&&function(t){e.d(i,t,function(){return a[t]})}(s);var o=e("h13e"),u=e("VU/8")(n.a,o.a,!1,null,null,null);i.default=u.exports},JXtA:function(t,i){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEwAAABMCAYAAADHl1ErAAAFVUlEQVR4Xu2cTWgkRRSAv5eExENihFWIksi6Bz2JvydZFFRQvPiHguheFEFFF1xWFPe8sKIouKKC6EVFUNT1IgoqKOLJv8WTHtzFBA2oYEwOTkjy5G1qdmZ6uidV3dWZjlZDLuHVq1ffVFfX+6kS0hNEQIKkkzBDAaaqI8BlwCXAhcBFwAXAmcCU+7OfZ9n9/Q2cAH4EfgKOA9+JyMZ2/4bbBkxVzwNuB64DrgHOqjjYv4DPgU+Bd0Xk14r6vJrXCkxVz3CQ9gHXA6NeVoULrQOfAK87eP+Eq/BrUQswVZ0EHgAOAOf6mRJN6jfgWeBlEVmJptUpigpMVceAR4BDwK7Yxgbq+xM4DBwVkbXAtoXi0YCp6l7gReDiWMZF0vMD8JCIfBlDX2VgqjoOPAM8DMP56nqAUOAF4KCIrHrI1zPDVHUP8DZwRRUjtrHtN8CdIvJz2T5LzzBVvRZ4D5gu2/mQ2i0Bt4nIZ2X6LwVMVe9wn/CJMp02oE0L2Cci74TaEgxMVe8HXqpxTxU6hrLytnd7UEReCVEQBMzNrLf+A7DajAzaXSEzzRuYW7M+BHbqa1g0kez1vMl3TfMC5r6G3+7ABd73bbMPweU+X88tgbl91lc7aOvgCykrZ1uOq7bap/kAe965O2UN2UntzI3aP8jggcCcu/NFg3fwsX8M8wiuHuRGFQJzjrStW03zDWNDyuoz39PWs1yHfRCwR12YpG4Dm6j/gIg8l2dYLjAXzzrZgBDNsGBaaGh3XjytCNhB4OlhWduQfh8TEYvC9Dx9wFxY2bz5KJHSjY0NlpeXmZycZHS0rgj15pjW19dZWVlhamqKkRHLs1R6LHK7R0R6wt15wO4G3qjUVVfjpaUlFhcXmZiYYHZ2lrExC8rGf9bW1lhYWKDVajEzM8P0dJQgyj0i8ma3tXnAPgJuiDUk+9Xn5+dPDaQuaN2wrI+5ublYs/ljEbmxEJhLhf0S27nODijmTKtTt73lwPndKbyeGaaqlsCwnX30p46B1aEzZ+D7ReRo+/9ZYMeAm6PTcgpjDjCmri3G+4GI3NIHzKXvbf9RNSM9sP8YA42hI2BSWIZ9V7ss4fQMU1VLZHwdoKi0aJUBV2lb2mC4UkQsmtFJi6nqvcCrFZQGNS0z8DJtgowqFr5PRF7LAjsCPB6pAy81IQBCZL06DxN6SkSeyAJ7Hzi9uIXpKy/tA8JHprwFXi2PicitWWDfu3otLw0xhQYBaQAsG+pxEbk0C8wK1nbHBBGiKw+MtW+7O3V5CZ42nhQRK/jrWfR/B872VFCLWDe08XEr2YDV1dXaXKqAQfwhIudkgZlXPvQUmkEz39NA2WPgzDesy2n3hNYSESsO7JlhCVgxvVxg6ZUsBpb7SlrQ8NTCNoyn4Yv+CRGx0q6eVzJtK4pnSu62Im1ci4HlblyTa1QMLNc1Ss53MbBc5zuFd4qB5YZ3LC+VAoj90PIDiCanqilE3Q8sP0TtgKUkSD+wgUkQO3GW0mwdaIPTbG6WpURuB9jgRK4DlkoFOsC8SgUsjJGKUcCvGMXNslTuBH7lTg6YHRBNBXU5B1RTyWb+7j6sZNPNMivkSkXBGaCp7LwXSPmy87YeVU0HG7qg+pwEsXxXOjrjoG0JzK1nFs9Oh7NCjsSk43+bU8xrhnWtZ3Z0OR0wDUmrpSPMIbScbDokXw5auoYhlFu66COU2GYeIF0lU4KbgUuXFYWCc6d47cz0kw04a9ns67C64aYL10KnWmf7ka70K8nO1rh0aWQFeEXXktqpUAuP29Wk9ti1pHaPod1c8v+6lrQs3Ka1C3K+m2b8MOz5F8a7knppQkynAAAAAElFTkSuQmCC"},RNVz:function(t,i,e){"use strict";var a={render:function(){var t=this,i=t.$createElement,a=t._self._c||i;return a("div",{staticStyle:{"text-align":"left",position:"relative",left:"0",top:"0"}},[a("div",{staticStyle:{position:"relative"}},[a("span",{staticClass:"font-18 f-white",staticStyle:{position:"absolute",left:"20px",top:"10px"}},[t._v(t._s(t.info.name))]),t._v(" "),a("span",{staticClass:"font-12 f-white",staticStyle:{position:"absolute",left:"20px",top:"37px",display:"-webkit-box","-webkit-box-orient":"vertical","-webkit-line-clamp":"2",overflow:"hidden"}},[t._v(t._s(t.info.content))]),t._v(" "),a("img",{staticStyle:{width:"100%","margin-bottom":"-8px"},attrs:{src:e("Zxlw")}})]),t._v(" "),a("div",{staticStyle:{height:"60vh",background:"#FFFFFF"}},[a("scroller",{staticClass:"height-60vh",attrs:{"lock-x":"","scrollbar-y":!1}},[a("div",{staticStyle:{background:"#FFFFFF",padding:"10px 20px"}},t._l(t.question,function(i,e){return a("div",[a("p",{staticClass:"font-14 f-black mg-b10"},[0==i.questionType?a("span",{staticClass:"f-base"},[t._v("单选")]):t._e(),t._v(" "),0!=i.questionType?a("span",{staticClass:"f-base"},[t._v("多选")]):t._e(),t._v("\n                        "+t._s(i.name))]),t._v(" "),t._l(i.answerList,function(n,s){return 0==i.questionType?a("div",{staticClass:"font-14 f-dark mg-b10",staticStyle:{"line-height":"20px"},on:{click:function(i){t.checkAnswer(e,s)}}},[s!=i.checking?a("div",{staticStyle:{display:"inline-block",width:"14px",height:"14px",border:"1px solid #979797","border-radius":"50%","vertical-align":"sub"}}):t._e(),t._v(" "),s==i.checking?a("div",{staticStyle:{display:"inline-block",width:"14px",height:"14px",border:"1px solid #FBC873",background:"#FFD795","border-radius":"50%","vertical-align":"sub"}}):t._e(),t._v(" "),a("span",[t._v(t._s(n.name))])]):t._e()}),t._v(" "),t._l(i.answerList,function(n,s){return 0!=i.questionType?a("div",{staticClass:"font-14 f-dark mg-b10",staticStyle:{"line-height":"20px"},on:{click:function(i){t.checkMultiselect(e,s+"")}}},[-1==i.checking.indexOf(s+"")?a("div",{staticStyle:{display:"inline-block",width:"14px",height:"14px",border:"1px solid #979797","border-radius":"4px","vertical-align":"sub"}}):t._e(),t._v(" "),i.checking&&-1!=i.checking.indexOf(s+"")?a("div",{staticStyle:{display:"inline-block",width:"14px",height:"14px",border:"1px solid #FBC873",background:"#FFD795","border-radius":"4px","vertical-align":"sub"}}):t._e(),t._v(" "),a("span",[t._v(t._s(n.name))])]):t._e()})],2)}))])],1),t._v(" "),a("div",{staticClass:"tc",staticStyle:{background:"#FFFFFF",padding:"10px"}},[a("a",{staticClass:"sure-btn",on:{click:t.commitQuestionActivity}},[t._v("提交")])]),t._v(" "),a("div",{staticStyle:{"text-align":"center"}},[1!==t.questionRequest?a("img",{staticStyle:{width:"45px","margin-top":"15px"},attrs:{src:e("JXtA")},on:{click:t.cancelQuestion}}):t._e()])])},staticRenderFns:[]};i.a=a},YEmu:function(t,i){t.exports="data:image/png;base64,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"},Zxlw:function(t,i){t.exports="data:image/png;base64,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"},h13e:function(t,i,e){"use strict";var a={render:function(){var t=this,i=t.$createElement,a=t._self._c||i;return a("div",{staticStyle:{width:"100%",position:"relative",height:"100vh",overflow:"hidden"}},[a("div",{staticClass:"mg-t15 mg-b10",staticStyle:{padding:"0 8px"}},[t._v("\n        参考图片\n    ")]),t._v(" "),a("div",{staticStyle:{padding:"0 8px"}},[a("img",{directives:[{name:"loading-img",rawName:"v-loading-img"}],staticStyle:{width:"23vw","border-radius":"8px"},attrs:{src:t.img}})]),t._v(" "),a("div",{staticClass:"mg-b10 mg-t15",staticStyle:{padding:"0 8px"}},[t._v("\n        上传活动图片\n    ")]),t._v(" "),a("div",{staticStyle:{padding:"0 8px"}},[t.picture.picture?t._e():a("div",{staticClass:"tc",staticStyle:{width:"23vw",height:"23vw",border:"1px solid black","border-radius":"8px"},on:{click:t.takePhoto}},[a("img",{staticStyle:{width:"30px",height:"30px","margin-top":"27px"},attrs:{src:e("YEmu")}})]),t._v(" "),t.picture.picture?a("img",{directives:[{name:"loading-img",rawName:"v-loading-img"}],staticStyle:{width:"23vw",height:"23vw",border:"1px solid black","border-radius":"8px"},attrs:{src:t.picture.picture}}):t._e(),t._v(" "),t.picture.picture?a("a",{staticClass:"font-12 mg-t5",staticStyle:{display:"block",padding:"3px 0vh","text-align":"center",width:"23vw","border-radius":"12px",color:"#979797",background:"#FFFFFF",border:"1px solid #979797"},on:{click:t.deleteImg}},[t._v("删除")]):t._e()]),t._v(" "),t.$route.query.childId?t._e():a("div",{staticClass:"mg-t15",staticStyle:{width:"100%","text-align":"center",position:"absolute",bottom:"20px"}},[a("a",{staticClass:"sure-btn mg-b15",staticStyle:{width:"90%"},on:{click:t.commitActivity}},[t._v("提交活动")])]),t._v(" "),t.$route.query.childId?a("div",{staticClass:"mg-t15",staticStyle:{width:"100%","text-align":"center",position:"absolute",bottom:"20px"}},[a("a",{staticClass:"sure-btn mg-b15",staticStyle:{width:"90%"},on:{click:t.commitChildActivity}},[t._v("提交活动")])]):t._e(),t._v(" "),a("div",{directives:[{name:"transfer-dom",rawName:"v-transfer-dom"}]},[a("x-dialog",{attrs:{"hide-on-blur":"","dialog-style":{width:"93%","max-width":"100%",height:"100%","background-color":"transparent"}},model:{value:t.dialogShow,callback:function(i){t.dialogShow=i},expression:"dialogShow"}},[t.result.kid?a("dialog-questionnaire",{attrs:{result:t.result,questionRequest:t.questionRequest,callback:t.$route.query.childId?t.commitChildActivity2:t.commitActivity2},on:{listenToChildEvent:t.closeDialog}}):t._e()],1)],1)])},staticRenderFns:[]};i.a=a},"mu+s":function(t,i,e){"use strict";Object.defineProperty(i,"__esModule",{value:!0});var a=A(e("Dd8w")),n=A(e("eOoE")),s=A(e("IcnI")),o=e("NYxO"),u=A(e("7+uW")),r=A(e("P8HK")),c=A(e("/kga")),d=A(e("63CM")),l=A(e("vQXk"));function A(t){return t&&t.__esModule?t:{default:t}}i.default={name:"commit_offline_activity",data:function(){return{dialogShow:!1,result:{},activityCode:null,img:"",questionRequest:""}},computed:(0,a.default)({},(0,o.mapState)({picture:function(t){return t.activityModule.picture?{picture:t.activityModule.picture.picture,code:t.activityModule.picture.code}:{}},uuid:function(t){return t.userModule.uuid},questionStatus:function(t){return t.activityModule.questionStatus}})),methods:{takePhoto:function(){var t=this.result.activityCode?this.result.activityCode:"-1";0===this.result.ifCheck&&(t="-1");try{r.default.method("takePhoto",function(t){console.log("调用成功")},t)}catch(t){alert(t)}},deleteImg:function(){s.default.commit("OFFLINE_IMG",{picture:null,code:"-1"})},commitChildActivity:function(){this.picture.picture?1==this.$route.query.lastOne&&0===this.$route.query.questionStatus?this.dialogShow=!0:this.commitChildActivity2(0):u.default.$vux.toast.show({text:"请上传活动图片",type:"cancel"})},commitChildActivity2:function(t){var i=this,e=this,a={uicode:e.uuid,seriesId:this.$route.query.childId,deliverImage1:this.picture.picture,questionStatus:t,lastOne:this.$route.query.lastOne};-1!=this.picture.code&&"-1"!=this.picture.code&&(a.activityCode=this.picture.code),n.default.commitChildActivity(this.$route.query.id,a,function(t){u.default.$vux.toast.show({text:"提交成功"}),s.default.commit("OFFLINE_IMG",{picture:null,code:"-1"}),e.$router.go(-1),1==i.$route.query.lastOne?(s.default.commit("UPDATE_STATUS"),s.default.commit("UPDATE_ACTIVITY_STATUS",{activityStatus:1})):s.default.commit("UPDATE_STATUS")})},commitActivity:function(){var t=this;this.picture.picture?n.default.activityDetail(t.$route.query.id,function(i){0==i.data.questionStatus?t.dialogShow=!0:t.commitActivity2()}):u.default.$vux.toast.show({text:"请上传活动图片",type:"cancel"})},commitActivity2:function(t){var i=this,e={uicode:i.uuid,activityForm:0,deliverImage1:this.picture.picture,questionStatus:t};-1!=this.picture.code&&"-1"!=this.picture.code&&(e.activityCode=this.picture.code),n.default.submitActivity(this.$route.query.id,e,function(t){u.default.$vux.toast.show({text:"提交成功"}),s.default.commit("OFFLINE_IMG",{picture:""}),s.default.commit("OFFLINE_CODE",""),i.$router.go(-1),s.default.commit("UPDATE_ACTIVITY_STATUS",{activityStatus:1})})},closeDialog:function(){this.dialogShow=!1}},directives:{TransferDom:d.default},components:{XDialog:c.default,dialogQuestionnaire:l.default},created:function(){var t=this;this.$route.query.childId?n.default.activityDetail(t.$route.query.id,function(i){t.questionRequest=i.data.questionRequest,n.default.getChildActivityDetail(t.$route.query.id,t.$route.query.childId,function(e){t.result=e.data,1===e.data.ifCheck&&u.default.$vux.toast.show({text:"上传图片前需要先进行识别二维码操作"}),t.$set(t.result,"questionId",t.$route.query.questionId),n.default.getActivityChildImage(t.$route.query.childId,i.data.activityType,function(i){t.img=i.data})})}):n.default.activityDetail(this.$route.query.id,function(i){t.result=i.data,1===i.data.ifCheck&&u.default.$vux.toast.show({text:"上传图片前需要先进行识别二维码操作"}),t.questionRequest=i.data.questionRequest,n.default.getActivityChildImage(t.$route.query.id,i.data.activityType,function(i){t.img=i.data})})}}},sk3I:function(t,i,e){"use strict";Object.defineProperty(i,"__esModule",{value:!0});var a=o(e("S8Wg")),n=o(e("eOoE")),s=o(e("7+uW"));function o(t){return t&&t.__esModule?t:{default:t}}i.default={name:"activity-questionnaire",props:["result","callback","questionRequest"],data:function(){return{question:null,info:{}}},methods:{checkAnswer:function(t,i){this.question[t].checking=i},checkMultiselect:function(t,i){-1==this.question[t].checking.indexOf(i)?this.question[t].checking+=","+i:this.question[t].checking=this.question[t].checking.replace(new RegExp(","+i,"g"),"")},cancelQuestion:function(){var t=this;n.default.submitQuestion(this.$route.query.id,[],function(i){t.$emit("listenToChildEvent"),t.callback(2)})},commitQuestionActivity:function(){for(var t=this,i=[],e=0;e<this.question.length;e++){var a=this.question[e];if("t"==a.checking)return void s.default.$vux.toast.show({text:"请填写完所有问题再提交",type:"cancel"});var o=a.checking;if(0==a.questionType){var u={questionId:a.kid,questionName:a.name,questionType:a.questionType,answerId:a.answerList[o].kid,answerName:a.answerList[o].name,answerType:a.answerList[o].type,questionnaireName:this.info.name,questionnaireId:this.info.kid};i.push(u)}else for(var r=o.split(","),c=1;c<r.length;c++){var d=r[c];u={questionId:a.kid,questionName:a.name,questionType:a.questionType,answerId:a.answerList[d].kid,answerName:a.answerList[d].name,answerType:a.answerList[d].type,questionnaireName:this.info.name,questionnaireId:this.info.kid};i.push(u)}}n.default.submitQuestion(this.$route.query.id,i,function(i){s.default.$vux.toast.show({text:"提交成功"}),t.$emit("listenToChildEvent"),t.callback(1)})}},components:{Scroller:a.default},created:function(){var t=this;this.result.questionId&&n.default.getQuestionList(this.result.questionId,function(i){for(var e=0;e<i.data.questionList.length;e++)i.data.questionList[e].checking="t";t.question=i.data.questionList,t.info={name:i.data.name,content:i.data.content,kid:i.data.kid}})}}},vQXk:function(t,i,e){"use strict";Object.defineProperty(i,"__esModule",{value:!0});var a=e("sk3I"),n=e.n(a);for(var s in a)"default"!==s&&function(t){e.d(i,t,function(){return a[t]})}(s);var o=e("RNVz"),u=e("VU/8")(n.a,o.a,!1,null,null,null);i.default=u.exports}});
//# sourceMappingURL=2.1f3d87bade0342ba1cb9.js.map