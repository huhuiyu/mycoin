$(function() {
  $('#btnwodetuandui').click(function() {
    location = 'wodetuandui.html';
  });
  $('#btnshiming').click(function() {
    location = 'shimingrenzheng.html';
  });

  $('#btnzaixiankefu').click(function() {
    location = 'zaixiankefu.html';
  });

  $('#btnguanyuwomen').click(function() {
    location = 'guanyuwomen.html';
  });

  $('#btnwoyaotuiguang').click(function() {
    location = 'woyaotuiguang.html';
  });

  $('#btngerenzhiliao').click(function() {
    location = 'gerenziliao.html';
  });

  $('#btnqianbao').click(function() {
    location = 'qianbao.html';
  });

  $('#btnjiaoyi').click(function() {
    location = 'jiaoyi.html';
  });

  $('#btnxitonggonggao').click(function() {
    location = 'xitonggonggao.html';
  });

  $('#btnwode').click(function() {
    location = 'wode.html';
  });

  $('#btnbizz').click(function() {
    location = 'bizengzhi.html';
  });
  $('#btndib').click(function() {
    location = 'daibi.html';
  });

  $('#btnExit').click(function() {
    mydialog.showConfirm('是否退出Fx?', '确认', function() {
      sendData();
    });
  });

  function sendData() {
    mydialog.showWait('安全退出中，请稍候...', '退出');
    dataService.send('/user/logout', {}, function(data) {
      mydialog.hideWait();
      if (data.success) {
        mydialog.showAlert(data.message, '退出', function() {
          location = 'index.html';
        });

        return;
      }
      mydialog.showAlert(data.message, '信息');
    });
  }

  $('#spCssMode').html(myutil.getCssIcon());
  //切换
  $('#divChangeCssMode').click(function() {
    if (myutil.getCssMode() == '') {
      myutil.changeCssToDark();
    } else {
      myutil.changeCssToNormal();
    }
    $('#spCssMode').html(myutil.getCssIcon());
  });
});
