$(function() {
  $('#btnback').click(function() {
    location = 'gerenziliao.html';
  });

  $('#btntijiao').click(function() {
    mydialog.showConfirm(
      '是否将登录密码修改为：<br>' + $('#txtQueRenpwd').val(),
      '确认',
      function() {
        sendData();
      }
    );
  });
  function sendData(){
  console.log('in xgjymm.html');
  var newpwd = dataService.trim($('#txtNewPassword').val());
  var qrpwd = dataService.trim($('#txtQueRenpwd').val());
  if (newpwd.length != 6) {
    $('#txtNewPassword').focus();
    mydialog.showAlert('密码必须是六位数', '信息');
    return;
  }

  if (qrpwd != newpwd) {
    $('#txtQueRenpwd').focus();
    mydialog.showAlert('两次密码必须一致', '信息');
    return;
  }

  mydialog.showWait('修改中...', '等候');
  dataService.send(
    '/user/modifyPaypwd',
    {
      'user.paypwd': newpwd
    },
    function(data) {
      mydialog.hideWait();
      if (data.success) {
        mydialog.showAlert(data.message, '信息', function() {
          location = 'xgjymm.html';
        });

        return;
      }
      mydialog.showAlert(data.message, '信息');
    }
  );
}
});
