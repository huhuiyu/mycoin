$(function () {
  /* 处理上下浮动 */
  console.log($('.div-main .div-head'));
  console.log($('.div-main .div-body'));

  function resizeleft() {
    var bodyheight = $(document.body).height();
    var topheight = 0;
    var bottomheight = 0;

    if (
      $('.div-main .div-head').length > 0 &&
      $('.div-main .div-body').length > 0
    ) {
      topheight = $('.div-main .div-head').height();
    }

    $('.div-main .left-div,.div-main .right-div').css(
      'height',
      bodyheight - topheight - bottomheight + 'px'
    );

    $('.div-main .left-div,.div-main .right-div').css(
      'min-height',
      bodyheight - topheight - bottomheight + 'px'
    );

    $('.div-main .right-div').css(
      'max-height',
      bodyheight - topheight - bottomheight + 'px'
    );

    console.log(
      bodyheight,
      topheight,
      bottomheight,
      $('.div-main .left-div').height()
    );
  }

  //下面是动态变化  默认不需要  如果要开启必须导入jquery-resize.js
  $(document.body).resize(resizeleft);

  resizeleft();
  var pathname = location.pathname.replace('/manage', '');

  console.log('!@#$%^^&^**(*)_+(&^><>?:"{/--||],-...===>', pathname);
  var pageinfos = {
    '/main.html': {
      my: '#yonghuguanli',
      other: '#jiaoyiguanli,#peizhiguanli,#daishifangbi,#gonggaofabu'
    },
    '/jymain.html': {
      my: '#jiaoyiguanli',
      other: '#yonghuguanli,#peizhiguanli,#daishifangbi,#gonggaofabu'
    },
    '/pzmain.html': {
      my: '#peizhiguanli',
      other: '#yonghuguanli,#jiaoyiguanli,#daishifangbi,#gonggaofabu'
    },
    '/ggmain.html': {
      my: '#gonggaofabu',
      other: '#yonghuguanli,#jiaoyiguanli,#peizhiguanli,#daishifangbi'
    },
    '/dsmbmain.html': {
      my: '#daishifangbi',
      other: '#yonghuguanli,#jiaoyiguanli,#peizhiguanli,#gonggaofabu'
    }
  };

  $(pageinfos[pathname].my)
    .css('cursor', 'pointer')
    .css('color', ' #000000')
    .css('background-color', ' rgb(213, 245, 255)');

  $(pageinfos[pathname].other)
    .mouseover(function () {
      $(this)
        .css('cursor', 'pointer')
        .css('color', ' #000000')
        .css('background-color', ' rgb(213, 245, 255)');
    })
    .mouseout(function () {
      $(this)
        .css('cursor', 'pointer')
        .css('color', '')
        .css('background-color', '');
    });

  //用户管理跳转
  $('#yonghuguanli').click(function () {
    location = 'main.html';
  });
  //交易管理跳转
  $('#jiaoyiguanli').click(function () {
    location = 'jymain.html';
  });
  //待释放币跳转
  $('#daishifangbi').click(function () {
    location = 'dsmbmain.html';
  });
  //配置管理跳转
  $('#peizhiguanli').click(function () {
    location = 'pzmain.html';
  });
  $('#gonggaofabu').click(function () {
    location = 'ggmain.html';
  });
});