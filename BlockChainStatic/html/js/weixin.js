$(function() {
  $('#btnback').click(function() {
    location = 'gerenziliao.html';
  });
  function query() {
    mydialog.showWait('查询中...', '等候');
    dataService.send('/user/queryUserInfoByKey', {}, function(data) {
      mydialog.hideWait();
      console.log(data);
      if (data.dataMap.userInfo && data.dataMap.userInfo.wechat) {
        $('#txtweixin').val(data.dataMap.userInfo.wechat);
      }
      return;
    });
  }

  $('#btntijiao').click(function() {
    mydialog.showConfirm(
      '是否将微信修改为：<br>' + $('#txtweixin').val(),
      '确认',
      function() {
        sendData();
      }
    );
  });

  function sendData() {
    var weixin = dataService.trim($('#txtweixin').val());
    mydialog.showWait('提交中。。。', '等待');
    dataService.send(
      '/user/modifyWechat',
      {
        'userInfo.wechat': weixin
      },
      function(data) {
        mydialog.hideWait();
        mydialog.showAlert(data.message, '信息');
      }
    );
  }

  query();
});
