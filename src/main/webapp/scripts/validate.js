$.validator.forms = {
	"#commentform": {
		rules: {
			author: "required",
			email: {
				required: true,
				email: true
			},
			url: "url",
			body: "required"
		}
	}
};