$(function () {
  console.log('in qianbao.js');
  $('#jfen').click(function () {
    location = 'debi.html';
  });

  $('#btndh').click(function () {
    location = 'bduihuan.html';
  });
  $('#btnhui').click(function () {
    location = 'huiyuan.html';
  });
  $('#btnhoot').click(function () {
    location = 'zhangdang.html';
  });

  $('#btnqianbao').click(function () {
    location = 'qianbao.html';
  });

  $('#btnjiaoyi').click(function () {
    location = 'jiaoyi.html';
  });

  $('#btnwode').click(function () {
    location = 'wode.html';
  });

  $('#eos').click(function () {
    location = 'EOS.html';
  });

  $('#ltc').click(function () {
    location = 'LTC.html';
  });

  $('#eth').click(function () {
    location = 'ETH.html';
  });

  $('#btc').click(function () {
    location = 'BTC.html';
  });

  $('#cny').click(function () {
    location = 'CNY.html';
  });

  $('#spcoin').click(function () {
    location = 'SPCOIN.html';
  });
  // $('#btnbizz').click(function() {
  //   location = 'bizengzhi.html';
  // });
  // $('#btndib').click(function() {
  //   location = 'daibi.html';
  // });

  function query() {
    console.log('in qianbao.query');
    mydialog.showWait('查询中.......', '等待');
    dataService.send('/user/queryUserInfoByKey', {}, function (data) {
      mydialog.hideWait();
      console.log(data);
      if (data.dataMap.userInfo) {
        $('#divUserInfo').html(data.loginUser.phone);
        $('#jdID').html(data.loginUser.blockId);
        $('#spLinkman').html(data.loginUser.linkuser.blockId);
        // $('#tokenId').html(data.loginUser.tokenId);
        // $('#divdaisifang').html(data.dataMap.userInfo.paybalance);
      }

      return;
    });
  }

  query();
});