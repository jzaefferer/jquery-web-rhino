load(request.realPath + "/blog.js");
window.document = new DOMDocument(request.realPath + "/" + request.page + ".html");

importPackage(Packages.de.bassistance.blog.domain);
var blog = new BlogService().getBlog();
Page.header();
Page.categories(blog.categories);
Page.posts(blog.recentPosts.toArray());
Page.sidebar(blog.posts.toArray());
Page.bottomNavigation(blog);

$().print();