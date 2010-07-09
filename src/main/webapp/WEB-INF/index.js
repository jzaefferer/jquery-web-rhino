window.document = new DOMDocument(request.realPath + "/" + request.page + ".html");

importPackage(Packages.de.bassistance.blog.domain);
var blog = new BlogService().getBlog();
Page.header();
Page.categories(blog.categories);
Page.posts(blog.recentPosts.toArray());
Page.sidebar(blog.posts.toArray());
Page.bottomNavigation(blog);

$().print();

var classRules = {
	required: "required",
	requiredChecked: { required: "#checked:checked" }
	requiredMail: {
		required: true,
		email: true
	}
}

var rules = {};
$(":input").each(function() {
	var input = this;
	$.each($(input).attr("class").split(" "), function(i, n)) {
		if ( n in classRules ) {
			rules[input.name] = classRules[n];
		}
	});
});