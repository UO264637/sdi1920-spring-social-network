$("#deleteButton").click(function() {
	var urlDelete = '/user/list/delete' + "?ids=";
	var $boxes = $('input[name=cb]:checked');
	$boxes.each(function() {
		urlDelete += $(this).attr("id") + ",";
	});
	urlDelete = urlDelete.substring(0, urlDelete.length - 1);
	$("#tableUsers").load(urlDelete);
});