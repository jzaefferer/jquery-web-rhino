load(request.realPath + "/blog.js");
window.document = new DOMDocument(request.realPath + "/" + request.page + ".html");
importPackage(Packages.de.bassistance.blog.domain);
importPackage(Packages.com.jquery.web);
var blog = new BlogService().getBlog();

function process() {
	if(request.method == "post") {
		load(request.realPath + "/../scripts/jquery.validate.js");
		load(request.realPath + "/../scripts/validate.js");
		$("#commentform :input").each(function(index, element) {
			element.value = "" + Request.get(element.name);
		});
		var result = $("#commentform").validate($.validator.forms["#commentform"]).form();
		if(result) {
			blog.postComment();
			return;
		}
	}
	Page.header();
	Page.categories(blog.categories);
	Page.post(blog.currentPost);
	Page.comments(blog.currentPost, blog.currentPost.comments.toArray());
	Page.sidebar(blog.posts.toArray());
	Page.topNavigation(blog);
	return $().print();
}
process();