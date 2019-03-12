$(function() {
  //===========================================================
  $('#btnTc').click(function() {
    mydialog.showWait('安全退出中，请稍候...', '退出');
    dataService.send('/manage/admin/logout', {}, function(data) {
      mydialog.hideWait();
      if (data.success) {
        mydialog.showAlert(data.message, '退出', function() {
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

  $('#btnGo').click(function() {
    page.pageNumber = $('#txtGoPage').val();
    query();
  });

  $('#btnNext').click(function() {
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

  $('#btnPre').click(function() {
    //上一页
    page.pageNumber = page.pageNumber - 1;
    if (page.pageNumber < 1) {
      page.pageNumber = 1;
      return;
    }
    query();
  });

  $('#btn-dsfb').click(query);
  var infos = {
    '10': '待释放币发放',
    '11': '交易购买',
    '12': '交易卖出',
    '13': '点对点转入',
    '14': '点对点转出',
    '15': '会员扣款',
    '16': '交易奖励待释放币',
    '17': '直接发放待释放币',
    '18': '直接发放币',
    '19': '复投扣款',
    '20': '复投获得待释放币',
    '21': '复投上级奖励'

  };
  function query() {
    mydialog.showWait('查询数据中', '等待');
    dataService.send(
      '/manage/userbalancerecord/queryAll',
      {
        'page.pageNumber': page.pageNumber,
        'page.pageSize': page.pageSize,
        'record.user.blockId': $('#txtIIDD').val(),
        'record.user.phone': $('#txtPone').val()
      },
      function(data) {
        mydialog.hideWait();
        console.log(data);
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
        $.each(data.dataMap.list, function(i, v) {
          var tr = $(document.createElement('tr'));
          var td;

          td = $(document.createElement('td'));
          td.append(v.balance);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.bdir == '1' ? '增加' : '减少');
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(infos[v.btype]);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.created);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.info);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.user.blockId);
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.user.phone);
          tr.append(td);

          $('#tbData').append(tr);
        });
      }
    );
  }
  query();
});
