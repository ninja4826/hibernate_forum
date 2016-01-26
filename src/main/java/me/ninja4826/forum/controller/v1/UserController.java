package me.ninja4826.forum.controller.v1;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.FetchMode;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.ninja4826.forum.model.entity.User;
import me.ninja4826.forum.model.table.UserTable;
import me.ninja4826.forum.model.table.registry.TableRegistry;
import me.ninja4826.forum.util.Props;

public class UserController extends Controller {
	
	private UserTable userTable = (UserTable) TableRegistry.getTable("User");
	
	public UserController() {
		
		this.get("/users", (req, res) -> {
			return userTable.getAll();
		});
		
		this.get("/test", (req, res) -> {
			return userTable.find((c) -> {
				
				c.setFetchMode("topics", FetchMode.JOIN)
					.setFetchMode("topics.category", FetchMode.JOIN)
					.setFetchMode("posts", FetchMode.JOIN);
				return c;
			});
		});
	}
	
	private static String createJWT(User u) {
		SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		String secret = Props.getProp("jwtSecret");
		byte[] apiBytes = DatatypeConverter.parseBase64Binary(secret);
		
		Key signingKey = new SecretKeySpec(apiBytes, sigAlg.getJcaName());
		
		HashMap<String, Object> payload = new HashMap<>();
		
		payload.put("iss", Props.getProp("webHost"));
		payload.put("sub", u.getUsername());
		payload.put("rol", u.getRole());
		payload.put("uid", Integer.toString(u.getID()));
		
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
