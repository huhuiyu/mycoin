$(function() {
  $('#btnyr').click(function() {
    location = 'debi.html';
  });
  function query() {
    mydialog.showWait('查询中。。。', '等待');
    dataService.send('/user/queryUserInfoByKey', {}, function(data) {
      mydialog.hideWait();
      console.log(data);
      //'请在浏览器中打开： http://dcoin.ttxiyi.cn:8000/android/share.html?blockid='
      var shareUrl = data.loginUser.tokenId;
      $('#txtShareUrl').html(shareUrl);
      if (data.dataMap.userInfo) {
        $('#tokenId').html(data.loginUser.tokenId);
      }
      return;
     
    });
  }
  dataService.copyContent(
    'spShare',
    'txtShareUrl',
    function() {
      $('#divShareUrl').hide();
      mydialog.showAlert('复制成功', '信息');
    },
    function() {
      $('#divShareUrl').show();
    }
  );
  query();
});
