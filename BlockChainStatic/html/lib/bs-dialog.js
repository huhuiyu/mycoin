$(function() {
  var emptyfn = function() {};
  var mydialog = {};
  window.mydialog = mydialog;
  /* 等待对话框部分 */
  var waitDialog = {
    title: '等待对话框',
    cb: emptyfn,
    info: '请等待...',
    dialog: $('#wait-dialog')
  };

  waitDialog.show = function(info, title, cb) {
    waitDialog.info = info;
    waitDialog.title = title;
    cb ? (waitDialog.cb = cb) : (waitDialog.cb = emptyfn);
    $('#wait-dialog-title').html(waitDialog.title);
    $('#wait-dialog-info').html(waitDialog.info);
    waitDialog.dialog.modal('show');
  };

  waitDialog.hide = function() {
    waitDialog.dialog.modal('hide');
  };

  waitDialog.dialog.on('hidden.bs.modal', function() {
    waitDialog.cb();
  });

  mydialog.showWait = waitDialog.show;
  mydialog.hideWait = waitDialog.hide;

  /* 确定对话框部分 */
  var alertDialog = {
    title: '确定对话框',
    cb: emptyfn,
    info: '',
    dialog: $('#alert-dialog')
  };

  alertDialog.show = function(info, title, cb) {
    alertDialog.info = info;
    alertDialog.title = title;
    cb ? (alertDialog.cb = cb) : (alertDialog.cb = emptyfn);
    $('#alert-dialog-title').html(alertDialog.title);
    $('#alert-dialog-info').html(alertDialog.info);
    alertDialog.dialog.modal('show');
  };

  alertDialog.hide = function() {
    alertDialog.dialog.modal('hide');
  };

  alertDialog.dialog.on('hidden.bs.modal', function() {
    alertDialog.cb();
  });

  mydialog.showAlert = alertDialog.show;
  mydialog.hideAlert = alertDialog.hide;

  /* 确认对话框部分 */
  var confirmDialog = {
    title: '确认对话框',
    cby: emptyfn,
    cbn: emptyfn,
    cb: emptyfn,
    info: '',
    dialog: $('#confirm-dialog')
  };

  confirmDialog.show = function(info, title, cby, cbn) {
    confirmDialog.cb = emptyfn;
    confirmDialog.info = info;
    confirmDialog.title = title;
    cby ? (confirmDialog.cby = cby) : (confirmDialog.cby = emptyfn);
    cbn ? (confirmDialog.cbn = cbn) : (confirmDialog.cbn = emptyfn);
    $('#confirm-dialog-title').html(confirmDialog.title);
    $('#confirm-dialog-info').html(confirmDialog.info);
    confirmDialog.dialog.modal('show');
  };

  confirmDialog.hide = function() {
    confirmDialog.dialog.modal('hide');
  };

  confirmDialog.dialog.on('hidden.bs.modal', function() {
    confirmDialog.cb();
  });

  $('#confirm-dialog-yes').click(function() {
    confirmDialog.cb = confirmDialog.cby;
    confirmDialog.hide();
  });

  $('#confirm-dialog-no').click(function() {
    confirmDialog.cb = confirmDialog.cbn;
    confirmDialog.hide();
  });

  mydialog.showConfirm = confirmDialog.show;
  mydialog.hideConfirm = confirmDialog.hide;

  /* 自定义对话框部分 */
  var customDialog = {
    title: '自定义对话框',
    cb: emptyfn,
    parentEl: '',
    el: '',
    dialog: $('#custom-dialog')
  };

  customDialog.show = function(el, title, cb) {
    customDialog.title = title;
    cb ? (customDialog.cb = cb) : (customDialog.cb = emptyfn);
    $('#custom-dialog-title').html(customDialog.title);

    customDialog.parentEl = el.parent();
    customDialog.el = el;
    el.appendTo('#custom-dialog-body');
    el.show();
    customDialog.dialog.modal('show');
  };

  customDialog.hide = function() {
    customDialog.dialog.modal('hide');
  };

  customDialog.dialog.on('hidden.bs.modal', function() {
    customDialog.el.hide();
    customDialog.el.appendTo(customDialog.parentEl);
    customDialog.cb();
  });

  mydialog.showCustom = customDialog.show;
  mydialog.hideCustom = customDialog.hide;
});
