var projectName = property.getProjectPath();
var tableId = null;
var main = {

  init: function () {
    if (null == tableId){
      tableId = property.getTimeJson();
    }
    this.initTable();
    this.tabBind();
    this.getPageData();
    $(".quxiao").click(function () {
      $('.myRefresh', window.parent.document).click();
    })

  },
  getPageData: function () {

  },

  initTable: function () {

      layui.use(['upload','element','form'], function(){
          var $ = layui.jquery
              ,upload = layui.upload,element = layui.element;
          var form  = layui.form;
          var demoListView = $('#demoList')
              ,uploadListIns = upload.render({
              elem: '#upload'
              ,url: property.getProjectPath()+"attach/upload.do?tableName="+"copyright"+"&tableId="+tableId
              ,accept: 'file'
              ,multiple: true
              ,auto: true
              ,xhr:xhrOnProgress
              ,progress:function(index,value){//上传进度回调 value进度值
                  var tr = demoListView.find('#upload-'+ index)
                      ,tds = tr.children();
                  tds.eq(1).html('<span style="color: red;">正在上传</span>');
                  element.progress('progressBar'+index, value+'%')//设置页面进度条
              }
              // ,bindAction: '#testListAction'
              ,choose: function(obj){
                  var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                  //读取本地文件
                  obj.preview(function(index, file, result){
                      /* var tr = $(['<tr id="upload-'+ index +'">'
                           ,'<td>'+ file.name +'</td>'
                           ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                           ,'<td>等待上传</td>'
                           ,'<td>'
                           ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                           ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                           ,'</td>'
                           ,'</tr>'].join(''));*/
                      var tr=$([
                      "                                    <div class='upLeft' id='upload-"+index+"'>" +
                      "                                        <span class=\"fileName\">"+file.name+"</span>" +
                      "                                        <span class=\"fileState\">准备上传</span>" +
                      "                                    </div>" +
                      "                                    <div class=\"upRight\">" +
                      "                                        <div class='layui-progress layui-col-md8 layui-col-sm8' lay-showPercent='yes' lay-filter='progressBar"+index+"'>" +
                      "                                            <div class=\"layui-progress-bar layui-progress-big layui-bg-red\" lay-percent=\"30%\">" +
                      '<span class="layui-progress-text">'+'0%'+'</span>'+'</div>' +
                      "                                        </div>" +
                      "                                        <a href=\"javascript:void (0);\" style='margin-left:15px;width:30px;' class=\"layui-col-md1 layui-col-sm1 layui-hide demo-reload\">重传</a>" +
                      "                                        <a href=\"javascript:void (0);\" style='margin-left:15px;width:30px;' class=\"layui-col-md1 layui-col-sm1 demo-cancel\">取消</a>" +
                      "                                    </div>" ].join(''));
                      //单个重传
                      tr.find('.demo-reload').on('click', function(){
                          obj.upload(index, file);
                      });
                      demoListView.append(tr);
                  });
              }
              ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                  //layer.load(); //上传loading
              }
              ,done: function(res, index, upload){
                  if(res.code == 1){ //上传成功
                      attCount = attCount+1;
                      var tr = demoListView.find('#upload-'+ index)
                          ,tds = tr.children();
                      tds.eq(1).html('<span style="color: #5FB878;">上传成功</span>');
                      //tds.eq(2).html(''); //清空操作
                      tr.siblings(".upRight").find(".demo-reload").remove();
                      tr.siblings(".upRight").find(".demo-cancel").addClass("demo-delete");
                      tr.siblings(".upRight").find(".demo-cancel").text("删除");
                      tr.siblings(".upRight").find(".demo-cancel").attr("data-id",res.data.id);
                      tr.siblings(".upRight").find(".demo-delete").removeClass("demo-cancel");

                      tr.siblings(".upRight").find(".layui-bg-red").addClass("layui-bg-green");
                      tr.siblings(".upRight").find(".layui-bg-green").removeClass("layui-bg-red");
                      return delete this.files[index]; //删除文件队列已经上传成功的文件
                  }else {
                      var tr = demoListView.find('#upload-'+ index)
                          ,tds = tr.children();
                      tds.eq(1).html('<span style="color: #5FB878;">'+res.msg+'</span>');
                      //tds.eq(2).html(''); //清空操作
                      //tr.siblings(".upRight").find(".demo-reload").remove();
                      //tr.siblings(".upRight").find(".demo-delete").remove();
                      // return delete this.files[index]; //删除文件队列已经上传成功的文件
                  }
                  this.error(index, upload);
              }
              ,error: function(index, upload){
                  var tr = demoListView.find('#upload-'+ index)
                  //     ,tds = tr.children();
                  // tds.eq(1).html('<span style="color: #FF5722;">上传失败</span>');
                  tr.siblings(".upRight").find('.demo-reload').removeClass('layui-hide'); //显示重传
              }
          });

      });
      //删除附件
      $('#demoList').on('click','.demo-delete',function(){
          var attId = $(this).attr("data-id");
          // delete files[index]; //删除对应的文件
          $(this).parent().parent().remove();
          deleteAttachment(attId);
          attCount = attCount-1;
          // uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
      });
      //取消上传
      $('#demoList').on('click','.demo-cancel',function(){
          var attId = $(this).attr("data-id");
          // delete files[index]; //删除对应的文件
          $(this).parent().parent().remove();
          // uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
      });

  },

  tabBind: function () {
    //导出函数
    $(".layui-btn-green").on({
      'click': function () {
        return false
      }
    })
    //时间切换
    $(".searchBtn").on({
      'click': function () {
        var index = $(this).index();
        if ($(this).hasClass('active')) {
          return false
        }
        if (index == 1) {
          $(".searchBtn").removeClass("active");
          $(".searchBtn").eq(0).addClass("active");
        } else {
          $(".searchBtn").removeClass("active");
          $(".searchBtn").eq(1).addClass("active");
        }

        return false
      }
    })
  }
}
main.init();
//日历切换
function cDayFunc() {
  main.initTable()
}
function xhrOnProgress(fun) {
  xhrOnProgress.onprogress = fun;
  return function () {
    var xhr = $.ajaxSettings.xhr();
    if (typeof xhrOnProgress.onprogress !== 'function') {
      return xhr;
    }
    if (xhrOnProgress.onprogress && xhr.upload) {
      xhr.upload.onprogress = xhrOnProgress.onprogress;
    }
    return xhr;
  }
}





