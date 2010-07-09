package de.bassistance.blog.domain;

import java.util.Calendar;
import java.util.Date;


public class BlogService {

	private static Blog blog;

	static {
		blog = new Blog();
		blog.setName("bassistance.de");
		blog.setDescription("Bass, Geeks and Rock'n'Roll");
		blog.addBlogEntry(new Post("1", "Random post #1", "Nothing noteworthy.", date(2007, 3, 11)));
		blog.addBlogEntry(new Post("2", "Random post #2", "Nothing noteworthy.", date(2007, 4, 3)));
		blog.addBlogEntry(new Post("3", "Random post #3", "Nothing noteworthy.", date(2007, 5, 23)));
		blog.addBlogEntry(new Post("4", "Random post #4", "Nothing noteworthy.", date(2007, 6, 3)));
		Post surface = new Post(
				"5",
				"Surface Computing",
				"<blockquote>One day, your computer will be a big ass table!</blockquote><p>Eine amüante Parodie auf Microsoft Surface:</p>",
				date(2007, 7, 10));
		surface.addComment(new Comment("Mr. Johnson", "We have to speak about that, in private!", date(2007, 7, 11)));
		surface.addCategory(new Category("4", "Big ass", "Runs on SCUMM VM"));
		blog.addBlogEntry(surface);
		blog.addBlogEntry(new Post(
						"6",
						" Er bockt, sie bockt, es bockt, ich verbocke.",
						"In Anlehnung an Es bockt!, hier ein kurzer Schadensbericht zu meinem eigenen Verbocken. Das ich in letzter Zeit ein paar Probleme mit meinem eigenen PC hatte, hat der ein oder andere im engeren Umfeld ja mitgekommen. Angefangen hat es mit meinem Versuch, den Rechner leiser zu bekommen, durch entfernen drei alter HDDs und zweier CD-Laufwerke, zu ersetzen durch zwei fixe und leise SATA-Laufwerke (400GB HDD, Samsung DVD Brenner).<p>Das entfernen der eklig lauten 40er-Platte hätte eigentlich schon gereicht, und eine Menge Frust vermieden.</p>",
						date(2007, 7, 11)));
		Post whyOS = new Post(
				"7",
				"Why open source?",
				"<p>While glancing over Dr. Dobb's article Getting Started With jQuery I read this:</p><blockquote>Whether the motivation behind making their labors freely available is a matter of seeking recognition, resume building, free advertising for other services, bragging rights, or just plain old-fashioned altruism, we can gratefully take advantage of these tools. jQuery is one such tool.</blockquote><p>Cool.</p>",
				date(2007, 7, 13));
		blog.addBlogEntry(whyOS);
		whyOS.addCategory(new Category("1", "Allgemein", "Was sonst nirgends reinpasst"));
		whyOS.addCategory(new Category("2", "Lesenswertes", "Am Anfang war das Wort..."));
		whyOS.addCategory(new Category("3", "jQuery", "New Wave JavaScript"));
		whyOS.addComment(new Comment("Hike Halsup", "Open-source changed my life!", date(2007, 7, 13)));
		whyOS.addComment(new Comment("anonymous user", "http://google.com", "mail@google.com", "No way!", date(2007, 7, 24)));
	}

	private static Date date(int year, int month, int day) {
		Calendar result = Calendar.getInstance();
		result.set(Calendar.YEAR, year);
		result.set(Calendar.MONTH, month - 1);
		result.set(Calendar.DATE, day);
		return result.getTime();
	}

	/**
	 * Gets the blog.
	 * 
	 * @return a Blog instance
	 */
	public Blog getBlog() {
		return blog;
	}
	
}
