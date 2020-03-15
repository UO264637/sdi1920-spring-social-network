function validate() {
	if ($('input[name="username"]').value() == ""
			&& $('input[name="password"]').value() == "") {
		alert("Username and password are required");
		$('input[name="email"]').focus();
		return false;
	}
	if ($('input[name="username"]').value() == "") {
		alert("Username is required");
		$('input[name="username"]').focus();
		return false;
	}
	if ($('input[name="username"]').value() == "") {
		alert("Password is required");
		$('input[name="username"]').focus();
		return false;
	}
}