alter table ThongBao add LoaiThongBao char(1)
alter table ThongBao add HetHieuLuc char(1)
alter table ThongBao add TinhTrang char(1)
CREATE TABLE THONGBAO_VBHD
(
	thongbao_fk NUMERIC(18,0),
	 vbhd_fk NUMERIC(18,0),
	 trangthai CHAR(1)
) 
UPDATE THONGBAO SET LOAITHONGBAO =1 WHERE LOAITHONGBAO IS NULL