function focusFun(e) {
	if ((e.ctrlKey && e.keyCode == 13) || (e.ctrlKey && e.keyCode == 10)) {
		rc();
	} else if (e.keyCode == 13) {
		document.getElementById('form1:inBarcode').focus();
	}
}