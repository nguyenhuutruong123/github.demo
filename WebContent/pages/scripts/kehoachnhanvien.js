var NhaPhanPhoi = function(id, ten) {
	
	this.id = id;
	this.ten = ten;
	this.ddkds = [];
};

var QuanHuyen = function(tinhId, id, ten) {
	this.tinhId = tinhId;
	this.id = id;
	this.ten = ten;
};

var TinhThanh = function(id, ten) {

	
	this.id = id;
	this.ten = ten;
	this.quans = [];
};
var DaidienKinhdoanh = function(nppId, id, ten){
	
	this.nppId = nppId;
	this.id = id;
	this.ten = ten;
	this.tbhs = [];
};
var Tuyenbanhang = function(ddkdId,nppId, id, ten){
	this.ddkdId = ddkdId;
	this.nppId = nppId;
	this.id = id;
	this.ten = ten;
	
};