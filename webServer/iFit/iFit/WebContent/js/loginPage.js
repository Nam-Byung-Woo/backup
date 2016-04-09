function loginBtnHandler(e){
	var id, pwd;
	
	id=$("#idFormat").val();
	pwd =$("input[type='password']").val();
	pwd = Sha256.hash(pwd);
	
	alert("id : " + id +"\n pwd : " +pwd  );
}

function findIdHandler(){
	alert("find ID...");
}

function findPwdHandler(){
	alert("find Pwd...");
}

function joinHandler(){
	alert("join...");
}