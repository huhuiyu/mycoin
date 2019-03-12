$(function () {
  $("#btnback").click(function () {
    location = "wode.html";
  });

  function query() {
    mydialog.showWait("正在查询中", "确认");
    dataService.send("/user/queryTeam", {}, function (data) {
      mydialog.hideWait();
      console.log(data);
      if (!data.success) {
        mydialog.showAlert(data.message, "信息");
        return;
      }

      // $("#div-sellinfos").html("");
      $.each(data.dataMap.list, function (i, v) {
        var div = $(document.createElement("div"));
        div.attr("class", "div-sellinfos");

        // var divname = $(document.createElement("div"));
        // divname.attr("class", "text-left");
        // if (v.userInfo && v.userInfo.nickname) {
        //   divname.append(v.userInfo.nickname);
        // } else {
        //   divname.append('未实名');
        // }
        // divname.html('');
        // div.append(divname);

        var divphone = $(document.createElement("div"));
        divphone.attr("class", "text-left");
        divphone.append(v.phone);
        div.append(divphone);

        var divpdate = $(document.createElement("div"));
        divpdate.attr("class", "text-left");
        divpdate.append(v.regDate.substr(0, 10));
        div.append(divpdate);




        $("#div-sellinfos").append(div);
      });
    });
  }
  query();
});