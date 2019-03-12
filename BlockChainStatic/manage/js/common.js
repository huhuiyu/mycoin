$(function() {
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
});
