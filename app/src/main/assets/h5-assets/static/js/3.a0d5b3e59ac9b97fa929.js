webpackJsonp([3],{"1YPC":function(A,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=h(e("Dd8w")),a=h(e("eOoE")),s=h(e("GQaK")),E=h(e("7+uW")),n=h(e("IcnI")),B=e("NYxO"),g=function(A){if(A&&A.__esModule)return A;var t={};if(null!=A)for(var e in A)Object.prototype.hasOwnProperty.call(A,e)&&(t[e]=A[e]);return t.default=A,t}(e("HR9g")),o=h(e("/kga")),Q=h(e("gYQk")),C=h(e("qolJ")),l=h(e("AxNP")),c=h(e("CE8n")),r=h(e("OtXZ")),I=h(e("DnK4")),u=h(e("owXI")),d=h(e("fThv")),v=h(e("3Ibj"));h(e("P8HK"));function h(A){return A&&A.__esModule?A:{default:A}}t.default={name:"new-infomation",data:function(){return{result:{},finishUser:[],activityId:this.$route.query.id,awakenApp:0!=g.PRODUCTION_ENV,question:null,info:{},videoMark:d.default,isShowAll:!1,isChoselabelShow:!1,submitParam:null,userType:this.$route.query.userType}},mounted:function(){var A=this;this.$nextTick(function(){A.scroll=new s.default(A.$refs.wrapper,{click:!0,probeType:3}),A.scroll.on("scroll",function(t){A.scrollY=t.y})})},computed:(0,i.default)({},(0,B.mapState)({getMoney:function(A){return A.activityModule.getMoney}})),components:{BScroll:s.default,awakenApp:Q.default,animateGetMoney:C.default,activityMemberHeader:c.default,dialogLabelchose:v.default,XDialog:o.default,activityTitle:l.default,activityBottomBtn:I.default,activityJoin:u.default,activityTags:r.default},created:function(){this.$nextTick(function(){var A=this;a.default.activityDetail(this.activityId,function(t){switch(A.result=t.data,A.result.activityForm){case 0:document.title="线下活动";break;case 1:document.title="视频活动";break;case 2:document.title="问卷活动";break;case 3:document.title="资讯活动";break;case 4:document.title="竞猜活动";break;default:document.title=_this.result.name}n.default.commit("UPDATE_ACTIVITY_STATUS",{activityStatus:t.data.cancelType&&2===t.data.cancelType?5:t.data.activityStatus}),n.default.commit("UPDATE_JOIN",{join:t.data.join}),n.default.commit("QUESTION_STATUS",t.data.questionStatus),n.default.commit("HTTP_LOADING",{httpLoading:!1}),A.result.contentRich=A.result.contentRich.replace(new RegExp('embed class="video"',"g"),"video  x-webkit-airplay='true' webkit-playsinline playsinline controls poster='"+A.videoMark+"'"),"[]"===A.result.activityLabel?A.result.activityLabel="":A.result.activityLabel=A.result.activityLabel.substring(1,A.result.activityLabel.length-1).replace(new RegExp("},{","g"),"}p{").split("p"),t.data.questionId&&a.default.getQuestionList(t.data.questionId,function(t){for(var e=0;e<t.data.questionList.length;e++)t.data.questionList[e].checking="t";A.question=t.data.questionList,A.info={name:t.data.name,content:t.data.content,kid:t.data.kid},n.default.commit("HTTP_LOADING",{httpLoading:!1})})}),1==this.userType&&a.default.getMoneyUserList({id:A.activityId,currentResult:0,pageSize:5},function(t){console.log("领取人员",t),A.finishUser=t.data,n.default.commit("HTTP_LOADING",{httpLoading:!1})})})},beforeUpdate:function(){this.$nextTick(function(){this.isShowAll=!0})},methods:{dialogBtn:function(A){if("cancel"==A)this.isChoselabelShow=!1;else{for(var t=[],e=0;e<A.length;e++)A[e].isChosed&&t.push(A[e].key);4!==this.result.activityForm&&this.submitParam.unshift({tagIds:t.join()}),this.$options.methods.commitQuestion.bind(this)(this.submitParam),this.isChoselabelShow=!1}},checkAnswer:function(A,t){this.question[A].checking=t},checkMultiselect:function(A,t){-1==this.question[A].checking.indexOf("n"+t+"n")?this.question[A].checking+=",n"+t+"n":this.question[A].checking=this.question[A].checking.replace(new RegExp(",n"+t+"n","g"),"")},commitQuestion:function(A){var t=this;4!==this.result.activityForm?a.default.submitInfoQuestion(this.$route.query.id,A,function(A){t.result.activityStatus=4,t.result.join=!0,A.code||(t.animateGetMoney(),t.result.informationQuestionStatus=1,4===t.result.questionType?E.default.$vux.toast.show({text:"您已成功参与"}):E.default.$vux.toast.show({text:"回答正确"}))}):a.default.submitCompetitionInfoQuestion(this.$route.query.id,{userQuestionnaireRefDtos:A},function(A){t.animateGetMoney(),t.result.activityStatus=4,t.result.join=!0,A.code||E.default.$vux.toast.show({text:"提交成功"})})},sureQuestionBtn:function(){if((new Date).getTime()-new Date(this.result.endTime).getTime()>=0)E.default.$vux.toast.show({text:"活动已结束",type:"text"});else if(this.result.actualUser>=this.result.maxUser)E.default.$vux.toast.show({text:"参与人数已达上限",type:"text"});else if(4!==this.result.activityStatus){for(var A=[],t=0;t<this.question.length;t++){var e=this.question[t];if("t"==e.checking)return void E.default.$vux.toast.show({text:"请填写完所有问题再提交",type:"text"});var i=e.checking;if(0==e.questionType||3==e.questionType){var a={questionId:e.kid,questionName:e.name,questionType:e.questionType,answerId:e.answerList[i].kid,answerName:e.answerList[i].name,answerType:e.answerList[i].type,questionnaireName:this.info.name,questionnaireId:this.info.kid};A.push(a)}else for(var s=i.split(","),n=1;n<s.length;n++){var B=s[n].slice(1,s[n].length-1);a={questionId:e.kid,questionName:e.name,questionType:e.questionType,answerId:e.answerList[B].kid,answerName:e.answerList[B].name,answerType:e.answerList[B].type,questionnaireName:this.info.name,questionnaireId:this.info.kid};A.push(a)}}this.submitParam=A,this.result.activityLabel?this.isChoselabelShow=!this.isChoselabelShow:this.$options.methods.commitQuestion.bind(this)(this.submitParam)}else E.default.$vux.toast.show({text:"您已提交过此问卷",type:"text"})},animateGetMoney:function(){n.default.commit("GET_MONEY",!0),setTimeout(function(){n.default.commit("GET_MONEY",!1)},2e3)}}}},"3Ibj":function(A,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=e("uNUQ"),a=e.n(i);for(var s in i)"default"!==s&&function(A){e.d(t,A,function(){return i[A]})}(s);var E=e("ACJu");var n=function(A){e("mb9a")},B=e("VU/8")(a.a,E.a,!1,n,null,null);t.default=B.exports},"45hD":function(A,t){},ACJu:function(A,t,e){"use strict";var i={render:function(){var A=this,t=A.$createElement,i=A._self._c||t;return i("div",{staticClass:"dialogBox"},[i("div",{staticClass:"topContent"},[i("div",{staticClass:"title"},[A._v("请选择适合你的标签")]),A._v(" "),i("div",{ref:"labelWrapper",staticClass:"labelWrapper"},[i("ul",{ref:"content",staticClass:"content"},A._l(A.labelArr,function(t,a){return i("li",{key:a,class:t.isChosed?"chosedli":"",on:{click:function(t){A.choseLabel(a)}}},[i("span",{staticClass:"txt"},[A._v(A._s(t.name))]),A._v(" "),t.isChosed?i("img",{staticClass:"iconChosed",attrs:{src:e("amOg")}}):i("img",{staticClass:"iconChosed",attrs:{src:e("yFfi")}})])}))])]),A._v(" "),i("div",{staticClass:"line1px"}),A._v(" "),i("div",{staticClass:"btnBox"},[i("div",{staticClass:"btn cancelBtn",on:{click:A.cancel}},[A._v("取消")]),A._v(" "),i("div",{staticClass:"btn sureBtn",on:{click:A.submitChoseLabel}},[A._v("确定")])])])},staticRenderFns:[]};t.a=i},AxNP:function(A,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=e("fbdz"),a=e.n(i);for(var s in i)"default"!==s&&function(A){e.d(t,A,function(){return i[A]})}(s);var E=e("I/2Y");var n=function(A){e("zcnD")},B=e("VU/8")(a.a,E.a,!1,n,null,null);t.default=B.exports},DnK4:function(A,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=e("OYlW"),a=e.n(i);for(var s in i)"default"!==s&&function(A){e.d(t,A,function(){return i[A]})}(s);var E=e("pem1");var n=function(A){e("Tr+q")},B=e("VU/8")(a.a,E.a,!1,n,"data-v-a8619252",null);t.default=B.exports},"I/2Y":function(A,t,e){"use strict";var i={render:function(){var A=this.$createElement,t=this._self._c||A;return t("div",{staticClass:"activityTitle"},[t("div",{staticClass:"name"},[this._v(this._s(this.name))])])},staticRenderFns:[]};t.a=i},LlL7:function(A,t){},OYlW:function(A,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=c(e("Dd8w")),a=c(e("eOoE")),s=c(e("IcnI")),E=c(e("7+uW")),n=e("NYxO"),B=c(e("/kga")),g=c(e("63CM")),o=c(e("vQXk")),Q=c(e("qolJ")),C=function(A){if(A&&A.__esModule)return A;var t={};if(null!=A)for(var e in A)Object.prototype.hasOwnProperty.call(A,e)&&(t[e]=A[e]);return t.default=A,t}(e("HR9g")),l=c(e("P8HK"));function c(A){return A&&A.__esModule?A:{default:A}}t.default={name:"activityBottomBtn",props:["result"],data:function(){return{isH5:0!=C.PRODUCTION_ENV,dialogShow:!1,id:this.$route.query.id}},directives:{TransferDom:g.default},components:{XDialog:B.default,animateGetMoney:Q.default,dialogQuestionnaire:o.default},computed:(0,i.default)({},(0,n.mapState)({activityStatus:function(A){return A.activityModule.activityStatus},join:function(A){return A.activityModule.join},getMoneyBtn:function(A){return A.activityModule.getMoneyBtn}}),{isIphoneX:function(){return/iphone/gi.test(navigator.userAgent)&&812==screen.height&&375==screen.width}}),watch:{activityStatus:{handler:function(A,t){console.log("活动状态改变",A),this.result.activityStatus=A},deep:!0},join:{handler:function(A,t){this.result.join=A},deep:!1}},methods:{startPlay:function(){this.$emit("startPlayVideo"),this.joinActivity()},closeDialog:function(){1===this.result.questionRequest?this.dialogShow=!1:this.commitActivity2(2)},commitActivity:function(A){var t=this;a.default.activityDetail(t.$route.query.id,function(A){1!=A.data.activityForm||t.result.hasPlay?A.data.questionId?t.dialogShow=!0:t.commitActivity2():E.default.$vux.toast.show({text:"请先观看视频后提交",type:"warn"})})},commitActivity2:function(A){var t=this,e=this;a.default.submitActivity(this.$route.query.id,{questionStatus:A,activityForm:e.result.activityForm},function(A){e.dialogShow=!1,E.default.$vux.toast.show({text:"提交成功"});var i=1;2==t.result.activityType||1!=t.result.activityForm&&2!=t.result.activityForm&&4!=t.result.activityForm?2!=t.result.activityType||1!=t.result.activityForm&&2!=t.result.activityForm&&4!=t.result.activityForm||(i=2,s.default.commit("GET_MONEY_BTN",!0)):(i=4,e.animateGetMoney()),s.default.commit("UPDATE_ACTIVITY_STATUS",{activityStatus:i}),s.default.commit("UPDATE_STATUS")})},getMoney:function(){var A=this;a.default.getActivityMoney(this.$route.query.id,function(t){A.animateGetMoney(),s.default.commit("UPDATE_ACTIVITY_STATUS",{activityStatus:4})})},animateGetMoney:function(){s.default.commit("GET_MONEY",!0),setTimeout(function(){s.default.commit("GET_MONEY",!1)},2e3)},joinActivity:function(){var A={};2==this.result.activityType&&(this.$route.query.code?(A.coopRoleType=1,A.coopInviteCode=this.$route.query.code):A.coopRoleType=0),a.default.joinActivity(this.$route.query.id,A,function(A){if(console.log("报名成功"),s.default.commit("UPDATE_JOIN",{join:!0}),s.default.commit("UPDATE_ACTIVITY_STATUS",{activityStatus:0}),s.default.commit("UPDATE_VIDEOMASK",{isShowVideoMask:!1}),0==C.PRODUCTION_ENV){var t=0;C.isIOS&&(t=50),setTimeout(function(){l.default.method("getPage",function(A){if(0==(A=JSON.parse(A)).type){var t={path:"/activity/detail",query:{id:A.id}};A.code&&(t.query.code=A.code),this.$router.push(t),s.default.commit("DEVICE_UUID",{uuid:A.deviceUUID}),s.default.commit("USER_TOKEN",{token:A.token}),s.default.commit("USER_POSITION",{x:A.longitude,y:A.latitude})}else 1==A.type?router.push({path:"/agreement/user"}):2==A.type?router.push({path:"/agreement/privacy"}):alert("type为空")})},t)}})},followActivity:function(){var A=this;a.default.followActivity(this.result.kid,{follow:this.result.follow?0:1},function(){1==A.result.follow?A.result.follow=!1:A.result.follow=!0})},sureQuestionBtn:function(){this.$emit("sureQuestionBtn")}}}},OtXZ:function(A,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=e("w4PT"),a=e.n(i);for(var s in i)"default"!==s&&function(A){e.d(t,A,function(){return i[A]})}(s);var E=e("w+ft");var n=function(A){e("LlL7")},B=e("VU/8")(a.a,E.a,!1,n,"data-v-3eac12c9",null);t.default=B.exports},"Tr+q":function(A,t){},amOg:function(A,t){A.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAABt0lEQVRIS+2Uv2sTYRjHP98TtNpNXHVxEMykF7o4OJtELIIBHWoVOvhHiIhjcRcErS5ChiI0l72Di/R0iiDoYHEUt9gqcl9J2qa5S3LXn3TxxuN9ns/7Pt/P+4pD/nTI/fkPGJqwo8t1rHkgQMHcgY7IzfAh8Bj6o189EIAbpeNMTjzHzGSO9H3fADdKpzl5YhHpaqq5WAffklvlayR+hhDmgWpxtFN13QrPYyLMhUzNDwJNq7LyTo7CVczZzQUdgmRKlY+fiiBula+Q+C1wJrPzz/x1TTc+fOn+l5vhN+DcwKI2ZkrX41/jII7Kt8EvMBPp5lqms3ZT9fbPrf/dEc2Q+FVmFwuqxvdGAUaYsrFMvKazPqd6+89gXS9kR+FLzGym4axqcR+cY4oJeKRK/GTUhjYAS+EpxHugNLCon0e+Kbqv6sqbcePsa+rWpYskQRcymcrjmO+QqJFnSp4QqXvgZngXWEgV2AlSMGSKqKoSfy2ybeiijclju4+9zNrvlCk7PkFOHrmm7ArQgwznkWvKrgE9yFYevTcl35Q9ATbvx1Okxe6bUhRmoaZ7bVBUt+/n+sgB/wBIGKgMnum7FgAAAABJRU5ErkJggg=="},bXkW:function(A,t){A.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAFb0lEQVRYR+2YeWxUVRSHv9+baSkENIq4kGgAMXFN1JlhKW4kqO28MUaUGJFEXOOGUdFijAbUmKBoXKKixqCmcYn7MjMFBCEqKJ1Xt7gmilHjGhW1Fuky75j3piMt0M50Wowmzr9zz+9899xz3znnin/5T/9yPv4HHOwJ/bcjaNmjx2CbTwXVYXYwMAbJx/gU9BqqWip3/Zf9RcnSiQnILsE4CnEgZj7iO1AL8ALjxr+sQ57u6EtjhxG09VOHs6nrWsy/HBjZN4DyyBoZMeoKTV/7a891tmrSaLbk7wLOAJx+NDbisEBJ75kdrdkO0FZOG0tn+4uYxUMDkQHnGdB6qvM/EKWKNg5Dmgt2BkYV8A04c5XKrQpMLBuvx1iG2d5AB1IjEedRIvn3iY4Sm1v3BY4H5mIcVvCj+xkxcp6mr+3qCdoLsLBrPwc2HviMqM5Undfc1+4tG9sf06OYTUPqwrgQWRXGvYWoaS2qnttXGpgtcshkzgO7E2w40sskx58iPZ0v+vwb0NYcF2Vz60qM6YgN2PB6pd7YVOoWhk6ymRsw/7pea8X1JL2bJVlJjaZEAt/PYIwBbleq5artATOxCzGWhsclJy43930p4V45l02cj2+BfRC7C5T0lg3IPp2oBX8NUE3EmaH63OpChgU5s+a4GtpaPwfGEonMVH3z8wMRL661dGwWciQ391SF9guAxaBmpbzJWwHTiZngPwu8rVRLrBLxobAJvx6/dHwWBioaOUZ1za8XIpiJBzfubKQGud6SoXBWqYZl4rdg1oC0RK7XUABMxz8AOwRHRyrpvVOp+FDYWTZxLL6/Fikn15tUBPwJbDQ1kT00o/nnoXBUqYYtj+1DF98G1UZuy9juI45twRiGs2eNkk3tlYoPhZ19OKuaLzYGDB1KtQwr5uBXmO1LlbOfTsx9PRSOKtWwFbV70tn+A+JHuS17FQHfCKuBo6SSXlOl4kNhZ9nJU/C73kR6V653RBFwIWaLkO6V6106FI4q1bBMfBFmC0H3KOXN6wZMHI757yB+w4aPL6fEVQrQn11YbttaPwYmIqdebm751lqciTdhVod0h1zvyp0BUErTwnLpPwh8gjvh0KBp2AqYjR+B0QwWAc2S6wWV5R/7WXrKQajrLcx2wdGsYn/Yu93KxC7DCJrMzd01ecU/QWhNU8bhd67C2B94TKmWOUW/2zes6dh9wEWITtBZcr0ndiakZSbFMT8NtldQPdit6ljVvvlnn4DdtflWzK5GGGi+XO+OnQFp2cRJ+P6TwAikV9h12Kk6al1rT199TnWWiQUX5TYM4XAb9V5DOc1nuRuxdOJisLvDnJceZsTIC7Zt9wOtfsdOy8RmA4+Ec4d4nHETzu5vAisHzsxEU/xWfLq7ZmehUrkb+7ItORdbOjED7DmwUaDVOJGZSm74vRyYbdcUGuM/GsFO687xc+V6jf1plQQs5GTicPCzGPsA71Fdk9QJ674dCGQ4kLX7L2FWGxYEBRttfrWURlmAIWTwKch3BnX6QOArnGi9khs+KuUgtF0+dSJdHYHtxNA26iRVl/uwHNuyAbsd7U6+M4jCNGAT0cjJQVveb/lqmjSVfP4lYI9gpCBKSnUt35UDV/KS7Eike8B6HDgF0Y40p69XAcvGT8NoxKwmfACIjj5dJ65sKxeuIsAwkuHAnb4LLOh8fBzNV9K7s6djy8bmYywJP1OwFHfCvJ4DebmQAzri7W5lNt6A2eIQQnqAiN1AVbXDls6bCkNY+KFfMJhBbFCAhRsem43pofDponcJaAPnnEpn5H5LXbnhL66zlZMPoDO/GKgNn9dgHTjXKJXbOFCtbdcPOoKDBShl/z9gqQiV+v8vv1MhRzp4qygAAAAASUVORK5CYII="},cmHI:function(A,t){A.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAD1klEQVRYR+2Ye0hTURzHv5vT5ZpTU7N8pKWrNEzR/sheZEEiFvR+UPYgiMKoIMU/FDOTtCcGlRVRkZhgFCUYjSgQhYIeqL2WrTnLfGXNdOp0usW5knm7u9e77ZIGnv92+P2+53N+O+d3fr8rAmDBGB6icUAH/53xCDoYQPzfEZQ6i7FpiScSYtwxTymDj7sEff0WvNb1QPWqA5cefENH9wBnkDzkEiQn+iA+WoGIYFfKtvGHCRVvDbhdqcfjqg5Of9YIblw8Cad3ByDQ25lVoL1rANnFTci/3wqLhZ6txGIRUtf6In3TVLi5ilk1yt8YkFzwGW/re6zaWAXM2xmAtPW+vI/Po6pOJJ2pQ4veRPn4ebmgKGU6lkbIeWkYjGZsPqFF2fOfDHsGINlxTpIfL+HhRtrmPsRnfoRIBDzKUSLIx8UmjQEzkHhUA9VLOiQNcFmkghIXk1k7RuvPfsprsrvEDm+AHJmYg++hbe4d8h8CFIlEeJE/G9EhMrvEhXIiF2djnpYJuCJaAVW2Uqh17NYhV23u/nd4oxu8NEMRPL9vGpUOxsI4WtyErKJGOmDNhXBEBA3mqdEeT9VdWJCipgO2FUfBy81ptNmo9Zv0Jvgl1dABjfeiIZXYeX0F3hZ5raSrX9EBP9+Yy/lqCMzAKUfSle/Wajrgk9yZiItw+5ccrGu91HRj3qH3dMDUdVNwcpf/mAA8eacFadcb6IDBvlLUXpkDZ6fRP4exKR/wTG2gA5JfBclB2JvgPapRJIXHioxa5ktCZrzdJXiRH2bzQy/UjowmC+YfVqNa220dkMxGzZCh8tQsTJSy13BCAf2ts/2sDoVPvtOmrdaDsWFylB0Jhaf83yRuUmoduPwFF8taGXtnrajDp7lCdUyJAC/2ilqISHb3mrHlVB1Kn7VbleNsmgJ9XPAwW4nwwAlCsDA0Wtr7sSpbg+e1Xaz6I3Z1nnIJSjNDsCicX/nOdyfqBiMSjmiga/lTnFrzHRGQOE1wEaModTrWxnrwXZ/TjjRKa3I+QW8YrMC5Bi9AIkC6tHN7ArF/pWM1463yH9iVX48+k3kkNmai5uORtn4Kcnf6U5WureN4STMyChsZLaogERwusi3OC9cOBfF+FvvNwL4L9biqarN1T/Z/+lgepcDd9BAoOJpyQtPZY8aGPC2jneRLyvsMWhOMnCHDg6xQ+E2yniu/fjdRve7wp4sv2G87hwCJCFuurNH1IDFLg4a2PluZaPYOAxI1N5kTcnf4Y91CT+oClFTokX6zEV1G7g9LfMgFAeSzkL0244D2Rk6wS+IowEj+vwDQOkMQJe3k/AAAAABJRU5ErkJggg=="},"d/cc":function(A,t,e){"use strict";var i={render:function(){var A=this,t=A.$createElement,e=A._self._c||t;return e("div",{directives:[{name:"show",rawName:"v-show",value:A.isShowAll,expression:"isShowAll"}],staticClass:"box",class:{"ov-h":!A.awakenApp},staticStyle:{overflow:"scroll",height:"auto","min-height":"100vh",position:"relative","overflow-x":"hidden"}},[A.getMoney?e("animate-get-money"):A._e(),A._v(" "),e("div",{ref:"wrapper",staticClass:"wrapper",staticStyle:{width:"100%",height:"90vh"}},[e("div",{staticClass:"content"},[A.awakenApp&&A.result.kid?e("awaken-app",{attrs:{result:A.result}}):A._e(),A._v(" "),e("activity-title",{attrs:{name:A.result.name}}),A._v(" "),A.result.kid&&1!=A.userType?e("activity-member-header",{attrs:{result:A.result}}):A._e(),A._v(" "),e("div",{staticClass:"newsContent",domProps:{innerHTML:A._s(A.result.contentRich)}}),A._v(" "),e("div",{staticClass:"line750"}),A._v(" "),e("div",{staticClass:"question_box",staticStyle:{"margin-bottom":".2rem",background:"#FFFFFF"}},[e("div",A._l(A.question,function(t,i){return e("div",{key:i},[e("p",{staticClass:"font-20 f-black mg-b10"},[0==t.questionType?e("span",{staticClass:"f-base"},[A._v("单选")]):A._e(),A._v(" "),1==t.questionType||2==t.questionType?e("span",{staticClass:"f-base"},[A._v("多选")]):A._e(),A._v(" "),3==t.questionType?e("span",{staticClass:"f-base"},[A._v("竞猜")]):A._e(),A._v(" "),e("span",{staticStyle:{"font-weight":"bold","word-wrap":"break-word","word-break":"break-all"}},[A._v(A._s(t.name))])]),A._v(" "),A._l(t.answerList,function(a,s){return 0==t.questionType||3==t.questionType?e("div",{key:s,staticClass:"font-14 f-dark mg-b10",on:{click:function(t){A.checkAnswer(i,s)}}},[s!=t.checking?e("div",{staticClass:"selectIdot"}):A._e(),A._v(" "),s==t.checking?e("div",{staticClass:"selectIdot chosed"}):A._e(),A._v(" "),e("span",[A._v(A._s(a.name))])]):A._e()}),A._v(" "),A._l(t.answerList,function(a,s){return 1==t.questionType||2==t.questionType||4==t.questionType?e("div",{key:s,staticClass:"font-14 f-dark mg-b10",on:{click:function(t){A.checkMultiselect(i,s+"")}}},[-1==t.checking.indexOf("n"+s+"n")?e("div",{staticClass:"checkIdot"}):A._e(),A._v(" "),t.checking&&-1!=t.checking.indexOf("n"+s+"n")?e("div",{staticClass:"checkIdot chosed"}):A._e(),A._v(" "),e("span",[A._v(A._s(a.name))])]):A._e()}),A._v(" "),A._m(0,!0)],2)}))]),A._v(" "),e("activity-join",{attrs:{result:A.result,finishUser:A.finishUser,userType:A.userType}}),A._v(" "),e("activity-tags",{attrs:{labels:A.result.activityLabel,type:3}})],1)]),A._v(" "),A.isChoselabelShow?e("div",{staticClass:"maskBox",on:{touchmove:function(A){A.preventDefault()}}},[e("div",{staticClass:"box"},[e("dialog-labelchose",{attrs:{labels:A.result.activityLabel},on:{"to-parent":A.dialogBtn}})],1)]):A._e(),A._v(" "),1!=A.userType?e("activity-bottom-btn",{attrs:{result:A.result},on:{sureQuestionBtn:A.sureQuestionBtn}}):A._e()],1)},staticRenderFns:[function(){var A=this.$createElement,t=this._self._c||A;return t("div",{staticClass:"line650",staticStyle:{transform:"translate(.48rem,0)"}},[t("div",{staticClass:"line"})])}]};t.a=i},fThv:function(A,t){A.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAArwAAAGQCAYAAABMPLOTAAAAAXNSR0IArs4c6QAAG0JJREFUeAHt3bFrHPYZxvHoJJIhh7ZCZDJ28tAtkDWrCoYsWjJp8D+QNZAhkDX/gAdNXrQUAtWa1dCtAU8djRTopipDgiX19xM6o7Of2KYQ4nv6MYiTXsv2vZ93+SLO0tZ7b/nr+Pj4/YuLi8+urq4ejD9y//r6+t54nG/Lt/wrfBoBAgQIECBAgACB/0XgYvyh062trdPx+HSxWHy/XC5/ODg4+PVt/rKtN33S0dHRR5eXl1+Pz/tiRO7umz7f7xMgQIAAAQIECBD4vQVG/J6Pf+Px9vb2N4eHhz+97t/7zeA9OTn54Ozs7KsRuV+Otw9f95f4PQIECBAgQIAAAQJ/hMAI35/H23d7e3vf7u/v/5KeQwze+VXd58+f/238gU/THzIjQIAAAQIECBAg8I4JPNnZ2fk8fbX3leB99OjRX8aT//t4+/gdW8LTIUCAAAECBAgQIPA6gWcjevdH9P5495PWgvf2K7v/GJ8gdu8qeZ8AAQIECBAgQGBTBGb0fnL3K72L1TOfr9m9fRmD2F2heCRAgAABAgQIENg0gY9n0862XT3xF8E7/4PaGHrN7krGIwECBAgQIECAwKYKfHrbtjfP/+YlDbffeuxfvhvDpt7U8yZAgAABAgQIELgrML97w/iWZX+eL224+Qrv/D67YvcukfcJECBAgAABAgQ2WWC27e3Pknhva/4EtfPz83+PoR8qsclX9dwJECBAgAABAgTWBOYPp9jd3f3TYv64YLG7ZuMDAgQIECBAgACBAoHZuLN1F1dXVw8K9rECAQIECBAgQIAAgVcEZuvO1/Def+V3DAgQIECAAAECBAh0CNxfjC/13uvYxRYECBAgQIAAAQIE1gVm686v8AredRcfESBAgAABAgQI9AjcBO+yZx+bECBAgAABAgQIEFgTWL74SWtrYx8QIECAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEBG/JIa1BgAABAgQIECCQBQRvdjElQIAAAQIECBAoERC8JYe0BgECBAgQIECAQBYQvNnFlAABAgQIECBAoERA8JYc0hoECBAgQIAAAQJZQPBmF1MCBAgQIECAAIESAcFbckhrECBAgAABAgQIZAHBm11MCRAgQIAAAQIESgQEb8khrUGAAAECBAgQIJAFBG92MSVAgAABAgQIECgRELwlh7QGAQIECBAgQIBAFhC82cWUAAECBAgQIECgREDwlhzSGgQIECBAgAABAllA8GYXUwIECBAgQIAAgRIBwVtySGsQIECAAAECBAhkAcGbXUwJECBAgAABAgRKBARvySGtQYAAAQIECBAgkAUEb3YxJUCAAAECBAgQKBEQvCWHtAYBAgQIECBAgEAWELzZxZQAAQIECBAgQKBEQPCWHNIaBAgQIECAAAECWUDwZhdTAgQIECBAgACBEgHBW3JIaxAgQIAAAQIECGQBwZtdTAkQIECAAAECBEoEZvBelOxiDQIECBAgQIAAAQJrAltbW/+ZwXu6NvUBAQIECBAgQIAAgRKB6+vrs8WoXsFbclBrECBAgAABAgQIrAvM1p1f4X26PvYRAQIECBAgQIAAgRqBp4vx6/uadSxCgAABAgQIECBA4I7AbN3Fcrn8YXyp9/zO3LsECBAgQIAAAQIENl5gNu5s3cXBwcGvY5vHG7+RBQgQIECAAAECBAisCzyerXvzfXi3t7e/GQX88/rv+4gAAQIECBAgQIDAZgrMtp2NO5/9TfAeHh7+NIbfbeY6njUBAgQIECBAgACBdYHZtrNx5/QmeOc7e3t7346HJ/N9vwgQIECAAAECBAhssMCT27a9WeFF8O7v7/+ys7Pz+Zg+2+DlPHUCBAgQIECAAIH/b4Fns2ln264YXgTvHNx+2fev413RuxLySIAAAQIECBAgsCkCM3b3Vy9lWD3pteCdw4cPH/5zfOIn410vb1gpeSRAgAABAgQIEHjXBZ7Mhh2x++PLT3Tr5cHq45OTkw/Ozs6+Gj9/+Mvx9uFq7pEAAQIECBAgQIDAuyIwvxvD/A9q8zW7d1/GcPf5/Wbwrj7p6Ojoo8vLy6/Hx1+M8N1dzT0SIECAAAECBAgQ+KMERuTOH5z2eH7rsZdfwvDyc3pj8K7+wPHx8fsXFxefXV1dPRiz+yN+743H+bZcfY5HAgQIECBAgAABAr+DwMX4O09H5J6Ox6fzxwXPn6B2+wPU3vjP/Rekvv0cBydjNQAAAABJRU5ErkJggg=="},fbdz:function(A,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={name:"activityTitle",props:["name"],data:function(){return{}}}},mb9a:function(A,t){},pem1:function(A,t,e){"use strict";var i={render:function(){var A=this,t=A.$createElement,i=A._self._c||t;return A.isH5?A._e():i("div",{staticClass:"activityBottom"},[i("div",{staticClass:"bottomBox"},[i("div",{staticClass:"follow"},[A.result.follow?i("img",{attrs:{src:e("cmHI")},on:{click:A.followActivity}}):A._e(),A._v(" "),A.result.follow?A._e():i("img",{attrs:{src:e("bXkW")},on:{click:A.followActivity}})]),A._v(" "),i("div",{staticClass:"rightBtn"},[0===A.result.online?i("div",[A._v("活动未开始")]):1===A.result.online?i("div",[3===A.result.activityForm||4===A.result.activityForm?i("div",[A.result.join?i("div",{on:{click:A.sureQuestionBtn}},[A._v(A._s(4!==A.result.activityStatus||4!==A.result.activityForm&&4!==A.result.questionType?1===A.result.informationQuestionStatus?"恭喜您答对啦":"不好意思您答错了":"您已经参与过了"))]):i("div",{on:{click:A.sureQuestionBtn}},[A._v("提交")])]):A._e(),A._v(" "),0===A.result.activityForm||1===A.result.activityForm?i("div",[A.result.join?i("div",[0===A.result.activityStatus||0===A.activityStatus?i("div",[0===A.result.activityForm?i("router-link",{attrs:{to:{path:"/activity/commitoffline",query:{id:A.id}}}},[A._v("提交活动")]):i("div",{staticClass:"submitBtn"},[A._v("提交活动")])],1):A._e(),A._v(" "),1===A.result.activityStatus||1===A.activityStatus?i("div",[i("div",{},[A._v("待审核")])]):A._e(),A._v(" "),2===A.result.activityStatus||A.getMoneyBtn||2===A.activityStatus?i("div",[i("div",{on:{click:A.getMoney}},[A._v("领钱")])]):A._e(),A._v(" "),3===A.result.activityStatus||3===A.activityStatus?i("div",[0===A.result.activityForm?i("router-link",{attrs:{to:{path:"/activity/commitoffline",query:{id:A.id}}}},[A._v("重新提交")]):i("div",{staticClass:"submitBtn",on:{click:A.commitActivity}},[A._v("重新提交")])],1):A._e(),A._v(" "),4===A.result.activityStatus||4===A.activityStatus?i("div",[i("div",[A._v("已完成")])]):A._e()]):i("div",[1===A.result.activityForm?i("div",{on:{click:A.startPlay}},[A._v("报名（播放视频）")]):i("div",{on:{click:A.joinActivity}},[A._v("报名")])]),A._v(" "),A.dialogShow?i("div",{staticClass:"dialogQuestion"},[i("div",{staticClass:"box"},[A.result.kid?i("dialog-questionnaire",{attrs:{result:A.result,callback:A.commitActivity2},on:{closeDialog:A.closeDialog}}):A._e()],1)]):A._e()]):A._e()]):2===A.result.online?i("div",[A._v("活动已结束")]):A._e()])]),A._v(" "),A.isIphoneX?i("div",{staticStyle:{width:"100%",height:"34px",background:"#fff"}}):A._e()])},staticRenderFns:[]};t.a=i},tgqJ:function(A,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=e("1YPC"),a=e.n(i);for(var s in i)"default"!==s&&function(A){e.d(t,A,function(){return i[A]})}(s);var E=e("d/cc");var n=function(A){e("45hD")},B=e("VU/8")(a.a,E.a,!1,n,null,null);t.default=B.exports},uNUQ:function(A,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=s(e("S8Wg")),a=(s(e("eOoE")),s(e("7+uW")),s(e("GQaK")));function s(A){return A&&A.__esModule?A:{default:A}}t.default={name:"activity-labelchose",props:["labels"],data:function(){return{labelArr:[]}},components:{Scroller:i.default},mounted:function(){for(var A=this,t=0;t<this.labels.length;t++){var e=JSON.parse(this.labels[t]);e.isChosed=!1,this.labelArr.push(e)}this.$nextTick(function(){A.scroll=new a.default(A.$refs.labelWrapper,{click:!0})})},methods:{choseLabel:function(A){this.labelArr[A].isChosed=!this.labelArr[A].isChosed},submitChoseLabel:function(){this.$emit("to-parent",this.labelArr)},cancel:function(){this.$emit("to-parent","cancel")}}}},"w+ft":function(A,t,e){"use strict";var i={render:function(){var A=this,t=A.$createElement,e=A._self._c||t;return e("div",[e("ul",{staticClass:"tagBox"},A._l(A.tagsList,function(t,i){return e("li",{key:i},[A._v(A._s(t.name))])}))])},staticRenderFns:[]};t.a=i},w4PT:function(A,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={name:"activityTags",props:["labels","type"],data:function(){return{tagsList:[]}},mounted:function(){console.log("活动标签",this.labels,this.type)},watch:{labels:function(A,t){if(console.log("活动标签",A),1==this.type)this.tagsList=JSON.parse(A);else{for(var e=[],i=0;i<A.length;i++)e.push(JSON.parse(A[i]));this.tagsList=e}console.log("活动标签",this.tagsList)}}}},yFfi:function(A,t){A.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAaklEQVRIS+2VMQ7AMAgDy6NgSl/fTvAoIjpHAgaGSs5sTHLEgq7hQx1/M3tCz8x3ta7VQFU9jEWkXFcWhjEapHMDoh8jioS6+0qf0BAQ0RuJ/4I23qB6MXzTlBQQAdGRQGtlji/9dEgHwQa3XmAZ8Q5jPgAAAABJRU5ErkJggg=="},zcnD:function(A,t){}});
//# sourceMappingURL=3.a0d5b3e59ac9b97fa929.js.map