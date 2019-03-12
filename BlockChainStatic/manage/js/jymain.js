$(function () {
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

  var statusinfo = {
    '01': '挂单',
    '02': '交易中',
    '03': '已付款',
    '04': '撤单',
    '05': '交易完成',
    '06': '后台撤单'
  };
  $('#btn-jygl').click(query);

  function cancelOrler(record) {
    console.log(record);
    mydialog.showConfirm(
      '是否撤销订单?',
      '撤单',
      function () {
        console.log('开始撤单');
        mydialog.showWait('撤单数据处理中...', '等待');
        dataService.send(
          '/manage/balanceRecord/cancel', {
            'record.brid': record.brid
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

  function query() {
    mydialog.showWait('查询数据中', '等待');
    dataService.send(
      '/manage/balanceRecord/queryBalanceRecord', {
        'page.pageNumber': page.pageNumber,
        'page.pageSize': page.pageSize,
        'record.bstatus': $('#jyxinxi').val(),
        'record.auser.blockId': $('#idzhiwang').val()
      },
      function (data) {
        mydialog.hideWait();
        console.log(data);
        //=============================================================

        $('#spUser').html(data.loginUser.username);
        //===================================================
        if (!data.success) {
          mydialog.showAlert(data.message, '信息');
          return;
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
          td.append(v.balance);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.price);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.auser && v.auser.blockId ? v.auser.blockId : '');
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.buser && v.buser.blockId ? v.buser.blockId : '');
          tr.append(td);

          td = $(document.createElement('td'));

          td.append(statusinfo[v.bstatus]);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.info);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.created);
          tr.append(td);

          td = $(document.createElement('td'));
          if (v.bstatus == '01') {
            var span = $(document.createElement('span'));
            var spanicon = $(document.createElement('span'));
            spanicon.attr('class', 'iconfont');
            spanicon.append('&#xe842;');
            span.append(spanicon);
            span.append('撤单');
            span.attr('class', 'btn btn-default');
            span.click(function () {
              cancelOrler(v);
            });
            td.append(span);
          }
          tr.append(td);

          $('#tbData').append(tr);
        });
      }
    );
  }

  query();
});