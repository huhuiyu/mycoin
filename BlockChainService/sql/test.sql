/*测试模拟数据*/
truncate table TbBalanceRecord;

insert into TbBalanceRecord (balance, price, auid, buid, bstatus) values(200, 190, 2, NULL, '01');
insert into TbBalanceRecord (balance, price, auid, buid, bstatus) values(199, 180, 2, 4, '04');
insert into TbBalanceRecord (balance, price, auid, buid, bstatus) values(190, 180, 1, 3, '02');
insert into TbBalanceRecord (balance, price, auid, buid, bstatus) values(345, 191, 2, 3, '03');
insert into TbBalanceRecord (balance, price, auid, buid, bstatus) values(23, 188, 3, 1, '02');

select * from TbBalanceRecord;

insert into TbUserInfo(uid,balance,freeze,paybalance)
select round(rand()*(10000000-10)+10) 'uid',round(rand()*(100-1)+1) 'balance',round(rand()*(100-1)+1) 'freeze',round(rand()*(10000-100)+100) 'paybalance' from  TbUserInfo;