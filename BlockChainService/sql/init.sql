use qukuailian;

truncate table TbConfig;
/*默认配置数据*/
insert into TbConfig(configKey,configVal,lastupdate) values('sms_bean_info','{"product":"","domain":"","region1":"","region2":"","accesskeyid":"","accesskeysecret":"","sign":"","template":{"validateCode":""}}',now());
insert into TbConfig(configKey,configVal,lastupdate) values('phonecheck_bean_info','{"appCode":"4988f0f6061241bd969defff43666ff4","url":"http://fephone.market.alicloudapi.com/phoneCheck?idCard=%s&mobile=%s&name=%s"}',now());
insert into TbConfig(configKey,configVal,lastupdate) values('site_config','{"poundage":0.2,"payback":0.0005,"freezeHour":9,"vip":100,"userReward":1,"parentReward":0.25,"parentReward2":0.1,"parentReward3":0.15,"parentPayReward":0.1,"dayPrice":0.01,"dayMaxPrice":0.02,"minBalance":10,"maxBalance":10000,"shareUrl":"http://fxtradlngcorp.com/download/share.html?blockid=","versionUrl":"http://localhost:40000/download/version.txt","apkUrl":"http://fxtradlngcorp.com/download/android.apk","closed":"n","exchange":10,"exchangeReward":0.25,"exchangeReward2":0.1,"exchangeReward3":0.15}',now());

select * from TbConfig;

truncate table TbBlockIds;
/*默认区块链id*/
insert into TbBlockIds(blockId,isUsed) values('8888888','y');
insert into TbBlockIds(blockId,isUsed) values('3491822','n');

select * from TbBlockIds;

truncate table TbUser;
/*默认测试用户*/
insert into TbUser(phone,tokenId,password,paypwd,blockId,linkman) values('17702349425','7f4e86c19810a83b0dcb2e2a574bf060','pwd123','pwd456','8888888',1);
select * from TbUser;

truncate table TbUserInfo;
/*默认测试用户附件信息*/
insert into TbUserInfo(uid,idCard,balance,freeze,paybalance) values(1,'17702349425',0,0,0);
select * from TbUserInfo;
select u.*,ui.* from TbUser u 
inner join TbUserInfo ui on ui.uid=u.uid;

truncate table TbAdmin;
/*默认管理用户*/
insert into TbAdmin(username,password) values('adminuser','adminpwd');
select * from TbAdmin;

truncate table TbBalanceRecord;
truncate table TbPayBalanceRecord;
truncate table TbPayBalanceUpdate;
truncate table TbSiteNotice;
truncate table TbToken;
truncate table TbTokenInfo;
truncate table TbUserBalanceRecord;
truncate table TbUserPhoneCheck;
