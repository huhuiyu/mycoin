$(function() {
  $('#btnreturn').click(function() {
    location = 'gerenziliao.html';
  });

  function query() {
    mydialog.showWait('查询中...', '等候');
    dataService.send('/user/queryUserInfoByKey', {}, function(data) {
      mydialog.hideWait();
      console.log(data);
      if (data.dataMap.userInfo && data.dataMap.userInfo.alipay) {
        $('#txtzhifubao').val(data.dataMap.userInfo.alipay);
      }
      return;
    });
  }
  $('#btntijiao').click(function() {
    mydialog.showConfirm(
      '是否将支付宝修改为：<br>' + $('#txtzhifubao').val(),
      '确认',
      function() {
        sendData();
      }
    );
  });

  function sendData() {
    var zhifubao = dataService.trim($('#txtzhifubao').val());
    mydialog.showWait('提交中。。。', '等待');
    dataService.send(
      '/user/modifyAlipay',
      {
        'userInfo.alipay': zhifubao
      },
      function(data) {
        mydialog.hideWait();
        mydialog.showAlert(data.message, '信息');
      }
    );
  }
  query();
});
