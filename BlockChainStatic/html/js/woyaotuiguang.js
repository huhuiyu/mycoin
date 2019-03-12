$(function() {
  $('#btnback').click(function() {
    location = 'wode.html';
  });

  function query() {
    console.log('in qianbao.query');
    mydialog.showWait('查询中.......', '等待');
    dataService.send('/user/queryUserInfoByKey', {}, function(data) {
      mydialog.hideWait();
      console.log(data);
      $('#spMyId').html(data.loginUser.blockId);
      //'请在浏览器中打开： http://dcoin.ttxiyi.cn:8000/android/share.html?blockid='
      var shareUrl =
        data.dataMap.siteBaseConfig.shareUrl + data.loginUser.blockId;
      $('#txtShareUrl').html(shareUrl);
    });
  }
  // $('#divShareUrl').show();
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
