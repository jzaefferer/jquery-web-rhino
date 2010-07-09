$(function() {
	$('a, input, label, #docs span.tooltip').Tooltip({
		delay: 0,
		track: true,
		showURL: false,
		showBody: "; "
	});
	$.fn.search = function() {
		return this.focus(function() {
			if( this.value == this.defaultValue ) {
				this.value = "";
			}
		}).blur(function() {
			if( !this.value.length ) {
				this.value = this.defaultValue;
			}
		});
	};
	$("#s").search();
	$.each($.validator.forms, function(selector, settings) {
		$(selector).validate(settings);
	});
});