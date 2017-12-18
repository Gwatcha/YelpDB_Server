package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import RecordClasses.Restaurant;
import ca.ece.ubc.cpen221.mp5.StructuredQueriesParser.*;

public class StructuredQueriesDoer extends StructuredQueriesBaseListener {

	private Set<Restaurant> restaurants;
	private Set<Restaurant> forExitOrExpr;
	public StructuredQueriesDoer(YelpDB database) {
		// get all the restaurants...
		this.restaurants = database.getMatches("name");
		forExitOrExpr = new HashSet<Restaurant>();
	}
	
	@Override
	public void exitOrExpr(OrExprContext ctx) {
		restaurants = new HashSet<Restaurant>(forExitOrExpr);
		forExitOrExpr.clear();
	}
	
	@Override
	public void enterOrExpr(OrExprContext ctx) {
		Set<Restaurant> main = new HashSet<Restaurant>(restaurants);
		Set<Restaurant> right;
		List<Set<Restaurant>> allOrs = new LinkedList<Set<Restaurant>>();
		
		if (ctx.andExpr().size() >= 2) {
			for (AndExprContext cxt : ctx.andExpr()) {

				exitAndExpr(cxt);
				right = new HashSet<Restaurant>(restaurants);

				allOrs.add(right);
				restaurants = new HashSet<Restaurant>(main);

			}

			for (Set<Restaurant> set : allOrs) {
				restaurants.addAll(set);
			}
			
			forExitOrExpr = new HashSet<Restaurant>(restaurants);
		} else
			System.err.println("Something is wrong...");

	}

	public void exitAndExpr(AndExprContext ctx) {
//		for (AtomContext cxt : ctx.atom()) {
//			exitAtom(cxt);
//		}
	}

