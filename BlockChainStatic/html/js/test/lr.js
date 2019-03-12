$(function() {
  /* 处理上下浮动 */
  console.log($('.div-main .div-head'));
  console.log($('.div-main .div-foot'));
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

    if (
      $('.div-main .div-foot').length > 0 &&
      $('.div-main .div-body').length > 0
    ) {
      bottomheight = $('.div-main .div-foot').height();
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
});
