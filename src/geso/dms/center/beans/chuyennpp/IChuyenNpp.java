package geso.dms.center.beans.chuyennpp;

import geso.dms.center.beans.stockintransit.IStockintransit;

public abstract interface IChuyenNpp extends IStockintransit
{
	public abstract String getNppIdFrom();

	public abstract void setNppIdFrom(String paramString);

	public abstract String getNppIdTo();

	public abstract void setNppIdTo(String paramString);

	public abstract String getMsg();

	public abstract void setMsg(String paramString);

	public abstract String getId();

	public abstract void setId(String paramString);

	public abstract String getNgayKs();

	public abstract void setNgayKs(String paramString);

	public abstract boolean TransferData();

	public abstract void closeDB();
}

