// TODO modify load to include realPath by defaul
load(request.realPath + "/env.js");
load(request.realPath + "/jquery.js");
load(request.realPath + "/lib.js");

var DateFormat = (function() {
	// store dateformats in a closure
	var dateformat = new java.text.SimpleDateFormat("dd. MMMM yyyy");
	var timeformat = new java.text.SimpleDateFormat("HH:mm");
	// expose formatting methods
	return {
		date: function(value) {
			return "" + dateformat.format(value);
		},
		datetime: function(value) {
			return String.format("{0} um {1}",
				dateformat.format(value),
				timeformat.format(value));
		}
	};
})();

var Page = {
	header: function() {
		$("script[@src],img[@src]").contextPath("src", /^../);
		$("link[@href]").contextPath("href", /^../);
		$("#header h1 a, #navmenu a:first").attr("href", "/blog");
		$("#navmenu a:not(:first)").remove();
		$("#navcol ul:gt(1)").remove();
		$("#navcol .feedlink").attr("href", "?feed");
	},
	categories: function(categories) {
		var container = $("#navcol ul:eq(1)").empty();
		var template = String.format("<li><a href='?category={0}' title='{1}'>{2}</a></li>");
		categories.forEach(function(category) {
			print(category.bla);
			$(template(category.id, category.title, category.name)).appendTo(container);
		});
	},
	posts: function(posts) {
		$("div.entry").template(posts, function(post) {
			this.insertBefore("div.bottommeta");
			this.find(".entrymeta").html(DateFormat.date(post.date));
			this.find(".entrytitle a").html("" + post.title).attr("href", "?post=" + post.id).attr("title", "Link zu " + post.title);
			this.find(".entrybody").html("" + post.body);
		});
	},
	feedHeader: function(blog) {
		$("channel>title").text("" + blog.name);
		$("channel>link").text(".");
		$("channel>description").text("" + blog.description);
	},
	feedPosts: function(posts) {
		 $("item").template(posts, function(post, index) {
			this.appendTo("channel");
			this.find("pubDate").html("" + post.date);
			this.find("title").html("" + post.title);
			this.find("description").html("" + post.body);
			this.find("content\\:encoded").html("" + post.body);
			this.find("link").text("?post=" + post.id);
			this.find("comments").text("?post=" + post.id + "#commentlist");
			this.find("category").remove();
			post.categories.toArray().forEach(function(category) {
				$("<category>" + category.name + "</category>").insertBefore(this.find("guid"));
			}, this);
		});
	},
	post: function(post) {
		var current = $("div.entry");
		current.find("#leftmeta").html(DateFormat.datetime(post.date));
		var template = String.format("<a href='?category={0}' title='{1}'>{2}</a>");
		$("#rightmeta").list(post.categories.toArray(), ", ", function(category) {
			return template(category.id, category.title, category.name);
		});
		current.find(".single-title").html(post.title);
		current.find(".entrybody").html(post.body);
	},
	comments: function(post, comments) {
		if ( comments.length ) {
			$("#comments span:last").text(comments.length + " Kommentar" + (comments.length > 1 ? "e" : ""));
			$("#commentlist li:first").template(comments, function(comment, index) {
				this.appendTo("#commentlist");
				this.attr("id", "comment-" + index);
				if(comment.Url && comment.url.length()) {
					this.find(".commentauthor a").text("" + comment.author).attr("href", comment.url);
				} else {
					this.find(".commentauthor").text("" + comment.author);
				}
				this.find(".commentdate").text("" + DateFormat.datetime(comment.date));
				this.find(".commentbody").text("" + comment.body);
			});
		} else {
			$("#comments span:last").text("Noch keine Kommentare vorhanden");
			$("#commentlist").remove();
		}
		$("#comments a.commentlink").attr("href", "?commentfeed=" + post.id);
		$("#commentblock .comment-track a").attr("href", "?trackback=" + post.id);
		$("#commentform").attr("action", "?post=" + post.id + "#commentform");
	},
	sidebar: function(posts) {
		var container = $("#navcol ul:first").empty();
		var template = String.format("<li><a href='?post={0}' title='Beitrag {1} ansehen'>{1}</a></li>");
		$.each(posts, function(index, post) {
			$(template(post.id, post.title)).appendTo(container);
		});
	},
	topNavigation: function(blog) {
		var meta = $("div.nextprev");
		var prev = meta.find("a:first");
		var prevPost = blog.previousPost();
		var nextPost = blog.nextPost();
		if (prevPost) {
			meta.find(".prev").html(String.format("&#171; <a href='?post={0}'>{1}</a>&#160;", prevPost.id, prevPost.title));
		} else {
			meta.find(".prev").html("&#160;");
		}
		if (nextPost) {
			meta.find(".next").html(String.format("&#160;<a href='?post={0}'>{1}</a> &#187; ", nextPost.id, nextPost.title));
		} else {
			meta.find(".next").html("&#160;");
		}
	},
	bottomNavigation: function(blog) {
		var meta = $("div.bottommeta");
		var prev = meta.find("a:first");
		if ( blog.previousPage() == -1 )
			prev.remove();
		else
			prev.attr("href", "?page=" + blog.previousPage());
			
		var next = meta.find("a:last")
		switch(blog.nextPage()) {
			case -1:
			case 0: next.remove(); break;
			case 1: next.attr("href", "."); break;
			default: next.attr("href", "?page=" + blog.nextPage())
		}
	}
}