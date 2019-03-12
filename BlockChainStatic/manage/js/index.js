$(function() {
  console.log('index.js');

  function resetInfo(){
    $('#txtUsername').val('');
    $('#txtPassword').val('');
    $('#txtUsername').trigger('focus');
  }

  resetInfo();



  $('#btnLogin').click(function(){
    var username = dataService.trim($('#txtUsername').val());
    var password = dataService.trim($('#txtPassword').val());
    if (username.length == 0) {
      $('#txtUsername').focus();
      mydialog.showAlert('用户名必须填写', '信息');
      return;
    }
    if (password.length == 0) {
      $('#txtPassword').focus();
      mydialog.showAlert('密码必须填写', '信息');
      return;
    }
    mydialog.showWait('登陆中。。。', '等待');
    dataService.send(
      '/manage/admin/login',
      {
        'admin.username': username,
        'admin.password': password
      },
      function(data) {
        mydialog.hideWait();
        if (data.success) {
          location = 'main.html';
          return;
        }
        mydialog.showAlert(data.message, '信息');
      }   
    );
  });
});
