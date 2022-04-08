function keypress(e){
		//Hàm dùng để ngăn người dùng nhập các ký tự khác ký tự số vào TextBox
		var keypressed = null;

		    if (window.event)
		    {
		    keypressed = window.event.keyCode; //IE
		    }
		    else
		    {
		    keypressed = e.which; //NON-IE, Standard
		    }

		    if (keypressed < 48 || keypressed > 57)
		    { //CharCode của 0 là 48 (Theo bảng mã ASCII)
		    //CharCode của 9 là 57 (Theo bảng mã ASCII)

		        if (keypressed == 8 || keypressed == 127)
		        {//Phím Delete và Phím Back
		        return;
		        }

		    return false;
		    }

		}