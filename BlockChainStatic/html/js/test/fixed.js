$(function() {
  $('.fixed-div .head,.fixed-div .tail').css('color', '#ffffff');

  $('.fixed-div .body').css('margin-top', $('.fixed-div .head').height());
  $('.fixed-div .body').css('margin-bottom', $('.fixed-div .tail').height());
  
  //下面是动态变化  默认不需要  如果要开启必须导入jquery-resize.js
  $('.fixed-div .head,.fixed-div .tail').resize(function() {
    $('.fixed-div .body').css('margin-top', $('.fixed-div .head').height());
    $('.fixed-div .body').css('margin-bottom', $('.fixed-div .tail').height());
  });

  setTimeout(function() {
    $('.fixed-div .head,.fixed-div .tail').append('<br/>添加的内容');
  }, 11000);
});
