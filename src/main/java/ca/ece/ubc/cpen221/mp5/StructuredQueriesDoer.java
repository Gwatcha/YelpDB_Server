package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.Set;
import RecordClasses.Restaurant;
import ca.ece.ubc.cpen221.mp5.StructuredQueriesParser.*;

public class StructuredQueriesDoer extends StructuredQueriesBaseListener {

	private Set<Restaurant> restaurants;

	public StructuredQueriesDoer(YelpDB database) {
		// get all the restaurants...
		this.restaurants = database.getMatches("name");
	}

	public void exitRoot(RootContext ctx) {
	}

	public void exitExpr(ExprContext ctx) {
	}

	public void exitOrExpr(OrExprContext ctx) {
		Set<Restaurant> left = new HashSet<Restaurant>(restaurants);
		Set<Restaurant> right;

		if (ctx.andExpr().size() == 2) {
			exitAndExpr(ctx.andExpr(0));
			right = new HashSet<Restaurant>(restaurants);
			restaurants.addAll(left);
			exitAndExpr(ctx.andExpr(1));
			restaurants.addAll(right);
		} else
			System.err.println("Something is wrong...");

	}

	public void exitAndExpr(AndExprContext ctx) {
		for (AtomContext cxt : ctx.atom()) {
			exitAtom(cxt);
		}
	}

	public void exitAtom(AtomContext ctx) {
		if (ctx.in() != null) {
			String name = ctx.in().STRING().getText();
			for (Restaurant res : restaurants) {

				for (String str : res.getNeighborhoods()) {
					if (!str.equals(name))
						restaurants.remove(res);
				}
			}
		}

		if (ctx.category() != null) {
			String name = ctx.category().STRING().getText();
			for (Restaurant res : restaurants) {

				for (String str : res.getCategories()) {
					if (!str.equals(name))
						restaurants.remove(res);
				}
			}

		}

		if (ctx.rating() != null) {
			int num = Integer.parseInt(ctx.rating().NUM().getText());

			if (ctx.rating().ineq().gt() != null) {
				for (Restaurant res : restaurants) {

					if (!(res.getStars() > num))
						restaurants.remove(res);
				}
			}

			if (ctx.rating().ineq().gte() != null) {
				for (Restaurant res : restaurants) {

					if (!(res.getStars() >= num))
						restaurants.remove(res);
				}
			}

			if (ctx.rating().ineq().lt() != null) {
				for (Restaurant res : restaurants) {

					if (!(res.getStars() < num))
						restaurants.remove(res);
				}
			}

			if (ctx.rating().ineq().lte() != null) {
				for (Restaurant res : restaurants) {

					if (!(res.getStars() <= num))
						restaurants.remove(res);
				}
			}

			if (ctx.rating().ineq().eq() != null) {
				for (Restaurant res : restaurants) {

					if (!(res.getStars() == num))
						restaurants.remove(res);
				}
			}

		}

		if (ctx.price() != null) {
			int num = Integer.parseInt(ctx.price().NUM().getText());

			if (ctx.price().ineq().gt() != null) {
				for (Restaurant res : restaurants) {

					if (!(res.getPrice() > num))
						restaurants.remove(res);
				}
			}

			if (ctx.price().ineq().gte() != null) {
				for (Restaurant res : restaurants) {

					if (!(res.getPrice() >= num))
						restaurants.remove(res);
				}
			}

			if (ctx.price().ineq().lt() != null) {
				for (Restaurant res : restaurants) {

					if (!(res.getPrice() < num))
						restaurants.remove(res);
				}
			}

			if (ctx.price().ineq().lte() != null) {
				for (Restaurant res : restaurants) {

					if (!(res.getPrice() <= num))
						restaurants.remove(res);
				}
			}

			if (ctx.price().ineq().eq() != null) {
				for (Restaurant res : restaurants) {

					if (!(res.getPrice() == num))
						restaurants.remove(res);
				}
			}

		}

		if (ctx.name() != null) {
			String name = ctx.name().STRING().getText();
			for (Restaurant res : restaurants) {

				if (!res.getName().equals(name))
					restaurants.remove(res);
			}

		}

		if (ctx.orWithinAtom() != null) {
			exitOrExpr(ctx.orWithinAtom().orExpr());
		}

	}
	
	@Override
	public String toString() {
		String response;
		String str = "";
		String neighbourhoods;
		String categories;
		String schools;
		if(restaurants.size() > 0) {
		for (Restaurant resta : restaurants) {

			neighbourhoods = ConnectionHandler.converStringArray(resta.getNeighborhoods());
			categories = ConnectionHandler.converStringArray(resta.getCategories());
			schools = ConnectionHandler.converStringArray(resta.getSchools());

			response = "{\"open\": " + resta.getOpen() + ", \"url\": \"" + resta.getUrl() + "\", " + "\"longitude\": "
					+ resta.getLongitude() + ", \"neighborhoods\": " + "[" + neighbourhoods + "], \"business_id\": "
					+ "\"" + resta.getBusiness_id() + "\", \"name\": \"" + resta.getName() + "\", "
					+ "\"categories\": [" + categories + "], \"state\": \"" + resta.getState() + "\", " + "\"type\": \""
					+ resta.getType() + "\", \"stars\": " + resta.getStars() + ", \"city\": \"" + resta.getCity()
					+ "\", \"full_address\": \"" + resta.getFull_address() + "\", " + "\"review_count\": "
					+ resta.getReview_count() + ", " + "\"photo_url\": \"" + resta.getPhoto_url() + "\", "
					+ "\"schools\": [" + schools + "], \"latitude\": " + resta.getLatitude() + ", \"price\": "
					+ resta.getPrice() + "}";
			str += response + "\n";
		}
		
		return str;
		
		}
		else return "ERR: NO_MATCH";
		
		
		
	}

}
