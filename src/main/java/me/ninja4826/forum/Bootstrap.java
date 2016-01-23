package me.ninja4826.forum;

import static spark.Spark.before;
import static spark.Spark.halt;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import me.ninja4826.forum.controller.SuperController;
import me.ninja4826.forum.controller.v1.UserController;
import me.ninja4826.forum.util.Props;
import me.ninja4826.forum.util.Util;

public class Bootstrap {
	
	private static String CLIENT_SECRET;
	private static String IP_ADDRESS;
	private static int PORT;
	
	public static void run() throws Exception {
		CLIENT_SECRET = Props.getProp("jwtSecret");
		IP_ADDRESS = Props.getProp("webAddress");
		PORT = Integer.parseInt(Props.getProp("webPort"));
		ipAddress(IP_ADDRESS);
		port(PORT);
		staticFileLocation("/public");
		
		before("/api/v1/*", (req, res) -> {
			res.header("Content-Type", "application/json");
		});
		
		before("/api/v1/auth/*", (req, res) -> {
			String method = req.requestMethod();
			if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")) {
				String token = req.headers("x-access-token");
				try {
					Claims claims = Jwts.parser()
							.setSigningKey(DatatypeConverter.parseBase64Binary(CLIENT_SECRET))
							.parseClaimsJws(token).getBody();
					req.attribute("subject", claims.getSubject());
					req.attribute("role", claims.get("rol"));
					req.attribute("uid", claims.get("uid"));
				} catch (ClaimJwtException e) {
					halt(401, Util.jsonError("User Unauthorized"));
				}
			}
		});
		
		UserController userController = new UserController();
		
		SuperController.addController(userController);
		SuperController.setupEndpoints();
	}
}
