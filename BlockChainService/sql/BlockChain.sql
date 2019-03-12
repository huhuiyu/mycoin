use information_schema;
drop database if exists qukuailian;
create database qukuailian default charset utf8 collate utf8_general_ci;
use qukuailian;

create table TbConfig
(
   configId int auto_increment primary key comment '配置id',
   configKey varchar(50) unique not null comment '配置的key',
   configVal varchar(1000) not null comment '配置值',
   lastupdate timestamp not null comment '最后修改时间'
)comment '配置信息表';

select * from TbConfig;

create table TbBlockIds
(
  blockId varchar(10) primary key not null comment '区块链id',
  isUsed enum('y','n') not null default 'n' comment '是否已经使用',
  created timestamp default now() not null comment '创建时间'
)comment '区块链id';

select * from TbBlockIds;

create table TbUser
(
  uid int auto_increment primary key not null comment '主键',
  phone varchar(50) unique not null comment '电话',
  tokenId varchar(50) unique not null comment '用户标志id',
  password varchar(50) not null comment '密码',
  paypwd char(6) not null comment '支付密码，6位数',
  blockId varchar(10) unique not null comment '区块链id',
  linkman int not null comment '区块链介绍人',
  isEnable enum('y','n') default 'y' not null comment '是否启用',
  regDate timestamp default now() not null
)comment '用户表';

select * from TbUser;

create table TbUserInfo
(
  uid int primary key not null comment '主键',
  wechat varchar(100) comment '用户的微信',
  alipay varchar(100) comment '用户的支付宝',
  balance decimal(14,2) comment '用户有效余额',
  freeze decimal(14,2) comment '用户冻结余额',
  paybalance decimal(14,2) comment '用户可返还余额',
  nickname varchar(100) comment '用户的昵称',
  idCard varchar(18) comment '身份证',
  vip enum('y','n') default 'n' comment '是否为会员',
  lastupdate timestamp not null comment '信息最后修改时间'
)comment '用户附加信息表';

select * from TbUserInfo;

create table TbUserPhoneCheck
(
  uid int primary key not null comment '主键',
  status varchar(50) not null comment '认证状态',
  msg varchar(50) not null comment '认证信息',
  idCard varchar(18) not null comment '身份证',
  mobile varchar(50) unique not null comment '电话',
  mobileType varchar(50) comment '电话类型',
  name varchar(50) comment '姓名',
  sex varchar(20) comment '性别',
  area varchar(200) comment '地区',
  province varchar(100) comment '省份',
  city varchar(100) comment '城市',
  prefecture varchar(100) comment '辖区',
  birthday varchar(100) comment '生日',
  addrCode varchar(100) comment '地区编码',
  lastCode varchar(10) comment '尾码',
  created timestamp default now() not null comment '信息创建时间'  
)comment '用户电话三网认证信息表';

select * from TbUserPhoneCheck;

create table TbToken
(
  token varchar(50) primary key not null comment '主键-token值',
  lastupdate timestamp not null comment '最后更新时间'
)comment '用户追踪的Token信息';

select * from TbToken;

create table TbTokenInfo
(
  token varchar(50) not null comment 'token值',
  infokey varchar(50) not null comment 'token信息键值',
  info varchar(500) not null comment 'token信息值',
  lastupdate timestamp not null comment '最后更新时间',
  unique key tokeninfo_token_infokey(infokey,token)
)comment 'token信息表';

select * from TbTokenInfo;

create table TbAdmin
(
  aid int auto_increment primary key not null comment '主键',
  username varchar(50) unique not null comment '登陆名称',
  password varchar(50) not null comment '密码',
  isEnable enum('y','n') default 'y' not null comment '是否启用',
  regDate timestamp default now() not null
)comment '后台管理员信息表';

select * from TbAdmin;

create table TbPayBalanceRecord
(
  pbrid int auto_increment primary key not null comment '主键',
  balance decimal(14,2) comment '变更金额',
  target enum('1','2') comment '变更目标，1：paybalance，2：balance',
  uid int comment '目标账号',
  aid int comment '操作账号',
  info varchar(500) comment '变更说明',
  created timestamp default now() not null  comment '记录添加时间'
)comment '发币信息记录表';

select * from TbPayBalanceRecord;

create table TbBalanceRecord
(
  brid int auto_increment primary key not null comment '主键',
  balance decimal(14,2) comment '挂单数量',
  price decimal(14,2) comment '售价',
  auid int comment '卖出账号',
  buid int comment '买入账号',
  bstatus enum('01','02','03','04','05','06') not null comment '交易状态，01：挂单，02：交易中，03：已付款，04：撤单，05：交易完成，06：后台撤单',
  brtype enum('01','02') not null comment '挂单，01：挂卖单，02：挂买单',
  info varchar(500) comment '变更说明',
  created timestamp default now() not null  comment '记录添加时间'
)comment '挂单交易信息记录表';

select * from TbBalanceRecord;

create table TbPayBalanceUpdate
(
  bruid int auto_increment primary key not null comment '主键',
  balance decimal(14,2) comment '发放金额',
  uid int comment '发放账号',
  created timestamp default now() not null  comment '记录添加时间'
)comment '待释放币发放记录表';

select * from TbPayBalanceUpdate;

create table TbSiteNotice
(
 snid int auto_increment primary key not null comment '主键',
 title varchar(50) not null comment '公告标题',
 content varchar(2000) not null comment '公告内容',
 intop enum('y','n') default 'n' not null comment '是否置顶，y:是，n：否，默认n',
 isEnable enum('y','n') default 'y' not null comment '是否启用',
 lastupdate timestamp not null comment '最后更新时间'
)comment '网站公告';

select * from TbSiteNotice;

create table TbUserBalanceRecord
(
  burid int auto_increment primary key not null comment '主键',
  uid int not null comment '资金变动用户id',
  bdir int not null comment '资金变动方向，1：增加，-1：减少',
  btype int not null comment '10：待释放币发放，11：交易购买，12：交易卖出，13：点对点转入，14：点对点转出，15：会员扣款，16：交易奖励待释放币，17：直接发放待释放币，18：直接发放币，19：复投扣款，20：复投收待释放币，21：复投上级奖励，22：交易奖励，23：交易2级奖励待释放币，24：交易3级奖励待释放币，25：复投2级奖励待释放币，26：复投3级奖励待释放币',
  balance decimal(14,2) not null comment '变动金额',
  info varchar(200) not null comment '变动说明',
  created timestamp default now() comment '变动时间'
)comment '用户账号资金变动记录';

select * from TbUserBalanceRecord;
