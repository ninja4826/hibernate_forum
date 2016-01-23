package me.ninja4826.forum.controller.v1;

import java.lang.reflect.Type;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.reflect.TypeToken;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.ninja4826.forum.model.entity.UserEntity;
import me.ninja4826.forum.model.table.UserTable;
import me.ninja4826.forum.util.Props;

public class UserController extends Controller {
	
	public UserController() {
		this.post("/auth", (req, res) -> {
			Type mapType = new TypeToken<HashMap<String, String>>(){}.getType();
			HashMap<String, String> map = gson.fromJson(req.body(), mapType);
			
			if (map.containsKey("username") && map.containsKey("password")) {
				UserEntity u = UserTable.getBy("username", map.get("username"));
				
				if (u.verifyPassword(map.get("password"))) {
					HashMap<String, String> payload = new HashMap<>();
					payload.put("token", createJWT(u));
					return payload;
				}
				this.halt(403,  "Authorization failed. Invalid password.");
			}
			this.halt(401, "Authorization failed. Invalid password.");
			return null;
		});
		
		this.get("/users", (req, res) -> {
			return UserTable.getAll(false);
		});
		
		this.post("/users", (req, res) -> {
			UserEntity user = gson.fromJson(req.body(), UserEntity.class);
			
			if (user.getRole() != null && req.attribute("role") != "admin") {
				user.setRole(null);
			}
			
			user.save();
			return user;
		});
		
		this.get("/users/:id", (req, res) -> {
			return UserTable.getByID(Integer.parseInt(req.params(":id")), false);
		});
		
		this.put("/users/:id", (req, res) -> {
			res.redirect(API_CONTEXT + "/auth/users/" + req.params(":id"));
			return null;
		});
		
		this.put("/auth/users/:id", (req, res) -> {
			Type stringMap = new TypeToken<HashMap<String,String>>(){}.getType();
			HashMap<String, String> userMap = gson.fromJson(req.body(), stringMap);
			UserEntity targetUser = UserTable.getByID(Integer.parseInt(req.params("id")));
			
			if (Integer.toString(targetUser.getId()) != (String) req.attribute("uid")) {
				this.halt(403, "User does not have permission for this action.");
				return null;
			}
			
			for (Map.Entry<String, String> entry : userMap.entrySet()) {
				switch (entry.getKey())
				{
				case "username":
					targetUser.setUsername(entry.getValue());
					break;
				case "password":
					targetUser.updatePassword(entry.getValue());
				}
			}
			
			targetUser.save();
			return targetUser;
		});
		
		this.delete("/users/:id", (req, res) -> {
			res.redirect(API_CONTEXT + "/auth/users/" + req.params(":id"));
			return null;
		});
		
		this.delete("/auth/users/:id", (req, res) -> {
			UserEntity targetUser = UserTable.getByID(Integer.parseInt(req.params(":id")));
			if (targetUser == null) {
				this.halt(404, "User does not exist.");
				return null;
			}
			if (targetUser.getId() == Integer.parseInt(req.attribute("uid")) || ((String) req.attribute("role")).equals("admin")) {
				targetUser.delete();
				return null;
			}
			halt(403, "User does not have permission for this action.");
			return null;
		});
	}
	
	private static String createJWT(UserEntity subject) {
		SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		String secret = Props.getProp("jwtSecret");
		byte[] apiBytes = DatatypeConverter.parseBase64Binary(secret);
		
		Key signingKey = new SecretKeySpec(apiBytes, sigAlg.getJcaName());
		
		HashMap<String, Object> payload = new HashMap<>();
		
		payload.put("iss", Props.getProp("webHost"));
		payload.put("sub", subject.getUsername());
		payload.put("rol", subject.getRole());
		payload.put("uid", Integer.toString(subject.getId()));
		
		JwtBuilder builder = Jwts.builder()
				.setClaims(payload)
				.setIssuedAt(now)
				.signWith(sigAlg, signingKey);
		
		long expMillis = nowMillis + 86400000;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);
		
		
		
		return builder.compact();
	}
}
