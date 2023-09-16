package util

import util.HtmlUtil.parseTimeTable
import kotlin.test.Test
import kotlin.test.assertNotNull

class HtmlUtilTest {
    @Test
    fun testParseTimeTable(){
        val html = """





































<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>









    
    
    
    
    
    
    
        
    
    
    
    
    
    
    
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        
        
        
        
        <meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="Thu, 01 Dec 1994 16:00:00 GMT">

        
        
        <link rel="SHORTCUT ICON" href="/campusweb/theme/default/image/favicon.ico">
        
        
        <meta name="format-detection" content="telephone=no">
        
        
    
         
        <title>履修登録・登録状況照会</title>
        
      
      
        <link rel=stylesheet href="/campusweb/theme/default/StyleSheet.css" type="text/css">
        
        <script type="text/javascript" src="/campusweb/js/ScriptColor.js"></script>
        <script type="text/javascript" src="/campusweb/js/Script.js"></script>
        
        
        <link rel="stylesheet" type="text/css" href="/campusweb/theme/default/newportal/CLEditor1_4_4/jquery.cleditor.css" />
        
        
        
        <link rel="stylesheet" type="text/css" href="/campusweb/theme/default/newportal/jquery-ui/css/smoothness/jquery-ui-1.8.19.custom.css" />
        
        
        <link rel="stylesheet" type="text/css" href="/campusweb/theme/default/newportal/portal_body.css" />
        <link rel="stylesheet" type="text/css" href="/campusweb/theme/default/newportal/portal_table.css" />
        <link rel="stylesheet" type="text/css" href="/campusweb/theme/default/newportal/wrapperStyleSheet.css" />
        <script type="text/javascript" src="/campusweb/theme/default/newportal/jquery.min.js"></script>
        <script type="text/javascript" src="/campusweb/theme/default/newportal/jquery-ui/js/jquery-ui-1.8.19.custom.min.js"></script>
        <script type="text/javascript" src="/campusweb/theme/default/newportal/CLEditor1_4_4/jquery.cleditor.js"></script>
        <script type="text/javascript" src="/campusweb/theme/default/newportal/portal_common.js"></script>
      
        
        <script type="text/javascript" src="/campusweb/theme/default/newportal/main_portlet.js"></script>
        
        
        
        
        
        <style type="text/css">
body.logout { background-color: #eeddaa; }
body {  background: none; background-image: none; background-color: #ffffff; }
.portlet-title { display: block; }
.portlet-title-linker { display: block; }
.portlet-title {  background: none; background-image: none; background-color: #dddddd; }
.portlet-title { border-radius: 10px 10px 10px 10px; }
.portlet-title { color: black; }
</style>

    </head>
    
    
    





    


<script language="JavaScript">
<!--
var rishu_ank_flg = false;
var icon_ank_flg = false;


function InputCallA(yobi, jigen) {
  rishu_ank_flg = true;

  if(!send()) {
    return false;
  }

  document.InputForm.yobi.value = yobi;
  document.InputForm.jigen.value = jigen;
  document.InputForm.submit();

  return false;
}


function InputCall(yobi, jigen) {
    if(rishu_ank_flg) {
      rishu_ank_flg = false;
      return false;
    }

    if(!send()) {
      return false;
    }

    document.InputForm.yobi.value = yobi;
    document.InputForm.jigen.value = jigen;
    document.InputForm.submit();

    return false;
}


function DeleteCallA(nendo, jikanwariShozokuCode, jikanwariCode, yobi, jigen, eventId) {
    rishu_ank_flg = true;

    if(!send()) {
      return false;
    }

    document.DeleteForm.nendo.value = nendo;
    document.DeleteForm.jikanwariShozokuCode.value = jikanwariShozokuCode;
    document.DeleteForm.jikanwariCode.value = jikanwariCode;
    document.DeleteForm.yobi.value = yobi;
    document.DeleteForm.jigen.value = jigen;
    document.DeleteForm._eventId.value = eventId;
    document.DeleteForm.submit();
    return false;
}


function DeleteCall(nendo, jikanwariShozokuCode, jikanwariCode, yobi, jigen, eventId) {
  if(rishu_ank_flg) {
    rishu_ank_flg = false;
    return false;
  }
  
  if (icon_ank_flg){
      icon_ank_flg = false;
      return false;
  }

  if(!send()) {
    return false;
  }

  document.DeleteForm.nendo.value = nendo;
  document.DeleteForm.jikanwariShozokuCode.value = jikanwariShozokuCode;
  document.DeleteForm.jikanwariCode.value = jikanwariCode;
  document.DeleteForm.yobi.value = yobi;
  document.DeleteForm.jigen.value = jigen;
  document.DeleteForm._eventId.value = eventId;
  document.DeleteForm.submit();

  return false;
}

function InputCallSaiRishu(nendo, jikanwariShozokuCode, jikanwariCode, yobi, jigen) {
  rishu_ank_flg = true;

  if(!send()) {
    return false;
  }

  document.InputForm.nendo.value = nendo;
  document.InputForm.jikanwariShozokuCode.value = jikanwariShozokuCode;
  document.InputForm.jikanwariCode.value = jikanwariCode;
  document.InputForm.yobi.value = yobi;
  document.InputForm.jigen.value = jigen;
  document.InputForm.submit();

  return false;
}


function OtherInputCall() {
  document.InputForm.yobi.value = '9';
  document.InputForm.jigen.value = '0';
  document.InputForm.submit();

  return false;
}

function kodoInputCall(category) {
    document.InputForm._eventId.value = 'kodo';
    document.InputForm.nendo.value = "2023";
    document.InputForm.jikanwariShozokuCode.value = "0902";
    document.InputForm.category.value = category;
    document.InputForm.submit();

    return false;
  }

function callExecShikakuJikoHantei() {
  var url = "/campusweb/campussquare.do?"
      + "_eventId=execShikakuJikoHantei"
      + "&_flowExecutionKey=_XXX";
  var opt = "location=0,menubar=0,scrollbars=1,resizable=1,toolbar=1";
  SubWin = window.open(url, "ShikakuJikoHanteiWindow", opt);
  return false;
}

function onLoadFunc() {
   
     var url = "/campusweb/campussquare.do?"
            + "_flowId=CHW0001300-flow"
            + "&rs_flg=1";
     var opt = "width=800,height=600,location=0,menubar=0,scrollbars=1,resizable=1,toolbar=1";
     SubWin = window.open(url, "gakuseiWindow", opt);
   
   return false;
}


function syllabusRefer(nendo, jikanwariShozokuCode, jikanwariCode) {
    icon_ank_flg = true;
    
    var url = "/campusweb/campussquare.do?"
        + "_flowId=SYW0001000-flow"
        + "&_eventId=syllabus"
        + "&nendo=" + nendo
        + "&jikanwarishozokucd=" + jikanwariShozokuCode
        + "&jikanwaricd=" + jikanwariCode ;
    var opt = "width=800,height=600,location=0,menubar=0,scrollbars=1,resizable=1,toolbar=1";
    SubWin = window.open(url, "syllabusWindow", opt);
    return false;
}


function fukuproRefer(nendo, jikanwariShozokuCode, jikanwariCode) {
    icon_ank_flg = true;
    
    var url = "/campusweb/campussquare.do?"
        + "_flowId=FPW4207000-flow"
        + "&bukyokuCode=" + '0902'
        + "&gakusekiNo=" + '09B23081'
        + "&jikanwaricd=" + jikanwariCode ;
    var opt = "width=800,height=600,location=0,menubar=0,scrollbars=1,resizable=1,toolbar=1";
    SubWin = window.open(url, "fukuproWindow", opt);
    return false;
}

function output(eventId) {
    document.OutputForm._eventId.value = eventId;
    document.OutputForm.submit();
    return false;
}
//指定URL　WindowOpen処理
function openUrlFunc(url) {
    icon_ank_flg = true;
	SubWin = window.open(
		url,
		"",
		"width=800, height=600, location=0, menubar=0, scrollbars=1, resizable=1, toolbar=0"
	);
	return false;
}

//-->
</script>

<body onload="onLoadFunc()">







    
    
    
    <div class="portlet-title" id="main-portlet-title">
        <img src="/campusweb/theme/default/newportal/image/icon/func_entry_green2.gif"><span><a href="" onclick="return false;" class="no-effect">履修登録・登録状況照会</a></span>
    </div>

    <form name="TimeoutForm" style="margin: 0px; padding: 0px;">
        <input type="hidden" name="status" value="1">
    </form>







<!-- 学生情報 -->
<table border="0" cellspacing="1" cellpadding="1">
  <tr class="rishu">
    <th height="25" class="rishu-head">学生氏名</th>
    <td colspan="3">
      
        阪大　太郎
      </td>
    <th class="rishu-head">学籍番号 </th>
    <td align="center">00A00000</td>
  </tr>
  <tr class="rishu">
    <th height="25" class="rishu-head">学生所属</th>
    <td colspan="3">基礎工学部情報科学科</td>
    <th class="rishu-head">学年</th>
    
    <td align="center">1年 </td>
  </tr>
  <tr class="rishu">
    <th width="100" height="25" class="rishu-head">年度・開講期</th>
    <td width="130" align="center">2023年度　秋学期</td>
    
    
    <th width="130" class="rishu-head" style="{white-space: nowrap;}">所属学部・研究科<br>開講科目期限</th>
    <td width="150" align="center">2023年10月16日 23:59</td>
    <th width="100" class="rishu-head">件数</th>
    <td width="100" align="center">6件</td>
  </tr>
</table>
<p></p>




<p></p>











<p></p>
<!-- 登録確認Form -->



  <form id="rishuReferKakuninForm" name="KakuninForm" method="post" action="/campusweb/campussquare.do">
    <input type="hidden" name="_eventId" value="kakunin">
    <input type="hidden" name="_flowExecutionKey" value="_XXX">
    <table border="0" cellspacing="1" cellpadding="0">
        <tr>
            <td><input type="submit" value="　登録完了　">　</td>
            <td><b>※履修登録が完了したら押してください。<br>　「登録完了」ボタンを押した場合でも、登録期間中は引き続き履修登録できます。</b></td>
    </table>
  </form>













<!-- ===== 全体テーブル(開始) ===== -->
<p>
<table border="0" cellspacing="0" cellpadding="0" hspace="0" vspace="0">
  <tr>
    <td height="20">
      <!-- ===== 学期タブテーブル(開始) ===== -->
      <table border="0" cellspacing="0" cellpadding="0" height="20" hspace="0" vspace="0">
        <tr>
          <td width="540" height="20" align="right" colspan="5">
            
              <a href="" onClick="return OtherInputCall();">集中講義などを登録</a>
            
          </td>
        </tr>
        <tr>
          <td width="540" height="20" align="right" colspan="5">
            
              <a href="" onClick="return kodoInputCall(12);">高度教養教育科目を登録</a>
            
          </td>
        </tr>
        <tr>
          <!-- 学期区分マスタの件数分ループ(開始) -->
          
            <!-- 選択学期/未選択学期で表示切替(開始) -->
            
              
              
                <!-- 未選択学期のとき -->
                
                
                
                
                
              
            
            <!-- 選択学期/未選択学期で表示切替(終了) -->
            <td width="100" height="20" align="center" background="/campusweb//theme/default/image/rs_tab.gif" class="rishu-tab" title="春学期を表示します">
              <img src="/campusweb//theme/default/image/space.gif" height="2" width="1"><br>
              <a href="/campusweb/campussquare.do?_flowExecutionKey=_XXX&_eventId=search&gakkiKbnCode=3">春学期</a>
            </td>
          
            <!-- 選択学期/未選択学期で表示切替(開始) -->
            
              
              
                <!-- 未選択学期のとき -->
                
                
                
                
                
              
            
            <!-- 選択学期/未選択学期で表示切替(終了) -->
            <td width="100" height="20" align="center" background="/campusweb//theme/default/image/rs_tab.gif" class="rishu-tab" title="夏学期を表示します">
              <img src="/campusweb//theme/default/image/space.gif" height="2" width="1"><br>
              <a href="/campusweb/campussquare.do?_flowExecutionKey=_XXX&_eventId=search&gakkiKbnCode=4">夏学期</a>
            </td>
          
            <!-- 選択学期/未選択学期で表示切替(開始) -->
            
              
                <!-- 選択学期のとき -->
                
                
                
                
              
              
            
            <!-- 選択学期/未選択学期で表示切替(終了) -->
            <td width="100" height="20" align="center" background="/campusweb//theme/default/image/rs_tab_sel.gif" class="rishu-tab-sel" title="秋学期を表示しています">
              <img src="/campusweb//theme/default/image/space.gif" height="2" width="1"><br>
              秋学期
            </td>
          
            <!-- 選択学期/未選択学期で表示切替(開始) -->
            
              
              
                <!-- 未選択学期のとき -->
                
                
                
                
                
              
            
            <!-- 選択学期/未選択学期で表示切替(終了) -->
            <td width="100" height="20" align="center" background="/campusweb//theme/default/image/rs_tab.gif" class="rishu-tab" title="冬学期を表示します">
              <img src="/campusweb//theme/default/image/space.gif" height="2" width="1"><br>
              <a href="/campusweb/campussquare.do?_flowExecutionKey=_XXX&_eventId=search&gakkiKbnCode=6">冬学期</a>
            </td>
          
          <!-- 学期区分マスタの件数分ループ(終了) -->
          <td width="540" align="right">
            
              <a href="" onClick="return kodoInputCall(13);">高度国際性涵養教育科目を登録</a>
            
          </td>
        </tr>
      </table>
      <!-- ===== 学期タブテーブル(終了) ===== -->
    </td>
  </tr>
  <tr>
    <td>
      <!-- ===== コマテーブル(開始) ===== -->
      <table border="0" cellspacing="1" cellpadding="0" class="rishu-koma">
        <!-- ===== 行ヘッダー[曜日] ===== -->
        <tr>
          <td width="40" height="20" class="rishu-koma-head">　</td>
          
            
              <td width="140" align="center" class="rishu-koma-head">月曜日</td>
            
          
            
              <td width="140" align="center" class="rishu-koma-head">火曜日</td>
            
          
            
              <td width="140" align="center" class="rishu-koma-head">水曜日</td>
            
          
            
              <td width="140" align="center" class="rishu-koma-head">木曜日</td>
            
          
            
              <td width="140" align="center" class="rishu-koma-head">金曜日</td>
            
          
            
              <td width="140" align="center" class="rishu-koma-head">土曜日</td>
            
          
        </tr>
        <!-- ===== コマテーブル ===== -->
        <!-- 時限マスタの件数分ループ(開始) -->
        
          <tr>
            <!-- ===== コマテーブル列ヘッダー[時限] ===== -->
            <td width="40" height="60" align="center" class="rishu-koma-head">
              <table border="0" width="40" cellspacing="0" cellpadding="2">
                <tr>
                  <td width="40" align="center" class="rishu-koma-head">1限</td>
                </tr>
              </table>
            </td>
            <!-- ===== コマテーブルデータ ===== -->
            <!-- 曜日マスタの件数分ループ(開始) -->
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('1','1')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('1','1')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	                <!-- 履修登録ありのとき -->
	                <!-- 開講区分コード&取消有無で背景色を設定(開始) -->
	                
                      
	                  
	                  
	                    <!-- 開講区分コード＝通年以外のとき -->
	                    
	                  
	                
	                <!-- 開講区分コード&取消有無で背景色を設定(終了) -->
	                <!-- 更新可/不可で表示切替(開始) -->
                    
                    
                        
                        
                           
                              
                              
                              
                          
                       
                    
                    
                    
                        
                        
                            
                            
                        
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
	                <!-- 履修中止可/不可で表示切替(終了) -->
	              
	              
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#ffffcc" onMouseOut="TDMouseOutC(this, '#ffffcc')" onMouseOver="TDMouseOverC(this, '#cc99cc')" >
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      137423
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      基礎解析学・同演義II
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      石渡　通徳<br>共Ｂ２０７
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return syllabusRefer('2023','13','137423');"><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_syllabus.png"></a>
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('3','1')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('3','1')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	                <!-- 履修登録ありのとき -->
	                <!-- 開講区分コード&取消有無で背景色を設定(開始) -->
	                
                      
	                  
	                  
	                    <!-- 開講区分コード＝通年以外のとき -->
	                    
	                  
	                
	                <!-- 開講区分コード&取消有無で背景色を設定(終了) -->
	                <!-- 更新可/不可で表示切替(開始) -->
                    
                    
                        
                        
                           
                              
                              
                              
                          
                       
                    
                    
                    
                        
                        
                            
                            
                        
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
	                <!-- 履修中止可/不可で表示切替(終了) -->
	              
	              
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#ffffcc" onMouseOut="TDMouseOutC(this, '#ffffcc')" onMouseOver="TDMouseOverC(this, '#cc99cc')" >
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      137471
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      線形代数学II
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      星野　壮登<br>共Ｂ２０８
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return syllabusRefer('2023','13','137471');"><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_syllabus.png"></a>
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('5','1')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('5','1')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('6','1')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('6','1')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
            <!-- 曜日マスタの件数分ループ(終了) -->
          </tr>
        
          <tr>
            <!-- ===== コマテーブル列ヘッダー[時限] ===== -->
            <td width="40" height="60" align="center" class="rishu-koma-head">
              <table border="0" width="40" cellspacing="0" cellpadding="2">
                <tr>
                  <td width="40" align="center" class="rishu-koma-head">2限</td>
                </tr>
              </table>
            </td>
            <!-- ===== コマテーブルデータ ===== -->
            <!-- 曜日マスタの件数分ループ(開始) -->
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('1','2')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('1','2')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	                <!-- 履修登録ありのとき -->
	                <!-- 開講区分コード&取消有無で背景色を設定(開始) -->
	                
                      
	                  
	                  
	                    <!-- 開講区分コード＝通年以外のとき -->
	                    
	                  
	                
	                <!-- 開講区分コード&取消有無で背景色を設定(終了) -->
	                <!-- 更新可/不可で表示切替(開始) -->
                    
                    
                        
                        
                           
                              
                              
                              
                          
                       
                    
                    
                    
                        
                        
                            
                            
                        
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
	                <!-- 履修中止可/不可で表示切替(終了) -->
	              
	              
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#ffffcc" onMouseOut="TDMouseOutC(this, '#ffffcc')" onMouseOver="TDMouseOverC(this, '#cc99cc')" >
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      137423
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      基礎解析学・同演義II
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      石渡　通徳<br>共Ｂ２０７
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return syllabusRefer('2023','13','137423');"><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_syllabus.png"></a>
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('3','2')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('3','2')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('4','2')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('4','2')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('5','2')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('5','2')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('6','2')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('6','2')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
            <!-- 曜日マスタの件数分ループ(終了) -->
          </tr>
        
          <tr>
            <!-- ===== コマテーブル列ヘッダー[時限] ===== -->
            <td width="40" height="60" align="center" class="rishu-koma-head">
              <table border="0" width="40" cellspacing="0" cellpadding="2">
                <tr>
                  <td width="40" align="center" class="rishu-koma-head">3限</td>
                </tr>
              </table>
            </td>
            <!-- ===== コマテーブルデータ ===== -->
            <!-- 曜日マスタの件数分ループ(開始) -->
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	                <!-- 履修登録ありのとき -->
	                <!-- 開講区分コード&取消有無で背景色を設定(開始) -->
	                
                      
	                  
	                  
	                    <!-- 開講区分コード＝通年以外のとき -->
	                    
	                  
	                
	                <!-- 開講区分コード&取消有無で背景色を設定(終了) -->
	                <!-- 更新可/不可で表示切替(開始) -->
                    
                    
                        
                        
                           
                              
                              
                                  
                                  
                              
                              
                          
                       
                    
                    
                    
                        
                            
                            
                            
                        
                        
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
	                <!-- 履修中止可/不可で表示切替(終了) -->
	              
	              
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#ffffcc" onMouseOut="TDMouseOutC(this, '#ffffcc')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return DeleteCall('2023','09','099024','1','3', 'delete')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return DeleteCallA('2023','09','099024','1','3', 'delete')">
                                099024
                              </a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      情報科学基礎
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      若宮　直紀<br>基礎工/A403講義室<BR>ｻｲﾊﾞｰ情報教育第3教室
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return syllabusRefer('2023','09','099024');"><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_syllabus.png"></a>
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	                <!-- 履修登録ありのとき -->
	                <!-- 開講区分コード&取消有無で背景色を設定(開始) -->
	                
                      
	                  
	                  
	                    <!-- 開講区分コード＝通年以外のとき -->
	                    
	                  
	                
	                <!-- 開講区分コード&取消有無で背景色を設定(終了) -->
	                <!-- 更新可/不可で表示切替(開始) -->
                    
                    
                        
                        
                           
                              
                              
                              
                          
                       
                    
                    
                    
                        
                        
                            
                            
                        
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
	                <!-- 履修中止可/不可で表示切替(終了) -->
	              
	              
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#ffffcc" onMouseOut="TDMouseOutC(this, '#ffffcc')" onMouseOver="TDMouseOverC(this, '#cc99cc')" >
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      192475
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      ドイツ語初級II
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      永谷　麻衣子<br>共Ａ２０４
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return syllabusRefer('2023','14','192475');"><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_syllabus.png"></a>
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('3','3')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('3','3')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	                <!-- 履修登録ありのとき -->
	                <!-- 開講区分コード&取消有無で背景色を設定(開始) -->
	                
                      
	                  
	                  
	                    <!-- 開講区分コード＝通年以外のとき -->
	                    
	                  
	                
	                <!-- 開講区分コード&取消有無で背景色を設定(終了) -->
	                <!-- 更新可/不可で表示切替(開始) -->
                    
                    
                        
                        
                           
                              
                              
                                  
                                  
                              
                              
                          
                       
                    
                    
                    
                        
                            
                            
                            
                        
                        
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
	                <!-- 履修中止可/不可で表示切替(終了) -->
	              
	              
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#ffffcc" onMouseOut="TDMouseOutC(this, '#ffffcc')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return DeleteCall('2023','09','099026','4','3', 'delete')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return DeleteCallA('2023','09','099026','4','3', 'delete')">
                                099026
                              </a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      プログラミングＢ
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      伊野　文彦<br>基礎工/B105講義室<BR>ｻｲﾊﾞｰ情報教育第3教室
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return syllabusRefer('2023','09','099026');"><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_syllabus.png"></a>
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('5','3')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('5','3')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('6','3')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('6','3')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
            <!-- 曜日マスタの件数分ループ(終了) -->
          </tr>
        
          <tr>
            <!-- ===== コマテーブル列ヘッダー[時限] ===== -->
            <td width="40" height="60" align="center" class="rishu-koma-head">
              <table border="0" width="40" cellspacing="0" cellpadding="2">
                <tr>
                  <td width="40" align="center" class="rishu-koma-head">4限</td>
                </tr>
              </table>
            </td>
            <!-- ===== コマテーブルデータ ===== -->
            <!-- 曜日マスタの件数分ループ(開始) -->
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	                <!-- 履修登録ありのとき -->
	                <!-- 開講区分コード&取消有無で背景色を設定(開始) -->
	                
                      
	                  
	                  
	                    <!-- 開講区分コード＝通年以外のとき -->
	                    
	                  
	                
	                <!-- 開講区分コード&取消有無で背景色を設定(終了) -->
	                <!-- 更新可/不可で表示切替(開始) -->
                    
                    
                        
                        
                           
                              
                              
                                  
                                  
                              
                              
                          
                       
                    
                    
                    
                        
                            
                            
                            
                        
                        
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
	                <!-- 履修中止可/不可で表示切替(終了) -->
	              
	              
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#ffffcc" onMouseOut="TDMouseOutC(this, '#ffffcc')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return DeleteCall('2023','09','099026','1','4', 'delete')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return DeleteCallA('2023','09','099026','1','4', 'delete')">
                                099026
                              </a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      プログラミングＢ
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      伊野　文彦<br>基礎工/B105講義室<BR>ｻｲﾊﾞｰ情報教育第3教室
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return syllabusRefer('2023','09','099026');"><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_syllabus.png"></a>
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('2','4')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('2','4')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('3','4')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('3','4')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('4','4')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('4','4')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('5','4')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('5','4')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('6','4')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('6','4')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
            <!-- 曜日マスタの件数分ループ(終了) -->
          </tr>
        
          <tr>
            <!-- ===== コマテーブル列ヘッダー[時限] ===== -->
            <td width="40" height="60" align="center" class="rishu-koma-head">
              <table border="0" width="40" cellspacing="0" cellpadding="2">
                <tr>
                  <td width="40" align="center" class="rishu-koma-head">5限</td>
                </tr>
              </table>
            </td>
            <!-- ===== コマテーブルデータ ===== -->
            <!-- 曜日マスタの件数分ループ(開始) -->
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('1','5')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('1','5')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('2','5')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('2','5')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('3','5')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('3','5')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('4','5')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('4','5')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('5','5')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('5','5')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('6','5')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('6','5')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
            <!-- 曜日マスタの件数分ループ(終了) -->
          </tr>
        
          <tr>
            <!-- ===== コマテーブル列ヘッダー[時限] ===== -->
            <td width="40" height="60" align="center" class="rishu-koma-head">
              <table border="0" width="40" cellspacing="0" cellpadding="2">
                <tr>
                  <td width="40" align="center" class="rishu-koma-head">6限</td>
                </tr>
              </table>
            </td>
            <!-- ===== コマテーブルデータ ===== -->
            <!-- 曜日マスタの件数分ループ(開始) -->
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('1','6')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('1','6')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('2','6')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('2','6')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('3','6')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('3','6')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('4','6')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('4','6')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('5','6')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('5','6')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('6','6')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('6','6')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
            <!-- 曜日マスタの件数分ループ(終了) -->
          </tr>
        
          <tr>
            <!-- ===== コマテーブル列ヘッダー[時限] ===== -->
            <td width="40" height="60" align="center" class="rishu-koma-head">
              <table border="0" width="40" cellspacing="0" cellpadding="2">
                <tr>
                  <td width="40" align="center" class="rishu-koma-head">7限</td>
                </tr>
              </table>
            </td>
            <!-- ===== コマテーブルデータ ===== -->
            <!-- 曜日マスタの件数分ループ(開始) -->
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('1','7')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('1','7')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('2','7')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('2','7')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('3','7')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('3','7')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('4','7')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('4','7')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('5','7')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('5','7')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
              
              
              
              
              
              
              
	            <!-- 履修登録あり/なしで表示切替(開始) -->
	            
	              
	              
	                  <!-- 履修登録なしのとき -->
	                  
	                  <!-- 更新可/不可で表示切替(開始) -->
	                  
	                    
	                      <!-- 更新可のとき -->
	                      
	                      
	                    
	                    
	                  
	                  <!-- 更新可/不可で表示切替(終了) -->
	                
	            
                <!-- 履修登録あり/なしで表示切替(終了) -->
              
              <td width="140" valign="top" bgcolor="#dddddd" onMouseOut="TDMouseOutC(this, '#dddddd')" onMouseOver="TDMouseOverC(this, '#cc99cc')" onclick="return InputCall('6','7')">
                <table border="0" width="140" cellspacing="0" cellpadding="2" class="rishu-koma-inner">
                  <tr>
                    <td width="140" valign="top">
                      <a href="" onclick="return InputCallA('6','7')">未登録</a>
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="35" valign="top">
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <td height="20" valign="top">
                      <br>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="140" valign="top">
                      
                    </td>
                  </tr>
                </table>
              </td>
            
            <!-- 曜日マスタの件数分ループ(終了) -->
          </tr>
        
        <!-- 時限マスタの件数分ループ(終了) -->
      </table>
    <!-- ===== コマテーブル(終了) ===== -->
    </td>
  </tr>
  <tr>
    <td>　</td>
  </tr>
  <tr>
    <td>
      <!-- ===== 集中テーブル(開始) ===== -->
      <table border="0" cellspacing="1" cellpadding="2" class="rishu-etc">
        <!-- 集中テーブルヘッダー１ -->
        <tr class="rishu">
          <th class="rishu-head" align="left" colspan="8">集中講義など</th>
        </tr>
        <!-- 集中テーブルヘッダー２ -->
        <tr>
          <td width="70" align="center" class="rishu-etc-head">曜日</td>
          <td width="70" align="center" class="rishu-etc-head">時限</td>
          <td width="100" align="center" class="rishu-etc-head">時間割コード</td>
          <td width="60" align="center" class="rishu-etc-head">警告</td>
          <td width="200" align="center" class="rishu-etc-head">開講科目名   </td>
          <td width="120" align="center" class="rishu-etc-head">担当教員名</td>
          <td width="100" align="center" class="rishu-etc-head">教室名</td>
          <td width="100" align="center" class="rishu-etc-head"></td>
        </tr>
        <!-- 集中テーブルデータ -->
        <!-- 集中講義あり/なしで表示切替(開始) -->
        
          
            <!-- 集中講義ありのとき -->
            <!-- 集中講義の件数分ループ(開始) -->
            
              <!-- 開講区分コード&取消有無で背景色を設定(開始) -->
              
                
                
                
                  
                  
                
              
              <!-- 開講区分コード&取消有無で背景色を設定(終了) -->
              <!-- 更新可/不可で表示切替(開始) -->
              
              
                  
                  
                     
                        
                        
                        
                    
                 
              
              
                  
                  
                      
                      
                      
                  
              
              <!-- 更新可/不可で表示切替(終了) -->
              <tr bgcolor="#ffffcc" onMouseOut="TDMouseOutC(this, '#ffffcc')" onMouseOver="TDMouseOverC(this, '#cc99cc')" >
                <td align="center">その他</td>
                
                  
                    <td align="center">その他</td>
                  
                  
                
                
                <td align="center">
                    195001
                </td>
                
                <td align="center"></td>
                
                <td>実践英語（e-learning入門）</td>
                
                <td>小口　一郎</td>
                
                <td align="center"></td>
                <td align="left">
                    
                      
                      
                        
                        <a href="" onclick="return syllabusRefer('2023','14','195001');"><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_syllabus.png"></a>
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                      
                    
                </td>
              </tr>
            
            <!-- 集中講義の件数分ループ(終了) -->
          
          
        
        <!-- 集中講義あり/なしで表示切替(終了) -->
      </table>
      <!-- ===== 集中テーブル(終了) ===== -->
    </td>
  </tr>
  <tr>
    <td>　</td>
  </tr>
</table>
</p>
<!-- ===== 全体テーブル(終了) ===== -->


<p></p>
<form id="rishuReferInputForm" name="InputForm" method="post" action="/campusweb/campussquare.do">
  <input type="hidden" name="_eventId" value="input">
  <input type="hidden" name="_flowExecutionKey" value="_XXX">
  <input type="hidden" name="nendo">
  <input type="hidden" name="jikanwariShozokuCode">
  <input type="hidden" name="jikanwariCode">
  <input type="hidden" name="yobi">
  <input type="hidden" name="jigen">
  <input type="hidden" name="gakkiKubunCode" value="5">
  <input type="hidden" name="bukyokuCd" value="0902">
  <input type="hidden" name="gakuNo" value="09B23081">
  <input type="hidden" name="nenji" value="1">
  <input type="hidden" name="category">
</form>

<form id="rishuReferUpdateForm" name="DeleteForm" method="post" action="/campusweb/campussquare.do">
  <input type="hidden" name="_eventId">
  <input type="hidden" name="_flowExecutionKey" value="_XXX">
  <input type="hidden" name="nendo">
  <input type="hidden" name="jikanwariShozokuCode">
  <input type="hidden" name="jikanwariCode">
  <input type="hidden" name="yobi">
  <input type="hidden" name="jigen">
</form>

<!-- テキスト出力Form -->
<table border="0">
<tbody>
    <tr>
        <td valign="top">
            
                <form id="rishuReferForm" name="OutputForm" method="post" action="/campusweb/campussquare.do">
                    <input type="hidden" name="_eventId">
                    <input type="hidden" name="_flowExecutionKey" value="_XXX">
                    <input type="button" onclick="return output('pdfOutput');" style="width:100px;" value=" PDF出力 ">　履修状況をPDFファイルに出力する場合に使用します<br>
                    <p></p>
                    <input type="button" onclick="return output('output');" style="width:100px;" value=" テキスト出力 ">　履修状況をテキストファイルに出力する場合に使用します
                </form>
            
        </td>
        <td width="50"><br></td>
        <td>
            <table border="0" cellpadding="0" cellspacing="0">
                <tbody>
                    <tr><td><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_syllabus.png">シラバス参照</td>
                    <tr><td><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_fukupro.png">副専攻・高度副プログラム</td>
                    <tr><td><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_gymnastics.png">（平成30年度以前入学者のみ）高度教養教育科目（旧知のジムナスティックス）</td>
                    <tr><td><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_leading.png">リーディングプログラム関係科目</td>
                    <tr height="23"><td><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_kodokyoyo.png">高度教養教育科目</td>
                    <tr height="23"><td><img height="20" width="20" style="vertical-align:middle;" src="/campusweb/theme/rishu/image/ico_kodokokusai.png">高度国際性涵養教育科目</td>
                </tbody>
            </table>
        </td>
    </tr>
    <tr>
        <td><br></td>
        <td><br></td>
        <td>
            <b>警告凡例</b><br>
            <table bgcolor="#e0e0e0" border="1" cellpadding="3" cellspacing="0">
                
                
                <tr>
                    
                    <td width="60">要件外</td>
                    
                    <td width="150">卒業要件外科目</td>
                </tr>
                
            </table>
        </td>
    </tr>

    <tr>
        <td><br></td>
        <td><br></td>
        <td>
            <b>背景色凡例</b><br>
            <table border="1" cellpadding="3" cellspacing="0">
                <tbody>
                    <tr bgcolor="#dddddd"><td width="150">未登録</td></tr>
                    <tr bgcolor="#ffffcc"><td>通常科目</td></tr>
                    <tr bgcolor="#ffcc99"><td>通年・年度またがり科目</td></tr>
                    <tr bgcolor="#ffc0cb"><td>取消科目</td></tr>
                </tbody>
            </table>
        </td>
    </tr>
</tbody>
</table>

<p></p>

  
  
    
    
  


<p></p>









    
    
    
    
        
    
    
    
    
    








</body>
</html>
"""
        val res = parseTimeTable(html)
        Logger.info(this::class.simpleName, res)
        assertNotNull(res)
    }
}