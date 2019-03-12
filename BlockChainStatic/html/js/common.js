$(function() {
  var appConfig = {};
  window.appConfig = appConfig;
  appConfig.version = '1.1.11';

  /* 处理上下浮动 */
  console.log($('.div-main .div-head'));
  console.log($('.div-main .div-foot'));
  console.log($('.div-main .div-body'));

  function resizefixed() {
    var bodyheight = $(document.body).height();
    var topheight = 0;
    var bottomheight = 0;

    if (
      $('.div-main .div-head').length > 0 &&
      $('.div-main .div-body').length > 0
    ) {
      $('.div-main .div-body').css(
        'padding-top',
        $('.div-main .div-head').height()
      );
      topheight = $('.div-main .div-head').height();
    }

    if (
      $('.div-main .div-foot').length > 0 &&
      $('.div-main .div-body').length > 0
    ) {
      $('.div-main .div-body').css(
        'padding-bottom',
        $('.div-main .div-foot').height()
      );
      bottomheight = $('.div-main .div-foot').height();
    }

    console.log(bodyheight, topheight, bottomheight);
  }

  //下面是动态变化  默认不需要  如果要开启必须导入jquery-resize.js
  $(document.body).resize(resizefixed);

  resizefixed();

  var cssModeKey = 'cssModeKey';
  //处理夜间模式，切换css模式
  function changeCssMode(cssMode) {
    if (cssMode != 'dark' && cssMode != '') {
      //如果没提供模式参数就直接读取本地储存中的css模式
      cssMode = localStorage.getItem(cssModeKey);
      cssMode = cssMode ? cssMode : '';
    }
    localStorage.setItem(cssModeKey, cssMode);
    //替换所有的link中href
    var links = $('link[rel=stylesheet]');
    $.each(links, function(i, v) {
      var csslink = $(v);
      var cssHref = csslink.attr('href');
      //跳过lib
      if (cssHref.substr(0, 4) == 'lib/') {
        return;
      }

      if (cssHref.substr(0, 9) == 'css/dark/' && cssMode != 'dark') {
        //dark模式
        csslink.attr('href', 'css/' + cssHref.substr(9));
      } else if (cssHref.substr(0, 9) != 'css/dark/' && cssMode == 'dark') {
        //非dark模式
        csslink.attr(
          'href',
          cssHref.substr(0, 4) + cssMode + '/' + cssHref.substr(4)
        );
      }
    });
  }

  changeCssMode();
  var darkIcon = '&#xe66a;'; //太阳
  var normalIcon = '&#xe600;'; //月亮
  window.myutil = {
    changeCssToDark: function() {
      changeCssMode('dark');
    },
    changeCssToNormal: function() {
      changeCssMode('');
    },
    getCssMode: function() {
      var cssMode = localStorage.getItem(cssModeKey);
      cssMode = cssMode ? cssMode : '';
      return cssMode;
    },
    getCssIcon: function() {
      if (this.getCssMode() == 'dark') {
        return darkIcon;
      } else {
        return normalIcon;
      }
    }
  };
});
