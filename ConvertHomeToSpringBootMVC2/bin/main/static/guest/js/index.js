
function register(){
	if(document.getElementById('pass1').value == document.getElementById('pass2').value){
		document.registerForm.submit();
	}else{
		alert('암호가 틀립니다.');
	}
	
}

function registerform(){
		document.getElementById('loginbox').style.display="none";
		document.getElementById('registerform').style.display="inline";
	}
function registeracancel(){
	document.getElementById('registerform').style.display="none";
	document.getElementById('loginbox').style.display="inline";
}

function writePost(){
	document.writeForm.submit();
}

function doLogOut(){
	document.getElementById('isLogOut').value = "1"; 
	document.loginForm.submit();
}

function guestDelete(idx){
	document.getElementById('guestSignal').value = "1"; 
	document.getElementById('postNum').value = idx;
	document.guestForm.submit();
}

function guestModify(idx){
	document.getElementById('guestSignal').value = "2"; 
	document.getElementById('postNum').value = idx;
	document.guestForm.submit();
}


