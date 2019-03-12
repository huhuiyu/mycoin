$(function() {
  $('#btnData').click(function() {
    mydialog.showWait('登陆中，请稍候...', '登陆');
    dataService.send('/', {}, function(data) {
      mydialog.hideWait();
      $('#result').html(JSON.stringify(data));
    });
  });
});
