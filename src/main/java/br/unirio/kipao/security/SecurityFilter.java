package br.unirio.kipao.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import br.unirio.kipao.security.models.Credentials;
import br.unirio.kipao.security.models.Credentials.CredentialType;
import br.unirio.kipao.security.models.SecurityProperties;
import br.unirio.kipao.security.models.User;
import br.unirio.kipao.security.roles.RoleConstants;
import br.unirio.kipao.security.roles.RoleService;
import br.unirio.kipao.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private CookieService cookieUtils;

	@Autowired
	private SecurityProperties securityProps;

	@Autowired
	RoleService securityRoleService;

	@Autowired
	private FirebaseAuth firebaseAuth;
	
	@Autowired
	private CustomerService customerService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		authorize(request);
		filterChain.doFilter(request, response);
	}

	private void authorize(HttpServletRequest request) {
		String sessionCookieValue = null;
		FirebaseToken decodedToken = null;
		CredentialType type = null;
		// Token verification
		boolean strictServerSessionEnabled = securityProps.getFirebaseProps().isEnableStrictServerSession();
		Cookie sessionCookie = cookieUtils.getCookie("session");
		String token = securityService.getBearerToken(request);
		try {
			if (sessionCookie != null) {
				sessionCookieValue = sessionCookie.getValue();
				decodedToken = firebaseAuth.verifySessionCookie(sessionCookieValue,
						securityProps.getFirebaseProps().isEnableCheckSessionRevoked());
				type = CredentialType.SESSION;
			} else if (!strictServerSessionEnabled && token != null && !token.equals("null")
					&& !token.equalsIgnoreCase("undefined")) {
				decodedToken = firebaseAuth.verifyIdToken(token);
				type = CredentialType.ID_TOKEN;
			}
		} catch (FirebaseAuthException e) {
			log.error("Firebase Exception:: ", e.getLocalizedMessage());
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		User user = firebaseTokenToUserDto(decodedToken);
		// Handle roles
		if (user != null) {
			// Handle Super Role
			if (securityProps.getSuperAdmins().contains(user.getEmail())) {
				if (!decodedToken.getClaims().containsKey(RoleConstants.ROLE_EMPLOYEE)) {
					try {
						securityRoleService.addRole(decodedToken.getUid(), RoleConstants.ROLE_EMPLOYEE);
					} catch (Exception e) {
						log.error("Super Role registration exception ", e);
					}
				}
				authorities.add(new SimpleGrantedAuthority(RoleConstants.ROLE_EMPLOYEE));
			}
			if(!decodedToken.getClaims().containsKey(RoleConstants.ROLE_CUSTOMER)) {
				try {
					securityRoleService.addRole(decodedToken.getUid(), RoleConstants.ROLE_CUSTOMER);
				} catch (Exception e) {
					log.error("Customer Role registration exception ", e);
				}
			}
			customerService.createCustomer(user);
			authorities.add(new SimpleGrantedAuthority(RoleConstants.ROLE_CUSTOMER));
			// Handle Other roles
			decodedToken.getClaims().forEach((k, v) -> authorities.add(new SimpleGrantedAuthority(k)));
			// Set security context
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
					new Credentials(type, decodedToken, token, sessionCookieValue), authorities);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

	private User firebaseTokenToUserDto(FirebaseToken decodedToken) {
		User user = null;
		if (decodedToken != null) {
			user = new User();
			user.setUid(decodedToken.getUid());
			user.setName(decodedToken.getName());
			user.setEmail(decodedToken.getEmail());
			user.setPicture(decodedToken.getPicture());
			user.setIssuer(decodedToken.getIssuer());
			user.setEmailVerified(decodedToken.isEmailVerified());
			
			user.setRoles(extractRoles(decodedToken));
		}
		return user;
	}

	private List<String> extractRoles(FirebaseToken decodedToken) {
		List<String> roles = new ArrayList<String>();
		for(Entry<String, Object> claim : decodedToken.getClaims().entrySet())
		{
			if(claim.getKey().contains("ROLE")){
				roles.add(claim.getKey());
			}
		}
		return roles;
	}

}