	public void exitAtom(AtomContext ctx) {
		if (ctx.in() != null) {

			String name = "";
			for (int i = 0; i < ctx.in().STRING().size(); i++) {
				name += ctx.in().STRING(i).getText();
				if ((i + 1) < ctx.in().STRING().size()) {
					name += ' ';
				}
			}
			
			Boolean found;
			Set<Restaurant> toRemove = new HashSet<Restaurant>();
			for (Restaurant res : restaurants) {
				found = false;
				for (String str : res.getNeighborhoods()) {
					if (str.equals(name))
						found = true;
				}

				if (!found) {
					toRemove.add(res);
				}
			}
			
			restaurants.removeAll(toRemove);
			toRemove.clear();
			
			
		}

		if (ctx.category() != null) {
			String name = ctx.category().STRING().getText();
			Boolean found;
			Set<Restaurant> toRemove = new HashSet<Restaurant>();
			for (Restaurant res : restaurants) {
				found = false;

				for (String str : res.getCategories()) {
					if (str.equals(name))
						found = true;
				}

				if (!found) {
					toRemove.add(res);
				}
			}
			restaurants.removeAll(toRemove);
			toRemove.clear();

		}

		if (ctx.rating() != null) {
			int num = Integer.parseInt(ctx.rating().NUM().getText());
			Set<Restaurant> toRemove;
			if (ctx.rating().ineq().gt() != null) {
				toRemove = new HashSet<Restaurant>();
				for (Restaurant res : restaurants) {

					if (!(res.getStars() > num))
						toRemove.add(res);
				}
				
				restaurants.removeAll(toRemove);
				toRemove.clear();
			}

			if (ctx.rating().ineq().gte() != null) {
				toRemove = new HashSet<Restaurant>();
				for (Restaurant res : restaurants) {

					if (!(res.getStars() >= num))
						toRemove.add(res);
				}
				restaurants.removeAll(toRemove);
				toRemove.clear();
			}

			if (ctx.rating().ineq().lt() != null) {
				toRemove = new HashSet<Restaurant>();
				for (Restaurant res : restaurants) {

					if (!(res.getStars() < num))
						toRemove.add(res);
				}
				restaurants.removeAll(toRemove);
				toRemove.clear();
			}

			if (ctx.rating().ineq().lte() != null) {
				toRemove = new HashSet<Restaurant>();
				for (Restaurant res : restaurants) {

					if (!(res.getStars() <= num))
						toRemove.add(res);
				}
				restaurants.removeAll(toRemove);
				toRemove.clear();
			}

			if (ctx.rating().ineq().eq() != null) {
				toRemove = new HashSet<Restaurant>();
				for (Restaurant res : restaurants) {

					if (!(res.getStars() == num))
						toRemove.add(res);
				}
				restaurants.removeAll(toRemove);
				toRemove.clear();
			}

		}

		if (ctx.price() != null) {
			int num = Integer.parseInt(ctx.price().NUM().getText());
			Set<Restaurant> toRemove;
			if (ctx.price().ineq().gt() != null) {
				toRemove = new HashSet<Restaurant>();
				for (Restaurant res : restaurants) {

					if (!(res.getPrice() > num))
						toRemove.add(res);
				}
				restaurants.removeAll(toRemove);
				toRemove.clear();
			}

			if (ctx.price().ineq().gte() != null) {
				toRemove = new HashSet<Restaurant>();
				for (Restaurant res : restaurants) {

					if (!(res.getPrice() >= num))
						toRemove.add(res);
				}
				restaurants.removeAll(toRemove);
				toRemove.clear();
			}

			if (ctx.price().ineq().lt() != null) {
				toRemove = new HashSet<Restaurant>();
				for (Restaurant res : restaurants) {

					if (!(res.getPrice() < num))
						toRemove.add(res);
				}
				restaurants.removeAll(toRemove);
				toRemove.clear();
			}

			if (ctx.price().ineq().lte() != null) {
				toRemove = new HashSet<Restaurant>();
				for (Restaurant res : restaurants) {

					if (!(res.getPrice() <= num))
						toRemove.add(res);
				}
				restaurants.removeAll(toRemove);
				toRemove.clear();
			}

			if (ctx.price().ineq().eq() != null) {
				toRemove = new HashSet<Restaurant>();
				for (Restaurant res : restaurants) {

					if (!(res.getPrice() == num))
						toRemove.add(res);
				}
				restaurants.removeAll(toRemove);
				toRemove.clear();
			}

		}

		if (ctx.name() != null) {
			String name = "";
			for (int i = 0; i < ctx.name().STRING().size(); i++) {
				name += ctx.name().STRING(i).getText();
				if ((i + 1) < ctx.name().STRING().size()) {
					name += ' ';
				}
			}
			Set<Restaurant> toRemove = new HashSet<Restaurant>();
			
			for (Restaurant res : restaurants) {

				if (!res.getName().equals(name))
					toRemove.add(res);
			}
			restaurants.removeAll(toRemove);
			toRemove.clear();

		}

		if (ctx.orWithinAtom() != null) {
			 enterOrExpr(ctx.orWithinAtom().orExpr());
		}

	}

	@Override
	public String toString() {
		String response;
		String str = "";
		String neighbourhoods;
		String categories;
		String schools;
		if (restaurants.size() > 0) {
			for (Restaurant resta : restaurants) {

				neighbourhoods = ConnectionHandler.converStringArray(resta.getNeighborhoods());
				categories = ConnectionHandler.converStringArray(resta.getCategories());
				schools = ConnectionHandler.converStringArray(resta.getSchools());

				response = "{\"open\": " + resta.getOpen() + ", \"url\": \"" + resta.getUrl() + "\", "
						+ "\"longitude\": " + resta.getLongitude() + ", \"neighborhoods\": " + "[" + neighbourhoods
						+ "], \"business_id\": " + "\"" + resta.getBusiness_id() + "\", \"name\": \"" + resta.getName()
						+ "\", " + "\"categories\": [" + categories + "], \"state\": \"" + resta.getState() + "\", "
						+ "\"type\": \"" + resta.getType() + "\", \"stars\": " + resta.getStars() + ", \"city\": \""
						+ resta.getCity() + "\", \"full_address\": \"" + resta.getFull_address() + "\", "
						+ "\"review_count\": " + resta.getReview_count() + ", " + "\"photo_url\": \""
						+ resta.getPhoto_url() + "\", " + "\"schools\": [" + schools + "], \"latitude\": "
						+ resta.getLatitude() + ", \"price\": " + resta.getPrice() + "}";
				str += response + "\n";
			}

			return str;

		} else
			return "ERR: NO_MATCH";

	}

}
