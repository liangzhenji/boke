package Blog.Blogtest.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder{
	
	
	public String encode(CharSequence arg0) {
		return arg0.toString();
	}


	public boolean matches(CharSequence arg0, String arg1) {
		return arg1.equals(arg0.toString());
	}

}
