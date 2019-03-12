$(function () {
  $('#spnTc').click(function () {
    $('#txtje').val('');
    $('#txtms').val('');
    mydialog.hideCustom();
  });
  $('#btnqd').click(function () {
    //校验
    if ($('#txtje').val().length > 13) {
      mydialog.showAlert('超过指定金额或输入有误,请重新输入', '错误');
      return;
    }
    //后台处理
    mydialog.showWait('发币中...', '等待');
    dataService.send(
      '/manage/paybalancerecord/addPayBalanceRecord', {
        'record.balance': $('#txtje').val(),
        'record.target': $('#selType').val(),
        'record.uid': $('#txtSbUid').val(),
        'record.info': $('#txtms').val()
      },
      function (data) {
        mydialog.hideWait();
        mydialog.showAlert(data.message, '信息');
      }
    );
  });

  var page = {
    pageNumber: 1,
    pageSize: 12
  };

  $('#btnGo').click(function () {
    page.pageNumber = $('#txtGoPage').val();
    query();
  });

  $('#btnNext').click(function () {
    //下一页
    page.pageNumber = page.pageNumber + 1;
    if (page.pageCount < 1) {
      page.pageNumber = 1;
    } else if (page.pageNumber > page.pageCount) {
      page.pageNumber = page.pageCount;
      return;
    }
    query();
  });

  $('#btnPre').click(function () {
    //上一页
    page.pageNumber = page.pageNumber - 1;
    if (page.pageNumber < 1) {
      page.pageNumber = 1;
      return;
    }
    query();
  });
  $('#btn-yhgl').click(query);

  function query() {
    mydialog.showWait('查询数据中', '等待');
    dataService.send(
      '/manage/user/queryAll', {
        'page.pageNumber': page.pageNumber,
        'page.pageSize': page.pageSize,
        'tbUser.isEnable': $('#selIsEnable').val(),
        'tbUser.phone': $('#txthyjb').val(),
        'tbUser.blockId': $('#txtC').val()
      },
      function (data) {
        mydialog.hideWait();
        console.log(data);
        //============================================================
        $('#spUser').html(data.loginUser.username);
        //===================================================
        if (!data.success) {
          mydialog.showAlert(data.message, '信息');
          return;
        }
        //统计信息
        if (data.dataMap && data.dataMap.total) {
          var spBalanceTotal = $('#spBalanceTotal');
          spBalanceTotal.html('fxcoin总数：' + data.dataMap.total.balance + ',待返总数：' + data.dataMap.total.paybalance);
        }
        console.log('分页信息：', page);
        page = data.dataMap.page;
        $('#spPageInfo').html(
          '总记录/当前页/分页总数' +
          page.total +
          '/' +
          page.pageNumber +
          '/' +
          page.pageCount
        );

        $('#tbData').html('');
        $.each(data.dataMap.list, function (i, v) {
          var tr = $(document.createElement('tr'));
          var td;

          td = $(document.createElement('td'));
          td.append(v.phone);
          tr.append(td);

          // td = $(document.createElement('td'));
          // td.append(v.userInfo.nickname);
          // tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.blockId);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.linkuser.blockId);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.userInfo.balance);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.userInfo.freeze);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.userInfo.paybalance);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.isEnable == 'y' ? '正常' : '冻结');
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.regDate);
          tr.append(td);

          td = $(document.createElement('td'));
          var span = $(document.createElement('span'));
          var spanicon = $(document.createElement('span'));
          spanicon.attr('class', 'iconfont');
          spanicon.append('&#xe842;');
          span.append(spanicon);
          span.append('冻结');
          span.attr('class', 'btn btn-default');
          span.click(function () {
            deleteIEbe(v);
          });
          td.append(span);

          span = $(document.createElement('span'));
          var spanicon = $(document.createElement('span'));
          spanicon.attr('class', 'iconfont');
          spanicon.append('&#xe71d;');
          span.append(spanicon);
          span.append('发币');
          span.attr('class', 'btn btn-default');
          span.click(function () {
            shaqian(v);
          });
          td.append(span);

          span = $(document.createElement('span'));
          var spanicon = $(document.createElement('span'));
          spanicon.attr('class', 'iconfont');
          spanicon.append('&#xe62f;');
          span.append(spanicon);
          span.append('解冻');
          span.attr('class', 'btn btn-default');
          span.click(function () {
            undeleteIEbee(v);
          });
          td.append(span);
          tr.append(td);

          $('#tbData').append(tr);
        });
      }
    );
  }

  function undeleteIEbee(iEbe) {
    console.log(iEbe);
    mydialog.showConfirm(
      '是否解冻：' + iEbe.uid + '(' + iEbe.phone + ')',
      '解冻',
      function () {
        console.log('开始解冻');
        mydialog.showWait('解冻数据处理中...', '等待');
        dataService.send(
          '/manage/user/undelete', {
            'tbUser.uid': iEbe.uid
          },
          function (data) {
            mydialog.hideWait();
            mydialog.showAlert(data.message, '信息', function () {
              query();
            });
          }
        );
      }
    );
  }

  function shaqian(sq) {
    console.log(sq);
    $('#txtSbUid').val(sq.uid);
    mydialog.showCustom($('#shaqian'), '发币', function () {});
  }

  function deleteIEbe(iEbe) {
    console.log(iEbe);
    mydialog.showConfirm(
      '是否冻结：' + iEbe.uid + '(' + iEbe.phone + ')',
      '冻结',
      function () {
        console.log('开始冻结');
        mydialog.showWait('冻结数据处理中...', '等待');
        dataService.send(
          '/manage/user/delete', {
            'tbUser.uid': iEbe.uid
          },
          function (data) {
            mydialog.hideWait();
            mydialog.showAlert(data.message, '信息', function () {
              query();
            });
          }
        );
      }
    );
  }
  //===========================================================
  $('#btnTc').click(function () {
    mydialog.showWait('安全退出中，请稍候...', '退出');
    dataService.send('/manage/admin/logout', {}, function (data) {
      mydialog.hideWait();
      if (data.success) {
        mydialog.showAlert(data.message, '退出', function () {
          location = 'index.html';
        });
        return;
      }
      mydialog.showAlert(data.message, '信息');
    });
  });
  //=================================================================
  $('#btnQc').click(function () {
    $('#txthyjb').val('');
    $('#txtC').val('');
  });
  query();
});