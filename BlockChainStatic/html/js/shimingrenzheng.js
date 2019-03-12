$(function() {
  function query() {
    mydialog.showWait('查询中...', '等候');
    dataService.send('/user/queryUserInfoByKey', {}, function(data) {
      mydialog.hideWait();
      console.log(data);
      $('#divInfo').hide();
      $('#divForm').hide();
      if (data.dataMap.userInfo && data.dataMap.userInfo.idCard) {
        $('#spIdCard').html(data.dataMap.userInfo.idCard);
        $('#divInfo').show();
      } else {
        $('#divForm').show();
      }
    });
  }

  query();

  $('#btnback').click(function() {
    location = 'wode.html';
  });

  $('#btnback1').click(function() {
    location = 'wode.html';
  });

  $('#btntijiao').click(function() {
    var sid = dataService.trim($('#txtsid').val());
    var sname = dataService.trim($('#txtsname').val());

    mydialog.showWait('提交中...', '等候');
    dataService.send(
      '/user/util/phoneCheck',
      {
        'phoneCheck.idCard': sid,
        'phoneCheck.name': sname
      },
      function(data) {
        mydialog.hideWait();
        if (data.success) {
          mydialog.showAlert(data.message, '信息', function() {
            query();
          });

          return;
        }
        mydialog.showAlert(data.message, '信息');
      }
    );
  });
});
