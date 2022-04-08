/**
 * Phía font-end : - Thêm thư viện jQuery 1.7 
 * 				   - Thêm thư viện loading : <script type="text/javascript" src="../scripts/loadingv2.js"></script>
 * 				   
 * Phía back-end : - Thêm cookie đẩy về lúc download : 
 * 				Cookie fileDwnld = new Cookie("fileDownload", "true");
		    	fileDwnld.setPath("/");
		    	response.addCookie(fileDwnld);
 * Hướng dẫn cài : 
 * - Ẩn hiện 1 loading :
 * 		var l = new Loading();
    	l.render();
    	l.show();
   - Ẩn loaiding sau khi download file :
   		l.hide_with_download(Svl) 
   		( chú ý Svl là đường dẫn gốc về Servlet : ví dụ Erp_BangKeChungTuSvl
 */



class Loading {
    constructor() {
    	
    }
    render() {
        if (window.jQuery) {

            if (!this.checkExistsId('bg_loading')) {

                var div = $('<div>').attr({
                    id: 'bg_loading',
                    width: '100%',
                    height: '500'
                }).appendTo('body');

                var p1 = $('<div>').attr({
                    id: 'div_loading',
                    width: '300',
                    height: '100%'
                }).appendTo(div);
                $('<img>').attr({
                    id: 'loading1',
                    src: "../images/logosgp.png",
                    width: '387',
                    height: '177'

                }).appendTo(p1);
                $('<img>').attr({
                    id: 'Newtoyo_Lego',
                    src: '../images/loading02.gif',
                    width: '200',
                    height: '200'

                }).appendTo(p1);

                $(
                    "<style type='text/css'> #div_loading > img { float:left; z-index:200000001} "
                    + "    #div_loading {width:300px; position: absolute;margin-left: "
                    + "    auto;top:100px;margin-right: auto;left: 0;right: 0;} "
                    + "    #bg_loading{position: fixed;\r\n"
                    + "    left: 0px;\r\n" + "    top: 0px;\r\n"
                    + "    width: 100%;\r\n" + "    height: 500%; "
                    + "    background: rgba(255,255,255,0.9); "
                    + "    z-index: 100000001;\r\n"
                    + "    min-height : 1200px} </style>").appendTo("head");
            }
        }
    }
    show() {
        $('#bg_loading').show();
    }
    hide() {
        $('#bg_loading').hide();
    }
    hide_with_download(svl) {
        $.fileDownload('../../' + svl).done(function () {
        	$('#bg_loading').hide();
        }).fail(function () {
        	$('#bg_loading').hide();
        });
    }

    checkExistsId(id) {
        var element = $('#' + id);
        if (typeof (element) != undefined && typeof (element) != null && typeof (element) != 'undefined' && element.length > 0) {
            return true;
        }
        return false;
    }
}

$(document).ready(function () {
	$("head").append('<script src="../scripts/jquery.fileDownload.js"></script>');
    var l = new Loading();
    l.render();
    l.show();
    $(window).load(function () { 
    	l.hide();
    	
    });
    
    window.onbeforeunload = function(event) {
    	l.show();
	
	}
    
});



